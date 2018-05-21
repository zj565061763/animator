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
 * 节点动画，可以在动画链{@link AnimatorChain}上一起执行，也可以单独执行
 */
public interface NodeAnimator extends SimplePropertyAnimator<NodeAnimator>
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
     * {@link AnimatorChain#nodeWith()}
     *
     * @return
     */
    NodeAnimator nodeWith();

    /**
     * {@link AnimatorChain#nodeWithClone()}
     *
     * @return
     */
    NodeAnimator nodeWithClone();

    /**
     * {@link AnimatorChain#nodeNext()} )}
     *
     * @return
     */
    NodeAnimator nodeNext();

    /**
     * 克隆为指定类型的动节点画
     *
     * @param type {@link Type}
     * @return
     */
    NodeAnimator cloneToType(Type type);

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
