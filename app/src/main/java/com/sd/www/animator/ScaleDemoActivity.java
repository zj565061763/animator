package com.sd.www.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.animator.FNodeAnimator;
import com.sd.lib.animator.mtv.pshifter.AlignCenterPositionShifter;

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
                    move();

            }
        });
    }

    private void scale()
    {
        new FNodeAnimator(fl_video)
                .scaleXToView(fl_video_target) // 缩放到目标view的x
                .with().scaleYToView(fl_video_target) // 缩放到目标view的y
                .with().moveXToView() // 配置移动到目标view的x
                .newTarget(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
                .node() // 返回node节点
                .with().moveYToView() // 配置移动到目标view的y
                .newTarget(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
                .node() // 返回node节点
                .chain().start(); // 开始整个链条动画
    }

    private void move()
    {
        new FNodeAnimator(fl_video)
                .moveXToView()
                .newTarget(fl_video_target_1).setPositionShifter(new AlignCenterPositionShifter()) // 配置位置转移器，设置动画View和目标View水平方向中心对齐
                .node()
                .with().moveYToView()
                .newTarget(fl_video_target_1).setPositionShifter(new AlignCenterPositionShifter())  // 配置位置转移器，设置动画View和目标View竖直方向中心对齐
                .node()
                .chain().start();
    }
}
