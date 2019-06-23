package com.sd.lib.animator.provider.property.location;

import android.view.View;

import com.sd.lib.animator.provider.property.BaseViewPropertyValue;

public abstract class LocationValue extends BaseViewPropertyValue<Float>
{
    private final Float mScale;

    /**
     * 如果传入的scale不为null，则用传入的scale计算View的位置
     *
     * @param scale
     */
    public LocationValue(Float scale)
    {
        mScale = scale;
    }

    @Override
    protected Float getValueImpl(View view)
    {
        final int location = getLocation(view);
        final float realScale = getScale(view);
        final float realSize = getSize(view);

        float pivot = getPivot(view);
        if (pivot > 1.0f)
            pivot = pivot / realSize;

        final float scale = mScale != null ? mScale : realScale;

        final float deltaScale = realScale - scale;
        final float delta = deltaScale * realSize * pivot;

        return location + delta;
    }

    protected abstract int getLocation(View view);

    protected abstract int getSize(View view);

    protected abstract float getPivot(View view);

    protected abstract float getScale(View view);
}
