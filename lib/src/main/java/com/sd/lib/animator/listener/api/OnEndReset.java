package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.ResetListener;

/**
 * 动画结束重置view
 */
public class OnEndReset extends ResetListener
{
    public OnEndReset()
    {
        super(Lifecycle.END);
    }

    public OnEndReset(View target)
    {
        super(Lifecycle.END, target);
    }
}
