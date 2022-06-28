// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.listview;

import android.content.Context;
import android.util.AttributeSet;

import com.example.myvideoeditorapp.kore.view.listview.TuSdkIndexPath;

public class TuDefaultLineListView extends TuArrayListView<String, TuDefaultLineListCellView>
{
    public TuDefaultLineListView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuDefaultLineListView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuDefaultLineListView(final Context context) {
        super(context);
    }
    
    @Override
    public int getCellLayoutId() {
        if (super.getCellLayoutId() == 0) {
            this.setCellLayoutId(TuDefaultLineListCellView.getLayoutId());
        }
        return super.getCellLayoutId();
    }
    
    @Override
    protected void onArrayListViewCreated(final TuDefaultLineListCellView tuDefaultLineListCellView, final TuSdkIndexPath tuSdkIndexPath) {
    }
    
    @Override
    protected void onArrayListViewBinded(final TuDefaultLineListCellView tuDefaultLineListCellView, final TuSdkIndexPath tuSdkIndexPath) {
    }
}
