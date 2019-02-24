package com.example.youbang.baseapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.example.youbang.baseapp.R;
import com.example.youbang.baseapp.log.L;
import com.example.youbang.baseapp.message.NetwordChangedMSG;
import com.example.youbang.baseapp.message.RxBus;
import com.example.youbang.baseapp.receiver.NetwordChangedReceiver;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * activity基类，封装所有activity都会用到的方法
 * 1.隐藏标题栏
 * 2.沉浸式状态栏  工具类包有
 * 3.全屏   工具类包有
 * 4.返回键
 * 5.退出 工具类包有
 * 6.activity自定义栈管理?
 * 7.网络变化
 * 8.权限确认 ?
 * 9.跳转  带值 带返回值  工具类包有
 * 10.过场动画   工具类包有
 * 11.loading   轮子
 * 12.dialog    轮子
 * 13.toast     轮子
 * 14.title建造
 * 16.布局创建
 * 17.view创建
 * 18.数据添加
 * 19.侧滑返回
 * 20.findviewbyid
 */
public class BaseActivity extends AppCompatActivity implements IBaseActivity,BGASwipeBackHelper.Delegate {
  protected static final String TAG=BaseActivity.class.getSimpleName();
  //滑动返回辅助类
  protected  BGASwipeBackHelper mSwipeBackHelper;
  //消息订阅者
  protected CompositeDisposable mCompositeDisposable;
  //记录网络是否真的能ping通
  protected boolean canPing;
  //需要开网络状态检查吗
  protected boolean isPing=false;
  private Disposable ping;

  protected void setPing(boolean ping) {
    isPing = ping;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    L.d(TAG,"BaseActivity-->onCreate");
    // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
    // 在 super.onCreate(savedInstanceState) 之前调用该方法
    // 先设置参数
    mCompositeDisposable=new CompositeDisposable();
    initParms();
    initSwipeBackFinish();
    super.onCreate(savedInstanceState);
    //  检查是否直接显示view
    View contentView=bindView();
    if(null==contentView){
      //根据布局创建contentView
      contentView=LayoutInflater.from(this).inflate(bindLayout(),null);
    }
    setContentView(contentView);
    //初始化控件
    initView(contentView);
    //设置监听事件
    setListeners();
    //业务逻辑，加载数据等
    doBusiness();


  }
  @Override protected void onResume() {
    L.d(TAG,"BaseActivity-->onResume");
    super.onResume();
    //刷新数据等
    resume();
    // TODO: 2019/2/23 注册网络监听器,泄漏问题没有处理
    if(isPing)
      ping= Observable.interval(2000,TimeUnit.MILLISECONDS)
          .observeOn(Schedulers.io())
          .map(new Function<Long, Boolean>() {
            @Override public Boolean apply(Long aLong) {
              if(!NetworkUtils.isAvailableByPing("www.baidu.com")){
                return false;
              }

              return true;
            }
          }).observeOn(AndroidSchedulers.mainThread())
          //.as(AutoDispose.<Boolean>autoDisposable(AndroidLifecycleScopeProvider.from(
          //     this,Lifecycle.Event.ON_DESTROY)))
          .subscribe(new Consumer<Boolean>() {
            @Override public void accept(Boolean aBoolean) throws Exception {
              onNetwordPing(aBoolean);
            }
          });


  }

  @Override protected void onPause() {
    L.d(TAG,"BaseActivity-->onPause");
    super.onPause();
    //  销毁网络监听器
    if(null!=ping && !ping.isDisposed()){
      ping.dispose();
      ping=null;
    }

  }

  @Override protected void onDestroy() {
    L.d(TAG,"BaseActivity-->onDestroy");
    super.onDestroy();
    //清除工作
    destroy();
  }
  
  
  
  @Override public void initParms() {
    L.d(TAG,"BaseActivity-->initParms");

  }

  @Override public int bindLayout() {
    L.d(TAG,"BaseActivity-->bindLayout");
    return 0;
  }

