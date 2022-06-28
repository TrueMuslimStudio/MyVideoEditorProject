// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view;

import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.utils.anim.AnimHelper;
import android.widget.ProgressBar;

public class TuSdkProgressBar extends ProgressBar implements TuSdkViewInterface
{
    private boolean a;
    private AnimHelper.TuSdkViewAnimatorAdapter b;
    
    public TuSdkProgressBar(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.b = new AnimHelper.TuSdkViewAnimatorAdapter() {
            @Override
            public void onAnimationEnd(final View view, final boolean b) {
                if (b) {
                    return;
                }
                if (!TuSdkProgressBar.this.a) {
                    TuSdkProgressBar.this.setVisibility(8);
                }
            }
        };
        this.initView();
    }
    
    public TuSdkProgressBar(final Context context, final AttributeSet set) {
        super(context, set);
        this.b = new AnimHelper.TuSdkViewAnimatorAdapter() {
            @Override
            public void onAnimationEnd(final View view, final boolean b) {
                if (b) {
                    return;
                }
                if (!TuSdkProgressBar.this.a) {
                    TuSdkProgressBar.this.setVisibility(8);
                }
            }
        };
        this.initView();
    }
    
    public TuSdkProgressBar(final Context context) {
        super(context);
        this.b = new AnimHelper.TuSdkViewAnimatorAdapter() {
            @Override
            public void onAnimationEnd(final View view, final boolean b) {
                if (b) {
                    return;
                }
                if (!TuSdkProgressBar.this.a) {
                    TuSdkProgressBar.this.setVisibility(8);
                }
            }
        };
        this.initView();
    }
    
    protected void initView() {
    }
    
    public void loadView() {
        this.setMax(100);
    }
    
    public void viewDidLoad() {
    }
    
    public void viewNeedRest() {
    }
    
    public void viewWillDestory() {
    }
    
    public void showWithAnim(final boolean a) {
        this.a = a;
        float n = 1.0f;
        if (!a) {
            n = 0.0f;
        }
        this.setVisibility(0);
        ViewCompat.animate((View)this).alpha(n).setDuration(240L).setListener((ViewPropertyAnimatorListener)this.b);
    }
}
