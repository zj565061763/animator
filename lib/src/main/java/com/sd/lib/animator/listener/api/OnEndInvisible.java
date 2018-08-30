package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.InvisibleListener;

/**
 * 动画结束设置view为View.INVISIBLE
 */
public class OnEndInvisible extends InvisibleListener
{
    public OnEndInvisible()
    {
        super(Lifecycle.END);
    }

    public OnEndInvisible(View target)
    {
        super(Lifecycle.END, target);
    }

}
