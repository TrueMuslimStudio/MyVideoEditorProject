// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;

public class TuSdkActivity extends Activity
{
    public <T extends View> T getViewById(final int n) {
        return TuSdkViewHelper.loadView(this.findViewById(n));
    }
    
    public <T extends View> T getViewById(final String s) {
        final int idResId = TuSdkContext.getIDResId(s);
        if (idResId == 0) {
            return null;
        }
        return (T)this.getViewById(idResId);
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.initView();
    }
    
    protected void initView() {
    }
}
