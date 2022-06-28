// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.sticker.StickerPositionInfo;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;

public class TuSDKDynamicStickerImage
{
    private DynamicStickerLoader a;
    private boolean b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private int g;
    private boolean h;
    private long i;
    private StickerData j;
    private boolean k;
    private boolean l;
    private final List<TuSDKStickerAnimationItem> m;
    private final List<TuSDKStickerAnimationItem> n;
    private OnTextureLoadedListener o;
    
    public TuSDKDynamicStickerImage(final DynamicStickerLoader a) {
        this.h = true;
        this.i = 0L;
        this.m = new ArrayList<TuSDKStickerAnimationItem>();
        this.n = new ArrayList<TuSDKStickerAnimationItem>();
        this.a = a;
    }
    
    public void setOnTextureLoadedListener(final OnTextureLoadedListener o) {
        this.o = o;
    }
    
    public boolean isActived() {
        return this.k;
    }
    
    public boolean isEnabled() {
        return this.l;
    }
    
    public void updateSticker(final StickerData j) {
        if (j.getType() != StickerData.StickerType.TypeDynamic) {
            return;
        }
        final StickerPositionInfo positionInfo = j.positionInfo;
        this.j = j;
        this.g = ((positionInfo != null) ? positionInfo.frameInterval : 0);
        if (this.g <= 0) {
            this.g = 100;
        }
        this.reset();
        this.k = true;
        this.c();
    }
    
    public StickerData getSticker() {
        return this.j;
    }
    
    public void removeSticker() {
        if (this.j != null) {
            final Bitmap image = this.j.getImage();
            this.j.setImage(null);
            if (image != null && !image.isRecycled()) {
                image.recycle();
            }
        }
        this.j = null;
        this.reset();
    }
    
    public Bitmap getCurrentBitmap() {
        if (!this.k) {
            return null;
        }
        if (this.h) {
            this.f = this.a(System.currentTimeMillis());
        }
        if (this.f >= this.m.size()) {
            return null;
        }
        return this.m.get(this.f).getBitmap();
    }
    
    public Bitmap getCurrentBitmap(final long n) {
        if (!this.k) {
            return null;
        }
        if (this.h) {
            this.f = this.a(n);
        }
        if (this.f >= this.m.size()) {
            return null;
        }
        return this.m.get(this.f).getBitmap();
    }
    
    public int getCurrentTextureID() {
        if (!this.k) {
            return 0;
        }
        if (this.h) {
            this.f = this.b(System.currentTimeMillis());
        }
        if (this.f >= this.n.size()) {
            return 0;
        }
        return this.n.get(this.f).textureID;
    }
    
    public TuSdkSize getTextureSize() {
        if (!this.k || this.m == null || this.m.size() == 0) {
            return null;
        }
        return this.m.get(0).imageSize;
    }
    
    public void setCurrentFrameIndex(final int f) {
        if (f < 0) {
            return;
        }
        this.f = f;
    }
    
    public int getCurrentFrameIndex() {
        return this.f;
    }
    
    public void seekStickerToFrameTime(final long n) {
        if (n < 0L) {
            return;
        }
        this.f = this.a(n);
    }
    
    public void setBenchmarkTime(final long i) {
        this.i = i;
    }
    
    public void setEnableAutoplayMode(final boolean h) {
        if (h == this.h) {
            return;
        }
        this.h = h;
        this.b();
    }

    public void reset() {
        this.b();
        this.d = 0;
        this.e = 0;
        if (this.m.size() > 0) {
            this.l = false;
            ArrayList var1 = new ArrayList(this.m);
            this.m.clear();
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
                TuSDKStickerAnimationItem var3 = (TuSDKStickerAnimationItem)var2.next();
                var3.destory();
            }
        }

        if (!this.b) {
            this.k = false;
        }

