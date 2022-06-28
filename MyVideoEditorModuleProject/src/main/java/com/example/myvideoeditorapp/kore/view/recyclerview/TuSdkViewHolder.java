// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.recyclerview;

import com.example.myvideoeditorapp.kore.view.listview.TuSdkListSelectableCellViewInterface;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellViewInterface;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import android.view.ViewGroup;
import android.view.View;
import com.example.myvideoeditorapp.kore.view.TuSdkViewInterface;
import androidx.recyclerview.widget.RecyclerView;

public class TuSdkViewHolder<T> extends RecyclerView.ViewHolder implements TuSdkViewInterface
{
    private View.OnClickListener a;
    private TuSdkViewHolderItemClickListener<T> b;
    
    public static <T> TuSdkViewHolder<T> create(final ViewGroup viewGroup, final int n) {
        return create(TuSdkViewHelper.buildView(viewGroup.getContext(), n, viewGroup));
    }
    
    public static <T> TuSdkViewHolder<T> create(final View view) {
        return new TuSdkViewHolder<T>(view);
    }
    
    public TuSdkViewHolderItemClickListener<T> getItemClickListener() {
        return this.b;
    }
    
    public void setItemClickListener(final TuSdkViewHolderItemClickListener<T> b) {
        this.b = b;
        if (this.b != null) {
            this.itemView.setOnClickListener(this.a);
        }
        else {
            this.itemView.setOnClickListener((View.OnClickListener)null);
        }
    }
    
    public TuSdkViewHolder(final View view) {
        super(view);
        this.a = (View.OnClickListener)new ViewHolderClickListener();
        this.viewNeedRest();
    }
    
    public void setModel(final T model, final int i) {
        this.viewNeedRest();
        this.itemView.setTag((Object)i);
        if (this.itemView instanceof TuSdkCellViewInterface) {
            ((TuSdkCellViewInterface)this.itemView).setModel(model);
        }
    }
    
    public T getModel() {
        if (this.itemView instanceof TuSdkCellViewInterface) {
            return (T) ((TuSdkCellViewInterface)this.itemView).getModel();
        }
        return null;
    }
    
    public void loadView() {
        if (this.itemView instanceof TuSdkViewInterface) {
            ((TuSdkViewInterface)this.itemView).loadView();
        }
    }
    
    public void viewDidLoad() {
        if (this.itemView instanceof TuSdkViewInterface) {
            ((TuSdkViewInterface)this.itemView).viewDidLoad();
        }
    }
    
    public void viewNeedRest() {
        if (this.itemView instanceof TuSdkViewInterface) {
            ((TuSdkViewInterface)this.itemView).viewNeedRest();
        }
    }
    
    public void viewWillDestory() {
        if (this.itemView instanceof TuSdkViewInterface) {
            ((TuSdkViewInterface)this.itemView).viewWillDestory();
        }
    }
    
    protected void onViewHolderItemClick(final View view) {
        if (this.b == null) {
            return;
        }
        this.b.onViewHolderItemClick(this);
    }
    
    public void setSelectedPosition(final int n) {
        if (n < 0) {
            return;
        }
        if (this.itemView instanceof TuSdkListSelectableCellViewInterface) {
            if (n == this.getPosition()) {
                ((TuSdkListSelectableCellViewInterface)this.itemView).onCellSelected(n);
            }
            else {
                ((TuSdkListSelectableCellViewInterface)this.itemView).onCellDeselected();
            }
        }
    }
    
    protected class ViewHolderClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            TuSdkViewHolder.this.onViewHolderItemClick(view);
        }
    }
    
    public interface TuSdkViewHolderItemClickListener<T>
    {
        void onViewHolderItemClick(final TuSdkViewHolder<T> p0);
    }
}
