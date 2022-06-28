// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

public interface HttpEntity
{
    boolean isRepeatable();
    
    boolean isChunked();
    
    long getContentLength();
    
    HttpHeader getContentType();
    
    HttpHeader getContentEncoding();
    
    InputStream getContent() throws IOException;
    
    void writeTo(final OutputStream p0) throws IOException;
    
    boolean isStreaming();
    
    void consumeContent();
}
