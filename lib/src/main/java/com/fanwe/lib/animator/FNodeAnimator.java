package com.fanwe.lib.animator;

/**
 * 节点动画
 */
public class FNodeAnimator extends BaseAnimator<NodeAnimator> implements NodeAnimator
{
    private Type mType;
    private AnimatorChain mChain;

    private boolean mIsDebug;

    public FNodeAnimator()
    {
        this(false);
    }

    public FNodeAnimator(boolean isDebug)
    {
        this(Type.Head, null);
        mIsDebug = isDebug;
    }

    private FNodeAnimator(Type type, AnimatorChain chain)
    {
        mType = type;
        mChain = chain;
    }

    @Override
    public final Type getType()
    {
        return mType;
    }

    @Override
    public final AnimatorChain chain()
    {
        if (mChain == null) mChain = new InternalAnimatorChain(mIsDebug, this);
        return mChain;
    }

    @Override
    public NodeAnimator nodeWith()
    {
        return chain().nodeWith();
    }

    @Override
    public NodeAnimator nodeWithClone()
    {
        return chain().nodeWithClone();
    }

    @Override
    public NodeAnimator nodeNext()
    {
        return chain().nodeNext();
    }

    @Override
    public final NodeAnimator cloneToType(Type type)
    {
        final FNodeAnimator clone = (FNodeAnimator) super.clone();
        clone.mType = type;
        return clone;
    }

    private final static class InternalAnimatorChain extends BaseAnimatorChain
    {
        public InternalAnimatorChain(boolean isDebug, FNodeAnimator animator)
        {
            super(isDebug, animator);
        }

        @Override
        protected NodeAnimator onCreateNodeAnimator(Type type)
        {
            return new FNodeAnimator(type, this);
        }
    }
}
