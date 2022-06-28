// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import android.graphics.RectF;

import com.example.myvideoeditorapp.kore.seles.extend.SelesVerticeCoordinateCorpBuilder;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutput;
import com.example.myvideoeditorapp.kore.seles.sources.SelesTextureReceiver;
import com.example.myvideoeditorapp.kore.seles.sources.SelesYuvDataReceiver;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;

public class TuSdkEngineInputYUVDataImpl implements TuSdkEngineInputImage
{
    private TuSdkEngineOrientation a;
    private TuSdkEngineProcessor b;
    private SelesYuvDataReceiver c;
    private SelesTextureReceiver d;
    
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
        if (this.d == null) {
            TLog.w("%s bindEngineProcessor has released.", "TuSdkEngineInputYUVDataImpl");
            return;
        }
        b.setHeaderNode(this.c);
    }
    
    @Override
    public void setTextureCoordinateBuilder(final SelesVerticeCoordinateCorpBuilder textureCoordinateBuilder) {
        if (this.d == null) {
            return;
        }
        this.d.setTextureCoordinateBuilder(textureCoordinateBuilder);
    }
    
    @Override
    public void setPreCropRect(final RectF preCropRect) {
        if (this.d == null) {
            return;
        }
        this.d.setPreCropRect(preCropRect);
    }
    
    @Override
    public SelesOutput getOutput() {
        return this.d;
    }
    
    public TuSdkEngineInputYUVDataImpl() {
        this.c = new SelesYuvDataReceiver();
        this.d = new SelesTextureReceiver();
        this.c.addTarget(this.d, 0);
    }
    
    @Override
    public void release() {
        if (this.c != null) {
            this.c.destroy();
            this.c = null;
        }
        if (this.d != null) {
            this.d.destroy();
            this.d = null;
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    @Override
    public void processFrame(final int n, final int n2, final int n3, final byte[] array, final long n4) {
        if (array == null) {
            TLog.w("%s processFrame need yuv data.", "TuSdkEngineInputYUVDataImpl");
            return;
        }
        if (this.c == null) {
            TLog.w("%s processFrame has released.", "TuSdkEngineInputYUVDataImpl");
            return;
        }
        if (this.b == null) {
            TLog.w("%s processFrame need bindEngineProcessor first.", "TuSdkEngineInputYUVDataImpl");
            return;
        }
        this.b.willProcessFrame(n4);
        if (this.a != null) {
            this.c.setInputRotation(this.a.getInputRotation());
            this.c.setInputSize(this.a.getInputSize());
        }
        else {
            this.c.setInputSize(TuSdkSize.create(n2, n3));
        }
        this.c.processFrameData(array);
        this.c.newFrameReady(n4);
    }
}
