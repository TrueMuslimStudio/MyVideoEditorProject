// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker;

import android.graphics.RectF;
import com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker.TuSDKLiveStickerImage;
import java.util.List;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;

public class TuSdkDynamicStickerFilterWrap extends FilterWrap implements SelesParameters.FilterStickerInterface
{
    private TuSDKMap2DDynamicFilter a;
    
    public static TuSdkDynamicStickerFilterWrap creat() {
        return creat(FilterLocalPackage.shared().option(null));
    }
    
    public static TuSdkDynamicStickerFilterWrap creat(final FilterOption filterOption) {
        if (filterOption == null) {
            TLog.e("Can not found FilterOption", new Object[0]);
            return null;
        }
        return new TuSdkDynamicStickerFilterWrap(filterOption);
    }
    
    protected TuSdkDynamicStickerFilterWrap(final FilterOption filterOption) {
        this.a = new TuSDKMap2DDynamicFilter();
        this.changeOption(filterOption);
        this.a();
    }
    
    private void a() {
        final TuSDKMap2DDynamicFilter a = this.a;
        this.mFilter = a;
        this.mLastFilter = a;
    }
    
    @Override
    public void updateFaceFeatures(final FaceAligment[] array, final float n) {
    }
    
    public int[] getMap2DCurrentStickerIndexs() {
        return this.a.getCurrentStickerIndexs();
    }
    
    public void setMap2DCurrentStickerIndex(final int[] currentStickerIndexs) {
        this.a.setCurrentStickerIndexs(currentStickerIndexs);
    }
    
    @Override
    public void updateStickers(final List<TuSDKLiveStickerImage> list) {
    }
    
    @Override
    public void updateDynamicStickers(final List<TuSDKDynamicStickerImage> list) {
        this.a.updateStickers(list);
    }
    
    @Override
    public void setDisplayRect(final RectF rectF, final float n) {
        this.a.setDisplayRect(rectF, n);
    }
    
    @Override
    public void setEnableAutoplayMode(final boolean enableAutoplayMode) {
        this.a.setEnableAutoplayMode(enableAutoplayMode);
    }
    
    @Override
    public void seekStickerToFrameTime(final long n) {
        this.a.seekStickerToFrameTime(n);
    }
    
    @Override
    public void setBenchmarkTime(final long benchmarkTime) {
        this.a.setBenchmarkTime(benchmarkTime);
    }
    
    @Override
    public void setStickerVisibility(final boolean stickerVisibility) {
        this.a.setStickerVisibility(stickerVisibility);
    }
    
    public boolean isStickerVisibility() {
        return this.a.isStickerVisibility();
    }
}
