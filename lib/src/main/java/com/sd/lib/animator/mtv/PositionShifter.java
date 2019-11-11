package com.sd.lib.animator.mtv;

import android.view.View;

/**
 * 位置转移器
 */
public interface PositionShifter
{
    /**
     * 返回偏移量
     *
     * @param params
     * @return
     */
    float shift(Params params);

    class Params
    {
        /**
         * 当前偏移量
         */
        public final float value;

        /**
         * 动画View
         */
        public final View animatorView;
        /**
         * 动画View移动到指定位置后，动画View的缩放值
         */
        public final Float animatorViewFutureScale;

        /**
         * 目标View
         */
        public final View targetView;
        /**
         * 动画View移动到指定位置后，目标View的缩放值
         */
        public final Float targetViewFutureScale;

        /**
         * true-水平方向，false-竖直方向
         */
        public final boolean horizontal;

        public Params(float value, View animatorView, Float animatorViewFutureScale, View targetView, Float targetViewFutureScale, boolean horizontal)
        {
            this.value = value;
            this.animatorView = animatorView;
            this.animatorViewFutureScale = animatorViewFutureScale;
            this.targetView = targetView;
            this.targetViewFutureScale = targetViewFutureScale;
            this.horizontal = horizontal;
        }

        public int getAnimatorViewFutureSize()
        {
            final float scale = animatorViewFutureScale != null ? animatorViewFutureScale :
                    (horizontal ? animatorView.getScaleX() : animatorView.getScaleY());

            int size = horizontal ? animatorView.getWidth() : animatorView.getHeight();

            size = (int) (size * scale);
            return size;
        }

        public int getTargetViewFutureSize()
        {
            final float scale = targetViewFutureScale != null ? targetViewFutureScale :
                    (horizontal ? targetView.getScaleX() : targetView.getScaleY());

            int size = horizontal ? targetView.getWidth() : targetView.getHeight();

            size = (int) (size * scale);
            return size;
        }
    }
}
