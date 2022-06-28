// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.widget.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker.DynamicStickerPlayController;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.TuSdkImageView;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.button.TuSdkImageButton;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;


public class StickerDynamicItemView extends StickerItemViewBase
{
    private TuSdkImageView a;
    private TuSdkImageButton b;
    private TuSdkImageButton c;
    private DynamicStickerPlayController d;
    private StickerDynamicData e;
    private boolean f;
    private int g;
    private int h;
    private OnClickListener i;
    
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_impl_component_widget_sticker_dynamic_image_item_view");
    }
    
    public StickerDynamicItemView(final Context context) {
        super(context);
        this.f = true;
        this.i = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerDynamicItemView.this.equalViewIds(view, (View)StickerDynamicItemView.this.getCancelButton())) {
                    StickerDynamicItemView.this.a();
                }
            }
        };
    }
    
    public StickerDynamicItemView(final Context context, final AttributeSet set) {
        super(context, set);
        this.f = true;
        this.i = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerDynamicItemView.this.equalViewIds(view, (View)StickerDynamicItemView.this.getCancelButton())) {
                    StickerDynamicItemView.this.a();
                }
            }
        };
    }
    
    public StickerDynamicItemView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.f = true;
        this.i = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerDynamicItemView.this.equalViewIds(view, (View)StickerDynamicItemView.this.getCancelButton())) {
                    StickerDynamicItemView.this.a();
                }
            }
        };
    }
    
    public final TuSdkImageView getRenderView() {
        if (this.a == null) {
            this.a = this.getViewById("lsq_sticker_imageView");
        }
        return this.a;
    }
    
    public final TuSdkImageButton getCancelButton() {
        if (this.b == null) {
            this.b = this.getViewById("lsq_sticker_cancelButton");
            if (this.b != null) {
                this.b.setOnClickListener(this.i);
            }
        }
        return this.b;
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    public final TuSdkImageButton getTurnButton() {
        if (this.c == null) {
            this.c = this.getViewById("lsq_sticker_turnButton");
            if (this.c != null) {
                this.c.setOnTouchListener(this.mOnTouchListener);
            }
        }
        return this.c;
    }
    
    @Override
    protected void onLayouted() {
        super.onLayouted();
        if (this.getRenderView() == null) {
            return;
        }
        this.mCMargin.x = this.getWidth() - this.getRenderView().getWidth();
        this.mCMargin.y = this.getHeight() - this.getRenderView().getHeight();
        final Rect locationInWindow = TuSdkViewHelper.locationInWindow((View)this);
        final Rect locationInWindow2 = TuSdkViewHelper.locationInWindow((View)this.getRenderView());
        this.mCOffset.x = locationInWindow2.left - locationInWindow.left;
        this.mCOffset.y = locationInWindow2.top - locationInWindow.top;
        this.initStickerPostion();
    }
    
    private void a() {
        if (this.mDelegate == null) {
            return;
        }
        this.mDelegate.onStickerItemViewClose(this);
        if (this.mSticker != null) {
            this.mSticker.setImage(null);
        }
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        return (this.mStickerType == StickerView.StickerType.Normal || this.mStickerType == this.mType) && super.onTouchEvent(motionEvent);
    }
    
    @Override
    public void loadView() {
        this.getRenderView();
        this.getCancelButton();
        this.getTurnButton();
        (this.d = new DynamicStickerPlayController()).setStickerPlayerListener(new DynamicStickerPlayController.OnStickerPlayerListener() {
            @Override
            public void onStickerLoaded() {
            }
            
            @Override
            public void OnStickerUpdate(final TuSdkSize tuSdkSize, final Bitmap imageBitmap) {
                if (imageBitmap == null) {
                    return;
                }
                if (StickerDynamicItemView.this.mCSize == null) {
                    StickerDynamicItemView.this.mCSize = TuSdkSize.create(imageBitmap);
                    final TuSdkSize scaledSize = StickerDynamicItemView.this.getScaledSize();
                    StickerDynamicItemView.this.setViewSize((View)StickerDynamicItemView.this, scaledSize.width, scaledSize.height);
                    StickerDynamicItemView.this.setSelected(StickerDynamicItemView.this.f);
                }
                if (StickerDynamicItemView.this.isLayouted) {
                    StickerDynamicItemView.this.initStickerPostion();
                }
                TLog.e("[Debug] BitmapSize = %s", TuSdkSize.create(imageBitmap));
                StickerDynamicItemView.this.getRenderView().setImageBitmap(imageBitmap);
                final TuSdkSize scaledSize2 = StickerDynamicItemView.this.getScaledSize();
                StickerDynamicItemView.this.setViewSize((View)StickerDynamicItemView.this, scaledSize2.width, scaledSize2.height);
            }
        });
    }
    
    @Override
    public void setSticker(final StickerDynamicData e) {
        this.e = e;
        if (this.d == null) {
            return;
        }
        this.showViewIn((View)this, false);
        this.getRenderView().post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (StickerDynamicItemView.this.getWidth() == 0) {
                    StickerDynamicItemView.this.postInvalidate();
                }
                StickerDynamicItemView.this.showViewIn((View)StickerDynamicItemView.this, true);
            }
        });
        this.d.showGroupSticker(e);
    }
    
    public StickerDynamicData getCurrentStickerGroup() {
        return this.e;
    }
    
    @Override
    public void setStroke(final int h, final int n) {
        this.h = h;
        this.g = ((n > 0) ? n : 0);
        if (this.mCSize == null) {
            return;
        }
        this.getRenderView().setStroke(h, n);
    }
    
    public void updateStickers(final long n) {
        this.d.updataStickers(n);
    }
    
    @Override
    public void setSelected(final boolean f) {
        this.f = f;
        if (this.mCSize != null) {
            if (this.getRenderView() != null) {
                this.getRenderView().setStroke(f ? this.h : 0, this.g);
            }
            this.showViewIn((View)this.getCancelButton(), f);
            this.showViewIn((View)this.getTurnButton(), f);
        }
    }
    
    public void setTranslation(final float n, final float n2) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                StickerDynamicItemView.this.mTranslation.x = n;
                StickerDynamicItemView.this.mTranslation.y = n2;
                ViewCompat.setTranslationX((View)StickerDynamicItemView.this, StickerDynamicItemView.this.mTranslation.x);
                ViewCompat.setTranslationY((View)StickerDynamicItemView.this, StickerDynamicItemView.this.mTranslation.y);
            }
        });
    }
    
    public PointF getTranslation() {
        return this.mTranslation;
    }
    
    public float getCurrentScale() {
        return this.mScale;
    }
    
    public float getCurrentDegree() {
        return this.mDegree;
    }
    
    public void restoreRotation() {
        ViewCompat.setRotation((View)this, this.mDegree);
    }
    
    @Override
    public TuSdkSize getScaledSize() {
        return TuSdkSize.create((int)Math.ceil(this.mCSize.width * this.mScale + this.mCMargin.x), (int)Math.ceil(this.mCSize.height * this.mScale + this.mCMargin.y));
    }
    
    @Override
    public TuSdkSize getRenderScaledSize() {
        return TuSdkSize.create((int)Math.ceil(this.mCSize.width * this.mScale), (int)Math.ceil(this.mCSize.height * this.mScale));
    }
}
