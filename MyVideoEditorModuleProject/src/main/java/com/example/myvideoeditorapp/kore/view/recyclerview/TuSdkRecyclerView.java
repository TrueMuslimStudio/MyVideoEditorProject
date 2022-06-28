// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.recyclerview;

import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.view.TuSdkViewInterface;
import androidx.recyclerview.widget.RecyclerView;

public class TuSdkRecyclerView extends RecyclerView implements TuSdkViewInterface
{
    public TuSdkRecyclerView(final Context context) {
        super(context);
        this.initView();
    }
    
    public TuSdkRecyclerView(final Context context, final AttributeSet set) {
        super(context, set);
        this.initView();
    }
    
    public TuSdkRecyclerView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.initView();
    }
    
    protected void initView() {
    }
    
    public void loadView() {
    }
    
    public void viewDidLoad() {
    }
    
    public void viewNeedRest() {
    }
    
    public void viewWillDestory() {
    }
}
