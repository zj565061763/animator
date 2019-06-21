package com.sd.lib.animator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sd.lib.animator.listener.api.OnStartVisible;

import java.util.ArrayList;
import java.util.List;

/**
 * 对ObjectAnimator进行封装提供更方便的调用方法
 */
abstract class BaseAnimator<T extends ExtendedPropertyAnimator> implements ExtendedPropertyAnimator<T>
{
    private ObjectAnimator mObjectAnimator = new ObjectAnimator();
    private String mDesc;

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

    //---------- ExtendedPropertyAnimator Start ----------

    @Override
    public T moveToX(float... values)
    {
        moveTo(Coordinate.X, values);
        return (T) this;
    }

    @Override
    public T moveToY(float... values)
    {
        moveTo(Coordinate.Y, values);
        return (T) this;
    }

    @Override
    public T moveToX(Aligner aligner, View... views)
    {
        moveTo(Coordinate.X, aligner, views);
        return (T) this;
    }

    @Override
    public T moveToY(Aligner aligner, View... views)
    {
        moveTo(Coordinate.Y, aligner, views);
        return (T) this;
    }

    @Override
    public T scaleX(View... views)
    {
        scale(Coordinate.X, views);
        return (T) this;
    }

    @Override
    public T scaleY(View... views)
    {
        scale(Coordinate.Y, views);
        return (T) this;
    }

    @Override
    public T setDesc(String desc)
    {
        mDesc = desc;
        return (T) this;
    }

    @Override
    public String getDesc()
    {
        if (mDesc == null)
            mDesc = "";
        return mDesc;
    }

    private int[] mTempLocation;

    private void saveTempLocation(View view)
    {
        if (mTempLocation == null)
            mTempLocation = new int[]{0, 0};

        view.getLocationOnScreen(mTempLocation);
    }

    private void moveTo(Coordinate coordinate, float... values)
    {
        checkCoordinate(coordinate);
        checkTarget();

        if (values == null || values.length <= 0)
            return;

        final int[] location = new int[2];
        getTarget().getLocationOnScreen(location);

        final float[] realValues = new float[values.length];
        for (int i = 0; i < values.length; i++)
        {
            if (coordinate == Coordinate.X)
                realValues[i] = (values[i] - location[0]) + getTarget().getTranslationX();
            else
                realValues[i] = (values[i] - location[1]) + getTarget().getTranslationY();
        }

        if (coordinate == Coordinate.X)
            translationX(realValues);
        else
            translationY(realValues);
    }

    private void moveTo(Coordinate coordinate, Aligner aligner, View... views)
    {
        checkCoordinate(coordinate);
        checkTarget();

        if (views == null || views.length <= 0)
            return;

        if (aligner == null)
            aligner = Aligner.DEFAULT;

        final List<Float> list = new ArrayList<>();
        for (int i = 0; i < views.length; i++)
        {
            final View view = views[i];
            if (view == null)
                continue;

            saveTempLocation(view);
            if (coordinate == Coordinate.X)
            {
                float value = aligner.align(getTarget(), view, mTempLocation[0]);
                list.add(value);
            } else
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
                moveToX(values);
            else
                moveToY(values);
        }
    }

    private void scale(Coordinate coordinate, View... views)
    {
        checkCoordinate(coordinate);
        checkTarget();

        if (coordinate == Coordinate.X && getTarget().getWidth() <= 0)
            return;
        if (coordinate == Coordinate.Y && getTarget().getHeight() <= 0)
            return;

        if (views != null && views.length > 0)
        {
            final List<Float> list = new ArrayList<>();
            for (int i = 0; i < views.length; i++)
            {
                final View view = views[i];
                if (view == null)
                    continue;

                if (coordinate == Coordinate.X)
                {
                    float value = ((float) view.getWidth()) / getTarget().getWidth();
                    list.add(value);
                } else
                {
                    float value = ((float) view.getHeight()) / getTarget().getHeight();
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
                    scaleX(values);
                else
                    scaleY(values);
            }
        }
    }

    private void checkTarget()
    {
        if (getTarget() == null)
            throw new NullPointerException("target view must be provided before this, see the Animator.setTarget(View) method");
    }

    private void checkCoordinate(Coordinate coordinate)
    {
        if (coordinate == null)
            throw new NullPointerException("coordinate is null");
    }

    private enum Coordinate
    {
        X, Y
    }

    //---------- ExtendedPropertyAnimator End ----------
}
