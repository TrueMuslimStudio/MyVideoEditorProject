// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.content.Context;
import com.example.myvideoeditorapp.kore.view.widget.TuSdkActionSheet;

public class TuActionSheet extends TuSdkActionSheet
{
    public TuActionSheet(final Context context) {
        super(context);
    }
    
    @Override
    protected int getActionSheetLayoutId() {
        return TuActionSheetView.getLayoutId();
    }
    
    @Override
    protected int getActionsheetButtonStyleResId() {
        return TuSdkContext.getStyleResId("lsq_actionsheetButton");
    }
    
    @Override
    protected int getButtonBackgroundResId(final int n, final int n2) {
        if (n2 == 1 && this.getTitle() == null) {
            return TuSdkContext.getDrawableResId("tusdk_view_widget_actionsheet_radius");
        }
        if (n == 0 && this.getTitle() == null) {
            return TuSdkContext.getDrawableResId("tusdk_view_widget_actionsheet_top_radius");
        }
        if (n == n2 - 1) {
            return TuSdkContext.getDrawableResId("tusdk_view_widget_actionsheet_bottom_radius");
        }
        return TuSdkContext.getDrawableResId("tusdk_view_widget_actionsheet_normal");
    }
    
    @Override
    protected int getButtonColor(final int n) {
        String s = "lsq_actionsheet_text_color";
        if (this.getDestructiveIndex() == n) {
            s = "lsq_actionsheet_text_stress";
        }
        return TuSdkContext.getColor(s);
    }
    
    @Override
    protected int getActionsheetBottomSpace(final boolean b) {
        String s = "lsq_actionsheet_space_button";
        if (b) {
            s = "lsq_actionsheet_space_bottom";
        }
        return TuSdkContext.getDimenOffset(s);
    }
}
