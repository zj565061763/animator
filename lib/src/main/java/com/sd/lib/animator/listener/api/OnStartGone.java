package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.GoneListener;

/**
 * 动画开始设置view为View.GONE
 */
public class OnStartGone extends GoneListener
{
    public OnStartGone()
    {
        super(Lifecycle.START);
    }

    public OnStartGone(View target)
    {
        super(Lifecycle.START, target);
    }
}
