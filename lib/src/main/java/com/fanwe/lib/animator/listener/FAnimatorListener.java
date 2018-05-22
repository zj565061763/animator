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
package com.fanwe.lib.animator.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * 动画监听，重写此类的方法要记得先调用super的方法
 */
public abstract class FAnimatorListener extends AnimatorListenerAdapter
{
    private WeakReference<View> mTarget;
    private WeakReference<Animator> mAnimator;

    public FAnimatorListener()
    {
    }

    public FAnimatorListener(View target)
    {
        setTarget(target);
    }

    public final void setTarget(View target)
    {
        mTarget = target == null ? null : new WeakReference<>(target);
    }

    /**
     * 优先返回设置的target，如果为null的话，返回动画对象中的target
     *
     * @return
     */
    public final View getTarget()
    {
        final View targetSpec = mTarget == null ? null : mTarget.get();
        if (targetSpec != null) return targetSpec;

        final Animator animator = getAnimator();
        if (animator != null && animator instanceof ObjectAnimator)
        {
            final Object targetObj = ((ObjectAnimator) animator).getTarget();
            if (targetObj instanceof View)
            {
                return (View) targetObj;
            }
        }

        return null;
    }

    private void setAnimator(Animator animator)
    {
        final Animator old = getAnimator();
        if (old != animator)
        {
            mAnimator = animator == null ? null : new WeakReference<>(animator);
        }
    }

    private Animator getAnimator()
    {
        return mAnimator == null ? null : mAnimator.get();
    }

    @Override
    public void onAnimationStart(Animator animation)
    {
        super.onAnimationStart(animation);
        setAnimator(animation);
    }

    @Override
    public void onAnimationCancel(Animator animation)
    {
        super.onAnimationCancel(animation);
        setAnimator(animation);
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        super.onAnimationEnd(animation);
        setAnimator(animation);
    }

    @Override
    public void onAnimationRepeat(Animator animation)
    {
        super.onAnimationRepeat(animation);
        setAnimator(animation);
    }

    @Override
    public void onAnimationPause(Animator animation)
    {
        super.onAnimationPause(animation);
        setAnimator(animation);
    }

    @Override
    public void onAnimationResume(Animator animation)
    {
        super.onAnimationResume(animation);
        setAnimator(animation);
    }
}
