package com.example.youbang.baseapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * 作者 bang
 * 时间 2019/2/23 17:34
 * 注释 activity需要实现的接口，概括统一的activity加载过程
 * 修改时间 2019/2/23 17:34
 * 注释
 */

public interface IBaseActivity {

    /**
     * 初始化界面参数
     */
    void initParms();

    /**
     * 绑定布局
     *
     */
    int bindLayout();

    /**
     * 绑定渲染视图的布局文件
     *
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
