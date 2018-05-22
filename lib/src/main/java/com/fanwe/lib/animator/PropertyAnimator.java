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

import android.animation.ObjectAnimator;

/**
 * 属性动画接口
 */
public interface PropertyAnimator<T extends PropertyAnimator> extends Animator<T>
{
    String X = "x";
    String Y = "y";
    String TRANSLATION_X = "translationX";
    String TRANSLATION_Y = "translationY";
    String ALPHA = "alpha";
    String SCALE_X = "scaleX";
    String SCALE_Y = "scaleY";
    String ROTATION = "rotation";
    String ROTATION_X = "rotationX";
    String ROTATION_Y = "rotationY";

    /**
     * 设置x坐标（相对于父容器）
     *
     * @param values
     * @return
     */
    T x(float... values);

    /**
     * 设置y坐标（相对于父容器）
     *
     * @param values
     * @return
     */
    T y(float... values);

    /**
     * 设置x方向相对于当前位置的偏移量
     *
     * @param values
     * @return
     */
    T translationX(float... values);

    /**
     * 设置y方向相对于当前位置的偏移量
     *
     * @param values
     * @return
     */
    T translationY(float... values);

    /**
     * 设置透明度
     *
     * @param values
     * @return
     */
    T alpha(float... values);

    /**
     * 设置缩放x
     *
     * @param values
     * @return
     */
    T scaleX(float... values);

    /**
     * 设置缩放y
     *
     * @param values
     * @return
     */
    T scaleY(float... values);

    /**
     * 设置绕z轴方向旋转角度（大于0顺时针，小于0逆时针）
     *
     * @param values
     * @return
     */
    T rotation(float... values);

    /**
     * 设置绕x轴方向旋转角度
     *
     * @param values
     * @return
     */
    T rotationX(float... values);

    /**
     * 设置绕y轴方向旋转角度
     *
     * @param values
     * @return
     */
    T rotationY(float... values);

    /**
     * 设置重复次数
     *
     * @param count 如果count小于0则无限重复
     * @return
     */
    T setRepeatCount(int count);

    /**
     * 获得重复次数
     *
     * @return
     */
    int getRepeatCount();

    /**
     * 返回属性名称
     *
     * @return
     */
    String getPropertyName();

    /**
     * 转为{@link ObjectAnimator}
     *
     * @return
     */
    ObjectAnimator toObjectAnimator();
}
