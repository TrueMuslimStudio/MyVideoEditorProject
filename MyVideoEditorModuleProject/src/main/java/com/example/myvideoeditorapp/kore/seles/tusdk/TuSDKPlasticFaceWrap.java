// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.tusdk.reshape.TuSDKPlasticFaceFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;

public class TuSDKPlasticFaceWrap extends FilterWrap implements SelesParameters.FilterFacePositionInterface
{
    public TuSDKPlasticFaceWrap() {
        final TuSDKPlasticFaceFilter tuSDKPlasticFaceFilter = new TuSDKPlasticFaceFilter();
        this.mLastFilter = tuSDKPlasticFaceFilter;
        this.mFilter = tuSDKPlasticFaceFilter;
    }
    
    public static TuSDKPlasticFaceWrap creat() {
        return new TuSDKPlasticFaceWrap();
    }
    
    @Override
    protected void changeOption(final FilterOption filterOption) {
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
        if (this.mFilter == null || !(this.mFilter instanceof SelesParameters.FilterFacePositionInterface)) {
            return;
        }
        ((SelesParameters.FilterFacePositionInterface)this.mFilter).updateFaceFeatures(array, n);
    }
    
    @Override
    public FilterWrap clone() {
        final TuSDKPlasticFaceWrap tuSDKPlasticFaceWrap = new TuSDKPlasticFaceWrap();
        tuSDKPlasticFaceWrap.mFilter.setParameter(this.mFilter.getParameter());
        return tuSDKPlasticFaceWrap;
    }
}
