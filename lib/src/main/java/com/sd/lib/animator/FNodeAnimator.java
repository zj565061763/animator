package com.sd.lib.animator;

import android.view.View;

import com.sd.lib.animator.listener.api.OnStartVisible;
import com.sd.lib.animator.mtv.MoveToViewConfig;
import com.sd.lib.animator.mtv.SimpleMoveToViewConfig;
import com.sd.lib.animator.provider.transform.location.LocationValueTransform;
import com.sd.lib.animator.provider.transform.location.ScreenXTransform;
import com.sd.lib.animator.provider.transform.location.ScreenYTransform;

import java.util.ArrayList;
import java.util.List;

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
    public final AnimatorChain chain()
    {
        if (mChain == null)
            mChain = new SimpleAnimatorChain(this);

        checkMoveToViewConfig();
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

    @Override
    public NodeAnimator start()
    {
        checkMoveToViewConfig();
        if (mChain == null)
            return super.start();
        else
            throw new UnsupportedOperationException("you must call chain().start() instead, because current animator has been added to the chain");
    }

    @Override
    public NodeAnimator startAsPop(boolean clone)
    {
        checkMoveToViewConfig();
        if (mChain == null)
            return super.startAsPop(clone);
        else
            throw new UnsupportedOperationException("you must call chain().startAsPop(boolean) instead, because current animator has been added to the chain");
    }

    private boolean mMoveHorizontal;
    private List<MoveToViewConfig> mListMoveConfig;

    @Override
    public MoveToViewConfig configMoveToViewX()
    {
        mMoveHorizontal = true;
        mListMoveConfig = new ArrayList<>();
        return new SimpleMoveToViewConfig(true, this, mListMoveConfig);
    }

    @Override
    public MoveToViewConfig configMoveToViewY()
    {
        mMoveHorizontal = false;
        mListMoveConfig = new ArrayList<>();
        return new SimpleMoveToViewConfig(false, this, mListMoveConfig);
    }

    private void checkMoveToViewConfig()
    {
        if (mListMoveConfig == null || mListMoveConfig.isEmpty())
            return;

        final boolean horizontal = mMoveHorizontal;
        final List<MoveToViewConfig> listConfig = mListMoveConfig;

        final List<Float> list = new ArrayList<>(listConfig.size());
        for (MoveToViewConfig item : listConfig)
        {
            final LocationValueTransform transform = horizontal
                    ? new ScreenXTransform(item.getFutureScale(), null)
                    : new ScreenYTransform(item.getFutureScale(), null);

            final Float value = transform.getValue(getTarget(), item.getView());
            if (value != null)
                list.add(value + item.getDelta());
        }

        final float[] values = listToValue(list);
        if (values == null)
            return;

        if (horizontal)
            translationX(values);
        else
            translationY(values);

        mListMoveConfig = null;
    }
}
