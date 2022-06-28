// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class UrlEncodedFormEntity extends StringEntity
{
    public UrlEncodedFormEntity(final List<URLEncodedUtils.BasicNameValuePair> list, final String s) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(list, s), "application/x-www-form-urlencoded", s);
    }
    
    public UrlEncodedFormEntity(final List<URLEncodedUtils.BasicNameValuePair> list) throws UnsupportedEncodingException {
        this(list, null);
    }
}
