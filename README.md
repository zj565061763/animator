# Animator
对ObjectAnimator和AnimatorSet进行封装，提供链式调用的库，并且解决View执行移动动画的时候无法超出父容器限制的问题

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
