// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.widget.smudge;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.utils.TLog;

public class FilterSmudgeView extends SmudgeView
{
    public FilterSmudgeView(final Context context) {
        super(context);
    }
    
    public FilterSmudgeView(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public FilterSmudgeView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    @Override
    protected SimpleProcessor getProcessorInstance() {
        if (!SdkValid.shared.wipeFilterEnabled()) {
            return null;
        }
        if (this.mSmudgeProcessor == null) {
            this.mSmudgeProcessor = new FilterSmudgeProcessor();
        }
        return this.mSmudgeProcessor;
    }
    
    @Override
    public void setImageBitmap(final Bitmap imageBitmap) {
        if (!SdkValid.shared.wipeFilterEnabled()) {
            TLog.e("You are not allowed to use the wipe-filter feature, please see http://tusdk.com", new Object[0]);
            return;
        }
        super.setImageBitmap(imageBitmap);
    }
    
    public FilterWrap getFilterWrap() {
        if (this.getProcessorInstance() != null) {
            return ((FilterSmudgeProcessor)this.getProcessorInstance()).getFilterWrap();
        }
        return null;
    }
    
    public final void setFilterWrap(final FilterWrap filterWrap) {
        if (this.getProcessorInstance() != null) {
            ((FilterSmudgeProcessor)this.getProcessorInstance()).setFilterWrap(filterWrap);
        }
    }
    
    @Override
    protected void updateBrushSettings() {
        if (this.getProcessorInstance() == null) {
            return;
        }
        this.getProcessorInstance().setBrushSize(this.getBrushSize());
    }
}
