package com.zijie.frameworksource_4;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zijie.base.BaseApplication;
import com.zijie.base.baseservices.IAppInfoService;
import com.zijie.base.baseservices.ILoginService;
import com.zijie.frameworksource_4.webview.login.LoginImpl;
import com.zijie.servicemanager.ServiceManager;
import com.zijie.xiangxuewebview.BuildConfig;


public class WebViewApplication extends BaseApplication implements IAppInfoService {

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        setIsDebug(BuildConfig.DEBUG);
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application

        //初始化manager
        ServiceManager.init(this);
        ServiceManager.publishService(IAppInfoService.APP_INFO_SERVICE_NAME, WebViewApplication.class.getName());
        ServiceManager.publishService(ILoginService.LOGIN_SERVICE_NAME, LoginImpl.class.getName());
    }

    @Override
    public String getApplicationName() {
        return getString(com.zijie.xiangxuewebview.R.string.app_name);
    }

    @Override
    public String getApplciationVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getApplicationVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean getApplicationDebug() {
        return BuildConfig.DEBUG;
    }
}
