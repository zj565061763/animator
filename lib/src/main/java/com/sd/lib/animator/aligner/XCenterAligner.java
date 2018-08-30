package com.sd.lib.animator.aligner;

import android.view.View;

import com.sd.lib.animator.ExtendedPropertyAnimator;

/**
 * x方向动画view和对齐view中心点对齐
 */
public class XCenterAligner implements ExtendedPropertyAnimator.Aligner
{
    @Override
    public int align(View animatorView, View alignView, int alignViewPosition)
    {
        final int delta = alignView.getWidth() / 2 - animatorView.getWidth() / 2;
        return alignViewPosition + delta;
    }
}
