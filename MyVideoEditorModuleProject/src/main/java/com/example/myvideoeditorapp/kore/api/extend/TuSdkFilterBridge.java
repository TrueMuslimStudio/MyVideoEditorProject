// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api.extend;

import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class TuSdkFilterBridge extends SelesOutInput
{
    private TuSdkSurfaceDraw a;
    private SelesFramebuffer b;
    private ByteBuffer c;
    private final List<SelesContext.SelesInput> d;
    
    public TuSdkFilterBridge() {
        this.d = new ArrayList<SelesContext.SelesInput>();
    }
    
    public void setSurfaceDraw(final TuSdkSurfaceDraw a) {
        this.a = a;
    }
    
    @Override
    public void setInputSize(final TuSdkSize mInputTextureSize, final int n) {
        if (mInputTextureSize == null) {
            return;
        }
        this.mInputTextureSize = mInputTextureSize;
    }
    
    @Override
    public void setInputFramebuffer(final SelesFramebuffer b, final int n) {
        if (b == null) {
            return;
        }
        (this.b = b).lock();
    }
    
    @Override
    protected void onDestroy() {
        if (this.b != null) {
            this.b.unlock();
            this.b = null;
        }
        if (this.mOutputFramebuffer != null) {
            this.mOutputFramebuffer.unlock();
            this.mOutputFramebuffer = null;
        }
    }
    
    @Override
    public void newFrameReady(final long n, final int n2) {
        this.runPendingOnDrawTasks();
        final SelesFramebuffer b = this.b;
        final TuSdkSize mInputTextureSize = this.mInputTextureSize;
        if (b == null || !mInputTextureSize.isSize()) {
            return;
        }
        int n3 = b.getTexture();
        if (this.a != null) {
            final IntBuffer captureImageBufferFromFramebufferContents = b.captureImageBufferFromFramebufferContents();
            final int[] array = new int[captureImageBufferFromFramebufferContents.limit()];
            captureImageBufferFromFramebufferContents.position(0);
            captureImageBufferFromFramebufferContents.get(array);
            captureImageBufferFromFramebufferContents.position(0);
            if (this.c == null || this.c.capacity() != captureImageBufferFromFramebufferContents.capacity()) {
                this.c = ByteBuffer.allocate(captureImageBufferFromFramebufferContents.limit() * 4);
            }
            this.c.asIntBuffer().put(array);
            n3 = this.a.onDrawFrame(n3, this.c.array(), mInputTextureSize.width, mInputTextureSize.height, n);
        }
        if (n3 == b.getTexture()) {
            this.mOutputFramebuffer = b;
        }
        else {
            b.unlock();
            this.mOutputFramebuffer = SelesContext.sharedFramebufferCache().fetchFramebuffer(SelesFramebuffer.SelesFramebufferMode.HOLDER, mInputTextureSize, n3);
        }
        this.b = null;
        this.a(n, b, mInputTextureSize);
    }
    
    private void a(final long n, final SelesFramebuffer selesFramebuffer, final TuSdkSize tuSdkSize) {
        this.d.clear();
        for (final SelesContext.SelesInput selesInput : this.mTargets) {
            if (!selesInput.isEnabled()) {
                continue;
            }
            if (selesInput == this.getTargetToIgnoreForUpdates()) {
                continue;
            }
            this.d.add(selesInput);
            final int intValue = this.mTargetTextureIndices.get(this.mTargets.indexOf(selesInput));
            this.setInputFramebufferForTarget(selesInput, intValue);
            selesInput.setInputSize(tuSdkSize, intValue);
        }
        if (this.mOutputFramebuffer != null) {
            this.mOutputFramebuffer.unlock();
            this.mOutputFramebuffer = null;
        }
        for (final SelesContext.SelesInput selesInput2 : this.d) {
            selesInput2.newFrameReady(n, this.mTargetTextureIndices.get(this.mTargets.indexOf(selesInput2)));
        }
        if (this.a != null) {
            this.a.onDrawFrameCompleted();
        }
    }
    
    @Override
    public int nextAvailableTextureIndex() {
        return 0;
    }
    
    @Override
    public void setInputRotation(final ImageOrientation imageOrientation, final int n) {
    }
    
    @Override
    public TuSdkSize maximumOutputSize() {
        return this.mInputTextureSize;
    }
    
    @Override
    public void endProcessing() {
    }
    
    @Override
    public boolean wantsMonochromeInput() {
        return false;
    }
    
    @Override
    public void setCurrentlyReceivingMonochromeInput(final boolean b) {
    }
}
