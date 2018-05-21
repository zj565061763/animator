/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.animator;

import android.view.View;

/**
 * 节点动画
 */
public class SimpleNodeAnimator extends BaseAnimator<NodeAnimator> implements NodeAnimator
{
    private Type mType;
    private AnimatorChain mChain;

    public SimpleNodeAnimator(View target)
    {
        this(Type.Head, null);
        setTarget(target);
    }

    private SimpleNodeAnimator(Type type, AnimatorChain chain)
    {
        mType = type;
        mChain = chain;
    }

    @Override
    public final Type getType()
    {
        return mType;
    }

    @Override
    public void start()
    {
        if (mChain == null)
            super.start();
        else
            throw new UnsupportedOperationException("you must call chain().start() instead, because current animator has been added to the chain");
    }

    @Override
    public final AnimatorChain chain()
    {
        if (mChain == null) mChain = new InternalAnimatorChain(this);
        return mChain;
    }

    @Override
    public NodeAnimator with()
    {
        return chain().appendNode(Type.With, false);
    }

    @Override
    public NodeAnimator withClone()
    {
        return chain().appendNode(Type.With, true);
    }

    @Override
    public NodeAnimator next()
    {
        return chain().appendNode(Type.Next, false);
    }

    @Override
    public final NodeAnimator cloneToType(Type type)
    {
        final SimpleNodeAnimator clone = (SimpleNodeAnimator) super.clone();
        clone.mType = type;
        return clone;
    }

    private static final class InternalAnimatorChain extends BaseAnimatorChain
    {
        public InternalAnimatorChain(SimpleNodeAnimator animator)
        {
            super(animator);
        }

        @Override
        protected NodeAnimator onCreateNodeAnimator(Type type)
        {
            return new SimpleNodeAnimator(type, this);
        }
    }
}
