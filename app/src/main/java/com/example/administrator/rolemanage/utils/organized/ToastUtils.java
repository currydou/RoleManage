/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.rolemanage.utils.organized;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.administrator.rolemanage.base.MyApplication;
import com.example.administrator.rolemanage.view.ProgressBar1;


/**
 * Class containing some static utility methods.
 */
@SuppressLint("NewApi")
public class ToastUtils {
    public static final int IO_BUFFER_SIZE = 8 * 1024;

    private static Toast toast;
    private static String lastToast = "";
    private static long lastToastTime;

    private static final int DATE_DIALOG = 0;
    public static Handler h;
    public static ProgressDialog mypDialog;
    public static ProgressBar1 myProgressBar;


    private ToastUtils() {
    }

    public static void showToast(final int id) {
        showToast(MyApplication.instance.getResources().getString(id));
    }

    public static void showToast(Context context, int id) {
        showToast(context, MyApplication.instance.getString(id));
    }

    public static void showToast(final String info) {
        h.post(new Runnable() {

            @Override
            public void run() {
                try {
                    //showToast(info, Toast.LENGTH_SHORT, 0);
                    Toast.makeText(MyApplication.instance, info, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void showToast(final Context context, final String info) {
        h.post(new Runnable() {

            @Override
            public void run() {
                try {
                    //showToast(info, Toast.LENGTH_SHORT, 0);
                    if (context != null) {
                        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /*-----------------------------------ProcessDialog------------------------------------------*/

    public static ProgressDialog showProcessDialog(Context ac, int id) {
        String message = ac.getResources().getString(id);
        return showProcessDialog(ac, message);
    }

    public static ProgressDialog showProcessDialog(Context ac, String message) {
        //        if (mypDialog != null) {
//            try {
//                if (!mypDialog.isShowing()) {
//                    mypDialog.show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            mypDialog.setMessage(ac.getResources().getString(id));
//
//            return mypDialog;
//        }
        mypDialog = new ProgressDialog(ac);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mypDialog.setProgressStyle(R.style.progressbar_translucent);
        mypDialog.setMessage(message);
        mypDialog.setCancelable(false);
        mypDialog.setIndeterminate(false);
        mypDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mypDialog != null) {
                            mypDialog.dismiss();
                            mypDialog = null;
                        }
                    }
                });
                return false;
            }
        });
        try {
            h.post(new Runnable() {
                @Override
                public void run() {
                    if (!mypDialog.isShowing()) {
                        mypDialog.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mypDialog;
    }

    public static void dismissProcessDialog() {
        h.post(new Runnable() {
            @Override
            public void run() {
                if (mypDialog != null && mypDialog.isShowing()) {
                    mypDialog.dismiss();
                    mypDialog = null;
                }
            }
        });

    }

    /*-----------------------------------ProcessDialog------------------------------------------*/


    public static ProgressBar1 showProcessBar(Context ac/*, String message*/) {
        if (myProgressBar != null) {
            try {
                if (!myProgressBar.isShowing()) {
                    myProgressBar.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myProgressBar;
        }
        myProgressBar = new ProgressBar1(ac);
        myProgressBar.setCancelable(false);
        myProgressBar.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                h.post(new Runnable() {

                    @Override
                    public void run() {
                        if (myProgressBar != null && myProgressBar.isShowing()) {
                            myProgressBar.dismiss();
                            myProgressBar = null;
                        }
                    }
                });

                return false;
            }
        });
        try {
            h.post(new Runnable() {
                @Override
                public void run() {
                    if (myProgressBar != null) {
                        myProgressBar.show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myProgressBar;
    }

    public static void dismissProcessBar() {
        h.post(new Runnable() {
            @Override
            public void run() {
                if (myProgressBar != null && myProgressBar.isShowing()) {
                    myProgressBar.dismiss();
                    myProgressBar = null;
                }
            }
        });
    }




}
