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
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.fanwe.lib.animator.aligner.Aligner;
import com.fanwe.lib.animator.listener.OnStartVisible;

import java.util.ArrayList;
import java.util.List;

/**
 * 对ObjectAnimator进行封装提供更方便的调用方法
 */
public class FAnimator implements SimplePropertyAnimator, Cloneable
{
    private ObjectAnimator mObjectAnimator = new ObjectAnimator();
    private String mTag;

    public FAnimator()
    {
        mObjectAnimator.setFloatValues(0);
        mObjectAnimator.addListener(new OnStartVisible());
    }

    //---------- Animator Start ----------

    @Override
    public FAnimator setTarget(View target)
    {
        mObjectAnimator.setTarget(target);
        return this;
    }

    @Override
    public View getTarget()
    {
        return (View) mObjectAnimator.getTarget();
    }

    @Override
    public FAnimator setDuration(long duration)
    {
        mObjectAnimator.setDuration(duration);
        return this;
    }

    @Override
    public long getDuration()
    {
        return mObjectAnimator.getDuration();
    }

    @Override
    public FAnimator setRepeatCount(int count)
    {
        if (count < 0)
        {
            mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        } else
        {
            mObjectAnimator.setRepeatCount(count);
        }
        return this;
    }

    @Override
    public int getRepeatCount()
    {
        return mObjectAnimator.getRepeatCount();
    }

    @Override
    public FAnimator setInterpolator(TimeInterpolator interpolator)
    {
        mObjectAnimator.setInterpolator(interpolator);
        return this;
    }

    @Override
    public TimeInterpolator getInterpolator()
    {
        return mObjectAnimator.getInterpolator();
    }

    @Override
    public FAnimator setStartDelay(long delay)
    {
        mObjectAnimator.setStartDelay(delay);
        return this;
    }

    @Override
    public long getStartDelay()
    {
        return mObjectAnimator.getStartDelay();
    }

    @Override
    public FAnimator addListener(Animator.AnimatorListener listener)
    {
        if (listener != null)
        {
            if (!containsListener(listener))
            {
                mObjectAnimator.addListener(listener);
            }
        }
        return this;
    }

    private boolean containsListener(Animator.AnimatorListener listener)
    {
        final ArrayList<Animator.AnimatorListener> list = mObjectAnimator.getListeners();
        if (list == null) return false;
        return list.contains(listener);
    }

    @Override
    public FAnimator removeListener(Animator.AnimatorListener listener)
    {
        mObjectAnimator.removeListener(listener);
        return this;
    }

    @Override
    public FAnimator clearListener()
    {
        final ArrayList<Animator.AnimatorListener> list = mObjectAnimator.getListeners();
        if (list != null) list.clear();
        return this;
    }

    @Override
    public void start()
    {
        mObjectAnimator.start();
    }

    @Override
    public boolean isRunning()
    {
        return mObjectAnimator.isRunning();
    }

    @Override
    public boolean isStarted()
    {
        return mObjectAnimator.isStarted();
    }

    @Override
    public void cancel()
    {
        mObjectAnimator.cancel();
    }

    //---------- Animator End ----------

    //---------- PropertyAnimator Start ----------

