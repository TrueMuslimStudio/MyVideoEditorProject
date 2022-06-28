// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.widget.sticker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.struct.ViewSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.TuSdkGestureRecognizer;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.button.TuSdkImageButton;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerItemViewInterface;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerResult;

public abstract class StickerItemViewBase extends TuSdkRelativeLayout implements StickerItemViewInterface
{
    protected boolean mEnableExpand;
    protected StickerItemViewDelegate mDelegate;
    private ViewTreeObserver.OnGlobalLayoutListener a;
    private ViewTreeObserver.OnPreDrawListener b;
    protected StickerData mSticker;
    protected TuSdkSize mCSize;
    protected Point mCMargin;
    protected Point mCOffset;
    protected Rect mParentFrame;
    protected float mMinScale;
    protected float mMaxScale;
    protected TuSdkSize mDefaultViewSize;
    protected float mCHypotenuse;
    private boolean c;
    protected StickerView.StickerType mStickerType;
    protected StickerView.StickerType mType;
    private PointF d;
    protected PointF mLastPoint;
    protected PointF mTranslation;
    protected float mScale;
    protected float mDegree;
    protected boolean mHasExceededMaxSize;
    private TuSdkGestureRecognizer e;
    @SuppressLint({ "ClickableViewAccessibility" })
    protected OnTouchListener mOnTouchListener;
    
    public StickerItemViewBase(final Context context) {
        super(context);
        this.mEnableExpand = true;
        this.mCMargin = new Point();
        this.mCOffset = new Point();
        this.mMinScale = 0.5f;
        this.d = new PointF();
        this.mLastPoint = new PointF();
        this.mTranslation = new PointF();
        this.mScale = 1.0f;
        this.e = new TuSdkGestureRecognizer() {
            @Override
            public void onTouchBegin(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent) {
                StickerItemViewBase.this.a(motionEvent);
            }
            
            @Override
            public void onTouchSingleMove(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, motionEvent);
            }
            
            @Override
            public void onTouchMultipleMoveForStablization(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, stepData);
            }
            
