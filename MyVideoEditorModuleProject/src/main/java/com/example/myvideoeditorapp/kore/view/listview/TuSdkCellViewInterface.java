// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.listview;

import com.example.myvideoeditorapp.kore.view.TuSdkViewInterface;

public interface TuSdkCellViewInterface<T> extends TuSdkViewInterface
{
    void setModel(final T p0);
    
    T getModel();
}
