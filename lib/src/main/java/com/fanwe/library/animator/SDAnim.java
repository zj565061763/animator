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
package com.fanwe.library.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.fanwe.library.animator.listener.OnStartVisible;

/**
 * 对ObjectAnimator进行封装提供更方便的调用方法
 */
public class SDAnim implements ISDPropertyAnim, Cloneable
{
    private ObjectAnimator mAnimator = new ObjectAnimator();

    private int[] mTargetLocation;
    private int[] mTempLocation;
    private AlignType mAlignType = AlignType.TopLeft;

    public SDAnim(View target)
    {
        setTarget(target);
        setDefaultValues();
        addListener(new OnStartVisible());
    }

    private void setDefaultValues()
    {
        mAnimator.setFloatValues(0);
    }

    /**
     * 快速创建对象方法
     *
     * @param target
     * @return
     */
    public static SDAnim from(View target)
    {
        SDAnim anim = new SDAnim(target);
        return anim;
    }

    @Override
    public SDAnim setTarget(View target)
    {
        mAnimator.setTarget(target);
        return this;
    }

    @Override
    public View getTarget()
    {
        return (View) mAnimator.getTarget();
    }

    @Override
    public SDAnim setDuration(long duration)
    {
        mAnimator.setDuration(duration);
        return this;
    }

    @Override
    public long getDuration()
    {
        return mAnimator.getDuration();
    }

    @Override
    public SDAnim setRepeatCount(int count)
    {
        if (count < 0)
        {
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        } else
        {
            mAnimator.setRepeatCount(count);
        }
        return this;
    }

    @Override
    public int getRepeatCount()
    {
        return mAnimator.getRepeatCount();
    }

    @Override
    public SDAnim setInterpolator(TimeInterpolator interpolator)
    {
        mAnimator.setInterpolator(interpolator);
        return this;
    }

    @Override
    public TimeInterpolator getInterpolator()
    {
        return mAnimator.getInterpolator();
    }

    @Override
    public SDAnim setStartDelay(long delay)
    {
        mAnimator.setStartDelay(delay);
        return this;
    }

    @Override
    public long getStartDelay()
    {
        return mAnimator.getStartDelay();
    }

    @Override
    public SDAnim addListener(Animator.AnimatorListener listener)
    {
        if (listener != null)
        {
            if (!containsListener(listener))
            {
                mAnimator.addListener(listener);
            }
        }
        return this;
    }

    private boolean containsListener(Animator.AnimatorListener listener)
    {
        if (mAnimator.getListeners() != null)
        {
            return mAnimator.getListeners().contains(listener);
        } else
        {
            return false;
        }
    }

    @Override
    public SDAnim removeListener(Animator.AnimatorListener listener)
    {
        mAnimator.removeListener(listener);
        return this;
    }

    @Override
    public SDAnim clearListener()
    {
        if (mAnimator.getListeners() != null)
        {
            mAnimator.getListeners().clear();
        }
        return this;
    }

    @Override
    public void start()
    {
        mAnimator.start();
    }

    @Override
    public boolean isRunning()
    {
        return mAnimator.isRunning();
    }

    @Override
    public boolean isStarted()
    {
        return mAnimator.isStarted();
    }

    @Override
    public void cancel()
    {
        mAnimator.cancel();
    }

