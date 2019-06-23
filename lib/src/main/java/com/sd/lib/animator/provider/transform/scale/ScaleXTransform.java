package com.sd.lib.animator.provider.transform.scale;

import android.view.View;

public class ScaleXTransform extends ScaleValueTransform
{
    @Override
    protected int getSize(View view)
    {
        return view.getWidth();
    }

    @Override
    protected float getScale(View view)
    {
        return view.getScaleX();
    }
}
