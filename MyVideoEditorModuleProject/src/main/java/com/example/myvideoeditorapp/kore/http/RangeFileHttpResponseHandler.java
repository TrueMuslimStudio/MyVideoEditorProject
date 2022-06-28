// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import com.example.myvideoeditorapp.kore.utils.TLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.File;

public abstract class RangeFileHttpResponseHandler extends FileHttpResponseHandler
{
    public static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    private long a;
    private boolean b;
    
    public RangeFileHttpResponseHandler(final File file) {
        super(file);
        this.a = 0L;
        this.b = false;
    }
    
    @Override
    public void sendResponseMessage(final HttpResponse httpResponse) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            final int responseCode = httpResponse.getResponseCode();
            if (responseCode == 416) {
                if (!Thread.currentThread().isInterrupted()) {
                    this.sendSuccessMessage(responseCode, httpResponse.getAllHeaders(), null);
                }
            }
            else if (responseCode >= 300) {
                if (!Thread.currentThread().isInterrupted()) {
                    this.sendFailureMessage(responseCode, httpResponse.getAllHeaders(), null, new HttpResponseException(responseCode, httpResponse.getResponseMessage()));
                }
            }
            else if (!Thread.currentThread().isInterrupted()) {
                final HttpHeader firstHeader = httpResponse.getFirstHeader("Content-Range");
                if (firstHeader == null) {
                    this.b = false;
                    this.a = 0L;
                }
                else {
                    TLog.w("%s : %s", "Content-Range", firstHeader.getValue());
                }
                this.sendSuccessMessage(responseCode, httpResponse.getAllHeaders(), this.getResponseData(httpResponse.getEntity()));
            }
        }
    }
    
    @Override
    protected byte[] getResponseData(final HttpEntity httpEntity) throws IOException {
        if (httpEntity != null) {
            final InputStream content = httpEntity.getContent();
            final long n = httpEntity.getContentLength() + this.a;
            final FileOutputStream fileOutputStream = new FileOutputStream(this.getTargetFile(), this.b);
            if (content != null) {
                try {
                    final byte[] array = new byte[4096];
                    int read;
                    while (this.a < n && (read = content.read(array)) != -1 && !Thread.currentThread().isInterrupted()) {
                        this.a += read;
                        fileOutputStream.write(array, 0, read);
                        this.sendProgressMessage(this.a, n);
                    }
                }
                finally {
                    content.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        }
        return null;
    }
    
    public void updateRequestHeaders(final HttpUriRequest httpUriRequest) {
        if (this.file.exists() && this.file.canWrite()) {
            this.a = this.file.length();
        }
        if (this.a > 0L) {
            this.b = true;
            httpUriRequest.setHeader("Range", "bytes=" + this.a + "-");
        }
    }
}
