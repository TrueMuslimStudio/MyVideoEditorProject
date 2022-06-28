// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkIntent;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.ActivityObserver;
import com.example.myvideoeditorapp.kore.activity.TuFragment;
import com.example.myvideoeditorapp.kore.type.ActivityAnimType;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;

public abstract class TuSdkComponent implements TuSdkComponentErrorListener
{
    private Activity a;
    private TuSdkComponentDelegate b;
    private boolean c;
    
    public Activity activity() {
        return this.a;
    }
    
    public TuSdkComponentDelegate getDelegate() {
        return this.b;
    }
    
    public void setDelegate(final TuSdkComponentDelegate b) {
        this.b = b;
    }
    
    public boolean isAutoDismissWhenCompleted() {
        return this.c;
    }
    
    public TuSdkComponent setAutoDismissWhenCompleted(final boolean c) {
        this.c = c;
        return this;
    }
    
    public TuSdkComponent(final Activity a) {
        this.a = a;
        this.initComponent();
    }
    
    protected abstract void initComponent();
    
    public abstract TuSdkComponent showComponent();
    
    protected void notifyResult(final TuSdkResult tuSdkResult, final Error error, final TuFragment tuFragment) {
        if (this.isAutoDismissWhenCompleted() && tuFragment != null) {
            tuFragment.dismissActivityWithAnim();
        }
        if (this.getDelegate() == null) {
            return;
        }
        this.getDelegate().onComponentFinished(tuSdkResult, error, tuFragment);
    }
    
    @Override
    public void onComponentError(final TuFragment tuFragment, final TuSdkResult tuSdkResult, final Error error) {
        this.notifyResult(tuSdkResult, error, tuFragment);
    }
    
    public boolean showAlertIfCannotSaveFile() {
        if (this.a == null) {
            return true;
        }
        String s = null;
        ComponentErrorType componentErrorType = null;
        if (!FileHelper.mountedExternalStorage()) {
            s = this.getResString("lsq_save_not_found_sdcard");
            componentErrorType = ComponentErrorType.TypeNotFoundSDCard;
        }
        else if (!FileHelper.hasAvailableExternal((Context)this.a)) {
            s = this.getResString("lsq_save_insufficient_storage_space");
            componentErrorType = ComponentErrorType.TypeStorageSpace;
        }
        if (componentErrorType == null) {
            return false;
        }
        TuSdkViewHelper.alert((Context)this.a, this.getResString("lsq_save_unsupport_storage_title"), s, this.getResString("lsq_button_done"));
        this.onComponentError(null, null, componentErrorType.getError(this));
        return true;
    }
    
    public void presentActivity(final TuSdkIntent tuSdkIntent, final boolean b) {
        com.example.myvideoeditorapp.tv.core.activity.ActivityHelper.presentActivity(this.a, tuSdkIntent, ActivityObserver.ins.getAnimPresent(), b);
    }
    
    public void presentModalNavigationActivity(final Fragment fragment) {
        this.presentModalNavigationActivity(fragment, false);
    }
    
    public void presentModalNavigationActivity(final Fragment fragment, final boolean b) {
        this.presentModalNavigationActivity(fragment, ActivityObserver.ins.getAnimPresent(), ActivityObserver.ins.getAnimDismiss(), b);
    }
    
    public void pushModalNavigationActivity(final Fragment fragment) {
        this.pushModalNavigationActivity(fragment, false);
    }
    
    public void pushModalNavigationActivity(final Fragment fragment, final boolean b) {
        this.presentModalNavigationActivity(fragment, ActivityObserver.ins.getAnimPush(), ActivityObserver.ins.getAnimPop(), b);
    }
    
    public void presentModalNavigationActivity(final Fragment fragment, final ActivityAnimType activityAnimType, final ActivityAnimType activityAnimType2, final boolean b) {
        com.example.myvideoeditorapp.tv.core.activity.ActivityHelper.presentActivity(this.a, ActivityObserver.ins.getMainActivityClazz(), fragment, activityAnimType, activityAnimType2, b, false, false);
    }
    
    public void alert(final TuSdkViewHelper.AlertDelegate alertDelegate, final int n, final int n2) {
        this.alert(alertDelegate, this.getResString(n), this.getResString(n2));
    }
    
    public void alert(final TuSdkViewHelper.AlertDelegate alertDelegate, final String s, final String s2) {
        TuSdkViewHelper.alert(alertDelegate, (Context)this.a, s, s2, this.getResString("lsq_nav_cancel"), this.getResString("lsq_button_done"));
    }
    
    public String getResString(final int n) {
        return ContextUtils.getResString((Context)this.a, n);
    }
    
    public String getResString(final String s) {
        return TuSdkContext.getString(s);
    }
    
    public interface TuSdkComponentDelegate
    {
        void onComponentFinished(final TuSdkResult p0, final Error p1, final TuFragment p2);
    }
}
