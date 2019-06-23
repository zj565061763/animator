package com.sd.lib.animator.provider.transform.location;

import android.view.View;

import com.sd.lib.animator.provider.property.location.LocationValue;
import com.sd.lib.animator.provider.property.location.ScreenXValue;

public class ScreenXTransform extends LocationValueTransform
{
    public ScreenXTransform(Float scaleFrom, Float scaleTo)
    {
        super(scaleFrom, scaleTo);
    }

    @Override
    protected LocationValue getLocationValue(Float scale)
    {
        return new ScreenXValue(scale);
    }

    @Override
    protected float getTranslation(View view)
    {
        return view.getTranslationX();
    }
}
