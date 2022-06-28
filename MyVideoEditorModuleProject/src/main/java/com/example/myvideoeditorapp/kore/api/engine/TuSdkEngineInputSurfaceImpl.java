// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesSurfaceReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.utils.TLog;

public class TuSdkEngineInputSurfaceImpl implements TuSdkEngineInputImage
{
    private TuSdkEngineOrientation a;
    private TuSdkEngineProcessor b;
    private _SelesSurfaceReceiver c;
    
    @Override
    public void setEngineRotation(final TuSdkEngineOrientation a) {
        if (a == null) {
            return;
        }
        this.a = a;
    }
    
    @Override
    public void bindEngineProcessor(final TuSdkEngineProcessor b) {
        if (b == null) {
            return;
        }
        this.b = b;
        if (this.c == null) {
            TLog.w("%s bindEngineProcessor has released.", "TuSdkEngineInputSurfaceImpl");
            return;
        }
        b.setHeaderNode(this.c);
    }
    
    @Override
    public void setTextureCoordinateBuilder(final SelesVerticeCoordinateCorpBuilder textureCoordinateBuilder) {
        if (this.c == null) {
            return;
        }
        this.c.setTextureCoordinateBuilder(textureCoordinateBuilder);
    }
    
    @Override
    public void setPreCropRect(final RectF preCropRect) {
        if (this.c == null) {
            return;
        }
        this.c.setPreCropRect(preCropRect);
    }
    
    @Override
    public SelesOutput getOutput() {
        return this.c;
    }
    
    public TuSdkEngineInputSurfaceImpl() {
        this.c = new _SelesSurfaceReceiver();
    }
    
    @Override
    public void release() {
        if (this.c != null) {
            this.c.destroy();
            this.c = null;
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    @Override
    public void processFrame(final int surface, final int n, final int n2, final byte[] array, final long n3) {
        if (this.c == null) {
            TLog.w("%s processFrame has released.", "TuSdkEngineInputSurfaceImpl");
            return;
        }
        if (this.b == null) {
            TLog.w("%s processFrame need bindEngineProcessor first.", "TuSdkEngineInputSurfaceImpl");
            return;
        }
        this.b.willProcessFrame(n3);
        if (this.a != null) {
            this.c.setInputRotation(this.a.getInputRotation());
            this.c.setInputSize(this.a.getInputSize());
        }
        else {
            this.c.setInputSize(TuSdkSize.create(n, n2));
        }
        this.c.setSurface(surface);
        if (!this.c.isInited()) {
            this.c.initInGLThread();
        }
        this.c.newFrameReadyInGLThread(n3);
    }
    
    private class _SelesSurfaceReceiver extends SelesSurfaceReceiver
    {
        public _SelesSurfaceReceiver() {
        }
        
        @Override
        protected void initSurfaceFBO() {
        }
        
        public void setSurface(final int n) {
            if (this.mSurfaceFBO == null || this.mSurfaceFBO.getTexture() != n) {
                this.mSurfaceFBO = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.HOLDER, this.mInputTextureSize, n);
            }
        }
    }
}
