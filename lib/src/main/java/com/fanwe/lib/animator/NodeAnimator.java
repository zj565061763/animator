package com.fanwe.lib.animator;

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

    public int getType()
    {
        return mType;
    }

    public AnimatorChain chain()
    {
        return mChain;
    }

    public static class Type
    {
        public static final int HEAD = 0;
        public static final int WITH = 1;
        public static final int NEXT = 2;
        public static final int DELAY = 3;
    }
}
