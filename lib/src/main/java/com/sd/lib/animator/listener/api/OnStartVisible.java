package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.VisibleListener;

/**
 * 动画开始设置view可见
 */
public class OnStartVisible extends VisibleListener
{
    public OnStartVisible()
    {
        super(Lifecycle.START);
    }

    public OnStartVisible(View target)
    {
        super(Lifecycle.START, target);
    }
}
