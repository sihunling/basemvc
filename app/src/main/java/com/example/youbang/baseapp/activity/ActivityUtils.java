package com.example.youbang.baseapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.AnimRes;

/**
 * 常见的activity操作，例如跳转，过场动画
 */
public class ActivityUtils {

  /**
   * activity跳转，带值，不带值，有返回值都可调用工具包里的相应方法
   * @param intent
   */
    public static void startActivity(Intent intent){

      com.blankj.utilcode.util.ActivityUtils.startActivity(intent);
    }

    public static void startActivity(Class<? extends Activity> cls,@AnimRes int animIn,@AnimRes int animOut){
      com.blankj.utilcode.util.ActivityUtils.startActivity(cls,animIn,animOut);
    }


}
