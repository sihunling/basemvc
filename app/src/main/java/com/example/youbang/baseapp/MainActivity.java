package com.example.youbang.baseapp;


import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.widget.Toast;

import com.example.youbang.baseapp.activity.BaseActivity;
import com.example.youbang.baseapp.message.NetwordChangedMSG;
import com.example.youbang.baseapp.message.RxBus;
import com.example.youbang.baseapp.utils.Main2Activity;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

  @Override public int bindLayout() {
    return R.layout.activity_main;
  }

  @Override public void doBusiness() {
    super.doBusiness();

    Toast.makeText(this,"open succed",Toast.LENGTH_LONG).show();



    //Intent intent=new Intent(MainActivity.this,Main2Activity.class);
    //    //startActivity(intent);
    //RxBus.get().register(NetwordChangedMSG.class)
    //    //.as(AutoDispose.<NetwordChangedMSG>autoDisposable(AndroidLifecycleScopeProvider.from(
    //    //     this,Lifecycle.Event.ON_DESTROY)))
    //    .subscribe(new Consumer<NetwordChangedMSG>() {
    //      @Override public void accept(NetwordChangedMSG networdChangedMSG) throws Exception {
    //
    //      }
    //    });
  }
}
