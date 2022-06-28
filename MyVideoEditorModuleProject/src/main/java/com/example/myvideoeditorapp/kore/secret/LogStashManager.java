// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import android.util.Base64;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.NetworkHelper;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.TuSdkDeviceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogStashManager
{
    private static String a;
    private static LogStashManager b;
    private final File c;
    private Object d;
    
    public static void init(final File file) {
        if (LogStashManager.b == null) {
            LogStashManager.b = new LogStashManager(file);
        }
    }
    
    public static LogStashManager getInstance() {
        if (LogStashManager.b == null) {
            TLog.w("LogStashManager is not Initialization !!!", new Object[0]);
        }
        return LogStashManager.b;
    }
    
    public LogStashManager(final File c) {
        this.d = new Object();
        this.c = c;
        this.stashLog();
    }
    
    public void stashLog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200L);
                    LogStashManager.this.a();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    
    private void a() {
        try {
            String advertisingIdInfo = "";
            try {
                advertisingIdInfo = TuSdkDeviceInfo.getAdvertisingIdInfo(TuSdk.appContext().getContext());
            }
            catch (Exception ex2) {
                TLog.w("Google id has exception!!!", new Object[0]);
            }
            this.a(this.a(advertisingIdInfo, new ArrayList<NetworkHelper.ScanResultBean>(), new ArrayList<PacketInfoBean>()).toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private JSONObject a(final String s, final List<NetworkHelper.ScanResultBean> list, final List<PacketInfoBean> list2) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("ttp", (Object)String.valueOf(System.currentTimeMillis()));
        jsonObject.put("0x1000", (Object) StringHelper.Base64Encode(s));
        jsonObject.put("0x4000", (Object)StringHelper.Base64Encode(NetworkHelper.getNetworkState()));
        return jsonObject;
    }
    
    private void a(final String s) {
        synchronized (this.d) {
            SdkValid.shared.saveLogStash(s, this.c.getPath() + "/" + LogStashManager.a);
        }
    }
    
    public LogBean getUpLoadData() {
        if (!new File(this.c + "/" + LogStashManager.a).exists()) {
            return null;
        }
        final LogBean logBean = new LogBean();
        synchronized (this.d) {
            final byte[] bytesFromFile = FileHelper.getBytesFromFile(new File(this.c + "/" + LogStashManager.a));
            if (bytesFromFile == null) {
                return logBean;
            }
            final String s = new String(bytesFromFile);
            if (s.isEmpty()) {
                return logBean;
            }
            final String substring = s.substring(0, s.length() - 2);
            logBean.b = s.substring(s.length() - 2, s.length());
            logBean.c = substring;
        }
        return logBean;
    }
    
    public void deleteTempFile() {
        try {
            if (!new File(this.c + "/" + LogStashManager.a).exists()) {
                return;
            }
            FileHelper.delete(new File(this.c + "/" + LogStashManager.a));
            if (getInstance() != null) {
                getInstance().stashLog();
            }
        }
        catch (Exception ex) {
            TLog.w("delete log temp file error", new Object[0]);
        }
    }
    
    static {
        LogStashManager.a = "logstash.statistics";
    }
    
    private class PacketInfoBean
    {
        private String a;
        private String b;
        private String c;
        private String d;
        
        public String getVersion() {
            return this.a;
        }
        
        public void setVersion(final String a) {
            this.a = a;
        }
        
        public String getCode() {
            return this.b;
        }
        
        public void setCode(final String b) {
            this.b = b;
        }
        
        public String getAppName() {
            return this.c;
        }
        
        public void setAppName(final String c) {
            this.c = c;
        }
        
        public String getInstallTime() {
            return this.d;
        }
        
        public void setInstallTime(final String d) {
            this.d = d;
        }
        
        @Override
        public String toString() {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("0x3001", (Object)StringHelper.Base64Encode(this.getVersion()));
                jsonObject.put("0x3002", (Object)StringHelper.Base64Encode(this.getCode()));
                jsonObject.put("0x3003", (Object)StringHelper.Base64Encode(this.getAppName()));
                jsonObject.put("0x3004", (Object)StringHelper.Base64Encode(this.getInstallTime()));
            }
            catch (JSONException ex) {}
            return jsonObject.toString();
        }
    }
    
    public class LogBean
    {
        private String b;
        private String c;
        
        public String getIndex() {
            return this.b;
        }
        
        public void setIndex(final String b) {
            this.b = b;
        }
        
        public String getData() {
            return this.c;
        }
        
        public void setData(final String c) {
            this.c = c;
        }
        
        public ByteArrayInputStream getByteArrayInputStream() {
            return new ByteArrayInputStream(Base64.decode(this.getData(), 0));
        }
        
        public boolean isValid() {
            try {
                return !StringHelper.isEmpty(this.b) && !StringHelper.isEmpty(this.c);
            }
            catch (Exception ex) {
                return false;
            }
        }
    }
}
