package com.sd.lib.animator.provider.transform;

import android.os.Build;
import android.view.View;

public abstract class BaseValueTransform<T> implements ViewValueTransform<T>
{
    @Override
    public T getValue(View from, View to)
    {
        if (!checkView(from))
            return null;

        if (!checkView(to))
            return null;

        return getValueImpl(from, to);
    }

    protected abstract T getValueImpl(View from, View to);

    private static boolean checkView(View view)
    {
        if (view == null)
            return false;

        if (view.getVisibility() == View.GONE)
            return false;

        final boolean isAttached = Build.VERSION.SDK_INT >= 19 ? view.isAttachedToWindow() : view.getWindowToken() != null;
        if (!isAttached)
            return false;

        return true;
    }
}
