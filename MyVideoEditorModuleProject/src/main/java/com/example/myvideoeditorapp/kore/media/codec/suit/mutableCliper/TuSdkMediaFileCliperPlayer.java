// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutableCliper;

import com.example.myvideoeditorapp.kore.media.codec.suit.TuSdkMediaFilePlayer;
import com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer.AVMediaType;

import java.util.List;

public interface TuSdkMediaFileCliperPlayer extends TuSdkMediaFilePlayer
{
    void appendTrackItem(final TuSdkMediaTrackItem p0, final Callback<Boolean> p1);
    
    void insertTrackItem(final TuSdkMediaTrackItem p0, final int p1, final Callback<Boolean> p2);
    
    void removeTrackItem(final TuSdkMediaTrackItem p0, final Callback<Boolean> p1);
    
    void removeTrackItem(final int p0, final AVMediaType p1, final Callback<TuSdkMediaTrackItem> p2);
    
    List<TuSdkMediaTrackItem> trackItemsWithMediaType(final AVMediaType p0);
    
    public interface Callback<T>
    {
        void onHandled(final T p0);
    }
}
