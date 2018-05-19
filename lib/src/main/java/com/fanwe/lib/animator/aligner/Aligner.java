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
package com.fanwe.lib.animator.aligner;

import android.view.View;

public interface Aligner
{
    /**
     * @param animatorView      动画view
     * @param alignView         动画view想要对齐的view
     * @param alignViewPosition 动画view想要对齐的view的x或者y（屏幕坐标）
     * @return
     */
    int align(View animatorView, View alignView, int alignViewPosition);

    Aligner DEFAULT = new Aligner()
    {
        @Override
        public int align(View animatorView, View alignView, int alignViewPosition)
        {
            return alignViewPosition;
        }
    };
}
