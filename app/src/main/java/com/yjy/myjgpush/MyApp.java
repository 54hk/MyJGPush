package com.yjy.myjgpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Project name：江云一代一路
 * <p>
 * 类说明：
 * <p>
 * 2017/12/11 0011.
 * <p>
 * by：杨金阳
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this,0,"");

    }
}
