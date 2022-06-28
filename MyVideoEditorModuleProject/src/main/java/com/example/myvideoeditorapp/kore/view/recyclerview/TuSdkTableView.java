// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.recyclerview;

import android.view.ViewGroup;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellViewInterface;
import android.content.Context;
import java.util.List;
import android.view.View;

public abstract class TuSdkTableView<T, V extends View> extends TuSdkRecyclerView
{
    private TuSdkTableViewItemClickDelegate<T, V> a;
    private TuSdkAdapter<T> b;
    private TuSdkLinearLayoutManager c;
    private int d;
    private List<T> e;
    private int f;
    private boolean g;
    private int h;
    protected TuSdkViewHolder.TuSdkViewHolderItemClickListener<T> mViewHolderItemClickListener;
    
    public TuSdkTableView(final Context context) {
        super(context);
        this.f = 0;
        this.h = -1;
        this.mViewHolderItemClickListener = new TuSdkViewHolder.TuSdkViewHolderItemClickListener<T>() {
            @Override
            public void onViewHolderItemClick(final TuSdkViewHolder<T> tuSdkViewHolder) {
                if (TuSdkTableView.this.a == null) {
                    return;
                }
                if (tuSdkViewHolder.itemView instanceof TuSdkCellViewInterface) {
                    TuSdkTableView.this.a.onTableViewItemClick(tuSdkViewHolder.getModel(), (V) tuSdkViewHolder.itemView, tuSdkViewHolder.getPosition());
                }
            }
        };
    }
    
    public TuSdkTableView(final Context context, final AttributeSet set) {
        super(context, set);
        this.f = 0;
        this.h = -1;
        this.mViewHolderItemClickListener = new TuSdkViewHolder.TuSdkViewHolderItemClickListener<T>() {
            @Override
            public void onViewHolderItemClick(final TuSdkViewHolder<T> tuSdkViewHolder) {
                if (TuSdkTableView.this.a == null) {
                    return;
                }
                if (tuSdkViewHolder.itemView instanceof TuSdkCellViewInterface) {
                    TuSdkTableView.this.a.onTableViewItemClick(tuSdkViewHolder.getModel(), (V) tuSdkViewHolder.itemView, tuSdkViewHolder.getPosition());
                }
            }
        };
    }
    
