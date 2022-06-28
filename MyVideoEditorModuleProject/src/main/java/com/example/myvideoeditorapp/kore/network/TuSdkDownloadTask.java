// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network;

import android.os.Handler;
import android.os.Looper;

import com.example.myvideoeditorapp.kore.http.FileHttpResponseHandler;
import com.example.myvideoeditorapp.kore.http.HttpHeader;
import com.example.myvideoeditorapp.kore.http.RequestHandle;
import com.example.myvideoeditorapp.kore.type.DownloadTaskStatus;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;

import java.io.File;
import java.util.List;

public class TuSdkDownloadTask
{
    public static final long PROGRESS_INTERVAL = 500L;
    private boolean a;
    private boolean b;
    private long c;
    private TuSdkDownloadItem d;
    private TuSdkDownloadTaskDelegate e;
    private RequestHandle f;
    
    public TuSdkDownloadItem getItem() {
        return this.d;
    }
    
    public TuSdkDownloadTaskDelegate getDelegate() {
        return this.e;
    }
    
    public void setDelegate(final TuSdkDownloadTaskDelegate e) {
        this.e = e;
    }
    
    public TuSdkDownloadTask(final TuSdkDownloadItem d) {
        this.d = d;
    }
    
    public boolean equals(final DownloadTaskType downloadTaskType, final long n) {
        return this.d != null && this.d.type == downloadTaskType && this.d.id == n;
    }
    
    public boolean canRunTask() {
        if (this.d == null) {
            return false;
        }
        switch (this.d.getStatus().ordinal()) {
            case 1:
            case 2:
            case 3: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    private void a(final DownloadTaskStatus status) {
        this.d.setStatus(status);
        if (!this.c(status)) {
            return;
        }
        this.b(status);
        if (!this.canRunTask()) {
            this.onDestory();
        }
    }
    
    private void b(final DownloadTaskStatus downloadTaskStatus) {
        if (this.e == null) {
            return;
        }
        if (ThreadHelper.isMainThread()) {
            this.e.onDownloadTaskStatusChanged(this, downloadTaskStatus);
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                TuSdkDownloadTask.this.b(downloadTaskStatus);
            }
        });
    }
    
    private boolean c(final DownloadTaskStatus downloadTaskStatus) {
        if (downloadTaskStatus != DownloadTaskStatus.StatusDowning) {
            return true;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.c < 500L) {
            return false;
        }
        this.c = currentTimeMillis;
        return true;
    }
    
    public void onDestory() {
        this.e = null;
        this.clear();
    }
    
    public void clear() {
        if (this.d == null) {
            return;
        }
        FileHelper.delete(this.d.localTempPath());
    }
    
    public void start() {
        if (this.a) {
            return;
        }
        this.b = false;
        this.a = true;
        this.a();
    }

    private void a() {
        DownloadTaskStatus var1 = DownloadTaskStatus.StatusInit;
        if (this.d == null) {
            var1 = DownloadTaskStatus.StatusDownFailed;
        } else if (this.d.getStatus() != null) {
            var1 = this.d.getStatus();
        }

        switch(var1) {
            case StatusDownFailed:
            case StatusDowned:
            case StatusCancel:
                this.a = false;
                this.clear();
                break;
            case StatusInit:
            case StatusDowning:
                this.b();
        }

    }
    private void b() {
        this.clear();
        this.a(DownloadTaskStatus.StatusDowning);
        this.f = TuSdkHttpEngine.webAPIEngine().get(String.format("/%s/download?id=%s", this.d.type.getAct(), this.d.fileId), true, new DownloadFileHandler(this.d.localTempPath()));
    }
    
    private void c() {
        if (this.b) {
            return;
        }
        FileHelper.rename(this.d.localTempPath(), this.d.localDownloadPath());
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                TuSdkDownloadTask.this.a(DownloadTaskStatus.StatusDowned);
            }
        });
    }
    
    public void cancel() {
        this.b = true;
        if (this.f != null) {
            this.f.cancel(true);
            this.f = null;
        }
        this.a(DownloadTaskStatus.StatusCancel);
    }
    
    private class DownloadFileHandler extends FileHttpResponseHandler
    {
        public DownloadFileHandler(final File file) {
            super(file);
        }
        
        @Override
        public void onFailure(final int n, final List<HttpHeader> list, final Throwable t, final File file) {
            TLog.e(t, "TuSdkDownloadTask onFailure: %s(%s) |%s", TuSdkDownloadTask.this.d.type.getAct(), TuSdkDownloadTask.this.d.id, file);
            TuSdkDownloadTask.this.a(DownloadTaskStatus.StatusDownFailed);
        }
        
        @Override
        public void onSuccess(final int n, final List<HttpHeader> list, final File file) {
            TuSdkDownloadTask.this.d.fileName = TuSdkHttp.attachmentFileName(list);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TuSdkDownloadTask.this.c();
                }
            }).start();
        }
        
        @Override
        public void onProgress(final long n, final long n2) {
            TuSdkDownloadTask.this.d.progress = n / (float)n2;
            TuSdkDownloadTask.this.a(DownloadTaskStatus.StatusDowning);
        }
    }
    
    public enum DownloadTaskType
    {
        TypeFilter("filter"), 
        TypeSticker("sticker"), 
        TypeBrush("brush");
        
        private String a;
        
        private DownloadTaskType(final String a) {
            this.a = a;
        }
        
        public String getAct() {
            return this.a;
        }
    }
    
    public interface TuSdkDownloadTaskDelegate
    {
        void onDownloadTaskStatusChanged(final TuSdkDownloadTask p0, final DownloadTaskStatus p1);
    }
}
