// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import java.io.IOException;

public class HttpResponseException extends IOException
{
    private final int a;
    
    public HttpResponseException(final int a, final String message) {
        super(message);
        this.a = a;
    }
    
    public int getStateCode() {
        return this.a;
    }
}