            @Override
            public void onTouchEnd(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.b(motionEvent);
            }
        };
        this.mOnTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                final Rect locationInWindow = TuSdkViewHelper.locationInWindow((View)StickerItemViewBase.this.getParent());
                TLog.e("onTouch ----- 0 parentFrame = $s", locationInWindow);
                float f = 0.0f;
                if (StickerItemViewBase.this.mParentFrame.top - locationInWindow.top > 100) {
                    f = (float)(StickerItemViewBase.this.mParentFrame.top - locationInWindow.top);
                }
                TLog.e("onTouch ----- 1 yOffset = $s", f);
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerItemViewBase.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                    case 2: {
                        StickerItemViewBase.this.handleTurnAndScaleActionMove(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                }
                return true;
            }
        };
    }
    
    public StickerItemViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.mEnableExpand = true;
        this.mCMargin = new Point();
        this.mCOffset = new Point();
        this.mMinScale = 0.5f;
        this.d = new PointF();
        this.mLastPoint = new PointF();
        this.mTranslation = new PointF();
        this.mScale = 1.0f;
        this.e = new TuSdkGestureRecognizer() {
            @Override
            public void onTouchBegin(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent) {
                StickerItemViewBase.this.a(motionEvent);
            }
            
            @Override
            public void onTouchSingleMove(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, motionEvent);
            }
            
            @Override
            public void onTouchMultipleMoveForStablization(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, stepData);
            }
            
            @Override
            public void onTouchEnd(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.b(motionEvent);
            }
        };
        this.mOnTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                final Rect locationInWindow = TuSdkViewHelper.locationInWindow((View)StickerItemViewBase.this.getParent());
                TLog.e("onTouch ----- 0 parentFrame = $s", locationInWindow);
                float f = 0.0f;
                if (StickerItemViewBase.this.mParentFrame.top - locationInWindow.top > 100) {
                    f = (float)(StickerItemViewBase.this.mParentFrame.top - locationInWindow.top);
                }
                TLog.e("onTouch ----- 1 yOffset = $s", f);
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerItemViewBase.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                    case 2: {
                        StickerItemViewBase.this.handleTurnAndScaleActionMove(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                }
                return true;
            }
        };
    }
    
    public StickerItemViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mEnableExpand = true;
        this.mCMargin = new Point();
        this.mCOffset = new Point();
        this.mMinScale = 0.5f;
        this.d = new PointF();
        this.mLastPoint = new PointF();
        this.mTranslation = new PointF();
        this.mScale = 1.0f;
        this.e = new TuSdkGestureRecognizer() {
            @Override
            public void onTouchBegin(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent) {
                StickerItemViewBase.this.a(motionEvent);
            }
            
            @Override
            public void onTouchSingleMove(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, motionEvent);
            }
            
            @Override
            public void onTouchMultipleMoveForStablization(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final StepData stepData) {
                StickerItemViewBase.this.a(tuSdkGestureRecognizer, stepData);
            }
            
            @Override
            public void onTouchEnd(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                StickerItemViewBase.this.b(motionEvent);
            }
        };
        this.mOnTouchListener = (OnTouchListener)new OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 1) {
                    return false;
                }
                final Rect locationInWindow = TuSdkViewHelper.locationInWindow((View)StickerItemViewBase.this.getParent());
                TLog.e("onTouch ----- 0 parentFrame = $s", locationInWindow);
                float f = 0.0f;
                if (StickerItemViewBase.this.mParentFrame.top - locationInWindow.top > 100) {
                    f = (float)(StickerItemViewBase.this.mParentFrame.top - locationInWindow.top);
                }
                TLog.e("onTouch ----- 1 yOffset = $s", f);
                switch (motionEvent.getAction()) {
                    case 0: {
                        StickerItemViewBase.this.handleTurnAndScaleActionStart(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                    case 2: {
                        StickerItemViewBase.this.handleTurnAndScaleActionMove(null, motionEvent.getRawX(), motionEvent.getRawY() + f);
                        break;
                    }
                }
                return true;
            }
        };
    }
    
    public StickerItemViewDelegate getDelegate() {
        return this.mDelegate;
    }
    
    @Override
    public void setDelegate(final StickerItemViewDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }
    
    @Override
    protected void initView() {
        ViewTreeObserver var1 = this.getViewTreeObserver();
        this.a = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                TuSdkViewHelper.removeGlobalLayoutListener(StickerItemViewBase.this.getViewTreeObserver(), StickerItemViewBase.this.a);
                if (!StickerItemViewBase.this.isLayouted) {
                    StickerItemViewBase.this.isLayouted = true;
                    StickerItemViewBase.this.e.setMultipleStablization(true);
                    StickerItemViewBase.this.onLayouted();
                }

            }
        };
        if (Build.VERSION.SDK_INT >= 16) {
            this.b = new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    StickerItemViewBase.this.d = new PointF(StickerItemViewBase.this.getX() + (float)(StickerItemViewBase.this.getWidth() / 2), StickerItemViewBase.this.getY() + (float)(StickerItemViewBase.this.getHeight() / 2));
                    return true;
                }
            };
            var1.addOnPreDrawListener(this.b);
        }

        var1.addOnGlobalLayoutListener(this.a);
    }
    
    @Override
    public void setStickerType(final StickerView.StickerType mType) {
        this.mType = mType;
    }
    
    @Override
    public void setStickerViewType(final StickerView.StickerType mStickerType) {
        this.mStickerType = mStickerType;
    }
    
    @Override
    public StickerView.StickerType getStickerType() {
        return this.mType;
    }
    
    @Override
    public void setSticker(final StickerData stickerData) {
    }
    
    @Override
    public StickerData getStickerData() {
        return this.mSticker;
    }
    
    public float getMinScale() {
        if (this.mMinScale < 0.5f) {
            this.mMinScale = 0.5f;
        }
        return this.mMinScale;
    }
    
    public void setMinScale(final float mMinScale) {
        this.mMinScale = mMinScale;
    }
    
    @Override
    public void setParentFrame(final Rect mParentFrame) {
        this.mParentFrame = mParentFrame;
    }
    
    protected void initStickerPostion() {
        if (this.mCSize == null) {
            return;
        }
        this.mCHypotenuse = RectHelper.getDistanceOfTwoPoints(0.0f, 0.0f, (float)this.mCSize.width, (float)this.mCSize.height);
        this.mDefaultViewSize = TuSdkSize.create(this.mCSize.width + this.mCMargin.x, this.mCSize.height + this.mCMargin.y);
        this.mMaxScale = Math.min((this.mParentFrame.width() - this.mCMargin.x) / (float)this.mCSize.width, (this.mParentFrame.height() - this.mCMargin.y) / (float)this.mCSize.height);
        if (this.mMaxScale < this.mMinScale) {
            this.mMaxScale = this.mMinScale;
        }
        this.setSize((View)this, this.mDefaultViewSize);
        if (this.mParentFrame == null) {
            return;
        }
        ((LayoutParams)this.getLayoutParams()).addRule(13);
        this.mTranslation.x = this.getTranslationX();
        this.mTranslation.y = this.getTranslationY();
        if (this.mSticker != null) {}
    }
    
    @Override
    public StickerResult getResult(final Rect rect) {
        final StickerResult stickerResult = new StickerResult();
        stickerResult.item = this.mSticker.copy();
        stickerResult.degree = this.mDegree;
        stickerResult.center = this.getCenterPercent(rect);
        return stickerResult;
    }
    
    protected RectF getCenterPercent(final Rect rect) {
        final PointF b = this.b(new Rect());
        final RectF rectF = new RectF();
        if (rect != null) {
            b.offset((float)(-rect.left), (float)(-rect.top));
            rectF.left = b.x / rect.width();
            rectF.top = b.y / rect.height();
            rectF.right = rectF.left + this.mCSize.width * this.mScale / rect.width();
            rectF.bottom = rectF.top + this.mCSize.height * this.mScale / rect.height();
            TLog.e("getCenterPercent : CSSize width: %s  height:%s  mScale \uff1a%s", this.mCSize.width, this.mCSize.height, this.mScale);
        }
        else {
            rectF.left = b.x / this.mParentFrame.width();
            rectF.top = b.y / this.mParentFrame.height();
            rectF.right = rectF.left + this.mCSize.width * this.mScale / this.mParentFrame.width();
            rectF.bottom = rectF.top + this.mCSize.height * this.mScale / this.mParentFrame.height();
        }
        return rectF;
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        return this.e.onTouch((View)this, motionEvent);
    }
    
    private void a(final MotionEvent motionEvent) {
        this.c = false;
        if (this.mDelegate != null) {
            this.mDelegate.onStickerItemViewSelected(this);
        }
    }
    
    private void b(final MotionEvent motionEvent) {
        if (this.mDelegate != null && !this.c) {
            this.mDelegate.onStickerItemViewReleased(this);
        }
    }
    
    private void a(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final MotionEvent motionEvent) {
        if (Math.abs(tuSdkGestureRecognizer.getStepPoint().x) >= 2.0f || Math.abs(tuSdkGestureRecognizer.getStepPoint().y) >= 2.0f) {
            this.c = true;
        }
        this.mTranslation.offset(tuSdkGestureRecognizer.getStepPoint().x, tuSdkGestureRecognizer.getStepPoint().y);
        final Rect rect = new Rect();
        final boolean b = this.mParentFrame.width() > this.mParentFrame.height();
        final PointF a = this.a(rect);
        final RectF minEnclosingRectangle = RectHelper.minEnclosingRectangle(a, ViewSize.create((View)this), this.mDegree);
        a.offset(tuSdkGestureRecognizer.getStepPoint().x, tuSdkGestureRecognizer.getStepPoint().y);
        this.a(a, minEnclosingRectangle);
        ViewCompat.setTranslationX((View)this, this.mTranslation.x);
        ViewCompat.setTranslationY((View)this, this.mTranslation.y);
        this.requestLayout();
        this.d = new PointF(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
    }
    
    private void a(final PointF pointF, final RectF rectF) {
        if (this.mParentFrame == null || pointF == null || rectF == null) {
            return;
        }
        final RectF rectF2 = new RectF(-rectF.width() * 0.5f, -rectF.height() * 0.5f, this.mParentFrame.width() + rectF.width() * 0.5f, this.mParentFrame.height() + rectF.height() * 0.5f);
        if (rectF.left < rectF2.left) {
            pointF.x = rectF2.left + (rectF.width() - this.getWidth()) * 0.5f;
        }
        if (rectF.right > rectF2.right) {
            pointF.x = rectF2.right - (rectF.width() + this.getWidth()) * 0.5f;
        }
        if (rectF.top < rectF2.top) {
            pointF.y = rectF2.top + (rectF.height() - this.getHeight()) * 0.5f;
        }
        if (rectF.bottom > rectF2.bottom) {
            pointF.y = rectF2.bottom - (rectF.height() + this.getHeight()) * 0.5f;
        }
    }
    
    private void a(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final TuSdkGestureRecognizer.StepData stepData) {
        ViewCompat.setRotation((View)this, this.mDegree = (360.0f + this.mDegree + stepData.stepDegree) % 360.0f);
        final Rect rect = new Rect();
        this.getGlobalVisibleRect(rect);
        this.computerScale(stepData.stepSpace, new PointF((float)rect.centerX(), (float)rect.centerY()));
        this.requestLayout();
    }
    
    protected void handleTurnAndScaleActionStart(final TuSdkImageButton tuSdkImageButton, final float n, final float n2) {
        this.mLastPoint.set(n, n2);
        if (this.mDelegate != null) {
            this.mDelegate.onStickerItemViewSelected(this);
        }
    }
    
    protected void handleTurnAndScaleActionMove(final TuSdkImageButton tuSdkImageButton, final float n, final float n2) {
        this.d = new PointF(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
        final PointF pointF = new PointF(n, n2);
        final Rect rect = new Rect();
        final PointF a = this.a(rect);
        TLog.e("handleTurnAndScaleActionMove mLastCenterPoint = %s point = %s trans = %s cPoint = %s", this.d, pointF, rect, a);
        this.a(pointF, a);
        this.c(pointF, a);
        this.requestLayout();
        this.mLastPoint.set(pointF.x, pointF.y);
    }
    
    private void a(final PointF pointF, final PointF pointF2) {
        ViewCompat.setRotation((View)this, this.mDegree = (360.0f + this.mDegree + (this.b(pointF, pointF2) - this.b(this.mLastPoint, pointF2))) % 360.0f);
    }
    
    public void resetRotation() {
        ViewCompat.setRotation((View)this, 0.0f);
    }
    
    private float b(final PointF pointF, final PointF pointF2) {
        final PointF pointF3 = new PointF(pointF.x - this.mParentFrame.left, pointF.y - this.mParentFrame.top);
        return RectHelper.computeAngle(pointF, pointF2);
    }
    
    protected PointF getCenterOpposite() {
        return this.a(this.mTranslation);
    }
    
    private PointF a(final PointF pointF) {
        final PointF pointF2 = new PointF();
        pointF2.x = pointF.x + this.getWidth() * 0.5f;
        pointF2.y = pointF.y + this.getHeight() * 0.5f;
        return pointF2;
    }
    
    private PointF a(final Rect rect) {
        this.getGlobalVisibleRect(rect, new Point());
        final PointF pointF = new PointF((float)rect.centerX(), (float)rect.centerY());
        pointF.set((float)rect.centerX(), (float)rect.centerY());
        return pointF;
    }
    
    private PointF b(final Rect rect) {
        this.getGlobalVisibleRect(rect, new Point());
        PointF pointF = new PointF((float)rect.centerX(), (float)rect.centerY());
        final ViewGroup viewGroup = (ViewGroup)this.getParent();
        final Rect rect2 = new Rect();
        viewGroup.getGlobalVisibleRect(rect2);
        final PointF pointF2 = new PointF();
        pointF2.set(this.d);
        int dimensionPixelSize = 0;
        final int identifier = this.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            dimensionPixelSize = this.getContext().getResources().getDimensionPixelSize(identifier);
        }
        if (rect.left == rect2.left || rect.right == rect2.right || rect.top == rect2.top || rect.bottom == rect2.bottom) {
            pointF = pointF2;
            if (rect2.top != 0) {
                pointF.offset(0.0f, (float)dimensionPixelSize);
            }
        }
        else {
            pointF.set((float)rect.centerX(), (float)rect.centerY());
        }
        if (rect2.top != 0) {
            pointF.offset(0.0f, (float)(-dimensionPixelSize));
        }
        return pointF2;
    }
    
    private void c(final PointF pointF, final PointF pointF2) {
        this.computerScale(RectHelper.getDistanceOfTwoPoints(pointF2, pointF) - RectHelper.getDistanceOfTwoPoints(pointF2, this.mLastPoint), pointF2);
    }
    
    protected void computerScale(final float n, final PointF pointF) {
        if (n == 0.0f) {
            return;
        }
        final float n2 = n / this.mCHypotenuse * 2.0f;
        if (!this.mHasExceededMaxSize || n2 < 0.0f) {
            this.mScale += n2;
        }
        if (this.mScale < this.mMinScale) {
            this.mScale = this.mMinScale;
        }
        else if (this.mScale > this.mMaxScale) {
            this.mScale = this.mMaxScale;
        }
        final TuSdkSize scaledSize = this.getScaledSize();
        if (!this.mEnableExpand) {
            final RectF minEnclosingRectangle = RectHelper.minEnclosingRectangle(pointF, scaledSize, this.mDegree);
            this.mTranslation.offset((this.getWidth() - scaledSize.width) * 0.5f, (this.getHeight() - scaledSize.height) * 0.5f);
            this.a(this.mTranslation, minEnclosingRectangle);
            ViewCompat.setTranslationX((View)this, this.mTranslation.x);
            ViewCompat.setTranslationY((View)this, this.mTranslation.y);
        }
        this.setViewSize((View)this, scaledSize.width, scaledSize.height);
    }
    
    public Point getCMargin() {
        return this.mCMargin;
    }
    
    public Point getCOffset() {
        return this.mCOffset;
    }
    
    public TuSdkSize getScaledSize() {
        return TuSdkSize.create((int)Math.ceil(this.mCSize.width * this.mScale + this.mCMargin.x), (int)Math.ceil(this.mCSize.height * this.mScale + this.mCMargin.y));
    }
    
    public TuSdkSize getRenderScaledSize() {
        return TuSdkSize.create((int)Math.ceil(this.mCSize.width * this.mScale), (int)Math.ceil(this.mCSize.height * this.mScale));
    }
    
    public void scaleSize(final float n) {
        this.mScale = ((this.mMaxScale * n < this.getMinScale()) ? this.getMinScale() : (this.mMaxScale * n));
        this.setViewSize((View)this, this.getScaledSize().width, this.getScaledSize().height);
        this.invalidate();
    }
}
