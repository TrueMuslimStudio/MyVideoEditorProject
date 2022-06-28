// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils;

import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;
import android.os.Message;
import com.example.myvideoeditorapp.kore.utils.hardware.HardwareHelper;
import java.util.Iterator;
import android.os.Build;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import java.util.Hashtable;
import com.example.myvideoeditorapp.kore.http.ResponseHandlerInterface;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import java.io.File;
import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.http.HttpHeader;
import java.util.List;
import com.example.myvideoeditorapp.kore.http.TextHttpResponseHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.os.Handler;
import java.util.LinkedList;

public class TuSdkDecodecPatch
{
    private static DeviceInfo a;
    private static LinkedList<DeviceInfo> b;
    private static LinkedList<DeviceInfo> c;
    private static LinkedList<DeviceInfo> d;
    private static String e;
    private static Handler f;
    
    public static void upDataPathFile() {
        TuSdkDecodecPatch.f.sendEmptyMessage(1);
    }
    
    private static synchronized void e() {
        final Hashtable<String, String> assetsFiles = AssetsHelper.getAssetsFiles(TuSdkContext.context(), "patch");
        if (assetsFiles == null || assetsFiles.size() == 0) {
            return;
        }
        TuSdkHttpEngine.shared().get("http://patch.tusdk.com/codecpatch/latest_android", null, false, false, new TextHttpResponseHandler() {
            final /* synthetic */ String a = assetsFiles.keys().nextElement();
            
            @Override
            public void onFailure(final int n, final List<HttpHeader> list, final String s, final Throwable t) {
                TLog.e("[error] failure in patch !!!", new Object[0]);
            }
            
            @Override
            public void onSuccess(final int n, final List<HttpHeader> list, final String s) {
                String anObject = "";
                final File file = new File(TuSdk.getAppTempPath().getPath() + "/" + "patch_code");
                if (file.exists()) {
                    anObject = new String(FileHelper.readFile(file));
                    TLog.d("[debug] tempCode : %s", anObject);
                }
                if (this.a.toLowerCase().equals(s.toLowerCase()) || s.toLowerCase().equals(anObject)) {
                    TLog.e("[debug] code is eq", new Object[0]);
                    return;
                }
                TuSdkDecodecPatch.e = s;
                TuSdkDecodecPatch.f.sendEmptyMessage(2);
            }
        });
    }
    
