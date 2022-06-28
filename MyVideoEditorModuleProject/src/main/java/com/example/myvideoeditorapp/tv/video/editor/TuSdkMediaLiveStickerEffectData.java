// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker.TuSdkDynamicStickerFilterWrap;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;


public class TuSdkMediaLiveStickerEffectData extends TuSdkMediaEffectData
{
    private StickerDynamicData a;
    
    public TuSdkMediaLiveStickerEffectData(final StickerDynamicData a) {
        if (a == null) {
            TLog.e("%s : Invalid sticker data", new Object[] { this });
            return;
        }
        this.a = a;
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeDynamicSticker);
        this.setVaild(true);
    }
    
    public StickerDynamicData getStickerData() {
        return this.a;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaLiveStickerEffectData tuSdkMediaLiveStickerEffectData = new TuSdkMediaLiveStickerEffectData(this.a);
        tuSdkMediaLiveStickerEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaLiveStickerEffectData.setVaild(true);
        tuSdkMediaLiveStickerEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaLiveStickerEffectData.setIsApplied(false);
        tuSdkMediaLiveStickerEffectData.mFilterWrap = this.getFilterWrap().clone();
        return tuSdkMediaLiveStickerEffectData;
    }
    
    public synchronized TuSdkDynamicStickerFilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = (FilterWrap)TuSdkDynamicStickerFilterWrap.creat(FilterLocalPackage.shared().option("Normal"))).processImage();
        }
        return (TuSdkDynamicStickerFilterWrap)this.mFilterWrap;
    }
}
