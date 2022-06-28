// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.combo.Face2DComboFilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.TuSdk;
import android.net.Uri;
import com.example.myvideoeditorapp.kore.struct.TuSdkMediaDataSource;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;

public class TuSdkMediaStickerAudioEffectData extends TuSdkMediaEffectData
{
    private TuSdkMediaAudioEffectData a;
    private TuSdkMediaStickerEffectData b;
    
    public TuSdkMediaStickerAudioEffectData(final TuSdkMediaAudioEffectData a, final TuSdkMediaStickerEffectData b) {
        if (a == null || b == null) {
            TLog.e("%s : Invalid MV data", new Object[] { this });
            return;
        }
        if (!a.isVaild() || !b.isVaild()) {
            TLog.e("%s : Invalid MV data", new Object[] { this });
            return;
        }
        this.a = a;
        this.b = b;
        this.setVaild(true);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio);
    }
    
    public TuSdkMediaStickerAudioEffectData(final String s, final StickerGroup stickerGroup) {
        this(new TuSdkMediaDataSource(s), stickerGroup);
    }
    
    public TuSdkMediaStickerAudioEffectData(final Uri uri, final StickerGroup stickerGroup) {
        this(new TuSdkMediaDataSource(TuSdk.appContext().getContext(), uri), stickerGroup);
    }
    
    public TuSdkMediaStickerAudioEffectData(final TuSdkMediaDataSource tuSdkMediaDataSource, final StickerGroup stickerGroup) {
        if (tuSdkMediaDataSource == null || !tuSdkMediaDataSource.isValid() || stickerGroup == null) {
            this.setVaild(false);
            TLog.w("%s : Invalid MV data", new Object[] { this });
            return;
        }
        this.a = new TuSdkMediaAudioEffectData(tuSdkMediaDataSource);
        this.b = new TuSdkMediaStickerEffectData(stickerGroup);
        this.setVaild(true);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeStickerAudio);
    }
    
    @Override
    public void setAtTimeRange(final TuSdkTimeRange atTimeRange) {
        if (!this.isVaild()) {
            return;
        }
        super.setAtTimeRange(atTimeRange);
        this.a.setAtTimeRange(atTimeRange);
        this.b.setAtTimeRange(atTimeRange);
    }
    
    public TuSdkMediaAudioEffectData getMediaAudioEffectData() {
        return this.a;
    }
    
    public TuSdkMediaStickerEffectData getMediaStickerEffectData() {
        return this.b;
    }
    
    public final void setVolume(final float volume) {
        if (!this.isVaild()) {
            return;
        }
        this.a.setVolume(volume);
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaStickerAudioEffectData tuSdkMediaStickerAudioEffectData = new TuSdkMediaStickerAudioEffectData(this.a, this.b);
        tuSdkMediaStickerAudioEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaStickerAudioEffectData.setVaild(true);
        tuSdkMediaStickerAudioEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaStickerAudioEffectData.setIsApplied(false);
        return tuSdkMediaStickerAudioEffectData;
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = (FilterWrap)Face2DComboFilterWrap.creat(FilterLocalPackage.shared().option("Normal"))).processImage();
        }
        return this.mFilterWrap;
    }
    
    @Override
    public void setIsApplied(final boolean isApplied) {
        super.setIsApplied(isApplied);
        if (this.a != null) {
            this.a.setIsApplied(isApplied);
        }
        if (this.b != null) {
            this.b.setIsApplied(isApplied);
        }
    }
}
