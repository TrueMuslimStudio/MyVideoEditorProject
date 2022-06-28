// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkAuthInfo;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TuSdkAuth
{
    private static TuSdkAuth a;
    
    private TuSdkAuth() {
    }
    
    public static TuSdkAuth shared() {
        if (TuSdkAuth.a == null) {
            TuSdkAuth.a = new TuSdkAuth();
        }
        return TuSdkAuth.a;
    }
    
    public LocalAuthInfo localAuthInfo() {
        final LocalAuthInfo localAuthInfo = TuSdkContext.sharedPreferences().loadSharedCacheObject("tusdk_local_auth_info");
        if (localAuthInfo == null) {
            return new LocalAuthInfo();
        }
        return localAuthInfo;
    }
    
    public void requestRemoteAuthInfo(final AuthInfoCallback authInfoCallback) {
        if (StringHelper.isEmpty(SdkValid.shared.a())) {
            TLog.e("app key not found! Please see: http://tusdk.com/docs/android/get-started", new Object[0]);
            if (authInfoCallback != null) {
                authInfoCallback.onAuthInfo(null);
            }
            return;
        }
        final LocalAuthInfo localAuthInfo = this.localAuthInfo();
        if (localAuthInfo != null && !localAuthInfo.canRequestAuthUpdate()) {
            if (localAuthInfo.a != null) {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                simpleDateFormat.setCalendar(localAuthInfo.a);
                TLog.i("No auth permissions need to be updated for the time being. The next update permission time is %s ", simpleDateFormat.format(localAuthInfo.a.getTime()));
            }
            if (authInfoCallback != null && localAuthInfo != null) {
                authInfoCallback.onAuthInfo(localAuthInfo.remoteAuthInfo);
            }
            return;
        }
        final TuSdkHttpHandler tuSdkHttpHandler = new TuSdkHttpHandler() {
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
                final TuSdkAuthInfo remoteAuthInfo = tuSdkHttpHandler.getJson().getJsonWithType(TuSdkAuthInfo.class);
                if (remoteAuthInfo == null || !remoteAuthInfo.isValid()) {
                    final LocalAuthInfo localAuthInfo = new LocalAuthInfo();
                    localAuthInfo.b();
                    localAuthInfo.persistence();
                    if (authInfoCallback != null) {
                        authInfoCallback.onAuthInfo(remoteAuthInfo);
                    }
                    return;
                }
                StatisticsManger.appendComponent(ComponentActType.getAppAuthActionSuccess);
                localAuthInfo.remoteAuthInfo = remoteAuthInfo;
                localAuthInfo.a();
                localAuthInfo.persistence();
                if (authInfoCallback != null) {
                    authInfoCallback.onAuthInfo(remoteAuthInfo);
                }
            }
            
            @Override
            protected void onRequestedFailed(final TuSdkHttpHandler tuSdkHttpHandler) {
                StatisticsManger.appendComponent(ComponentActType.getAppAuthActionFail);
                localAuthInfo.b();
                localAuthInfo.persistence();
                if (authInfoCallback != null) {
                    authInfoCallback.onAuthInfo(localAuthInfo.remoteAuthInfo);
                }
            }
        };
        final TuSdkHttpParams tuSdkHttpParams = new TuSdkHttpParams();
        tuSdkHttpParams.put("app_key", SdkValid.shared.a());
        TuSdkHttpEngine.auth().post("/app/authorize", tuSdkHttpParams, true, tuSdkHttpHandler);
        StatisticsManger.appendComponent(ComponentActType.getUpdatedAppAuthAction);
    }
    
    public static class LocalAuthInfo extends JsonBaseBean implements Serializable
    {
        public TuSdkAuthInfo remoteAuthInfo;
        public String bundleMasterMD5;
        private GregorianCalendar a;
        
        public boolean canRequestAuthUpdate() {
            if (SdkValid.shared.imageServiceExpireSeconds() <= 0L && SdkValid.shared.videoServiceExpireSeconds() <= 0L && SdkValid.shared.evaServiceExpireSeconds() <= 0L) {
                return false;
            }
            if (this.a == null) {
                return true;
            }
            final String master = SdkValid.shared.geTuSdkConfigs().master;
            if (master != null && this.bundleMasterMD5 != null && !this.bundleMasterMD5.equalsIgnoreCase(StringHelper.md5(master))) {
                TLog.i("An update to the bundle resource has been detected and permission information needs to be requested.", new Object[0]);
                return true;
            }
            return Calendar.getInstance().compareTo((Calendar)this.a) >= 0;
        }
        
        public void persistence() {
            final String master = SdkValid.shared.geTuSdkConfigs().master;
            if (master != null) {
                this.bundleMasterMD5 = StringHelper.md5(master);
            }
            TuSdkContext.sharedPreferences().saveSharedCacheObject("tusdk_local_auth_info", this);
        }
        
        void a() {
            final double c = this.c();
            final GregorianCalendar a = new GregorianCalendar();
            if (c > 30.0) {
                a.add(2, 1);
                a.set(5, 1);
                a.set(11, 0);
                a.set(12, 0);
                a.set(13, 0);
                a.set(14, 0);
            }
            else if (c >= 0.0 && c <= 30.0) {
                a.add(5, 1);
                a.set(11, 0);
                a.set(12, 0);
                a.set(13, 0);
                a.set(14, 0);
            }
            else if (c < 0.0) {
                a.add(1, 10);
            }
            this.a = a;
        }
        
        void b() {
            final double c = this.c();
            GregorianCalendar a = new GregorianCalendar();
            if (c >= 0.0) {
                a.add(11, 6);
                a.set(12, 0);
                a.set(13, 0);
                a.set(14, 0);
            }
            else {
                a = new GregorianCalendar();
            }
            this.a = a;
        }
        
        private long d() {
            if ((SdkValid.shared.imageServiceExpire() || SdkValid.shared.videoServiceExpire() || SdkValid.shared.evaServiceExpire()) && this.remoteAuthInfo != null && this.remoteAuthInfo.service_expire != null) {
                return this.remoteAuthInfo.service_expire.getTimeInMillis() / 1000L;
            }
            return Math.min(SdkValid.shared.imageServiceExpireSeconds(), Math.min(SdkValid.shared.videoServiceExpireSeconds(), SdkValid.shared.evaServiceExpireSeconds()));
        }
        
        double c() {
            final long n = this.d() - new GregorianCalendar().getTimeInMillis() / 1000L;
            if (n < 0L) {
                return -1.0;
            }
            return (double)(n / 60L / 60L / 24L);
        }
    }
    
    public interface AuthInfoCallback
    {
        void onAuthInfo(final TuSdkAuthInfo p0);
    }
}
