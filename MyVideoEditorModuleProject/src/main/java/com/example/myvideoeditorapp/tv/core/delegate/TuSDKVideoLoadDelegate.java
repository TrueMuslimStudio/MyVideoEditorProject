// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.delegate;

import com.example.myvideoeditorapp.tv.core.decoder.TuSDKVideoInfo;

public interface TuSDKVideoLoadDelegate
{
    void onProgressChaned(final float p0);
    
    void onLoadComplete(final TuSDKVideoInfo p0);
}
