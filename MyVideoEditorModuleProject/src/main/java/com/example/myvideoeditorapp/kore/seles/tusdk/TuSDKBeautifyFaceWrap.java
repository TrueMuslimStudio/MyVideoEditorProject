// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import com.example.myvideoeditorapp.kore.seles.tusdk.filters.beauty.TuSDKBeautifyFaceFilter;

public class TuSDKBeautifyFaceWrap extends FilterWrap
{
    public TuSDKBeautifyFaceWrap() {
        final TuSDKBeautifyFaceFilter tuSDKBeautifyFaceFilter = new TuSDKBeautifyFaceFilter();
        this.mLastFilter = tuSDKBeautifyFaceFilter;
        this.mFilter = tuSDKBeautifyFaceFilter;
    }
    
    public static TuSDKBeautifyFaceWrap creat() {
        return new TuSDKBeautifyFaceWrap();
    }
    
    @Override
    protected void changeOption(final FilterOption filterOption) {
    }
    
    @Override
    public FilterWrap clone() {
        final TuSDKBeautifyFaceWrap tuSDKBeautifyFaceWrap = new TuSDKBeautifyFaceWrap();
        tuSDKBeautifyFaceWrap.mFilter.setParameter(this.mFilter.getParameter());
        return tuSDKBeautifyFaceWrap;
    }
}
