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
package com.fanwe.library.animator;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.view.View;

/**
 * 动画接口
 */
public interface ISDAnim
{
    /**
     * 设置要执行动画的view
     *
     * @param target
     * @return
     */
    ISDAnim setTarget(View target);

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
    ISDAnim setDuration(long duration);

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
    ISDAnim setRepeatCount(int count);

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
    ISDAnim setInterpolator(TimeInterpolator interpolator);

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
    ISDAnim setStartDelay(long delay);

    /**
     * 获得动画延迟多久开始执行
     *
     * @return
     */
    long getStartDelay();

    /**
     * 添加动画监听
     *
     * @param listener
     * @return
     */
    ISDAnim addListener(Animator.AnimatorListener listener);

    /**
     * 移除动画监听
     *
     * @param listener
     */
    ISDAnim removeListener(Animator.AnimatorListener listener);

    /**
     * 清空监听
     *
     * @return
     */
    ISDAnim clearListener();

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
}
