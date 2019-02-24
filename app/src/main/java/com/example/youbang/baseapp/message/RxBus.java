package com.example.youbang.baseapp.message;


import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by legle on 2017/11/13.
 */

public class RxBus {

    private final Relay<Object> mBus;

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishRelay.create().toSerialized();
    }

    public static RxBus get() {
        return Holder.BUS;
    }

    public void post(Object obj) {
        mBus.accept(obj);
    }

    public <T> Observable<T> register(Class<T> tClass) {
        return mBus.ofType(tClass);
    }
    public void unregister(Disposable disposable){
        if(null!=disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }

}
