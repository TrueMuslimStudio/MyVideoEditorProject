// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.io.IOException;
import com.example.myvideoeditorapp.kore.utils.TLog;
import java.util.List;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClearHttpRequest implements Runnable
{
    private final HttpManager a;
    private final HttpUriRequest b;
    private final ResponseHandlerInterface c;
    private final AtomicBoolean d;
    private int e;
    private boolean f;
    private volatile boolean g;
    private boolean h;
    
    public ClearHttpRequest(final HttpManager httpManager, final HttpUriRequest httpUriRequest, final ResponseHandlerInterface responseHandlerInterface) {
        this.d = new AtomicBoolean();
        this.a = ReflectUtils.notNull(httpManager, "httpManager");
        this.b = ReflectUtils.notNull(httpUriRequest, "uriRequest");
        this.c = ReflectUtils.notNull(responseHandlerInterface, "responseHandler");
    }
    
    public void onPreProcessRequest(final ClearHttpRequest clearHttpRequest) {
    }
    
    public void onPostProcessRequest(final ClearHttpRequest clearHttpRequest) {
    }
    
    @Override
    public void run() {
        if (this.isCancelled()) {
            return;
        }
        if (!this.h) {
            this.h = true;
            this.onPreProcessRequest(this);
        }
        if (this.isCancelled()) {
            return;
        }
        this.c.sendStartMessage();
        if (this.isCancelled()) {
            return;
        }
        try {
            this.b();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (this.isCancelled()) {
            return;
        }
        this.c.sendFinishMessage();
        if (this.isCancelled()) {
            return;
        }
        this.onPostProcessRequest(this);
        this.g = true;
    }

    private void a() throws IOException {
        if (!this.isCancelled()) {
            if (this.b.getURL().getProtocol() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            } else {
                if (this.c instanceof RangeFileHttpResponseHandler) {
                    ((RangeFileHttpResponseHandler)this.c).updateRequestHeaders(this.b);
                }

                HttpResponse var1 = this.a.execute(this.b);
                if (!this.isCancelled()) {
                    this.c.onPreProcessResponse(this.c, var1);
                    if (!this.isCancelled()) {
                        this.a.executeResponse(var1, this.b);
                        if (!this.isCancelled()) {
                            this.c.sendResponseMessage(var1);
                            if (!this.isCancelled()) {
                                this.c.onPostProcessResponse(this.c, var1);
                                var1.disconnect();
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void b() throws IOException {
        boolean b = true;
        IOException ex = null;
        try {
            while (b) {
                try {
                    this.a();
                    return;
                } catch (NullPointerException ex3) {
                    ex = new IOException("NPE in HttpClient: " + ex3.getMessage());
                    b = this.a.retryRequest(ex, ++this.e);
                }
                if (b) {
                    this.c.sendRetryMessage(this.e);
                }
            }
        }
        catch (Exception ex5) {
            TLog.e("Unhandled exception origin cause", ex5);
            ex = new IOException("Unhandled exception: " + ex5.getMessage());
        }
        throw ex;
    }
    
    public boolean isCancelled() {
        final boolean value = this.d.get();
        if (value) {
            this.c();
        }
        return value;
    }
    
    private synchronized void c() {
        if (!this.g && this.d.get() && !this.f) {
            this.f = true;
            this.c.sendCancelMessage();
        }
    }
    
    public boolean isDone() {
        return this.isCancelled() || this.g;
    }
    
    public boolean cancel(final boolean b) {
        this.d.set(true);
        this.b.abort();
        return this.isCancelled();
    }
    
    public ClearHttpRequest setRequestTag(final Object tag) {
        this.c.setTag(tag);
        return this;
    }
    
    public Object getTag() {
        return this.c.getTag();
    }
}
