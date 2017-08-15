package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.view.View;

/**
 * 动画结束设置view为View.GONE
 */
public class OnEndGone extends SDAnimatorListener
{
    public OnEndGone()
    {
        super();
    }

    public OnEndGone(View target)
    {
        super(target);
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        super.onAnimationEnd(animation);

        final View target = getTarget();
        if (target != null && target.getVisibility() != View.GONE)
        {
            target.setVisibility(View.GONE);
        }
    }
}
