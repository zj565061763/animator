package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.GoneListener;

/**
 * 动画结束设置view为View.GONE
 */
public class OnEndGone extends GoneListener
{
    public OnEndGone()
    {
        super(Lifecycle.END);
    }

    public OnEndGone(View target)
    {
        super(Lifecycle.END, target);
    }
}
