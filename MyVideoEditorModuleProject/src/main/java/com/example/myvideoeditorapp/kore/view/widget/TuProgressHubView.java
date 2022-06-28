// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget;

import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.example.myvideoeditorapp.kore.view.widget.TuSdkProgressHubView;

public class TuProgressHubView extends TuSdkProgressHubView
{
    private ProgressBar a;
    private TextView b;
    private LinearLayout c;
    private ImageView d;
    
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_view_widget_progress_hud_view");
    }
    
    public TuProgressHubView(final Context context) {
        super(context);
    }
    
    public TuProgressHubView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuProgressHubView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    @Override
    public ProgressBar getProgressBar() {
        if (this.a == null) {
            this.a = this.getViewById("lsq_progressBar");
        }
        return this.a;
    }
    
    @Override
    public ImageView getImageView() {
        if (this.d == null) {
            this.d = this.getViewById("lsq_hubImageView");
        }
        return this.d;
    }
    
    @Override
    public TextView getTitleView() {
        if (this.b == null) {
            this.b = this.getViewById("lsq_hubTitleView");
        }
        return this.b;
    }
    
    @Override
    public LinearLayout getHubView() {
        if (this.c == null) {
            this.c = this.getViewById("lsq_hubView");
        }
        return this.c;
    }
    
    @Override
    public int getImageSucceedResId() {
        return TuSdkContext.getDrawableResId("lsq_style_default_hud_success");
    }
    
    @Override
    public int getImageFailedResId() {
        return TuSdkContext.getDrawableResId("lsq_style_default_hud_error");
    }
}
