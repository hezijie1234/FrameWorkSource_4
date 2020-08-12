package com.zijie.aidlipc;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hezijie on 2020/7/22.
 */
public class IPC {
    public static void register(Class<?> iTask){
        Register.newInstance().saveTaskMsg(iTask);
    }

    public static void bindService(Class<? extends CommonService> service, String packageName, Context context){
        ClientRequest.getInstance().bindService(packageName,service,context);
    }

    public static <T>T  send(final Class<? extends CommonService> service, Class<T> requestInterface, final String methodName, final Object ... objects){
        if (!requestInterface.isInterface()){
            throw new RuntimeException("request interface");
        }
        final TaskId annotation = requestInterface.getAnnotation(TaskId.class);
        Response response = ClientRequest.getInstance().send(service, annotation.value(), Request.MEHOD_TYPE_ONE, methodName, convertParam(objects));
        if (response.isSuccess()){
            return (T)Proxy.newProxyInstance(requestInterface.getClassLoader(), new Class[]{requestInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Response response = ClientRequest.getInstance().send(service, annotation.value(), Request.MEHOD_TYPE_TWO, method.getName(), convertParam(args));
                    if (response.isSuccess()){
                        Class<?> returnType = method.getReturnType();
                        if (returnType != void.class && returnType != Void.class){
                            return gson.fromJson(response.getResult(),returnType);
                        }
                    }
                    return null;
                }
            });
        }
        return null;
    }
    private static Gson gson = new Gson();
    private static Parameters[] convertParam(Object ... objects){
        Parameters[] parameters = null;
        if (null != objects){
            parameters = new Parameters[objects.length];
            for (int i = 0; i < objects.length; i++) {
                parameters[i] = new Parameters(objects[i].getClass().getName(),gson.toJson(objects[i]));
            }
        }
        return parameters;
    }
}
