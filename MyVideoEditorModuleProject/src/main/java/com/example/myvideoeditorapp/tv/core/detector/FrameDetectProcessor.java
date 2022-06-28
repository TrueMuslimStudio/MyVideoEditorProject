// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.detector;

import android.graphics.PointF;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.listener.TuSdkOrientationEventListener;
import com.example.myvideoeditorapp.kore.seles.output.SelesOffscreenRotate;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;

import java.nio.Buffer;

public class FrameDetectProcessor
{
    private InterfaceOrientation a;
    private TuSdkOrientationEventListener b;
    private SelesOffscreenRotate c;
    private boolean d;
    private TuSdkSize e;
    private boolean f;
    private boolean g;
    private FrameDetectProcessorDelegate h;
    private TuSdkOrientationEventListener.TuSdkOrientationDelegate i;
    private SelesOffscreenRotate.SelesOffscreenRotateDelegate j;
    
    public FrameDetectProcessor() {
        this(0);
    }
    
    public FrameDetectProcessor(final int n) {
        this.a = InterfaceOrientation.Portrait;
        this.d = false;
        this.i = (TuSdkOrientationEventListener.TuSdkOrientationDelegate)new TuSdkOrientationEventListener.TuSdkOrientationDelegate() {
            public void onOrientationChanged(final InterfaceOrientation interfaceOrientation) {
                if (FrameDetectProcessor.this.h != null) {
                    FrameDetectProcessor.this.h.onOrientationChanged(interfaceOrientation);
                }
            }
        };
        this.j = (SelesOffscreenRotate.SelesOffscreenRotateDelegate)new SelesOffscreenRotate.SelesOffscreenRotateDelegate() {
            public boolean onFrameRendered(final SelesOffscreenRotate selesOffscreenRotate) {
                if (!FrameDetectProcessor.this.f) {
                    return true;
                }
                final float angle = selesOffscreenRotate.getAngle();
                selesOffscreenRotate.setAngle((float)FrameDetectProcessor.this.b());
                FrameDetectProcessor.this.a(selesOffscreenRotate.outputFrameSize(), angle, selesOffscreenRotate.readBuffer());
                return true;
            }
        };
        (this.b = new TuSdkOrientationEventListener(TuSdkContext.context())).setDelegate(this.i, (TuSdkOrientationEventListener.TuSdkOrientationDegreeDelegate)null);
        this.b.enable();
        ThreadHelper.runThread((Runnable)new Runnable() {
            @Override
            public void run() {
                TuSdkFaceDetector.init();
                FrameDetectProcessor.this.g = true;
            }
        });
    }
    
    public FrameDetectProcessorDelegate getDelegate() {
        return this.h;
    }
    
    public void setDelegate(final FrameDetectProcessorDelegate h) {
        this.h = h;
    }
    
    public boolean inited() {
        return this.g;
    }
    
    public static void setDetectScale(final float detectScale) {
        TuSdkFaceDetector.setDetectScale(detectScale);
    }
    
    public void destroy() {
        this.h = null;
        if (this.b != null) {
            this.b.disable();
            this.b = null;
        }
        this.destroyOutput();
    }
    
    public void destroyOutput() {
        if (this.c == null) {
            return;
        }
        this.c.setDelegate((SelesOffscreenRotate.SelesOffscreenRotateDelegate)null);
        this.c.destroy();
        this.c = null;
    }
    
    public SelesOffscreenRotate getSelesRotateShotOutput() {
        if (this.c != null) {
            return this.c;
        }
        (this.c = new SelesOffscreenRotate()).setSyncOutput(this.d);
        this.a();
        this.c.setDelegate(this.j);
        return this.c;
    }
    
    public void setSyncOutput(final boolean b) {
        this.d = b;
        if (this.c != null) {
            this.c.setSyncOutput(b);
        }
    }
    
    public void setInputTextureSize(final TuSdkSize e) {
        if (e == null || e.equals((Object)this.e)) {
            return;
        }
        this.e = e;
        this.a();
    }
    
