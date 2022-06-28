// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components;

import com.example.myvideoeditorapp.kore.activity.TuFragment;
import com.example.myvideoeditorapp.kore.utils.ReflectUtils;


public abstract class TuSdkComponentOption
{
    private int a;
    private Class<?> b;
    
    public TuSdkComponentOption() {
        this.a = 0;
    }
    
    public Class<?> getComponentClazz() {
        if (this.b == null) {
            this.b = this.getDefaultComponentClazz();
        }
        return this.b;
    }
    
    protected abstract Class<?> getDefaultComponentClazz();
    
    public void setComponentClazz(final Class<?> b) {
        if (b != null && this.getComponentClazz() != null && this.getDefaultComponentClazz().isAssignableFrom(b)) {
            this.b = b;
        }
    }
    
    public void setRootViewLayoutId(final int a) {
        this.a = a;
    }
    
    public int getRootViewLayoutId() {
        if (this.a == 0) {
            this.a = this.getDefaultRootViewLayoutId();
        }
        return this.a;
    }
    
    protected abstract int getDefaultRootViewLayoutId();
    
    private void a(final TuFragment tuFragment) {
        if (tuFragment == null) {
            return;
        }
        tuFragment.setRootViewLayoutId(this.getRootViewLayoutId());
    }
    
    protected <T extends TuFragment> T fragmentInstance() {
        final TuFragment tuFragment = ReflectUtils.classInstance(this.getComponentClazz());
        this.a(tuFragment);
        return (T)tuFragment;
    }
}
