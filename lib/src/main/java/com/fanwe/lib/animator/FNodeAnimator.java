package com.fanwe.lib.animator;

/**
 * 节点动画
 */
public class FNodeAnimator extends BaseAnimator<NodeAnimator> implements NodeAnimator
{
    private int mType;
    private AnimatorChain mChain;

    private boolean mIsDebug;

    public FNodeAnimator()
    {
        this(false);
    }

    public FNodeAnimator(boolean isDebug)
    {
        this(Type.HEAD, null);
        mIsDebug = isDebug;
    }

    private FNodeAnimator(int type, AnimatorChain chain)
    {
        setType(type);
        mChain = chain;
    }

    private void setType(int type)
    {
        if (type == Type.HEAD || type == Type.WITH || type == Type.NEXT)
        {
            mType = type;
        } else
        {
            throw new IllegalArgumentException("type must be value of NodeAnimator.Type.XXX");
        }
    }

    @Override
    public final int getType()
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
    public final NodeAnimator cloneToType(int type)
    {
        final FNodeAnimator clone = (FNodeAnimator) super.clone();
        clone.setType(type);
        return clone;
    }

    private final static class InternalAnimatorChain extends BaseAnimatorChain
    {
        public InternalAnimatorChain(boolean isDebug, FNodeAnimator animator)
        {
            super(isDebug, animator);
        }

        @Override
        protected NodeAnimator onCreateNodeAnimator(int type)
        {
            return new FNodeAnimator(type, this);
        }
    }
}
