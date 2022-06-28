// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.recyclerview;

import android.view.View;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkListSelectableCellViewInterface;
import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TuSdkLinearLayoutManager extends LinearLayoutManager
{
    public TuSdkLinearLayoutManager(final Context context) {
        super(context);
    }
    
    public TuSdkLinearLayoutManager(final Context context, final int n, final boolean b) {
        super(context, n, b);
        this.initManager();
    }
    
    protected void initManager() {
    }
    
    public void selectedPosition(final int n, final boolean b) {
        final View viewByPosition = this.findViewByPosition(n);
        if (viewByPosition == null || !(viewByPosition instanceof TuSdkListSelectableCellViewInterface)) {
            return;
        }
        if (b) {
            ((TuSdkListSelectableCellViewInterface)viewByPosition).onCellSelected(n);
        }
        else {
            ((TuSdkListSelectableCellViewInterface)viewByPosition).onCellDeselected();
        }
    }
}
