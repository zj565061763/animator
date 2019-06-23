package com.sd.lib.animator.provider.transform.location;

import android.view.View;

import com.sd.lib.animator.provider.property.location.LocationValue;
import com.sd.lib.animator.provider.transform.BaseValueTransform;

public abstract class LocationValueTransform extends BaseValueTransform<Float>
{
    private final Float mScaleFrom;
    private final Float mScaleTo;

    public LocationValueTransform(Float scaleFrom, Float scaleTo)
    {
        mScaleFrom = scaleFrom;
        mScaleTo = scaleTo;
    }

    @Override
    protected Float getValueImpl(View from, View to)
    {
        final Float fromLocation = getLocationValue(mScaleFrom).getValue(from);
        final Float toLocation = getLocationValue(mScaleTo).getValue(to);
        if (fromLocation == null || toLocation == null)
            return null;

        final float delta = toLocation - fromLocation;
        final float translation = getTranslation(from);
        final float result = delta + translation;

        return result;
    }

    protected abstract LocationValue getLocationValue(Float scale);

    protected abstract float getTranslation(View view);
}
