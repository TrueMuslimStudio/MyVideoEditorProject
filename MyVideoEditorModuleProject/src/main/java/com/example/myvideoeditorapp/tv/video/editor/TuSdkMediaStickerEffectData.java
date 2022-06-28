// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.combo.Face2DComboFilterWrap;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;


public class TuSdkMediaStickerEffectData extends TuSdkMediaEffectData
{
    private StickerGroup a;
    
    public TuSdkMediaStickerEffectData(final StickerGroup a) {
        if (a == null) {
            TLog.e("%s : Invalid sticker data", new Object[] { this });
            return;
        }
        this.a = a;
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSticker);
        this.setVaild(true);
    }
    
    public StickerGroup getStickerGroup() {
        return this.a;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaStickerEffectData tuSdkMediaStickerEffectData = new TuSdkMediaStickerEffectData(this.a);
        tuSdkMediaStickerEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaStickerEffectData.setVaild(true);
        tuSdkMediaStickerEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaStickerEffectData.setIsApplied(false);
        tuSdkMediaStickerEffectData.mFilterWrap = (FilterWrap)this.getFilterWrap().clone();
        return tuSdkMediaStickerEffectData;
    }
    
    public synchronized Face2DComboFilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = (FilterWrap)Face2DComboFilterWrap.creat(FilterLocalPackage.shared().option("Normal"))).processImage();
        }
        return (Face2DComboFilterWrap)this.mFilterWrap;
    }
}
