// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec;

import java.io.IOException;

public interface TuSdkEncodecOperation
{
    boolean isEncodecStarted();
    
    boolean encodecInit(final TuSdkMediaMuxer p0);
    
    boolean encodecProcessUntilEnd(final TuSdkMediaMuxer p0) throws IOException;
    
    void encodecRelease();
    
    void encodecException(final Exception p0) throws IOException;
    
    void flush();
}
