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

import android.animation.AnimatorSet;
import android.view.View;

/**
 * 动画链
 */
public interface AnimatorChain
{
    /**
     * 生成一个新动画和上一个动画同时执行
     *
     * @return
     */
    NodeAnimator with();

    /**
     * {@link #with()}
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用上一个动画的View对象
     * @return
     */
    NodeAnimator with(View target);

    /**
     * 在{@link #with()}方法的基础上会复制上一个动画的一些设置属性，比如动画时长等
     *
     * @return
     */
    NodeAnimator withClone();

    /**
     * 生成一个新动画在上一个动画执行完成后执行
     *
     * @return
     */
    NodeAnimator next();

    /**
     * {@link #next()}
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用上一个动画的View对象
     * @return
     */
    NodeAnimator next(View target);

    /**
     * 生成一个延迟动画在上一个动画执行完成后执行
     *
     * @param time 延迟多少毫秒
     * @return
     */
    NodeAnimator delay(long time);

    /**
     * 转为{@link AnimatorSet}
     *
     * @return
     */
    AnimatorSet toAnimatorSet();

    /**
     * 开始动画
     *
     * @return
     */
    AnimatorChain start();

    /**
     * 对target截图然后设置给ImageView，让ImageView镜像在android.R.id.content的FrameLayout里面执行动画
     *
     * @return
     */
    AnimatorChain startAsPop();

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
