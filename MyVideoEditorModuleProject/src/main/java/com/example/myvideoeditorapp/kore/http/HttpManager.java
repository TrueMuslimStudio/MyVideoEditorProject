// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import javax.net.ssl.SSLException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import android.os.SystemClock;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class HttpManager
{
    private static final HashSet<Class<?>> a;
    private static final HashSet<Class<?>> b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private List<HttpRequestInterceptor> l;
    private List<HttpResponseInterceptor> m;
    
    public HttpManager() {
        this.l = new ArrayList<HttpRequestInterceptor>();
        this.m = new ArrayList<HttpResponseInterceptor>();
    }
    
    public int getMaxConnections() {
        return this.c;
    }
    
    public void setMaxConnections(final int c) {
        this.c = c;
    }
    
    public int getConnectTimeout() {
        return this.d;
    }
    
    public void setConnectTimeout(final int d) {
        this.d = d;
    }
    
    public int getResponseTimeout() {
        return this.e;
    }
    
    public void setResponseTimeout(final int e) {
        this.e = e;
    }
    
    public int getSocketBufferSize() {
        return this.f;
    }
    
    public void setSocketBufferSize(final int f) {
        this.f = f;
    }
    
    public int getDefaultMaxRetries() {
        return this.g;
    }
    
    public void setDefaultMaxRetries(final int g) {
        this.g = g;
    }
    
    public int getDefaultRetrySleepTimemillis() {
        return this.h;
    }
    
    public void setDefaultRetrySleepTimemillis(final int h) {
        this.h = h;
    }
    
    public int getHttpPort() {
        return this.i;
    }
    
    public void setHttpPort(final int i) {
        this.i = i;
    }
    
    public int getHttpsPort() {
        return this.j;
    }
    
    public void setHttpsPort(final int j) {
        this.j = j;
    }
    
    public boolean isEnableRedirct() {
        return this.k;
    }
    
    public void setEnableRedirct(final boolean k) {
        this.k = k;
    }
    
    public void addRequestInterceptor(final HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return;
        }
        this.l.add(httpRequestInterceptor);
    }
    
    public void addResponseInterceptor(final HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return;
        }
        this.m.add(httpResponseInterceptor);
    }
    
    public boolean retryRequest(final IOException ex, final int n) {
        boolean b = true;
        if (n > this.g) {
            b = false;
        }
        else if (this.a(HttpManager.a, ex)) {
            b = true;
        }
        else if (this.a(HttpManager.b, ex)) {
            b = false;
        }
        if (b) {}
        if (b) {
            SystemClock.sleep((long)this.h);
        }
        else {
            ex.printStackTrace();
        }
        return b;
    }
    
    private boolean a(final HashSet<Class<?>> set, final Throwable t) {
        final Iterator<Class<?>> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isInstance(t)) {
                return true;
            }
        }
        return false;
    }

    public HttpResponse execute(HttpUriRequest var1) throws IOException {
        this.a(var1);
        HttpURLConnection var2 = var1.openConnection();
        var2.setConnectTimeout(this.getConnectTimeout());
        var2.setReadTimeout(this.getResponseTimeout());
        var2.setInstanceFollowRedirects(this.isEnableRedirct());
        var2.connect();
        HttpResponse var3 = new HttpResponse(var2);
        if (var1.canOutput()) {
            var3.openOutputStream();
            var1.getEntity().writeTo(var3.getOutputStream());
        }

        return var3;
    }
    
    private void a(final HttpUriRequest httpUriRequest) {
        final Iterator<HttpRequestInterceptor> iterator = this.l.iterator();
        while (iterator.hasNext()) {
            iterator.next().process(httpUriRequest);
        }
    }

    public void executeResponse(HttpResponse var1, HttpUriRequest var2) throws IOException {
        var1.openInputStream();
        this.a(var1);
    }
    
    private void a(final HttpResponse httpResponse) {
        final Iterator<HttpResponseInterceptor> iterator = this.m.iterator();
        while (iterator.hasNext()) {
            iterator.next().process(httpResponse);
        }
    }
    
    static {
        a = new HashSet<Class<?>>();
        b = new HashSet<Class<?>>();
        HttpManager.a.add(UnknownHostException.class);
        HttpManager.a.add(SocketException.class);
        HttpManager.b.add(InterruptedIOException.class);
        HttpManager.b.add(SSLException.class);
    }
    
    public interface HttpResponseInterceptor
    {
        void process(final HttpResponse p0);
    }
    
    public interface HttpRequestInterceptor
    {
        void process(final HttpUriRequest p0);
    }
}
