package com.example.youbang.baseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.youbang.baseapp.message.NetwordChangedMSG;
import com.example.youbang.baseapp.message.RxBus;

public class NetwordChangedReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO: This method is called when the BroadcastReceiver is receiving
    // an Intent broadcast.
    RxBus.get().post(new NetwordChangedMSG());
  }
}
