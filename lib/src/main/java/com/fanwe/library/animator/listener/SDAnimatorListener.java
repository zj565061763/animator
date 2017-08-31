/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
