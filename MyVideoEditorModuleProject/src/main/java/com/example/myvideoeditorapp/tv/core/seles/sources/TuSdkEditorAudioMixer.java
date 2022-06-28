// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.seles.sources;

import java.util.List;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.tv.api.audio.preproc.mixer.TuSDKAudioRenderEntry;
import com.example.myvideoeditorapp.tv.core.decoder.TuSDKAudioDecoderTaskManager;

public interface TuSdkEditorAudioMixer
{
    void setDataSource(final TuSdkMediaDataSource p0);
    
    void setMasterAudioTrack(final float p0);
    
    void setSecondAudioTrack(final float p0);
    
    void addAudioRenderEntry(final TuSDKAudioRenderEntry p0);
    
    void setAudioRenderEntryList(final List<TuSDKAudioRenderEntry> p0);
    
    void addTaskStateListener(final TuSDKAudioDecoderTaskManager.TuSDKAudioDecoderTaskStateListener p0);
    
    void removeTaskStateListener(final TuSDKAudioDecoderTaskManager.TuSDKAudioDecoderTaskStateListener p0);
    
    void removeAllTaskStateListener();
    
    void clearAllAudioData();
    
    void loadAudio();
    
    boolean isLoaded();
    
    void notifyLoadCompleted();
    
    void destroy();
}
