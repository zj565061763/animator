package com.sd.lib.animator.listener;

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
