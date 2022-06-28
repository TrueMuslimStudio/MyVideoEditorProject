// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.image;

import java.util.Iterator;

import android.graphics.PixelFormat;
import android.os.Message;
import android.os.Looper;
import java.lang.ref.WeakReference;
import android.os.Handler;
import java.util.Locale;
import android.graphics.BitmapShader;
import android.os.SystemClock;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.Matrix;
import java.util.concurrent.TimeUnit;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.content.ContentResolver;
import java.io.FileDescriptor;
import android.content.res.AssetFileDescriptor;
import java.io.File;
import android.util.TypedValue;
import java.io.IOException;
import android.content.res.Resources;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import android.graphics.RectF;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.Paint;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public final class TuSdkGifDrawable extends Drawable implements Animatable
{
    final ScheduledThreadPoolExecutor a;
    volatile boolean b;
    private long e;
    private final Paint f;
    public final Bitmap mBuffer;
    final boolean c;
    public final GifHelper mGifHelper;
    private final Rect g;
    private final RectF h;
    private int i;
    private int j;
    final InvalidationHandler d;
    private final RenderTask k;
    private ScheduledFuture<?> l;
    private float m;
    private final ConcurrentLinkedQueue<TuGifAnimationListener> n;
    
    public static TuSdkGifDrawable createFromResource(final Resources resources, final int n) throws IOException {
        return new TuSdkGifDrawable(resources, n);
    }
    
    public TuSdkGifDrawable(final Resources resources, final int n) throws IOException {
        this(resources.openRawResourceFd(n));
        final float a = this.a(resources, n);
        this.j = (int)(this.mGifHelper.getHeight() * a);
        this.i = (int)(this.mGifHelper.getWidth() * a);
    }
    
    private float a(final Resources resources, final int n) {
        final TypedValue typedValue = new TypedValue();
        resources.getValue(n, typedValue, true);
        final int density = typedValue.density;
        int n2;
        if (density == 0) {
            n2 = 160;
        }
        else if (density != 65535) {
            n2 = density;
        }
        else {
            n2 = 0;
        }
        final int densityDpi = resources.getDisplayMetrics().densityDpi;
        if (n2 > 0 && densityDpi > 0) {
            return densityDpi / (float)n2;
        }
        return 1.0f;
    }
    
    public TuSdkGifDrawable(final String s) {
        this(GifHelper.parseFile(s), null, null, true);
    }
    
    public TuSdkGifDrawable(final File file) {
        this(GifHelper.parseFile(file.getPath()), null, null, true);
    }
    
    public TuSdkGifDrawable(final AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(GifHelper.openAssetFileDescriptor(assetFileDescriptor), null, null, true);
    }
    
    public TuSdkGifDrawable(final FileDescriptor fileDescriptor) {
        this(GifHelper.parseFd(fileDescriptor), null, null, true);
    }
    
    public TuSdkGifDrawable(final ContentResolver contentResolver, final Uri uri) throws IOException {
        this(GifHelper.openURI(contentResolver, uri), null, null, true);
    }
    
    public TuSdkGifDrawable(final GifHelper mGifHelper, final TuSdkGifDrawable tuSdkGifDrawable, final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, final boolean c) {
        this.b = false;
        this.e = Long.MIN_VALUE;
        this.f = new Paint(6);
        this.g = new Rect();
        this.h = new RectF();
        this.k = new RenderTask(this);
        this.n = new ConcurrentLinkedQueue<TuGifAnimationListener>();
        this.c = c;
        this.a = ((scheduledThreadPoolExecutor != null) ? scheduledThreadPoolExecutor : GifRenderingExecutor.getInstance());
        this.mGifHelper = mGifHelper;
        Bitmap mBuffer = null;
        if (tuSdkGifDrawable != null) {
            synchronized (tuSdkGifDrawable.mGifHelper) {
                if (!tuSdkGifDrawable.mGifHelper.isRecycled() && tuSdkGifDrawable.mGifHelper.getHeight() >= this.mGifHelper.getHeight() && tuSdkGifDrawable.mGifHelper.getWidth() >= this.mGifHelper.getWidth()) {
                    tuSdkGifDrawable.a();
                    mBuffer = tuSdkGifDrawable.mBuffer;
                    mBuffer.eraseColor(0);
                }
            }
        }
        if (mBuffer == null) {
            this.mBuffer = Bitmap.createBitmap(this.mGifHelper.getWidth(), this.mGifHelper.getHeight(), Bitmap.Config.ARGB_8888);
        }
        else {
            this.mBuffer = mBuffer;
        }
        this.d = new InvalidationHandler(this);
        this.k.doWork();
        this.i = this.mGifHelper.getWidth();
        this.j = this.mGifHelper.getHeight();
    }
    
    public void recycle() {
        this.a();
        this.mBuffer.recycle();
    }
    
    private void a() {
        this.b = false;
        this.d.removeMessages(-1);
        this.mGifHelper.recycle();
    }
    
    public boolean isRecycled() {
        return this.mGifHelper.isRecycled();
    }
    
    public int getIntrinsicHeight() {
        return this.j;
    }
    
    public int getIntrinsicWidth() {
        return this.i;
    }
    
    public void setAlpha(final int alpha) {
        this.f.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this.f.setColorFilter(colorFilter);
    }
    
    public ColorFilter getColorFilter() {
        return this.f.getColorFilter();
    }
    
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
    
    public void start() {
        synchronized (this) {
            if (this.b) {
                return;
            }
            this.b = true;
        }
        this.a(this.mGifHelper.restoreRemainder());
    }
    
    private void a(final long a) {
        if (this.c) {
            this.e = 0L;
            this.d.sendEmptyMessageAtTime(-1, 0L);
        }
        else {
            this.b();
            this.l = this.a.schedule(this.k, Math.max(a, 0L), TimeUnit.MILLISECONDS);
        }
    }
    
    public void reset() {
        this.a.execute(new SafeRunnable(this) {
            public void doWork() {
                if (TuSdkGifDrawable.this.mGifHelper.reset()) {
                    TuSdkGifDrawable.this.start();
                }
            }
        });
    }
    
    public void stop() {
        synchronized (this) {
            if (!this.b) {
                return;
            }
            this.b = false;
        }
        this.b();
        this.mGifHelper.saveRemainder();
    }
    
    private void b() {
        if (this.l != null) {
            this.l.cancel(false);
        }
        this.d.removeMessages(-1);
    }
    
    public boolean isRunning() {
        return this.b;
    }
    
    public int getLoopCount() {
        return this.mGifHelper.getLoopCount();
    }
    
    public void setLoopCount(final int loopCount) {
        this.mGifHelper.setLoopCount(loopCount);
    }
    
    public int getNumberOfFrames() {
        return this.mGifHelper.getFrameCount();
    }
    
    public GifHelper.GifError getError() {
        return GifHelper.GifError.fromCode(this.mGifHelper.getErrorCode());
    }
    
    public void setSpeed(final float speed) {
        this.mGifHelper.setSpeed(speed);
    }
    
    public void pause() {
        this.stop();
    }
    
    public int getDuration() {
        return this.mGifHelper.getDuration();
    }
    
    public int getCurrentPosition() {
        return this.mGifHelper.getCurrentPosition();
    }
    
    protected void onBoundsChange(final Rect rect) {
        this.g.set(rect);
        this.h.set(this.g);
        final Shader shader = this.f.getShader();
        if (shader != null) {
            final Matrix localMatrix = new Matrix();
            localMatrix.setTranslate(this.h.left, this.h.top);
            localMatrix.preScale(this.h.width() / this.mBuffer.getWidth(), this.h.height() / this.mBuffer.getHeight());
            shader.setLocalMatrix(localMatrix);
            this.f.setShader(shader);
        }
    }
    
    public void draw(final Canvas canvas) {
        if (this.f.getShader() == null) {
            canvas.drawBitmap(this.mBuffer, new Rect(0, 0, this.mGifHelper.getWidth(), this.mGifHelper.getHeight()), this.g, this.f);
        }
        else {
            canvas.drawRoundRect(this.h, this.m, this.m, this.f);
        }
        if (this.c && this.b && this.e != Long.MIN_VALUE) {
            final long max = Math.max(0L, this.e - SystemClock.uptimeMillis());
            this.e = Long.MIN_VALUE;
            this.a.remove(this.k);
            this.l = this.a.schedule(this.k, max, TimeUnit.MILLISECONDS);
        }
    }
    
    public int getAlpha() {
        return this.f.getAlpha();
    }
    
    @Deprecated
    public void setDither(final boolean dither) {
        this.f.setDither(dither);
        this.invalidateSelf();
    }
    
    public void addAnimationListener(final TuGifAnimationListener e) {
        this.n.add(e);
    }
    
    public boolean removeAnimationListener(final TuGifAnimationListener o) {
        return this.n.remove(o);
    }
    
    public boolean setVisible(final boolean b, final boolean b2) {
        final boolean setVisible = super.setVisible(b, b2);
        if (!this.c) {
            if (b) {
                if (b2) {
                    this.reset();
                }
                if (setVisible) {
                    this.start();
                }
            }
            else if (setVisible) {
                this.stop();
            }
        }
        return setVisible;
    }
    
    public int getCurrentFrameIndex() {
        return this.mGifHelper.getCurrentFrameIndex();
    }
    
    public int getCurrentLoop() {
        final int currentLoop = this.mGifHelper.getCurrentLoop();
        if (currentLoop == 0 || currentLoop < this.mGifHelper.getLoopCount()) {
            return currentLoop;
        }
        return currentLoop - 1;
    }
    
    public boolean isAnimationCompleted() {
        return this.mGifHelper.isAnimationCompleted();
    }
    
    public int getFrameDuration(final int n) {
        if (n < 0 || n > this.getNumberOfFrames()) {
            return 0;
        }
        return this.mGifHelper.getFrameDuration(n);
    }
    
    public void setCornerRadius(final float m) {
        this.m = m;
        Object shader;
        if (m > 0.0f) {
            shader = new BitmapShader(this.mBuffer, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        else {
            shader = null;
        }
        this.f.setShader((Shader)shader);
    }
    
    public float getCornerRadius() {
        return this.m;
    }
    
    public String toString() {
        return String.format(Locale.US, "GIF: size: %dx%d, frames: %d, error: %d", this.mGifHelper.getWidth(), this.mGifHelper.getHeight(), this.mGifHelper.getFrameCount(), this.mGifHelper.getErrorCode());
    }
    
    private class InvalidationHandler extends Handler
    {
        private final WeakReference<TuSdkGifDrawable> b;
        
        public InvalidationHandler(final TuSdkGifDrawable referent) {
            super(Looper.getMainLooper());
            this.b = new WeakReference<TuSdkGifDrawable>(referent);
        }

        public void handleMessage(Message var1) {
            TuSdkGifDrawable var2 = (TuSdkGifDrawable)this.b.get();
            if (var2 != null) {
                if (var1.what == -1) {
                    var2.invalidateSelf();
                } else {
                    Iterator var3 = var2.n.iterator();

                    while(var3.hasNext()) {
                        TuGifAnimationListener var4 = (TuGifAnimationListener)var3.next();
                        var4.onGifAnimationCompleted(var1.what);
                    }
                }

            }
        }
    }
    
    public interface TuGifAnimationListener
    {
        void onGifAnimationCompleted(final int p0);
    }
    
    private class RenderTask extends SafeRunnable
    {
        public RenderTask(final TuSdkGifDrawable tuSdkGifDrawable) {
            super(tuSdkGifDrawable);
        }
        
        public final void doWork() {
            final long renderFrame = this.mGifDrawable.mGifHelper.renderFrame(this.mGifDrawable.mBuffer);
            if (renderFrame >= 0L) {
                this.mGifDrawable.e = SystemClock.uptimeMillis() + renderFrame;
                if (this.mGifDrawable.isVisible() && this.mGifDrawable.b && !this.mGifDrawable.c) {
                    this.mGifDrawable.a.remove(this);
                    this.mGifDrawable.l = this.mGifDrawable.a.schedule(this, renderFrame, TimeUnit.MILLISECONDS);
                }
                if (!this.mGifDrawable.n.isEmpty() && this.mGifDrawable.getCurrentFrameIndex() == this.mGifDrawable.mGifHelper.getFrameCount() - 1) {
                    this.mGifDrawable.d.sendEmptyMessageAtTime(this.mGifDrawable.getCurrentLoop(), this.mGifDrawable.e);
                }
            }
            else {
                this.mGifDrawable.e = Long.MIN_VALUE;
                this.mGifDrawable.b = false;
            }
            if (this.mGifDrawable.isVisible() && !this.mGifDrawable.d.hasMessages(-1)) {
                this.mGifDrawable.d.sendEmptyMessageAtTime(-1, 0L);
            }
        }
    }
    
    private abstract class SafeRunnable implements Runnable
    {
        protected final TuSdkGifDrawable mGifDrawable;
        
        SafeRunnable(final TuSdkGifDrawable mGifDrawable) {
            this.mGifDrawable = mGifDrawable;
        }
        
        @Override
        public final void run() {
            try {
                if (!this.mGifDrawable.isRecycled()) {
                    this.doWork();
                }
            }
            catch (Throwable t) {
                final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                if (defaultUncaughtExceptionHandler != null) {
                    defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), t);
                }
            }
        }
        
        abstract void doWork();
    }
}
