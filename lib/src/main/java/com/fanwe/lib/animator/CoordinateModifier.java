package com.fanwe.lib.animator;

import android.view.View;

public interface CoordinateModifier
{
    int modify(View target, View view, int viewPosition);

    CoordinateModifier DEFAULT = new CoordinateModifier()
    {
        @Override
        public int modify(View target, View view, int viewPosition)
        {
            return viewPosition;
        }
    };

    CoordinateModifier ALIGN_X_CENTER = new CoordinateModifier()
    {
        @Override
        public int modify(View target, View view, int viewPosition)
        {
            final int delta = view.getWidth() / 2 - target.getWidth() / 2;
            return viewPosition + delta;
        }
    };
}
