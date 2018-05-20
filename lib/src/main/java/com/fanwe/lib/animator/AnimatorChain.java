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

/**
 * 动画链
 */
public interface AnimatorChain
{
    /**
     * 返回当前节点动画
     *
     * @return
     */
    NodeAnimator currentNode();

    /**
     * 创建一个新的节点动画，新动画和上一个动画同时执行
     *
     * @return
     */
    NodeAnimator nodeWith();

    /**
     * {@link #nodeWith()}
     *
     * @param clone 是否复制上一个动画的设置参数，比如动画时长等
     * @return
     */
    NodeAnimator nodeWith(boolean clone);

    /**
     * 创建一个新的节点动画，新动画在上一个动画执行完成后执行
     *
     * @return
     */
    NodeAnimator nodeNext();

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
