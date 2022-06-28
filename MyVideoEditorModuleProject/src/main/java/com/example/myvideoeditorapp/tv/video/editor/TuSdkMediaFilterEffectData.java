// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import android.text.TextUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterManager;

public final class TuSdkMediaFilterEffectData extends TuSdkMediaEffectData
{
    private String a;
    
    public TuSdkMediaFilterEffectData(final String a) {
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeFilter);
        if (!FilterManager.shared().isFilterEffect(a)) {
            TLog.e("Invalid filter effect code \uff1a%s", new Object[] { a });
            this.setVaild(false);
            return;
        }
        this.a = a;
        this.setVaild(!TextUtils.isEmpty((CharSequence)a));
        if (!this.isVaild()) {
            TLog.e("Invalid filter effect code \uff1a%s", new Object[] { a });
        }
    }
    
    public String getFilterCode() {
        return this.a;
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = FilterWrap.creat(FilterLocalPackage.shared().option(this.a))).processImage();
        }
        return this.mFilterWrap;
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        if (!this.isVaild()) {
            return null;
        }
        final TuSdkMediaFilterEffectData tuSdkMediaFilterEffectData = new TuSdkMediaFilterEffectData(this.a);
        tuSdkMediaFilterEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaFilterEffectData.setVaild(true);
        tuSdkMediaFilterEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaFilterEffectData.setIsApplied(false);
        tuSdkMediaFilterEffectData.mFilterWrap = tuSdkMediaFilterEffectData.getFilterWrap();
        return tuSdkMediaFilterEffectData;
    }
}
