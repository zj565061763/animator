package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.view.View;

/**
 * 动画结束设置view可见
 */
public class OnEndVisible extends SDAnimatorListener
{
    public OnEndVisible()
    {
        super();
    }

    public OnEndVisible(View target)
    {
        super(target);
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        super.onAnimationEnd(animation);

        final View target = getTarget();
        if (target != null && target.getVisibility() != View.VISIBLE)
        {
            target.setVisibility(View.VISIBLE);
        }
    }
}
