package com.fanwe.www.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.fanwe.lib.animator.AnimatorChain;
import com.fanwe.lib.animator.SimpleNodeAnimator;
import com.fanwe.lib.animator.listener.api.OnEndInvisible;
import com.fanwe.lib.animator.listener.api.OnEndReset;

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
        if (mAnimatorChain != null && mAnimatorChain.isRunning())
        {
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

        mAnimatorChain = new SimpleNodeAnimator(iv_down_car_front_tyre).chain().setDebug(true);

        mAnimatorChain.currentNode()
                //轮胎旋转
                .rotation(-360).setRepeatCount(-1).setDuration(1000).setTag("下-前轮旋转")
                .withClone().setTarget(iv_down_car_back_tyre)
                //右上角移动到屏幕中央
                .with().setTarget(fl_down_car).moveToX(carDownX1, carDownX2).setDuration(1500).setInterpolator(new DecelerateInterpolator())
                .withClone().moveToY(carDownY1, carDownY2)
                .next().setDuration(500)
                //屏幕中央移动到左下角
                .next().moveToX(carDownX2, carDownX3).setDuration(1500).setInterpolator(new AccelerateInterpolator())
                .withClone().moveToY(carDownY2, carDownY3)
                .addListener(new OnEndInvisible(fl_down_car))
                .addListener(new OnEndReset(fl_down_car))
                //汽车上去
                .next().setTarget(iv_up_car_front_tyre)
                //轮胎旋转
                .rotation(360).setRepeatCount(-1).setDuration(1000).withClone().setTarget(iv_up_car_back_tyre)
                //左下角移动到屏幕中央
                .with().setTarget(fl_up_car).moveToX(carUpX1, carUpX2).setDuration(1500).setInterpolator(new DecelerateInterpolator())
                .withClone().moveToY(carUpY1, carUpY2)
                .next().setDuration(500)
                //屏幕中央移动到右上角
                .next().moveToX(carUpX2, carUpX3).setDuration(1500).setInterpolator(new AccelerateInterpolator())
                .withClone().moveToY(carUpY2, carUpY3)
                .addListener(new OnEndInvisible(fl_up_car), new OnEndReset(fl_up_car))
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
