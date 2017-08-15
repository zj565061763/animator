package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 动画结束移除view
 */
public class OnEndRemoveView extends SDAnimatorListener
{
    public OnEndRemoveView()
    {
        super();
    }

    public OnEndRemoveView(View target)
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
            try
            {
                final ViewParent viewParent = target.getParent();
                ((ViewGroup) viewParent).removeView(target);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
