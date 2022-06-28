// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk;

import com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins.TuSDKSkinMoistFilter;

public final class TuSDKSkinMoistWrap extends FilterWrap
{
    public TuSDKSkinMoistWrap() {
        final TuSDKSkinMoistFilter tuSDKSkinMoistFilter = new TuSDKSkinMoistFilter();
        this.mLastFilter = tuSDKSkinMoistFilter;
        this.mFilter = tuSDKSkinMoistFilter;
    }
    
    public static TuSDKSkinMoistWrap creat() {
        return new TuSDKSkinMoistWrap();
    }
    
    @Override
    protected void changeOption(final FilterOption filterOption) {
    }
    
    @Override
    public FilterWrap clone() {
        final TuSDKSkinMoistWrap tuSDKSkinMoistWrap = new TuSDKSkinMoistWrap();
        tuSDKSkinMoistWrap.mFilter.setParameter(this.mFilter.getParameter());
        return tuSDKSkinMoistWrap;
    }
}
