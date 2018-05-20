package com.fanwe.www.animator;

import android.animation.Animator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.lib.animator.AnimatorChain;
import com.fanwe.lib.animator.FNodeAnimator;
import com.fanwe.lib.animator.listener.FAnimatorListener;
import com.fanwe.lib.animator.listener.api.OnEndInvisible;
import com.fanwe.lib.animator.listener.api.OnEndReset;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RocketDemoActivity extends AppCompatActivity
{
    private View fl_rocket_root;
    private TextView tv_number;
    private ImageView iv_rocket;
    private ImageView iv_rocket_smoke;

    private AnimatorChain mAnimatorChain;
    private int mNumber = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_demo);

        fl_rocket_root = findViewById(R.id.fl_rocket_root);
        tv_number = findViewById(R.id.tv_number);
        iv_rocket = findViewById(R.id.iv_rocket);
        iv_rocket_smoke = findViewById(R.id.iv_rocket_smoke);
    }

    public void onclickStartRocket(View v)
    {
        if (mAnimatorChain != null && mAnimatorChain.isStarted())
        {
            return;
        }

        /**
         * true-调试模式，会输出整个动画链的结构，方便开发调试，可以给每个节点动画设置tag，来加强描述
         *
         * demo中的日志输入如下：
         *
         *    Head:火箭淡入
         *    Next:延迟500毫秒
         *    Next:开始数字缩放X With:开始数字缩放Y
         *    Next:火箭起飞 With:烟雾淡入
         *    Next:烟雾淡出
         *
         */
        mAnimatorChain = new FNodeAnimator(true)
                .setTarget(fl_rocket_root)
                .alpha(0, 1f).setDuration(500).setTag("火箭淡入")
                .chain().nodeNext().setDuration(500).setTag("延迟500毫秒")
                .chain().nodeNext().setTarget(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000).setTag("开始数字缩放X")
                .chain().nodeWith(true).scaleY(1f, 0f).setTag("开始数字缩放Y")
                .addListener(new FAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        tv_number.setText(String.valueOf(mNumber));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        mNumber = 3;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation)
                    {
                        super.onAnimationRepeat(animation);
                        mNumber--;
                        tv_number.setText(String.valueOf(mNumber));
                    }
                })
                .chain().nodeNext().setTarget(fl_rocket_root).translationY(0, -getResources().getDisplayMetrics().heightPixels).setTag("火箭起飞")
                .setDuration(3000).setInterpolator(new AccelerateInterpolator())
                .addListener(new OnEndInvisible(fl_rocket_root)) //动画结束隐藏fl_rocket_root
                .addListener(new OnEndReset(fl_rocket_root)) //动画结束重置fl_rocket_root
                .addListener(new FAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        AnimationDrawable animationDrawable = (AnimationDrawable) iv_rocket.getDrawable();
                        animationDrawable.start(); //开始火箭喷火动画
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        AnimationDrawable animationDrawable = (AnimationDrawable) iv_rocket.getDrawable();
                        animationDrawable.stop(); //停止火箭喷火动画
                    }
                })
                .chain().nodeWith().setTarget(iv_rocket_smoke).alpha(0, 1f).setDuration(3000).setStartDelay(500).setTag("烟雾淡入")
                .chain().nodeNext().alpha(1f, 0).setDuration(500).setTag("烟雾淡出")
                .addListener(new OnEndInvisible(iv_rocket_smoke)) //动画结束隐藏烟雾
                .chain().start();
    }
}
