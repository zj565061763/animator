package com.fanwe.library.animator.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class SDAnimatorListener extends AnimatorListenerAdapter
{
    private WeakReference<View> mTarget;

    public SDAnimatorListener()
    {
    }

    public SDAnimatorListener(View target)
    {
        setTarget(target);
    }

    public SDAnimatorListener setTarget(View target)
    {
        if (target != null)
        {
            mTarget = new WeakReference<>(target);
        } else
        {
            mTarget = null;
        }
        return this;
    }

    public View getTarget()
    {
        if (mTarget != null)
        {
            return mTarget.get();
        } else
        {
            return null;
        }
    }

    @Override
    public void onAnimationStart(Animator animation)
    {
        super.onAnimationStart(animation);
        if (animation instanceof ObjectAnimator)
        {
            ObjectAnimator objectAnimator = (ObjectAnimator) animation;
            Object objectTarget = objectAnimator.getTarget();
            if (objectTarget instanceof View)
            {
                if (getTarget() == null)
                {
                    setTarget((View) objectTarget);
                }
            }
        }
    }
}
