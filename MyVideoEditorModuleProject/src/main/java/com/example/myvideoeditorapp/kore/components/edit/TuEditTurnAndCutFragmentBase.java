// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
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
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageView;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageViewInterface;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.TuMaskRegionView;

import java.io.IOException;

public abstract class TuEditTurnAndCutFragmentBase extends TuImageResultFragment
{
    private String a;
    private TuSdkTouchImageViewInterface b;
    protected View.OnLayoutChangeListener mRegionLayoutChangeListener;
    
    public TuEditTurnAndCutFragmentBase() {
        this.mRegionLayoutChangeListener = (View.OnLayoutChangeListener)new View.OnLayoutChangeListener() {
            public void onLayoutChange(final View view, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
                if (n != n5 || n2 != n6 || n3 != n7 || n4 != n8) {
                    TuEditTurnAndCutFragmentBase.this.onRegionLayoutChanged(TuEditTurnAndCutFragmentBase.this.getCutRegionView());
                }
            }
        };
    }
    
    public abstract TuSdkSize getCutSize();
    
    public abstract RelativeLayout getImageWrapView();
    
    public abstract TuMaskRegionView getCutRegionView();
    
    public String getSelectedFilterCode() {
        return this.a;
    }
    
    public <T extends View> T getImageView() {
        if (this.b == null) {
            final RelativeLayout imageWrapView = this.getImageWrapView();
            if (imageWrapView != null) {
                this.b = new TuSdkTouchImageView((Context)this.getActivity());
                imageWrapView.addView((View)this.b, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                this.b.setInvalidateTargetView(this.getCutRegionView());
            }
        }
        return (T)this.b;
    }

    protected void onRegionLayoutChanged(TuMaskRegionView var1) {
        if (var1 != null && this.getImageView() != null) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).changeRegionRatio(var1.getRegionRect(), this.getCutSize() == null ? 0.0F : var1.getRegionRatio());
        }
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editEntryFragment);
    }
    
    @Override
    protected void viewDidLoad(ViewGroup var1) {
        if (this.getImage() == null) {
            this.notifyError((TuSdkResult)null, ComponentErrorType.TypeInputImageEmpty);
            TLog.e("Can not find input image.", new Object[0]);
        } else if (this.getImageView() != null) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).setImageBitmap(this.getImage());
            if (this.getCutSize() != null && this.getCutRegionView() != null) {
                this.getCutRegionView().setRegionSize(this.getCutSize());
                ((TuSdkTouchImageViewInterface)this.getImageView()).setScaleType(ImageView.ScaleType.CENTER_CROP);
                Rect var2 = this.getCutRegionView().getRegionRect();
                TuSdkViewHelper.setViewRect(this.getImageView(), var2);
                ((TuSdkTouchImageViewInterface)this.getImageView()).setZoom(1.0F);
            } else {
                ((TuSdkTouchImageViewInterface)this.getImageView()).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    }
    protected boolean handleSwitchFilter(final String var1) {
        if (this.getImageView() != null && !((TuSdkTouchImageViewInterface)this.getImageView()).isInAnimation()) {
            final Bitmap var2 = this.getImage();
            if (var2 != null && var1 != null && !var1.equalsIgnoreCase(this.a)) {
                this.a = var1;
                this.hubStatus(TuSdkContext.getString("lsq_edit_filter_processing"));
                ThreadHelper.runThread(new Runnable() {
                    public void run() {
                        TuEditTurnAndCutFragmentBase.this.a(var1, var2);
                    }
                });
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void a(String var1, Bitmap var2) {
        final Bitmap var3 = this.a(var2, var1, ((TuSdkTouchImageViewInterface)this.getImageView()).getImageOrientation());
        ThreadHelper.post(new Runnable() {
            public void run() {
                TuEditTurnAndCutFragmentBase.this.processedFilter(var3);
            }
        });
    }

    private Bitmap a(final Bitmap bitmap, final String s, final ImageOrientation imageOrientation) {
        final FilterWrap filterWrap = FilterLocalPackage.shared().getFilterWrap(s);
        if (filterWrap == null) {
            return bitmap;
        }
        filterWrap.setFilterParameter(null);
        return filterWrap.process(bitmap, imageOrientation, 0.0f);
    }
    

    protected void processedFilter(Bitmap var1) {
        if (this.getImageView() != null) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).setImageBitmapWithAnim(var1);
            this.hubDismiss();
        }
    }
    protected void handleCompleteButton() {
        if (this.getImageView() != null && !((TuSdkTouchImageViewInterface)this.getImageView()).isInAnimation()) {
            final TuSdkResult var1 = new TuSdkResult();
            var1.cutRect = ((TuSdkTouchImageViewInterface)this.getImageView()).getZoomedRect();
            var1.imageOrientation = ((TuSdkTouchImageViewInterface)this.getImageView()).getImageOrientation();
            var1.outputSize = this.getCutSize();
            var1.filterCode = this.getSelectedFilterCode();
            this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        TuEditTurnAndCutFragmentBase.this.asyncEditWithResult(var1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
        }
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        if (tuSdkResult.outputSize != null) {
            tuSdkResult.image = BitmapHelper.imageCorp(tuSdkResult.image, tuSdkResult.cutRect, tuSdkResult.outputSize, tuSdkResult.imageOrientation);
        }
        else {
            tuSdkResult.image = BitmapHelper.imageRotaing(tuSdkResult.image, tuSdkResult.imageOrientation);
        }
        if (tuSdkResult.filterCode != null) {
            tuSdkResult.image = this.a(tuSdkResult.image, tuSdkResult.filterCode, ImageOrientation.Up);
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
