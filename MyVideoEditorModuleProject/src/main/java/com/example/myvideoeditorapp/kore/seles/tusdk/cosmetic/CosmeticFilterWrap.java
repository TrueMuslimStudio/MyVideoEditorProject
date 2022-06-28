// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import java.util.List;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class CosmeticFilterWrap extends FilterWrap implements SelesParameters.FilterCosmeticInterface
{
    public CosmeticFacialFilter facialFilter;
    public CosmeticLipFilter lipFilter;
    public CosmeticBlushFilter blushFilter;
    public CosmeticEyebrowFilter eyebrowFilter;
    public CosmeticEyePartFilter eyePartFilter;
    private SelesParameters a;
    public boolean[] toggleFilter;
    public List<SelesOutInput> cosFilterList;
    
    public static CosmeticFilterWrap create() {
        return create(FilterLocalPackage.shared().option(null));
    }
    
    public static CosmeticFilterWrap create(final FilterOption filterOption) {
        if (filterOption == null) {
            TLog.e("Can not found FilterOption", new Object[0]);
            return null;
        }
        return new CosmeticFilterWrap(filterOption);
    }
    
    public CosmeticFilterWrap(final FilterOption filterOption) {
        this.toggleFilter = new boolean[] { false, false, false, false, false };
        this.cosFilterList = new ArrayList<SelesOutInput>();
        this.facialFilter = new CosmeticFacialFilter();
        this.lipFilter = new CosmeticLipFilter();
        this.blushFilter = new CosmeticBlushFilter();
        this.eyebrowFilter = new CosmeticEyebrowFilter();
        this.eyePartFilter = new CosmeticEyePartFilter();
        this.cosFilterList.add(this.facialFilter);
        this.cosFilterList.add(this.lipFilter);
        this.cosFilterList.add(this.blushFilter);
        this.cosFilterList.add(this.eyebrowFilter);
        this.cosFilterList.add(this.eyePartFilter);
    }
    
    public SelesOutInput addTargetRecursive(final SelesOutInput selesOutInput, int n) {
        if (n > this.cosFilterList.size() - 1) {
            return selesOutInput;
        }
        if (this.toggleFilter[n]) {
            selesOutInput.addTarget(this.cosFilterList.get(n), 0);
            return this.addTargetRecursive(this.cosFilterList.get(n), ++n);
        }
        return this.addTargetRecursive(selesOutInput, ++n);
    }
    
    @Override
    public void refreshRelation() {
        if (this.mFilter != null) {
            this.mFilter.removeAllTargets();
            this.mFilter = null;
        }
        if (this.mLastFilter != null) {
            this.mLastFilter.removeAllTargets();
            this.mLastFilter = null;
        }
        this.facialFilter.removeAllTargets();
        this.lipFilter.removeAllTargets();
        this.blushFilter.removeAllTargets();
        this.eyebrowFilter.removeAllTargets();
        this.eyePartFilter.removeAllTargets();
        for (int i = 0; i < this.cosFilterList.size(); ++i) {
            if (this.toggleFilter[i]) {
                this.mFilter = this.cosFilterList.get(i);
                this.mLastFilter = this.addTargetRecursive(this.cosFilterList.get(i), ++i);
                break;
            }
        }
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
        this.facialFilter.updateFaceFeatures(array, n);
        this.lipFilter.updateFaceFeatures(array, n);
        this.eyebrowFilter.updateFaceFeatures(array, n);
        this.blushFilter.updateFaceFeatures(array, n);
        this.eyePartFilter.updateFaceFeatures(array, n);
    }
    
    @Override
    public FilterWrap clone() {
        final CosmeticFilterWrap cosmeticFilterWrap = new CosmeticFilterWrap(null);
        cosmeticFilterWrap.facialFilter.setParameter(this.facialFilter.getParameter());
        cosmeticFilterWrap.lipFilter.setParameter(this.lipFilter.getParameter());
        cosmeticFilterWrap.blushFilter.setParameter(this.blushFilter.getParameter());
        cosmeticFilterWrap.eyebrowFilter.setParameter(this.eyebrowFilter.getParameter());
        cosmeticFilterWrap.eyePartFilter.setParameter(this.eyePartFilter.getParameter());
        cosmeticFilterWrap.toggleFilter = this.toggleFilter;
        cosmeticFilterWrap.refreshRelation();
        return cosmeticFilterWrap;
    }
    
    @Override
    public void updateCosmeticFacial(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[0] = true;
        this.facialFilter.updateSticker(tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticFacial() {
        this.toggleFilter[0] = false;
    }
    
    @Override
    public void updateCosmeticLip(final CosmeticLipFilter.CosmeticLipType cosmeticLipType, final int n) {
        this.toggleFilter[1] = true;
        this.lipFilter.updateColor(new int[] { (n & 0xFF0000) >> 16, (n & 0xFF00) >> 8, n & 0xFF });
        this.lipFilter.updateType(cosmeticLipType);
    }
    
    @Override
    public void updateCosmeticLip(final CosmeticLipFilter.CosmeticLipType cosmeticLipType, final int[] array) {
        this.toggleFilter[1] = true;
        this.lipFilter.updateType(cosmeticLipType);
        this.lipFilter.updateColor(array);
    }
    
    @Override
    public void closeCosmeticLip() {
        this.toggleFilter[1] = false;
    }
    
    @Override
    public void updateCosmeticBlush(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[2] = true;
        this.blushFilter.updateSticker(tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticBlush() {
        this.toggleFilter[2] = false;
    }
    
    @Override
    public void updateCosmeticEyebrow(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[3] = true;
        this.eyebrowFilter.updateSticker(tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticEyebrow() {
        this.toggleFilter[3] = false;
    }
    
    @Override
    public void updateCosmeticEyeshadow(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[4] = true;
        this.eyePartFilter.updateStickers(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYESHADOW_TYPE, tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticEyeshadow() {
        this.eyePartFilter.close(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYESHADOW_TYPE);
        this.toggleFilter[4] = this.eyePartFilter.enable();
    }
    
    @Override
    public void updateCosmeticEyeline(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[4] = true;
        this.eyePartFilter.updateStickers(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYELINE_TYPE, tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticEyeline() {
        this.eyePartFilter.close(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYELINE_TYPE);
        this.toggleFilter[4] = this.eyePartFilter.enable();
    }
    
    @Override
    public void updateCosmeticEyelash(final TuSDKCosmeticImage tuSDKCosmeticImage) {
        this.toggleFilter[4] = true;
        this.eyePartFilter.updateStickers(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYELASH_TYPE, tuSDKCosmeticImage);
    }
    
    @Override
    public void closeCosmeticEyelash() {
        this.eyePartFilter.close(CosmeticEyePartFilter.CosmeticEyePartType.COSMETIC_EYELASH_TYPE);
        this.toggleFilter[4] = this.eyePartFilter.enable();
    }
    
    @Override
    public SelesParameters getFilterParameter() {
        if (this.a == null) {
            (this.a = new SelesParameters()).merge(this.facialFilter.getParameter());
            this.a.merge(this.lipFilter.getParameter());
            this.a.merge(this.blushFilter.getParameter());
            this.a.merge(this.eyebrowFilter.getParameter());
            this.a.merge(this.eyePartFilter.getParameter());
        }
        return this.a;
    }
    
    @Override
    public void submitFilterParameter() {
        this.facialFilter.submitParameter();
        this.lipFilter.submitParameter();
        this.blushFilter.submitParameter();
        this.eyebrowFilter.submitParameter();
        this.eyePartFilter.submitParameter();
    }
    
    @Override
    public boolean active() {
        for (int i = 0; i < this.toggleFilter.length; ++i) {
            if (this.toggleFilter[i]) {
                return true;
            }
        }
        return false;
    }
}
