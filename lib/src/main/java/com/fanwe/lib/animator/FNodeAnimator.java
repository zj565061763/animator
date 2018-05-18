package com.fanwe.lib.animator;

import android.view.View;

public class FNodeAnimator extends FAnimator
{
    private final NodeType mNodeType;

    FNodeAnimator(View target, NodeType nodeType)
    {
        super(target);
        mNodeType = nodeType;
    }

    public final NodeType getNodeType()
    {
        return mNodeType;
    }

    public enum NodeType
    {
        Head, With, Next, Delay
    }
}
