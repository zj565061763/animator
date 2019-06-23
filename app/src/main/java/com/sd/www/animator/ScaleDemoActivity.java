package com.sd.www.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.animator.FNodeAnimator;

public class ScaleDemoActivity extends AppCompatActivity
{
    private boolean mScale = false;
    private View fl_video, fl_video_target, fl_video_target_1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_demo);
        fl_video = findViewById(R.id.fl_video);
        fl_video_target = findViewById(R.id.fl_video_target);
        fl_video_target_1 = findViewById(R.id.fl_video_target_1);

        fl_video.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mScale = !mScale;

                if (mScale)
                    scale();
                else
                    restore();

            }
        });
    }

    private void scale()
    {
        new FNodeAnimator(fl_video)
                .scaleXToView(fl_video_target).with().scaleYToView(fl_video_target)
                .with().configMoveXToView()
                .newView(fl_video_target).setFutureScale(fl_video_target)
                .node()
                .with().configMoveYToView()
                .newView(fl_video_target).setFutureScale(fl_video_target)
                .node()
                .chain().start();
    }

    private void restore()
    {
        new FNodeAnimator(fl_video)
                .scaleXToView(fl_video_target_1).with().scaleYToView(fl_video_target_1)
                .with().configMoveXToView()
                .newView(fl_video_target_1).setFutureScale(fl_video_target_1)
                .node()
                .with().configMoveYToView()
                .newView(fl_video_target_1).setFutureScale(fl_video_target_1)
                .node()
                .chain().start();
    }
}
