package com.fanwe.lib.animator;

/**
 * 链条节点动画
 */
public final class NodeAnimator extends BaseAnimator<NodeAnimator>
{
    int mType;
    private final AnimatorChain mChain;

    public NodeAnimator()
    {
        this(false);
    }

    public NodeAnimator(boolean isDebug)
    {
        this(Type.HEAD, new FAnimatorChain(isDebug));
        ((FAnimatorChain) mChain).setCurrent(this);
    }

    NodeAnimator(int type, AnimatorChain chain)
    {
        mType = type;
        mChain = chain;
    }

    /**
     * {@link Type}
     *
     * @return
     */
    public int getType()
    {
        return mType;
    }

    /**
     * 返回动画链条{@link AnimatorChain}
     *
     * @return
     */
    public AnimatorChain chain()
    {
        return mChain;
    }

    /**
     * 节点类型
     */
    public static class Type
    {
        public static final int HEAD = 0;
        public static final int WITH = 1;
        public static final int NEXT = 2;
        public static final int DELAY = 3;
    }
}
