package com.sd.lib.animator.provider.transform.scale;

import android.view.View;

public class ScaleYTransform extends ScaleValueTransform
{
    @Override
    protected int getSize(View view)
    {
        return view.getHeight();
    }

    @Override
    protected float getScale(View view)
    {
        return view.getScaleY();
    }
}