    private void a() {
        if (this.c == null) {
            return;
        }
        final int n = (this.e == null) ? 512 : this.e.maxSide();
        final int n2 = (n > 512) ? 512 : n;
        this.c.forceProcessingAtSize(TuSdkSize.create(n2, n2));
    }
    
    public void setEnabled(final boolean b) {
        this.f = b;
        if (this.c != null) {
            this.c.setEnabled(b);
        }
    }
    
    public void setInterfaceOrientation(final InterfaceOrientation a) {
        this.a = a;
    }
    
    public int getDeviceAngle() {
        return this.b.getDeviceAngle();
    }
    
    private int b() {
        if (this.a == null) {
            return this.getDeviceAngle();
        }
        return this.getDeviceAngle() + this.a.getDegree();
    }
    
    private void a(final TuSdkSize tuSdkSize, final float n, final Buffer buffer) {
        if (buffer == null) {
            return;
        }
        System.currentTimeMillis();
        this.a(TuSdkFaceDetector.markFaceVideo(tuSdkSize.width, tuSdkSize.height, -Math.toRadians(n), true, buffer), tuSdkSize, n, false);
    }
    
    private void a(final FaceAligment[] array, final TuSdkSize tuSdkSize, final float n, final boolean b) {
        if (this.h == null || this.e == null) {
            return;
        }
        if (array != null) {
            if (array.length >= 1) {
                final float ratioFloat = this.e.getRatioFloat();
                float n2 = 0.0f;
                float n3 = 0.0f;
                float n4 = 1.0f;
                float n5 = 1.0f;
                if (ratioFloat < 1.0f) {
                    n2 = (1.0f - ratioFloat) * 0.5f;
                    n4 = 1.0f / ratioFloat;
                }
                else {
                    n3 = (1.0f - 1.0f / ratioFloat) * 0.5f;
                    n5 = 1.0f * ratioFloat;
                }
                for (final FaceAligment faceAligment : array) {
                    final RectF rect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
                    rect.left = (faceAligment.rect.left - n2) * n4;
                    rect.top = (faceAligment.rect.top - n3) * n5;
                    rect.right = (faceAligment.rect.right - n2) * n4;
                    rect.bottom = (faceAligment.rect.bottom - n3) * n5;
                    if (faceAligment.getOrginMarks() != null) {
                        for (final PointF pointF2 : faceAligment.getOrginMarks()) {
                            final PointF pointF = pointF2;
                            pointF.x = (pointF2.x - n2) * n4;
                            pointF.y = (pointF2.y - n3) * n5;
                        }
                        faceAligment.rect = rect;
                        faceAligment.setOrginMarks(faceAligment.getOrginMarks());
                        if (faceAligment.getEyeMarks() != null) {
                            for (final PointF pointF4 : faceAligment.getEyeMarks()) {
                                final PointF pointF3 = pointF4;
                                pointF3.x = (pointF4.x - n2) * n4;
                                pointF3.y = (pointF4.y - n3) * n5;
                            }
                            faceAligment.setEyeMarks(faceAligment.getEyeMarks());
                            if (faceAligment.getMouthMarks() != null) {
                                for (final PointF pointF6 : faceAligment.getMouthMarks()) {
                                    final PointF pointF5 = pointF6;
                                    pointF5.x = (pointF6.x - n2) * n4;
                                    pointF5.y = (pointF6.y - n3) * n5;
                                }
                                faceAligment.setMouthMarks(faceAligment.getMouthMarks());
                            }
                        }
                    }
                }
            }
        }
        if (this.getDelegate() != null) {
            this.getDelegate().onFrameDetectedResult(array, tuSdkSize, n, b);
        }
    }
    
    public FaceAligment[] syncProcessFrameData(final byte[] array, final TuSdkSize tuSdkSize, final int n, final double n2, final boolean b) {
        if (!this.inited()) {
            return null;
        }
        return TuSdkFaceDetector.markFaceGrayImage(tuSdkSize.width, tuSdkSize.height, n, n2, b, array);
    }
    
    public interface FrameDetectProcessorDelegate extends TuSdkOrientationEventListener.TuSdkOrientationDelegate
    {
        void onFrameDetectedResult(final FaceAligment[] p0, final TuSdkSize p1, final float p2, final boolean p3);
    }
}
