package com.sd.lib.animator;

import com.sd.lib.animator.mtv.MoveToViewConfig;

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

    MoveToViewConfig configMoveXToView();

    MoveToViewConfig configMoveYToView();

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
