# Animator
对ObjectAnimator和AnimatorSet进行封装，可以链式调用，提供的with()和next()方法可以方便的指定动画的顺序，并且解决View执行移动动画的时候无法超出父容器限制的问题

## Gradle
`implementation 'com.fanwe.android:animator:1.0.19'`

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
     * 为了解决一些view没办法超出父布局边界来执行动画的问题，提供了这个方法
     *
     * 内部实现原理：
     *
     * 1.对动画View截图然后设置给ImageView
     * 2.让镜像ImageView添加到android.R.id.content的FrameLayout里面，并对齐覆盖在动画view上面
     * 3.让镜像ImageView执行动画
     *
     * 注意：如果调用此方法的话，要记得在最后的动画结束后移除镜像ImageView，除非业务需求就是不移除
     */
    animatorSet.startAsPop();

    ObjectAnimator objectAnimator = animatorSet.get(); // 也可以得到原生的动画对象
}
```
## 火箭动画demo
效果图：<br>
git太大了有点卡，具体可以看demo<br>
![](http://thumbsnap.com/i/cD2NW5lZ.gif?0815)<br>

```java
new FAnimatorSet(v)
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
        .addListener(new OnEndInvisible(iv_rocket_smoke))
        .start();
```
