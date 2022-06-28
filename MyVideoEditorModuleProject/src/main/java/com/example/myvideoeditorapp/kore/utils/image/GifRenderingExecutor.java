// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.image;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class GifRenderingExecutor extends ScheduledThreadPoolExecutor
{
    private static volatile GifRenderingExecutor a;
    
    private GifRenderingExecutor() {
        super(1, new DiscardPolicy());
    }
    
    public static GifRenderingExecutor getInstance() {
        if (GifRenderingExecutor.a == null) {
            synchronized (GifRenderingExecutor.class) {
                if (GifRenderingExecutor.a == null) {
                    GifRenderingExecutor.a = new GifRenderingExecutor();
                }
            }
        }
        return GifRenderingExecutor.a;
    }
    
    static {
        GifRenderingExecutor.a = null;
    }
}
