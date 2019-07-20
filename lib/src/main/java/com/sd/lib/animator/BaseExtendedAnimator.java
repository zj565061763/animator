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
    public T screenX(float... values)
    {
        screenInternal(true, values);
        return (T) this;
    }

    @Override
    public T screenY(float... values)
    {
        screenInternal(false, values);
        return (T) this;
    }

    @Override
    public T scaleXToView(View... views)
    {
        scaleToViewInternal(true, views);
        return (T) this;
    }

    @Override
    public T scaleYToView(View... views)
    {
        scaleToViewInternal(false, views);
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

    private void screenInternal(final boolean horizontal, final float... values)
    {
        checkTarget(getTarget());

        if (values == null || values.length <= 0)
            return;

        final LocationValue locationValue = horizontal ? new ScreenXValue(null) : new ScreenYValue(null);
        final Float targetLocation = locationValue.getValue(getTarget());
        if (targetLocation == null)
            return;

        final float[] realValues = new float[values.length];
        for (int i = 0; i < values.length; i++)
        {
            if (horizontal)
                realValues[i] = (values[i] - targetLocation) + getTarget().getTranslationX();
            else
                realValues[i] = (values[i] - targetLocation) + getTarget().getTranslationY();
        }

        if (horizontal)
            translationX(realValues);
        else
            translationY(realValues);
    }

    private void scaleToViewInternal(final boolean horizontal, final View... views)
    {
        checkTarget(getTarget());

        if (views == null || views.length <= 0)
            return;

        final ScaleValueTransform transform = horizontal ? new ScaleXTransform() : new ScaleYTransform();

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

        if (horizontal)
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
}
