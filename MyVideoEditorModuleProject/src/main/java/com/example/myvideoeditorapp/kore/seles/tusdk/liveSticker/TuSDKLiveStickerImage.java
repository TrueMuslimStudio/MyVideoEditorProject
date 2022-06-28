// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.liveSticker;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.sticker.LiveStickerLoader;
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

public class TuSDKLiveStickerImage
{
    private LiveStickerLoader a;
    private boolean b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private long g;
    private StickerData h;
    private boolean i;
    private boolean j;
    private final List<TuSDKStickerAnimationItem> k;
    
    public TuSDKLiveStickerImage(final LiveStickerLoader a) {
        this.f = true;
        this.k = new ArrayList<TuSDKStickerAnimationItem>();
        this.a = a;
    }
    
    public boolean isActived() {
        return this.i;
    }
    
    public boolean isEnabled() {
        return this.j;
    }
    
    public void updateSticker(final StickerData h) {
        if (h.getType() != StickerData.StickerType.TypeDynamic) {
            return;
        }
        final StickerPositionInfo positionInfo = h.positionInfo;
        this.h = h;
        this.e = ((positionInfo != null) ? positionInfo.frameInterval : 0);
        if (this.e <= 0) {
            this.e = 100;
        }
        this.reset();
        this.i = true;
        this.c();
    }
    
    public StickerData getSticker() {
        return this.h;
    }
    
    public void removeSticker() {
        if (this.h != null) {
            final Bitmap image = this.h.getImage();
            this.h.setImage(null);
            if (image != null && !image.isRecycled()) {
                image.recycle();
            }
        }
        this.h = null;
        this.reset();
    }
    
    public int getCurrentTextureID() {
        if (!this.i) {
            return 0;
        }
        if (this.f) {
            this.d = this.a(System.currentTimeMillis());
        }
        if (this.d >= this.k.size()) {
            return 0;
        }
        return this.k.get(this.d).textureID;
    }
    
    public TuSdkSize getTextureSize() {
        if (!this.i || this.k == null || this.k.size() == 0) {
            return null;
        }
        return this.k.get(0).imageSize;
    }
    
    public void setCurrentFrameIndex(final int d) {
        if (d < 0) {
            return;
        }
        this.d = d;
    }
    
    public int getCurrentFrameIndex() {
        return this.d;
    }
    
    public void seekStickerToFrameTime(final long n) {
        if (n < 0L) {
            return;
        }
        this.d = this.a(n);
    }
    
    public void setBenchmarkTime(final long g) {
        this.g = g;
    }
    
    public long getBenchmarkTime() {
        return this.g;
    }
    
    public void setEnableAutoplayMode(final boolean f) {
        if (f == this.f) {
            return;
        }
        this.f = f;
        this.b();
    }

    public void reset() {
        this.b();
        this.c = 0;
        if (this.k.size() > 0) {
            this.j = false;
            ArrayList var1 = new ArrayList(this.k);
            this.k.clear();
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
                TuSDKStickerAnimationItem var3 = (TuSDKStickerAnimationItem)var2.next();
                var3.destory();
            }
        }

        if (!this.b) {
            this.i = false;
        }

        this.b = false;
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.reset();
        super.finalize();
    }
    
    private void a() {
        if (!this.f || this.d > 0 || this.g > 0L) {
            return;
        }
        this.d = 0;
        this.setBenchmarkTime(System.currentTimeMillis());
    }
    
    private void b() {
        this.setBenchmarkTime(0L);
        this.d = 0;
    }
    
    private int a(final long n) {
        if (n < 0L || this.g <= 0L || n < this.g || this.e == 0 || this.k.size() == 0) {
            return 0;
        }
        int n2 = (int)Math.floor((n - this.g) / (float)this.e);
        if (n2 > this.k.size() - 1) {
            if (this.h.positionInfo.loopStartIndex > 0 && this.h.positionInfo.loopStartIndex < this.k.size()) {
                final int loopStartIndex = this.h.positionInfo.loopStartIndex;
                n2 = (n2 - loopStartIndex) % (this.k.size() - loopStartIndex) + loopStartIndex;
            }
            else {
                n2 %= this.k.size();
            }
        }
        return n2;
    }
    
    private void c() {
        this.b = true;
        final StickerPositionInfo positionInfo = this.h.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            this.nextTextureLoadTask();
        }
        else {
            this.a(this.h.stickerImageName);
        }
    }
    
    protected void nextTextureLoadTask() {
        this.a(this.h.positionInfo.resourceList.get(this.c));
    }
    
    private void a(final String s) {
        if (this.a == null) {
            return;
        }
        this.a.loadImage(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                if (TuSDKLiveStickerImage.this.h.getImage() != null) {
                    bitmap = TuSDKLiveStickerImage.this.h.getImage();
                }
                else if (s.toLowerCase().endsWith(".png")) {
                    final StickerGroup stickerGroup = StickerLocalPackage.shared().getStickerGroup(TuSDKLiveStickerImage.this.h.groupId);
                    bitmap = BitmapHelper.getBitmap(new File(TuSdk.getAppTempPath() + File.separator + stickerGroup.file.substring(0, stickerGroup.file.lastIndexOf(".")) + File.separator + TuSDKLiveStickerImage.this.h.stickerId + File.separator + s));
                }
                else {
                    bitmap = StickerLocalPackage.shared().loadSmartStickerItem(TuSDKLiveStickerImage.this.h, s);
                }
                TuSDKLiveStickerImage.this.runOnGLContext(new Runnable() {
                    @Override
                    public void run() {
                        TuSDKLiveStickerImage.this.a(bitmap);
                    }
                });
            }
        });
    }
    
    private void a(final SelesFramebuffer selesFramebuffer) {
        if (!this.b) {
            selesFramebuffer.destroy();
            this.i = false;
            return;
        }
        this.k.add(new TuSDKStickerAnimationItem(selesFramebuffer));
        final StickerPositionInfo positionInfo = this.h.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            ++this.c;
            final int size = positionInfo.resourceList.size();
            if (this.c >= size) {
                this.d();
            }
            else {
                final int min = Math.min(5, size);
                if (!this.j && this.c > min) {
                    this.j = true;
                    this.a();
                }
                this.nextTextureLoadTask();
            }
        }
        else {
            this.d();
        }
    }
    
    private void d() {
        this.j = true;
        this.b = false;
        this.c = 0;
        final StickerPositionInfo positionInfo = this.h.positionInfo;
        if (positionInfo != null && positionInfo.hasAnimationSupported()) {
            this.a();
        }
    }
    
    private void a(final Bitmap bitmap) {
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
                TuSDKLiveStickerImage.this.a(fetchTexture);
            }
        });
    }
    
    protected void runOnGLContext(final Runnable runnable) {
        if (runnable == null || this.a == null) {
            return;
        }
        this.a.uploadTexture(runnable);
    }
    
    public final class TuSDKStickerAnimationItem
    {
        public TuSdkSize imageSize;
        public int textureID;
        private SelesFramebuffer b;
        
        public TuSDKStickerAnimationItem(final SelesFramebuffer b) {
            this.textureID = b.getTexture();
            this.imageSize = b.getSize();
            this.b = b;
        }
        
        public SelesFramebuffer getFbo() {
            return this.b;
        }
        
        public void destory() {
            if (this.b != null) {
                this.b.destroy();
                this.b = null;
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            this.destory();
            super.finalize();
        }
    }
}
