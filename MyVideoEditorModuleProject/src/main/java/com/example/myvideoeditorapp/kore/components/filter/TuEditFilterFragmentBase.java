// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.ComponentErrorType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageView;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageViewInterface;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;

import java.io.IOException;

public abstract class TuEditFilterFragmentBase extends TuImageResultFragment
{
    private FilterWrap a;
    private FilterImageViewInterface b;
    
    public abstract RelativeLayout getImageWrapView();
    
    public abstract void notifyFilterConfigView();
    
    public abstract boolean isOnlyReturnFilter();
    
    public FilterWrap getFilterWrap() {
        return this.a;
    }
    
    public void setFilterWrap(final FilterWrap a) {
        this.a = a;
    }
    
    public <T extends View> T getImageView() {
        if (this.b == null && this.getImageWrapView() != null) {
            (this.b = new FilterImageView((Context)this.getActivity())).enableTouchForOrigin();
            final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.getImageWrapView().addView((View)this.b, 0, (ViewGroup.LayoutParams)layoutParams);
        }
        return (T)this.b;
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        this.getImageView();
    }
    
    @Override
    protected void viewDidLoad(ViewGroup var1) {
        StatisticsManger.appendComponent(ComponentActType.editFilterFragment);
        if (this.getImage() == null) {
            this.notifyError((TuSdkResult)null, ComponentErrorType.TypeInputImageEmpty);
            TLog.e("Can not find input image.", new Object[0]);
        } else if (this.getImageView() != null) {
            ((FilterImageViewInterface)this.getImageView()).setImage(this.getImage());
            if (this.getFilterWrap() != null) {
                this.a = this.getFilterWrap().clone();
                ((FilterImageViewInterface)this.getImageView()).setFilterWrap(this.a);
            }

            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    TuEditFilterFragmentBase.this.notifyFilterConfigView();
                }
            }, 1L);
        }
    }
    
    protected boolean handleSwitchFilter(final String s) {
        if (s == null || this.getImageView() == null) {
            return false;
        }
        if (this.a != null && this.a.equalsCode(s)) {
            return false;
        }
        this.hubStatus(TuSdkContext.getString("lsq_edit_filter_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                TuEditFilterFragmentBase.this.asyncProcessingFilter(s);
            }
        }).start();
        return true;
    }

    protected void asyncProcessingFilter(String var1) {
        this.a = FilterLocalPackage.shared().getFilterWrap(var1);
        if (this.a != null) {
            ((FilterImageViewInterface)this.getImageView()).setFilterWrap(this.a);
        }

        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
                TuEditFilterFragmentBase.this.processedFilter();
            }
        });
    }
    
    protected void processedFilter() {
        this.hubDismiss();
        this.notifyFilterConfigView();
    }
    
    protected void handleCompleteButton() {
        final TuSdkResult tuSdkResult = new TuSdkResult();
        tuSdkResult.filterWrap = this.getFilterWrap();
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuEditFilterFragmentBase.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        if (this.isOnlyReturnFilter()) {
            this.backUIThreadNotifyProcessing(tuSdkResult);
            return;
        }
        this.loadOrginImage(tuSdkResult);
        if (tuSdkResult.filterWrap != null && tuSdkResult.image != null) {
            tuSdkResult.image = BitmapHelper.imageScale(tuSdkResult.image, TuSdkSize.create(tuSdkResult.image).limitScale());
            tuSdkResult.image = tuSdkResult.filterWrap.process(tuSdkResult.image);
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
