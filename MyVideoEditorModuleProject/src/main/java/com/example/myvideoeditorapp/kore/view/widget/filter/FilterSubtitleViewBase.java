// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget.filter;

import androidx.core.view.ViewPropertyAnimatorListener;
import com.example.myvideoeditorapp.kore.utils.anim.AnimHelper;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.view.TuSdkLinearLayout;

public abstract class FilterSubtitleViewBase extends TuSdkLinearLayout implements FilterSubtitleViewInterface
{
    private Runnable a;
    
    public FilterSubtitleViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = new Runnable() {
            @Override
            public void run() {
                FilterSubtitleViewBase.this.a();
            }
        };
    }
    
    public FilterSubtitleViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = new Runnable() {
            @Override
            public void run() {
                FilterSubtitleViewBase.this.a();
            }
        };
    }
    
    public FilterSubtitleViewBase(final Context context) {
        super(context);
        this.a = new Runnable() {
            @Override
            public void run() {
                FilterSubtitleViewBase.this.a();
            }
        };
    }
    
    protected String getGroupName(final FilterOption filterOption) {
        if (filterOption == null) {
            return null;
        }
        return TuSdkContext.getString(FilterLocalPackage.shared().getGroupNameKey(filterOption.groupId));
    }
    
    protected void startScaleAnimation() {
        ThreadHelper.cancel(this.a);
        ViewCompat.setAlpha((View)this, 0.0f);
        ViewCompat.setScaleX((View)this, 2.0f);
        ViewCompat.setScaleY((View)this, 2.0f);
        this.showViewIn(true);
        ViewCompat.animate((View)this).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(220L).setListener((ViewPropertyAnimatorListener)new AnimHelper.TuSdkViewAnimatorAdapter() {
            @Override
            public void onAnimationEnd(final View view, final boolean b) {
                if (b) {
                    return;
                }
                ThreadHelper.postDelayed(FilterSubtitleViewBase.this.a, 500L);
            }
        });
    }
    
    private void a() {
        ViewCompat.animate((View)this).alpha(0.0f).setDuration(200L).setListener((ViewPropertyAnimatorListener)new AnimHelper.TuSdkViewAnimatorAdapter() {
            @Override
            public void onAnimationEnd(final View view, final boolean b) {
                if (b) {
                    return;
                }
                view.setVisibility(4);
            }
        });
    }
}
