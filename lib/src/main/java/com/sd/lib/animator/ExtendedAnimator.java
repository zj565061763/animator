package com.sd.lib.animator;

import android.view.View;

import com.sd.lib.animator.mtv.MoveToViewConfig;

public interface ExtendedAnimator<T extends ExtendedAnimator> extends PropertyAnimator<T>
{
    /**
     * 移动到屏幕x坐标
     *
     * @param values
     * @return
     */
    T screenX(float... values);

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    T screenY(float... values);

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    T scaleXToView(View... views);

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    T scaleYToView(View... views);

    /**
     * 移动x到View
     *
     * @return
     */
    MoveToViewConfig moveXToView();

    /**
     * 移动y到View
     *
     * @return
     */
    MoveToViewConfig moveYToView();

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
