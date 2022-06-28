// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.tusdk.reshape.TuSDKReshapeGroup;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;

public class TuSDKReshapeWrap extends FilterWrap implements SelesParameters.FilterFacePositionInterface
{
    public TuSDKReshapeWrap() {
        final TuSDKReshapeGroup tuSDKReshapeGroup = new TuSDKReshapeGroup();
        this.mLastFilter = tuSDKReshapeGroup;
        this.mFilter = tuSDKReshapeGroup;
    }
    
    public static TuSDKReshapeWrap creat() {
        return new TuSDKReshapeWrap();
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
        final TuSDKReshapeWrap tuSDKReshapeWrap = new TuSDKReshapeWrap();
        tuSDKReshapeWrap.mFilter.setParameter(this.mFilter.getParameter());
        return tuSDKReshapeWrap;
    }
}
