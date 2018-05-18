package com.fanwe.www.animator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fanwe.lib.animator.FAnimatorSet;
import com.fanwe.lib.animator.aligner.XCenterAligner;
import com.fanwe.lib.animator.aligner.YCenterAligner;
import com.fanwe.lib.animator.listener.OnEndRemoveView;
import com.fanwe.lib.animator.listener.OnEndReset;

public class SimpleDemoActivity extends AppCompatActivity
{

    View view_target_1, view_target_2, view_target_3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        view_target_1 = findViewById(R.id.btn_target_1);
        view_target_2 = findViewById(R.id.btn_target_2);
        view_target_3 = findViewById(R.id.btn_target_3);

    }

    private final XCenterAligner mXCenterAligner = new XCenterAligner();
    private final YCenterAligner mYCenterAligner = new YCenterAligner();

    public void onClickBtnAnim(View v)
    {
        new FAnimatorSet().setTarget(v)
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000)//依次移动x到v, view_target_1, view_target_2, view_target_3
                .withClone().moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3) //依次移动y到v, view_target_1, view_target_2, view_target_3
                .delay(1000)
                .addListener(new OnEndReset()) //动画完成后重置view
                .start();
    }

    /**
     * 对target截图然后设置给ImageView，让ImageView镜像在android.R.id.content的FrameLayout里面执行动画，以此来突破父容器范围的局限
     *
     * @param v
     */
    public void onClickBtnAnimInside(View v)
    {
        new FAnimatorSet().setTarget(v)
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000) //依次移动x到v, view_target_1, view_target_2, view_target_3
                .withClone().moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3) //依次移动y到v, view_target_1, view_target_2, view_target_3
                .delay(1000)
                .addListener(new OnEndRemoveView()) //动画完成后移除view
                .startAsPop();
    }

}