  @Override public View bindView() {
    L.d(TAG,"BaseActivity-->bindView");
    return null;
  }

  @Override public void initView(View view) {
    L.d(TAG,"BaseActivity-->initView");
  }

  @Override public void setListeners() {
    L.d(TAG,"BaseActivity-->setListeners");
  }

  @Override public void doBusiness() {
    L.d(TAG,"BaseActivity-->doBusiness");
  }

  @Override public void resume() {
    L.d(TAG,"BaseActivity-->resume");
  }

  @Override public void destroy() {
    L.d(TAG,"BaseActivity-->destroy");
  }

  

  /**
   * 封装一下findviewbyid方法及强制转换
   * @throws ClassCastException 有可能强转失败
   */
  @SuppressWarnings("unchecked")
  @Nullable
  protected  <T extends View>  T fvbi(@IdRes int id) throws ClassCastException
  {
    return findViewById(id);
  }

  //boolean firstClick=false;
  //long currentMills=System.currentTimeMillis();
  //int timeInterval=1000;
  @Override
  public void onBackPressed() {
    L.d(TAG,"BaseActivity-->onBackPressed");
    //if(!firstClick || System.currentTimeMillis()-currentMills>timeInterval){
    //  firstClick=true;
    //  currentMills=System.currentTimeMillis();
    //} else {
    //  if(System.currentTimeMillis()-currentMills<timeInterval){
    //    onDoubleBack();
    //    firstClick=false;
    //  }
    //}
    // 正在滑动返回的时候取消返回按钮事件
    if (mSwipeBackHelper.isSliding()) {
      return;
    }
    mSwipeBackHelper.backward();

  }

  /**
   * 双击返回键
   */
  protected  void onDoubleBack(){
    com.blankj.utilcode.util.AppUtils.exitApp();
  }

  /**
   * 监听网络变化，进行界面提示，或者暂停某些操作，例如网络请求
   * @param isConnected  连接为true，掉线为false
   */
  private void onNetwordPing(boolean isConnected){
    if(canPing!=isConnected && isConnected)onNetReConnect();
    if(canPing!=isConnected && !isConnected)onNetLost();

    canPing=isConnected;
  }

  /**
   * 网络中断
   */
  protected void onNetLost(){
    L.d(TAG,"BaseActivity-->onNetLost");
  }

  /**
   * 网络重新连接上
   */
  protected void onNetReConnect(){
    L.d(TAG,"BaseActivity-->onNetReConnect");
  }

  /**
   * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
   */
  private void initSwipeBackFinish() {
    mSwipeBackHelper = new BGASwipeBackHelper(this, this);

    // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
    // 下面几项可以不配置，这里只是为了讲述接口用法。

    // 设置滑动返回是否可用。默认值为 true
    //mSwipeBackHelper.setSwipeBackEnable(true);
    // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
    mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
    // 设置是否是微信滑动返回样式。默认值为 true
    mSwipeBackHelper.setIsWeChatStyle(true);
    // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
    mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
    // 设置是否显示滑动返回的阴影效果。默认值为 true
    mSwipeBackHelper.setIsNeedShowShadow(true);
    // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
    mSwipeBackHelper.setIsShadowAlphaGradient(true);
    // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
    mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    // 设置底部导航条是否悬浮在内容上，默认值为 false
    mSwipeBackHelper.setIsNavigationBarOverlap(false);
  }
  /**
   * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
   *
   * @return
   */
  @Override public boolean isSupportSwipeBack() {
    return true;
  }
  /**
   * 正在滑动返回
   *
   * @param slideOffset 从 0 到 1
   */
  @Override public void onSwipeBackLayoutSlide(float slideOffset) {

  }
  /**
   * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
   */
  @Override public void onSwipeBackLayoutCancel() {

  }
  /**
   * 滑动返回执行完毕，销毁当前 Activity
   */
  @Override public void onSwipeBackLayoutExecuted() {
    mSwipeBackHelper.swipeBackward();
  }


}