    public TuSdkTableView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.f = 0;
        this.h = -1;
        this.mViewHolderItemClickListener = new TuSdkViewHolder.TuSdkViewHolderItemClickListener<T>() {
            @Override
            public void onViewHolderItemClick(final TuSdkViewHolder<T> tuSdkViewHolder) {
                if (TuSdkTableView.this.a == null) {
                    return;
                }
                if (tuSdkViewHolder.itemView instanceof TuSdkCellViewInterface) {
                    TuSdkTableView.this.a.onTableViewItemClick(tuSdkViewHolder.getModel(), (V) tuSdkViewHolder.itemView, tuSdkViewHolder.getPosition());
                }
            }
        };
    }
    
    public void setItemClickDelegate(final TuSdkTableViewItemClickDelegate<T, V> a) {
        this.a = a;
        if (this.b == null) {
            return;
        }
        if (a == null) {
            this.b.setItemClickListener(null);
        }
        else {
            this.b.setItemClickListener(this.mViewHolderItemClickListener);
        }
    }
    
    public TuSdkTableViewItemClickDelegate<T, V> getItemClickDelegate() {
        return this.a;
    }
    
    public int getCellLayoutId() {
        return this.d;
    }
    
    public void setCellLayoutId(final int d) {
        this.d = d;
        if (d > 0 && this.b != null) {
            this.b.setViewLayoutId(this.getCellLayoutId());
        }
    }
    
    public List<T> getModeList() {
        return this.e;
    }
    
    public void setModeList(final List<T> e) {
        this.e = e;
        if (this.b != null) {
            this.b.setModeList(this.e);
        }
    }
    
    public TuSdkAdapter<T> getSdkAdapter() {
        if (this.b == null) {
            (this.b = new TableViewAdapter(this.getCellLayoutId(), this.e)).setSelectedPosition(this.h);
            if (this.a != null) {
                this.b.setItemClickListener(this.mViewHolderItemClickListener);
            }
        }
        return this.b;
    }
    
    public void setAdapter(final Adapter adapter) {
        if (adapter instanceof TuSdkAdapter) {
            this.b = (TuSdkAdapter<T>)adapter;
        }
        super.setAdapter(adapter);
    }
    
    public TuSdkLinearLayoutManager getSdkLayoutManager() {
        if (this.c == null) {
            this.c = new TuSdkLinearLayoutManager(this.getContext(), this.f, this.g);
        }
        return this.c;
    }
    
    public void setLayoutManager(final LayoutManager layoutManager) {
        if (layoutManager instanceof TuSdkLinearLayoutManager) {
            this.c = (TuSdkLinearLayoutManager)layoutManager;
        }
        super.setLayoutManager(layoutManager);
    }
    
    public int getOrientation() {
        return this.f;
    }
    
    public void setOrientation(final int f) {
        this.f = f;
        if (this.c != null) {
            this.c.setOrientation(this.f);
        }
    }
    
    public void scrollToPositionWithOffset(final int n, final int n2) {
        if (this.c != null) {
            this.c.scrollToPositionWithOffset(n, n2);
        }
    }
    
    public void smoothScrollByCenter(final View view) {
        if (view == null) {
            return;
        }
        if (this.f == 0) {
            this.smoothScrollBy(TuSdkViewHelper.locationInWindowLeft(view) - TuSdkViewHelper.locationInWindowLeft((View)this) - (this.getWidth() - view.getWidth()) / 2, 0);
        }
        else if (this.f == 1) {
            this.smoothScrollBy(0, TuSdkViewHelper.locationInWindowTop(view) - TuSdkViewHelper.locationInWindowTop((View)this) - (this.getHeight() - view.getHeight()) / 2);
        }
    }
    
    public int getSelectedPosition() {
        return this.h;
    }
    
    public void setSelectedPosition(final int n) {
        this.setSelectedPosition(n, true);
    }
    
    public void setSelectedPosition(final int n, final boolean b) {
        this.h = n;
        if (this.b == null) {
            return;
        }
        this.b.setSelectedPosition(n);
        if (b) {
            this.b.notifyDataSetChanged();
        }
    }
    
    public void changeSelectedPosition(final int n) {
        if (this.b == null || this.c == null) {
            return;
        }
        final int selectedPosition = this.b.getSelectedPosition();
        if (selectedPosition == n) {
            return;
        }
        this.h = n;
        this.b.setSelectedPosition(n);
        this.c.selectedPosition(selectedPosition, false);
        this.c.selectedPosition(n, true);
    }
    
    public boolean isReverseLayout() {
        return this.g;
    }
    
    public void setReverseLayout(final boolean g) {
        this.g = g;
        if (this.c != null) {
            this.c.setReverseLayout(this.g);
        }
    }
    
    private void a() {
        if (this.getLayoutManager() == null) {
            this.setLayoutManager((LayoutManager)this.getSdkLayoutManager());
        }
        if (this.getAdapter() == null) {
            this.setAdapter(this.getSdkAdapter());
        }
    }
    
    public void reloadData() {
        if (this.getAdapter() == null) {
            this.a();
        }
        else {
            this.getAdapter().notifyDataSetChanged();
        }
    }
    
    protected abstract void onViewCreated(final V p0, final ViewGroup p1, final int p2);
    
    protected abstract void onViewBinded(final V p0, final int p1);
    
    protected class TableViewAdapter extends TuSdkAdapter<T>
    {
        public TableViewAdapter() {
        }
        
        public TableViewAdapter(final int n, final List<T> list) {
            super(n, list);
        }
        
        public TableViewAdapter(final int n) {
            super(n);
        }
        
        @Override
        public TuSdkViewHolder<T> onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            final TuSdkViewHolder<T> onCreateViewHolder = super.onCreateViewHolder(viewGroup, n);
            if (onCreateViewHolder.itemView instanceof TuSdkCellViewInterface) {
                TuSdkTableView.this.onViewCreated((V) onCreateViewHolder.itemView, viewGroup, n);
            }
            return onCreateViewHolder;
        }
        
        @Override
        public void onBindViewHolder(final TuSdkViewHolder<T> tuSdkViewHolder, final int n) {
            super.onBindViewHolder(tuSdkViewHolder, n);
            if (tuSdkViewHolder.itemView instanceof TuSdkCellViewInterface) {
                TuSdkTableView.this.onViewBinded((V) tuSdkViewHolder.itemView, n);
            }
        }
    }
    
    public interface TuSdkTableViewItemClickDelegate<T, V extends View>
    {
        void onTableViewItemClick(final T p0, final V p1, final int p2);
    }
}
