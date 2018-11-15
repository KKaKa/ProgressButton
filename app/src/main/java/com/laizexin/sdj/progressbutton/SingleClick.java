package com.laizexin.sdj.progressbutton;

/**
 * @Description:
 * @Author: laizexin
 * @Time: 2018/11/15
 */
public @interface SingleClick {
    //点击间隔 默认1000ms
    long value() default 1000;
}
