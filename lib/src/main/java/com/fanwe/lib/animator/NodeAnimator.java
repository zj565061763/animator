package com.fanwe.lib.animator;

/**
 * 链条节点动画，可以在链条{@link AnimatorChain}上一起执行，也可以单独执行
 */
public interface NodeAnimator extends SimplePropertyAnimator<NodeAnimator>
{
    /**
     * 返回节点类型{@link Type}
     *
     * @return
     */
    int getType();

    /**
     * 返回动画链条{@link AnimatorChain}
     *
     * @return
     */
    AnimatorChain chain();

    /**
     * 节点类型
     */
    final class Type
    {
        public static final int HEAD = 0;
        public static final int WITH = 1;
        public static final int NEXT = 2;
        public static final int DELAY = 3;
    }
}
