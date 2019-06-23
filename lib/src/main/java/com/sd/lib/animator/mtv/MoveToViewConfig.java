package com.sd.lib.animator.mtv;

import android.view.View;

import com.sd.lib.animator.NodeAnimator;

public interface MoveToViewConfig
{
    View getView();

    int getDelta();

    float getFutureScale();

    /**
     * 移动到某个View
     *
     * @param view
     * @return
     */
    MoveToViewConfig newView(View view);

    /**
     * 设置偏移量
     *
     * @param delta
     * @return
     */
    MoveToViewConfig setDelta(int delta);

    /**
     * 设置移动后的缩放值
     *
     * @param scale
     * @return
     */
    MoveToViewConfig setFutureScale(float scale);

    /**
     * 设置移动后的缩放值，由当前动画view和目标view计算出缩放值
     *
     * @param view
     * @return
     */
    MoveToViewConfig setFutureScale(View view);

    /**
     * 返回config所在的节点动画对象
     *
     * @return
     */
    NodeAnimator node();
}
