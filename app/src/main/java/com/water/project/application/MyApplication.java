package com.water.project.application;

import android.app.Application;

import com.google.gson.Gson;
import com.water.project.utils.SPUtil;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class MyApplication extends Application {

    public static MyApplication application;
    public static SPUtil spUtil;
    public static Gson gson;
    public void onCreate() {
        super.onCreate();
        application = this;
        spUtil = SPUtil.getInstance(this);
        gson=new Gson();

        spUtil.removeMessage(SPUtil.BLE_DEVICE);
    }
}
