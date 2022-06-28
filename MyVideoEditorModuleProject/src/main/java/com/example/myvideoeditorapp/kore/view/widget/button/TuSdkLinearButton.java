// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.button;

import android.annotation.SuppressLint;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.listener.TuSdkTouchColorChangeListener;
import com.example.myvideoeditorapp.kore.view.TuSdkLinearLayout;

public class TuSdkLinearButton extends TuSdkLinearLayout
{
    protected TuSdkTouchColorChangeListener colorChangeListener;
    public int index;
    public int typeTag;
    public long idTag;
    
    public TuSdkLinearButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuSdkLinearButton(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuSdkLinearButton(final Context context) {
        super(context);
    }
    
    protected void bindColorChangeListener() {
        if (this.colorChangeListener != null) {
            return;
        }
        this.colorChangeListener = TuSdkTouchColorChangeListener.bindTouchDark((View)this);
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    protected void removeColorChangeListener() {
        this.colorChangeListener = null;
        this.setOnTouchListener((OnTouchListener)null);
    }
    
    public void setOnClickListener(final OnClickListener onClickListener) {
        if (onClickListener != null) {
            this.bindColorChangeListener();
        }
        else {
            this.removeColorChangeListener();
        }
        super.setOnClickListener(onClickListener);
    }
    
    public void setEnabled(final boolean enabled) {
        if (this.colorChangeListener != null && this.isEnabled() != enabled) {
            this.colorChangeListener.enabledChanged((View)this, enabled);
        }
        super.setEnabled(enabled);
    }
    
    public void setSelected(final boolean selected) {
        if (this.colorChangeListener != null && this.isSelected() != selected) {
            this.colorChangeListener.selectedChanged((View)this, selected);
        }
        super.setSelected(selected);
    }
}
