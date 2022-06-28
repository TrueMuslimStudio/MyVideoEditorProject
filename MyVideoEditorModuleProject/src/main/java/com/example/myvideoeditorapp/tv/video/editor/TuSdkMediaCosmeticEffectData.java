// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticLipFilter;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticFilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.example.myvideoeditorapp.kore.sticker.StickerPositionInfo;
import java.util.Map;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticSticker;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;

import java.util.Queue;

public class TuSdkMediaCosmeticEffectData extends TuSdkMediaEffectData
{
    private Queue<CosmeticSticker> a;
    private Map<StickerPositionInfo.StickerPositionType, CosmeticSticker> b;
    
    public TuSdkMediaCosmeticEffectData() {
        this.a = new ConcurrentLinkedQueue<CosmeticSticker>();
        this.b = new HashMap<StickerPositionInfo.StickerPositionType, CosmeticSticker>();
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeCosmetic);
        (this.mFilterWrap = (FilterWrap)CosmeticFilterWrap.create(FilterLocalPackage.shared().option("Normal"))).processImage();
        this.setVaild(true);
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaCosmeticEffectData tuSdkMediaCosmeticEffectData = new TuSdkMediaCosmeticEffectData();
        tuSdkMediaCosmeticEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaCosmeticEffectData.setVaild(true);
        tuSdkMediaCosmeticEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaCosmeticEffectData.setIsApplied(false);
        tuSdkMediaCosmeticEffectData.mFilterWrap = this.getFilterWrap().clone();
        tuSdkMediaCosmeticEffectData.b = this.b;
        return tuSdkMediaCosmeticEffectData;
    }
    
    @Override
    public FilterWrap getFilterWrap() {
        return this.mFilterWrap;
    }
    
    public Queue<CosmeticSticker> cosmeticStickerQueue() {
        return this.a;
    }
    
    public Map<StickerPositionInfo.StickerPositionType, CosmeticSticker> cosmeticStickerCache() {
        return this.b;
    }
    
    public void updateFacial(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosFacial).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosFacial, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosFacial).data(stickerData).build());
    }
    
    public void closeFacial() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosFacial).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosFacial);
    }
    
    public void updateLip(final CosmeticLipFilter.CosmeticLipType cosmeticLipType, final int n) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosLipGloss).lipType(cosmeticLipType).lipColor(n).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosLipGloss, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosLipGloss).lipType(cosmeticLipType).lipColor(n).build());
    }
    
    public void closeLip() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosLipGloss).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosLipGloss);
    }
    
    public void updateBlush(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosBlush).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosBlush, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosBlush).data(stickerData).build());
    }
    
    public void closeBlush() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosBlush).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosBlush);
    }
    
    public void updateEyebrow(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosBrows).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosBrows, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosBrows).data(stickerData).build());
    }
    
    public void closeEyebrow() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosBrows).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosBrows);
    }
    
    public void updateEyeshadow(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeShadow).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosEyeShadow, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeShadow).data(stickerData).build());
    }
    
    public void closeEyeshadow() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosEyeShadow).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosEyeShadow);
    }
    
    public void updateEyeline(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeLine).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosEyeLine, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeLine).data(stickerData).build());
    }
    
    public void closeEyeline() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosEyeLine).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosEyeLine);
    }
    
    public void updateEyelash(final StickerData stickerData) {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeLash).data(stickerData).build());
        this.b.put(StickerPositionInfo.StickerPositionType.CosEyeLash, new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Update).type(StickerPositionInfo.StickerPositionType.CosEyeLash).data(stickerData).build());
    }
    
    public void closeEyelash() {
        this.a.add(new CosmeticSticker.Builder().state(CosmeticSticker.CosmeticStickerState.Close).type(StickerPositionInfo.StickerPositionType.CosEyeLash).build());
        this.b.remove(StickerPositionInfo.StickerPositionType.CosEyeLash);
    }
}
