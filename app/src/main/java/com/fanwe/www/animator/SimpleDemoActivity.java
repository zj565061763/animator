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

    /**
     * x方向中心点对齐
     */
    private final XCenterAligner mXCenterAligner = new XCenterAligner();
    /**
     * y方向中心点对齐
     */
    private final YCenterAligner mYCenterAligner = new YCenterAligner();

    public void onClickBtnAnim(View v)
    {
        final FAnimatorSet animatorSet = new FAnimatorSet();

        animatorSet

                /**
                 * 设置要执行动画的view
                 */
                .setTarget(v)

                /**
                 * 设置动画view的x方向要移动到哪些view的位置
                 * 第一个参数为动画view和对齐view的对齐方式，默认左边对齐，库中还提供了中心点对齐的实现类
                 */
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000)

                /**
                 * 动画节点方法，有with()，withClone()，next()，delay()
                 *
                 * with()：内部生成一个新动画，新动画会和上一个动画一起执行
                 * withClone()：在with()方法的基础上会复制上一个动画的一些设置属性，比如动画时长等
                 * next()：内部生成一个新动画，新动画在上一个动画执行结束后开始执行
                 * delay()：内部其实就是一个next()动画
                 */
                .withClone()

                /**
                 * 设置动画view的y方向要移动到哪些view的位置
                 * 第一个参数为动画view和对齐view的对齐方式，默认顶部对齐，库中还提供了中心点对齐的实现类
                 */
                .moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3)

                // 延迟1000毫秒
                .delay(1000)

                /**
                 * 添加一个动画监听
                 * 库中默认提供了很多方便的动画监听，比如：
                 * - OnStartVisible，动画开时的时候让某个view可见，默认的view是动画view
                 * - OnEndRemoveView，动画结束后移除某个view，默认是移除动画view
                 *
                 * 注意：在哪个节点addListener，就是监听哪个节点的动画，demo这里监听的是延迟1000毫秒这个动画
                 */
                .addListener(new OnEndReset()) // 动画完成后重置view

                /**
                 * 开始执行动画
                 */
                .start();

        /**
         * 为了解决view没办法超出父布局边界来执行动画的问题，提供了这个方法
         *
         * 内部实现原理：
         *
         * 1.对动画View截图然后设置给ImageView
         * 2.让镜像ImageView添加到android.R.id.content的FrameLayout里面，并对齐覆盖在动画view上面
         * 3.让镜像ImageView执行动画
         *
         * 注意：如果调用此方法的话，要记得在最后的动画结束后移除镜像ImageView，除非业务需求就是不移除
         */
//        animatorSet.startAsPop();
    }

    public void onClickBtnAnimInside(View v)
    {
        new FAnimatorSet().setTarget(v)
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000)
                .withClone().moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3)
                .delay(1000)
                .addListener(new OnEndRemoveView()) //动画完成后移除view
                .startAsPop();
    }
}
