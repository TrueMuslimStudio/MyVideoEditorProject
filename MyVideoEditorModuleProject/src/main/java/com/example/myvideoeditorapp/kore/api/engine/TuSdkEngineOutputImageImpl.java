// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.engine;

import com.example.myvideoeditorapp.kore.secret.ColorSpaceConvert;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.output.SelesOffscreen;
import com.example.myvideoeditorapp.kore.seles.output.SelesTerminalFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.type.ColorFormatType;
import com.example.myvideoeditorapp.kore.utils.TLog;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class TuSdkEngineOutputImageImpl implements TuSdkEngineOutputImage
{
    private TuSdkEngineOrientation a;
    private SelesTerminalFilter b;
    private SelesOffscreen c;
    private boolean d;
    private TuSdkSize e;
    private ByteBuffer f;
    private List<ByteBuffer> g;
    private Object h;
    private ColorFormatType i;
    private SelesOffscreen.SelesOffscreenDelegate j;

    public void setEngineRotation(TuSdkEngineOrientation var1) {
        this.a = var1;
    }

    public List<SelesContext.SelesInput> getInputs() {
        ArrayList var1 = new ArrayList(2);
        if (this.c != null) {
            var1.add(this.c);
        }

        if (this.b != null) {
            var1.add(this.b);
        }

        return var1;
    }
    
    public TuSdkEngineOutputImageImpl() {
        this.h = new Object();
        this.i = ColorFormatType.NV21;
        this.j = new SelesOffscreen.SelesOffscreenDelegate() {
            @Override
            public boolean onFrameRendered(final SelesOffscreen selesOffscreen) {
                if (!TuSdkEngineOutputImageImpl.this.a()) {
                    return false;
                }
                final IntBuffer renderBuffer = selesOffscreen.renderBuffer();
                final List c;
                final ByteBuffer d;
                synchronized (TuSdkEngineOutputImageImpl.this.h) {
                    c = TuSdkEngineOutputImageImpl.this.g;
                    d = TuSdkEngineOutputImageImpl.this.f;
                }
                if (renderBuffer == null || c == null || c.size() < 1) {
                    return true;
                }
                final ByteBuffer byteBuffer = (ByteBuffer) c.remove(0);
                byteBuffer.position(0);
                TuSdkEngineOutputImageImpl.this.a(renderBuffer.array(), byteBuffer.array(), TuSdkEngineOutputImageImpl.this.i);
                synchronized (TuSdkEngineOutputImageImpl.this.h) {
                    TuSdkEngineOutputImageImpl.this.f = byteBuffer;
                    if (d != null) {
                        c.add(d);
                    }
                }
                return true;
            }
        };
        this.b = new SelesTerminalFilter();
    }
    
    @Override
    public void release() {
        if (this.b != null) {
            this.b.destroy();
            this.b = null;
        }
        if (this.c != null) {
            this.c.setDelegate(null);
            this.c.setEnabled(false);
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
    public void willProcessFrame(final long n) {
        if (this.a == null || this.b == null) {
            return;
        }
        this.b.setInputRotation(this.a.getOutputOrientation(), 0);
    }
    
    @Override
    public int getTerminalTexture() {
        if (this.b == null || this.b.framebufferForOutput() == null) {
            return -1;
        }
        return this.b.framebufferForOutput().getTexture();
    }
    
    @Override
    public SelesFramebuffer getTerminalFrameBuffer() {
        if (this.b == null || this.b.framebufferForOutput() == null) {
            return null;
        }
        return this.b.framebufferForOutput();
    }
    
    @Override
    public void setYuvOutputImageFormat(final ColorFormatType i) {
        if (i == null) {
            return;
        }
        this.i = i;
    }
    
    @Override
    public void setEnableOutputYUVData(final boolean d) {
        this.d = d;
        if (this.d && this.c == null) {
            (this.c = new SelesOffscreen()).setDelegate(this.j);
        }
        if (this.d) {
            this.c.startWork();
        }
        else {
            this.c.setEnabled(false);
        }
    }
    
    @Override
    public void snatchFrame(final byte[] dst) {
        if (!this.a()) {
            return;
        }
        final ByteBuffer f;
        synchronized (this.h) {
            f = this.f;
        }
        if (f == null || dst.length != f.capacity()) {
            return;
        }
        this.c.setInputRotation(this.a.getYuvOutputOrienation(), 0);
        f.position(0);
        f.get(dst);
    }
    
    private boolean a() {
        if (!this.d || this.c == null) {
            return false;
        }
        if (this.a == null) {
            TLog.w("%s checkBuffer need setEngineRotation first.", "TuSdkEngineOutputImageImpl");
            return false;
        }
        if (this.a.getInputSize().equals(this.e) && this.g != null) {
            return true;
        }
        synchronized (this.h) {
            this.g = null;
            this.f = null;
            this.e = this.a.getInputSize();
            final ArrayList<ByteBuffer> g = new ArrayList<ByteBuffer>(3);
            final int capacity = this.e.width * this.e.height * 3 / 2;
            for (int i = 0; i < 3; ++i) {
                g.add(ByteBuffer.allocate(capacity));
            }
            this.g = g;
        }
        return true;
    }
    
    private void a(final int[] array, final byte[] array2, final ColorFormatType colorFormatType) {
        if (this.c == null) {
            return;
        }
        final TuSdkSize sizeOfFBO = this.c.sizeOfFBO();
        if (colorFormatType == ColorFormatType.NV21) {
            ColorSpaceConvert.rgbaToNv21(array, sizeOfFBO.width, sizeOfFBO.height, array2);
        }
        else if (colorFormatType == ColorFormatType.I420) {
            ColorSpaceConvert.rgbaToI420(array, sizeOfFBO.width, sizeOfFBO.height, array2);
        }
        else if (colorFormatType == ColorFormatType.YV12) {
            ColorSpaceConvert.rgbaToYv12(array, sizeOfFBO.width, sizeOfFBO.height, array2);
        }
        else {
            TLog.e("%s Unsupported image format", "TuSdkEngineOutputImageImpl");
        }
    }
}
