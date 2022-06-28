package com.example.myvideoeditorapp.imagelaoder.core.display;

import android.graphics.Bitmap;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;

import com.example.myvideoeditorapp.imagelaoder.core.deque.LoadedFrom;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageAware;
import com.example.myvideoeditorapp.imagelaoder.core.imageaware.ImageViewAware;

public class RoundedVignetteBitmapDisplayer extends RoundedBitmapDisplayer {
    public RoundedVignetteBitmapDisplayer(int cornerRadiusPixels, int marginPixels) {
        super(cornerRadiusPixels, marginPixels);
    }

    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        } else {
            imageAware.setImageDrawable(new RoundedVignetteDrawable(bitmap, this.cornerRadius, this.margin));
        }
    }

    protected static class RoundedVignetteDrawable extends RoundedDrawable {
        RoundedVignetteDrawable(Bitmap bitmap, int cornerRadius, int margin) {
            super(bitmap, cornerRadius, margin);
        }

        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            RadialGradient vignette = new RadialGradient(this.mRect.centerX(), this.mRect.centerY() * 1.0F / 0.7F, this.mRect.centerX() * 1.3F, new int[]{0, 0, 2130706432}, new float[]{0.0F, 0.7F, 1.0F}, Shader.TileMode.CLAMP);
            Matrix oval = new Matrix();
            oval.setScale(1.0F, 0.7F);
            vignette.setLocalMatrix(oval);
            this.paint.setShader(new ComposeShader(this.bitmapShader, vignette, PorterDuff.Mode.SRC_OVER));
        }
    }
}

