// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.widget.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;


import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.FontUtils;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkTextView;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.button.TuSdkImageButton;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerFactory;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerResult;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerText;

import java.util.Iterator;

public final class StickerTextItemView extends StickerItemViewBase
{
    private TuSdkTextView a;
    private TuSdkImageButton b;
    private TuSdkImageButton c;
    private TuSdkImageButton d;
    private float e;
    protected OnTouchListener mOnResizeButtonTouchListener;
    private int f;
    private boolean g;
    private OnClickListener h;
    private int i;
    private int j;
    private float k;
    private int l;
    private boolean m;
    private String n;
    private String o;
    private String p;
    
    public static int getLayoutId() {
        return TuSdkContext.getLayoutResId("tusdk_impl_component_widget_sticker_text_item_view");
    }
    
    public StickerTextItemView(final Context context) {
        super(context);
        this.e = 1.0f;
        this.mOnResizeButtonTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerTextItemView.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY());
                        break;
                    }
                    case 2: {
                        StickerTextItemView.this.handleResizeActionMove(null, motionEvent);
                        break;
                    }
                }
                return true;
            }
        };
        this.h = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerTextItemView.this.equalViewIds(view, (View)StickerTextItemView.this.getCancelButton())) {
                    StickerTextItemView.this.a();
                }
            }
        };
    }
    
    public StickerTextItemView(final Context context, final AttributeSet set) {
        super(context, set);
        this.e = 1.0f;
        this.mOnResizeButtonTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerTextItemView.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY());
                        break;
                    }
                    case 2: {
                        StickerTextItemView.this.handleResizeActionMove(null, motionEvent);
                        break;
                    }
                }
                return true;
            }
        };
        this.h = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerTextItemView.this.equalViewIds(view, (View)StickerTextItemView.this.getCancelButton())) {
                    StickerTextItemView.this.a();
                }
            }
        };
    }
    
    public StickerTextItemView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.e = 1.0f;
        this.mOnResizeButtonTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerTextItemView.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY());
                        break;
                    }
                    case 2: {
                        StickerTextItemView.this.handleResizeActionMove(null, motionEvent);
                        break;
                    }
                }
                return true;
            }
        };
        this.h = (OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (StickerTextItemView.this.equalViewIds(view, (View)StickerTextItemView.this.getCancelButton())) {
                    StickerTextItemView.this.a();
                }
            }
        };
        this.setBackgroundColor(-1);
    }
    
    protected void onMeasure(final int n, final int n2) {
        if (this.mEnableExpand) {
            final String[] split = this.getTextView().getText().toString().split("\\\n");
            int n3;
            if (split == null) {
                n3 = (int)this.getTextView().getPaint().measureText(this.getTextView().getText().toString()) + 100 + this.l * 2;
            }
            else {
                String s = null;
                for (final String s2 : split) {
                    if (s == null) {
                        s = s2;
                    }
                    else if (s.length() < s2.length()) {
                        s = s2;
                    }
                }
                n3 = (int)this.getTextView().getPaint().measureText(s) + 100 + this.l * 2;
            }
            final int measureSpec = MeasureSpec.makeMeasureSpec((this.getTextView().getWidth() > n3) ? this.getTextView().getWidth() : n3, Integer.MIN_VALUE);
            int n4 = 40;
            if (split != null) {
                n4 = 40 * split.length;
            }
            final int measureSpec2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(n2) + n4, MeasureSpec.getMode(n2));
            this.mCSize.width = ((this.getTextView().getWidth() > n3) ? this.getTextView().getWidth() : n3);
            this.mCSize.width = this.mCSize.width - 100 - this.l;
            this.mSticker.width = this.mCSize.width;
            super.onMeasure(measureSpec, measureSpec2);
        }
        else {
            super.onMeasure(n, n2);
        }
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        return (this.mStickerType == StickerView.StickerType.Normal || this.mStickerType == this.mType) && super.onTouchEvent(motionEvent);
    }
    
    public TuSdkTextView getTextView() {
        if (this.a == null) {
            this.a = this.getViewById("lsq_sticker_textView");
            this.a.getPaint().setAntiAlias(true);
            this.a.getPaint().setDither(true);
        }
        return this.a;
    }
    
    public final TuSdkImageButton getCancelButton() {
        if (this.b == null) {
            this.b = this.getViewById("lsq_sticker_cancelButton");
            if (this.b != null) {
                this.b.setOnClickListener(this.h);
            }
        }
        return this.b;
    }
    
    public final TuSdkImageButton getResizeButton() {
        if (this.c == null) {
            this.c = this.getViewById("lsq_sticker_resizeButton");
            if (this.c != null) {
                this.c.setOnTouchListener(this.mOnResizeButtonTouchListener);
            }
        }
        return this.c;
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    public final TuSdkImageButton getTurnButton() {
        if (this.d == null) {
            this.d = this.getViewById("lsq_sticker_turnButton");
            if (this.d != null) {
                this.d.setOnTouchListener(this.mOnTouchListener);
            }
        }
        return this.d;
    }
    
    @Override
    protected PointF getCenterOpposite() {
        final PointF centerOpposite = super.getCenterOpposite();
        final PointF pointF = new PointF(centerOpposite.x, centerOpposite.y - TuSdkContext.dip2px(32.0f) * 0.5f);
        centerOpposite.offset((float)(RectHelper.computerPotintDistance(pointF, centerOpposite) * Math.sin(this.mDegree * 3.141592653589793 / 180.0)), -(float)(RectHelper.computerPotintDistance(pointF, centerOpposite) * Math.cos(this.mDegree * 3.141592653589793 / 180.0)));
        return centerOpposite;
    }
    
    @Override
    protected void handleTurnAndScaleActionStart(final TuSdkImageButton tuSdkImageButton, final float n, final float n2) {
        super.handleTurnAndScaleActionStart(tuSdkImageButton, n, n2);
        this.f = this.getTextView().getWidth();
    }
    
    protected void handleResizeActionMove(final TuSdkImageButton tuSdkImageButton, final MotionEvent motionEvent) {
        this.a(new PointF(motionEvent.getRawX(), motionEvent.getRawY()), this.getCenterOpposite());
        this.requestLayout();
    }
    
    private int a(final String s) {
        int width = 0;
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            final Rect textBoundsExcludeFontPadding = FontUtils.getTextBoundsExcludeFontPadding(String.valueOf(charArray[i]), this.getTextView().getTextSize());
            if (textBoundsExcludeFontPadding.width() > width) {
                width = textBoundsExcludeFontPadding.width();
            }
        }
        return width;
    }
    
    @Override
    protected void handleTurnAndScaleActionMove(final TuSdkImageButton tuSdkImageButton, final float n, final float n2) {
        super.handleTurnAndScaleActionMove(tuSdkImageButton, n, n2);
        this.a(new PointF(n, n2), (PointF)null);
    }
    
    private void a(final PointF pointF, final PointF pointF2) {
        final float n = (float)((pointF.x - this.mLastPoint.x) / Math.cos(this.mDegree * 3.141592653589793 / 180.0));
        if (this.f + n < this.a(this.getTextView().getText().toString()) + this.l * 2) {
            return;
        }
        if (this.g && n < 0.0f) {
            return;
        }
        final StaticLayout staticLayout = new StaticLayout(this.getTextView().getText(), this.getTextView().getPaint(), this.getTextView().getWidth() - this.l * 2, Layout.Alignment.ALIGN_CENTER, this.e, 0.0f, false);
        final int n2 = staticLayout.getHeight() + this.mCMargin.y;
        final int n3 = this.f + this.mCMargin.x + (int)n - this.l * 2;
        if (n2 > this.mParentFrame.height()) {
            this.g = true;
            this.setViewSize((View)this, this.mCSize.width + this.mCMargin.x, this.mCSize.height + this.mCMargin.y);
            return;
        }
        this.g = false;
        this.setViewSize((View)this, n3, n2);
        this.mSticker.width = TuSdkContext.px2dip((float)(this.getTextView().getWidth() - this.l * 2));
        this.mSticker.height = TuSdkContext.px2dip((float)staticLayout.getHeight());
        this.mCSize = this.mSticker.sizePixies();
        this.mMaxScale /= this.mScale;
        this.mMinScale /= this.mScale;
        this.mScale = 1.0f;
        this.k = TuSdkContext.px2sp(this.getTextView().getTextSize());
        this.mCHypotenuse = RectHelper.getDistanceOfTwoPoints(0.0f, 0.0f, (float)this.mCSize.width, (float)this.mCSize.height);
    }
    
    @Override
    public void loadView() {
        this.getTextView();
        this.getCancelButton();
        this.getTurnButton();
        this.getResizeButton();
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
    public void setStroke(final int j, final int n) {
        this.j = j;
        this.i = ((n > 0) ? n : 0);
        if (this.getTextView() != null) {
            this.getTextView().setStroke(this.j, this.i);
        }
    }
    
    @Override
    protected void onLayouted() {
        super.onLayouted();
        if (this.getTextView() == null) {
            return;
        }
        this.mCMargin.x = this.getWidth() - this.getTextView().getWidth() + this.l * 2;
        this.mCMargin.y = this.getHeight() - this.getTextView().getHeight() + this.l * 2;
        final Rect locationInWindow = TuSdkViewHelper.locationInWindow((View)this);
        final Rect locationInWindow2 = TuSdkViewHelper.locationInWindow((View)this.getTextView());
        this.mCOffset.x = locationInWindow2.left - locationInWindow.left;
        this.mCOffset.y = locationInWindow2.top - locationInWindow.top;
        this.initStickerPostion();
    }
    
    @Override
    public void setSticker(final StickerData mSticker) {
        if (mSticker == null) {
            return;
        }
        this.showViewIn((View)this.getTurnButton(), mSticker.getType() == StickerData.StickerType.TypeText);
        this.showViewIn((View)this, false);
        this.getTextView().post((Runnable)new Runnable() {
            @Override
            public void run() {
                StickerTextItemView.this.showViewIn((View)StickerTextItemView.this, true);
            }
        });
        this.a(mSticker);
        this.mSticker = mSticker;
        this.mCSize = this.mSticker.sizePixies();
        if (this.isLayouted) {
            this.initStickerPostion();
        }
    }
    
    @Override
    public void setSticker(final StickerDynamicData stickerDynamicData) {
        throw new UnsupportedOperationException();
    }
    
    private void a(final StickerData stickerData) {
        if (stickerData.texts == null || stickerData.texts.size() == 0 || this.getTextView() == null) {
            return;
        }
        final StickerText stickerText = stickerData.texts.get(0);
        this.getTextView().setText((CharSequence)stickerText.content);
        this.getTextView().setTextSize(2, stickerText.textSize);
        this.getTextView().setTextColor(Color.parseColor(stickerText.color));
        this.l = TuSdkContext.dip2px((float)stickerText.paddings);
        this.getTextView().setPadding(this.l, this.l, this.l, this.l);
        this.getTextView().setGravity(17);
        stickerText.alignment = 1;
        this.k = stickerText.textSize;
        this.n = stickerText.color;
        this.getTextView().measure(0, 0);
        stickerData.width = TuSdkContext.px2dip((float)(this.getTextView().getMeasuredWidth() - this.l * 2));
        stickerData.height = TuSdkContext.px2dip((float)(this.getTextView().getMeasuredHeight() - this.l * 2));
        stickerData.width += 10;
    }
    
    @Override
    public StickerResult getResult(final Rect rect) {
        this.setSelected(false);
        final Bitmap bitmapFromView = StickerFactory.createBitmapFromView((View)this.getTextView(), 5);
        this.setSelected(true);
        this.mScale = 1.0f;
        this.mCSize = TuSdkSize.create(bitmapFromView);
        final StickerResult result = super.getResult(rect);
        result.item.setImage(bitmapFromView);
        result.item.texts = null;
        result.item.width = TuSdkContext.px2dip((float)this.getTextView().getWidth());
        result.item.height = TuSdkContext.px2dip((float)this.getTextView().getHeight());
        return result;
    }
    
    public void updateText(String replaceAll, final boolean m) {
        if (replaceAll == null) {
            return;
        }
        this.m = m;
        replaceAll = replaceAll.trim().replaceAll("\\s+\\n", "\n");
        this.getTextView().setText((CharSequence)replaceAll);
        final StaticLayout staticLayout = new StaticLayout(this.getTextView().getText(), this.getTextView().getPaint(), this.mEnableExpand ? Integer.MAX_VALUE : (this.getTextView().getWidth() - this.l * 2), Layout.Alignment.ALIGN_CENTER, this.e, 0.0f, false);
        this.setViewSize((View)this, staticLayout.getWidth() + this.mCMargin.x, staticLayout.getHeight() + this.mCMargin.y);
        this.mSticker.width = TuSdkContext.px2dip(this.mEnableExpand ? ((float)this.getTextView().getWidth()) : ((float)(staticLayout.getWidth() - this.l * 2)));
        this.mSticker.height = TuSdkContext.px2dip((float)staticLayout.getHeight());
        this.mCSize = this.mSticker.sizePixies();
        this.mMaxScale /= this.mScale;
        this.mMinScale /= this.mScale;
        this.mScale = 1.0f;
        this.k = TuSdkContext.px2sp(this.getTextView().getTextSize());
        this.mCHypotenuse = RectHelper.getDistanceOfTwoPoints(0.0f, 0.0f, (float)this.mCSize.width, (float)this.mCSize.height);
    }
    
    public void onSelectedColorChanged(final int n, final String p2) {
        if (n == 0) {
            this.n = p2;
        }
        else if (n == 1) {
            this.o = p2;
        }
        else if (n == 2) {
            this.p = p2;
        }
        this.onSelectedColorChanged(n, Color.parseColor(p2));
    }
    
    public void onSelectedColorChanged(final int n, final int n2) {
        if (n == 0) {
            this.getTextView().setTextColor(n2);
        }
        else if (n == 1) {
            this.getTextView().setBackgroundColor(n2);
        }
        else if (n == 2) {
            this.getTextView().setShadowLayer(2.0f, 3.0f, 2.0f, n2);
        }
    }
    
    public void toggleTextUnderlineStyle() {
        for (final StickerText stickerText : this.mSticker.texts) {
            stickerText.underline = !stickerText.underline;
            this.getTextView().setUnderlineText(stickerText.underline);
            this.getTextView().invalidate();
        }
    }
    
    public void setUnderline(final boolean b) {
        final Iterator<StickerText> iterator = this.mSticker.texts.iterator();
        while (iterator.hasNext()) {
            iterator.next().underline = b;
            this.getTextView().setUnderlineText(b);
            this.getTextView().invalidate();
        }
    }
    
    protected void toggleTextReverse() {
        this.getTextView().setText((CharSequence)this.b(this.getTextView().getText().toString()));
    }
    
    public boolean isNeedReverse() {
        return this.m;
    }
    
    private String b(final String s) {
        final StringBuilder sb = new StringBuilder();
        final String[] split = s.split("\n");
        for (int i = 0; i < split.length; ++i) {
            sb.append((CharSequence)new StringBuilder(split[i]).reverse());
            if (i < split.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    public void changeTextAlignment(final int gravity) {
        if (this.getTextView() == null) {
            return;
        }
        this.getTextView().setGravity(gravity);
        final Iterator<StickerText> iterator = this.mSticker.texts.iterator();
        while (iterator.hasNext()) {
            iterator.next().alignment = this.a(gravity);
        }
    }
    
    private int a(final int n) {
        switch (n) {
            case 17: {
                return 1;
            }
            case 8388613: {
                return 2;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void setSelected(final boolean b) {
        if (this.getTextView() != null) {
            this.getTextView().setStroke(b ? this.j : 0, this.i);
        }
        this.showViewIn((View)this.getCancelButton(), b);
        this.showViewIn((View)this.getTurnButton(), b);
        this.showViewIn((View)this.getResizeButton(), b);
    }
    
    @Override
    public TuSdkSize getScaledSize() {
        final TuSdkSize scaledSize = super.getScaledSize();
        if (this.mEnableExpand) {
            this.mHasExceededMaxSize = false;
            if (this.getTextView() != null) {
                this.getTextView().setTextSize(2, this.k * this.mScale);
            }
        }
        else {
            if (scaledSize.width > this.mParentFrame.width() || scaledSize.height > this.mParentFrame.height()) {
                this.mHasExceededMaxSize = true;
                scaledSize.width = Math.min(this.getWidth(), this.mParentFrame.width());
                scaledSize.height = Math.min(this.getHeight(), this.mParentFrame.height());
                return scaledSize;
            }
            if (scaledSize.width < this.mParentFrame.width() && scaledSize.height < this.mParentFrame.height()) {
                this.mHasExceededMaxSize = false;
                if (this.getTextView() != null) {
                    this.getTextView().setTextSize(2, this.k * this.mScale);
                }
            }
        }
        return scaledSize;
    }
    
    public void setTextFont(final Typeface typeface) {
        if (this.a == null) {
            return;
        }
        this.a.setTypeface(typeface);
    }
    
    public void setTextStrokeWidth(final int textStrokeWidth) {
        if (this.a == null) {
            return;
        }
        this.a.setTextStrokeWidth(textStrokeWidth);
    }
    
    public void setTextStrokeColor(final int textStrokeColor) {
        if (this.a == null) {
            return;
        }
        this.a.setTextStrokeColor(textStrokeColor);
    }
    
    public void setTextSize(final float textSize) {
        if (this.a == null) {
            return;
        }
        this.a.setTextSize(textSize);
        this.postInvalidate();
    }
    
    public void setLetterSpacing(final float letterSpacing) {
        if (this.a == null) {
            return;
        }
        this.a.setLetterSpacing(letterSpacing);
        this.updateText(this.a.getText().toString(), this.m);
    }
    
    public void setLineSpacing(final float n, final float e) {
        if (this.a == null) {
            return;
        }
        this.e = e;
        this.a.setLineSpacing(n, e);
        this.updateText(this.a.getText().toString(), this.m);
    }
    
    public void setTranslation(final float n, final float n2) {
        this.post((Runnable)new Runnable() {
            @Override
            public void run() {
                StickerTextItemView.this.mTranslation.x = n;
                StickerTextItemView.this.mTranslation.y = n2;
                ViewCompat.setTranslationX((View)StickerTextItemView.this, StickerTextItemView.this.mTranslation.x);
                ViewCompat.setTranslationY((View)StickerTextItemView.this, StickerTextItemView.this.mTranslation.y);
            }
        });
    }
    
    public void reRotate() {
        ViewCompat.setRotation((View)this, this.mDegree);
    }
    
    public PointF getTranslation() {
        return this.mTranslation;
    }
    
    public TuSdkSize getCSize() {
        return this.mCSize;
    }
    
    public StickerData getSticker() {
        return this.mSticker;
    }
    
    public void setVisibility(final int visibility) {
        super.setVisibility(visibility);
    }
}
