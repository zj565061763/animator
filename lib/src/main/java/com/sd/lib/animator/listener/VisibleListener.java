package com.sd.lib.animator.listener;

import android.animation.Animator;
import android.view.View;

public class VisibleListener extends FLifecycleAnimatorListener
{
    public VisibleListener(Lifecycle lifecycle)
    {
        super(lifecycle);
    }

    public VisibleListener(Lifecycle lifecycle, View target)
    {
        super(lifecycle, target);
    }

    @Override
    public void onAnimationLifecycle(Animator animator)
    {
        final View target = getTarget();
        if (target == null)
            return;

        if (target.getVisibility() != View.VISIBLE)
            target.setVisibility(View.VISIBLE);
    }
}
