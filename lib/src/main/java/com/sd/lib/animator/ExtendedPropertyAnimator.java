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
    T moveToX(float... values);

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    T moveToY(float... values);

    /**
     * 移动到views的屏幕x坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    T moveToX(Aligner aligner, View... views);

    /**
     * 移动到views的屏幕y坐标
     *
     * @param aligner
     * @param views
     * @return
     */
    T moveToY(Aligner aligner, View... views);

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    T scaleX(View... views);

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    T scaleY(View... views);

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

    interface Aligner
    {
        /**
         * @param animatorView      动画view
         * @param alignView         动画view想要对齐的view
         * @param alignViewPosition 动画view想要对齐的view的x或者y（屏幕坐标）
         * @return
         */
        int align(View animatorView, View alignView, int alignViewPosition);

        Aligner DEFAULT = new Aligner()
        {
            @Override
            public int align(View animatorView, View alignView, int alignViewPosition)
            {
                return alignViewPosition;
            }
        };
    }
}
