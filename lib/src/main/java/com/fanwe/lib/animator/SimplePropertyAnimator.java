package com.fanwe.lib.animator;

import android.view.View;

import com.fanwe.lib.animator.aligner.Aligner;

public interface SimplePropertyAnimator extends PropertyAnimator
{
    /**
     * 移动到屏幕x坐标
     *
     * @param values
     * @return
     */
    SimplePropertyAnimator moveToX(float... values);

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    SimplePropertyAnimator moveToY(float... values);

    /**
     * 移动到views的屏幕x坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    SimplePropertyAnimator moveToX(Aligner aligner, View... views);

    /**
     * 移动到views的屏幕y坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    SimplePropertyAnimator moveToY(Aligner aligner, View... views);

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    SimplePropertyAnimator scaleX(View... views);

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    SimplePropertyAnimator scaleY(View... views);

    /**
     * 设置tag
     *
     * @param tag
     * @return
     */
    SimplePropertyAnimator setTag(String tag);

    /**
     * 返回设置的tag{@link #setTag(String)}
     *
     * @return
     */
    String getTag();
}
