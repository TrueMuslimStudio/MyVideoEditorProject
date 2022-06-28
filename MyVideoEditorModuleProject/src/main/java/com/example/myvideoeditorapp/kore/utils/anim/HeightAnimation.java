// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.anim;

import android.view.animation.Transformation;
import com.example.myvideoeditorapp.kore.struct.ViewSize;
import android.view.View;
import android.view.animation.Animation;

public class HeightAnimation extends Animation
{
    private float a;
    private float b;
    private float c;
    private View d;
    
    public HeightAnimation(final View view, final float n) {
        this(view, -1.0f, n);
    }
    
    public HeightAnimation(final View d, final float a, final float b) {
        this.d = d;
        this.a = a;
        this.b = b;
        if (this.a == -1.0f) {
            this.a = (float)ViewSize.create(d).height;
        }
        this.c = this.b - this.a;
    }
    
    public boolean willChangeBounds() {
        return true;
    }
    
    protected void applyTransformation(final float n, final Transformation transformation) {
        this.d.getLayoutParams().height = (int)(this.a + this.c * n);
        this.d.requestLayout();
    }
    
    protected void finalize() throws Throwable {
        this.d = null;
        super.finalize();
    }
}
