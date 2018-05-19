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
package com.fanwe.lib.animator.listener.api;

import android.view.View;

import com.fanwe.lib.animator.listener.InvisibleListener;

/**
 * 动画开始设置view为View.INVISIBLE
 */
public class OnStartInvisible extends InvisibleListener
{
    public OnStartInvisible()
    {
        super(Lifecycle.START);
    }

    public OnStartInvisible(View target)
    {
        super(Lifecycle.START, target);
    }
}
