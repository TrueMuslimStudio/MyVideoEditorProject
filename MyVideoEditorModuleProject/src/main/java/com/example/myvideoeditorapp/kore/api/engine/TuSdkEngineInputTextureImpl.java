// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesTextureReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.sources.SelesTextureReceiver;

public class TuSdkEngineInputTextureImpl implements TuSdkEngineInputImage
{
    private TuSdkEngineOrientation a;
    private TuSdkEngineProcessor b;
    private SelesTextureReceiver c;
    private SelesFramebuffer d;
    
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
            TLog.w("%s bindEngineProcessor has released.", "TuSdkEngineInputTextureImpl");
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
    
    public TuSdkEngineInputTextureImpl() {
        this.c = new SelesTextureReceiver();
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
    public void processFrame(final int n, final int n2, final int n3, final byte[] array, final long n4) {
        if (this.c == null) {
            TLog.w("%s processFrame has released.", "TuSdkEngineInputTextureImpl");
            return;
        }
        if (this.b == null) {
            TLog.w("%s processFrame need bindEngineProcessor first.", "TuSdkEngineInputTextureImpl");
            return;
        }
        this.b.willProcessFrame(n4);
        if (this.d == null || this.d.getTexture() != n) {
            this.d = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.HOLDER, null, n);
        }
        this.c.setInputFramebuffer(this.d, 0);
        if (this.a != null) {
            this.c.setInputRotation(this.a.getInputRotation(), 0);
            this.c.setInputSize(this.a.getInputSize(), 0);
        }
        else {
            this.c.setInputSize(TuSdkSize.create(n2, n3), 0);
        }
        this.c.newFrameReadyInGLThread(n4);
    }
}
