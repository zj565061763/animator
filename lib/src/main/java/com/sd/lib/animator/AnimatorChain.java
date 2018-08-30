package com.sd.lib.animator;

import android.animation.AnimatorSet;

/**
 * 动画链
 */
public interface AnimatorChain extends Animator<AnimatorChain>
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
     * 设置是否调试模式
     *
     * @param debug true-调试模式，会输出整个动画链的结构，方便开发调试
     * @return
     */
    AnimatorChain setDebug(boolean debug);
}