    @Override
    public SDAnim x(float... values)
    {
        mAnimator.setPropertyName(X);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim y(float... values)
    {
        mAnimator.setPropertyName(Y);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim translationX(float... values)
    {
        mAnimator.setPropertyName(TRANSLATION_X);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim translationY(float... values)
    {
        mAnimator.setPropertyName(TRANSLATION_Y);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim alpha(float... values)
    {
        mAnimator.setPropertyName(ALPHA);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim scaleX(float... values)
    {
        mAnimator.setPropertyName(SCALE_X);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim scaleY(float... values)
    {
        mAnimator.setPropertyName(SCALE_Y);
        mAnimator.setFloatValues(values);
        return this;
    }

    @Override
    public SDAnim rotation(float... values)
    {
        mAnimator.setPropertyName(ROTATION);
        mAnimator.setFloatValues(values);
        setLinear();
        return this;
    }

    @Override
    public SDAnim rotationX(float... values)
    {
        mAnimator.setPropertyName(ROTATION_X);
        mAnimator.setFloatValues(values);
        setLinear();
        return this;
    }

    @Override
    public SDAnim rotationY(float... values)
    {
        mAnimator.setPropertyName(ROTATION_Y);
        mAnimator.setFloatValues(values);
        setLinear();
        return this;
    }

    @Override
    public boolean isEmptyProperty()
    {
        String propertyName = mAnimator.getPropertyName();
        return TextUtils.isEmpty(propertyName) || "null".equals(propertyName);
    }

    /**
     * 设置动画为减速效果
     *
     * @return
     */
    public SDAnim setDecelerate()
    {
        setInterpolator(new DecelerateInterpolator());
        return this;
    }

    /**
     * 设置动画为加速效果
     *
     * @return
     */
    public SDAnim setAccelerate()
    {
        setInterpolator(new AccelerateInterpolator());
        return this;
    }

    /**
     * 设置动画为先加速后减速效果
     *
     * @return
     */
    public SDAnim setAccelerateDecelerate()
    {
        setInterpolator(new AccelerateDecelerateInterpolator());
        return this;
    }

    /**
     * 设置动画为匀速效果
     *
     * @return
     */
    public SDAnim setLinear()
    {
        setInterpolator(new LinearInterpolator());
        return this;
    }

    /**
     * 获得内部包装的ObjectAnimator对象
     *
     * @return
     */
    public ObjectAnimator get()
    {
        return mAnimator;
    }

    /**
     * 设置ObjectAnimator对象
     *
     * @param animator
     */
    public void setAnimator(ObjectAnimator animator)
    {
        mAnimator = animator;
    }

    @Override
    public SDAnim clone()
    {
        try
        {
            SDAnim clone = (SDAnim) super.clone();
            clone.setAnimator(mAnimator.clone());
            clone.clearListener();
            clone.addListener(new OnStartVisible());
            return clone;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //----------extend start----------

    private void updateTargetLocation()
    {
        if (getTarget() == null)
        {
            return;
        }
        if (mTargetLocation == null)
        {
            mTargetLocation = new int[]{0, 0};
        }
        getTarget().getLocationOnScreen(mTargetLocation);
    }

    private void updateTempLocation(View view)
    {
        if (view == null)
        {
            return;
        }
        if (mTempLocation == null)
        {
            mTempLocation = new int[]{0, 0};
        }
        view.getLocationOnScreen(mTempLocation);
    }

    /**
     * 移动到屏幕x坐标
     *
     * @param values
     * @return
     */
    public SDAnim moveToX(float... values)
    {
        if (values != null && values.length > 0)
        {
            updateTargetLocation();

            float[] realValues = new float[values.length];
            for (int i = 0; i < values.length; i++)
            {
                float real = (values[i] - mTargetLocation[0]) + getTarget().getTranslationX();
                realValues[i] = real;
            }
            return translationX(realValues);
        } else
        {
            return translationX(values);
        }
    }

    /**
     * 移动到屏幕y坐标
     *
     * @param values
     * @return
     */
    public SDAnim moveToY(float... values)
    {
        if (values != null && values.length > 0)
        {
            updateTargetLocation();

            float[] realValues = new float[values.length];
            realValues[0] = getTarget().getTranslationY();
            for (int i = 0; i < values.length; i++)
            {
                float real = (values[i] - mTargetLocation[1]) + getTarget().getTranslationY();
                realValues[i] = real;
            }
            return translationY(realValues);
        } else
        {
            return translationY(values);
        }
    }

    /**
     * 移动到view的屏幕x坐标
     *
     * @param views
     * @return
     */
    public SDAnim moveToX(View... views)
    {
        if (views != null && views.length > 0)
        {
            float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                View view = views[i];
                updateTempLocation(view);
                values[i] = mTempLocation[0];

                if (mAlignType == AlignType.Center)
                {
                    int delta = view.getWidth() / 2 - getTarget().getWidth() / 2;
                    float result = mTempLocation[0] + delta;
                    values[i] = result;
                }
            }
            return moveToX(values);
        } else
        {
            return this;
        }
    }

    /**
     * 移动到view的屏幕y坐标
     *
     * @param views
     * @return
     */
    public SDAnim moveToY(View... views)
    {
        if (views != null && views.length > 0)
        {
            float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                View view = views[i];
                updateTempLocation(view);
                values[i] = mTempLocation[1];

                if (mAlignType == AlignType.Center)
                {
                    int delta = view.getHeight() / 2 - getTarget().getHeight() / 2;
                    float result = mTempLocation[1] + delta;
                    values[i] = result;
                }
            }
            return moveToY(values);
        } else
        {
            return this;
        }
    }

    /**
     * 缩放x到views的宽度
     *
     * @param views
     * @return
     */
    public SDAnim scaleX(View... views)
    {
        if (views != null && views.length > 0)
        {
            float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                View view = views[i];
                float value = ((float) view.getWidth()) / ((float) getTarget().getWidth());
                values[i] = value;
            }
            return scaleX(values);
        } else
        {
            return this;
        }
    }

    /**
     * 缩放y到views的高度
     *
     * @param views
     * @return
     */
    public SDAnim scaleY(View... views)
    {
        if (views != null && views.length > 0)
        {
            float[] values = new float[views.length];
            for (int i = 0; i < views.length; i++)
            {
                View view = views[i];
                float value = ((float) view.getHeight()) / ((float) getTarget().getHeight());
                values[i] = value;
            }
            return scaleY(values);
        } else
        {
            return this;
        }
    }

    /**
     * 设置对齐方式，仅对{@link #moveToX(View...)}和{@link #moveToY(View...)}有效
     *
     * @param alignType
     * @return
     */
    public SDAnim setAlignType(AlignType alignType)
    {
        if (alignType != null)
        {
            mAlignType = alignType;
        }
        return this;
    }

    public enum AlignType
    {
        /**
         * View左上角对齐
         */
        TopLeft,
        /**
         * View中间对齐
         */
        Center
    }

    //----------extend end----------
}
