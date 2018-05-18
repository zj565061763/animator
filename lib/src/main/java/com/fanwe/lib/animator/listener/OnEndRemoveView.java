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
import android.view.ViewGroup;

/**
 * 动画结束移除view
 */
public class OnEndRemoveView extends FAnimatorListener
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

        try
        {
            final View target = getTarget();
            ((ViewGroup) target.getParent()).removeView(target);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
