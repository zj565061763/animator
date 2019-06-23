package com.sd.lib.animator.provider.property.location;

import android.view.View;

public class ScreenYValue extends LocationValue
{
    public ScreenYValue(Float scale)
    {
        super(scale);
    }

    @Override
    protected int getLocation(View view)
    {
        final int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    @Override
    protected int getSize(View view)
    {
        return view.getHeight();
    }

    @Override
    protected float getPivot(View view)
    {
        return view.getPivotY();
    }

    @Override
    protected float getScale(View view)
    {
        return view.getScaleY();
    }
}
