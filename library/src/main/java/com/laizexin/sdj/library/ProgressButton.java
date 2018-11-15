package com.laizexin.sdj.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.StateSet;

/**
 * @Description:
 * @Author: laizexin
 * @Time: 2018/11/12
 */
public class ProgressButton extends android.support.v7.widget.AppCompatButton {
    public static final int NORMAL_STATE_PROGRESS = 0;
    public static final int ERROR_STATE_PROGRESS = -1;
    public static final int COMPLETE_STATE_PROGRESS = 100;
    public static final int INDETERMINATE_STATE_PROGRESS = 50;

    private String mNormalText;
    private String mCompleteText;
    private String mErrorText;
    private String mProgressText;
    private int mCompleteIcon;
    private int mErrorIcon;
    private ColorStateList mNormalBackground;
    private ColorStateList mCompleteBackground;
    private ColorStateList mErrorBackground;
    private int mNormalTextColor;
    private int mCompleteTextColor;
    private int mErrorTextColor;
    private int mProgressColor;
    private int mProgressIndicatorColor;
    private int mProgressIndicatorBackgroundColor;
    private StateListDrawable mNormalStateDrawable;
    private StateListDrawable mCompleteStateDrawable;
    private StateListDrawable mErrorStateDrawable;

    private int mMaxProgress;
    private int mProgress;
    private STATUS status;
    private ProgressButtonGradientDrawable background;
    private float mCornerRadius;
    private int mStrokeWidth;
    private boolean mMorphingInProgress;
    private CircularAnimatedDrawable mAnimatedDrawable;
    private CircularProgressDrawable mProgressDrawable;

