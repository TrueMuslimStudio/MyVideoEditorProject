// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.edit;

import android.content.Context;
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
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageView;
import com.example.myvideoeditorapp.kore.view.TuSdkTouchImageViewInterface;

import java.io.IOException;

public abstract class TuEditTurnFragmentBase extends TuImageResultFragment
{
    private TuSdkTouchImageViewInterface a;
    
    public abstract RelativeLayout getImageWrapView();
    
    public <T extends View> T getImageView() {
        if (this.a == null) {
            final RelativeLayout imageWrapView = this.getImageWrapView();
            if (imageWrapView != null) {
                this.a = new TuSdkTouchImageView((Context)this.getActivity());
                imageWrapView.addView((View)this.a, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
                this.a.setInvalidateTargetView((View)this.getImageWrapView());
            }
        }
        return (T)this.a;
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
            ((TuSdkTouchImageViewInterface)this.getImageView()).setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    protected void handleCompleteButton() {
        if (this.getImageView() != null && !((TuSdkTouchImageViewInterface)this.getImageView()).isInAnimation()) {
            final TuSdkResult var1 = new TuSdkResult();
            var1.imageOrientation = ((TuSdkTouchImageViewInterface)this.getImageView()).getImageOrientation();
            this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        TuEditTurnFragmentBase.this.asyncEditWithResult(var1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })).start();
        }
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        tuSdkResult.image = BitmapHelper.imageRotaing(tuSdkResult.image, tuSdkResult.imageOrientation);
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }

    protected void changeImageAnimation(TuSdkTouchImageViewInterface.LsqImageChangeType var1) {
        if (this.getImageView() != null && !((TuSdkTouchImageViewInterface)this.getImageView()).isInAnimation()) {
            ((TuSdkTouchImageViewInterface)this.getImageView()).changeImageAnimation(var1);
        }
    }
}
