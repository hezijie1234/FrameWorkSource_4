package com.zijie.frameworksource_4.webview;



import com.zijie.base.baseservices.ILoginListener;
import com.zijie.base.baseservices.ILoginService;
import com.zijie.servicemanager.ServiceManager;

import java.util.HashMap;
import java.util.Map;

public class WebviewLoginListner extends ILoginListener {
    private Map<String, Object> mParams;
    private static ILoginService mLoginService = (ILoginService) ServiceManager.getService(ILoginService.LOGIN_SERVICE_NAME);

    public WebviewLoginListner(Map<String, Object> params) {
        mParams = params;
    }

    @Override
    public void onLogin() {
        jsCallLogin(mParams);
        mLoginService.unregisterListener(this);
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onCancel() {

    }

    /**
     * 调用js方法，讲参数信息传递给h5
     */
    private void jsCallLogin(Map<String, Object> params) {

    }
}
