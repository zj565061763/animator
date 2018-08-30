package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.VisibleListener;

/**
 * 动画结束设置view可见
 */
public class OnEndVisible extends VisibleListener
{
    public OnEndVisible()
    {
        super(Lifecycle.END);
    }

    public OnEndVisible(View target)
    {
        super(Lifecycle.END, target);
    }
}
