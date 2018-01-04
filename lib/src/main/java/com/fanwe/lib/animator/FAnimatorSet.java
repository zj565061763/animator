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
import android.view.View;

import com.fanwe.lib.poper.view.FPopImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 对AnimatorSet进行封装提供一系列可以同时或者连续执行的动画集合
 */
public class FAnimatorSet extends FAnimator
{
    //parent only
    private AnimatorSet mAnimatorSet;
    private FAnimatorSet mCurrentAnim;

    private FAnimatorSet mParentAnim;
    private AnimType mAnimType;

    public FAnimatorSet(View target)
    {
        super(target);
        mAnimType = AnimType.Parent;
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(get());
        mCurrentAnim = this;
    }

    private FAnimatorSet()
    {
        super(null);
    }

    /**
     * 快速创建对象方法
     *
     * @param target
     * @return
     */
    public static FAnimatorSet from(View target)
    {
        FAnimatorSet animSet = new FAnimatorSet(target);
        return animSet;
    }

    private void initNewAnim(FAnimatorSet anim)
    {
        //如果target为空，设置默认target
        View target = anim.getTarget();
        if (target == null)
        {
            target = this.getTarget();
            if (target == null)
            {
                if (mParentAnim != null)
                {
                    target = mParentAnim.getTarget();
                }
            }
            anim.setTarget(target);
        }
    }

    private void setParentAnim(FAnimatorSet parentAnim)
    {
        this.mParentAnim = parentAnim;
    }

    private void setCurrentAnim(FAnimatorSet anim)
    {
        if (mParentAnim != null)
        {
            mParentAnim.setCurrentAnim(anim);
        } else
        {
            // 只有parent会触发这里
            if (this != anim)
            {
                anim.setParentAnim(this);
            }
            this.mCurrentAnim = anim;
        }
    }

    private FAnimatorSet getCurrentAnim()
    {
        if (mParentAnim != null)
        {
            return mParentAnim.getCurrentAnim();
        } else
        {
            // 只有parent会触发这里
            return this.mCurrentAnim;
        }
    }

    private FAnimatorSet newInstance()
    {
        FAnimatorSet animSet = new FAnimatorSet();
        return animSet;
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
        FAnimatorSet with = newInstance();
        with.setTarget(target);
        return withInternal(with);
    }

    private FAnimatorSet withInternal(FAnimatorSet with)
    {
        with.mAnimType = AnimType.With;
        initNewAnim(with);
        FAnimator current = getCurrentAnim();
        getSet().play(current.get()).with(with.get());
        setCurrentAnim(with);
        return with;
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
        FAnimatorSet next = newInstance();
        next.setTarget(target);
        return nextInternal(next);
    }

    private FAnimatorSet nextInternal(FAnimatorSet next)
    {
        next.mAnimType = AnimType.Next;
        initNewAnim(next);
        FAnimator current = getCurrentAnim();
        getSet().play(next.get()).after(current.get());
        setCurrentAnim(next);
        return next;
    }

    /**
     * 返回延迟动画，返回的对象不应该再进行任何可以改变动画行为的参数设置
     *
     * @param time 延迟多少毫秒
     * @return 延迟动画
     */
    public FAnimatorSet delay(long time)
    {
        FAnimatorSet delay = null;
        if (isEmptyProperty())
        {
            delay = this;
        } else
        {
            delay = next();
        }
        delay.setDuration(time);
        return delay;
    }

    /**
     * 获得内部封装的AnimatorSet对象
     *
     * @return
     */
    public AnimatorSet getSet()
    {
        if (mParentAnim != null)
        {
            return mParentAnim.getSet();
        } else
        {
            return mAnimatorSet;
        }
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
        final HashMap<View, FPopImageView> mapTargetPoper = new HashMap<>();
        for (Animator animator : listChild)
        {
            View target = (View) ((ObjectAnimator) animator).getTarget();
            if (target == null)
            {
                continue;
            }
            if (!mapTargetPoper.containsKey(target))
            {
                if (target.getContext() instanceof Activity)
                {
                    FPopImageView popView = new FPopImageView(target.getContext());
                    popView.setDrawingCacheView(target);
                    popView.getPoper().setTarget(target).attach(true).setTarget(null);

                    animator.setTarget(popView);

                    mapTargetPoper.put(target, popView);
                }
            } else
            {
                animator.setTarget(mapTargetPoper.get(target));
            }
        }
        start();
    }

    /**
     * 在{@link #with()}方法的基础上会保留当前动画的参数设置
     *
     * @return
     */
    public FAnimatorSet withClone()
    {
        return withClone(null);
    }

