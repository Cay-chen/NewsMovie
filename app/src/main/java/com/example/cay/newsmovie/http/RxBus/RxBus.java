package com.example.cay.newsmovie.http.RxBus;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Cay on 2017/2/23.
 */

public class RxBus {
    private static volatile RxBus mDefaultInstance;

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }
    private final Subject<Object> _bus = PublishSubject.create().toSerialized();

    /**
     * 发送事件
     */
    public void send(int code,Object o) {

        _bus.onNext(new RxBusBaseMessage(code,o));
    }

    /**
     * 订阅事件

     */
    public<T> Observable toObservable(final int code, final Class<T> eventType) {
        return _bus.ofType(RxBusBaseMessage.class)
                .filter(new Predicate<RxBusBaseMessage>() {
                    @Override
                    public boolean test(RxBusBaseMessage rxBusBaseMessage) throws Exception {
                        //过滤code和eventType都相同的事件
                        return rxBusBaseMessage.getCode() == code && eventType.isInstance(rxBusBaseMessage.getObject());
                    }
                }).map(new Function<RxBusBaseMessage, Object>() {
                    @Override
                    public Object apply(RxBusBaseMessage msg) throws Exception {
                        return msg.getObject();
                    }
                }).cast(eventType);
    }

}
