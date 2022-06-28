// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.listview;

import android.view.ViewGroup;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;

public abstract class TuSdkGroupListView<T, V extends View, M, N extends View> extends TuSdkListView
{
    private GroupListViewItemClickListener<Object, View> a;
    private GroupListViewHeaderClickListener<Object, View> b;
    private GroupListViewDeleagte c;
    private int d;
    private int e;
    
    public TuSdkGroupListView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuSdkGroupListView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuSdkGroupListView(final Context context) {
        super(context);
    }
    
    public void setCellLayoutId(final int d) {
        this.d = d;
    }
    
    public int getCellLayoutId() {
        return this.d;
    }
    
    public int getHeaderLayoutId() {
        return this.e;
    }
    
    public void setHeaderLayoutId(final int e) {
        this.e = e;
    }
    
    public void setItemClickListener(final GroupListViewItemClickListener<Object, View> a) {
        this.a = a;
    }
    
    public void setHeaderClickListener(final GroupListViewHeaderClickListener<Object, View> b) {
        this.b = b;
    }
    
    @Override
    public void setDataSource(final TuSdkIndexPath.TuSdkDataSource dataSource) {
        super.setDataSource(dataSource);
        if (this.c == null) {
            this.setDeleagte(this.c = new GroupListViewDeleagte());
        }
        this.reloadData();
    }
    
    protected abstract void onGroupListViewCreated(final V p0, final TuSdkIndexPath p1);
    
    protected abstract void onGroupListHeaderCreated(final N p0, final TuSdkIndexPath p1);
    
    private class GroupListViewDeleagte implements TuSdkListViewDeleagte
    {
        @Override
        public void onListViewItemClick(final TuSdkListView tuSdkListView, final View view, final TuSdkIndexPath tuSdkIndexPath) {
            if (TuSdkViewHelper.isFastDoubleClick(1000L)) {
                return;
            }
            if (tuSdkIndexPath == null) {
                return;
            }
            if (tuSdkIndexPath.viewType == 0 && TuSdkGroupListView.this.a != null) {
                TuSdkGroupListView.this.a.onGroupItemClick(TuSdkGroupListView.this.getDataSource().getItem(tuSdkIndexPath), view, tuSdkIndexPath);
            }
            else if (tuSdkIndexPath.viewType == 1 && TuSdkGroupListView.this.b != null) {
                TuSdkGroupListView.this.b.onGroupHeaderClick(TuSdkGroupListView.this.getDataSource().getItem(tuSdkIndexPath), view, tuSdkIndexPath);
            }
        }
        
        @Override
        public View onListViewItemCreate(final TuSdkListView tuSdkListView, final TuSdkIndexPath tuSdkIndexPath, final ViewGroup viewGroup) {
            View view = null;
            if (tuSdkIndexPath.viewType == 0) {
                view = TuSdkViewHelper.buildView(TuSdkGroupListView.this.getContext(), TuSdkGroupListView.this.getCellLayoutId(), viewGroup);
                TuSdkGroupListView.this.onGroupListViewCreated((V) view, tuSdkIndexPath);
            }
            else if (tuSdkIndexPath.viewType == 1) {
                view = TuSdkViewHelper.buildView(TuSdkGroupListView.this.getContext(), TuSdkGroupListView.this.getHeaderLayoutId(), viewGroup);
                TuSdkGroupListView.this.onGroupListHeaderCreated((N) view, tuSdkIndexPath);
            }
            return view;
        }
    }
    
    public interface GroupListViewItemClickListener<T, V>
    {
        void onGroupItemClick(final T p0, final V p1, final TuSdkIndexPath p2);
    }
    
    public interface GroupListViewHeaderClickListener<M, N>
    {
        void onGroupHeaderClick(final M p0, final N p1, final TuSdkIndexPath p2);
    }
}
