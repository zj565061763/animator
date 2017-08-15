package com.fanwe.library.animator;

/**
 * 属性动画接口
 */
public interface ISDPropertyAnim extends ISDAnim
{
    String X = "x";
    String Y = "y";
    String TRANSLATION_X = "translationX";
    String TRANSLATION_Y = "translationY";
    String ALPHA = "alpha";
    String SCALE_X = "scaleX";
    String SCALE_Y = "scaleY";
    String ROTATION = "rotation";
    String ROTATION_X = "rotationX";
    String ROTATION_Y = "rotationY";

    /**
     * 设置x坐标（相对于父容器）
     *
     * @param values
     * @return
     */
    ISDPropertyAnim x(float... values);

    /**
     * 设置y坐标（相对于父容器）
     *
     * @param values
     * @return
     */
    ISDPropertyAnim y(float... values);

    /**
     * 设置x方向相对于当前位置的偏移量
     *
     * @param values
     * @return
     */
    ISDPropertyAnim translationX(float... values);

    /**
     * 设置y方向相对于当前位置的偏移量
     *
     * @param values
     * @return
     */
    ISDPropertyAnim translationY(float... values);

    /**
     * 设置透明度
     *
     * @param values
     * @return
     */
    ISDPropertyAnim alpha(float... values);

    /**
     * 设置缩放x
     *
     * @param values
     * @return
     */
    ISDPropertyAnim scaleX(float... values);

    /**
     * 设置缩放y
     *
     * @param values
     * @return
     */
    ISDPropertyAnim scaleY(float... values);

    /**
     * 设置绕z轴方向旋转角度（大于0顺时针，小于0逆时针）
     *
     * @param values
     * @return
     */
    ISDPropertyAnim rotation(float... values);

    /**
     * 设置绕x轴方向旋转角度
     *
     * @param values
     * @return
     */
    ISDPropertyAnim rotationX(float... values);

    /**
     * 设置绕y轴方向旋转角度
     *
     * @param values
     * @return
     */
    ISDPropertyAnim rotationY(float... values);


    /**
     * 动画类型参数是否为空
     *
     * @return
     */
    boolean isEmptyProperty();
}
