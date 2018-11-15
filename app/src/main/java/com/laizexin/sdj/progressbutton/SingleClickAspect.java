package com.laizexin.sdj.progressbutton;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: laizexin
 * @Time: 2018/11/15
 */
@Aspect
public class SingleClickAspect {

    /**
     * 定义切点
     */
    @Pointcut("excution(@com.laizexin.sdj.progressbutton.SingleClick)")
    public void methodAnnotated() {}

    @Around("methodAnnotated()")
    public void aroundJionPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for(Object object : joinPoint.getArgs()){
            if(object instanceof View){
                view = (View) object;
                break;
            }
        }

        if(view == null)
            return;

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if(!method.isAnnotationPresent(SingleClick.class)){
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        if(!Utils.isFastDoubleClick(view,singleClick.value())){
            joinPoint.proceed();
        }
    }

}
