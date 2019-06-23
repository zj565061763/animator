package com.sd.lib.animator;

import android.view.View;

import com.sd.lib.animator.provider.property.location.LocationValue;
import com.sd.lib.animator.provider.property.location.ScreenXValue;
import com.sd.lib.animator.provider.property.location.ScreenYValue;
import com.sd.lib.animator.provider.transform.scale.ScaleValueTransform;
import com.sd.lib.animator.provider.transform.scale.ScaleXTransform;
import com.sd.lib.animator.provider.transform.scale.ScaleYTransform;

import java.util.ArrayList;
import java.util.List;

class BaseExtendedAnimator<T extends ExtendedPropertyAnimator> extends BaseAnimator<T> implements ExtendedPropertyAnimator<T>
{
    private String mDesc;

    @Override
    public T moveToX(float... values)
    {
        moveToInternal(Coordinate.X, values);
        return (T) this;
    }

    @Override
    public T moveToY(float... values)
    {
        moveToInternal(Coordinate.Y, values);
        return (T) this;
    }

    @Override
    public T scaleToViewX(View... views)
    {
        scaleToViewInternal(Coordinate.X, views);
        return (T) this;
    }

    @Override
    public T scaleToViewY(View... views)
    {
        scaleToViewInternal(Coordinate.Y, views);
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

    private void moveToInternal(final Coordinate coordinate, final float... values)
    {
        checkCoordinate(coordinate);
        checkTarget(getTarget());

        if (values == null || values.length <= 0)
            return;

        final LocationValue locationValue = coordinate == Coordinate.X ? new ScreenXValue(null) : new ScreenYValue(null);
        final Float targetLocation = locationValue.getValue(getTarget());
        if (targetLocation == null)
            return;

        final float[] realValues = new float[values.length];
        for (int i = 0; i < values.length; i++)
        {
            if (coordinate == Coordinate.X)
                realValues[i] = (values[i] - targetLocation) + getTarget().getTranslationX();
            else
                realValues[i] = (values[i] - targetLocation) + getTarget().getTranslationY();
        }

        if (coordinate == Coordinate.X)
            translationX(realValues);
        else
            translationY(realValues);
    }

    private void scaleToViewInternal(final Coordinate coordinate, final View... views)
    {
        checkCoordinate(coordinate);
        checkTarget(getTarget());

        if (views == null || views.length <= 0)
            return;

        final ScaleValueTransform transform = coordinate == Coordinate.X ? new ScaleXTransform() : new ScaleYTransform();

        final List<Float> list = new ArrayList<>(views.length);
        for (int i = 0; i < views.length; i++)
        {
            final Float value = transform.getValue(getTarget(), views[i]);
            if (value != null)
                list.add(value);
        }

        final float[] values = listToValue(list);
        if (values == null)
            return;

        if (coordinate == Coordinate.X)
            scaleX(values);
        else
            scaleY(values);
    }

    protected static float[] listToValue(List<Float> list)
    {
        if (list == null || list.isEmpty())
            return null;

        final int count = list.size();
        final float[] values = new float[count];
        for (int i = 0; i < count; i++)
        {
            values[i] = list.get(i);
        }
        return values;
    }

    private static void checkTarget(View target)
    {
        if (target == null)
            throw new NullPointerException("target view must be provided before this, see the Animator.setTarget(View) method");
    }

    private static void checkCoordinate(Coordinate coordinate)
    {
        if (coordinate == null)
            throw new IllegalArgumentException("coordinate is null");
    }

    enum Coordinate
    {
        X, Y
    }
}
