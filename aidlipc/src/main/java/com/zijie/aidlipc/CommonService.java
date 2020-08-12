package com.zijie.aidlipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hezijie on 2020/7/22.
 */
public class CommonService extends Service {


    @Override
    public IBinder onBind(Intent intent) {

        return new IPCService.Stub() {
            @Override
            public Response send(Request request) throws RemoteException {
                int type = request.getType();
                //根据方法名称来确定任务执行的具体方法
                String methodName = request.getMethodName();
                Parameters[] parameters = request.getParameters();
                //根据serviceId来确定执行哪一个任务，
                String serviceId = request.getServiceId();
                //将参数转换过来等会调用方法时使用。
                Object[] field = getField(parameters);
                Method taskMethod = Register.newInstance().getTaskMethod(field, methodName, serviceId);
                switch (type){
                    case  Request.MEHOD_TYPE_ONE:
                        try {
                            Object o = taskMethod.invoke(null, field);
                            Register.newInstance().addObj(o,serviceId);
                            return new Response(true,null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Response(false,null);
                        }
                    case  Request.MEHOD_TYPE_TWO:
                        Object obj = Register.newInstance().getObj(serviceId);
                        try {
                            Object invoke = taskMethod.invoke(obj, field);
                            return new Response(true,gson.toJson(invoke));
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new Response(false,null);
                        }
                }
                return null;
            }
        };
    }
    private Gson gson = new Gson();
    private Object[] getField(Parameters[] parameters){
        Object[] objects = new Object[parameters.length];
        try{
            if (null != parameters)
                for (int i = 0; i < parameters.length; i++) {
                    objects[i] = gson.fromJson(parameters[i].getValue(),Class.forName(parameters[i].getType()));
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objects;
    }

    public static class MyService1 extends CommonService{

    }
    public static class MyService2 extends CommonService{

    }
    public static class MyService3 extends CommonService{

    }
}
