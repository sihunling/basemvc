package com.example.youbang.baseapp.activity;

import android.app.Activity;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import java.util.List;

/**
 * 管理应用内activity堆栈
 * 1.activity的入栈出栈，全部关闭
 * 2.应用退出
 * 3.获取当前activity，关闭应用activity
 */
public class ActivityManager {

  /**
   * 获取当前activity
   * @return
   */
  public static Activity getCurrentActivity(){

    return  ActivityUtils.getTopActivity();

  }

  /**
   * 退出应用
   */
  public static void exitApp(){
    AppUtils.exitApp();
  }

  /**
   * 获取activity列表
   * @return
   */
  public static List<Activity> getActivityList(){

    return ActivityUtils.getActivityList();
  }
}
