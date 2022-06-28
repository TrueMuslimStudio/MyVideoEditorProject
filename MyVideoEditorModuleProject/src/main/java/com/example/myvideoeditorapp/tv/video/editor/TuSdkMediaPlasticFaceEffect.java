// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKPlasticFaceWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class TuSdkMediaPlasticFaceEffect extends TuSdkMediaEffectData
{
    public TuSdkMediaPlasticFaceEffect() {
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypePlasticFace);
        this.setVaild(true);
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = (FilterWrap)new TuSDKPlasticFaceWrap()).processImage();
        }
        return this.mFilterWrap;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        if (!this.isVaild()) {
            return null;
        }
        final TuSdkMediaPlasticFaceEffect tuSdkMediaPlasticFaceEffect = new TuSdkMediaPlasticFaceEffect();
        tuSdkMediaPlasticFaceEffect.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaPlasticFaceEffect.setVaild(true);
        tuSdkMediaPlasticFaceEffect.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaPlasticFaceEffect.setIsApplied(false);
        tuSdkMediaPlasticFaceEffect.mFilterWrap = this.getFilterWrap().clone();
        if (tuSdkMediaPlasticFaceEffect.getFilterWrap() != null && this.mFilterWrap != null) {
            tuSdkMediaPlasticFaceEffect.getFilterWrap().getFilter().setParameter(this.mFilterWrap.getFilter().getParameter());
        }
        return tuSdkMediaPlasticFaceEffect;
    }
}
