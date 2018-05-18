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

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 对AnimatorSet进行封装提供一系列可以同时或者连续执行的动画集合
 */
public class FAnimatorSet extends FAnimator
{
    private final AnimatorSet mAnimatorSet = new AnimatorSet();
    private NodeAnimator mCurrent;

    private final boolean mIsDebug;
    private List<NodeAnimator> mListNode;

    public FAnimatorSet()
    {
        this(false);
    }

    public FAnimatorSet(boolean isDebug)
    {
        mIsDebug = isDebug;
        mAnimatorSet.play(getCurrent().get());
    }

    private NodeAnimator getCurrent()
    {
        if (mCurrent == null)
        {
            setCurrent(new NodeAnimator(NodeAnimator.NodeType.Head));
        }
        return mCurrent;
    }

    private void setCurrent(NodeAnimator current)
    {
        if (current == null) throw new NullPointerException("current is null");
        mCurrent = current;

        if (mIsDebug)
        {
            if (mListNode == null) mListNode = new ArrayList<>();
            mListNode.add(current);
        }
    }

    /**
     * 返回和当前动画共同执行的新动画
     *
     * @return 新动画
     */
    public FAnimatorSet with()
    {
        return with(null);
    }

    /**
     * 返回和当前动画共同执行的新动画
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return 新动画
     */
    public FAnimatorSet with(View target)
    {
        final NodeAnimator animator = new NodeAnimator(NodeAnimator.NodeType.With);
        animator.setTarget(target);
        return withInternal(animator);
    }

    /**
     * 在{@link #with()}方法的基础上会保留当前动画的参数设置
     *
     * @return
     */
    public FAnimatorSet withClone()
    {
        NodeAnimator clone = (NodeAnimator) getCurrent().clone();
        clone.mNodeType = NodeAnimator.NodeType.With;
        withInternal(clone);
        return this;
    }

    private FAnimatorSet withInternal(NodeAnimator animator)
    {
        initNodeAnim(animator);
        getSet().play(getCurrent().get()).with(animator.get());
        setCurrent(animator);
        return this;
    }

    /**
     * 返回在当前动画后面执行的新动画
     *
     * @return 新动画
     */
    public FAnimatorSet next()
    {
        return next(null);
    }

    /**
     * 返回在当前动画后面执行的新动画
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return 新动画
     */
    public FAnimatorSet next(View target)
    {
        final NodeAnimator animator = new NodeAnimator(NodeAnimator.NodeType.Next);
        animator.setTarget(target);
        return nextInternal(animator);
    }

    private FAnimatorSet nextInternal(NodeAnimator animator)
    {
        initNodeAnim(animator);
        getSet().play(animator.get()).after(getCurrent().get());
        setCurrent(animator);
        return this;
    }

    private void initNodeAnim(FAnimator anim)
    {
        final View target = anim.getTarget();
        if (target == null)
        {
            anim.setTarget(getCurrent().getTarget());
        }
    }

    /**
     * 返回延迟动画，返回的对象不应该再进行任何可以改变动画行为的参数设置
     *
     * @param time 延迟多少毫秒
     * @return 延迟动画
     */
    public FAnimatorSet delay(long time)
    {
        final NodeAnimator animator = new NodeAnimator(NodeAnimator.NodeType.Delay);
        animator.setDuration(time);
        nextInternal(animator);
        return this;
    }

    /**
     * 获得内部封装的AnimatorSet对象
     *
     * @return
     */
    public AnimatorSet getSet()
    {
        return mAnimatorSet;
    }

    //----------extend start----------

    /**
     * 对target截图然后设置给ImageView，让ImageView镜像在android.R.id.content的FrameLayout里面执行动画
     */
    public void startAsPop()
    {
        final AnimatorSet animatorSet = getSet();
        final ArrayList<Animator> listChild = animatorSet.getChildAnimations();
        if (listChild == null || listChild.isEmpty())
        {
            return;
        }
        final HashMap<View, ImageView> mapCache = new HashMap<>();
        for (Animator animator : listChild)
        {
            final View target = (View) ((ObjectAnimator) animator).getTarget();
            if (target == null)
            {
                continue;
            }

            final ImageView cache = mapCache.get(target);
            if (cache == null)
            {
                final Context context = target.getContext();
                if (context instanceof Activity)
                {
                    PopImageView imageView = new PopImageView(context);
                    imageView.setDrawingCacheView(target);
                    imageView.attachTarget(target);

                    animator.setTarget(imageView);
                    mapCache.put(target, imageView);
                }
            } else
            {
                animator.setTarget(cache);
            }
        }
        start();
    }

