// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import java.util.List;
import com.example.myvideoeditorapp.kore.media.codec.suit.TuSdkMediaFilePlayer;

public interface TuSdkMediaMutableFilePlayer extends TuSdkMediaFilePlayer
{
    int maxInputSize();
    
    void setMediaDataSources(final List<TuSdkMediaDataSource> p0);
    
    void setOutputRatio(final float p0);
    
    boolean supportSeek();
}
