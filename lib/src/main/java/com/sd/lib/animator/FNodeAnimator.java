package com.sd.lib.animator;

import android.view.View;

import com.sd.lib.animator.listener.api.OnStartVisible;

/**
 * 节点动画
 */
public class FNodeAnimator extends BaseExtendedAnimator<NodeAnimator> implements NodeAnimator
{
    private Type mType;
    private AnimatorChain mChain;

    public FNodeAnimator(View target)
    {
        this(Type.Head, null);
        setTarget(target);
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
    public NodeAnimator start()
    {
        if (mChain == null)
            return super.start();
        else
            throw new UnsupportedOperationException("you must call chain().start() instead, because current animator has been added to the chain");
    }

    @Override
    public NodeAnimator startAsPop(boolean clone)
    {
        if (mChain == null)
            return super.startAsPop(clone);
        else
            throw new UnsupportedOperationException("you must call chain().startAsPop(boolean) instead, because current animator has been added to the chain");
    }

    @Override
    public final AnimatorChain chain()
    {
        if (mChain == null)
            mChain = new SimpleAnimatorChain(this);
        return mChain;
    }

    @Override
    public NodeAnimator with()
    {
        final AnimatorChain chain = chain();
        return chain.appendNode(new FNodeAnimator(Type.With, chain));
    }

    @Override
    public NodeAnimator withClone()
    {
        final AnimatorChain chain = chain();

        final FNodeAnimator clone = (FNodeAnimator) super.clone();
        clone.mType = Type.With;
        clone.mChain = chain;
        clone.clearListener();
        clone.addListener(new OnStartVisible());

        return chain.appendNode(clone);
    }

    @Override
    public NodeAnimator next()
    {
        final AnimatorChain chain = chain();
        return chain.appendNode(new FNodeAnimator(Type.Next, chain));
    }
}
