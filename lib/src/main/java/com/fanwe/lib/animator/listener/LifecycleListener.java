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
import android.view.View;

public abstract class LifecycleListener extends FAnimatorListener
{
    private final int mLifecycle;

    public LifecycleListener(int lifecycle)
    {
        this(lifecycle, null);
    }

    public LifecycleListener(int lifecycle, View target)
    {
        super(target);

        if (lifecycle == Lifecycle.START
                || lifecycle == Lifecycle.PAUSE
                || lifecycle == Lifecycle.RESUME
                || lifecycle == Lifecycle.REPEAT
                || lifecycle == Lifecycle.CANCEL
                || lifecycle == Lifecycle.END)
        {
            mLifecycle = lifecycle;
        } else
        {
            throw new IllegalArgumentException("lifecycle must be value of LifecycleListener.Lifecycle.XXX");
        }
    }

    public final int getLifecycle()
    {
        return mLifecycle;
    }

    @Override
    public final void onAnimationStart(Animator animation)
    {
        super.onAnimationStart(animation);
        onAnimationLifecycle(Lifecycle.START, animation);
    }

    @Override
    public final void onAnimationPause(Animator animation)
    {
        super.onAnimationPause(animation);
        onAnimationLifecycle(Lifecycle.PAUSE, animation);
    }

    @Override
    public final void onAnimationResume(Animator animation)
    {
        super.onAnimationResume(animation);
        onAnimationLifecycle(Lifecycle.RESUME, animation);
    }

    @Override
    public final void onAnimationRepeat(Animator animation)
    {
        super.onAnimationRepeat(animation);
        onAnimationLifecycle(Lifecycle.REPEAT, animation);
    }

    @Override
    public final void onAnimationCancel(Animator animation)
    {
        super.onAnimationCancel(animation);
        onAnimationLifecycle(Lifecycle.CANCEL, animation);
    }

    @Override
    public final void onAnimationEnd(Animator animation)
    {
        super.onAnimationEnd(animation);
        onAnimationLifecycle(Lifecycle.END, animation);
    }

    private void onAnimationLifecycle(int lifecycle, Animator animator)
    {
        if (lifecycle == mLifecycle)
        {
            onAnimationLifecycle(animator);
        }
    }

    public abstract void onAnimationLifecycle(Animator animator);

    public static final class Lifecycle
    {
        public static final int START = 0;
        public static final int PAUSE = 1;
        public static final int RESUME = 2;
        public static final int REPEAT = 3;
        public static final int CANCEL = 4;
        public static final int END = 5;
    }
}
