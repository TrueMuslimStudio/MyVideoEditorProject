// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.extend;

import com.example.myvideoeditorapp.kore.media.codec.extend.TuSdkMediaFileCuterTimeline;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;

public interface TuSdkMediaDirectorPlayerListener extends TuSdkMediaPlayerListener
{
    void onProgress(final long p0, final long p1, final TuSdkMediaDataSource p2, final TuSdkMediaFileCuterTimeline p3);
}
