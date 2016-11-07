package com.asiainfo.aicns;

import android.app.Activity;
import android.app.Application;

import com.asiainfo.aicns.common.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuom on 16-10-31.
 */
public class App extends Application {

    private static OkHttpUtil okHttpUtil;
    private static List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();

        activities = new ArrayList<>();

        okHttpUtil = new OkHttpUtil(this.getApplicationContext());
        okHttpUtil.init();
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void clear(){
        for (Activity aty:
             activities) {
           aty.finish();
        }
    }

    public static OkHttpUtil getOkHttpUtil() {
        return okHttpUtil;
    }
}
