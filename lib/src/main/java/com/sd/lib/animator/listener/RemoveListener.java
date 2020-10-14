package com.sd.lib.animator.listener;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class RemoveListener extends AnimatorLifecycleListener
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
        final View target = getTarget();
        if (target == null)
            return;

        final ViewParent parent = target.getParent();
        if (parent == null)
            return;

        try
        {
            ((ViewGroup) parent).removeView(target);
        } catch (Exception e)
        {
        }
    }
}
