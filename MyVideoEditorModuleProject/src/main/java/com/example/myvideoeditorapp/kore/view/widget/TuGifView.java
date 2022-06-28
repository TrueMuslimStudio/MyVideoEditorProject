// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.view.widget;

import android.graphics.Canvas;

import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import android.content.res.Resources;
import java.io.IOException;
import android.net.Uri;
import android.util.AttributeSet;
import android.content.Context;
import com.example.myvideoeditorapp.kore.utils.image.TuSdkGifDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class TuGifView extends androidx.appcompat.widget.AppCompatImageView implements Drawable.Callback, TuSdkGifDrawable.TuGifAnimationListener
{
    private TuSdkGifDrawable a;
    private boolean b;
    private TuGifViewDelegate c;
    
    public TuGifView(final Context context) {
        super(context);
        this.b = true;
    }
    
    public TuGifView(final Context context, final AttributeSet set) {
        super(context, set);
        this.b = true;
        this.a(set);
    }
    
    public TuGifView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.b = true;
        this.a(set);
    }
    
    private void a(final AttributeSet set) {
        if (set == null && this.isInEditMode()) {
            return;
        }
        final int attributeResourceValue = set.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        if (attributeResourceValue > 0) {
            final String resourceTypeName = this.getResources().getResourceTypeName(attributeResourceValue);
            if ("drawable".equals(resourceTypeName) || "raw".equals(resourceTypeName)) {
                this.setImageResource(attributeResourceValue);
            }
        }
    }
    
    public void setImageURI(final Uri imageURI) {
        if (this.a(imageURI)) {
            super.setImageURI(imageURI);
        }
    }
    
    private boolean a(final Uri uri) {
        if (uri != null) {
            try {
                this.a();
                this.a(this.a = new TuSdkGifDrawable(this.getContext().getContentResolver(), uri));
                return true;
            }
            catch (IOException ex) {}
        }
        return false;
    }
    
    public void setImageResource(final int imageResource) {
        if (!this.a(imageResource)) {
            super.setImageResource(imageResource);
        }
        if (this.a != null && this.isAutoPlay()) {
            this.start();
        }
    }
    
    private boolean a(final int n) {
        final Resources resources = this.getResources();
        if (resources != null) {
            try {
                this.a();
                this.a(this.a = new TuSdkGifDrawable(resources, n));
                return true;
            }
            catch (IOException ex) {}
        }
        return false;
    }
    
    private void a() {
        if (this.a != null && !this.a.isRecycled()) {
            this.a.setCallback((Drawable.Callback)null);
            this.a.removeAnimationListener(this);
            this.a.recycle();
            this.a = null;
        }
    }
    
    private void a(final TuSdkGifDrawable imageDrawable) {
        this.setImageDrawable((Drawable)imageDrawable);
        imageDrawable.addAnimationListener(this);
        StatisticsManger.appendComponent(ComponentActType.gifViewer);
    }
    
    protected void onDraw(final Canvas canvas) {
        if (this.a != null) {
            this.a.draw(canvas);
        }
    }
    
    public void invalidateDrawable(final Drawable drawable) {
        this.invalidate();
    }
    
    public void scheduleDrawable(final Drawable drawable, final Runnable runnable, final long n) {
        this.postDelayed(runnable, n);
    }
    
    public void unscheduleDrawable(final Drawable drawable, final Runnable runnable) {
        this.removeCallbacks(runnable);
    }
    
    public boolean isAutoPlay() {
        return this.b;
    }
    
    public void setAutoPlay(final boolean b) {
        this.b = b;
    }
    
    public boolean isRunning() {
        return this.a != null && this.a.isRunning();
    }
    
    public void start() {
        if (this.a != null) {
            if (this.a.getCallback() == null) {
                this.a.setCallback((Drawable.Callback)this);
            }
            this.a.start();
        }
    }
    
    public void pause() {
        if (this.a != null) {
            this.a.pause();
        }
    }
    
    public void onGifAnimationCompleted(final int n) {
        if (this.getDelegate() != null) {
            this.getDelegate().onGifAnimationComplete(n);
        }
    }
    
    public TuGifViewDelegate getDelegate() {
        return this.c;
    }
    
    public void setDelegate(final TuGifViewDelegate c) {
        this.c = c;
    }
    
    public void reset() {
        if (this.a != null) {
            this.a.reset();
        }
    }
    
    public void dispose() {
        this.a();
    }
    
    public interface TuGifViewDelegate
    {
        void onGifAnimationComplete(final int p0);
    }
}
