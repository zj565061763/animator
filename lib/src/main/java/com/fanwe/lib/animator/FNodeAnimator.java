package com.fanwe.lib.animator;

/**
 * 链条节点动画，可以在链条{@link AnimatorChain}上一起执行，也可以单独执行
 */
public class FNodeAnimator extends BaseAnimator<FNodeAnimator>
{
    private int mType;
    private final AnimatorChain mChain;

    public FNodeAnimator()
    {
        this(false);
    }

    public FNodeAnimator(boolean isDebug)
    {
        this(Type.HEAD, new FAnimatorChain(isDebug));
        ((FAnimatorChain) mChain).setCurrent(this);
    }

    FNodeAnimator(int type, AnimatorChain chain)
    {
        setType(type);
        mChain = chain;
    }

    final void setType(int type)
    {
        if (type == Type.HEAD || type == Type.WITH
                || type == Type.NEXT || type == Type.DELAY)
        {
            mType = type;
        } else
        {
            throw new IllegalArgumentException("type must be value of FNodeAnimator.Type.XXX");
        }
    }

    /**
     * {@link Type}
     *
     * @return
     */
    public final int getType()
    {
        return mType;
    }

    /**
     * 返回动画链条{@link AnimatorChain}
     *
     * @return
     */
    public final AnimatorChain chain()
    {
        if (isEmptyProperty() && mType != Type.DELAY)
            throw new UnsupportedOperationException("Can not access AnimatorChain because property is empty");
        return mChain;
    }

    /**
     * 节点类型
     */
    public static final class Type
    {
        public static final int HEAD = 0;
        public static final int WITH = 1;
        public static final int NEXT = 2;
        public static final int DELAY = 3;
    }
}
