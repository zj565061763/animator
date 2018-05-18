package com.fanwe.lib.animator.aligner;

import android.view.View;

/**
 * y方向动画view和对齐view中心点对齐
 */
public class YCenterAligner implements Aligner
{
    @Override
    public int align(View animatorView, View alignView, int alignViewPosition)
    {
        final int delta = alignView.getHeight() / 2 - animatorView.getHeight() / 2;
        return alignViewPosition + delta;
    }
}
