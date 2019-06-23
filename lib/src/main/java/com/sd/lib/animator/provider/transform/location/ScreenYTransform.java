package com.sd.lib.animator.provider.transform.location;

import android.view.View;

import com.sd.lib.animator.provider.property.location.LocationValue;
import com.sd.lib.animator.provider.property.location.ScreenYValue;

public class ScreenYTransform extends LocationValueTransform
{
    public ScreenYTransform(Float scaleFrom, Float scaleTo)
    {
        super(scaleFrom, scaleTo);
    }

    @Override
    protected LocationValue getLocationValue(Float scale)
    {
        return new ScreenYValue(scale);
    }

    @Override
    protected float getTranslation(View view)
    {
        return view.getTranslationY();
    }
}
