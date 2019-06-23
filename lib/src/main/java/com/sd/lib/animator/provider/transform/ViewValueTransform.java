package com.sd.lib.animator.provider.transform;

import android.view.View;

public interface ViewValueTransform<T>
{
    T getValue(View form, View to);
}
