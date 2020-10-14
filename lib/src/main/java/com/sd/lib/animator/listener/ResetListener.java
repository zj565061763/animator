package com.sd.lib.animator.listener;

import android.animation.Animator;
import android.view.View;

public class ResetListener extends FLifecycleAnimatorListener
{
    public ResetListener(Lifecycle lifecycle)
    {
        super(lifecycle);
    }

    public ResetListener(Lifecycle lifecycle, View target)
    {
        super(lifecycle, target);
    }

    @Override
    public void onAnimationLifecycle(Animator animator)
    {
        final View target = getTarget();
        if (target != null)
        {
            target.setAlpha(1.0f);
            target.setRotation(0.0f);
            target.setRotationX(0.0f);
            target.setRotationY(0.0f);
            target.setTranslationX(0.0f);
            target.setTranslationY(0.0f);
            target.setScaleX(1.0f);
            target.setScaleY(1.0f);
        }
    }
}
