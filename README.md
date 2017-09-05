# Animator
对ObjectAnimator和AnimatorSet进行封装，可以链式调用，提供的with()和next()方法可以方便的指定动画的顺序，并且解决View执行移动动画的时候无法超出父容器限制的问题

## Gradle
`compile 'com.fanwe.android:animator:1.0.5'`

## 简单demo
效果图：<br>
![](http://thumbsnap.com/i/wOZ62RgA.gif?0814)<br>

```java
public void onClickBtnAnim(View v)
{
    SDAnimSet.from(v)
            .setAlignType(SDAnim.AlignType.Center) //设置要移动到的目标view和动画view的对齐方式
            .moveToX(v, view_target_1, view_target_2, view_target_3).setDuration(2000)//依次移动x到v, view_target_1, view_target_2, view_target_3
            .withClone().moveToY(v, view_target_1, view_target_2, view_target_3) //依次移动y到v, view_target_1, view_target_2, view_target_3
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
    SDAnimSet.from(v)
            .setAlignType(SDAnim.AlignType.Center) //设置要移动到的目标view和动画view的对齐方式
            .moveToX(v, view_target_1, view_target_2, view_target_3).setDuration(2000) //依次移动x到v, view_target_1, view_target_2, view_target_3
            .withClone().moveToY(v, view_target_1, view_target_2, view_target_3) //依次移动y到v, view_target_1, view_target_2, view_target_3
            .delay(1000)
            .addListener(new SDAnimatorListener()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    super.onAnimationEnd(animation);
                    getTarget().setVisibility(View.GONE); //动画完成后隐藏view
                }
            })
            .addListener(new OnEndRemoveView()) //动画完成后移除view
            .startAsPop();
}
```
## 火箭动画demo
效果图：<br>
git太大了有点卡，具体可以看demo<br>
![](http://thumbsnap.com/i/cD2NW5lZ.gif?0815)<br>

```java
SDAnimSet.from(fl_rocket_root)
        //火箭淡入
        .alpha(0, 1f).setDuration(1000)
        .delay(500)
        //数字倒数
        .with(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000)
        .withClone().scaleY(1f, 0f)
        .addListener(new SDAnimatorListener()
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
        .addListener(new SDAnimatorListener()
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
