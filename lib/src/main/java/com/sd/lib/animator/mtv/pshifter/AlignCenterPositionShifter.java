package com.sd.lib.animator.mtv.pshifter;

import com.sd.lib.animator.mtv.PositionShifter;

/**
 * 动画View和目标View中心对齐
 */
public class AlignCenterPositionShifter implements PositionShifter
{
    @Override
    public float shift(Params params)
    {
        final int animatorSize = params.getAnimatorViewFutureSize();
        final int targetSize = params.getTargetViewFutureSize();

        final int shift = (targetSize - animatorSize) / 2;
        return shift;
    }
}
