package com.laizexin.sdj.library;

/**
 * @Description:用于保存和检查状态更新
 * @Author: laizexin
 * @Time: 2018/11/15
 */
public class StatusUtlis {

    private int mProgress;

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public void checkProgressStatus(ProgressButton progressButton){
        if(mProgress != progressButton.getProgress()){
            setProgress(progressButton.getProgress());
            progressButton.setProgress(progressButton.getProgress());
        }
    }
}
