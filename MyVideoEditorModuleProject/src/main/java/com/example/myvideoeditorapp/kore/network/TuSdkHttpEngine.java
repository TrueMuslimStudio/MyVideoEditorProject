// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.myvideoeditorapp.kore.TuSdkConfigs;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.http.RequestHandle;
import com.example.myvideoeditorapp.kore.http.ResponseHandlerInterface;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.TuSdkDeviceInfo;
import com.example.myvideoeditorapp.kore.utils.hardware.HardwareHelper;

import java.util.Calendar;

public class TuSdkHttpEngine
{
    public static final String SDK_TYPE_IMAGE = "1";
    public static final int ENVIRONMENT_LOCAL = 0;
    public static final int ENVIRONMENT_TEST = 1;
    public static final int ENVIRONMENT_PRODUCTION = 2;
    public static final int ENVIRONMENT = 2;
    public static final String NETWORK_PATH = "api";
    public static final String WEB_PATH = "web";
    public static final String SRV_PATH = "srv";
    public static final String NETWORK_DOMAIN;
    public static final String NETWORK_WEB_DOMAIN;
    public static final String SERVICE_DOMAIN;
    public static final String NETWORK_AUTH_DOMAIN;
    public static final int NETWORK_PORT;
    public static final int SERVICE_PORT;
    public static String API_DOMAIN;
    public static String WEB_DOMAIN;
    public static String WEB_API_DOMAIN;
    public static String AUTH_DOMAIN;
    public static String WEB_PIC_DOMAIN;
    public static String SRV_DOMAIN;
    public static final boolean DEBUG;
    private boolean a;
    private static TuSdkHttpEngine b;
    private static TuSdkHttpEngine c;
    private static TuSdkHttpEngine d;
    private static TuSdkHttpEngine e;
    public static boolean useSSL;
    private Context f;
    private TuSdkHttp g;
    private String h;
    private String i;
    private String j;
    private String k;
    private int l;
    private BroadcastReceiver m;
    
    public static TuSdkHttpEngine shared() {
        return TuSdkHttpEngine.b;
    }
    
    public static TuSdkHttpEngine service() {
        return TuSdkHttpEngine.c;
    }
    
    public static TuSdkHttpEngine webAPIEngine() {
        return TuSdkHttpEngine.d;
    }
    
    public static TuSdkHttpEngine auth() {
        return TuSdkHttpEngine.e;
    }
    
    public static TuSdkHttpEngine init(final TuSdkConfigs tuSdkConfigs, final String s, final Context context) {
        if (TuSdkHttpEngine.b == null && TuSdkHttpEngine.c == null && TuSdkHttpEngine.d == null && TuSdkHttpEngine.e == null && tuSdkConfigs != null) {
            final String s2 = TuSdkHttpEngine.useSSL ? "https://%s/%s" : "http://%s/%s";
            TuSdkHttpEngine.API_DOMAIN = String.format("http://%s/%s", TuSdkHttpEngine.NETWORK_DOMAIN, "api");
            TuSdkHttpEngine.WEB_DOMAIN = String.format(s2, TuSdkHttpEngine.NETWORK_WEB_DOMAIN, "web");
            TuSdkHttpEngine.SRV_DOMAIN = String.format(s2, TuSdkHttpEngine.SERVICE_DOMAIN, "srv");
            TuSdkHttpEngine.WEB_API_DOMAIN = String.format(s2, TuSdkHttpEngine.NETWORK_WEB_DOMAIN, "api");
            TuSdkHttpEngine.WEB_PIC_DOMAIN = String.format(s2, "img.tusdk.com", "api");
            TuSdkHttpEngine.AUTH_DOMAIN = String.format("https://%s/%s", TuSdkHttpEngine.NETWORK_AUTH_DOMAIN, "api");
            TuSdkHttpEngine.b = new TuSdkHttpEngine(tuSdkConfigs, s, context, TuSdkHttpEngine.API_DOMAIN, TuSdkHttpEngine.NETWORK_PORT);
            TuSdkHttpEngine.c = new TuSdkHttpEngine(tuSdkConfigs, s, context, TuSdkHttpEngine.SRV_DOMAIN, TuSdkHttpEngine.SERVICE_PORT);
            TuSdkHttpEngine.d = new TuSdkHttpEngine(tuSdkConfigs, s, context, TuSdkHttpEngine.WEB_API_DOMAIN, TuSdkHttpEngine.NETWORK_PORT);
            TuSdkHttpEngine.e = new TuSdkHttpEngine(tuSdkConfigs, s, context, TuSdkHttpEngine.AUTH_DOMAIN, TuSdkHttpEngine.NETWORK_PORT);
        }
        return TuSdkHttpEngine.b;
    }
    
    private TuSdkHttp a() {
        return this.g;
    }
    
