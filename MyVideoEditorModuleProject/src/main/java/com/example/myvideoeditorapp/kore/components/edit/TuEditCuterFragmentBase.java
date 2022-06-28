// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.edit;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.ComponentErrorType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.utils.image.RatioType;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageView;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageViewInterface;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.TuMaskRegionView;

import java.io.IOException;


public abstract class TuEditCuterFragmentBase extends TuImageResultFragment
{
    private TuSdkTouchImageViewInterface a;
    protected View.OnLayoutChangeListener mRegionLayoutChangeListener;
    private RectF b;
    private float c;
    private ImageOrientation d;
    private int e;
    private float f;
    
    public TuEditCuterFragmentBase() {
        this.mRegionLayoutChangeListener = (View.OnLayoutChangeListener)new View.OnLayoutChangeListener() {
            public void onLayoutChange(final View view, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
                if (n != n5 || n2 != n6 || n3 != n7 || n4 != n8) {
                    TuEditCuterFragmentBase.this.onRegionLayoutChanged(TuEditCuterFragmentBase.this.getCutRegionView());
                }
            }
        };
        this.d = ImageOrientation.Up;
    }
    
    public abstract int[] getRatioTypes();
    
    public abstract boolean isOnlyReturnCuter();
    
    public abstract RelativeLayout getImageWrapView();
    
    public abstract TuMaskRegionView getCutRegionView();
    
    public <T extends View> T getImageView() {
        if (this.a == null) {
            final RelativeLayout imageWrapView = this.getImageWrapView();
            if (imageWrapView != null) {
                this.a = new TuSdkTouchImageView((Context)this.getActivity());
                imageWrapView.addView((View)this.a, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                this.a.setInvalidateTargetView(this.getCutRegionView());
            }
        }
        return (T)this.a;
    }

    protected void onRegionLayoutChanged(TuMaskRegionView var1) {
        if (var1 != null && this.getImageView() != null) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).changeRegionRatio(var1.getRegionRect(), var1.getRegionRatio());
        }
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
    }
    
    @Override
    protected  void viewDidLoad(ViewGroup var1) {
        if (this.getImage() == null) {
            this.notifyError((TuSdkResult)null, ComponentErrorType.TypeInputImageEmpty);
            TLog.e("Can not find input image.", new Object[0]);
        } else if (this.getImageView() != null) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).setImageBitmap(this.getImage(), this.getImageOrientation());
            if ((this.getRatioTypes().length != 1 || this.getRatioTypes()[0] != 1) && this.getCurrentRatio() != 0.0F && this.getCutRegionView() != null) {
                Rect var2 = this.getCutRegionView().setRegionRatio(this.getCurrentRatio());
                ((TuSdkTouchImageViewInterface)this.getImageView()).setScaleType(ImageView.ScaleType.CENTER_CROP);
                TuSdkViewHelper.setViewRect(this.getImageView(), var2);
                RectF var3 = this.getZoomRect();
                if (var3 == null) {
                    ((TuSdkTouchImageViewInterface)this.getImageView()).setZoom(1.0F);
                } else if (var3 != null) {
                    ((TuSdkTouchImageViewInterface)this.getImageView()).setZoom(this.getZoomScale(), (var3.left + var3.right) * 0.5F, (var3.top + var3.bottom) * 0.5F);
                }

                StatisticsManger.appendComponent(ComponentActType.editCuterFragment);
            } else {
                ((TuSdkTouchImageViewInterface)this.getImageView()).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    }
    
    public final RectF getZoomRect() {
        return this.b;
    }
    
    public final void setZoomRect(final RectF b) {
        this.b = b;
    }
    
    public final float getZoomScale() {
        if (this.c <= 0.0f) {
            this.c = 1.0f;
        }
        return this.c;
    }
    
    public final void setZoomScale(final float c) {
        this.c = c;
    }
    
    public final ImageOrientation getImageOrientation() {
        return this.d;
    }
    
    public final void setImageOrientation(final ImageOrientation d) {
        this.d = d;
    }
    
    public final int getCurrentRatioType() {
        if (this.e < 1) {
            int[] array = this.getRatioTypes();
            if (array == null || array.length == 0) {
                array = RatioType.ratioTypes;
            }
            this.setCurrentRatioType(array[0]);
        }
        return this.e;
    }
    
    public final void setCurrentRatioType(final int e) {
        this.e = e;
        this.f = RatioType.ratio(this.e);
        StatisticsManger.appendComponent(RatioType.ratioActionType(e));
    }
    
    public final float getCurrentRatio() {
        return this.f;
    }
    
    public final void setCuterResult(final TuSdkResult tuSdkResult) {
        if (tuSdkResult == null) {
            return;
        }
        this.setZoomRect(tuSdkResult.cutRect);
        this.setZoomScale(tuSdkResult.cutScale);
        this.setImageOrientation(tuSdkResult.imageOrientation);
        this.setCurrentRatioType(tuSdkResult.cutRatioType);
    }

    protected void handleCompleteButton() {
        if (this.getImageView() != null && !((TuSdkTouchImageViewInterface)this.getImageView()).isInAnimation()) {
            final TuSdkResult var1 = new TuSdkResult();
            var1.imageOrientation = ((TuSdkTouchImageViewInterface)this.getImageView()).getImageOrientation();
            if (this.getCurrentRatio() > 0.0F) {
                var1.imageSizeRatio = this.getCurrentRatio();
                var1.cutRect = ((TuSdkTouchImageViewInterface)this.getImageView()).getZoomedRect();
                var1.cutScale = ((TuSdkTouchImageViewInterface)this.getImageView()).getCurrentZoom();
            }

            var1.cutRatioType = this.getCurrentRatioType();
            this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        TuEditCuterFragmentBase.this.asyncEditWithResult(var1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            })).start();
        }
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        if (this.isOnlyReturnCuter()) {
            this.backUIThreadNotifyProcessing(tuSdkResult);
            return;
        }
        this.loadOrginImage(tuSdkResult);
        tuSdkResult.image = this.getCuterImage(tuSdkResult.image, tuSdkResult);
        TLog.d("editCompleted:%s", tuSdkResult);
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
