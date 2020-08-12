package com.zijie.aidlipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hezijie on 2020/7/22.
 */
public class Request implements Parcelable {

    //方法的请求类型
    public static final  int MEHOD_TYPE_ONE = 1;
    public static final  int MEHOD_TYPE_TWO = 2;
    private int type;
    private String methodName;
    private String serviceId;
    private Parameters[] parameters;

    public Request(int type, String methodName, String serviceId, Parameters[] parameters) {
        this.type = type;
        this.methodName = methodName;
        this.serviceId = serviceId;
        this.parameters = parameters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Parameters[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameters[] parameters) {
        this.parameters = parameters;
    }

    protected Request(Parcel in) {
        type = in.readInt();
        methodName = in.readString();
        serviceId = in.readString();
        parameters = in.createTypedArray(Parameters.CREATOR);
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(methodName);
        dest.writeString(serviceId);
        dest.writeTypedArray(parameters, flags);
    }
}
