// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.listview;

import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkRefreshListFooterView;

public class TuRefreshListFooterView extends TuSdkRefreshListFooterView
{
    private TextView a;
    private RelativeLayout b;
    
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_view_widget_list_view_refresh_footer_view");
    }
    
    public TuRefreshListFooterView(final Context context) {
        super(context);
    }
    
    public TuRefreshListFooterView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuRefreshListFooterView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    @Override
    public TextView getTitleLabel() {
        if (this.a == null) {
            this.a = this.getViewById("lsq_titleLabel");
        }
        return this.a;
    }
    
    public void setTitleLabel(final TextView a) {
        this.a = a;
    }
    
    @Override
    public RelativeLayout getFootWrap() {
        if (this.b == null) {
            this.b = this.getViewById("lsq_footWrap");
        }
        return this.b;
    }
    
    public void setFootWrap(final RelativeLayout b) {
        this.b = b;
    }
}
