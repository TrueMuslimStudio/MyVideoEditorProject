// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.TuSDKMediaTransitionWrap;

public class TuSdkMediaTransitionEffectData extends TuSdkMediaEffectData
{
    private TuSDKMediaTransitionWrap.TuSDKMediaTransitionType a;
    
    public TuSdkMediaTransitionEffectData(final TuSDKMediaTransitionWrap.TuSDKMediaTransitionType a) {
        this.a = a;
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeTransition);
        this.setVaild(true);
        this.mFilterWrap = (FilterWrap)new TuSDKMediaTransitionWrap(a);
    }
    
    public final TuSDKMediaTransitionWrap.TuSDKMediaTransitionType getEffectCode() {
        return this.a;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaTransitionEffectData tuSdkMediaTransitionEffectData = new TuSdkMediaTransitionEffectData(this.a);
        tuSdkMediaTransitionEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaTransitionEffectData.setVaild(true);
        tuSdkMediaTransitionEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaTransitionEffectData.setIsApplied(false);
        return tuSdkMediaTransitionEffectData;
    }
    
    @Override
    public FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            this.mFilterWrap = (FilterWrap)new TuSDKMediaTransitionWrap(this.a);
        }
        return this.mFilterWrap;
    }
}
