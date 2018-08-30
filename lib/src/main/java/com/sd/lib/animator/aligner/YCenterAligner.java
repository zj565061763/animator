package com.sd.lib.animator.aligner;

import android.view.View;

import com.sd.lib.animator.ExtendedPropertyAnimator;

/**
 * y方向动画view和对齐view中心点对齐
 */
public class YCenterAligner implements ExtendedPropertyAnimator.Aligner
{
    @Override
    public int align(View animatorView, View alignView, int alignViewPosition)
    {
        final int delta = alignView.getHeight() / 2 - animatorView.getHeight() / 2;
        return alignViewPosition + delta;
    }
}
