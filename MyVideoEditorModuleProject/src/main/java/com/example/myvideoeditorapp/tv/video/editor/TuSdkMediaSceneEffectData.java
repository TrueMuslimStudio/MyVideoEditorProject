// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.filters.lives.TuSDKLiveMegrimFilter;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterManager;

public class TuSdkMediaSceneEffectData extends TuSdkMediaEffectData
{
    private String a;
    
    public TuSdkMediaSceneEffectData(final String a) {
        this.a = a;
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeScene);
        this.setVaild(FilterManager.shared().isSceneEffectFilter(a));
        if (!this.isVaild()) {
            TLog.e("Invalid scene effect code \uff1a%s", new Object[] { a });
        }
    }
    
    public final String getEffectCode() {
        return this.a;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        final TuSdkMediaSceneEffectData tuSdkMediaSceneEffectData = new TuSdkMediaSceneEffectData(this.a);
        tuSdkMediaSceneEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaSceneEffectData.setVaild(true);
        tuSdkMediaSceneEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaSceneEffectData.setIsApplied(false);
        return tuSdkMediaSceneEffectData;
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = FilterWrap.creat(FilterLocalPackage.shared().option(this.a))).processImage();
            if (this.mFilterWrap.getFilter() instanceof TuSDKLiveMegrimFilter) {
                ((TuSDKLiveMegrimFilter)this.mFilterWrap.getFilter()).enableSeprarate();
            }
        }
        return this.mFilterWrap;
    }
}
