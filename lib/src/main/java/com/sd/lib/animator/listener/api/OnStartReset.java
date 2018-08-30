package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.ResetListener;

/**
 * 动画开始重置view
 */
public class OnStartReset extends ResetListener
{
    public OnStartReset()
    {
        super(Lifecycle.START);
    }

    public OnStartReset(View target)
    {
        super(Lifecycle.START, target);
    }
}
