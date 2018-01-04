package com.fanwe.www.animator;

import android.animation.Animator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.lib.animator.FAnimatorSet;
import com.fanwe.lib.animator.listener.FAnimatorListener;
import com.fanwe.lib.animator.listener.OnEndInvisible;
import com.fanwe.lib.animator.listener.OnEndReset;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RocketDemoActivity extends AppCompatActivity
{
    private View fl_rocket_root;
    private TextView tv_number;
    private ImageView iv_rocket;
    private ImageView iv_rocket_smoke;

    private FAnimatorSet mAnimatorSet;
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
        if (mAnimatorSet != null && mAnimatorSet.getSet().isStarted())
        {
            return;
        }

        mAnimatorSet = new FAnimatorSet(fl_rocket_root);
        //火箭淡入
        mAnimatorSet.alpha(0, 1f).setDuration(1000)
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
        mAnimatorSet.start();

    }


}
