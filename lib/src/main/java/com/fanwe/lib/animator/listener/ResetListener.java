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
import android.view.View;

public class ResetListener extends LifecycleListener
{
    public ResetListener(Lifecycle lifecycle)
    {
        super(lifecycle);
    }

    public ResetListener(Lifecycle lifecycle, View target)
    {
        super(lifecycle, target);
    }

    @Override
    public void onAnimationLifecycle(Animator animator)
    {
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
