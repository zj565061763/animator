package com.sd.lib.animator.provider.property;

import android.os.Build;
import android.view.View;

public abstract class BaseViewPropertyValue<T> implements ViewPropertyValue<T>
{
    @Override
    public T getValue(View view)
    {
        if (!checkView(view))
            return null;

        return getValueImpl(view);
    }

    protected abstract T getValueImpl(View view);

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
