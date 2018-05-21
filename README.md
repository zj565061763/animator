# Animator
对ObjectAnimator和AnimatorSet进行封装

## Gradle
`implementation 'com.fanwe.android:animator:1.1.0-rc9'`

## 简单demo
效果图：<br>
![](http://thumbsnap.com/i/wOZ62RgA.gif?0814)<br>

```java
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
             * with()：创建一个新的节点动画，新动画和上一个动画同时执行
             *
             * withClone()：with()的基础上，复制上一个动画的设置参数，比如动画时长等
             *
             * next()：创建一个新的节点动画，新动画在上一个动画执行完成后执行
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
    //    nodeAnimator.chain().startAsPop();

    AnimatorSet animatorSet = nodeAnimator.chain().toAnimatorSet(); // 也可以得到原生的动画对象
}
```
## 火箭动画demo
效果图：<br>
git太大了有点卡，具体可以看demo<br>
![](http://thumbsnap.com/i/cD2NW5lZ.gif?0815)<br>

```java
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
     *    Head:(火箭淡入 alpha:500)
     *    Next:(延迟500毫秒 null:500)
     *    Next:(开始数字缩放X scaleX:1000) With:(开始数字缩放Y scaleY:1000)
     *    Next:(火箭起飞 translationY:3000) With:(烟雾淡入 alpha:3000 startDelay:500)
     *    Next:(烟雾淡出 alpha:500)
     *
     */
    mAnimatorChain = new SimpleNodeAnimator(fl_rocket_root).chain().setDebug(true);

    mAnimatorChain.currentNode().alpha(0, 1f).setDuration(500).setTag("火箭淡入")
            .next().setDuration(500).setTag("延迟500毫秒")
            .next().setTarget(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000).setTag("开始数字缩放X")
            .withClone().scaleY(1f, 0f).setTag("开始数字缩放Y")
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
            .next().setTarget(fl_rocket_root).translationY(0, -getResources().getDisplayMetrics().heightPixels).setTag("火箭起飞")
            .setDuration(3000).setInterpolator(new AccelerateInterpolator())
            .addListener(new OnEndInvisible(), new OnEndReset()) //动画结束隐藏，重置fl_rocket_root
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
            .with().setTarget(iv_rocket_smoke).alpha(0, 1f).setDuration(3000).setStartDelay(500).setTag("烟雾淡入")
            .next().alpha(1f, 0).setDuration(500).setTag("烟雾淡出")
            .addListener(new OnEndInvisible()) //动画结束隐藏烟雾
            .chain().start();
}
```
