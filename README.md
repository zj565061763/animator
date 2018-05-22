# Animator
对ObjectAnimator和AnimatorSet进行封装

## Gradle
`implementation 'com.fanwe.android:animator:1.1.0-rc12'`

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

## 效果
![](http://thumbsnap.com/i/sK3VSRT3.gif?0521)
![](http://thumbsnap.com/i/cD2NW5lZ.gif?0815)
![](http://thumbsnap.com/i/4SvypvW0.gif?0522)

## 简单demo
```java
/**
 * 动画view和target在同一个父布局里面
 */
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
            .moveToX(new XCenterAligner(), v, view_target_1, view_target_2, view_target_3).setDuration(1500)

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
            .moveToY(new YCenterAligner(), v, view_target_1, view_target_2, view_target_3)

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
    new SimpleNodeAnimator(v)
            .moveToX(new XCenterAligner(), v, view_target_1, view_target_2, view_target_3).setDuration(1500).setTag("x移动")
            .withClone().moveToY(new YCenterAligner(), v, view_target_1, view_target_2, view_target_3).setTag("y移动")
            .with().scaleX(v, view_target_1, view_target_2, view_target_3).setDuration(1500).setTag("x缩放")
            .withClone().scaleY(v, view_target_1, view_target_2, view_target_3).setTag("y缩放")
            .next().setDuration(500).setTag("延迟500毫秒")
            .addListener(new OnEndRemoveView()) //动画完成后移除view
            .chain().setDebug(true).startAsPop(true);
}
```
## 火箭动画demo
```java
public void onclickStart(View v)
{
    if (mAnimatorChain != null && mAnimatorChain.isRunning())
    {
        return;
    }
    /**
     * true-调试模式，会输出整个动画链的结构，方便开发调试，可以给每个节点动画设置tag，来加强描述
     *
     * 过滤tag：AnimatorChain，demo中的日志输入如下：
     *
     *    Head:(火箭淡入 alpha:500)
     *    Next:(延迟500毫秒 null:500)
     *    Next:(开始数字缩放X scaleX:1000) With:(开始数字缩放Y scaleY:1000)
     *    Next:(火箭起飞 translationY:3000) With:(烟雾淡入 alpha:3000 startDelay:500)
     *    Next:(烟雾淡出 alpha:500)
     *
     */
    mAnimatorChain = new SimpleNodeAnimator(fl_rocket_root).chain().setDebug(true);

    mAnimatorChain.currentNode()
            .alpha(0, 1f).setDuration(500).setTag("火箭淡入")
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
                    tv_number.setVisibility(View.INVISIBLE);
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
## 汽车动画demo
```java
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

    mAnimatorChain = new SimpleNodeAnimator(iv_down_car_front_tyre).chain().setDebug(true);

    mAnimatorChain.currentNode()
            .rotation(-360).setRepeatCount(-1).setDuration(1000)
            .addListener(new OnEndReset(), new OnEndInvisible()).setTag("下-前轮旋转")
            .withClone().setTarget(iv_down_car_back_tyre)
            .addListener(new OnEndReset(), new OnEndInvisible()).setTag("下-后轮旋转")

            .with().setTarget(fl_down_car).moveToX(carDownX1, carDownX2)
            .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setTag("X右上角移动到屏幕中央")
            .withClone().moveToY(carDownY1, carDownY2).setTag("Y右上角移动到屏幕中央")

            .next().setDuration(500).setTag("屏幕中央停止500毫秒")

            .next().moveToX(carDownX2, carDownX3)
            .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setTag("X屏幕中央移动到左下角")
            .withClone().moveToY(carDownY2, carDownY3)
            .addListener(new OnEndInvisible(), new OnEndReset()).setTag("Y屏幕中央移动到左下角")

            .next().setTarget(iv_up_car_front_tyre).rotation(360).setRepeatCount(-1).setDuration(1000)
            .addListener(new OnEndReset(), new OnEndInvisible()).setTag("上-前轮旋转")
            .withClone().setTarget(iv_up_car_back_tyre)
            .addListener(new OnEndReset(), new OnEndInvisible()).setTag("上-后轮旋转")

            .with().setTarget(fl_up_car).moveToX(carUpX1, carUpX2)
            .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setTag("X左下角移动到屏幕中央")
            .withClone().moveToY(carUpY1, carUpY2).setTag("Y左下角移动到屏幕中央")

            .next().setDuration(500).setTag("屏幕中央停止500毫秒")

            .next().moveToX(carUpX2, carUpX3)
            .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setTag("X屏幕中央移动到右上角")
            .withClone().moveToY(carUpY2, carUpY3).setTag("Y屏幕中央移动到右上角")
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
```

