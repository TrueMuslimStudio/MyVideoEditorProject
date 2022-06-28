// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.secret;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;

import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.utils.ByteUtils;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.TuSdkDate;
import com.example.myvideoeditorapp.kore.utils.TuSdkLocation;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatisticsManger
{
    private static String a;
    private static long b;
    private static StatisticsManger c;
    private final Context d;
    private final File e;
    private final List<StatisticData> f;
    private boolean g;
    private boolean h;
    
    public static void init(final Context context, final File file) {
        if (StatisticsManger.c == null && context != null) {
            StatisticsManger.c = new StatisticsManger(context, file);
        }
    }
    
    public static void appendComponent(final long n) {
        if (StatisticsManger.c == null) {
            return;
        }
        StatisticsManger.c.a(new StatisticData(StatisticsType.Component, n));
    }
    
    public static void appendFilter(final FilterOption filterOption) {
        if (StatisticsManger.c == null || filterOption == null || filterOption.id == 0L) {
            return;
        }
        StatisticsManger.c.a(new StatisticData(StatisticsType.Filter, filterOption.id));
    }
    
    public static void appendSticker(final StickerData stickerData) {
        if (StatisticsManger.c == null || stickerData == null) {
            return;
        }
        StatisticsManger.c.a(new StatisticData(StatisticsType.Sticker, stickerData.stickerId));
    }
    
    public static void appendBrush(final BrushData brushData) {
        if (StatisticsManger.c == null || brushData == null) {
            return;
        }
        StatisticsManger.c.a(new StatisticData(StatisticsType.Brush, brushData.brushId));
    }
    
    private synchronized boolean a() {
        return this.g;
    }
    
    private synchronized void a(final boolean g) {
        this.g = g;
    }
    
    private StatisticsManger(final Context d, final File e) {
        this.d = d;
        this.f = new ArrayList<StatisticData>();
        this.e = e;
        this.h = !TuSdkHttpEngine.DEBUG;
        TuSdkLocation.init(d);
        new Thread(new Runnable() {
            @Override
            public void run() {
                StatisticsManger.this.b();
            }
        }).start();
    }
    
    private void b() {
        if (this.e == null || !this.e.exists() || !this.h) {
            return;
        }
        final byte[] bytesFromFile = FileHelper.getBytesFromFile(new File(this.e, StatisticsManger.a));
        final StatisticData statisticData = new StatisticData(StatisticsType.Component, ComponentActType.sdkLoadedComponent);
        this.b(statisticData);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (bytesFromFile != null) {
                byteArrayOutputStream.write(bytesFromFile);
            }
            byteArrayOutputStream.write(statisticData.a());
        }
        catch (IOException ex) {
            TLog.e(ex, "asyncLoadCacheData", new Object[0]);
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            final /* synthetic */ ByteArrayInputStream a = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            
            @Override
            public void run() {
                StatisticsManger.this.a(this.a);
            }
        });
    }
    
    private void c() {
        final File file = new File(this.e, StatisticsManger.a);
        final File file2 = new File(this.e, String.format("%s.%s", StatisticsManger.a, TuSdkDate.create().getTimeInMillis()));
        FileHelper.rename(file, file2);
        FileHelper.delete(file);
        FileHelper.delete(file2);
        if (LogStashManager.getInstance() == null) {
            return;
        }
        LogStashManager.getInstance().deleteTempFile();
    }
    
    private void a(final InputStream inputStream) {
        this.a(true);
        final TuSdkHttpHandler tuSdkHttpHandler = new TuSdkHttpHandler() {
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
            }
            
            @Override
            protected void onRequestedFinish(final TuSdkHttpHandler tuSdkHttpHandler) {
                StatisticsManger.this.a(false);
                StatisticsManger.this.c();
            }
        };
        final TuSdkHttpParams tuSdkHttpParams = new TuSdkHttpParams();
        tuSdkHttpParams.put("statistics", inputStream);
        if (LogStashManager.getInstance() != null) {
            final LogStashManager.LogBean upLoadData = LogStashManager.getInstance().getUpLoadData();
            if (upLoadData != null && upLoadData.isValid()) {
                try {
                    tuSdkHttpParams.put("key_index", (int)Integer.valueOf(upLoadData.getIndex()) + "");
                    tuSdkHttpParams.put("loginfo", upLoadData.getByteArrayInputStream());
                }
                catch (Exception ex) {}
            }
        }
        TuSdkHttpEngine.shared().post("/put", tuSdkHttpParams, true, tuSdkHttpHandler);
    }
    
    private void a(final StatisticData statisticData) {
        if (statisticData == null || !this.h) {
            return;
        }
        this.b(statisticData);
        synchronized (this.f) {
            this.f.add(statisticData);
            this.c(statisticData);
            this.d();
        }
    }
    
    private void b(final StatisticData statisticData) {
        if (statisticData == null) {
            return;
        }
        statisticData.c = TuSdkDate.create().getTimeInSeconds();
        final Location lastLocation = TuSdkLocation.getLastLocation();
        if (lastLocation != null) {
            statisticData.d = lastLocation.getLongitude();
            statisticData.e = lastLocation.getLatitude();
        }
    }
    
    private void c(final StatisticData statisticData) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(new File(this.e, StatisticsManger.a), "rw");
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(statisticData.a());
        }
        catch (FileNotFoundException ex) {
            TLog.e(ex, "asyncFlushData: %s", statisticData.b);
        }
        catch (IOException ex2) {
            TLog.e(ex2, "asyncFlushData: %s", statisticData.b);
        }
        finally {
            FileHelper.safeClose(randomAccessFile);
        }
    }

    private void d() {
        if (!this.a() && (long)this.f.size() >= b) {
            final ArrayList var1 = new ArrayList(this.f);
            this.f.clear();
            this.c();
            (new Thread(new Runnable() {
                public void run() {
                    StatisticsManger.this.asyncConvertData(var1);
                }
            })).start();
        }
    }
    
    protected void asyncConvertData(final List<StatisticData> list) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (final StatisticData statisticData : list) {
            try {
                byteArrayOutputStream.write(statisticData.a());
            }
            catch (IOException ex) {
                TLog.e(ex, "asyncConvertData", new Object[0]);
            }
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            final /* synthetic */ ByteArrayInputStream a = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            
            @Override
            public void run() {
                StatisticsManger.this.a(this.a);
            }
        });
    }
    
    static {
        StatisticsManger.a = "tusdk.statistics";
        StatisticsManger.b = 256L;
    }
    
    private static class StatisticData
    {
        private byte a;
        private long b;
        private long c;
        private double d;
        private double e;
        
        private StatisticData(final byte a, final long b) {
            this.a = a;
            this.b = b;
        }
        
        private byte[] a() throws IOException {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(33);
            byteArrayOutputStream.write(this.a);
            byteArrayOutputStream.write(ByteUtils.getBytes(this.b));
            byteArrayOutputStream.write(ByteUtils.getBytes(this.c));
            byteArrayOutputStream.write(ByteUtils.getBytes(this.d));
            byteArrayOutputStream.write(ByteUtils.getBytes(this.e));
            return byteArrayOutputStream.toByteArray();
        }
    }
    
    public static class StatisticsType
    {
        public static byte Component;
        public static byte Filter;
        public static byte Sticker;
        public static byte Brush;
        
        static {
            StatisticsType.Component = 16;
            StatisticsType.Filter = 32;
            StatisticsType.Sticker = 48;
            StatisticsType.Brush = 64;
        }
    }
}
