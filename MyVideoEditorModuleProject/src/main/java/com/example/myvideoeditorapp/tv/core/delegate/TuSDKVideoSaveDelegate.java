// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.delegate;

import com.example.myvideoeditorapp.tv.core.video.TuSDKVideoResult;

public interface TuSDKVideoSaveDelegate
{
    void onProgressChaned(final float p0);
    
    void onSaveResult(final TuSDKVideoResult p0);
    
    void onResultFail(final Exception p0);
}
