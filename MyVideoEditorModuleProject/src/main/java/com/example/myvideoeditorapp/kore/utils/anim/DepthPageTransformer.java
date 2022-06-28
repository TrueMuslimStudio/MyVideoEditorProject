// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.anim;

import androidx.core.view.ViewCompat;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer
{
    public void transformPage(final View view, final float a) {
        final int width = view.getWidth();
        if (a < -1.0f) {
            ViewCompat.setAlpha(view, 0.0f);
        }
        else if (a <= 0.0f) {
            ViewCompat.setAlpha(view, 1.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setScaleX(view, 1.0f);
            ViewCompat.setScaleY(view, 1.0f);
        }
        else if (a <= 1.0f) {
            ViewCompat.setAlpha(view, 1.0f - a);
            ViewCompat.setTranslationX(view, width * -a);
            final float n = 0.75f + 0.25f * (1.0f - Math.abs(a));
            ViewCompat.setScaleX(view, n);
            ViewCompat.setScaleY(view, n);
        }
        else {
            ViewCompat.setAlpha(view, 0.0f);
        }
    }
}
