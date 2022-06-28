// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore;

import android.content.Context;
import android.os.Build;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.secret.LogStashManager;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterManager;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.TuSdkDecodecPatch;
import com.example.myvideoeditorapp.kore.utils.TuSdkLocation;
import com.example.myvideoeditorapp.kore.utils.hardware.Camera2Helper;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCamera;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCamera2;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraInterface;
import com.example.myvideoeditorapp.kore.utils.image.ImageLoaderHelper;
import com.example.myvideoeditorapp.kore.view.widget.TuMessageHubImpl;
import com.example.myvideoeditorapp.kore.view.widget.TuMessageHubInterface;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushManager;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

import java.io.File;
import java.util.List;

public class TuSdk
{
    public static final String SDK_VERSION = "3.2.5";
    public static final String BUILD_VERSION = "3.2.5-202106071650";
    public static final int SDK_CODE = 12;
    public static final String SDK_CONFIGS = "lsq_tusdk_configs.json";
    public static final String TEMP_DIR = "lasFilterTemp";
    public static final String SAMPLE_DIR = "lasFilterSamples";
    public static final String DOWNLOAD_DIR = "lasDownload";
    public static final String SAMPLE_EXTENSION = "lfs";
    private static TuSdk a;
    private TuMessageHubInterface b;
    private static Class<?> c;
    
    public static TuSdk shared() {
        return TuSdk.a;
    }
    
    public static TuSdk init(final Context context, final String s) {
        return init(context, s, null);
    }
    
    public static TuSdk init(final Context context, final String s, final String s2) {
        if (!ThreadHelper.isMainThread()) {
            TLog.e("Error Thread, Please Init On Main Thread", new Object[0]);
            return null;
        }
        if (TuSdk.a == null && context != null) {
            TuSdk.a = new TuSdk(context, s, s2);
        }
        return TuSdk.a;
    }
    
    private TuSdk(final Context context, final String s, final String s2) {
        if (context == null) {
            TLog.e("TuSdk init : The context cannot be null.", new Object[0]);
            return;
        }
        if (StringHelper.isEmpty(s)) {
            TLog.e("TuSdk init : The appKey cannot be null or empty.", new Object[0]);
            return;
        }
        if (SdkValid.shared.sdkValid(context, s, s2)) {
            this.a();
        }
        else {
            TLog.e("Incorrect app key! Please see: http://tusdk.com/docs/help/package-name-and-app-key", new Object[0]);
        }
    }
    
    private void a() {
        if (TuSdk.c != null) {
            TuSdkContext.ins().setResourceClazz(TuSdk.c);
        }
        TuSpecialScreenHelper.dealNotchScreen();
        TuSdkLocation.init(TuSdkContext.context());
        SelesContext.init(TuSdkContext.context());
        TuSdkHttpEngine.init(SdkValid.shared.geTuSdkConfigs(), SdkValid.shared.getDeveloperId(), TuSdkContext.context());
        ImageLoaderHelper.initImageCache(TuSdkContext.context(), TuSdkContext.getScreenSize());
        FilterManager.init(SdkValid.shared.geTuSdkConfigs());
        StickerLocalPackage.init(SdkValid.shared.geTuSdkConfigs());
        BrushManager.init(SdkValid.shared.geTuSdkConfigs());
        StatisticsManger.init(TuSdkContext.context(), getAppTempPath());
        LogStashManager.init(getAppTempPath());
        TuSdkDecodecPatch.upDataPathFile();
        SdkValid.shared.checkAppAuth();
        TLog.e("BuildVersion %s", "3.2.5-202106071650");
    }
    
    public static String userIdentify() {
        if (TuSdkHttpEngine.shared() == null) {
            return null;
        }
        return TuSdkHttpEngine.shared().userIdentify();
    }
    
    public static void setUserIdentify(final Object userIdentify) {
        if (TuSdkHttpEngine.shared() == null) {
            return;
        }
        TuSdkHttpEngine.shared().setUserIdentify(userIdentify);
    }
    
    public static void enableDebugLog(final boolean b) {
        if (b) {
            TLog.enableLogging("TuSdk");
        }
        else {
            TLog.disableLogging();
        }
    }
    
    public static void setUseSSL(final boolean useSSL) {
        TuSdkHttpEngine.useSSL = useSSL;
    }
    
    public static TuSdkContext appContext() {
        if (TuSdk.a != null && SdkValid.shared.isVaild()) {
            return TuSdkContext.ins();
        }
        return null;
    }
    
    public static void setResourcePackageClazz(final Class<?> c) {
        TuSdk.c = c;
    }
    
    public static File getAppTempPath() {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        return TuSdkContext.getAppCacheDir("lasFilterTemp", false);
    }
    
    public static File getAppDownloadPath() {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        return TuSdkContext.getAppCacheDir("lasDownload", false);
    }
    
    public static List<String> filterNames() {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        return filterManager().getFilterNames();
    }
    
    public static void checkFilterManager(final FilterManager.FilterManagerDelegate filterManagerDelegate) {
        if (!SdkValid.shared.isVaild()) {
            return;
        }
        FilterManager.shared().checkFilterManager(filterManagerDelegate);
    }
    
    public static FilterManager filterManager() {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        return FilterManager.shared();
    }
    
    public static StickerLocalPackage stickerManager() {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        return StickerLocalPackage.shared();
    }
    
    public static void setMessageHub(final TuMessageHubInterface b) {
        if (TuSdk.a == null) {
            return;
        }
        TuSdk.a.b = b;
    }
    
    public static TuMessageHubInterface messageHub() {
        if (TuSdk.a == null) {
            return null;
        }
        if (TuSdk.a.b == null) {
            TuSdk.a.b = new TuMessageHubImpl();
        }
        return TuSdk.a.b;
    }
    
    public static TuSdkStillCameraInterface camera(final Context context, final CameraConfigs.CameraFacing cameraFacing, final RelativeLayout relativeLayout) {
        return camera(context, cameraFacing, relativeLayout, false);
    }
    
    public static TuSdkStillCameraInterface camera(final Context context, final CameraConfigs.CameraFacing cameraFacing, final RelativeLayout relativeLayout, final boolean b) {
        if (!SdkValid.shared.isVaild()) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 21 || !b || Camera2Helper.hardwareOnlySupportLegacy(context)) {
            return new TuSdkStillCamera(context, cameraFacing, relativeLayout);
        }
        return new TuSdkStillCamera2(context, cameraFacing, relativeLayout);
    }
}
