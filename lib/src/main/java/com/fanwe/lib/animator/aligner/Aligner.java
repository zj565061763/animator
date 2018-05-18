package com.fanwe.lib.animator.aligner;

import android.view.View;

public interface Aligner
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
