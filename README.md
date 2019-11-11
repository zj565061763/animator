# Animator
对ObjectAnimator和AnimatorSet进行封装，节点动画通过next()和with()方法可以组拼一个动画链，逻辑更清晰。

# Gradle
[![](https://jitpack.io/v/zj565061763/animator.svg)](https://jitpack.io/#zj565061763/animator)

# 效果
![](http://thumbsnap.com/i/cD2NW5lZ.gif?0815)
![](http://thumbsnap.com/i/4SvypvW0.gif?0522)
![](https://thumbsnap.com/i/TkMoPGYo.gif?1111)

# 节点动画
节点动画[FNodeAnimator](https://github.com/zj565061763/animator/blob/master/lib/src/main/java/com/sd/lib/animator/NodeAnimator.java)几个比较重要的方法解释:

* with()

返回一个新的节点动画，新的节点动画和上一个节点动画同时执行

* next()

返回一个新的节点动画，新的节点动画会在上一个节点动画执行完成之后执行

* node()

返回最后一个节点动画对象

* chain()

返回整个动画链对象

# 关于移动到某个目标View
动画View移动到某个目标View是一个比较复杂的逻辑，包括水平方向和竖直方向，默认是左上角对齐。<br>
由于View是可以缩放的，在执行动画之后有可能动画View和目标View都发生了缩放，所以要保证动画执行之后动画View和目标View的位置关系是正确的，需要考虑以下情况之后才可以计算出正确的偏移量：

* 动画View和目标View的未来缩放值，即动画执行之后的缩放值
* 动画View和目标View的缩放锚点

如果不希望左上角对齐的话，开发者可以通过[PositionShifter](https://github.com/zj565061763/animator/blob/master/lib/src/main/java/com/sd/lib/animator/mtv/PositionShifter.java)接口设置偏移量，
具体的使用方法参考下面的方块demo


# 方块demo
```java
private void scale()
{
    new FNodeAnimator(fl_video)
            .scaleXToView(fl_video_target) // 缩放到目标view的x
            .with().scaleYToView(fl_video_target) // 缩放到目标view的y
            .with().moveXToView() // 配置移动到目标view的x
            .newTarget(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
            .node() // 返回node节点
            .with().moveYToView() // 配置移动到目标view的y
            .newTarget(fl_video_target).setFutureScale(fl_video_target) // 添加一个移动目标view，并设置目标view未来的缩放目标
            .node() // 返回node节点
            .chain().start(); // 开始整个链条动画
}

private void move()
{
    new FNodeAnimator(fl_video)
            .moveXToView()
            .newTarget(fl_video_target_1).setPositionShifter(new AlignCenterPositionShifter()) // 配置位置转移器，设置动画View和目标View水平方向中心对齐
            .node()
            .with().moveYToView()
            .newTarget(fl_video_target_1).setPositionShifter(new AlignCenterPositionShifter())  // 配置位置转移器，设置动画View和目标View竖直方向中心对齐
            .node()
            .chain().start();
}
```

# 火箭动画demo
```java
public void onclickStart(View v)
{
    if (mAnimatorChain != null && mAnimatorChain.isRunning())
    {
        return;
    }
    /**
     * true-调试模式，会输出整个动画链的结构，方便开发调试，可以给每个节点动画设置描述
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
    mAnimatorChain = new FNodeAnimator(fl_rocket_root).chain().setDebug(true);

    mAnimatorChain.currentNode()
            .alpha(0, 1f).setDuration(500).setDesc("火箭淡入")
            .next().setDuration(500).setDesc("延迟500毫秒")
            .next().setTarget(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000).setDesc("开始数字缩放X")
            .withClone().scaleY(1f, 0f).setDesc("开始数字缩放Y")
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
            .next().setTarget(fl_rocket_root).translationY(0, -getResources().getDisplayMetrics().heightPixels).setDesc("火箭起飞")
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
            .with().setTarget(iv_rocket_smoke).alpha(0, 1f).setDuration(3000).setStartDelay(500).setDesc("烟雾淡入")
            .next().alpha(1f, 0).setDuration(500).setDesc("烟雾淡出")
            .addListener(new OnEndInvisible()) //动画结束隐藏烟雾
            .chain().start();
}
```

# 汽车动画demo
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

    mAnimatorChain = new FNodeAnimator(iv_down_car_front_tyre).chain().setDebug(true);

    mAnimatorChain.currentNode()
            .rotation(-360).setRepeatCount(-1).setDuration(1000)
            .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("下-前轮旋转")
            .withClone().setTarget(iv_down_car_back_tyre)
            .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("下-后轮旋转")

            .with().setTarget(fl_down_car).moveToX(carDownX1, carDownX2)
            .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setDesc("X右上角移动到屏幕中央")
            .withClone().moveToY(carDownY1, carDownY2).setDesc("Y右上角移动到屏幕中央")

            .next().setDuration(500).setDesc("屏幕中央停止500毫秒")

            .next().moveToX(carDownX2, carDownX3)
            .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setDesc("X屏幕中央移动到左下角")
            .withClone().moveToY(carDownY2, carDownY3)
            .addListener(new OnEndInvisible(), new OnEndReset()).setDesc("Y屏幕中央移动到左下角")

            .next().setTarget(iv_up_car_front_tyre).rotation(360).setRepeatCount(-1).setDuration(1000)
            .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("上-前轮旋转")
            .withClone().setTarget(iv_up_car_back_tyre)
            .addListener(new OnEndReset(), new OnEndInvisible()).setDesc("上-后轮旋转")

            .with().setTarget(fl_up_car).moveToX(carUpX1, carUpX2)
            .setDuration(1500).setInterpolator(new DecelerateInterpolator()).setDesc("X左下角移动到屏幕中央")
            .withClone().moveToY(carUpY1, carUpY2).setDesc("Y左下角移动到屏幕中央")

            .next().setDuration(500).setDesc("屏幕中央停止500毫秒")

            .next().moveToX(carUpX2, carUpX3)
            .setDuration(1500).setInterpolator(new AccelerateInterpolator()).setDesc("X屏幕中央移动到右上角")
            .withClone().moveToY(carUpY2, carUpY3).setDesc("Y屏幕中央移动到右上角")
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

# 关于startAsPop(boolean clone)
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
 * clone == true，执行克隆的对象，返回克隆的对象 <br>
 * clone == false，执行当前对象，返回当前对象 <br>
 * <br>
 * 注意：不克隆的性能会好一点，但是会修改当前动画对象的target，开发者可以根据具体的应用场景来决定是否克隆
 *
 * @param clone
 * @return 如果返回不为null，表示返回的是克隆对象或者当前对象，取决于传入的参数；如果返回null，表示执行失败
 */
T startAsPop(boolean clone);
```

