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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PopImageView extends ImageView
{
    private final FrameLayout mFrameLayout;

    public PopImageView(Context context)
    {
        super(context);
        if (!(context instanceof Activity))
        {
            throw new IllegalArgumentException("context must be instance of Activity");
        }
        mFrameLayout = ((Activity) context).findViewById(android.R.id.content);
    }

    /**
     * 设置要截图的view
     *
     * @param view
     * @return
     */
    public void setDrawingCacheView(View view)
    {
        Bitmap bitmap = createViewBitmap(view);
        setImageBitmap(bitmap);
    }

    /**
     * 依附到目标view
     *
     * @param target
     */
    public void attachTarget(View target)
    {
        removeSelf();

        final int[] locationTarget = {0, 0};
        target.getLocationOnScreen(locationTarget);

        final int[] locationContainer = {0, 0};
        mFrameLayout.getLocationOnScreen(locationContainer);

        final int left = locationTarget[0] - locationContainer[0];
        final int top = locationTarget[1] - locationContainer[1];

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        params.leftMargin = left;
        params.topMargin = top;

        final ViewGroup.LayoutParams paramsTarget = target.getLayoutParams();
        if (paramsTarget != null)
        {
            params.width = paramsTarget.width;
            params.height = paramsTarget.height;
        }

        setLayoutParams(params);
        mFrameLayout.addView(this);
    }

    private void removeSelf()
    {
        try
        {
            ((ViewGroup) getParent()).removeView(this);
        } catch (Exception e)
        {
        }
    }

    private static Bitmap createViewBitmap(View view)
    {
        if (view == null)
        {
            return null;
        }
        view.setDrawingCacheEnabled(true);
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null)
        {
            return null;
        }

        Bitmap bmp = Bitmap.createBitmap(drawingCache);
        view.destroyDrawingCache();
        return bmp;
    }
}
