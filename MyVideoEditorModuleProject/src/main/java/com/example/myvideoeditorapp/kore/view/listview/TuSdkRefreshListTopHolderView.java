// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.listview;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import com.example.myvideoeditorapp.kore.view.TuSdkLinearLayout;

public class TuSdkRefreshListTopHolderView extends TuSdkLinearLayout
{
    private View a;
    
    public TuSdkRefreshListTopHolderView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuSdkRefreshListTopHolderView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuSdkRefreshListTopHolderView(final Context context) {
        super(context);
    }
    
    @Override
    public void loadView() {
        super.loadView();
        this.addView(this.a = new View(this.getContext()), (ViewGroup.LayoutParams)new LayoutParams(-1, 0));
    }
    
    public void setVisiableHeight(final int height) {
        final ViewGroup.LayoutParams layoutParams = this.a.getLayoutParams();
        layoutParams.height = height;
        this.a.setLayoutParams(layoutParams);
    }
}