    public ProgressButton(Context context) {
        super(context);
        init(context,null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private enum STATUS{
        NORMAL,ERROR,COMPLETE,PROGRESS
    }

    private void init(Context context,AttributeSet attrs){
        mStrokeWidth = 8;
        initArrts(context,attrs);
        mMaxProgress = COMPLETE_STATE_PROGRESS;
        status = STATUS.NORMAL;
        setText(mNormalText);
        initNormalStateDrawable();
        setBackgroundCompat(mNormalStateDrawable);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            setProgress(mProgress);
        }
    }

    @Override
    protected void drawableStateChanged() {
        if(status == STATUS.NORMAL){
            initNormalStateDrawable();
            setBackgroundCompat(mNormalStateDrawable);
        }else if(status == STATUS.COMPLETE){
            initCompleteStateDrawable();
            setBackgroundCompat(mCompleteStateDrawable);
        }else if(status == STATUS.ERROR){
            initErrorStateDrawable();
            setBackgroundCompat(mErrorStateDrawable);
        }

        if(status != STATUS.PROGRESS){
            super.drawableStateChanged();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mProgress > 0 && status == STATUS.PROGRESS && !mMorphingInProgress){
            drawIndeterminateProgress(canvas);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mAnimatedDrawable || super.verifyDrawable(who);
    }

    private void drawIndeterminateProgress(Canvas canvas) {
        if(mAnimatedDrawable == null){
            int offSet = (getWidth() - getHeight())/2; //高即直径
            mAnimatedDrawable = new CircularAnimatedDrawable(mProgressIndicatorColor,mStrokeWidth);
            int left = offSet;
            int right = offSet + getHeight();
            int top = 0;
            int bottom = getHeight();
            mAnimatedDrawable.setBounds(left,top,right,bottom);
            mAnimatedDrawable.setCallback(this);
            mAnimatedDrawable.start();
        }else{
            mAnimatedDrawable.draw(canvas);
        }
    }

    private void initArrts(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs,R.styleable.ProgressButton,0,0);
        if(attr == null)
            return;
        try {
            mNormalText = attr.getString(R.styleable.ProgressButton_pb_normalText);
            mNormalTextColor = attr.getColor(R.styleable.ProgressButton_pb_normalTextColor, ContextCompat.getColor(context,R.color.pb_normal_text_color));
            int normalStateSelector = attr.getResourceId(R.styleable.ProgressButton_pb_normalBackgroundColor, R.color.pb_normal_background_colors);
            mNormalBackground = getResources().getColorStateList(normalStateSelector);

            mCompleteText = attr.getString(R.styleable.ProgressButton_pb_completeText);
            mCompleteTextColor = attr.getColor(R.styleable.ProgressButton_pb_completelTextColor,ContextCompat.getColor(context,R.color.pb_complete_text_color));
            int completeStateSelector = attr.getResourceId(R.styleable.ProgressButton_pb_completeBackgroundColor,R.color.pb_complete_background_colors);
            mCompleteBackground = getResources().getColorStateList(completeStateSelector);
            mCompleteIcon = attr.getResourceId(R.styleable.ProgressButton_pb_iconComplete,0);

            mErrorText = attr.getString(R.styleable.ProgressButton_pb_errorText);
            mErrorTextColor = attr.getColor(R.styleable.ProgressButton_pb_errorTextColor,ContextCompat.getColor(context,R.color.pb_error_text_color));
            int errorStateSelector = attr.getResourceId(R.styleable.ProgressButton_pb_errorBackgroundColor,R.color.pb_error_background_colors);
            mErrorBackground = getResources().getColorStateList(errorStateSelector);
            mErrorIcon = attr.getResourceId(R.styleable.ProgressButton_pb_iconError,0);

            int white = ContextCompat.getColor(context,R.color.pb_progress);
            int blue = ContextCompat.getColor(context,R.color.pb_progress_indicator);
            int grey = ContextCompat.getColor(context,R.color.pb_progress_indicator_background);

            mProgressText = attr.getString(R.styleable.ProgressButton_pb_progressText);
            mProgressColor = attr.getColor(R.styleable.ProgressButton_pb_progressColor,white);
            mProgressIndicatorColor = attr.getColor(R.styleable.ProgressButton_pb_progress_indicator_color,blue);
            mProgressIndicatorBackgroundColor = attr.getColor(R.styleable.ProgressButton_pb_progress_indicator_background_color,grey);

            mCornerRadius = attr.getDimension(R.styleable.ProgressButton_pb_corner_radius,0);
        }catch (Exception e){

        }finally {
            attr.recycle();
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;

        if (mMorphingInProgress || getWidth() == 0) {
            return;
        }
        //转为完成
        if(mProgress >= mMaxProgress){
            if(status == STATUS.NORMAL){
                //正常 -> 完成
                normalToComplete();
            }else if(status == STATUS.PROGRESS){
                //转圈 -> 完成
                progressToComplete();
            }
        }
        //转为转圈
        else if(mProgress > NORMAL_STATE_PROGRESS){
            if(status == STATUS.NORMAL){
                //正常 -> 转圈
                normalToProgress();
            }else if(status == STATUS.PROGRESS){
                invalidate();
            }else if(status == STATUS.ERROR){
                //错误 -> 转圈
                errorToProgress();
            }
        }
        //转为错误
        else if(mProgress == ERROR_STATE_PROGRESS){
            if(status == STATUS.PROGRESS){
                //转圈 -> 错误
                progressToError();
            }else if(status == STATUS.NORMAL){
                //正常 -> 错误
                normalToError();
            }
        }
        //转为正常
        else if(mProgress == NORMAL_STATE_PROGRESS){
            if(status == STATUS.COMPLETE){
                //成功 -> 正常
                completeToNormal();
            }else if(status == STATUS.ERROR){
                //错误 -> 正常
                errorToNormal();
            }else if(status == STATUS.PROGRESS){
                //转圈 -> 正常
                progressToNormal();
            }
        }
    }

    public int getProgress() {
        return mProgress;
    }

    private void progressToNormal() {
        setWidth(getWidth());
        AnimManager animManager = createAnimManager(getHeight(),getWidth(),
                mProgressColor,getNormalColor(mNormalBackground),
                mProgressIndicatorColor,getNormalColor(mNormalBackground));
        animManager.setmFromCornerRadius(getHeight());
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setmDuration(AnimManager.DURATION);
        animManager.setListener(normalListener);
        animManager.start();
    }

    private void errorToNormal() {
        mMorphingInProgress = true;
        AnimManager animManager = createAnimManager(
                getWidth(),getWidth(),
                getNormalColor(mErrorBackground),getNormalColor(mNormalBackground),
                getNormalColor(mErrorBackground),getNormalColor(mNormalBackground));
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setmDuration(AnimManager.IMMEDIATELY);
        animManager.setListener(normalListener);
        animManager.start();
    }

    private void completeToNormal() {
        mMorphingInProgress = true;
        AnimManager animManager = createAnimManager(
                getWidth(),getWidth(),
                getNormalColor(mCompleteBackground),getNormalColor(mNormalBackground),
                getNormalColor(mCompleteBackground),getNormalColor(mNormalBackground));
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setmDuration(AnimManager.IMMEDIATELY);
        animManager.setListener(normalListener);
        animManager.start();
    }

    private AnimManager.AnimListener normalListener = new AnimManager.AnimListener() {
        @Override
        public void onAnimEnd() {
            removeIcon();
            setText(mNormalText);
            setTextColor(mNormalTextColor);
            mMorphingInProgress = false;
            status = STATUS.NORMAL;
        }
    };

    private void normalToError() {
        AnimManager animManager = new AnimManager(this,background);
        animManager.setmFromColor(getNormalColor(mNormalBackground));
        animManager.setmToColor(getNormalColor(mErrorBackground));
        animManager.setmFromStrokeColor(getNormalColor(mNormalBackground));
        animManager.setmToStrokeColor(getNormalColor(mErrorBackground));
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setmDuration(AnimManager.IMMEDIATELY);
        animManager.setListener(errorListener);
        animManager.start();
    }

    private void progressToError() {
        AnimManager animManager = createAnimManager(getHeight(),getWidth(),
                mProgressColor,getNormalColor(mErrorBackground),
                mProgressIndicatorColor,getNormalColor(mErrorBackground));
        animManager.setmFromCornerRadius(getHeight());
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setmDuration(AnimManager.DURATION);
        animManager.setListener(errorListener);
        animManager.start();
    }

    private AnimManager.AnimListener errorListener = new AnimManager.AnimListener() {
        @Override
        public void onAnimEnd() {
            if (mErrorIcon != 0) {
                setIcon(mErrorIcon);
                setText(null);
            }else {
                setText(mErrorText);
                setTextColor(mErrorTextColor);
            }
            mMorphingInProgress = false;
            status = STATUS.ERROR;
        }
    };

    private void errorToProgress() {
        setWidth(getWidth());
        AnimManager animManager = createAnimManager(
                getWidth(),getHeight(),
                getNormalColor(mErrorBackground),mProgressColor,
                getNormalColor(mErrorBackground),mProgressIndicatorBackgroundColor);
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(getHeight());
        animManager.setmDuration(AnimManager.DURATION);
        animManager.setListener(progressListener);
        animManager.start();
    }

    private void normalToProgress() {
        setWidth(getWidth());
        AnimManager animManager = createAnimManager(
                getWidth(),getHeight(),
                getNormalColor(mNormalBackground),mProgressColor,
                getNormalColor(mNormalBackground),mProgressIndicatorBackgroundColor);
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(getHeight());
        animManager.setListener(progressListener);
        animManager.setmDuration(AnimManager.DURATION);
        animManager.start();
    }

    private AnimManager.AnimListener progressListener = () -> {
        mMorphingInProgress = false;
        status =STATUS.PROGRESS;
        setText(mProgressText);
    };

    private void progressToComplete() {
        AnimManager animManager = createAnimManager(
                getHeight(),getWidth(),
                mProgressColor,getNormalColor(mCompleteBackground),
                mProgressIndicatorColor,getNormalColor(mCompleteBackground));
        animManager.setmFromCornerRadius(getHeight());
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setListener(mCompleteListener);
        animManager.start();
    }

    private void normalToComplete() {
        AnimManager animManager = createAnimManager(getWidth(),getWidth(),
                getNormalColor(mNormalBackground),getNormalColor(mCompleteBackground),
                getNormalColor(mNormalBackground),getNormalColor(mCompleteBackground));
        animManager.setmFromCornerRadius(mCornerRadius);
        animManager.setmToCornerRadius(mCornerRadius);
        animManager.setListener(mCompleteListener);
        animManager.setmDuration(AnimManager.IMMEDIATELY);
        animManager.start();
    }

    private AnimManager.AnimListener mCompleteListener = () -> {
        //是否有完成图片 有则显示图片 没有则显示文字
        if(mCompleteIcon != 0){
            setIcon(mCompleteIcon);
            setText(null);
        }else{
            setText(mCompleteText);
            setTextColor(mCompleteTextColor);
        }
        mMorphingInProgress = false;
        status = STATUS.COMPLETE;
    };

    private AnimManager createAnimManager(
            int fromWidth,int toWidth,
            int fromColor,int toColor,
            int fromStrokeColor,int toStrokeColor){
        mMorphingInProgress = true;

        AnimManager animManager = new AnimManager(this,background);
        animManager.setmFromWidth(fromWidth);
        animManager.setmToWidth(toWidth);
        animManager.setmFromColor(fromColor);
        animManager.setmToColor(toColor);
        animManager.setmFromStrokeColor(fromStrokeColor);
        animManager.setmToStrokeColor(toStrokeColor);

        //TODO 当只是状态变化 时长不需要那么长 做个区分
        animManager.setmDuration(AnimManager.DURATION);

        return animManager;
    }

    private void initNormalStateDrawable(){
        setText(mNormalText);
        setTextColor(mNormalTextColor);
        int colorNormal = getNormalColor(mNormalBackground);
        int colorPressed = getPressedColor(mNormalBackground);
        int colorFocused = getFocusedColor(mNormalBackground);
        int colorDisabled = getDisabledColor(mNormalBackground);
        if(background == null){
            background = createDrawable(colorNormal);
        }
        ProgressButtonGradientDrawable drawableDisabled = createDrawable(colorDisabled);
        ProgressButtonGradientDrawable drawableFocused = createDrawable(colorFocused);
        ProgressButtonGradientDrawable drawablePressed = createDrawable(colorPressed);
        mNormalStateDrawable = new StateListDrawable();

        mNormalStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mNormalStateDrawable.addState(new int[]{android.R.attr.state_focused}, drawableFocused.getGradientDrawable());
        mNormalStateDrawable.addState(new int[]{-android.R.attr.state_enabled}, drawableDisabled.getGradientDrawable());
        mNormalStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private void initCompleteStateDrawable(){
        int colorPressed = getPressedColor(mCompleteBackground);
        ProgressButtonGradientDrawable drawablePressed = createDrawable(colorPressed);
        mCompleteStateDrawable = new StateListDrawable();
        mCompleteStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mCompleteStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private void initErrorStateDrawable(){
        int colorPressed = getPressedColor(mErrorBackground);
        ProgressButtonGradientDrawable drawablePressed = createDrawable(colorPressed);
        mErrorStateDrawable = new StateListDrawable();
        mErrorStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mErrorStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private int getNormalColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
    }

    private int getPressedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_pressed}, 0);
    }

    private int getFocusedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_focused}, 0);
    }

    private int getDisabledColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{-android.R.attr.state_enabled}, 0);
    }

    private ProgressButtonGradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.pb_background).mutate();
        drawable.setColor(color);
        drawable.setCornerRadius(mCornerRadius);
        ProgressButtonGradientDrawable strokeGradientDrawable = new ProgressButtonGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(color);
        strokeGradientDrawable.setStrokeWidth(mStrokeWidth);
        return strokeGradientDrawable;
    }

    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    private void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            int padding = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding(padding, 0, 0, 0);
        }
    }

    protected void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }
}
