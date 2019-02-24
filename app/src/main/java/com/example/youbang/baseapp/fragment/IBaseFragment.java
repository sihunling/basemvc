package com.example.youbang.baseapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by legle on 2017/7/10.
 */

public interface IBaseFragment {
    /**
     * 初始化界面参数
     */
    void initParms();

    /**
     * 绑定布局
     * @return
     */
    int bindLayout();

    /**
     * 绑定渲染视图的布局文件
     * @return
     */
    View bindView();


    /**
     * 初始化控件
     */
    void initView(final View view);

    void setListeners();
    /**
     * 业务处理
     */
    void doBusiness();

    /**
     * 暂停 恢复刷新相关操作
     */
    void resume();

    /**
     * 销毁释放资源相关操作
     */
    void destroy();
}
