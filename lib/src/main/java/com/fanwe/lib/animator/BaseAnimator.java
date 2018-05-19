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
import com.fanwe.lib.animator.listener.api.OnStartVisible;

import java.util.ArrayList;
import java.util.List;

/**
 * 对ObjectAnimator进行封装提供更方便的调用方法
 */
class BaseAnimator<T extends SimplePropertyAnimator> implements SimplePropertyAnimator<T>
{
    private ObjectAnimator mObjectAnimator = new ObjectAnimator();
    private String mTag;

    public BaseAnimator()
    {
        mObjectAnimator.setFloatValues(0);
        mObjectAnimator.addListener(new OnStartVisible());
    }

    //---------- Animator Start ----------

    @Override
    public T setTarget(View target)
    {
        mObjectAnimator.setTarget(target);
        return (T) this;
    }

    @Override
    public View getTarget()
    {
        return (View) mObjectAnimator.getTarget();
    }

    @Override
    public T setDuration(long duration)
    {
        mObjectAnimator.setDuration(duration);
        return (T) this;
    }

    @Override
    public long getDuration()
    {
        return mObjectAnimator.getDuration();
    }

    @Override
    public T setRepeatCount(int count)
    {
        if (count < 0)
        {
            count = ValueAnimator.INFINITE;
        }
        mObjectAnimator.setRepeatCount(count);
        return (T) this;
    }

    @Override
    public int getRepeatCount()
    {
        return mObjectAnimator.getRepeatCount();
    }

    @Override
    public T setInterpolator(TimeInterpolator interpolator)
    {
        mObjectAnimator.setInterpolator(interpolator);
        return (T) this;
    }

    @Override
    public TimeInterpolator getInterpolator()
    {
        return mObjectAnimator.getInterpolator();
    }

    @Override
    public T setStartDelay(long delay)
    {
        mObjectAnimator.setStartDelay(delay);
        return (T) this;
    }

    @Override
    public long getStartDelay()
    {
        return mObjectAnimator.getStartDelay();
    }

    @Override
    public T addListener(Animator.AnimatorListener listener)
    {
        if (listener != null)
        {
            if (!containsListener(listener))
            {
                mObjectAnimator.addListener(listener);
            }
        }
        return (T) this;
    }

    private boolean containsListener(Animator.AnimatorListener listener)
    {
        final ArrayList<Animator.AnimatorListener> list = mObjectAnimator.getListeners();
        if (list == null) return false;
        return list.contains(listener);
    }

    @Override
    public T removeListener(Animator.AnimatorListener listener)
    {
        mObjectAnimator.removeListener(listener);
        return (T) this;
    }

    @Override
    public T clearListener()
    {
        final ArrayList<Animator.AnimatorListener> list = mObjectAnimator.getListeners();
        if (list != null) list.clear();
        return (T) this;
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
    public T x(float... values)
    {
        mObjectAnimator.setPropertyName(X);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T y(float... values)
    {
        mObjectAnimator.setPropertyName(Y);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T translationX(float... values)
    {
        mObjectAnimator.setPropertyName(TRANSLATION_X);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T translationY(float... values)
    {
        mObjectAnimator.setPropertyName(TRANSLATION_Y);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T alpha(float... values)
    {
        mObjectAnimator.setPropertyName(ALPHA);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T scaleX(float... values)
    {
        mObjectAnimator.setPropertyName(SCALE_X);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T scaleY(float... values)
    {
        mObjectAnimator.setPropertyName(SCALE_Y);
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T rotation(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
    }

    @Override
    public T rotationX(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION_X);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
    }

    @Override
    public T rotationY(float... values)
    {
        mObjectAnimator.setPropertyName(ROTATION_Y);
        mObjectAnimator.setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
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
    public T moveToX(float... values)
    {
        moveTo(X_COORDINATE, values);
        return (T) this;
    }

    @Override
    public T moveToY(float... values)
    {
        moveTo(Y_COORDINATE, values);
        return (T) this;
    }

    @Override
    public T moveToX(Aligner aligner, View... views)
    {
        moveTo(X_COORDINATE, aligner, views);
        return (T) this;
    }

    @Override
    public T moveToY(Aligner aligner, View... views)
    {
        moveTo(Y_COORDINATE, aligner, views);
        return (T) this;
    }

    @Override
    public T scaleX(View... views)
    {
        scale(X_COORDINATE, views);
        return (T) this;
    }

    @Override
    public T scaleY(View... views)
    {
        scale(Y_COORDINATE, views);
        return (T) this;
    }

    @Override
    public T setTag(String tag)
    {
        mTag = tag;
        return (T) this;
    }

    @Override
    public String getTag()
    {
        if (mTag == null) mTag = "";
        return mTag;
    }

    private static final int X_COORDINATE = 0;
    private static final int Y_COORDINATE = 1;

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

    private void moveTo(int coordinate, float... values)
    {
        if (values != null && values.length > 0)
        {
            saveTargetLocation();
            final float[] realValues = new float[values.length];
            for (int i = 0; i < values.length; i++)
            {
                if (coordinate == X_COORDINATE)
                {
                    realValues[i] = (values[i] - mTargetLocation[0]) + getTarget().getTranslationX();
                } else if (coordinate == Y_COORDINATE)
                {
                    realValues[i] = (values[i] - mTargetLocation[1]) + getTarget().getTranslationY();
                }
            }
            if (coordinate == X_COORDINATE)
            {
                translationX(realValues);
            } else if (coordinate == Y_COORDINATE)
            {
                translationY(realValues);
            }
        }
    }

    private void moveTo(int coordinate, Aligner aligner, View... views)
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
                if (coordinate == X_COORDINATE)
                {
                    float value = aligner.align(getTarget(), view, mTempLocation[0]);
                    list.add(value);
                } else if (coordinate == Y_COORDINATE)
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
                if (coordinate == X_COORDINATE)
                {
                    moveToX(values);
                } else if (coordinate == Y_COORDINATE)
                {
                    moveToY(values);
                }
            }
        }
    }

    private void scale(int coordinate, View... views)
    {
        if (views != null && views.length > 0)
        {
            final float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                if (coordinate == X_COORDINATE)
                {
                    values[i] = ((float) views[i].getWidth()) / ((float) getTarget().getWidth());
                } else if (coordinate == Y_COORDINATE)
                {
                    values[i] = ((float) views[i].getHeight()) / ((float) getTarget().getHeight());
                }
            }
            if (coordinate == X_COORDINATE)
            {
                scaleX(values);
            } else if (coordinate == Y_COORDINATE)
            {
                scaleY(values);
            }
        }
    }

    //---------- SimplePropertyAnimator End ----------

    @Override
    public T clone()
    {
        try
        {
            BaseAnimator clone = (BaseAnimator) super.clone();
            clone.mObjectAnimator = mObjectAnimator.clone();
            clone.clearListener();
            clone.addListener(new OnStartVisible());
            return (T) clone;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
