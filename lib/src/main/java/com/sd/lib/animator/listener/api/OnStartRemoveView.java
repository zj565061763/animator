package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.RemoveListener;

/**
 * 动画开始移除view
 */
public class OnStartRemoveView extends RemoveListener
{
    public OnStartRemoveView()
    {
        super(Lifecycle.START);
    }

    public OnStartRemoveView(View target)
    {
        super(Lifecycle.START, target);
    }
}
