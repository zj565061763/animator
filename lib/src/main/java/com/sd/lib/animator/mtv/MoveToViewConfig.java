package com.sd.lib.animator.mtv;

import android.view.View;

import com.sd.lib.animator.NodeAnimator;

public interface MoveToViewConfig
{
    View getView();

    int getDelta();

    float getFutureScale();

    /**
     * 移动到某个view
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
     * <p>
     * 如果移动到目的地后当前动画view有缩放值的话，应该设置缩放值，才能计算正确的移动位置
     *
     * @param scale
     * @return
     */
    MoveToViewConfig setFutureScale(float scale);

    /**
     * 缩放值由当前动画view和目标view计算出
     * <p>
     * {@link #setFutureScale(float)}
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
