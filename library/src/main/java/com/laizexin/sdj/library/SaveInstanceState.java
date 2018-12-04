package com.laizexin.sdj.library;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @Description:
 * @Author: laizexin
 * @Time: 2018/12/3
 */
public class SaveInstanceState extends View.BaseSavedState {
    private int progress;

    public SaveInstanceState(Parcelable source) {
        super(source);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(progress);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}