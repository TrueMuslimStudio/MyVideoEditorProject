// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.sources;

import com.example.myvideoeditorapp.kore.utils.hardware.InterfaceOrientation;
import android.graphics.Bitmap;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import android.opengl.GLSurfaceView;
import android.content.Context;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.output.SelesViewInterface;
import com.example.myvideoeditorapp.kore.seles.SelesContext;

public class SelesVideoCameraProcessor extends SelesVideoCameraBase
{
    private final SelesContext.SelesInput c;
    private final SelesViewInterface d;
    private FilterWrap e;
    private boolean f;
    private SelesVideoCameraProcessorEngine g;
    static final /* synthetic */ boolean b;
    
    public final boolean isFilterChanging() {
        return this.f;
    }
    
    public void setCameraEngine(final SelesVideoCameraProcessorEngine g) {
        super.setCameraEngine(this.g = g);
    }
    
    @Deprecated
    @Override
    public void setCameraEngine(final SelesVideoCameraEngine selesVideoCameraEngine) {
    }
    
    public <T extends SelesContext.SelesInput> SelesVideoCameraProcessor(final Context context, final T c) {
        super(context);
        if (!SelesVideoCameraProcessor.b && c == null) {
            throw new AssertionError();
        }
        ((SelesViewInterface)c).setRenderer((GLSurfaceView.Renderer)this);
        this.c = (SelesContext.SelesInput)c;
        this.d = (SelesViewInterface)c;
        (this.e = FilterLocalPackage.shared().getFilterWrap(null)).bindWithCameraView(this.c);
        this.e.addOrgin(this);
    }
    
    @Override
    protected void onMainThreadStart() {
        super.onMainThreadStart();
        if (this.d != null) {
            this.d.requestRender();
        }
    }
    
    @Override
    public void pauseCameraCapture() {
        super.pauseCameraCapture();
        this.a(false);
    }
    
    @Override
    public void resumeCameraCapture() {
        super.resumeCameraCapture();
        this.a(true);
    }
    
    @Override
    public void stopCameraCapture() {
        this.a(this.f = false);
        super.stopCameraCapture();
    }
    
    private void a(final boolean b) {
        if (this.d == null) {
            return;
        }
        if (b) {
            this.d.setRenderModeContinuously();
        }
        else {
            this.d.setRenderModeDirty();
        }
    }
    
    public void setRendererFPS(final int rendererFPS) {
        if (rendererFPS < 1 || this.d == null) {
            return;
        }
        this.d.setRendererFPS(rendererFPS);
    }
    
    public void switchFilter(final String s) {
        if (s == null || this.isFilterChanging() || !this.isCapturing() || this.e.equalsCode(s)) {
            return;
        }
        this.f = true;
        this.runOnDraw(new Runnable() {
            @Override
            public void run() {
                SelesVideoCameraProcessor.this.a(s);
            }
        });
    }
    
    private void a(final String s) {
        final FilterWrap filterWrap = FilterLocalPackage.shared().getFilterWrap(s);
        this.e.removeOrgin(this);
        filterWrap.bindWithCameraView(this.c);
        filterWrap.addOrgin(this);
        filterWrap.processImage();
        this.e.destroy();
        this.e = filterWrap;
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                SelesVideoCameraProcessor.this.a();
            }
        });
    }
    
    private void a() {
        if (this.g != null) {
            this.g.onFilterSwitched(this.e.getFilter());
            this.e.rotationTextures(this.g.deviceOrientation());
        }
        this.f = false;
    }
    
    public Bitmap processCaptureImage(Bitmap imageFromCurrentlyProcessedOutput) {
        if (imageFromCurrentlyProcessedOutput == null || imageFromCurrentlyProcessedOutput.isRecycled()) {
            return imageFromCurrentlyProcessedOutput;
        }
        final FilterWrap clone = this.e.clone();
        clone.processImage();
        final SelesPicture selesPicture = new SelesPicture(imageFromCurrentlyProcessedOutput, false);
        clone.addOrgin(selesPicture);
        selesPicture.processImage();
        imageFromCurrentlyProcessedOutput = selesPicture.imageFromCurrentlyProcessedOutput();
        return imageFromCurrentlyProcessedOutput;
    }
    
    static {
        b = !SelesVideoCameraProcessor.class.desiredAssertionStatus();
    }
    
    public interface SelesVideoCameraProcessorEngine extends SelesVideoCameraEngine
    {
        InterfaceOrientation deviceOrientation();
        
        void onFilterSwitched(final SelesOutInput p0);
    }
}
