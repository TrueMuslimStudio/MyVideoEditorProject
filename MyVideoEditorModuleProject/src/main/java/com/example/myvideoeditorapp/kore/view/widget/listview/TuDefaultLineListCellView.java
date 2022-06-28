// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.listview;

import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.widget.TextView;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellRelativeLayout;

public class TuDefaultLineListCellView extends TuSdkCellRelativeLayout<String>
{
    private TextView a;
    
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_view_widget_list_view_default_line_cell_view");
    }
    
    public TuDefaultLineListCellView(final Context context) {
        super(context);
    }
    
    public TuDefaultLineListCellView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuDefaultLineListCellView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TextView getTitleView() {
        if (this.a == null) {
            this.a = this.getViewById("lsq_titleView");
        }
        return this.a;
    }
    
    public void setTitleView(final TextView a) {
        this.a = a;
    }

    protected void bindModel() {
        if (this.getTitleView() != null && this.getModel() != null) {
            this.getTitleView().setText((CharSequence)this.getModel());
        }
    }
}
