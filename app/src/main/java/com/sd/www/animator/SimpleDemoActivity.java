package com.sd.www.animator;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sd.lib.animator.FNodeAnimator;
import com.sd.lib.animator.NodeAnimator;
import com.sd.lib.animator.listener.api.OnEndRemoveView;
import com.sd.lib.animator.listener.api.OnEndReset;

public class SimpleDemoActivity extends AppCompatActivity
{
    public static final String TAG = SimpleDemoActivity.class.getSimpleName();

    private View view_target_1, view_target_2, view_target_3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        view_target_1 = findViewById(R.id.view_target_1);
        view_target_2 = findViewById(R.id.view_target_2);
        view_target_3 = findViewById(R.id.view_target_3);
    }

    /**
     * 动画view和target在同一个父布局里面
     */
    public void onClickBtnAnim(View v)
    {
        /**
         * 创建一个节点动画
         */
        final NodeAnimator nodeAnimator = new FNodeAnimator(v);

        nodeAnimator
                /**
                 * 设置动画view的x方向要移动到哪些view的位置
                 * 第一个参数为动画view和对齐view的对齐方式，默认左边对齐，库中还提供了中心点对齐的实现类
                 */
                .moveXToView(0, v, view_target_1, view_target_2, view_target_3).setDuration(1500)

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
                .moveYToView(0, v, view_target_1, view_target_2, view_target_3)

                /**
                 * 延迟500毫秒
                 */
                .next().setDuration(500)

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

    /**
     * 动画view和target不在同一个父布局
     */
    public void onClickBtnAnimInside(View v)
    {
        new FNodeAnimator(v)
                .moveXToView(0, v, view_target_1, view_target_2, view_target_3).setDuration(1500).setDesc("x移动")
                .withClone().moveYToView(0, v, view_target_1, view_target_2, view_target_3).setDesc("y移动")
                .with().scaleXToView(v, view_target_1, view_target_2, view_target_3).setDuration(1500).setDesc("x缩放")
                .withClone().scaleYToView(v, view_target_1, view_target_2, view_target_3).setDesc("y缩放")
                .next().setDuration(500).setDesc("延迟500毫秒")
                .addListener(new OnEndRemoveView()) //动画完成后移除view
                .chain().setDebug(true).startAsPop(true);
    }
}
