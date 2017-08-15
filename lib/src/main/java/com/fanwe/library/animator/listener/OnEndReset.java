package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.view.View;

/**
 * 动画结束重置view
 */
public class OnEndReset extends SDAnimatorListener
{
    public OnEndReset()
    {
        super();
    }

    public OnEndReset(View target)
    {
        super(target);
    }

    @Override
    public void onAnimationEnd(Animator animation)
    {
        super.onAnimationEnd(animation);

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