    public String userIdentify() {
        return this.j;
    }
    
    public void setUserIdentify(final Object o) {
        if (this.j == null) {
            this.j = null;
            this.g.removeHeader("x-client-user");
        }
        else {
            this.j = o.toString();
            this.g.addHeader("x-client-user", this.j);
        }
    }
    
    private TuSdkHttpEngine(final TuSdkConfigs tuSdkConfigs, final String i, final Context f, final String k, final int l) {
        this.a = false;
        this.f = f;
        this.i = i;
        this.k = k;
        this.l = l;
        this.a(i);
    }
    
    private void a(final String s) {
        (this.g = new TuSdkHttp(this.l)).setEnableRedirct(true);
        this.g.setMaxConnections(2);
        this.g.addHeader("x-client-identifier", "android");
        this.g.addHeader("uuid", this.uniqueDeviceID());
        if (s != null) {
            this.g.addHeader("x-client-dev", s);
        }
        this.b();
    }
    
    private void b() {
        final StringBuilder sb = new StringBuilder(String.format("%s:%s", 24, StringHelper.Base64Encode("3.2.5")));
        sb.append(String.format("|%s:%s", 40, StringHelper.Base64Encode(TuSdkDeviceInfo.getModel())));
        sb.append(String.format("|%s:%s", 56, StringHelper.Base64Encode(TuSdkDeviceInfo.getOSVersion())));
        sb.append(String.format("|%s:%s", 72, StringHelper.Base64Encode(this.f.getPackageName())));
        sb.append(String.format("|%s:%s", 88, StringHelper.Base64Encode(ContextUtils.getVersionName(this.f))));
        sb.append(String.format("|%s:%s", 280, StringHelper.Base64Encode(TuSdkDeviceInfo.getVender())));
        sb.append(String.format("|%s:%s", 296, StringHelper.Base64Encode(TuSdkDeviceInfo.getIP())));
        sb.append(String.format("|%s:%s", 312, StringHelper.Base64Encode(TuSdkDeviceInfo.getAndroidID())));
        this.a = (TuSdkDeviceInfo.getLocation() != null && !TuSdkDeviceInfo.getLocation().isEmpty());
        sb.append(String.format("|%s:%s", 328, StringHelper.Base64Encode(TuSdkDeviceInfo.getLocation())));
        if (SdkValid.shared.appType() > 0) {
            sb.append(String.format("|%s:%s", 376, StringHelper.Base64Encode(String.format("%s", SdkValid.shared.appType()))));
        }
        this.g.addHeader("x-client-bundle", StringHelper.Base64Encode(sb.toString()));
    }
    
    public RequestHandle get(final String s, final boolean b, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        return this.get(s, null, b, responseHandlerInterface);
    }
    
    public RequestHandle get(String urlBuild, final TuSdkHttpParams tuSdkHttpParams, final boolean b, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        urlBuild = this.urlBuild(urlBuild, b);
        return this.a().get(urlBuild, tuSdkHttpParams, responseHandlerInterface);
    }
    
    public RequestHandle get(String urlBuild, final TuSdkHttpParams tuSdkHttpParams, final boolean b, final boolean b2, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        if (b) {
            urlBuild = this.urlBuild(urlBuild, b2);
        }
        return this.a().get(urlBuild, tuSdkHttpParams, responseHandlerInterface);
    }
    
    public RequestHandle post(final String s, final boolean b, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        return this.post(s, null, b, responseHandlerInterface);
    }
    
    public RequestHandle post(String urlBuild, final TuSdkHttpParams tuSdkHttpParams, final boolean b, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        urlBuild = this.urlBuild(urlBuild, b);
        return this.a().post(urlBuild, tuSdkHttpParams, responseHandlerInterface);
    }
    
    public RequestHandle postService(String serviceUrlBuild, final TuSdkHttpParams tuSdkHttpParams, final ResponseHandlerInterface responseHandlerInterface) {
        if (!this.a) {
            this.b();
        }
        serviceUrlBuild = this.serviceUrlBuild(serviceUrlBuild, tuSdkHttpParams, true);
        return this.a().post(serviceUrlBuild, tuSdkHttpParams, responseHandlerInterface);
    }
    
