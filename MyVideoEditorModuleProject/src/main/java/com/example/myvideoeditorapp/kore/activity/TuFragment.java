// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkIntent;
import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.type.ActivityAnimType;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.TuNavigatorBar;
import com.example.myvideoeditorapp.kore.view.widget.button.TuNavigatorButton;
import com.example.myvideoeditorapp.kore.view.widget.button.TuSdkNavigatorButton;

public abstract class TuFragment extends TuSdkFragment
{
    @Override
    protected void initCreateView() {
        this.setNavigatorBarId(TuSdkContext.getIDResId("lsq_navigatorBar"), TuSdkContext.getIDResId("lsq_backButton"), TuNavigatorButton.getLayoutId());
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        if (!SdkValid.shared.sdkValid()) {
            return null;
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
    }
    
    public TuSdkNavigatorButton setNavLeftButton(final String s) {
        return this.setNavLeftButton(s, TuNavigatorBar.TuNavButtonStyle.button);
    }
    
    public TuSdkNavigatorButton setNavLeftButton(final String s, final int textColor) {
        final TuSdkNavigatorButton setNavLeftButton = this.setNavLeftButton(s, TuNavigatorBar.TuNavButtonStyle.button);
        setNavLeftButton.setTextColor(textColor);
        return setNavLeftButton;
    }
    
    public TuSdkNavigatorButton setNavLeftButton(final int n) {
        return this.setNavLeftButton(this.getResString(n));
    }
    
    public TuSdkNavigatorButton setNavLeftHighLightButton(final String s) {
        return this.setNavLeftButton(s, TuNavigatorBar.TuNavButtonStyle.highlight);
    }
    
    public TuSdkNavigatorButton setNavLeftHighLightButton(final int n) {
        return this.setNavLeftHighLightButton(this.getResString(n));
    }
    
    public TuSdkNavigatorButton setNavRightButton(final String s) {
        return this.setNavRightButton(s, TuNavigatorBar.TuNavButtonStyle.button);
    }
    
    public TuSdkNavigatorButton setNavRightButton(final String s, final int textColor) {
        final TuSdkNavigatorButton setNavRightButton = this.setNavRightButton(s, TuNavigatorBar.TuNavButtonStyle.button);
        setNavRightButton.setTextColor(textColor);
        return setNavRightButton;
    }
    
    public TuSdkNavigatorButton setNavRightButton(final int n) {
        return this.setNavRightButton(this.getResString(n));
    }
    
    public TuSdkNavigatorButton setNavRightHighLightButton(final String s) {
        return this.setNavRightButton(s, TuNavigatorBar.TuNavButtonStyle.highlight);
    }
    
    public TuSdkNavigatorButton setNavRightHighLightButton(final int n) {
        return this.setNavRightHighLightButton(this.getResString(n));
    }
    
    public void presentActivity(final TuSdkIntent tuSdkIntent, final boolean b) {
        this.presentActivity(tuSdkIntent, ActivityObserver.ins.getAnimPresent(), b);
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
        this.presentModalNavigationActivity(fragment, ActivityObserver.ins.getAnimPresent(), ActivityObserver.ins.getAnimDismiss(), b);
    }
    
    public void presentModalNavigationActivity(final Fragment fragment, final ActivityAnimType activityAnimType, final ActivityAnimType activityAnimType2, final boolean b) {
        this.presentModalNavigationActivity(ActivityObserver.ins.getMainActivityClazz(), fragment, activityAnimType, activityAnimType2, b);
    }
    
    public void filpModalNavigationActivity(final Fragment fragment, final boolean b, final boolean b2) {
        this.filpModalNavigationActivity(ActivityObserver.ins.getMainActivityClazz(), fragment, b, b2);
    }
    
    public void filpModalNavigationActivity(final Fragment fragment, final boolean b) {
        this.filpModalNavigationActivity(fragment, false, b);
    }
    
    public void alert(final TuSdkViewHelper.AlertDelegate alertDelegate, final int n, final int n2) {
        this.alert(alertDelegate, this.getResString(n), this.getResString(n2));
    }
    
    public void alert(final TuSdkViewHelper.AlertDelegate alertDelegate, final String s, final String s2) {
        TuSdkViewHelper.alert(alertDelegate, (Context)this.getActivity(), s, s2, TuSdkContext.getString("lsq_nav_cancel"), TuSdkContext.getString("lsq_button_done"));
    }
    
    public void hubStatus(final String s) {
        TuSdk.messageHub().setStatus((Context)this.getActivity(), s);
    }
    
    public void hubStatus(final int n) {
        TuSdk.messageHub().setStatus((Context)this.getActivity(), n);
    }
    
    public void hubSuccess(final String s) {
        TuSdk.messageHub().showSuccess((Context)this.getActivity(), s);
    }
    
    public void hubSuccess(final int n) {
        TuSdk.messageHub().showSuccess((Context)this.getActivity(), n);
    }
    
    public void hubError(final String s) {
        TuSdk.messageHub().showError((Context)this.getActivity(), s);
    }
    
    public void hubError(final int n) {
        TuSdk.messageHub().showError((Context)this.getActivity(), n);
    }
    
    public void hubDismiss() {
        TuSdk.messageHub().dismiss();
    }
    
    public void hubDismissRightNow() {
        TuSdk.messageHub().dismissRightNow();
    }
}
