/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 动画链
 */
public class FAnimatorChain implements AnimatorChain
{
    private final AnimatorSet mAnimatorSet = new AnimatorSet();
    private NodeAnimator mCurrent;

    private final boolean mIsDebug;
    private List<NodeAnimator> mListNode;

    private FAnimatorChain(boolean isDebug)
    {
        mIsDebug = isDebug;
    }

    /**
     * 返回一个节点动画
     *
     * @return
     */
    public static NodeAnimator node()
    {
        return node(false);
    }

    /**
     * 返回一个节点动画
     *
     * @param isDebug true-调试模式，会输出整个动画链的结构，方便开发调试，可以给每个节点动画设置tag，来加强描述
     * @return
     */
    public static NodeAnimator node(boolean isDebug)
    {
        final FAnimatorChain chain = new FAnimatorChain(isDebug);
        final NodeAnimator head = new InternalNodeAnimator(NodeAnimator.Type.HEAD, chain);
        chain.setHead(head);
        return head;
    }

    private void setHead(NodeAnimator animator)
    {
        if (animator == null)
            throw new NullPointerException("animator is null");
        if (animator.getType() != NodeAnimator.Type.HEAD)
            throw new IllegalArgumentException("HEAD animator required");
        if (mCurrent != null)
            throw new UnsupportedOperationException("HEAD has been provided");

        mCurrent = animator;
        mAnimatorSet.play(animator.toObjectAnimator());
        addNodeIfNeed(animator);
    }

    @Override
    public NodeAnimator with()
    {
        return with(null);
    }

    @Override
    public NodeAnimator with(View target)
    {
        return initNodeAnimator(new InternalNodeAnimator(NodeAnimator.Type.WITH, this).setTarget(target));
    }

    @Override
    public NodeAnimator withClone()
    {
        final InternalNodeAnimator animator = (InternalNodeAnimator) mCurrent.clone();
        animator.setType(NodeAnimator.Type.WITH);
        return initNodeAnimator(animator);
    }

    @Override
    public NodeAnimator next()
    {
        return next(null);
    }

    @Override
    public NodeAnimator next(View target)
    {
        return initNodeAnimator(new InternalNodeAnimator(NodeAnimator.Type.NEXT, this).setTarget(target));
    }

    @Override
    public NodeAnimator delay(long time)
    {
        return initNodeAnimator(new InternalNodeAnimator(NodeAnimator.Type.DELAY, this).setDuration(time));
    }

    /**
     * 初始化节点动画
     *
     * @param animator
     * @return
     */
    private NodeAnimator initNodeAnimator(NodeAnimator animator)
    {
        if (animator == null)
            throw new NullPointerException("animator is null");

        final View target = animator.getTarget();
        if (target == null) animator.setTarget(mCurrent.getTarget());

        final int type = animator.getType();
        switch (type)
        {
            case NodeAnimator.Type.WITH:
                mAnimatorSet.play(mCurrent.toObjectAnimator()).with(animator.toObjectAnimator());
                break;
            case NodeAnimator.Type.NEXT:
            case NodeAnimator.Type.DELAY:
                mAnimatorSet.play(animator.toObjectAnimator()).after(mCurrent.toObjectAnimator());
                break;
        }
        setCurrent(animator);

        return animator;
    }

    private void setCurrent(NodeAnimator animator)
    {
        if (mCurrent == null)
            throw new RuntimeException("HEAD animator must be provided before this");
        if (animator.getType() == NodeAnimator.Type.HEAD)
            throw new UnsupportedOperationException("HEAD animator is not supported here");

        mCurrent = animator;
        addNodeIfNeed(animator);
    }

    private void addNodeIfNeed(NodeAnimator animator)
    {
        if (mIsDebug)
        {
            if (mListNode == null) mListNode = new ArrayList<>();
            mListNode.add(animator);
        }
    }

    @Override
    public AnimatorSet toAnimatorSet()
    {
        return mAnimatorSet;
    }

    @Override
    public void start()
    {
        if (mIsDebug)
        {
            if (mListNode != null)
            {
                final StringBuilder sb = new StringBuilder("----------");
                for (NodeAnimator item : mListNode)
                {
                    switch (item.getType())
                    {
                        case NodeAnimator.Type.HEAD:
                            sb.append("\r\n").append("Head:").append(item.getTag());
                            break;
                        case NodeAnimator.Type.NEXT:
                            sb.append("\r\n").append("Next:").append(item.getTag());
                            break;
                        case NodeAnimator.Type.WITH:
                            sb.append(" With:").append(item.getTag());
                            break;
                        case NodeAnimator.Type.DELAY:
                            sb.append("\r\n").append("Delay:").append(item.getTag());
                            break;
                    }
                }
                Log.i(AnimatorChain.class.getSimpleName(), sb.toString());
            }
        }

        mAnimatorSet.start();
    }

    @Override
    public void startAsPop()
    {
        final ArrayList<android.animation.Animator> listChild = mAnimatorSet.getChildAnimations();
        if (listChild == null || listChild.isEmpty())
        {
            return;
        }
        final HashMap<View, ImageView> mapCache = new HashMap<>();
        for (Animator animator : listChild)
        {
            final View target = (View) ((ObjectAnimator) animator).getTarget();
            if (target == null) continue;

            final ImageView cache = mapCache.get(target);
            if (cache == null)
            {
                final Context context = target.getContext();
                if (context instanceof Activity)
                {
                    PopImageView imageView = new PopImageView(context);
                    imageView.setDrawingCacheView(target);
                    imageView.attachTarget(target);

                    animator.setTarget(imageView);
                    mapCache.put(target, imageView);
                }
            } else
            {
                animator.setTarget(cache);
            }
        }
        start();
    }

    @Override
    public boolean isRunning()
    {
        return mAnimatorSet.isRunning();
    }

    @Override
    public boolean isStarted()
    {
        return mAnimatorSet.isStarted();
    }

    @Override
    public void cancel()
    {
        mAnimatorSet.cancel();
    }

    private static class InternalNodeAnimator extends BaseAnimator<NodeAnimator> implements NodeAnimator
    {
        private int mType;
        private final AnimatorChain mChain;

        private InternalNodeAnimator(int type, AnimatorChain chain)
        {
            setType(type);
            mChain = chain;
        }

        private void setType(int type)
        {
            if (type == Type.HEAD || type == Type.WITH
                    || type == Type.NEXT || type == Type.DELAY)
            {
                mType = type;
            } else
            {
                throw new IllegalArgumentException("type must be value of NodeAnimator.Type.XXX");
            }
        }

        @Override
        public final int getType()
        {
            return mType;
        }

        @Override
        public final AnimatorChain chain()
        {
            if (isEmptyProperty() && mType != Type.DELAY)
                throw new UnsupportedOperationException("can not access AnimatorChain because animator property is empty");
            if (getTarget() == null)
                throw new UnsupportedOperationException("target view must be provided before this see the Animator.setTarget(View) method");
            return mChain;
        }
    }
}
