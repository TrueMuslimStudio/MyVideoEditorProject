// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.sticker.LiveStickerPlayController;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.combo.TuSDKComboFilterWrapChain;
import com.example.myvideoeditorapp.tv.api.video.preproc.filter.TuSDKVideoProcesser;


public interface TuSDKMediaEffectsManager
{
    void setMediaEffectDelegate(final TuSDKVideoProcesser.TuSDKVideoProcessorMediaEffectDelegate p0);
    
    void setManagerDelegate(final TuSDKMediaEffectsDataManager.TuSDKMediaEffectsManagerDelegate p0);
    
    TuSDKComboFilterWrapChain getFilterWrapChain();
    
    boolean addMediaEffectData(final TuSdkMediaEffectData p0);
    
    void addTerminalNode(final SelesContext.SelesInput p0);
    
     <T extends TuSdkMediaEffectData> List<T> mediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    List<TuSdkMediaEffectData> getAllMediaEffectData();
    
    boolean removeMediaEffectData(final TuSdkMediaEffectData p0);
    
    void removeMediaEffectsWithType(final TuSdkMediaEffectData.TuSdkMediaEffectDataType p0);
    
    void removeAllMediaEffects();
    
    void updateEffectTimeLine(final long p0, final OnFilterChangeListener p1);
    
    void showGroupSticker(final TuSdkMediaStickerEffectData p0);
    
    void showCosmetic(final TuSdkMediaCosmeticEffectData p0);
    
    LiveStickerPlayController getLiveStickerPlayController();
    
    void removeAllLiveSticker();
    
    void release();
    
    public interface OnFilterChangeListener
    {
        void onFilterChanged(final FilterWrap p0);
    }
}
