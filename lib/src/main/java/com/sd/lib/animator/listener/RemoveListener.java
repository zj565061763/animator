package com.sd.lib.animator.listener;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;

public class RemoveListener extends LifecycleListener
{
    public RemoveListener(Lifecycle lifecycle)
    {
        super(lifecycle);
    }

    public RemoveListener(Lifecycle lifecycle, View target)
    {
        super(lifecycle, target);
    }

    @Override
    public void onAnimationLifecycle(Animator animator)
    {
        try
        {
            final View target = getTarget();
            if (target != null)
                ((ViewGroup) target.getParent()).removeView(target);
        } catch (Exception e)
        {
        }
    }
}
