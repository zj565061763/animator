package com.sd.lib.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 动画链
 */
final class SimpleAnimatorChain implements AnimatorChain, Cloneable
{
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private TimeInterpolator mTimeInterpolator;
    private LinkedList<NodeAnimator> mListNode = new LinkedList<>();

    private boolean mIsDebug;

    public SimpleAnimatorChain(NodeAnimator animator)
    {
        if (animator.getType() != NodeAnimator.Type.Head)
            throw new IllegalArgumentException("animator must be " + NodeAnimator.Type.Head + " type");

        mAnimatorSet.play(animator.toObjectAnimator());
        mListNode.add(animator);
    }

    @Override
    public NodeAnimator currentNode()
    {
        return mListNode.getLast();
    }

    @Override
    public NodeAnimator appendNode(NodeAnimator animator)
    {
        if (animator.chain() != this)
            throw new IllegalArgumentException("animator's chain() method must return current instance");

        final NodeAnimator current = currentNode();
        if (animator.getTarget() == null)
            animator.setTarget(current.getTarget());

        switch (animator.getType())
        {
            case With:
                mAnimatorSet.play(current.toObjectAnimator()).with(animator.toObjectAnimator());
                break;
            case Next:
                mAnimatorSet.play(animator.toObjectAnimator()).after(current.toObjectAnimator());
                break;
            default:
                throw new IllegalArgumentException("Illegal type:" + animator.getType());
        }
        mListNode.add(animator);

        return animator;
    }

    private void logIfNeed()
    {
        if (mIsDebug)
        {
            final StringBuilder sb = new StringBuilder("----------");
            for (NodeAnimator item : mListNode)
            {
                switch (item.getType())
                {
                    case Head:
                        sb.append("\r\n").append("Head:");
                        break;
                    case With:
                        sb.append(" With:");
                        break;
                    case Next:
                        sb.append("\r\n").append("Next:");
                        break;
                }

                sb.append("(");

                if (!TextUtils.isEmpty(item.getDesc()))
                    sb.append(item.getDesc()).append(" ");

                sb.append(item.getPropertyName()).append(":").append(String.valueOf(item.getDuration()));

                if (item.getStartDelay() > 0)
                    sb.append(" startDelay:").append(String.valueOf(item.getStartDelay()));

                sb.append(")");
            }
            Log.i(AnimatorChain.class.getSimpleName(), sb.toString());
        }
    }

    @Override
    public AnimatorSet toAnimatorSet()
    {
        logIfNeed();
        return mAnimatorSet;
    }

    @Override
    public AnimatorChain setDebug(boolean debug)
    {
        mIsDebug = debug;
        return this;
    }

    //---------- Animator Start ----------

    @Override
    public AnimatorChain setTarget(View target)
    {
        mAnimatorSet.setTarget(target);
        return null;
    }

    @Override
    public View getTarget()
    {
        return null;
    }

    @Override
    public AnimatorChain setDuration(long duration)
    {
        mAnimatorSet.setDuration(duration);
        return this;
    }

    @Override
    public long getDuration()
    {
        return mAnimatorSet.getDuration();
    }

    @Override
    public AnimatorChain setInterpolator(TimeInterpolator interpolator)
    {
        mAnimatorSet.setInterpolator(interpolator);
        mTimeInterpolator = interpolator;
        return this;
    }

    @Override
    public TimeInterpolator getInterpolator()
    {
        return mTimeInterpolator;
    }

    @Override
    public AnimatorChain setStartDelay(long delay)
    {
        mAnimatorSet.setStartDelay(delay);
        return this;
    }

    @Override
    public long getStartDelay()
    {
        return mAnimatorSet.getStartDelay();
    }

    @Override
    public AnimatorChain addListener(Animator.AnimatorListener... listeners)
    {
        if (listeners != null)
        {
            for (Animator.AnimatorListener item : listeners)
            {
                mAnimatorSet.addListener(item);
            }
        }
        return this;
    }

    @Override
    public AnimatorChain removeListener(Animator.AnimatorListener... listeners)
    {
        if (listeners != null)
        {
            for (Animator.AnimatorListener item : listeners)
            {
                mAnimatorSet.removeListener(item);
            }
        }
        return this;
    }

    @Override
    public ArrayList<Animator.AnimatorListener> getListeners()
    {
        return mAnimatorSet.getListeners();
    }

    @Override
    public AnimatorChain clearListener()
    {
        final ArrayList<Animator.AnimatorListener> listeners = getListeners();
        if (listeners != null)
            listeners.clear();
        return this;
    }

    @Override
    public boolean isRunning()
    {
        return mAnimatorSet.isRunning();
    }

    @Override
    public boolean isStarted()
    {
        return mAnimatorSet.isStarted();
    }

    @Override
    public void cancel()
    {
        mAnimatorSet.cancel();
    }

    @Override
    public AnimatorChain start()
    {
        toAnimatorSet().start();
        return this;
    }

    @Override
    public AnimatorChain startAsPop(boolean clone)
    {
        final AnimatorChain chain = clone ? clone() : this;
        final AnimatorSet animatorSet = chain.toAnimatorSet();

        final HashMap<View, ImageView> mapCache = new HashMap<>();
        final ArrayList<android.animation.Animator> listChild = animatorSet.getChildAnimations();
        for (Animator animator : listChild)
        {
            final View target = (View) ((ObjectAnimator) animator).getTarget();
            if (target == null)
                continue;

            final ImageView cache = mapCache.get(target);
            if (cache == null)
            {
                final Context context = target.getContext();
                if (context instanceof Activity)
                {
                    PopImageView imageView = new PopImageView(context);
                    imageView.setDrawingCacheView(target);
                    imageView.attachTarget(target);
                    imageView.setVisibility(target.isShown() ? View.VISIBLE : View.INVISIBLE);

                    animator.setTarget(imageView);
                    mapCache.put(target, imageView);
                }
            } else
            {
                animator.setTarget(cache);
            }
        }

        if (mapCache.isEmpty())
        {
            return null;
        } else
        {
            animatorSet.start();
            return chain;
        }
    }

    @Override
    public SimpleAnimatorChain clone()
    {
        try
        {
            final SimpleAnimatorChain chain = (SimpleAnimatorChain) super.clone();
            chain.mAnimatorSet = mAnimatorSet.clone();
            chain.mListNode = new LinkedList<>(mListNode);
            return chain;
        } catch (CloneNotSupportedException e)
        {
        }
        return null;
    }

    //---------- Animator End ----------
}
