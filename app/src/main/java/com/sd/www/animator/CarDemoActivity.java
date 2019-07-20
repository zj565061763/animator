package com.sd.www.animator;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.sd.lib.animator.AnimatorChain;
import com.sd.lib.animator.FNodeAnimator;
import com.sd.lib.animator.listener.FAnimatorListener;
import com.sd.lib.animator.listener.api.OnEndInvisible;
import com.sd.lib.animator.listener.api.OnEndReset;

public class CarDemoActivity extends AppCompatActivity
{
    public static final String TAG = CarDemoActivity.class.getSimpleName();

    private View fl_down_car;
    private ImageView iv_down_car_front_tyre;
    private ImageView iv_down_car_back_tyre;

    private View fl_up_car;
    private ImageView iv_up_car_front_tyre;
    private ImageView iv_up_car_back_tyre;

    private AnimatorChain mAnimatorChain;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_demo);

        fl_down_car = findViewById(R.id.fl_car_down);
        iv_down_car_front_tyre = findViewById(R.id.iv_car_down_front_tyre);
        iv_down_car_back_tyre = findViewById(R.id.iv_car_down_back_tyre);

        fl_up_car = findViewById(R.id.fl_car_up);
        iv_up_car_front_tyre = findViewById(R.id.iv_car_up_front_tyre);
        iv_up_car_back_tyre = findViewById(R.id.iv_car_up_back_tyre);
    }

    public void onclickStart(View v)
    {
        if (mAnimatorChain != null)
        {
            if (!mAnimatorChain.isRunning())
                mAnimatorChain.start();
            return;
        }

        //汽车下来
        int carDownX1 = getScreenWidth();
        int carDownX2 = getScreenWidth() / 2 - fl_down_car.getWidth() / 2;
        int carDownX3 = -fl_down_car.getWidth();

        int carDownY1 = -fl_down_car.getHeight();
        int carDownY2 = getScreenHeight() / 2 - fl_down_car.getHeight() / 2;
        int carDownY3 = getScreenHeight();

        //汽车上去
        int carUpX1 = -fl_down_car.getWidth();
        int carUpX2 = getScreenWidth() / 2 - fl_down_car.getWidth() / 2;
        int carUpX3 = getScreenWidth();

        int carUpY1 = getScreenHeight();
        int carUpY2 = getScreenHeight() / 2 - fl_down_car.getHeight() / 2;
        int carUpY3 = -fl_down_car.getHeight();

        mAnimatorChain = new FNodeAnimator(iv_down_car_front_tyre).chain().setDebug(true);

        mAnimatorChain.currentNode()
                .rotation(-360).setRepeatCount(-1).setDuration(1000)
                .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("下-前轮旋转")
                .withClone().setTarget(iv_down_car_back_tyre)
                .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("下-后轮旋转")

                .with().setTarget(fl_down_car).moveX(carDownX1, carDownX2)
                .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setDesc("X右上角移动到屏幕中央")
                .withClone().moveY(carDownY1, carDownY2).setDesc("Y右上角移动到屏幕中央")

                .next().setDuration(500).setDesc("屏幕中央停止500毫秒")

                .next().moveX(carDownX2, carDownX3)
                .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setDesc("X屏幕中央移动到左下角")
                .withClone().moveY(carDownY2, carDownY3)
                .addListener(new OnEndInvisible(), new OnEndReset()).setDesc("Y屏幕中央移动到左下角")

                .next().setTarget(iv_up_car_front_tyre).rotation(360).setRepeatCount(-1).setDuration(1000)
                .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("上-前轮旋转")
                .withClone().setTarget(iv_up_car_back_tyre)
                .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("上-后轮旋转")

                .with().setTarget(fl_up_car).moveX(carUpX1, carUpX2)
                .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setDesc("X左下角移动到屏幕中央")
                .withClone().moveY(carUpY1, carUpY2).setDesc("Y左下角移动到屏幕中央")

                .next().setDuration(500).setDesc("屏幕中央停止500毫秒")

                .next().moveX(carUpX2, carUpX3)
                .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setDesc("X屏幕中央移动到右上角")
                .withClone().moveY(carUpY2, carUpY3).setDesc("Y屏幕中央移动到右上角")
                .addListener(new OnEndInvisible(fl_up_car), new OnEndReset(fl_up_car), new FAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        // 最后一个动画执行完成后，停止动画链，要不然轮子无限旋转，判断的时候动画链一直处于运行中
                        mAnimatorChain.cancel();
                    }
                })
                .chain().start();
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public int getScreenWidth()
    {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @return
     */
    public int getScreenHeight()
    {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
