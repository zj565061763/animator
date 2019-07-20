package com.sd.lib.animator;

import android.view.View;

public interface ExtendedPropertyAnimator<T extends ExtendedPropertyAnimator> extends PropertyAnimator<T>
{
    /**
     * 移动到屏幕x坐标
     *
     * @param values
     * @return
     */
    T moveX(float... values);

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    T moveY(float... values);

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    T scaleToViewX(View... views);

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    T scaleToViewY(View... views);

    /**
     * 设置描述
     *
     * @param desc
     * @return
     */
    T setDesc(String desc);

    /**
     * 返回设置的描述{@link #setDesc(String)}
     *
     * @return
     */
    String getDesc();
}
