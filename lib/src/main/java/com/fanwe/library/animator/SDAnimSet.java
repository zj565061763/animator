package com.fanwe.library.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.view.View;

import com.fanwe.library.poper.view.SDPopImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 对AnimatorSet进行封装提供一系列可以同时或者连续执行的动画集合
 */
public class SDAnimSet extends SDAnim
{
    //parent only
    private AnimatorSet mAnimatorSet;
    private SDAnimSet mCurrentAnim;

    private SDAnimSet mParentAnim;
    private AnimType mAnimType;

    public SDAnimSet(View target)
    {
        super(target);
        mAnimType = AnimType.Parent;
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(get());
        mCurrentAnim = this;
    }

    private SDAnimSet()
    {
        super(null);
    }

    /**
     * 快速创建对象方法
     *
     * @param target
     * @return
     */
    public static SDAnimSet from(View target)
    {
        SDAnimSet animSet = new SDAnimSet(target);
        return animSet;
    }

    private void initNewAnim(SDAnimSet anim)
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

    private void setParentAnim(SDAnimSet parentAnim)
    {
        this.mParentAnim = parentAnim;
    }

    private void setCurrentAnim(SDAnimSet anim)
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

    private SDAnimSet getCurrentAnim()
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

    private SDAnimSet newInstance()
    {
        SDAnimSet animSet = new SDAnimSet();
        return animSet;
    }

    /**
     * 返回和当前动画共同执行的新动画
     *
     * @return 新动画
     */
    public SDAnimSet with()
    {
        return with(null);
    }

    /**
     * 返回和当前动画共同执行的新动画
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return 新动画
     */
    public SDAnimSet with(View target)
    {
        SDAnimSet with = newInstance();
        with.setTarget(target);
        return withInternal(with);
    }

    private SDAnimSet withInternal(SDAnimSet with)
    {
        with.mAnimType = AnimType.With;
        initNewAnim(with);
        SDAnim current = getCurrentAnim();
        getSet().play(current.get()).with(with.get());
        setCurrentAnim(with);
        return with;
    }

    /**
     * 返回在当前动画后面执行的新动画
     *
     * @return 新动画
     */
    public SDAnimSet next()
    {
        return next(null);
    }

    /**
     * 返回在当前动画后面执行的新动画
     *
     * @param target 新动画要执行的View对象，如果为null，则沿用当前动画的View对象
     * @return 新动画
     */
    public SDAnimSet next(View target)
    {
        SDAnimSet next = newInstance();
        next.setTarget(target);
        return nextInternal(next);
    }

    private SDAnimSet nextInternal(SDAnimSet next)
    {
        next.mAnimType = AnimType.Next;
        initNewAnim(next);
        SDAnim current = getCurrentAnim();
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
    public SDAnimSet delay(long time)
    {
        SDAnimSet delay = null;
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
        final HashMap<View, SDPopImageView> mapTargetPoper = new HashMap<>();
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
                    SDPopImageView popView = new SDPopImageView(target);
                    popView.getPoper().attach(true);
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
    public SDAnimSet withClone()
    {
        return withClone(null);
    }

    /**
     * 在{@link #with(View)}方法的基础上会保留当前动画的参数设置
     *
     * @param target
     * @return
     */
    public SDAnimSet withClone(View target)
    {
        SDAnimSet clone = clone();
        clone.setTarget(target);
        SDAnimSet with = withInternal(clone);
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
    public SDAnimSet setTarget(View target)
    {
        return (SDAnimSet) super.setTarget(target);
    }

    @Override
    public SDAnimSet setDuration(long duration)
    {
        return (SDAnimSet) super.setDuration(duration);
    }

    @Override
    public SDAnimSet setRepeatCount(int count)
    {
        return (SDAnimSet) super.setRepeatCount(count);
    }

    @Override
    public SDAnimSet setInterpolator(TimeInterpolator interpolator)
    {
        return (SDAnimSet) super.setInterpolator(interpolator);
    }

    @Override
    public SDAnimSet setStartDelay(long delay)
    {
        return (SDAnimSet) super.setStartDelay(delay);
    }

    @Override
    public SDAnimSet addListener(Animator.AnimatorListener listener)
    {
        return (SDAnimSet) super.addListener(listener);
    }

    @Override
    public SDAnimSet removeListener(Animator.AnimatorListener listener)
    {
        return (SDAnimSet) super.removeListener(listener);
    }

    @Override
    public SDAnimSet clearListener()
    {
        return (SDAnimSet) super.clearListener();
    }

    @Override
    public SDAnimSet x(float... values)
    {
        return (SDAnimSet) super.x(values);
    }

    @Override
    public SDAnimSet y(float... values)
    {
        return (SDAnimSet) super.y(values);
    }

    @Override
    public SDAnimSet translationX(float... values)
    {
        return (SDAnimSet) super.translationX(values);
    }

    @Override
    public SDAnimSet translationY(float... values)
    {
        return (SDAnimSet) super.translationY(values);
    }

    @Override
    public SDAnimSet alpha(float... values)
    {
        return (SDAnimSet) super.alpha(values);
    }

    @Override
    public SDAnimSet scaleX(float... values)
    {
        return (SDAnimSet) super.scaleX(values);
    }

    @Override
    public SDAnimSet scaleY(float... values)
    {
        return (SDAnimSet) super.scaleY(values);
    }

    @Override
    public SDAnimSet rotation(float... values)
    {
        return (SDAnimSet) super.rotation(values);
    }

    @Override
    public SDAnimSet rotationX(float... values)
    {
        return (SDAnimSet) super.rotationX(values);
    }

    @Override
    public SDAnimSet rotationY(float... values)
    {
        return (SDAnimSet) super.rotationY(values);
    }

    @Override
    public SDAnimSet setDecelerate()
    {
        return (SDAnimSet) super.setDecelerate();
    }

    @Override
    public SDAnimSet setAccelerate()
    {
        return (SDAnimSet) super.setAccelerate();
    }

    @Override
    public SDAnimSet setAccelerateDecelerate()
    {
        return (SDAnimSet) super.setAccelerateDecelerate();
    }

    @Override
    public SDAnimSet setLinear()
    {
        return (SDAnimSet) super.setLinear();
    }

    @Override
    public SDAnimSet clone()
    {
        SDAnimSet clone = (SDAnimSet) super.clone();
        clone.mAnimatorSet = null;
        clone.mCurrentAnim = null;
        return clone;
    }

    @Override
    public SDAnimSet moveToX(float... values)
    {
        return (SDAnimSet) super.moveToX(values);
    }

    @Override
    public SDAnimSet moveToX(View... views)
    {
        return (SDAnimSet) super.moveToX(views);
    }

    @Override
    public SDAnimSet moveToY(float... values)
    {
        return (SDAnimSet) super.moveToY(values);
    }

    @Override
    public SDAnimSet moveToY(View... views)
    {
        return (SDAnimSet) super.moveToY(views);
    }

    @Override
    public SDAnimSet scaleX(View... views)
    {
        return (SDAnimSet) super.scaleX(views);
    }

    @Override
    public SDAnimSet scaleY(View... views)
    {
        return (SDAnimSet) super.scaleY(views);
    }

    @Override
    public SDAnimSet setAlignType(AlignType alignType)
    {
        return (SDAnimSet) super.setAlignType(alignType);
    }

    public enum AnimType
    {
        Parent, With, Next
    }
}
