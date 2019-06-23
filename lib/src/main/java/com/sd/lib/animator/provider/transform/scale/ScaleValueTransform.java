package com.sd.lib.animator.provider.transform.scale;

import android.view.View;

import com.sd.lib.animator.provider.transform.BaseValueTransform;

public abstract class ScaleValueTransform extends BaseValueTransform<Float>
{
    @Override
    protected Float getValueImpl(View from, View to)
    {
        final float fromScale = getScale(from);

        final float fromSize = getSize(from) * fromScale;
        final float toSize = getSize(to) * getScale(to);

        return toSize / fromSize * fromScale;
    }

    protected abstract int getSize(View view);

    protected abstract float getScale(View view);
}