    /**
     * 在{@link #with(View)}方法的基础上会保留当前动画的参数设置
     *
     * @param target
     * @return
     */
    public FAnimatorSet withClone(View target)
    {
        FAnimatorSet clone = clone();
        clone.setTarget(target);
        FAnimatorSet with = withInternal(clone);
        return with;
    }

    //----------extend end----------

    // override

    @Override
    public void start()
    {
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
        return (FAnimatorSet) super.setTarget(target);
    }

    @Override
    public FAnimatorSet setDuration(long duration)
    {
        return (FAnimatorSet) super.setDuration(duration);
    }

    @Override
    public FAnimatorSet setRepeatCount(int count)
    {
        return (FAnimatorSet) super.setRepeatCount(count);
    }

    @Override
    public FAnimatorSet setInterpolator(TimeInterpolator interpolator)
    {
        return (FAnimatorSet) super.setInterpolator(interpolator);
    }

    @Override
    public FAnimatorSet setStartDelay(long delay)
    {
        return (FAnimatorSet) super.setStartDelay(delay);
    }

    @Override
    public FAnimatorSet addListener(Animator.AnimatorListener listener)
    {
        return (FAnimatorSet) super.addListener(listener);
    }

    @Override
    public FAnimatorSet removeListener(Animator.AnimatorListener listener)
    {
        return (FAnimatorSet) super.removeListener(listener);
    }

    @Override
    public FAnimatorSet clearListener()
    {
        return (FAnimatorSet) super.clearListener();
    }

    @Override
    public FAnimatorSet x(float... values)
    {
        return (FAnimatorSet) super.x(values);
    }

    @Override
    public FAnimatorSet y(float... values)
    {
        return (FAnimatorSet) super.y(values);
    }

    @Override
    public FAnimatorSet translationX(float... values)
    {
        return (FAnimatorSet) super.translationX(values);
    }

    @Override
    public FAnimatorSet translationY(float... values)
    {
        return (FAnimatorSet) super.translationY(values);
    }

    @Override
    public FAnimatorSet alpha(float... values)
    {
        return (FAnimatorSet) super.alpha(values);
    }

    @Override
    public FAnimatorSet scaleX(float... values)
    {
        return (FAnimatorSet) super.scaleX(values);
    }

    @Override
    public FAnimatorSet scaleY(float... values)
    {
        return (FAnimatorSet) super.scaleY(values);
    }

    @Override
    public FAnimatorSet rotation(float... values)
    {
        return (FAnimatorSet) super.rotation(values);
    }

    @Override
    public FAnimatorSet rotationX(float... values)
    {
        return (FAnimatorSet) super.rotationX(values);
    }

    @Override
    public FAnimatorSet rotationY(float... values)
    {
        return (FAnimatorSet) super.rotationY(values);
    }

    @Override
    public FAnimatorSet setDecelerate()
    {
        return (FAnimatorSet) super.setDecelerate();
    }

    @Override
    public FAnimatorSet setAccelerate()
    {
        return (FAnimatorSet) super.setAccelerate();
    }

    @Override
    public FAnimatorSet setAccelerateDecelerate()
    {
        return (FAnimatorSet) super.setAccelerateDecelerate();
    }

    @Override
    public FAnimatorSet setLinear()
    {
        return (FAnimatorSet) super.setLinear();
    }

    @Override
    public FAnimatorSet clone()
    {
        FAnimatorSet clone = (FAnimatorSet) super.clone();
        clone.mAnimatorSet = null;
        clone.mCurrentAnim = null;
        return clone;
    }

    @Override
    public FAnimatorSet moveToX(float... values)
    {
        return (FAnimatorSet) super.moveToX(values);
    }

    @Override
    public FAnimatorSet moveToX(View... views)
    {
        return (FAnimatorSet) super.moveToX(views);
    }

    @Override
    public FAnimatorSet moveToY(float... values)
    {
        return (FAnimatorSet) super.moveToY(values);
    }

    @Override
    public FAnimatorSet moveToY(View... views)
    {
        return (FAnimatorSet) super.moveToY(views);
    }

    @Override
    public FAnimatorSet scaleX(View... views)
    {
        return (FAnimatorSet) super.scaleX(views);
    }

    @Override
    public FAnimatorSet scaleY(View... views)
    {
        return (FAnimatorSet) super.scaleY(views);
    }

    @Override
    public FAnimatorSet setAlignType(AlignType alignType)
    {
        return (FAnimatorSet) super.setAlignType(alignType);
    }

    public enum AnimType
    {
        Parent, With, Next
    }
}