    protected String urlBuild(final String str, final boolean b) {
        if (str == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(this.k).append(str);
        if (b) {
            this.a(append, str);
        }
        this.b(append, str);
        return append.toString();
    }
    
    private StringBuilder a(final StringBuilder sb, final String str) {
        if (sb == null) {
            return null;
        }
        final long n = Calendar.getInstance().getTimeInMillis() / 1000000L * 1000L;
        final String md5 = StringHelper.md5(str + "_" + this.uniqueDeviceID() + "_" + n);
        if (sb.indexOf("?") == -1) {
            sb.append("?");
        }
        else {
            sb.append("&");
        }
        sb.append("hash=").append(md5);
        sb.append("&t=").append(n);
        return sb;
    }
    
    protected String serviceUrlBuild(final String str, final TuSdkHttpParams tuSdkHttpParams, final boolean b) {
        if (str == null) {
            return null;
        }
        final StringBuilder append = new StringBuilder(this.k).append(str);
        if (b && append != null) {
            final long l = Calendar.getInstance().getTimeInMillis() / 1000000L * 1000L;
            tuSdkHttpParams.add("pid", StringHelper.md5(this.i));
            tuSdkHttpParams.add("t", String.valueOf(l));
            tuSdkHttpParams.add("sign", StringHelper.md5(tuSdkHttpParams.toPairString() + this.i));
        }
        return append.toString();
    }
    
    public RequestHandle postService(String serviceUrlBuild, final boolean b, final TuSdkHttpParams tuSdkHttpParams, final ResponseHandlerInterface responseHandlerInterface) {
        if (b) {
            serviceUrlBuild = this.serviceUrlBuild(serviceUrlBuild, tuSdkHttpParams, true);
        }
        return this.a().post(serviceUrlBuild, tuSdkHttpParams, responseHandlerInterface);
    }
    
    private StringBuilder b(final StringBuilder sb, final String s) {
        if (sb == null) {
            return null;
        }
        final String packageName = this.f.getPackageName();
        if (sb.indexOf("?") == -1) {
            sb.append("?");
        }
        else {
            sb.append("&");
        }
        sb.append("app=").append(packageName);
        return sb;
    }
    
    public String getWebUrl(final String str, final boolean b) {
        if (str == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(TuSdkHttpEngine.WEB_DOMAIN);
        sb.append(str);
        this.b(sb, str);
        if (!b) {
            return sb.toString();
        }
        if (sb.indexOf("?") == -1) {
            sb.append("?");
        }
        else {
            sb.append("&");
        }
        sb.append("uuid=").append(this.uniqueDeviceID());
        if (this.i != null) {
            sb.append("&devid=").append(this.i);
        }
        if (this.j != null) {
            sb.append("&uid=").append(this.j);
        }
        sb.append("&v=").append(12);
        this.a(sb, str);
        return sb.toString();
    }
    
    public String uniqueDeviceID() {
        if (this.h != null) {
            return this.h;
        }
        this.h = TuSdkContext.sharedPreferences().loadSharedCache("TUSDK_DeviceUUID");
        if (this.h != null) {
            return this.h;
        }
        this.h = StringHelper.md5(StringHelper.uuid() + "_" + Calendar.getInstance().getTimeInMillis() / 1000000L * 1000L);
        TuSdkContext.sharedPreferences().saveSharedCache("TUSDK_DeviceUUID", this.h);
        return this.h;
    }
    
    public String getDevId() {
        return this.i;
    }
    
    protected void overseeNetwork() {
        if (this.m != null) {
            return;
        }
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.m = new BroadcastReceiver() {
            public void onReceive(final Context context, final Intent intent) {
                TuSdkHttpEngine.this.onNetworkStateChanged(HardwareHelper.isNetworkAvailable(context));
            }
        };
        this.f.registerReceiver(this.m, intentFilter);
    }
    
    protected void cancelOverseeNetwork() {
        if (this.m != null) {
            this.f.unregisterReceiver(this.m);
        }
        this.m = null;
    }
    
    protected void onNetworkStateChanged(final boolean b) {
        TLog.d("connected: %s", b);
    }
    
    static {
        switch (2) {
            case 2: {
                NETWORK_DOMAIN = "api.tusdk.com";
                NETWORK_WEB_DOMAIN = "m.tusdk.com";
                NETWORK_AUTH_DOMAIN = "auth.tusdk.com";
                NETWORK_PORT = 80;
                SERVICE_DOMAIN = "srv2.tusdk.com";
                SERVICE_PORT = 80;
                DEBUG = false;
                break;
            }
            case 1: {
                NETWORK_DOMAIN = "10.10.10.25";
                NETWORK_WEB_DOMAIN = "m.tusdk.com";
                NETWORK_AUTH_DOMAIN = "auth.tusdk.com";
                NETWORK_PORT = 80;
                SERVICE_DOMAIN = "srv2.tusdk.com";
                SERVICE_PORT = 80;
                DEBUG = false;
                break;
            }
            default: {
                NETWORK_DOMAIN = "192.168.199.152:80";
                NETWORK_WEB_DOMAIN = "192.168.199.152:80";
                NETWORK_AUTH_DOMAIN = "auth.tusdk.com";
                NETWORK_PORT = 80;
                SERVICE_DOMAIN = "192.168.199.152:80";
                SERVICE_PORT = 80;
                DEBUG = true;
                break;
            }
        }
        TuSdkHttpEngine.useSSL = true;
    }
}