        this.b = false;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.reset();
        super.finalize();
    }
    
    private void a() {
        if (!this.h || this.f > 0 || this.i > 0L) {
            return;
        }
        this.f = 0;
        this.setBenchmarkTime(System.currentTimeMillis());
    }
    
    private void b() {
        this.setBenchmarkTime(0L);
        this.f = 0;
    }
    
    private int a(final long n) {
        if (n < 0L || this.i <= 0L || n < this.i || this.g == 0 || this.m.size() == 0) {
            return 0;
        }
        int n2 = (int)Math.floor((n - this.i) / (float)this.g);
        if (n2 > this.m.size() - 1) {
            if (this.j.positionInfo.loopStartIndex > 0 && this.j.positionInfo.loopStartIndex < this.m.size()) {
                final int loopStartIndex = this.j.positionInfo.loopStartIndex;
                n2 = (n2 - loopStartIndex) % (this.m.size() - loopStartIndex) + loopStartIndex;
            }
            else {
                n2 %= this.m.size();
            }
        }
        return n2;
    }
    
    private int b(final long n) {
        if (n < 0L || this.i <= 0L || n < this.i || this.g == 0 || this.n.size() == 0) {
            return 0;
        }
        int n2 = (int)Math.floor((n - this.i) / (float)this.g);
        if (n2 > this.m.size() - 1) {
            if (this.j.positionInfo.loopStartIndex > 0 && this.j.positionInfo.loopStartIndex < this.m.size()) {
                final int loopStartIndex = this.j.positionInfo.loopStartIndex;
                n2 = (n2 - loopStartIndex) % (this.m.size() - loopStartIndex) + loopStartIndex;
            }
            else {
                n2 %= this.m.size();
            }
        }
        return n2;
    }
    
    private void c() {
        this.b = true;
        final StickerPositionInfo positionInfo = this.j.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            this.nextTextureLoadTask();
            this.nextBitmapLoadTask();
        }
        else {
            this.b(this.j.stickerImageName);
        }
    }
    
    protected void nextBitmapLoadTask() {
        this.a(this.j.positionInfo.resourceList.get(this.d));
    }
    
    protected void nextTextureLoadTask() {
        this.b(this.j.positionInfo.resourceList.get(this.e));
    }
    
    private void a(final String s) {
        if (this.a == null) {
            return;
        }
        this.a.loadImage(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                if (s.toLowerCase().endsWith(".png")) {
                    final StickerGroup stickerGroup = StickerLocalPackage.shared().getStickerGroup(TuSDKDynamicStickerImage.this.j.groupId);
                    bitmap = BitmapHelper.getBitmap(new File(TuSdk.getAppTempPath() + File.separator + stickerGroup.file.substring(0, stickerGroup.file.lastIndexOf(".")) + File.separator + TuSDKDynamicStickerImage.this.j.stickerId + File.separator + s));
                }
                else {
                    bitmap = StickerLocalPackage.shared().loadSmartStickerItem(TuSDKDynamicStickerImage.this.j, s);
                }
                if (TuSdkSize.create(bitmap).minSide() <= 0) {
                    TLog.e("Passed image must not be empty - it should be at least 1px tall and wide", new Object[0]);
                    return;
                }
                ThreadHelper.post(new Runnable() {
                    @Override
                    public void run() {
                        TuSDKDynamicStickerImage.this.a(bitmap);
                    }
                });
            }
        });
    }
    
    private void b(final String s) {
        if (this.a == null) {
            return;
        }
        this.a.loadImage(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                if (s.toLowerCase().endsWith(".png")) {
                    final StickerGroup stickerGroup = StickerLocalPackage.shared().getStickerGroup(TuSDKDynamicStickerImage.this.j.groupId);
                    bitmap = BitmapHelper.getBitmap(new File(TuSdk.getAppTempPath() + File.separator + stickerGroup.file.substring(0, stickerGroup.file.lastIndexOf(".")) + File.separator + TuSDKDynamicStickerImage.this.j.stickerId + File.separator + s));
                }
                else {
                    bitmap = StickerLocalPackage.shared().loadSmartStickerItem(TuSDKDynamicStickerImage.this.j, s);
                }
                if (TuSdkSize.create(bitmap).minSide() <= 0) {
                    TLog.e("Passed image must not be empty - it should be at least 1px tall and wide", new Object[0]);
                    return;
                }
                TuSDKDynamicStickerImage.this.runOnGLContext(new Runnable() {
                    @Override
                    public void run() {
                        if (SelesContext.currentEGLContext() != EGL10.EGL_NO_CONTEXT) {
                            TuSDKDynamicStickerImage.this.b(bitmap);
                        }
                    }
                });
            }
        });
    }
    
    private void a(final Bitmap bitmap) {
        if (!this.b) {
            bitmap.recycle();
            this.k = false;
            return;
        }
        this.m.add(new TuSDKStickerAnimationItem(bitmap));
        final StickerPositionInfo positionInfo = this.j.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            ++this.d;
            final int size = positionInfo.resourceList.size();
            TLog.e("[Debug] mLoadTaskIndex = %s", this.d);
            if (this.d >= size) {
                this.e();
            }
            else {
                final int min = Math.min(5, size);
                if (this.d == 1) {
                    this.o.onFirstBitmapLoader(this);
                }
                if (!this.l && this.d > min) {
                    this.l = true;
                    if (this.i == 0L) {
                        this.a();
                    }
                }
                this.nextBitmapLoadTask();
            }
        }
        else {
            this.e();
        }
    }
    
    private void a(final SelesFramebuffer selesFramebuffer) {
        if (!this.b) {
            selesFramebuffer.destroy();
            this.k = false;
            return;
        }
        this.n.add(new TuSDKStickerAnimationItem(selesFramebuffer));
        final StickerPositionInfo positionInfo = this.j.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            ++this.e;
            final int size = positionInfo.resourceList.size();
            TLog.e("[Debug] mTextureLoadTaskIndex = %s", this.e);
            if (this.e >= size) {
                this.d();
            }
            else {
                final int min = Math.min(5, size);
                if (!this.l && this.e > min) {
                    this.l = true;
                    if (this.i == 0L) {
                        this.a();
                    }
                }
                this.nextTextureLoadTask();
            }
        }
        else {
            this.d();
        }
    }
    
    private void d() {
        this.l = true;
        this.b = false;
        this.e = 0;
        final StickerPositionInfo positionInfo = this.j.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported() && this.i == 0L) {
            this.a();
        }
        this.o.onAllTextureLoader(this);
    }
    
    private void e() {
        this.l = true;
        this.c = false;
        this.d = 0;
        final StickerPositionInfo positionInfo = this.j.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported() && this.i == 0L) {
            this.a();
        }
        this.o.onAllBitmapLoader(this);
    }
    
    private void b(final Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        final boolean b = false;
        TuSdkSize tuSdkSize = TuSdkSize.create(bitmap);
        if (tuSdkSize.minSide() <= 0) {
            TLog.e("Passed image must not be empty - it should be at least 1px tall and wide", new Object[0]);
            return;
        }
        if (b) {
            tuSdkSize = SelesContext.sizeThatFitsWithinATexture(tuSdkSize.copy());
        }
        final SelesFramebuffer fetchTexture = SelesContext.sharedFramebufferCache().fetchTexture(tuSdkSize, false);
        fetchTexture.bindTexture(bitmap, b, true);
        ThreadHelper.post(new Runnable() {
            @Override
            public void run() {
                TuSDKDynamicStickerImage.this.a(fetchTexture);
            }
        });
    }
    
    protected void runOnGLContext(final Runnable runnable) {
        if (runnable == null || this.a == null) {
            return;
        }
        this.a.uploadTexture(runnable);
    }
    
    public interface OnTextureLoadedListener
    {
        void onAllBitmapLoader(final TuSDKDynamicStickerImage p0);
        
        void onAllTextureLoader(final TuSDKDynamicStickerImage p0);
        
        void onFirstBitmapLoader(final TuSDKDynamicStickerImage p0);
    }
    
    public final class TuSDKStickerAnimationItem
    {
        public TuSdkSize imageSize;
        public Bitmap texture;
        public int textureID;
        private SelesFramebuffer b;
        
        public TuSDKStickerAnimationItem(final Bitmap texture) {
            this.imageSize = TuSdkSize.create(texture);
            this.texture = texture;
        }
        
        public TuSDKStickerAnimationItem(final SelesFramebuffer b) {
            this.imageSize = b.getSize();
            this.textureID = b.getTexture();
            this.b = b;
        }
        
        public Bitmap getBitmap() {
            return this.texture;
        }
        
        public void destory() {
            if (this.texture != null) {
                this.texture.recycle();
                this.texture = null;
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            this.destory();
            super.finalize();
        }
    }
}
