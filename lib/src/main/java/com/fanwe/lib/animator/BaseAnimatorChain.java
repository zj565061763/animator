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
abstract class BaseAnimatorChain implements AnimatorChain
{
    private final AnimatorSet mAnimatorSet = new AnimatorSet();
    private NodeAnimator mCurrent;

    private final boolean mIsDebug;
    private List<NodeAnimator> mListNode;

    public BaseAnimatorChain(boolean isDebug, FNodeAnimator animator)
    {
        checkNull(animator);
        if (animator.getType() != NodeAnimator.Type.HEAD)
            throw new IllegalArgumentException("HEAD animator is required");

        mIsDebug = isDebug;
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
        return initNodeAnimator(createNodeAnimator(NodeAnimator.Type.WITH).setTarget(target));
    }

    @Override
    public NodeAnimator withClone()
    {
        final NodeAnimator animator = mCurrent.cloneToType(NodeAnimator.Type.WITH);
        checkNull(animator);
        if (animator.getType() != NodeAnimator.Type.WITH)
            throw new RuntimeException("clone animator must be NodeAnimator.Type.WITH type");
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
        return initNodeAnimator(createNodeAnimator(NodeAnimator.Type.NEXT).setTarget(target));
    }

    private NodeAnimator createNodeAnimator(final int type)
    {
        final NodeAnimator animator = onCreateNodeAnimator(type);
        checkNull(animator);
        if (animator.getType() != type)
            throw new RuntimeException("animator must be " + type + " type");
        if (animator.chain() != this)
            throw new RuntimeException("animator's chain() method must return current instance");
        return animator;
    }

    protected abstract NodeAnimator onCreateNodeAnimator(int type);

    /**
     * 初始化新节点动画
     *
     * @param animator
     * @return
     */
    private NodeAnimator initNodeAnimator(NodeAnimator animator)
    {
        final View target = animator.getTarget();
        if (target == null) animator.setTarget(mCurrent.getTarget());

        final int type = animator.getType();
        switch (type)
        {
            case NodeAnimator.Type.WITH:
                mAnimatorSet.play(mCurrent.toObjectAnimator()).with(animator.toObjectAnimator());
                break;
            case NodeAnimator.Type.NEXT:
                mAnimatorSet.play(animator.toObjectAnimator()).after(mCurrent.toObjectAnimator());
                break;
            default:
                throw new IllegalArgumentException("Illegal animator:" + type);
        }

        mCurrent = animator;
        addNodeIfNeed(animator);

        return animator;
    }

    private void addNodeIfNeed(NodeAnimator animator)
    {
        if (mIsDebug)
        {
            if (mListNode == null) mListNode = new ArrayList<>();
            mListNode.add(animator);
        }
    }

    private static void checkNull(NodeAnimator animator)
    {
        if (animator == null) throw new NullPointerException("animator is null");
    }

    @Override
    public AnimatorSet toAnimatorSet()
    {
        return mAnimatorSet;
    }

    @Override
    public AnimatorChain start()
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
                    }
                }
                Log.i(AnimatorChain.class.getSimpleName(), sb.toString());
            }
        }

        mAnimatorSet.start();
        return this;
    }

    @Override
    public AnimatorChain startAsPop()
    {
        final ArrayList<android.animation.Animator> listChild = mAnimatorSet.getChildAnimations();
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
        return this;
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
}
