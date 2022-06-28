// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.components.camera;

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
import com.example.myvideoeditorapp.kore.media.camera.TuSdkCamera;
import com.example.myvideoeditorapp.kore.media.camera.TuSdkCameraFocus;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.struct.ViewSize;
import com.example.myvideoeditorapp.kore.utils.RectHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.CameraConfigs;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkFace;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkStillCameraAdapter;
import com.example.myvideoeditorapp.kore.view.TuSdkRelativeLayout;
import com.example.myvideoeditorapp.tv.core.seles.sources.SelesVideoCameraInterface;
import com.example.myvideoeditorapp.tv.core.utils.hardware.TuSDKVideoCameraFocusViewInterface;
import com.example.myvideoeditorapp.tv.core.utils.hardware.TuSdkRecorderVideoCamera;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public abstract class TuSdkVideoFocusTouchViewBase extends TuSdkRelativeLayout implements TuSDKVideoCameraFocusViewInterface
{
    public static final long SamplingDistance = 2000L;
    public static final float SamplingRange = 50.0f;
    public static final long FaceDetectionDistance = 5000L;
    private long a;
    private TuSdkRecorderVideoCamera b;
    private PointF c;
    private MediaPlayer d;
    private boolean e;
    private boolean f;
    private boolean g;
    private RectF h;
    private boolean i;
    private boolean j;
    protected GestureListener listener;
    private boolean k;
    private float l;
    private float m;
    protected final List<View> mFaceViews;
    
    public TuSdkVideoFocusTouchViewBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a = 0L;
        this.j = true;
        this.mFaceViews = new ArrayList<View>();
    }
    
    public TuSdkVideoFocusTouchViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.a = 0L;
        this.j = true;
        this.mFaceViews = new ArrayList<View>();
    }
    
    public TuSdkVideoFocusTouchViewBase(final Context context) {
        super(context);
        this.a = 0L;
        this.j = true;
        this.mFaceViews = new ArrayList<View>();
    }
    
    public abstract void setRangeViewFoucsState(final boolean p0);
    
    public TuSdkRecorderVideoCamera getCamera() {
        return this.b;
    }
    
    public void setCamera(final TuSdkRecorderVideoCamera b) {
        this.b = b;
    }
    
    public boolean isDisableFocusBeep() {
        return this.f;
    }
    
    public void setDisableFocusBeep(final boolean f) {
        this.f = f;
    }
    
    public boolean isDisableContinueFoucs() {
        return this.g;
    }
    
    public void setDisableContinueFoucs(final boolean g) {
        this.g = g;
    }
    
    public boolean isEnableFaceFeatureDetection() {
        return this.i;
    }
    
    public void setEnableFaceFeatureDetection(final boolean i) {
        this.i = i;
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
    
    public void setRegionPercent(final RectF h) {
        this.h = h;
    }
    
    public RectF getRegionPercent() {
        if (this.h == null) {
            this.h = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        }
        return this.h;
    }
    
    public void viewWillDestory() {
        super.viewWillDestory();
        this.b = null;
        if (this.d != null) {
            this.d.release();
            this.d = null;
        }
    }
    
    protected boolean notifyFoucs(final PointF pointF, final boolean b) {
        if (this.b == null || !this.b.canSupportAutoFocus() || !this.b(pointF)) {
            return false;
        }
        this.b.autoFocus(CameraConfigs.CameraAutoFocus.Auto, this.getRatioPoint(pointF), (TuSdkCameraFocus.TuSdkCameraFocusListener)new TuSdkCameraFocus.TuSdkCameraFocusListener() {
            public void onFocusStart(final TuSdkCameraFocus tuSdkCameraFocus) {
            }
            
            public void onAutoFocus(final boolean b, final TuSdkCameraFocus tuSdkCameraFocus) {
                TuSdkVideoFocusTouchViewBase.this.a(b);
            }
        });
        return true;
    }
    
    private void a(final boolean rangeViewFoucsState) {
        this.setRangeViewFoucsState(rangeViewFoucsState);
        this.b();
    }
    
    protected RectF getRegionRectF() {
        final ViewSize create = ViewSize.create((View)this);
        return new RectF(this.getRegionPercent().left * ((TuSdkSize)create).width, this.getRegionPercent().top * ((TuSdkSize)create).height, this.getRegionPercent().right * ((TuSdkSize)create).width, this.getRegionPercent().bottom * ((TuSdkSize)create).height);
    }
    
    private boolean b(final PointF pointF) {
        return this.getRegionRectF().contains(pointF.x, pointF.y);
    }
    
    public void isShowFoucusView(final boolean j) {
        this.j = j;
    }
    
    public void setGestureListener(final GestureListener listener) {
        this.listener = listener;
    }
    
    @SuppressLint({ "ClickableViewAccessibility" })
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return super.onTouchEvent(motionEvent);
        }
        this.a = Calendar.getInstance().getTimeInMillis();
        switch (motionEvent.getAction()) {
            case 0: {
                this.b(motionEvent);
                break;
            }
            case 1: {
                this.c(motionEvent);
                break;
            }
            case 2: {
                this.d(motionEvent);
                break;
            }
            default: {
                this.e = true;
                this.k = false;
                break;
            }
        }
        return true;
    }
    
    private void a(final MotionEvent motionEvent) {
        final PointF lastPoint = this.getLastPoint();
        lastPoint.x = motionEvent.getX();
        lastPoint.y = motionEvent.getY();
    }
    
    private void b(final MotionEvent motionEvent) {
        this.e = false;
        this.k = true;
        final float x = motionEvent.getX();
        this.l = x;
        this.m = x;
        this.a(motionEvent);
    }
    
    private void c(final MotionEvent motionEvent) {
        this.k = false;
        if (this.e) {
            return;
        }
        this.e = true;
        this.a(motionEvent);
        if (this.j && Math.abs(this.m - this.l) < 50.0f && this.canShowFocusView(this.getLastPoint())) {
            this.showFocusView(this.getLastPoint());
            this.notifyFoucs(this.getLastPoint(), false);
        }
        if (this.listener != null && Math.abs(this.m - this.l) < 50.0f) {
            this.listener.onClick();
        }
    }
    
    public abstract void showFocusView(final PointF p0);
    
    private void d(final MotionEvent motionEvent) {
        this.m = motionEvent.getX();
        if (this.m - this.l > 0.0f && Math.abs(this.m - this.l) > 50.0f && this.k) {
            this.k = false;
            if (this.listener != null) {
                this.listener.onRightGesture();
            }
        }
        else if (this.m - this.l < 0.0f && Math.abs(this.m - this.l) > 50.0f && this.k) {
            this.k = false;
            if (this.listener != null) {
                this.listener.onLeftGesture();
            }
        }
        if (this.e) {
            return;
        }
        this.a(motionEvent);
    }
    
    protected final PointF getRatioPoint(final PointF pointF) {
        if (pointF == null) {
            return null;
        }
        final PointF pointF2 = new PointF();
        final float n = (float)this.getWidth();
        final float n2 = (float)this.getHeight();
        if (this.b.isFrontFacingCameraPresent()) {
            pointF2.x = 1.0f - Math.min(1.0f, pointF.x / n);
            pointF2.y = 1.0f - Math.min(1.0f, pointF.y / n2);
        }
        else {
            pointF2.x = Math.max(0.0f, pointF.x / n);
            pointF2.y = Math.max(0.0f, pointF.y / n2);
        }
        if (this.getCamera().getRegionHandler().getRatio() > 0.0f) {
            final PointF pointF3 = pointF2;
            pointF3.y -= this.getCamera().getRegionHandler().getCenterRectPercent().bottom - this.getRegionPercent().bottom;
        }
        return pointF2;
    }
    
    protected boolean canShowFocusView(final PointF pointF) {
        final RectF rectF = new RectF();
        final RectF regionPercent = this.getRegionPercent();
        final float n = (float)this.getWidth();
        final float n2 = (float)this.getHeight();
        rectF.left = n * regionPercent.left;
        rectF.right = n * regionPercent.right;
        rectF.top = n2 * regionPercent.top;
        rectF.bottom = n2 * regionPercent.bottom;
        return rectF.contains(pointF.x, pointF.y);
    }
    
    private void b() {
        final MediaPlayer a = this.a();
        if (a == null) {
            return;
        }
        a.start();
    }
    
    public void cameraStateChanged(final boolean b, final TuSdkCamera tuSdkCamera, final TuSdkCamera.TuSdkCameraStatus tuSdkCameraStatus) {
        if (tuSdkCameraStatus == TuSdkCamera.TuSdkCameraStatus.CAMERA_START_PREVIEW) {
            this.a((PointF)null);
        }
        else {
            this.hiddenFaceViews();
        }
        if (tuSdkCamera == null || !b) {
            return;
        }
    }
    
    public void cameraStateChanged(final SelesVideoCameraInterface selesVideoCameraInterface, final TuSdkStillCameraAdapter.CameraState cameraState) {
        if (cameraState == TuSdkStillCameraAdapter.CameraState.StateStarted) {
            this.a((PointF)null);
        }
        else {
            this.hiddenFaceViews();
        }
        if (selesVideoCameraInterface == null || !selesVideoCameraInterface.canSupportAutoFocus()) {
            return;
        }
    }
    
    protected void hiddenFaceViews() {
        final Iterator<View> iterator = this.mFaceViews.iterator();
        while (iterator.hasNext()) {
            this.showView((View)iterator.next(), false);
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
        if (this.mFaceViews.size() > 0 && this.mFaceViews.size() < list.size()) {
            return;
        }
        final RectF rectRelativeImage = this.makeRectRelativeImage(tuSdkSize);
        int n = 0;
        final Iterator<TuSdkFace> iterator = list.iterator();
        while (iterator.hasNext()) {
            final Rect a = this.a(iterator.next().rect, rectRelativeImage);
            if (a == null) {
                continue;
            }
            View view = null;
            if (this.mFaceViews.size() > 0) {
                view = this.mFaceViews.get(n);
            }
            if (view != null) {
                this.setRect(view, a);
                this.showView(view, true);
            }
            if (n == 0) {
                this.a(a);
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
    
    private Rect a(final RectF rectF, final RectF rectF2) {
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
    }
    
    @Deprecated
    protected void setAutoContinueFocus(final boolean b) {
    }
    
    public interface GestureListener
    {
        void onLeftGesture();
        
        void onRightGesture();
        
        void onClick();
    }
}
