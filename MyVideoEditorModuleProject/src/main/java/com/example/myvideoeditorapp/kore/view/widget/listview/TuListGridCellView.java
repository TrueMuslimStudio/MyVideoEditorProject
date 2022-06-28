// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.listview;

import java.util.Iterator;
import com.example.myvideoeditorapp.kore.view.TuSdkViewInterface;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import android.util.AttributeSet;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellViewInterface;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellLinearLayout;

public class TuListGridCellView<T, V extends View & TuSdkCellViewInterface<T>> extends TuSdkCellLinearLayout<List<T>>
{
    private ArrayList<V> a;
    private TuListGridCellViewDelegate<T, V> b;
    private OnClickListener c;

    public TuListGridCellView(final Context context) {
        super(context);
        this.a = new ArrayList<V>();
        this.c = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (TuListGridCellView.this.b != null && view instanceof TuSdkCellViewInterface) {
                    TuListGridCellView.this.b.onGridItemClick(view, ((TuSdkCellViewInterface)view).getModel());
                }
            }
        };
    }

    public TuListGridCellView(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = new ArrayList<V>();
        this.c = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (TuListGridCellView.this.b != null && view instanceof TuSdkCellViewInterface) {
                    TuListGridCellView.this.b.onGridItemClick(view, ((TuSdkCellViewInterface)view).getModel());
                }
            }
        };
    }

    public TuListGridCellView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = new ArrayList<V>();
        this.c = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (TuListGridCellView.this.b != null && view instanceof TuSdkCellViewInterface) {
                    TuListGridCellView.this.b.onGridItemClick(view, ((TuSdkCellViewInterface)view).getModel());
                }
            }
        };
    }

    public TuListGridCellViewDelegate<T, V> getGridDelegate() {
        return this.b;
    }

    public void setGridDelegate(final TuListGridCellViewDelegate<T, V> b) {
        this.b = b;
    }

    @Override
    protected void onLayouted() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            if (child instanceof TuSdkCellViewInterface) {
                this.a((V)child);
            }
        }
        this.setHeight(ContextUtils.getScreenSize(this.getContext()).width / this.a.size());
        super.onLayouted();
    }

    private void a(final V e) {
        ((TuSdkViewInterface)e).loadView();
        ((View)e).setOnClickListener(this.c);
        ((View)e).setVisibility(View.INVISIBLE);
        this.a.add(e);
    }

    @Override
    protected void bindModel() {
        List list = (List)this.getModel();
        if (list == null) {
            return;
        }
        int i = 0;
        final int size = this.a.size();
        final int size2 = list.size();
        while (i < size) {
            final View view = (View)this.a.get(i);
            if (i < size2) {
                ((TuSdkCellViewInterface<Object>)view).setModel(list.get(i));
                this.showViewIn(view, true);
            }
            ++i;
        }
    }

    @Override
    public void viewNeedRest() {
        super.viewNeedRest();
        for (final View view : this.a) {
            ((TuSdkViewInterface)view).viewNeedRest();
            this.showViewIn(view, false);
        }
    }

    @Override
    public void viewWillDestory() {
        this.viewNeedRest();
        super.viewWillDestory();
    }

    public interface TuListGridCellViewDelegate<T, V extends View & TuSdkCellViewInterface<T>> {
        void onGridItemClick(View var1, Object var2);
    }
}
