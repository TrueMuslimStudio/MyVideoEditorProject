// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker;

import android.os.Build;
import java.util.concurrent.Executors;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import javax.microedition.khronos.egl.EGLContext;
import java.util.concurrent.ExecutorService;
import android.os.Handler;
import android.os.HandlerThread;
import com.example.myvideoeditorapp.kore.seles.egl.SelesEGL10Core;

public class DynamicStickerLoader
{
    private SelesEGL10Core a;
    private HandlerThread b;
    private Handler c;
    private ExecutorService d;
    
    public DynamicStickerLoader() {
        (this.b = new HandlerThread("com.tusdk.DynamicStickerLoader")).start();
    }
    
    public DynamicStickerLoader(final EGLContext eglContext) {
        this();
        this.c = new Handler(this.b.getLooper());
        this.a(eglContext);
    }
    
    private void a(final EGLContext eglContext) {
        this.c.post((Runnable)new Runnable() {
            @Override
            public void run() {
                DynamicStickerLoader.this.a = SelesEGL10Core.create(TuSdkSize.create(1, 1), eglContext);
            }
        });
    }
    
    public void finalize() throws Throwable {
        super.finalize();
    }
    
    public void loadImage(final Runnable runnable) {
        if (this.d == null) {
            this.d = Executors.newCachedThreadPool();
        }
        this.d.execute(runnable);
    }
    
    public void uploadTexture(final Runnable runnable) {
        if (this.c != null) {
            this.c.post(runnable);
        }
    }
    
    public void destroy() {
        if (this.d != null) {
            this.d.shutdown();
            this.d = null;
        }
        if (this.b != null) {
            this.c.post((Runnable)new Runnable() {
                private SelesEGL10Core b = DynamicStickerLoader.this.a;
                
                @Override
                public void run() {
                    if (this.b != null) {
                        this.b.destroy();
                        this.b = null;
                    }
                }
            });
            if (Build.VERSION.SDK_INT < 18) {
                this.b.quit();
            }
            else {
                this.b.quitSafely();
            }
            this.b = null;
        }
    }
}
