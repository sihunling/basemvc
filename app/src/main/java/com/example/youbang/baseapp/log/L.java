package com.example.youbang.baseapp.log;


import com.example.youbang.baseapp.BaseApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;

import static com.example.youbang.baseapp.BaseApplication.isDebug;

/**
 * Created by legle on 2017/7/5.
 */

public class L {

    public static void init(){
            FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
            .tag("custom")
            .build();
       Logger.addLogAdapter(new DiskLogAdapter(formatStrategy) {
        @Override public boolean isLoggable(int priority, String tag) {
            return BaseApplication.isDebug;
        }
    });

        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override public boolean isLoggable(int priority, String tag) {
                return BaseApplication.isDebug;}
            });

    }



    public static void i(String tag,String message){
        try {
                Logger.i(message,tag);
        }catch (Exception e){
            e.printStackTrace();
        }

            //Log.i(tag,message);
    }

    public static void v(String tag,String message){


            //Log.v(tag,message);
        try {
                Logger.v(message,tag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void d(String tag,String message){


            //Log.d(tag,message);

        try {
                Logger.d(message,tag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void e(String tag, String msg, Throwable tr) {

        //Log.e(tag, msg, tr);
        try {

                Logger.e(msg,tag,tr);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void e(String tag,String message){


            //Log.e(tag,message);
        try {

            Logger.e(message,tag);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
