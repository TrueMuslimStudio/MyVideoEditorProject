// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins.TuSDKSkinNaturalFilter;

public final class TuSDKSkinNaturalWrap extends FilterWrap
{
    public TuSDKSkinNaturalWrap() {
        final TuSDKSkinNaturalFilter tuSDKSkinNaturalFilter = new TuSDKSkinNaturalFilter();
        this.mLastFilter = tuSDKSkinNaturalFilter;
        this.mFilter = tuSDKSkinNaturalFilter;
    }
    
    public static TuSDKSkinNaturalWrap creat() {
        return new TuSDKSkinNaturalWrap();
    }
    
    @Override
    protected void changeOption(final FilterOption filterOption) {
    }
    
    @Override
    public FilterWrap clone() {
        final TuSDKSkinNaturalWrap tuSDKSkinNaturalWrap = new TuSDKSkinNaturalWrap();
        tuSDKSkinNaturalWrap.mFilter.setParameter(this.mFilter.getParameter());
        return tuSDKSkinNaturalWrap;
    }
}
