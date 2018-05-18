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
package com.fanwe.lib.animator.listener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;

public abstract class FAnimatorListener extends AnimatorListenerAdapter
{
    private WeakReference<View> mTarget;

    public FAnimatorListener()
    {
    }

    public FAnimatorListener(View target)
    {
        setTarget(target);
    }

    public final void setTarget(View target)
    {
        if (target != null)
        {
            mTarget = new WeakReference<>(target);
        } else
        {
            mTarget = null;
        }
    }

    public final View getTarget()
    {
        return mTarget == null ? null : mTarget.get();
    }

    @Override
    public void onAnimationStart(Animator animation)
    {
        super.onAnimationStart(animation);
        if (animation instanceof ObjectAnimator)
        {
            final Object target = ((ObjectAnimator) animation).getTarget();
            if (target != null && (target instanceof View))
            {
                if (getTarget() == null)
                {
                    setTarget((View) target);
                }
            }
        }
    }
}
