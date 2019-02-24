package com.example.youbang.baseapp.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import com.example.youbang.baseapp.BaseApplication;
import com.example.youbang.baseapp.log.L;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by bang on 2017/10/18.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    // 保存手机信息和异常信息
    private Map<String, String> mMessage = new HashMap<>();

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    synchronized (CrashHandler.class) {
                        sInstance = new CrashHandler();
                    }
                }
            }
        }
        return sInstance;
    }

    private CrashHandler() {
    }

    /**
     * 初始化默认异常捕获
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取默认异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将此类设为默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) || !BaseApplication.isDebug) {
            // 未经过人为处理,则调用系统默认处理异常,弹出系统强制关闭的对话框
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
        } else {
            // 已经人为处理,系统自己退出
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            //Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    /**
     * 是否人为捕获异常
     *
     * @param e Throwable
     * @return true:已处理 false:未处理
     */
    private boolean handleException(Throwable e) {
        if (e == null) {// 异常是否为空
            return false;
        }

        e.printStackTrace();

        collectErrorMessages();
        saveErrorMessages(e);
        return true;
    }

    /**
     * 1.收集错误信息
     */
    private void collectErrorMessages() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = TextUtils.isEmpty(pi.versionName) ? "null" : pi.versionName;
                String versionCode = "" + pi.versionCode;
                mMessage.put("versionName", versionName);
                mMessage.put("versionCode", versionCode);
            }
            // 通过反射拿到错误信息
            Field[] fields = Build.class.getFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        mMessage.put(field.getName(), field.get(null).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.保存错误信息
     *
     * @param e Throwable
     */
    private void saveErrorMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mMessage.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        sb.append(e.toString());
        L.e("CrashHandler", sb.toString());
        //String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
        //String fileName = "crash-" + time + "-" + com.github.lazylibrary.util.RandomUtils.getRandom("asdfghjklqwertyiopzxcvbnm",5) + ".log";
        //
        //// 有无SD卡
        //if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        //    String path = Environment.getExternalStorageDirectory().getPath() + "/crash/";
        //    File dir = new File(path);
        //    if (!dir.exists()) dir.mkdirs();
        //    FileOutputStream fos = null;
        //    try {
        //        fos = new FileOutputStream(path + fileName);
        //        fos.write(sb.toString().getBytes());
        //    } catch (Exception e1) {
        //        e1.printStackTrace();
        //    } finally {
        //        if (fos != null) {
        //            try {
        //                fos.close();
        //            } catch (IOException e1) {
        //                e1.printStackTrace();
        //            }
        //        }
        //    }
        //
        //    File file=new File(path + fileName);
        //
        //    if(file.exists()){
        //        //发生错误是发送错误报告
        //
        //
        //    }
        //}
    }
}
