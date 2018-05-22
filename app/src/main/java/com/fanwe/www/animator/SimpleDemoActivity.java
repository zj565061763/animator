package com.fanwe.www.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.animator.NodeAnimator;
import com.fanwe.lib.animator.SimpleNodeAnimator;
import com.fanwe.lib.animator.aligner.XCenterAligner;
import com.fanwe.lib.animator.aligner.YCenterAligner;
import com.fanwe.lib.animator.listener.api.OnEndRemoveView;
import com.fanwe.lib.animator.listener.api.OnEndReset;

public class SimpleDemoActivity extends AppCompatActivity
{
    public static final String TAG = SimpleDemoActivity.class.getSimpleName();

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
        /**
         * 创建一个节点动画
         */
        final NodeAnimator nodeAnimator = new SimpleNodeAnimator(v);

        nodeAnimator
                /**
                 * 设置动画view的x方向要移动到哪些view的位置
                 * 第一个参数为动画view和对齐view的对齐方式，默认左边对齐，库中还提供了中心点对齐的实现类
                 */
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000)

                /**
                 * with()：添加一个新的节点动画，新动画和当前动画同时执行
                 *
                 * withClone()：with()的基础上，复制当前动画的设置参数，比如动画时长等
                 *
                 * next()：添加一个新的节点动画，新动画在当前动画执行完成后执行
                 */
                .withClone()

                /**
                 * 设置动画view的y方向要移动到哪些view的位置
                 * 第一个参数为动画view和对齐view的对齐方式，默认顶部对齐，库中还提供了中心点对齐的实现类
                 */
                .moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3)

                /**
                 * 延迟1000毫秒
                 */
                .next().setDuration(1000)

                /**
                 * 添加一个动画监听
                 * 库中默认提供了很多方便的动画监听，比如：
                 * - OnStartVisible，动画开时的时候让某个view可见，默认的view是动画view
                 * - OnEndRemoveView，动画结束后移除某个view，默认是移除动画view
                 *
                 * 注意：在哪个节点addListener，就是监听哪个节点的动画，demo这里监听的是延迟1000毫秒这个动画
                 */
                .addListener(new OnEndReset()) // 动画完成后重置，构造方法可以传入某个指定的view，不传的话默认view是动画view

                /**
                 * chain()方法返回的是动画链对象
                 *
                 * 开始执行整个动画链
                 */
                .chain().start();

        /**
         * 可以得到原生的动画对象
         */
        AnimatorSet animatorSet = nodeAnimator.chain().toAnimatorSet();
    }

    public void onClickBtnAnimInside(View v)
    {
        new SimpleNodeAnimator(v)
                .moveToX(mXCenterAligner, v, view_target_1, view_target_2, view_target_3).setDuration(2000).setTag("x移动")
                .addListener(mAnimatorListener)
                .withClone().moveToY(mYCenterAligner, v, view_target_1, view_target_2, view_target_3).setTag("y移动")
                .next().setDuration(1000).setTag("延迟1000毫秒")
                .addListener(new OnEndRemoveView()) //动画完成后移除view
                .chain().setDebug(true).startAsPop(true);
    }

    private final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener()
    {
        @Override
        public void onAnimationStart(Animator animation)
        {
            Log.i(TAG, "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            Log.i(TAG, "onAnimationEnd");
        }

        @Override
        public void onAnimationCancel(Animator animation)
        {
            Log.i(TAG, "onAnimationCancel");
        }

        @Override
        public void onAnimationRepeat(Animator animation)
        {
            Log.i(TAG, "onAnimationRepeat");
        }
    };
}
