// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.listview;

import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkGroupListView;
import android.view.View;

public abstract class TuGroupListView<T, V extends View, M, N extends View> extends TuSdkGroupListView<T, V, M, N>
{
    public TuGroupListView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuGroupListView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuGroupListView(final Context context) {
        super(context);
    }
    
    @Override
    protected void initView() {
        this.setTotalFooterViewId(TuListTotalFootView.getLayoutId());
        this.setRefreshLayoutResId(TuRefreshListHeaderView.getLayoutId(), TuRefreshListFooterView.getLayoutId());
        super.initView();
    }
}