    @Override
    public FAnimator x(float... values)
    {
        mObjectAnimator.setPropertyName(X);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator y(float... values)
    {
        mObjectAnimator.setPropertyName(Y);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator translationX(float... values)
    {
        mObjectAnimator.setPropertyName(TRANSLATION_X);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator translationY(float... values)
    {
        mObjectAnimator.setPropertyName(TRANSLATION_Y);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator alpha(float... values)
    {
        mObjectAnimator.setPropertyName(ALPHA);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator scaleX(float... values)
    {
        mObjectAnimator.setPropertyName(SCALE_X);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator scaleY(float... values)
    {
        mObjectAnimator.setPropertyName(SCALE_Y);
        mObjectAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public FAnimator rotation(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return this;
    }

    @Override
    public FAnimator rotationX(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION_X);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return this;
    }

    @Override
    public FAnimator rotationY(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION_Y);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return this;
    }

    @Override
    public boolean isEmptyProperty()
    {
        final String propertyName = mObjectAnimator.getPropertyName();
        return TextUtils.isEmpty(propertyName) || "null".equals(propertyName);
    }

    @Override
    public ObjectAnimator toObjectAnimator()
    {
        return mObjectAnimator;
    }

    //---------- PropertyAnimator End ----------

    //---------- SimplePropertyAnimator Start ----------

    @Override
    public FAnimator moveToX(float... values)
    {
        moveTo(Coordinate.X, values);
        return this;
    }

    @Override
    public FAnimator moveToY(float... values)
    {
        moveTo(Coordinate.Y, values);
        return this;
    }

    @Override
    public FAnimator moveToX(Aligner aligner, View... views)
    {
        moveTo(Coordinate.X, aligner, views);
        return this;
    }

    @Override
    public FAnimator moveToY(Aligner aligner, View... views)
    {
        moveTo(Coordinate.Y, aligner, views);
        return this;
    }

    @Override
    public FAnimator scaleX(View... views)
    {
        scale(Coordinate.X, views);
        return this;
    }

    @Override
    public FAnimator scaleY(View... views)
    {
        scale(Coordinate.Y, views);
        return this;
    }

    @Override
    public FAnimator setTag(String tag)
    {
        mTag = tag;
        return this;
    }

    @Override
    public String getTag()
    {
        if (mTag == null) mTag = "";
        return mTag;
    }

    private int[] mTargetLocation;
    private int[] mTempLocation;

    private void saveTargetLocation()
    {
        if (mTargetLocation == null) mTargetLocation = new int[]{0, 0};
        getTarget().getLocationOnScreen(mTargetLocation);
    }

    private void saveTempLocation(View view)
    {
        if (view == null) return;
        if (mTempLocation == null) mTempLocation = new int[]{0, 0};
        view.getLocationOnScreen(mTempLocation);
    }

    private void moveTo(Coordinate coordinate, float... values)
    {
        if (values != null && values.length > 0)
        {
            saveTargetLocation();
            final float[] realValues = new float[values.length];
            for (int i = 0; i < values.length; i++)
            {
                if (coordinate == Coordinate.X)
                {
                    realValues[i] = (values[i] - mTargetLocation[0]) + getTarget().getTranslationX();
                } else if (coordinate == Coordinate.Y)
                {
                    realValues[i] = (values[i] - mTargetLocation[1]) + getTarget().getTranslationY();
                }
            }
            if (coordinate == Coordinate.X)
            {
                translationX(realValues);
            } else if (coordinate == Coordinate.Y)
            {
                translationY(realValues);
            }
        }
    }

    private void moveTo(Coordinate coordinate, Aligner aligner, View... views)
    {
        if (views != null && views.length > 0)
        {
            final List<Float> list = new ArrayList<>();
            for (int i = 0; i < views.length; i++)
            {
                final View view = views[i];
                if (view == null) continue;
                if (aligner == null) aligner = Aligner.DEFAULT;

                saveTempLocation(view);
                if (coordinate == Coordinate.X)
                {
                    float value = aligner.align(getTarget(), view, mTempLocation[0]);
                    list.add(value);
                } else if (coordinate == Coordinate.Y)
                {
                    float value = aligner.align(getTarget(), view, mTempLocation[1]);
                    list.add(value);
                }
            }

            final int count = list.size();
            if (count > 0)
            {
                final float[] values = new float[count];
                for (int i = 0; i < count; i++)
                {
                    values[i] = list.get(i);
                }
                if (coordinate == Coordinate.X)
                {
                    moveToX(values);
                } else if (coordinate == Coordinate.Y)
                {
                    moveToY(values);
                }
            }
        }
    }

    private void scale(Coordinate coordinate, View... views)
    {
        if (views != null && views.length > 0)
        {
            final float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                if (coordinate == Coordinate.X)
                {
                    values[i] = ((float) views[i].getWidth()) / ((float) getTarget().getWidth());
                } else if (coordinate == Coordinate.Y)
                {
                    values[i] = ((float) views[i].getHeight()) / ((float) getTarget().getHeight());
                }
            }
            if (coordinate == Coordinate.X)
            {
                scaleX(values);
            } else if (coordinate == Coordinate.Y)
            {
                scaleY(values);
            }
        }
    }

    private enum Coordinate
    {
        X, Y
    }

    //---------- SimplePropertyAnimator End ----------

    @Override
    public FAnimator clone()
    {
        try
        {
            FAnimator clone = (FAnimator) super.clone();
            clone.mObjectAnimator = mObjectAnimator.clone();
            clone.clearListener();
            clone.addListener(new OnStartVisible());
            return clone;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
