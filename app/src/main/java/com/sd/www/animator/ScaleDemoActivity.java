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
                .scaleXToView(fl_video_target) // 缩放到目标view的x
                .with().scaleYToView(fl_video_target) // 缩放到目标view的y
                .with().configMoveToViewX() // 配置移动到目标view的x
                .newView(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
                .node() // 返回node节点
                .with().configMoveToViewY() // 配置移动到目标view的y
                .newView(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
                .node() // 返回node节点
                .chain().start(); // 开始整个链条动画
    }

    private void restore()
    {
        new FNodeAnimator(fl_video)
                .scaleXToView(fl_video_target_1).with().scaleYToView(fl_video_target_1)
                .with().configMoveToViewX()
                .newView(fl_video_target_1).setFutureScale(fl_video_target_1)
                .node()
                .with().configMoveToViewY()
                .newView(fl_video_target_1).setFutureScale(fl_video_target_1)
                .node()
                .chain().start();
    }
}
