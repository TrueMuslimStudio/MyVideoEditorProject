// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.sticker;

import android.graphics.Rect;
import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.widget.sticker.StickerView;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.view.widget.TuMaskRegionView;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerFactory;

import java.io.IOException;


public abstract class TuEditTextFragmentBase extends TuImageResultFragment
{
    public abstract StickerView getStickerView();
    
    public abstract TuMaskRegionView getCutRegionView();
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editTextFragment);
    }
    
    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }
    
    public final void appendStickerItem(final StickerData stickerData) {
        if (stickerData == null || this.getStickerView() == null) {
            return;
        }
        this.getStickerView().appendSticker(stickerData);
    }
    
    public void updateText(final String s, final boolean b) {
        if (s == null) {
            return;
        }
        this.getStickerView().updateText(s, b);
    }
    
    protected void toggleTextReverse() {
        if (this.getStickerView() == null) {
            return;
        }
        this.getStickerView().toggleTextReverse();
    }
    
    protected void handleCompleteButton() {
        if (this.getStickerView() == null) {
            this.handleBackButton();
            return;
        }
        final TuSdkResult tuSdkResult = new TuSdkResult();
        Rect regionRect = null;
        if (this.getCutRegionView() != null) {
            regionRect = this.getCutRegionView().getRegionRect();
        }
        tuSdkResult.stickers = this.getStickerView().getResults(regionRect);
        if (tuSdkResult.stickers == null || tuSdkResult.stickers.size() == 0) {
            this.handleBackButton();
            return;
        }
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuEditTextFragmentBase.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        if (tuSdkResult.stickers != null) {
            tuSdkResult.image = StickerFactory.megerStickers(tuSdkResult.image, tuSdkResult.stickers, null, !this.isShowResultPreview());
            tuSdkResult.stickers = null;
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
