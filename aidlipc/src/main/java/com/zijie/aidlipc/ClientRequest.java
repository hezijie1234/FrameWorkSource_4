package com.zijie.aidlipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hezijie on 2020/7/22.
 * 客户端请求服务
 */
public class ClientRequest {
    private static volatile ClientRequest mInstance;
    private ConcurrentHashMap<Class<? extends CommonService>, IPCService> mBinders = new ConcurrentHashMap<>();

    public static ClientRequest getInstance() {
        if (mInstance == null) {
            synchronized (ClientRequest.class) {
                if (null == mInstance) {
                    mInstance = new ClientRequest();
                }
            }
        }
        return mInstance;
    }

    public void bindService(String packageName, Class<? extends CommonService> service, Context context) {
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setClassName(packageName, service.getName());
        }
        context.bindService(intent, new MyServiceCollect(service), Context.BIND_AUTO_CREATE);
    }

    public Response send(Class<? extends CommonService> service, String serviceId, int type, String method, Parameters[] parameters) {
        Request request = new Request(type, method, serviceId, parameters);
        IPCService ipcService = mBinders.get(service);
        Response response = null;
        try {
            response = ipcService.send(request);
        } catch (RemoteException e) {
            e.printStackTrace();
            response = new Response(false,null);
        }
        return response;
    }

    private class MyServiceCollect implements ServiceConnection {

        private Class<? extends CommonService> commonService;

        public MyServiceCollect(Class<? extends CommonService> service) {
            this.commonService = service;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IPCService binder = IPCService.Stub.asInterface(service);
            mBinders.put(commonService, binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinders.remove(commonService);
        }
    }
}
