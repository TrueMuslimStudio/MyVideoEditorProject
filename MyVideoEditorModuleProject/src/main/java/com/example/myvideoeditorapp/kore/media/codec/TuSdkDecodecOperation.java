// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec;

import java.io.IOException;

public interface TuSdkDecodecOperation
{
    void flush();
    
    boolean decodecInit(final TuSdkMediaExtractor p0) throws IOException;
    
    boolean decodecProcessUntilEnd(final TuSdkMediaExtractor p0) throws IOException;
    
    void decodecRelease();
    
    void decodecException(final Exception p0) throws IOException;
}
