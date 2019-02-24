package com.example.youbang.baseapp;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.example.youbang.baseapp.exception.CrashHandler;
import com.example.youbang.baseapp.log.L;
import com.example.youbang.baseapp.receiver.NetwordChangedReceiver;

/**
 * application基类，包含应用级别的变量及方法
 */
public class BaseApplication extends Application {
  public static boolean isDebug=true;
  //获取到主线程的上下文
  private static BaseApplication mContext;
  //获取到主线程的handler
  private static Handler mMainThreadHanler;
  //获取到主线程
  private static Thread mMainThread;
  //获取到主线程的id
  private static int mMainThreadId;
  //网络变化监听器
  private NetwordChangedReceiver mNetwordChangedReceiver=null;


  @Override
  public void onCreate() {
    // TODO Auto-generated method stub
    super.onCreate();
    mContext = this;
    mMainThreadHanler = new Handler();
    mMainThread = Thread.currentThread();
    //获取到调用线程的id
    mMainThreadId = android.os.Process.myTid();
    L.init();
    if(isDebug)
      CrashHandler.getInstance().init(this);
    /**
     * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
     * 第一个参数：应用程序上下文
     * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
     */
    BGASwipeBackHelper.init(this,null);
    mNetwordChangedReceiver=new NetwordChangedReceiver();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    registerReceiver(mNetwordChangedReceiver, intentFilter);

  }

  @Override public void onTerminate() {
    super.onTerminate();
    if(null!=mNetwordChangedReceiver)
      unregisterReceiver(mNetwordChangedReceiver);
  }

  public static BaseApplication getApplication(){
    return mContext;
  }

  public static Handler getMainThreadHandler(){
    return mMainThreadHanler;
  }

  public static Thread getMainThread(){
    return mMainThread;
  }

  public static int getMainThreadId(){
    return mMainThreadId;
  }
}
