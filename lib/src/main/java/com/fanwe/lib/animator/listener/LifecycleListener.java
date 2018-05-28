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
    private final Lifecycle mLifecycle;

    public LifecycleListener(Lifecycle lifecycle)
    {
        this(lifecycle, null);
    }

    public LifecycleListener(Lifecycle lifecycle, View target)
    {
        super(target);
        mLifecycle = lifecycle;
    }

    public final Lifecycle getLifecycle()
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

    private void onAnimationLifecycle(Lifecycle lifecycle, Animator animator)
    {
        if (lifecycle == mLifecycle)
            onAnimationLifecycle(animator);
    }

    public abstract void onAnimationLifecycle(Animator animator);

    public enum Lifecycle
    {
        START,
        PAUSE,
        RESUME,
        REPEAT,
        CANCEL,
        END
    }
}
