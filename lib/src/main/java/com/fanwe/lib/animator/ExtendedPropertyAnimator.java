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
package com.fanwe.lib.animator;

import android.view.View;

import com.fanwe.lib.animator.aligner.Aligner;

public interface ExtendedPropertyAnimator<T extends ExtendedPropertyAnimator> extends PropertyAnimator<T>
{
    /**
     * 移动到屏幕x坐标
     *
     * @param values
     * @return
     */
    T moveToX(float... values);

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    T moveToY(float... values);

    /**
     * 移动到views的屏幕x坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    T moveToX(Aligner aligner, View... views);

    /**
     * 移动到views的屏幕y坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    T moveToY(Aligner aligner, View... views);

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    T scaleX(View... views);

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    T scaleY(View... views);

    /**
     * 设置tag
     *
     * @param tag
     * @return
     */
    T setTag(String tag);

    /**
     * 返回设置的tag{@link #setTag(String)}
     *
     * @return
     */
    String getTag();

    /**
     * 实现原理： <br>
     * <p>
     * 1.对target截图然后设置给ImageView <br>
     * 2.把ImageView添加到Activity中android.R.id.content的FrameLayout里面 <br>
     * 注意：这里的Activity对象是从原target获取，所以要保证原target的getContext()返回的是Activity对象，否则会失败 <br>
     * 3.根据传入的参数是否克隆，来决定把ImageView设置给哪个动画对象执行
     * <p>
     * 参数说明： <br>
     * clone == true，克隆当前对象执行，返回克隆的对象 <br>
     * clone == false，执行当前对象，返回当前对象 <br>
     * 不克隆的性能会好一点，但是会修改当前动画对象的target，开发者可以根据具体的应用场景来决定是否克隆
     *
     * @param clone
     * @return 如果返回不为null，表示返回的是克隆对象或者当前对象，取决于传入的参数；如果返回null，表示执行失败
     */
    T startAsPop(boolean clone);
}
