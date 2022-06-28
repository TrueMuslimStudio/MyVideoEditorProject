// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import android.text.TextUtils;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterManager;

public class TuSdkMediaComicEffectData extends TuSdkMediaEffectData
{
    private String a;
    
    public TuSdkMediaComicEffectData(final String a) {
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediaEffectDataTypeComic);
        if (!FilterManager.shared().isConmicEffectFilter(a)) {
            TLog.e("Please enter a valid comic code.", new Object[0]);
            this.setVaild(false);
            return;
        }
        this.a = a;
        this.setVaild(!TextUtils.isEmpty((CharSequence)a));
    }
    
    @Override
    public TuSdkMediaEffectData clone() {
        if (!this.isVaild()) {
            return null;
        }
        final TuSdkMediaComicEffectData tuSdkMediaComicEffectData = new TuSdkMediaComicEffectData(this.a);
        tuSdkMediaComicEffectData.setAtTimeRange(this.getAtTimeRange());
        tuSdkMediaComicEffectData.setVaild(true);
        tuSdkMediaComicEffectData.setMediaEffectType(this.getMediaEffectType());
        tuSdkMediaComicEffectData.setIsApplied(false);
        return tuSdkMediaComicEffectData;
    }
    
    @Override
    public synchronized FilterWrap getFilterWrap() {
        if (this.mFilterWrap == null) {
            (this.mFilterWrap = FilterWrap.creat(FilterLocalPackage.shared().option(this.a))).processImage();
        }
        return this.mFilterWrap;
    }
}
