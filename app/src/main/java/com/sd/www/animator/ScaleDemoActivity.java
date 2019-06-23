package com.sd.www.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.animator.FNodeAnimator;

public class ScaleDemoActivity extends AppCompatActivity
{
    private boolean mIsScaled = false;

    private View view_root, fl_video, fl_video_target;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_demo);
        view_root = findViewById(R.id.view_root);
        fl_video = findViewById(R.id.fl_video);
        fl_video_target = findViewById(R.id.fl_video_target);

        fl_video.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIsScaled = !mIsScaled;

                if (mIsScaled)
                    scale();
                else
                    restore();

            }
        });
    }

    private void scale()
    {
        new FNodeAnimator(fl_video)
                .moveXToView(0, fl_video_target)
                .chain().start();


//        new FNodeAnimator(fl_video)
//                .moveXToView(0, fl_video_target)
//                .with().moveYTo(new YCenterAligner(), fl_video_target)
//                .with().scaleXToView(fl_video_target)
//                .with().scaleY(fl_video_target)
//                .chain().start();
    }

    private void restore()
    {
        new FNodeAnimator(fl_video)
                .moveXToView(0, view_root)
                .chain().start();


//        new FNodeAnimator(fl_video)
//                .moveXToView(0, view_root)
//                .with().moveYTo(new YCenterAligner(), view_root)
//                .with().scaleXToView(view_root)
//                .with().scaleY(view_root)
//                .chain().start();
    }
}
