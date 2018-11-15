package com.laizexin.sdj.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;

/**
 * @Description:
 * @Author: laizexin
 * @Time: 2018/11/13
 */
public class AnimManager {
    public static final int DURATION = 400;
    public static final int IMMEDIATELY = 1;
    private static final String PROPERTY_COLOR = "color";
    private static final String PROPERTY_STROKE_COLOR = "strokeColor";
    private static final String PROPERTY_CORNER_RADIUS = "cornerRadius";
    private Button viewgroup;
    private ProgressButtonGradientDrawable gradientDrawableManager;

    private int mDuration;

    private int mFromWidth;
    private int mToWidth;

    private int mFromColor;
    private int mToColor;

    private float mFromCornerRadius;
    private float mToCornerRadius;

    private int mFromStrokeColor;
    private int mToStrokeColor;

    private int mPadding;

    private AnimListener listener;

    public interface AnimListener{
        void onAnimEnd();
    }

    public AnimManager(Button viewgroup , ProgressButtonGradientDrawable gradientDrawableManager) {
        this.viewgroup = viewgroup;
        this.gradientDrawableManager = gradientDrawableManager;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void setmDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getmFromWidth() {
        return mFromWidth;
    }

    public void setmFromWidth(int mFromWidth) {
        this.mFromWidth = mFromWidth;
    }

    public int getmToWidth() {
        return mToWidth;
    }

    public void setmToWidth(int mToWidth) {
        this.mToWidth = mToWidth;
    }

    public int getmFromColor() {
        return mFromColor;
    }

    public void setmFromColor(int mFromColor) {
        this.mFromColor = mFromColor;
    }

    public int getmToColor() {
        return mToColor;
    }

    public void setmToColor(int mToColor) {
        this.mToColor = mToColor;
    }

    public float getmFromCornerRadius() {
        return mFromCornerRadius;
    }

    public void setmFromCornerRadius(float mFromCornerRadius) {
        this.mFromCornerRadius = mFromCornerRadius;
    }

    public float getmToCornerRadius() {
        return mToCornerRadius;
    }

    public void setmToCornerRadius(float mToCornerRadius) {
        this.mToCornerRadius = mToCornerRadius;
    }

    public int getmPadding() {
        return mPadding;
    }

    public void setmPadding(int mPadding) {
        this.mPadding = mPadding;
    }

    public AnimListener getListener() {
        return listener;
    }

    public void setListener(AnimListener listener) {
        this.listener = listener;
    }

    public int getmFromStrokeColor() {
        return mFromStrokeColor;
    }

    public void setmFromStrokeColor(int mFromStrokeColor) {
        this.mFromStrokeColor = mFromStrokeColor;
    }

    public int getmToStrokeColor() {
        return mToStrokeColor;
    }

    public void setmToStrokeColor(int mToStrokeColor) {
        this.mToStrokeColor = mToStrokeColor;
    }

    public void start(){
        //ValueAnim
         ValueAnimator valueAnimator = ValueAnimator.ofInt(getmFromWidth(),getmToWidth());
        GradientDrawable gradientDrawable = gradientDrawableManager.getGradientDrawable();
        valueAnimator.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();
            int leftOffset;
            int rightOffset;
            int padding;

            if(mFromWidth > mToWidth){
                //收缩
                leftOffset = (mFromWidth - value)/2;
                rightOffset = mFromWidth - leftOffset;
                padding = (int) (mPadding * animation.getAnimatedFraction());
            }
            else{
                //扩张
                leftOffset = (mToWidth - value)/2;
                rightOffset = mToWidth - leftOffset;
                padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
            }
            gradientDrawable.setBounds(leftOffset + padding,padding,rightOffset - padding ,viewgroup.getHeight() - padding);
        });
        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(gradientDrawable,PROPERTY_COLOR,mFromColor,mToColor);
        colorAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator storkColorAnimator = ObjectAnimator.ofInt(gradientDrawableManager,PROPERTY_STROKE_COLOR,mFromStrokeColor,mToStrokeColor);
        storkColorAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator cornerAnimation =
                ObjectAnimator.ofFloat(gradientDrawable, PROPERTY_CORNER_RADIUS, mFromCornerRadius, mToCornerRadius);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(valueAnimator,colorAnimator,storkColorAnimator,cornerAnimation);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(listener == null)
                    return;
                listener.onAnimEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
}
