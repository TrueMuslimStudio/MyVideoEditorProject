// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import com.example.myvideoeditorapp.kore.utils.ReflectUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringEntity extends AbstractHttpEntity implements Cloneable
{
    public static final String TEXT_PLAIN = "text/plain";
    protected final byte[] content;
    
    public StringEntity(final String s, final String s2, final String s3) throws UnsupportedEncodingException {
        ReflectUtils.notNull(s, "Source string");
        final String str = (s2 != null) ? s2 : "text/plain";
        final String s4 = (s3 != null) ? s3 : "UTF-8";
        this.content = s.getBytes(s4);
        this.setContentType(str + "; " + s4);
    }
    
    public StringEntity(final String s, final String s2) throws UnsupportedEncodingException {
        this(s, null, s2);
    }
    
    public StringEntity(final String s) throws UnsupportedEncodingException {
        this(s, null);
    }
    
    @Override
    public boolean isRepeatable() {
        return true;
    }
    
    @Override
    public long getContentLength() {
        return this.content.length;
    }
    
    @Override
    public InputStream getContent() {
        return new ByteArrayInputStream(this.content);
    }
    
    @Override
    public void writeTo(final OutputStream outputStream) throws IOException {
        ReflectUtils.notNull(outputStream, "Output stream");
        outputStream.write(this.content);
        outputStream.flush();
    }
    
    @Override
    public boolean isStreaming() {
        return false;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
