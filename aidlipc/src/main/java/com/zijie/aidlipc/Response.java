package com.zijie.aidlipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hezijie on 2020/7/22.
 */
public class Response implements Parcelable {
    private boolean isSuccess;
    private String result;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Response(boolean isSuccess, String result) {
        this.isSuccess = isSuccess;
        this.result = result;
    }

    protected Response(Parcel in) {
        isSuccess = in.readByte() != 0;
        result = in.readString();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSuccess ? 1 : 0));
        dest.writeString(result);
    }
}
