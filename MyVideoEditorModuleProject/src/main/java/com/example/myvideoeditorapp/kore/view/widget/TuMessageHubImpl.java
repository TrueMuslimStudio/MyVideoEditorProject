// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget;

import com.example.myvideoeditorapp.kore.view.widget.TuSdkProgressHub;
import android.content.Context;

public class TuMessageHubImpl implements TuMessageHubInterface
{
    @Override
    public void setStatus(final Context context, final String s) {
        try {
            TuProgressHub.setStatus(context, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void setStatus(final Context context, final int n) {
        try {
            TuProgressHub.setStatus(context, n);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showToast(final Context context, final String s) {
        try {
            TuProgressHub.showToast(context, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showToast(final Context context, final int n) {
        try {
            TuProgressHub.showToast(context, n);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showSuccess(final Context context, final String s) {
        try {
            TuProgressHub.showSuccess(context, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showSuccess(final Context context, final int n) {
        try {
            TuProgressHub.showSuccess(context, n);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showError(final Context context, final String s) {
        try {
            TuProgressHub.showError(context, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void showError(final Context context, final int n) {
        try {
            TuProgressHub.showError(context, n);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void dismiss() {
        try {
            TuProgressHub.dismiss();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void dismissRightNow() {
        try {
            TuProgressHub.dismissRightNow();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void applyToViewWithNavigationBarHidden(final boolean b) {
        TuSdkProgressHub.applyToViewWithNavigationBarHidden(b);
    }
}
