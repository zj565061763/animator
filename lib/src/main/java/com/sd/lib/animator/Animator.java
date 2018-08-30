package com.sd.lib.animator;

import android.animation.TimeInterpolator;
import android.view.View;

import java.util.ArrayList;

/**
 * 动画接口
 */
public interface Animator<T extends Animator> extends Cloneable
{
    /**
     * 设置要执行动画的view
     *
     * @param target
     * @return
     */
    T setTarget(View target);

    /**
     * 获得执行动画的view
     *
     * @return
     */
    View getTarget();

    /**
     * 设置动画时长
     *
     * @param duration 毫秒
     * @return
     */
    T setDuration(long duration);

    /**
     * 获得动画时长
     *
     * @return
     */
    long getDuration();

    /**
     * 设置插值器
     *
     * @param interpolator
     * @return
     */
    T setInterpolator(TimeInterpolator interpolator);

    /**
     * 获得插值器
     *
     * @return
     */
    TimeInterpolator getInterpolator();

    /**
     * 设置动画延迟多久开始执行
     *
     * @param delay 毫秒
     * @return
     */
    T setStartDelay(long delay);

    /**
     * 获得动画延迟多久开始执行
     *
     * @return
     */
    long getStartDelay();

    /**
     * 添加动画监听，内部不会进行监听是否重复的判断
     *
     * @param listeners
     * @return
     */
    T addListener(android.animation.Animator.AnimatorListener... listeners);

    /**
     * 移除动画监听
     *
     * @param listeners
     */
    T removeListener(android.animation.Animator.AnimatorListener... listeners);

    /**
     * 返回保存的监听对象
     *
     * @return
     */
    ArrayList<android.animation.Animator.AnimatorListener> getListeners();

    /**
     * 清空监听
     *
     * @return
     */
    T clearListener();

    /**
     * 动画是否处于运行中
     *
     * @return
     */
    boolean isRunning();

    /**
     * 动画是否已经被启动
     *
     * @return
     */
    boolean isStarted();

    /**
     * 取消动画
     */
    void cancel();

    /**
     * 开始执行动画
     *
     * @return
     */
    T start();

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

    /**
     * 克隆动画
     *
     * @return
     */
    T clone();
}
