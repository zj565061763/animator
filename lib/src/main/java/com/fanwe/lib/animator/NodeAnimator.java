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

/**
 * 节点动画
 */
public interface NodeAnimator extends ExtendedPropertyAnimator<NodeAnimator>
{
    /**
     * 返回节点类型{@link Type}
     *
     * @return
     */
    Type getType();

    /**
     * 返回动画链{@link AnimatorChain}
     *
     * @return
     */
    AnimatorChain chain();

    /**
     * 添加一个新的节点动画，新动画和当前动画同时执行
     *
     * @return
     */
    NodeAnimator with();

    /**
     * {@link #with()}的基础上，复制当前动画的设置参数，比如动画时长等，监听对象不会被复制
     *
     * @return
     */
    NodeAnimator withClone();

    /**
     * 添加一个新的节点动画，新动画在当前动画执行完成后执行
     *
     * @return
     */
    NodeAnimator next();

    /**
     * 节点类型
     */
    enum Type
    {
        Head,
        /**
         * 新动画和上一个动画同时执行
         */
        With,
        /**
         * 新动画在上一个动画执行完成后执行
         */
        Next
    }
}
