// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myvideoeditorapp.kore.TuSdkBundle;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesBaseCameraInterface;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.struct.ViewSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkFace;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraAdapter;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraInterface;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public abstract class TuFocusTouchViewBase extends TuSdkRelativeLayout
{
    public static final long SamplingDistance = 2000L;
    public static final float SamplingRange = 50.0f;
    public static final long FaceDetectionDistance = 5000L;
    private long a;
    private TuSdkStillCameraInterface b;
    private PointF c;
    private MediaPlayer d;
    private boolean e;
    private RectF f;
    private Runnable g;
    protected final List<View> mFaceViews;
    
    public TuFocusTouchViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = 0L;
        this.g = new Runnable() {
            @Override
            public void run() {
                TuFocusTouchViewBase.this.c(TuFocusTouchViewBase.this.getLastPoint());
            }
        };
        this.mFaceViews = new ArrayList<View>();
    }
    
    public TuFocusTouchViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = 0L;
        this.g = new Runnable() {
            @Override
            public void run() {
                TuFocusTouchViewBase.this.c(TuFocusTouchViewBase.this.getLastPoint());
            }
        };
        this.mFaceViews = new ArrayList<View>();
    }
    
    public TuFocusTouchViewBase(final Context context) {
        super(context);
        this.a = 0L;
        this.g = new Runnable() {
            @Override
            public void run() {
                TuFocusTouchViewBase.this.c(TuFocusTouchViewBase.this.getLastPoint());
            }
        };
        this.mFaceViews = new ArrayList<View>();
    }
    
    public abstract boolean isDisableFocusBeep();
    
    public abstract boolean isEnableLongTouchCapture();
    
    protected abstract long getLongPressDistance();
    
    protected abstract int getLongPressOffset();
    
    public abstract boolean isDisableContinueFoucs();
    
    public abstract void setRangeViewFoucsState(final boolean p0);
    
    public TuSdkStillCameraInterface getCamera() {
        return this.b;
    }
    
    public void setCamera(final TuSdkStillCameraInterface b) {
        this.b = b;
    }
    
    private MediaPlayer a() {
        if (this.isDisableFocusBeep()) {
            return null;
        }
        if (this.d == null) {
            this.d = TuSdkContext.loadMediaAsset(TuSdkBundle.sdkBundleOther("camera_focus_beep.mp3"));
        }
        return this.d;
    }
    
    protected PointF getLastPoint() {
        if (this.c == null) {
            this.c = new PointF(this.getWidth() * 0.5f, this.getHeight() * 0.5f);
        }
        return this.c;
    }
    
    private void a(final PointF c) {
        this.c = c;
    }
    
    public void setRegionPercent(final RectF f) {
        this.f = f;
    }
    
    public RectF getRegionPercent() {
        if (this.f == null) {
            this.f = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        }
        return this.f;
    }
    
    @Override
    public void viewWillDestory() {
        super.viewWillDestory();
        this.b = null;
        if (this.d != null) {
            this.d.release();
            this.d = null;
        }
    }
    
    protected boolean notifyFoucs(final PointF pointF, final boolean b) {
        if (this.b == null || !this.b.canSupportAutoFocus() || this.b.getState() != TuSdkStillCameraAdapter.CameraState.StateStarted || !this.b(pointF)) {
            return false;
        }
        this.b.autoFocus(CameraConfigs.CameraAutoFocus.Auto, this.getRatioPoint(pointF), new SelesBaseCameraInterface.TuSdkAutoFocusCallback() {
            @Override
            public void onAutoFocus(final boolean b, final SelesBaseCameraInterface selesBaseCameraInterface) {
                TuFocusTouchViewBase.this.a(b);
                if (b) {
                    TuFocusTouchViewBase.this.b.captureImage();
                }
            }
        });
        return true;
    }
    
    private void a(final boolean rangeViewFoucsState) {
        this.setRangeViewFoucsState(rangeViewFoucsState);
        this.c();
    }
    
    protected RectF getRegionRectF() {
        final ViewSize create = ViewSize.create((View)this);
        return new RectF(this.getRegionPercent().left * create.width, this.getRegionPercent().top * create.height, this.getRegionPercent().right * create.width, this.getRegionPercent().bottom * create.height);
    }
    
    private boolean b(final PointF pointF) {
        return this.getRegionRectF().contains(pointF.x, pointF.y);
    }
    
    private void c(final PointF pointF) {
        this.notifyFoucs(pointF, this.e = true);
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            ThreadHelper.cancel(this.g);
            return super.onTouchEvent(motionEvent);
        }
        this.a = Calendar.getInstance().getTimeInMillis();
        switch (motionEvent.getAction()) {
            case 0: {
                this.a(motionEvent);
                break;
            }
            case 1: {
                this.b(motionEvent);
                break;
            }
            case 2: {
                this.c(motionEvent);
                break;
            }
            default: {
                this.e = true;
                this.d(motionEvent);
                break;
            }
        }
        return true;
    }
    
    private void a(final MotionEvent motionEvent) {
        this.e = false;
        this.d(motionEvent);
        this.b();
    }
    
    private void b() {
        if (this.isEnableLongTouchCapture()) {
            ThreadHelper.postDelayed(this.g, this.getLongPressDistance());
        }
    }
    
    private void b(final MotionEvent motionEvent) {
        if (this.e) {
            return;
        }
        this.e = true;
        this.d(motionEvent);
        this.notifyFoucs(this.getLastPoint(), false);
    }
    
    private void c(final MotionEvent motionEvent) {
        if (this.e) {
            return;
        }
        if (this.e(motionEvent)) {
            this.d(motionEvent);
            this.b();
        }
    }
    
    protected final PointF getRatioPoint(final PointF pointF) {
        if (pointF == null) {
            return null;
        }
        final PointF pointF2 = new PointF();
        pointF2.x = pointF.x / this.getWidth();
        pointF2.y = pointF.y / this.getHeight();
        return pointF2;
    }
    
    private void d(final MotionEvent motionEvent) {
        final PointF lastPoint = this.getLastPoint();
        lastPoint.x = motionEvent.getX();
        lastPoint.y = motionEvent.getY();
        ThreadHelper.cancel(this.g);
    }
    
    private boolean e(final MotionEvent motionEvent) {
        final PointF lastPoint = this.getLastPoint();
        return Math.abs(lastPoint.x - motionEvent.getX()) > this.getLongPressOffset() || Math.abs(lastPoint.y - motionEvent.getY()) > this.getLongPressOffset();
    }
    
    private void c() {
        final MediaPlayer a = this.a();
        if (a == null) {
            return;
        }
        a.start();
    }
    
    public void cameraStateChanged(final TuSdkStillCameraInterface tuSdkStillCameraInterface, final TuSdkStillCameraAdapter.CameraState cameraState) {
        if (cameraState == TuSdkStillCameraAdapter.CameraState.StateStarted) {
            this.a((PointF)null);
        }
        else {
            this.hiddenFaceViews();
        }
        if (tuSdkStillCameraInterface == null || !tuSdkStillCameraInterface.canSupportAutoFocus()) {
            return;
        }
    }
    
    protected void hiddenFaceViews() {
        final Iterator<View> iterator = this.mFaceViews.iterator();
        while (iterator.hasNext()) {
            this.showView(iterator.next(), false);
        }
    }
    
    protected abstract View buildFaceDetectionView();
    
    public void setCameraFaceDetection(final List<TuSdkFace> list, final TuSdkSize tuSdkSize) {
        this.hiddenFaceViews();
        if (list == null || tuSdkSize == null || list.isEmpty()) {
            return;
        }
        if (this.mFaceViews.size() < list.size()) {
            for (int i = 0; i < list.size() - this.mFaceViews.size(); ++i) {
                final View buildFaceDetectionView = this.buildFaceDetectionView();
                if (buildFaceDetectionView != null) {
                    this.showView(buildFaceDetectionView, false);
                    this.addView(buildFaceDetectionView);
                    this.mFaceViews.add(buildFaceDetectionView);
                }
            }
        }
        if (this.mFaceViews.size() < list.size()) {
            return;
        }
        final RectF rectRelativeImage = this.makeRectRelativeImage(tuSdkSize);
        int n = 0;
        final Iterator<TuSdkFace> iterator = list.iterator();
        while (iterator.hasNext()) {
            final Rect transforRect = this.transforRect(iterator.next().rect, rectRelativeImage);
            if (transforRect == null) {
                continue;
            }
            final View view = this.mFaceViews.get(n);
            this.setRect(view, transforRect);
            this.showView(view, true);
            if (n == 0) {
                this.a(transforRect);
            }
            ++n;
        }
    }
    
    protected final RectF makeRectRelativeImage(final TuSdkSize tuSdkSize) {
        if (tuSdkSize == null || !tuSdkSize.isSize()) {
            return null;
        }
        return RectHelper.makeRectWithAspectRatioOutsideRect(tuSdkSize, this.getRegionRectF());
    }
    
    protected final Rect transforRect(final RectF rectF, final RectF rectF2) {
        if (rectF == null || rectF2 == null) {
            return null;
        }
        final Rect rect = new Rect();
        rect.left = (int)(rectF.left * rectF2.width() - rectF2.left);
        rect.right = (int)(rectF.right * rectF2.width() - rectF2.left);
        rect.top = (int)(rectF.top * rectF2.height() - rectF2.top);
        rect.bottom = (int)(rectF.bottom * rectF2.height() - rectF2.top);
        return rect;
    }
    
    private void a(final Rect rect) {
        final long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (rect == null || timeInMillis - this.a < 5000L) {
            return;
        }
        this.a = timeInMillis;
        this.c = new PointF((float)rect.centerX(), (float)rect.centerY());
        if (this.b == null) {
            return;
        }
        if (this.b.canSupportAutoFocus()) {
            this.notifyFoucs(this.getLastPoint(), false);
        }
        else {
            this.b.autoMetering(this.getLastPoint());
        }
    }
    
    @Deprecated
    protected void setAutoContinueFocus(final boolean b) {
    }
}
