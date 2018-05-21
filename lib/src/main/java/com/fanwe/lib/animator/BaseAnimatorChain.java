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
import android.text.TextUtils;
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
    private final List<NodeAnimator> mListNode = new ArrayList<>();

    private boolean mIsDebug;

    public BaseAnimatorChain(SimpleNodeAnimator animator)
    {
        checkAnimator(animator, NodeAnimator.Type.Head);
        mListNode.add(animator);
    }

    @Override
    public NodeAnimator currentNode()
    {
        return mListNode.get(mListNode.size() - 1);
    }

    @Override
    public NodeAnimator newNode(NodeAnimator.Type type, boolean clone)
    {
        checkHeadTarget();
        return createNode(type, clone);
    }

    private NodeAnimator createNode(NodeAnimator.Type type, boolean clone)
    {
        if (type == null)
            throw new NullPointerException("type is null");
        if (type == NodeAnimator.Type.Head)
            throw new IllegalArgumentException("Illegal type:" + type);

        final NodeAnimator current = currentNode();
        final NodeAnimator animator = clone ? current.cloneToType(type) : onCreateNodeAnimator(type);

        checkAnimator(animator, type);
        checkChain(animator);

        if (animator.getTarget() == null) animator.setTarget(current.getTarget());

        mListNode.add(animator);
        return animator;
    }

    protected abstract NodeAnimator onCreateNodeAnimator(NodeAnimator.Type type);

    private void orderNode()
    {
        ObjectAnimator lastAnimator = null;

        for (NodeAnimator item : mListNode)
        {
            final ObjectAnimator currentAnimator = item.toObjectAnimator();
            switch (item.getType())
            {
                case Head:
                    mAnimatorSet.play(currentAnimator);
                    break;
                case With:
                    mAnimatorSet.play(lastAnimator).with(currentAnimator);
                    break;
                case Next:
                    mAnimatorSet.play(currentAnimator).after(lastAnimator);
                    break;
            }
            lastAnimator = currentAnimator;
        }

        logIfNeed();
    }

    private void logIfNeed()
    {
        if (mIsDebug)
        {
            final StringBuilder sb = new StringBuilder("----------");
            for (NodeAnimator item : mListNode)
            {
                switch (item.getType())
                {
                    case Head:
                        sb.append("\r\n").append("Head:");
                        break;
                    case With:
                        sb.append(" With:");
                        break;
                    case Next:
                        sb.append("\r\n").append("Next:");
                        break;
                }

                sb.append("(");
                if (!TextUtils.isEmpty(item.getTag()))
                    sb.append(item.getTag()).append(" ");
                sb.append(item.getPropertyName()).append(":").append(String.valueOf(item.getDuration()));
                if (item.getStartDelay() > 0)
                    sb.append(" startDelay:").append(String.valueOf(item.getStartDelay()));
                sb.append(")");
            }
            Log.i(AnimatorChain.class.getSimpleName(), sb.toString());
        }
    }

    @Override
    public AnimatorSet toAnimatorSet()
    {
        checkHeadTarget();
        orderNode();
        return mAnimatorSet;
    }

    @Override
    public AnimatorChain start()
    {
        toAnimatorSet().start();
        return this;
    }

    @Override
    public AnimatorChain startAsPop()
    {
        final AnimatorSet animatorSet = toAnimatorSet();

        final ArrayList<android.animation.Animator> listChild = animatorSet.getChildAnimations();
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
        animatorSet.start();
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

    @Override
    public AnimatorChain setDebug(boolean debug)
    {
        mIsDebug = debug;
        return this;
    }

    //---------- check start ----------

    private static void checkAnimator(NodeAnimator animator, NodeAnimator.Type targetType)
    {
        if (animator == null)
            throw new NullPointerException("animator is null");
        if (targetType == null)
            throw new NullPointerException("targetType is null");
        if (animator.getType() != targetType)
            throw new RuntimeException("animator must be " + targetType + " type");
    }

    private void checkChain(NodeAnimator animator)
    {
        if (animator.chain() != this)
            throw new RuntimeException("animator's chain() method must return current instance");
    }

    private void checkHeadTarget()
    {
        final NodeAnimator animator = currentNode();
        if (animator.getType() == NodeAnimator.Type.Head && animator.getTarget() == null)
            throw new NullPointerException(NodeAnimator.Type.Head + " animator's target must not be null");
    }

    //---------- check end ----------
}
