package com.example.youbang.baseapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youbang.baseapp.R;

/**
 *fragment 基类
 * 1.需要一个newinstance方法
 * 2.设置布局
 * 3.创建view
 * 4.初始化数据
 * 5.是否第一次可见，只有第一次才会加载数据
 */
public class BaseFragment extends Fragment implements IBaseFragment {
  //
  protected View mContentView=null;
  public BaseFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   * @return A new instance of fragment BaseFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static BaseFragment newInstance(@Nullable Bundle args) {
    BaseFragment fragment = new BaseFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //第一步：初始化参数
    initParms();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    if(null==mContentView){
    //  第二步：创建视图
      mContentView=bindView();
      if(null==mContentView)
        mContentView=inflater.inflate(bindLayout(), container, false);
    //  添加监听
      setListeners();
    //  加载数据等逻辑
      doBusiness();
    }
    // Inflate the layout for this fragment
    return mContentView;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

  }

  @Override
  public void onDetach() {
    super.onDetach();

  }

  @Override public void onResume() {
    super.onResume();
    //可以进行刷新等
    resume();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    //执行清理工作
    destroy();
  }



  @Override public void initParms() {

  }

  @Override public int bindLayout() {
    return 0;
  }

  @Override public View bindView() {
    return null;
  }

  @Override public void initView(View view) {

  }

  @Override public void setListeners() {

  }

  @Override public void doBusiness() {

  }

  @Override public void resume() {

  }

  @Override public void destroy() {

  }

}
