// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.widget.sticker.StickerView;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageView;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageViewInterface;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.RatioType;
import com.example.myvideoeditorapp.kore.view.widget.TuMaskRegionView;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerFactory;

import java.io.IOException;

public abstract class TuEditEntryFragmentBase extends TuImageResultFragment
{
    private FilterImageViewInterface a;
    private FilterWrap b;
    
    public abstract RelativeLayout getImageWrapView();
    
    public abstract TuMaskRegionView getCutRegionView();
    
    public abstract StickerView getStickerView();
    
    public abstract TuSdkResult getCuterResult();
    
    public abstract int[] getRatioTypes();
    
    public abstract int getLimitSideSize();
    
    public abstract boolean isLimitForScreen();
    
    public <T extends View> T getImageView() {
        if (this.a == null && this.getImageWrapView() != null) {
            this.a = new FilterImageView((Context)this.getActivity());
            final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.getImageWrapView().addView((View)this.a, 0, (ViewGroup.LayoutParams)layoutParams);
        }
        return (T)this.a;
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        this.getImageView();
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editTurnAndCutFragment);
    }
    
    public FilterWrap getFilterWrap() {
        return this.b;
    }

    public void setFilterWrap(FilterWrap var1) {
        this.b = var1;
        if (this.b != null && this.getImageView() != null) {
            this.b = this.b.clone();
            ((FilterImageViewInterface)this.getImageView()).setFilterWrap(this.b);
        }

    }
    public Bitmap getFilterImage() {
        Bitmap bitmap = this.getImage();
        if (bitmap == null) {
            return null;
        }
        if (this.getFilterWrap() != null) {
            bitmap = this.getFilterWrap().clone().process(bitmap);
        }
        return bitmap;
    }
    
    public Bitmap getCuterImage() {
        final Bitmap image = this.getImage();
        final TuSdkResult cuterResult = this.getCuterResult();
        final float firstRatio = RatioType.firstRatio(this.getRatioTypes()[0]);
        if (cuterResult != null) {
            return this.getCuterImage(image, cuterResult);
        }
        if (firstRatio > 0.0f) {
            return BitmapHelper.imageCorp(image, firstRatio);
        }
        return image;
    }
    
    private int a() {
        int a;
        if (this.getLimitSideSize() > 0) {
            a = this.getLimitSideSize();
        }
        else {
            a = ContextUtils.getScreenSize((Context)this.getActivity()).maxSide();
        }
        final Integer value = SdkValid.shared.maxImageSide();
        if (value == 0) {
            return a;
        }
        return Math.min(a, value);
    }
    
    protected void handleCompleteButton() {
        final TuSdkResult tuSdkResult = new TuSdkResult();
        tuSdkResult.filterWrap = this.getFilterWrap();
        if (this.getCuterResult() != null) {
            tuSdkResult.cutRect = this.getCuterResult().cutRect;
            tuSdkResult.imageOrientation = this.getCuterResult().imageOrientation;
        }
        else {
            tuSdkResult.imageSizeRatio = RatioType.firstRatio(this.getRatioTypes()[0]);
        }
        if (this.getStickerView() != null) {
            Rect regionRect = null;
            if (this.getCutRegionView() != null) {
                regionRect = this.getCutRegionView().getRegionRect();
            }
            tuSdkResult.stickers = this.getStickerView().getResults(regionRect);
        }
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuEditEntryFragmentBase.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        tuSdkResult.image = this.getCuterImage(tuSdkResult.image, tuSdkResult.cutRect, tuSdkResult.imageOrientation, tuSdkResult.imageSizeRatio);
        tuSdkResult.image = BitmapHelper.imageLimit(tuSdkResult.image, this.a());
        if (tuSdkResult.filterWrap != null) {
            tuSdkResult.image = tuSdkResult.filterWrap.process(tuSdkResult.image);
        }
        if (tuSdkResult.stickers != null) {
            tuSdkResult.image = StickerFactory.megerStickers(tuSdkResult.image, tuSdkResult.stickers);
            tuSdkResult.stickers = null;
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
    
    public final void appendStickerItem(final StickerData stickerData) {
        if (stickerData == null || this.getStickerView() == null) {
            return;
        }
        this.getStickerView().appendSticker(stickerData);
    }
}
