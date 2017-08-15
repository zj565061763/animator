package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.view.View;

/**
 * 动画开始设置view为View.GONE
 */
public class OnStartGone extends SDAnimatorListener
{
    public OnStartGone()
    {
        super();
    }

    public OnStartGone(View target)
    {
        super(target);
    }

    @Override
    public void onAnimationStart(Animator animation)
    {
        super.onAnimationStart(animation);

        final View target = getTarget();
        if (target != null && target.getVisibility() != View.GONE)
        {
            target.setVisibility(View.GONE);
        }
    }
}
