package com.sd.lib.animator;

import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class BaseExtendedAnimator<T extends ExtendedPropertyAnimator> extends BaseAnimator<T> implements ExtendedPropertyAnimator<T>
{
    private String mDesc;

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
        moveToView(Coordinate.X, aligner, views);
        return (T) this;
    }

    @Override
    public T moveToY(Aligner aligner, View... views)
    {
        moveToView(Coordinate.Y, aligner, views);
        return (T) this;
    }

    @Override
    public T scaleX(View... views)
    {
        scaleToView(Coordinate.X, views);
        return (T) this;
    }

    @Override
    public T scaleY(View... views)
    {
        scaleToView(Coordinate.Y, views);
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

    private void moveTo(final Coordinate coordinate, final float... values)
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

    private void moveToView(final Coordinate coordinate, Aligner aligner, final View... views)
    {
        checkCoordinate(coordinate);
        checkTarget();

        if (views == null || views.length <= 0)
            return;

        if (aligner == null)
            aligner = Aligner.DEFAULT;

        final int[] location = new int[2];
        final List<Float> list = new ArrayList<>(views.length);
        for (int i = 0; i < views.length; i++)
        {
            final View view = views[i];
            if (!checkView(view))
                continue;

            view.getLocationOnScreen(location);
            if (coordinate == Coordinate.X)
            {
                float value = aligner.align(getTarget(), view, location[0]);
                list.add(value);
            } else
            {
                float value = aligner.align(getTarget(), view, location[1]);
                list.add(value);
            }
        }

        final int count = list.size();
        if (count <= 0)
            return;

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

    private void scaleToView(final Coordinate coordinate, final View... views)
    {
        checkCoordinate(coordinate);
        checkTarget();

        if (coordinate == Coordinate.X && getTarget().getWidth() <= 0)
            return;
        if (coordinate == Coordinate.Y && getTarget().getHeight() <= 0)
            return;

        if (views == null || views.length <= 0)
            return;

        final List<Float> list = new ArrayList<>(views.length);
        for (int i = 0; i < views.length; i++)
        {
            final View view = views[i];
            if (!checkView(view))
                continue;

            if (coordinate == Coordinate.X)
            {
                final float viewWidth = view.getWidth() * view.getScaleX();
                final float targetWidth = getTarget().getWidth() * getTarget().getScaleX();

                float value = viewWidth / targetWidth * getTarget().getScaleX();
                list.add(value);
            } else
            {
                final float viewHeight = view.getHeight() * view.getScaleY();
                final float targetHeight = getTarget().getHeight() * getTarget().getScaleY();

                float value = viewHeight / targetHeight * getTarget().getScaleY();
                list.add(value);
            }
        }

        final int count = list.size();
        if (count <= 0)
            return;

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


    private void checkTarget()
    {
        if (getTarget() == null)
            throw new NullPointerException("target view must be provided before this, see the Animator.setTarget(View) method");
    }

    private static void checkCoordinate(Coordinate coordinate)
    {
        if (coordinate == null)
            throw new IllegalArgumentException("coordinate is null");
    }

    private static boolean checkView(View view)
    {
        if (view == null)
            return false;

        if (view.getVisibility() == View.GONE)
            return false;

        final boolean isAttached = Build.VERSION.SDK_INT >= 19 ? view.isAttachedToWindow() : view.getWindowToken() != null;
        if (!isAttached)
            return false;

        return true;
    }

    private enum Coordinate
    {
        X, Y
    }
}
