package com.sd.lib.animator.listener;

import android.animation.Animator;
import android.view.View;

public class GoneListener extends AnimatorLifecycleListener
{
    public GoneListener(Lifecycle lifecycle)
    {
        super(lifecycle);
    }

    public GoneListener(Lifecycle lifecycle, View target)
    {
        super(lifecycle, target);
    }

    @Override
    public void onAnimationLifecycle(Animator animator)
    {
        final View target = getTarget();
        if (target != null)
            target.setVisibility(View.GONE);
    }
}
