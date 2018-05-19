package com.fanwe.lib.animator;

import android.view.View;

/**
 * 动画链
 */
public interface AnimatorChain
{
    /**
     * 生成一个新动画和当前动画同时执行
     *
     * @return
     */
    NodeAnimator with();

    /**
     * 生成一个新动画和当前动画同时执行
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return
     */
    NodeAnimator with(View target);

    /**
     * 在{@link #with()}方法的基础上会保留当前动画的参数设置
     *
     * @return
     */
    NodeAnimator withClone();

    /**
     * 生成一个新动画在当前动画执行完成后执行
     *
     * @return
     */
    NodeAnimator next();

    /**
     * 生成一个新动画在当前动画执行完成后执行
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return
     */
    NodeAnimator next(View target);

    /**
     * 生成一个延迟动画在当前动画执行完成后执行
     *
     * @param time 延迟多少毫秒
     * @return
     */
    NodeAnimator delay(long time);

    /**
     * 转为{@link android.animation.AnimatorSet}
     *
     * @return
     */
    android.animation.AnimatorSet toAnimatorSet();

    /**
     * 开始动画
     */
    void start();

    /**
     * 对target截图然后设置给ImageView，让ImageView镜像在android.R.id.content的FrameLayout里面执行动画
     */
    void startAsPop();
}
