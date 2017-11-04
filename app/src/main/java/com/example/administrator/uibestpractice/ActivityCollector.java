package com.example.administrator.uibestpractice;

import android.app.Activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ActivityCollector {
    public static List<Activity> activityArray=new ArrayList<>();
    public static void AddActivity(Activity activity){
        activityArray.add(activity);
    }
    public static void RemoveActivity(Activity activity){
        activityArray.remove(activity);
    }
    public static void FinishALL(){
        for (Activity activity1: activityArray){
            if(!activity1.isFinishing()){
                activity1.finish();
            }
        }
    }
}