    private static synchronized void f() {
        TLog.e("[debug] update  patch file ", new Object[0]);
        TuSdkHttpEngine.shared().get("http://patch.tusdk.com/codecpatch/patch_android", null, false, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(final int n, final List<HttpHeader> list, final String s, final Throwable t) {
                TLog.e("[error] failure in patch !!!", new Object[0]);
            }
            
            @Override
            public void onSuccess(final int n, final List<HttpHeader> list, final String s) {
                try {
                    final String loadString = SdkValid.shared.loadString(s);
                    TLog.d("[debug] save code  : %s", TuSdkDecodecPatch.e);
                    FileHelper.saveFile(TuSdk.getAppTempPath().getPath() + "/" + "patch_code", TuSdkDecodecPatch.e.getBytes());
                    TLog.d("[debug] save json  : %s", loadString);
                    FileHelper.saveFile(TuSdk.getAppTempPath().getPath() + "/" + "patch", loadString.getBytes());
                }
                catch (Exception ex) {
                    TLog.e("[error] patch sync to file error %s!!!", ex.getMessage());
                }
            }
        });
    }
    
    private static DeviceInfo a(final String a, final String b, final CodecType e) {
        final DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.a = a;
        deviceInfo.b = b;
        deviceInfo.e = e;
        return deviceInfo;
    }
    
    private static DeviceInfo a(final String c, final CodecType e) {
        final DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.c = c;
        deviceInfo.e = e;
        return deviceInfo;
    }
    
    public static DeviceInfo getCurrentDeviceInfo() {
        if (TuSdkDecodecPatch.a == null) {
            TuSdkDecodecPatch.a = new DeviceInfo(Build.MODEL, Build.MANUFACTURER, Build.HARDWARE, g());
        }
        return TuSdkDecodecPatch.a;
    }
    
    public static CodecType getUseageCodecType() {
        final DeviceInfo currentDeviceInfo = getCurrentDeviceInfo();
        for (final DeviceInfo deviceInfo : TuSdkDecodecPatch.d) {
            if (currentDeviceInfo.isPatchDevice(deviceInfo)) {
                TLog.d("[debug] codec type : %s", deviceInfo.e);
                return deviceInfo.e;
            }
        }
        TLog.d("[debug] codec type : %s", CodecType.HW_FFDeCodec);
        return CodecType.HW_FFDeCodec;
    }
    
    private static String g() {
        final String hardware = Build.HARDWARE;
        if (hardware == null || hardware.isEmpty()) {
            return null;
        }
        try {
            if (hardware.contains("qcom")) {
                final String hardWareInfo = HardwareHelper.getHardWareInfo();
                if (hardWareInfo == null || hardWareInfo.isEmpty()) {
                    return null;
                }
                String s;
                if (hardWareInfo.contains("MSM")) {
                    final int index = hardWareInfo.indexOf("MSM");
                    s = hardWareInfo.substring(index + 3, index + 7);
                    if (Integer.valueOf(s) < 8976) {
                        return "653";
                    }
                }
                else {
                    s = hardWareInfo.substring(hardWareInfo.length() - 3);
                }
                if (!s.isEmpty()) {
                    return s;
                }
            }
            else if (hardware.contains("kirin") || hardware.contains("hi")) {
                final String substring = hardware.substring(hardware.length() - 3);
                if (!substring.isEmpty()) {
                    return substring;
                }
            }
            else if (hardware.contains("mt")) {
                final String substring2 = hardware.substring(hardware.length() - 4);
                if (!substring2.isEmpty()) {
                    return substring2;
                }
            }
            else if (hardware.contains("samsung")) {
                final String substring3 = hardware.substring(hardware.length() - 4);
                if (!substring3.isEmpty()) {
                    return substring3;
                }
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    static {
        TuSdkDecodecPatch.b = new LinkedList<DeviceInfo>();
        TuSdkDecodecPatch.c = new LinkedList<DeviceInfo>();
        TuSdkDecodecPatch.d = new LinkedList<DeviceInfo>();
        TuSdkDecodecPatch.f = new Handler() {
            public void dispatchMessage(final Message message) {
                super.dispatchMessage(message);
                switch (message.what) {
                    case 1: {
                        e();
                        break;
                    }
                    case 2: {
                        f();
                        break;
                    }
                }
            }
        };
        TuSdkDecodecPatch.b.add(a("OPPO R11 Pluskt", "OPPO", CodecType.HW_MediaCodec));
        TuSdkDecodecPatch.c.add(a("kirin", CodecType.HW_MediaCodec));
        TuSdkDecodecPatch.c.add(a("hi", CodecType.HW_MediaCodec));
    }
    
    public static class DeviceInfo extends JsonBaseBean
    {
        String a;
        String b;
        String c;
        String d;
        CodecType e;
        Operation f;
        int g;
        int h;
        
        public DeviceInfo() {
            this.e = CodecType.HW_FFDeCodec;
            this.f = Operation.no_op;
            this.g = 0;
            this.h = 0;
        }
        
        public DeviceInfo(final String a, final String b, final String c, final String d) {
            this.e = CodecType.HW_FFDeCodec;
            this.f = Operation.no_op;
            this.g = 0;
            this.h = 0;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
        
        public void setOperation(final Operation f) {
            this.f = f;
        }
        
        public boolean isPatchDevice(final DeviceInfo deviceInfo) {
            if (deviceInfo == null) {
                return false;
            }
            if (TextUtils.isEmpty((CharSequence)deviceInfo.a) && TextUtils.isEmpty((CharSequence)deviceInfo.b) && TextUtils.isEmpty((CharSequence)deviceInfo.c)) {
                return false;
            }
            if (!TextUtils.isEmpty((CharSequence)deviceInfo.a) && !TextUtils.isEmpty((CharSequence)deviceInfo.b) && this.a.toLowerCase().equals(deviceInfo.a.toLowerCase()) && this.b.toLowerCase().equals(deviceInfo.b.toLowerCase())) {
                return true;
            }
            if (deviceInfo.f == Operation.no_op) {
                if (TextUtils.isEmpty((CharSequence)deviceInfo.c) || !this.c.toLowerCase().contains(deviceInfo.c.toLowerCase())) {
                    return false;
                }
                if (!TextUtils.isEmpty((CharSequence)deviceInfo.c) && this.c.toLowerCase().contains(deviceInfo.c.toLowerCase())) {
                    return true;
                }
            }
            if (TextUtils.isEmpty((CharSequence)deviceInfo.c) || !this.c.toLowerCase().contains(deviceInfo.c.toLowerCase())) {
                return false;
            }
            if (TextUtils.isEmpty((CharSequence)deviceInfo.d) || TextUtils.isEmpty((CharSequence)this.d)) {
                return false;
            }
            final Integer value = Integer.valueOf(this.d);
            final Integer value2 = Integer.valueOf(deviceInfo.d);
            switch (deviceInfo.f.ordinal()) {
                case 1: {
                    return value < value2;
                }
                case 2: {
                    return value <= value2;
                }
                case 3: {
                    return value > value2;
                }
                case 4: {
                    return value >= value2;
                }
                default: {
                    return false;
                }
            }
        }
        
        public static DeviceInfo getBeanFromJson(final JSONObject jsonObject) throws JSONException {
            final DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.h = jsonObject.getInt("oType");
            deviceInfo.g = jsonObject.getInt("cType");
            deviceInfo.c = jsonObject.getString("cpuInfo");
            deviceInfo.d = jsonObject.getString("cpuModel");
            deviceInfo.a = jsonObject.getString("model");
            deviceInfo.b = jsonObject.getString("manuFacturer");
            return deviceInfo;
        }
        
        public enum Operation
        {
            no_op(0), 
            less(1), 
            greater(2), 
            eq_less(3), 
            eq_greater(4);
            
            int a;
            
            private Operation(final int a) {
                this.a = a;
            }
            
            public static Operation getOperation(final int n) {
                switch (n) {
                    case 0: {
                        return Operation.no_op;
                    }
                    case 1: {
                        return Operation.less;
                    }
                    case 2: {
                        return Operation.greater;
                    }
                    case 3: {
                        return Operation.eq_less;
                    }
                    case 4: {
                        return Operation.eq_greater;
                    }
                    default: {
                        return Operation.no_op;
                    }
                }
            }
        }
    }
    
    public enum CodecType
    {
        HW_FFDeCodec(0), 
        SW_FFDeCodec(1), 
        HW_MediaCodec(2), 
        SW_MediaCodec(3);
        
        int a;
        
        private CodecType(final int a) {
            this.a = a;
        }
        
        public int getType() {
            return this.a;
        }
        
        public static CodecType getCodecType(final int n) {
            switch (n) {
                case 0: {
                    return CodecType.HW_FFDeCodec;
                }
                case 1: {
                    return CodecType.SW_FFDeCodec;
                }
                case 2: {
                    return CodecType.HW_MediaCodec;
                }
                case 3: {
                    return CodecType.SW_MediaCodec;
                }
                default: {
                    return CodecType.HW_FFDeCodec;
                }
            }
        }
    }
}
