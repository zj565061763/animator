package com.fanwe.lib.animator;

public final class NodeAnimator extends BaseAnimator<NodeAnimator>
{
    int mType;
    private final AnimatorSet mAnimatorSet;

    NodeAnimator(int type, AnimatorSet animatorSet)
    {
        mType = type;
        mAnimatorSet = animatorSet;
    }

    public int getType()
    {
        return mType;
    }

    public AnimatorSet set()
    {
        return mAnimatorSet;
    }

    public static class Type
    {
        public static final int HEAD = 0;
        public static final int WITH = 1;
        public static final int NEXT = 2;
        public static final int DELAY = 3;
    }
}
