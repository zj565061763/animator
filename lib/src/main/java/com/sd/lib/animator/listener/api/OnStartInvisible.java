package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.InvisibleListener;

/**
 * 动画开始设置view为View.INVISIBLE
 */
public class OnStartInvisible extends InvisibleListener
{
    public OnStartInvisible()
    {
        super(Lifecycle.START);
    }

    public OnStartInvisible(View target)
    {
        super(Lifecycle.START, target);
    }
}
