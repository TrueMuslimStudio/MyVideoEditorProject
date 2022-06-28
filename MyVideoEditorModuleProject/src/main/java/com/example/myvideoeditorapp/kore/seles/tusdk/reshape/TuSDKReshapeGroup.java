// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.reshape;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilterGroup;

public class TuSDKReshapeGroup extends SelesFilterGroup implements SelesParameters.FilterFacePositionInterface, SelesParameters.FilterParameterInterface
{
    public SelesFilter mScaleFilter;
    public TuSDKMeanBlurFilter mMeanBlurFilter;
    public TuSDKReshapeFaceFilter mReshapeFaceFilter;
    public TuSDKReshapeEyeFilter mReshapeEyeFilter;
    
    public TuSDKReshapeGroup() {
        (this.mScaleFilter = new SelesFilter()).setScale(0.25f);
        this.mMeanBlurFilter = new TuSDKMeanBlurFilter();
        this.mScaleFilter.addTarget(this.mMeanBlurFilter, 0);
        this.mReshapeFaceFilter = new TuSDKReshapeFaceFilter();
        this.mScaleFilter.addTarget(this.mReshapeFaceFilter, 1);
        this.mMeanBlurFilter.addTarget(this.mReshapeFaceFilter, 2);
        this.mReshapeEyeFilter = new TuSDKReshapeEyeFilter();
        this.mReshapeFaceFilter.addTarget(this.mReshapeEyeFilter, 0);
        this.addFilter(this.mReshapeEyeFilter);
        this.setInitialFilters(this.mScaleFilter, this.mReshapeFaceFilter);
        this.setTerminalFilter(this.mReshapeEyeFilter);
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
        this.mReshapeFaceFilter.updateFaceFeatures(array, n);
        this.mReshapeEyeFilter.updateFaceFeatures(array, n);
    }
    
    @Override
    protected SelesParameters initParams(SelesParameters initParams) {
        initParams = super.initParams(initParams);
        initParams.merge(this.mReshapeFaceFilter.getParameter());
        initParams.merge(this.mReshapeEyeFilter.getParameter());
        return initParams;
    }
    
    @Override
    protected void submitFilterArg(final SelesParameters.FilterArg filterArg) {
        if (filterArg == null) {
            return;
        }
        this.mReshapeFaceFilter.submitFilterArg(filterArg);
        this.mReshapeEyeFilter.submitFilterArg(filterArg);
        this.mMeanBlurFilter.a(this.mReshapeFaceFilter.a());
    }
}