    //----------extend end----------

    // override

    @Override
    public void start()
    {
        if (mIsDebug)
        {
            if (mListNode != null)
            {
                final StringBuilder sb = new StringBuilder("----------");
                for (NodeAnimator item : mListNode)
                {
                    switch (item.mNodeType)
                    {
                        case Head:
                            sb.append("\r\n").append("Head:").append(item.getTag());
                            break;
                        case Next:
                            sb.append("\r\n").append("Next:").append(item.getTag());
                            break;
                        case With:
                            sb.append(" With:").append(item.getTag());
                            break;
                        case Delay:
                            sb.append("\r\n").append("Delay:").append(item.getTag());
                            break;
                    }
                }
                Log.i(FAnimatorSet.class.getSimpleName(), sb.toString());
            }
        }
        getSet().start();
    }

    @Override
    public void cancel()
    {
        getSet().cancel();
    }

    @Override
    public FAnimatorSet setTarget(View target)
    {
        getCurrent().setTarget(target);
        return this;
    }

    @Override
    public FAnimatorSet setDuration(long duration)
    {
        getCurrent().setDuration(duration);
        return this;
    }

    @Override
    public FAnimatorSet setRepeatCount(int count)
    {
        getCurrent().setRepeatCount(count);
        return this;
    }

    @Override
    public FAnimatorSet setInterpolator(TimeInterpolator interpolator)
    {
        getCurrent().setInterpolator(interpolator);
        return this;
    }

    @Override
    public FAnimatorSet setStartDelay(long delay)
    {
        getCurrent().setStartDelay(delay);
        return this;
    }

    @Override
    public FAnimatorSet addListener(Animator.AnimatorListener listener)
    {
        getCurrent().addListener(listener);
        return this;
    }

    @Override
    public FAnimatorSet removeListener(Animator.AnimatorListener listener)
    {
        getCurrent().removeListener(listener);
        return this;
    }

    @Override
    public FAnimatorSet clearListener()
    {
        getCurrent().clearListener();
        return this;
    }

    @Override
    public FAnimatorSet x(float... values)
    {
        getCurrent().x(values);
        return this;
    }

    @Override
    public FAnimatorSet y(float... values)
    {
        getCurrent().y(values);
        return this;
    }

    @Override
    public FAnimatorSet translationX(float... values)
    {
        getCurrent().translationX(values);
        return this;
    }

    @Override
    public FAnimatorSet translationY(float... values)
    {
        getCurrent().translationY(values);
        return this;
    }

    @Override
    public FAnimatorSet alpha(float... values)
    {
        getCurrent().alpha(values);
        return this;
    }

    @Override
    public FAnimatorSet scaleX(float... values)
    {
        getCurrent().scaleX(values);
        return this;
    }

    @Override
    public FAnimatorSet scaleY(float... values)
    {
        getCurrent().scaleY(values);
        return this;
    }

    @Override
    public FAnimatorSet rotation(float... values)
    {
        getCurrent().rotation(values);
        return this;
    }

    @Override
    public FAnimatorSet rotationX(float... values)
    {
        getCurrent().rotationX(values);
        return this;
    }

    @Override
    public FAnimatorSet rotationY(float... values)
    {
        getCurrent().rotationY(values);
        return this;
    }

    @Override
    public FAnimatorSet moveToX(float... values)
    {
        getCurrent().moveToX(values);
        return this;
    }

    @Override
    public FAnimatorSet moveToY(float... values)
    {
        getCurrent().moveToY(values);
        return this;
    }

    @Override
    public FAnimatorSet moveToX(AlignType alignType, View... views)
    {
        getCurrent().moveToX(alignType, views);
        return this;
    }

    @Override
    public FAnimatorSet moveToY(AlignType alignType, View... views)
    {
        getCurrent().moveToY(alignType, views);
        return this;
    }

    @Override
    public FAnimatorSet scaleX(View... views)
    {
        getCurrent().scaleX(views);
        return this;
    }

    @Override
    public FAnimatorSet scaleY(View... views)
    {
        getCurrent().scaleY(views);
        return this;
    }

    @Override
    public FAnimatorSet setTag(String tag)
    {
        getCurrent().setTag(tag);
        return this;
    }

    @Override
    public FAnimatorSet clone()
    {
        throw new UnsupportedOperationException("clone() is not supported here");
    }
}
