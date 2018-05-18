package com.fanwe.lib.animator;

class NodeAnimator extends FAnimator
{
    NodeType mNodeType;

    public NodeAnimator(NodeType nodeType)
    {
        mNodeType = nodeType;
    }

    public enum NodeType
    {
        Head, With, Next, Delay
    }
}
