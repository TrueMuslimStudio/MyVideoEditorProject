// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.media.codec.audio.TuSdkAudioInfo;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.tv.api.audio.preproc.mixer.TuSDKAudioRenderEntry;


public class TuSdkMediaAudioEffectData extends TuSdkMediaEffectData
{
    private TuSDKAudioRenderEntry a;
    private float b;
    
    public TuSdkMediaAudioEffectData(final TuSdkMediaDataSource tuSdkMediaDataSource) {
        if (tuSdkMediaDataSource == null || !tuSdkMediaDataSource.isValid()) {
            TLog.e("%s : Invalid audio data", new Object[] { this });
            this.setVaild(false);
            return;
        }
        this.a = new TuSDKAudioRenderEntry(tuSdkMediaDataSource);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeAudio);
        this.setVaild(true);
    }
    
    public TuSdkMediaAudioEffectData(final TuSdkAudioInfo tuSdkAudioInfo) {
        if (tuSdkAudioInfo == null) {
            TLog.w("%s : Invalid audio data", new Object[] { this });
            this.setVaild(false);
            return;
        }
        this.a = new TuSDKAudioRenderEntry(tuSdkAudioInfo);
        this.setVaild(true);
    }
    
    public final TuSDKAudioRenderEntry getAudioEntry() {
        return this.a;
    }
    
    @Override
    public void setAtTimeRange(final TuSdkTimeRange tuSdkTimeRange) {
        if (!this.isVaild()) {
            return;
        }
        super.setAtTimeRange(tuSdkTimeRange);
        this.a.setTimeRange(tuSdkTimeRange);
    }
    
    public final void setVolume(final float n) {
        if (!this.isVaild()) {
            return;
        }
        this.b = n;
        this.a.setVolume(n);
    }
    
    public final float getVolume() {
        return this.b;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaAudioEffectData tuSdkMediaAudioEffectData = new TuSdkMediaAudioEffectData(this.a);
        tuSdkMediaAudioEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaAudioEffectData.setVaild(true);
        tuSdkMediaAudioEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaAudioEffectData.setIsApplied(false);
        return tuSdkMediaAudioEffectData;
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = FilterWrap.creat(FilterLocalPackage.shared().option("Normal"))).processImage();
        }
        return this.mFilterWrap;
    }
}
