package com.sd.lib.animator.mtv;

import android.view.View;

import com.sd.lib.animator.NodeAnimator;

public interface MoveToViewConfig
{
    /**
     * 目标view
     *
     * @return
     */
    View getTarget();

    /**
     * 偏移量
     *
     * @return
     */
    int getDelta();

    /**
     * 移动到目标view后，动画view的缩放值
     *
     * @return
     */
    Float getFutureScale();

    /**
     * 移动到目标view后，目标view的缩放值
     *
     * @return
     */
    Float getTargetFutureScale();

    /**
     * {@link PositionShifter}
     *
     * @return
     */
    PositionShifter getPositionShifter();

    /**
     * 移动到目标view
     *
     * @param view
     * @return
     */
    MoveToViewConfig newTarget(View view);

    /**
     * 设置偏移量
     *
     * @param delta
     * @return
     */
    MoveToViewConfig setDelta(int delta);

    /**
     * 设置移动到目标view后，动画view的缩放值
     * <p>
     * 如果移动到目标view后动画view有缩放值的话，应该设置缩放值，才能计算正确的移动位置
     *
     * @param scale
     * @return
     */
    MoveToViewConfig setFutureScale(Float scale);

    /**
     * 缩放值由动画view和参数view计算出
     * <p>
     * {@link #setFutureScale(Float)}
     *
     * @param view
     * @return
     */
    MoveToViewConfig setFutureScale(View view);

    /**
     * 设置移动到目标view后，目标view的缩放值
     * <p>
     * 如果移动到目标view后目标view有缩放值的话，应该设置缩放值，才能计算正确的移动位置
     *
     * @param scale
     * @return
     */
    MoveToViewConfig setTargetFutureScale(Float scale);

    /**
     * 缩放值由目标view和参数view计算出
     * <p>
     * {@link #setTargetFutureScale(Float)}
     *
     * @param view
     * @return
     */
    MoveToViewConfig setTargetFutureScale(View view);

    /**
     * 设置位置转移器
     *
     * @param shifter
     * @return
     */
    MoveToViewConfig setPositionShifter(PositionShifter shifter);

    /**
     * 返回config所在的节点动画对象
     *
     * @return
     */
    NodeAnimator node();
}
