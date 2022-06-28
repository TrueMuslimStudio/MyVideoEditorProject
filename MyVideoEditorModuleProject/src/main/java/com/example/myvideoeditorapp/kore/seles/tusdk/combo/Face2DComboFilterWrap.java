// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.combo;

import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker.TuSDKDynamicStickerImage;
import com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker.TuSDKLiveStickerImage;
import java.util.List;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.filters.SelesPointDrawFilter;
import com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker.TuSDKMap2DFilter;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class Face2DComboFilterWrap extends FilterWrap implements SelesParameters.FilterStickerInterface
{
    private SelesParameters a;
    private TuSDKMap2DFilter b;
    private SelesPointDrawFilter c;
    protected SelesOutInput mFirstFilter;
    private boolean d;
    
    public static Face2DComboFilterWrap creat() {
        return creat(FilterLocalPackage.shared().option(null));
    }
    
    public static Face2DComboFilterWrap creat(final FilterOption filterOption) {
        if (filterOption == null) {
            TLog.e("Can not found FilterOption", new Object[0]);
            return null;
        }
        return new Face2DComboFilterWrap(filterOption);
    }
    
    protected Face2DComboFilterWrap(final FilterOption filterOption) {
        this.d = false;
        this.b = new TuSDKMap2DFilter();
        if (this.d) {
            this.c = new SelesPointDrawFilter();
        }
        this.changeOption(filterOption);
    }
    
    @Override
    public Face2DComboFilterWrap clone() {
        final Face2DComboFilterWrap creat = creat(this.getOption());
        if (creat != null) {
            creat.setFilterParameter(this.getFilterParameter());
            creat.setStickerVisibility(this.isStickerVisibility());
        }
        return creat;
    }
    
    @Override
    protected void changeOption(final FilterOption filterOption) {
        super.changeOption(filterOption);
        final TuSDKMap2DFilter b = this.b;
        this.mLastFilter = b;
        this.mFilter = b;
    }
    
    @Override
    public void addOrgin(final SelesOutput selesOutput) {
        if (selesOutput == null || this.mFirstFilter == null) {
            return;
        }
        selesOutput.addTarget(this.mFirstFilter, 0);
    }
    
    @Override
    public void removeOrgin(final SelesOutput selesOutput) {
        if (selesOutput == null || this.mFirstFilter == null) {
            return;
        }
        selesOutput.removeTarget(this.mFirstFilter);
    }
    
    @Override
    public void setFilterParameter(final SelesParameters selesParameters) {
    }
    
    @Override
    public SelesParameters getFilterParameter() {
        return this.a;
    }
    
    @Override
    public void submitFilterParameter() {
        super.submitFilterParameter();
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
        this.b.updateFaceFeatures(array, n);
        if (this.d) {
            this.c.updateFaceFeatures(array, n);
        }
    }
    
    @Override
    public void updateStickers(final List<TuSDKLiveStickerImage> list) {
        this.b.updateStickers(list);
    }
    
    @Override
    public void updateDynamicStickers(final List<TuSDKDynamicStickerImage> list) {
        throw new UnsupportedOperationException();
    }
    
    public int[] getMap2DCurrentStickerIndexs() {
        return this.b.getCurrentStickerIndexs();
    }
    
    public long[] getMap2DCurrentStickerBenchmarkTime() {
        return this.b.getCurrentStickerBenchmarkTime();
    }
    
    public void setMap2DCurrentStickerIndex(final int[] currentStickerIndexs) {
        this.b.setCurrentStickerIndexs(currentStickerIndexs);
    }
    
    @Override
    public void setDisplayRect(final RectF rectF, final float n) {
        this.b.setDisplayRect(rectF, n);
    }
    
    @Override
    public void setEnableAutoplayMode(final boolean enableAutoplayMode) {
        this.b.setEnableAutoplayMode(enableAutoplayMode);
    }
    
    @Override
    public void seekStickerToFrameTime(final long n) {
        this.b.seekStickerToFrameTime(n);
    }
    
    @Override
    public void setBenchmarkTime(final long benchmarkTime) {
        this.b.setBenchmarkTime(benchmarkTime);
    }
    
    @Override
    public void setStickerVisibility(final boolean stickerVisibility) {
        this.b.setStickerVisibility(stickerVisibility);
    }
    
    public boolean isStickerVisibility() {
        return this.b.isStickerVisibility();
    }
}
