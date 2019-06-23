package com.sd.lib.animator.provider.property;

import android.view.View;

public interface ViewPropertyValue<T>
{
    T getValue(View view);
}
