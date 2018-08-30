package com.sd.lib.animator.listener.api;

import android.view.View;

import com.sd.lib.animator.listener.RemoveListener;

/**
 * 动画结束移除view
 */
public class OnEndRemoveView extends RemoveListener
{
    public OnEndRemoveView()
    {
        super(Lifecycle.END);
    }

    public OnEndRemoveView(View target)
    {
        super(Lifecycle.END, target);
    }
}
