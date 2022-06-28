// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKBeautifyFaceWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKSkinMoistWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKSkinNaturalWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public final class TuSdkMediaSkinFaceEffect extends TuSdkMediaEffectData
{
    private SkinFaceType a;
    
    public TuSdkMediaSkinFaceEffect(final SkinFaceType a) {
        this.a = a;
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeSkinFace);
        this.setVaild(true);
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            if (this.a == SkinFaceType.SkinNatural) {
                this.mFilterWrap = (FilterWrap)new TuSDKSkinNaturalWrap();
            }
            else if (this.a == SkinFaceType.SkinMoist) {
                this.mFilterWrap = (FilterWrap)new TuSDKSkinMoistWrap();
            }
            else {
                this.mFilterWrap = (FilterWrap)new TuSDKBeautifyFaceWrap();
            }
            this.mFilterWrap.processImage();
        }
        return this.mFilterWrap;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        if (!this.isVaild()) {
            return null;
        }
        final TuSdkMediaSkinFaceEffect tuSdkMediaSkinFaceEffect = new TuSdkMediaSkinFaceEffect(this.a);
        tuSdkMediaSkinFaceEffect.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaSkinFaceEffect.setIsApplied(false);
        tuSdkMediaSkinFaceEffect.mFilterWrap = this.getFilterWrap().clone();
        if (tuSdkMediaSkinFaceEffect.getFilterWrap() != null && this.mFilterWrap != null) {
            tuSdkMediaSkinFaceEffect.getFilterWrap().getFilterParameter().syncArgs(this.mFilterWrap.getFilterParameter());
        }
        return tuSdkMediaSkinFaceEffect;
    }
    
    public enum SkinFaceType
    {
        SkinNatural, 
        SkinMoist, 
        Beauty;
    }
}
