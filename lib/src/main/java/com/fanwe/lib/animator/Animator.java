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

import android.animation.TimeInterpolator;
import android.view.View;

/**
 * 动画接口
 */
public interface Animator<T extends Animator> extends Cloneable
{
    /**
     * 设置要执行动画的view
     *
     * @param target
     * @return
     */
    T setTarget(View target);

    /**
     * 获得执行动画的view
     *
     * @return
     */
    View getTarget();

    /**
     * 设置动画时长
     *
     * @param duration 毫秒
     * @return
     */
    T setDuration(long duration);

    /**
     * 获得动画时长
     *
     * @return
     */
    long getDuration();

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
     * 设置插值器
     *
     * @param interpolator
     * @return
     */
    T setInterpolator(TimeInterpolator interpolator);

    /**
     * 获得插值器
     *
     * @return
     */
    TimeInterpolator getInterpolator();

    /**
     * 设置动画延迟多久开始执行
     *
     * @param delay 毫秒
     * @return
     */
    T setStartDelay(long delay);

    /**
     * 获得动画延迟多久开始执行
     *
     * @return
     */
    long getStartDelay();

    /**
     * 添加动画监听，内部不会进行{@link #containsListener(android.animation.Animator.AnimatorListener...)}的判断
     *
     * @param listeners
     * @return
     */
    T addListener(android.animation.Animator.AnimatorListener... listeners);

    /**
     * 移除动画监听
     *
     * @param listeners
     */
    T removeListener(android.animation.Animator.AnimatorListener... listeners);

    /**
     * 是否包含动画监听
     *
     * @param listeners
     * @return
     */
    boolean containsListener(android.animation.Animator.AnimatorListener... listeners);

    /**
     * 清空监听
     *
     * @return
     */
    T clearListener();

    /**
     * 开始执行动画
     */
    void start();

    /**
     * 动画是否处于运行中
     *
     * @return
     */
    boolean isRunning();

    /**
     * 动画是否已经被启动
     *
     * @return
     */
    boolean isStarted();

    /**
     * 取消动画
     */
    void cancel();

    /**
     * 克隆动画，监听对象不会被克隆
     *
     * @return
     */
    T clone();
}
