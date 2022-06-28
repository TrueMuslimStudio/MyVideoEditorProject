// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.extend;

import androidx.annotation.Nullable;

import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;

import java.io.IOException;

public interface TuSdkMediaProgress
{
    void onProgress(final float p0, final TuSdkMediaDataSource p1, final int p2, final int p3);
    
    void onCompleted(@Nullable final Exception p0, final TuSdkMediaDataSource p1, final int p2) throws IOException;
}
