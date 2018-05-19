package com.fanwe.lib.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class FAnimatorChain implements AnimatorChain
{
    private final AnimatorSet mAnimatorSet = new AnimatorSet();
    private FNodeAnimator mCurrent;

    private final boolean mIsDebug;
    private List<FNodeAnimator> mListNode;

    FAnimatorChain(boolean isDebug)
    {
        mIsDebug = isDebug;
    }

    void setCurrent(FNodeAnimator current)
    {
        if (current == null) throw new NullPointerException("current is null");

        if (current.getType() == FNodeAnimator.Type.HEAD)
        {
            if (mCurrent == null)
                mAnimatorSet.play(current.toObjectAnimator());
            else
                throw new UnsupportedOperationException("HEAD is already set");
        } else
        {
            if (mCurrent == null)
                throw new UnsupportedOperationException("HEAD must be set before this");
        }

        mCurrent = current;
        addNodeIfNeed(current);
    }

    private void addNodeIfNeed(FNodeAnimator animator)
    {
        if (mIsDebug)
        {
            if (mListNode == null) mListNode = new ArrayList<>();
            mListNode.add(animator);
        }
    }

    @Override
    public FNodeAnimator with()
    {
        return with(null);
    }

    @Override
    public FNodeAnimator with(View target)
    {
        final FNodeAnimator animator = new FNodeAnimator(FNodeAnimator.Type.WITH, this);
        animator.setTarget(target);
        return withInternal(animator);
    }

    @Override
    public FNodeAnimator withClone()
    {
        final FNodeAnimator animator = mCurrent.clone();
        animator.setType(FNodeAnimator.Type.WITH);
        return withInternal(animator);
    }

    private FNodeAnimator withInternal(FNodeAnimator animator)
    {
        initNodeAnim(animator);
        mAnimatorSet.play(mCurrent.toObjectAnimator()).with(animator.toObjectAnimator());
        setCurrent(animator);
        return animator;
    }

    @Override
    public FNodeAnimator next()
    {
        return next(null);
    }

    @Override
    public FNodeAnimator next(View target)
    {
        final FNodeAnimator animator = new FNodeAnimator(FNodeAnimator.Type.NEXT, this);
        animator.setTarget(target);
        return nextInternal(animator);
    }

    private FNodeAnimator nextInternal(FNodeAnimator animator)
    {
        initNodeAnim(animator);
        mAnimatorSet.play(animator.toObjectAnimator()).after(mCurrent.toObjectAnimator());
        setCurrent(animator);
        return animator;
    }

    @Override
    public FNodeAnimator delay(long time)
    {
        final FNodeAnimator animator = new FNodeAnimator(FNodeAnimator.Type.DELAY, this);
        animator.setDuration(time);
        return nextInternal(animator);
    }

    private void initNodeAnim(FNodeAnimator anim)
    {
        final View target = anim.getTarget();
        if (target == null)
        {
            anim.setTarget(mCurrent.getTarget());
        }
    }

    @Override
    public AnimatorSet toAnimatorSet()
    {
        return mAnimatorSet;
    }

    @Override
    public void start()
    {
        if (mIsDebug)
        {
            if (mListNode != null)
            {
                final StringBuilder sb = new StringBuilder("----------");
                for (FNodeAnimator item : mListNode)
                {
                    switch (item.getType())
                    {
                        case FNodeAnimator.Type.HEAD:
                            sb.append("\r\n").append("Head:").append(item.getTag());
                            break;
                        case FNodeAnimator.Type.NEXT:
                            sb.append("\r\n").append("Next:").append(item.getTag());
                            break;
                        case FNodeAnimator.Type.WITH:
                            sb.append(" With:").append(item.getTag());
                            break;
                        case FNodeAnimator.Type.DELAY:
                            sb.append("\r\n").append("Delay:").append(item.getTag());
                            break;
                    }
                }
                Log.i(AnimatorChain.class.getSimpleName(), sb.toString());
            }
        }

        mAnimatorSet.start();
    }

    @Override
    public void startAsPop()
    {
        final ArrayList<android.animation.Animator> listChild = mAnimatorSet.getChildAnimations();
        if (listChild == null || listChild.isEmpty())
        {
            return;
        }
        final HashMap<View, ImageView> mapCache = new HashMap<>();
        for (Animator animator : listChild)
        {
            final View target = (View) ((ObjectAnimator) animator).getTarget();
            if (target == null) continue;

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
}
