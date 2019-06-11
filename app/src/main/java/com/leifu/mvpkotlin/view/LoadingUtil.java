package com.leifu.mvpkotlin.view;


import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 控制一个页面不能有多个Dialog
 */
public class LoadingUtil {

    private static WeakReference<Context> mThreadActivityRef;
    private static WeakReference<LoadingDialog> waitDialog;

    /**
     * 自定义用于等待的dialog,有动画和message提示
     * 调用stopWaitDialog()方法来取消
     *
     * @param activity
     * @param message
     */
    public static void showLoading(Activity activity, String message) {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }
        if (activity == null || activity.isFinishing()) {
            return;
        }
        mThreadActivityRef = new WeakReference<>(activity);
        waitDialog = new WeakReference<>(new LoadingDialog(mThreadActivityRef.get(), message));
        if (waitDialog != null && waitDialog.get() != null && !waitDialog.get().isShowing()) {
            waitDialog.get().setCanceledOnTouchOutside(false);
            waitDialog.get().setCancelable(false);
            waitDialog.get().show();  //
        }
    }

    /**
     * 取消等待dialog
     */
    public static void dismissLoading() {
        if (waitDialog != null && waitDialog.get() != null && waitDialog.get().isShowing()) {
            waitDialog.get().dismiss();
        }
    }
}
