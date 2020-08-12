package com.zijie.aidlipc;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hezijie on 2020/7/22.
 */
public class Register {

    private static volatile Register register;
    //存储任务信息
    private ConcurrentHashMap<String,Class<?>> mTasks = new ConcurrentHashMap<>();
    //存储每个任务里面的方法信息
    private ConcurrentHashMap<Class<?>,ConcurrentHashMap<String, Method>> mMethods = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> mResultObj = new ConcurrentHashMap<>();
    public static Register newInstance(){
        if (null == register){
            synchronized (Register.class){
                if (null == register){
                    register = new Register();
                }
            }
        }
        return register;
    }

    public void saveTaskMsg(Class<?> task){
        TaskId annotation = task.getAnnotation(TaskId.class);
        if (null == annotation){
            throw new RuntimeException("类信息错误");
        }
        String taskId = annotation.value();
        mTasks.put(taskId,task);
        ConcurrentHashMap<String, Method> methodMap = mMethods.get(task);
        if (null == methodMap){
            methodMap = new ConcurrentHashMap<>();
            mMethods.put(task,methodMap);
        }
        Method[] methods = task.getMethods();
        for (int i = 0; i < methods.length; i++) {
            methodMap.put(methods[i].getName() + convertMethodName(methods[i]),methods[i]);
        }
    }

    public Method getTaskMethod(Object[] objects,String methodName,String serviceId){
        Class<?> taskId = mTasks.get(serviceId);
        ConcurrentHashMap<String, Method> methodConcurrentHashMap = mMethods.get(taskId);
        String name = methodName;
        for (int i = 0; i < objects.length; i++) {
            name +=objects[i].getClass().getName();
        }
        return methodConcurrentHashMap.get(name);
    }

    public Object getObj(String serviceId) {
        return mResultObj.get(serviceId);
    }

    public void addObj(Object o,String serviceId) {
        mResultObj.put(serviceId,o);
    }

    private String convertMethodName(Method method){
        String name = "";
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            name += parameterTypes[i].getName();
        }
        Log.e("111", "convertMethodName: 存储的方法名称" +  name);
        return name;
    }


}
