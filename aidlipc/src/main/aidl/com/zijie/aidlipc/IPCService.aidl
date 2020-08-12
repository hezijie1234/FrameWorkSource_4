// IPCService.aidl
package com.zijie.aidlipc;
import com.zijie.aidlipc.Request;
import com.zijie.aidlipc.Response;
// Declare any non-default types here with import statements

interface IPCService {
    Response send(in Request request);
}
