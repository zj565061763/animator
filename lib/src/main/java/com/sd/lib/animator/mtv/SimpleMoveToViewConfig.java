package com.sd.lib.animator.mtv;

import android.view.View;

import com.sd.lib.animator.NodeAnimator;
import com.sd.lib.animator.provider.transform.scale.ScaleValueTransform;
import com.sd.lib.animator.provider.transform.scale.ScaleXTransform;
import com.sd.lib.animator.provider.transform.scale.ScaleYTransform;

import java.util.List;

public class SimpleMoveToViewConfig implements MoveToViewConfig
{
    private final boolean mHorizontal;
    private final NodeAnimator mNodeAnimator;
    private final List<MoveToViewConfig> mListConfig;

    private View mView;
    private int mDelta;
    private Float mFutureScale;
    private Float mTargetFutureScale;

    public SimpleMoveToViewConfig(boolean horizontal, NodeAnimator nodeAnimator, List<MoveToViewConfig> listConfig)
    {
        if (nodeAnimator == null)
            throw new IllegalArgumentException("nodeAnimator is null");

        if (listConfig == null)
            throw new IllegalArgumentException("listConfig is null");

        mHorizontal = horizontal;
        mNodeAnimator = nodeAnimator;
        mListConfig = listConfig;
    }

    @Override
    public View getView()
    {
        return mView;
    }

    @Override
    public int getDelta()
    {
        return mDelta;
    }

    @Override
    public Float getFutureScale()
    {
        return mFutureScale;
    }

    public Float getTargetFutureScale()
    {
        return mTargetFutureScale;
    }

    @Override
    public MoveToViewConfig newTarget(View view)
    {
        if (view == null)
            throw new IllegalArgumentException("view is null");

        if (mListConfig.isEmpty())
        {
            mView = view;
            mListConfig.add(this);
            return this;
        } else
        {
            final SimpleMoveToViewConfig config = new SimpleMoveToViewConfig(mHorizontal, mNodeAnimator, mListConfig);
            config.mView = view;
            mListConfig.add(config);
            return config;
        }
    }

    @Override
    public MoveToViewConfig setDelta(int delta)
    {
        mDelta = delta;
        return this;
    }

    @Override
    public MoveToViewConfig setFutureScale(Float scale)
    {
        mFutureScale = scale;
        return this;
    }

    @Override
    public MoveToViewConfig setFutureScale(View view)
    {
        if (view == null)
            throw new IllegalArgumentException("view is null");

        final ScaleValueTransform transform = mHorizontal ? new ScaleXTransform() : new ScaleYTransform();
        final Float scale = transform.getValue(node().getTarget(), view);
        setFutureScale(scale);
        return this;
    }

    @Override
    public MoveToViewConfig setTargetFutureScale(Float scale)
    {
        mTargetFutureScale = scale;
        return this;
    }

    @Override
    public MoveToViewConfig setTargetFutureScale(View view)
    {
        if (view == null)
            throw new IllegalArgumentException("view is null");

        final ScaleValueTransform transform = mHorizontal ? new ScaleXTransform() : new ScaleYTransform();
        final Float scale = transform.getValue(getView(), view);

        setTargetFutureScale(scale);
        return this;
    }

    @Override
    public NodeAnimator node()
    {
        return mNodeAnimator;
    }
}
