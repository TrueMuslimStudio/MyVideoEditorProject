// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import androidx.fragment.app.Fragment;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkIntent;
import com.example.myvideoeditorapp.kore.type.ActivityAnimType;


public class TuFragmentActivity extends TuSdkFragmentActivity
{
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_activity_fragment_context_layout");
    }
    
    @Override
    protected void initActivity() {
        super.initActivity();
        this.setRootView(getLayoutId(), TuSdkContext.getIDResId("lsq_fragment_container"));
        this.setfragmentChangeAnim(ActivityObserver.ins.getAnimPush(), ActivityObserver.ins.getAnimPop());
    }
    
    @Override
    protected ActivityAnimType getAnimType(final String key) {
        if (key == null) {
            return null;
        }
        return ActivityObserver.ins.getActivityAnims().get(key);
    }
    
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
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
        this.presentModalNavigationActivity(fragment, ActivityObserver.ins.getAnimPush(), ActivityObserver.ins.getAnimPop(), b);
    }
    
    public void presentModalNavigationActivity(final Fragment fragment, final ActivityAnimType activityAnimType, final ActivityAnimType activityAnimType2, final boolean b) {
        this.presentModalNavigationActivity(ActivityObserver.ins.getMainActivityClazz(), fragment, activityAnimType, activityAnimType2, b);
    }
    
    public void filpModalNavigationActivity(final Fragment fragment, final boolean b, final boolean b2) {
        this.filpModalNavigationActivity(ActivityObserver.ins.getMainActivityClazz(), fragment, b, b2);
    }
}
