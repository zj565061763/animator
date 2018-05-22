# Animator
对ObjectAnimator和AnimatorSet进行封装

## Gradle
`implementation 'com.fanwe.android:animator:1.1.0-rc9'`

## 关于startAsPop(boolean clone)
为了解决view没办法超出父布局边界来执行动画的问题，提供了这个方法

```java
/**
 * 实现原理： <br>
 * <p>
 * 1.对target截图然后设置给ImageView <br>
 * 2.把ImageView添加到Activity中android.R.id.content的FrameLayout里面 <br>
 * 注意：这里的Activity对象是从原target获取，所以要保证原target的getContext()返回的是Activity对象，否则会失败 <br>
 * 3.根据传入的参数是否克隆，来决定把ImageView设置给哪个动画对象执行
 * <p>
 * 参数说明： <br>
 * clone == true，克隆当前对象执行，返回克隆的对象 <br>
 * clone == false，执行当前对象，返回当前对象 <br>
 * <br>
 * 注意：不克隆的性能会好一点，但是会修改当前动画对象的target，开发者可以根据具体的应用场景来决定是否克隆
 *
 * @param clone
 * @return 如果返回不为null，表示返回的是克隆对象或者当前对象，取决于传入的参数；如果返回null，表示执行失败
 */
T startAsPop(boolean clone);
```

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
