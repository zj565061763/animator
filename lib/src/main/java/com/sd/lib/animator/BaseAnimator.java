package com.sd.lib.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sd.lib.animator.listener.api.OnStartVisible;

import java.util.ArrayList;

class BaseAnimator<T extends PropertyAnimator> implements PropertyAnimator<T>
{
    private ObjectAnimator mObjectAnimator = new ObjectAnimator();

    public BaseAnimator()
    {
        final PropertyValuesHolder[] valuesHolders = mObjectAnimator.getValues();
        if (valuesHolders == null || valuesHolders.length <= 0)
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
    public T addListener(Animator.AnimatorListener... listeners)
    {
        if (listeners != null)
        {
            for (Animator.AnimatorListener item : listeners)
            {
                mObjectAnimator.addListener(item);
            }
        }
        return (T) this;
    }

    @Override
    public T removeListener(Animator.AnimatorListener... listeners)
    {
        if (listeners != null)
        {
            for (Animator.AnimatorListener item : listeners)
            {
                mObjectAnimator.removeListener(item);
            }
        }
        return (T) this;
    }

    @Override
    public ArrayList<Animator.AnimatorListener> getListeners()
    {
        return mObjectAnimator.getListeners();
    }

    @Override
    public T clearListener()
    {
        final ArrayList<Animator.AnimatorListener> listeners = getListeners();
        if (listeners != null)
            listeners.clear();

        return (T) this;
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

    @Override
    public T start()
    {
        mObjectAnimator.start();
        return (T) this;
    }

    @Override
    public T startAsPop(boolean clone)
    {
        final View target = getTarget();
        if (target != null && (target.getContext() instanceof Activity))
        {
            final PopImageView imageView = new PopImageView(target.getContext());
            imageView.setDrawingCacheView(target);
            imageView.attachTarget(target);
            imageView.setVisibility(target.isShown() ? View.VISIBLE : View.INVISIBLE);

            final T animator = clone ? clone() : (T) this;
            animator.setTarget(imageView).start();

            return animator;
        }
        return null;
    }

    //---------- Animator End ----------

    //---------- PropertyAnimator Start ----------

    @Override
    public T alpha(float... values)
    {
        setPropertyName(ALPHA);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T x(float... values)
    {
        setPropertyName(X);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T y(float... values)
    {
        setPropertyName(Y);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T translationX(float... values)
    {
        setPropertyName(TRANSLATION_X);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T translationY(float... values)
    {
        setPropertyName(TRANSLATION_Y);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T scaleX(float... values)
    {
        setPropertyName(SCALE_X);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T scaleY(float... values)
    {
        setPropertyName(SCALE_Y);
        setFloatValues(values);
        return (T) this;
    }

    @Override
    public T rotation(float... values)
    {
        setPropertyName(ROTATION);
        setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
    }

    @Override
    public T rotationX(float... values)
    {
        setPropertyName(ROTATION_X);
        setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
    }

    @Override
    public T rotationY(float... values)
    {
        setPropertyName(ROTATION_Y);
        setFloatValues(values);
        setInterpolator(new LinearInterpolator());
        return (T) this;
    }

    @Override
    public T setFloatValues(float... values)
    {
        mObjectAnimator.setFloatValues(values);
        return (T) this;
    }

    @Override
    public T setPropertyName(String propertyName)
    {
        if (!TextUtils.isEmpty(propertyName))
        {
            if (propertyName.equals(ALPHA)
                    || propertyName.equals(X) || propertyName.equals(Y)
                    || propertyName.equals(TRANSLATION_X) || propertyName.equals(TRANSLATION_Y)
                    || propertyName.equals(SCALE_X) || propertyName.equals(SCALE_Y)
                    || propertyName.equals(ROTATION) || propertyName.equals(ROTATION_X) || propertyName.equals(ROTATION_Y))
            {
                mObjectAnimator.setPropertyName(propertyName);
            } else
            {
                throw new IllegalArgumentException("Illegal propertyName:" + propertyName);
            }
        }
        return (T) this;
    }

    @Override
    public String getPropertyName()
    {
        final String propertyName = mObjectAnimator.getPropertyName();
        if (TextUtils.isEmpty(propertyName) || "null".equals(propertyName))
            return null;

        return propertyName;
    }

    @Override
    public T setRepeatCount(int count)
    {
        if (count < 0)
            count = ValueAnimator.INFINITE;

        mObjectAnimator.setRepeatCount(count);
        return (T) this;
    }

    @Override
    public int getRepeatCount()
    {
        return mObjectAnimator.getRepeatCount();
    }

    @Override
    public ObjectAnimator toObjectAnimator()
    {
        return mObjectAnimator;
    }

    @Override
    public T clone()
    {
        try
        {
            final BaseAnimator clone = (BaseAnimator) super.clone();
            clone.mObjectAnimator = mObjectAnimator.clone();
            return (T) clone;
        } catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //---------- PropertyAnimator End ----------
}
