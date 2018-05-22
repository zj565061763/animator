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
     * 把节点动画添加到动画链上
     *
     * @param animator
     * @return
     */
    NodeAnimator appendNode(NodeAnimator animator);

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
     * {@link ExtendedPropertyAnimator#startAsPop(boolean)}
     *
     * @return
     */
    AnimatorChain startAsPop(boolean clone);

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
     * 设置是否调试模式
     *
     * @param debug true-调试模式，会输出整个动画链的结构，方便开发调试，可以给每个节点动画设置tag，来加强描述
     * @return
     */
    AnimatorChain setDebug(boolean debug);
}
