package com.sd.lib.animator.provider.property.location;

import android.view.View;

public class ScreenXValue extends LocationValue
{
    public ScreenXValue(Float scale)
    {
        super(scale);
    }

    @Override
    protected int getLocation(View view)
    {
        final int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    @Override
    protected int getSize(View view)
    {
        return view.getWidth();
    }

    @Override
    protected float getPivot(View view)
    {
        return view.getPivotX();
    }

    @Override
    protected float getScale(View view)
    {
        return view.getScaleX();
    }
}
