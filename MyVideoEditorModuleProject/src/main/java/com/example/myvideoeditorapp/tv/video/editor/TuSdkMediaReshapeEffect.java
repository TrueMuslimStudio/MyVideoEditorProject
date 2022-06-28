// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKReshapeWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class TuSdkMediaReshapeEffect extends TuSdkMediaEffectData
{
    public TuSdkMediaReshapeEffect() {
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeReshape);
        this.setVaild(true);
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = (FilterWrap)new TuSDKReshapeWrap()).processImage();
        }
        return this.mFilterWrap;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        if (!this.isVaild()) {
            return null;
        }
        final TuSdkMediaReshapeEffect tuSdkMediaReshapeEffect = new TuSdkMediaReshapeEffect();
        tuSdkMediaReshapeEffect.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaReshapeEffect.setVaild(true);
        tuSdkMediaReshapeEffect.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaReshapeEffect.setIsApplied(false);
        tuSdkMediaReshapeEffect.mFilterWrap = this.getFilterWrap().clone();
        if (tuSdkMediaReshapeEffect.getFilterWrap() != null && this.mFilterWrap != null) {
            tuSdkMediaReshapeEffect.getFilterWrap().getFilter().setParameter(this.mFilterWrap.getFilter().getParameter());
        }
        return tuSdkMediaReshapeEffect;
    }
}
