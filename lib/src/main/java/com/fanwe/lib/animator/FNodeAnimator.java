package com.fanwe.lib.animator;

class FNodeAnimator extends FAnimator
{
    private final NodeType mNodeType;

    public FNodeAnimator(NodeType nodeType)
    {
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
