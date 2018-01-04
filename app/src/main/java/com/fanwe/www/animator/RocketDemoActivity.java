package com.fanwe.www.animator;

import android.animation.Animator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.lib.animator.FAnimatorSet;
import com.fanwe.lib.animator.listener.OnEndInvisible;
import com.fanwe.lib.animator.listener.OnEndReset;
import com.fanwe.lib.animator.listener.FAnimatorListener;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RocketDemoActivity extends AppCompatActivity
{
    private View fl_rocket_root;
    private TextView tv_number;
    private ImageView iv_rocket;
    private ImageView iv_rocket_smoke;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_demo);

        fl_rocket_root = findViewById(R.id.fl_rocket_root);
        tv_number = (TextView) findViewById(R.id.tv_number);
        iv_rocket = (ImageView) findViewById(R.id.iv_rocket);
        iv_rocket_smoke = (ImageView) findViewById(R.id.iv_rocket_smoke);
    }

    private FAnimatorSet mAnimSet;
    private int number = 3;

    public void onclickStartRocket(View v)
    {
        if (mAnimSet != null && mAnimSet.getSet().isStarted())
        {
            return;
        }

        mAnimSet = FAnimatorSet.from(fl_rocket_root)
                //火箭淡入
                .alpha(0, 1f).setDuration(1000)
                .delay(500)
                //数字倒数
                .with(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000)
                .withClone().scaleY(1f, 0f)
                .addListener(new FAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        tv_number.setText(String.valueOf(number));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        number = 3;
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation)
                    {
                        super.onAnimationRepeat(animation);
                        number--;
                        tv_number.setText(String.valueOf(number));
                    }
                })
                //火箭起飞
                .next(fl_rocket_root).translationY(0, -getResources().getDisplayMetrics().heightPixels)
                .setDuration(3000).setAccelerate()
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
                //烟雾淡入
                .with(iv_rocket_smoke).alpha(0, 1f).setDuration(3000).setStartDelay(500)
                //烟雾淡出
                .next().alpha(1f, 0).setDuration(500)
                .addListener(new OnEndInvisible(iv_rocket_smoke));
        mAnimSet.start();

    }


}
