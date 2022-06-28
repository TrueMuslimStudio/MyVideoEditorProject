// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.dynamicSticker;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerDynamicData;


import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLContext;

public class DynamicStickerPlayController
{
    public static final int LIVE_STICKER_MAX_NUM = 5;
    private DynamicStickerLoader a;
    private List<TuSDKDynamicStickerImage> b;
    private List<TuSDKDynamicStickerImage> c;
    private List<StickerData> d;
    private boolean e;
    private OnStickerPlayerListener f;
    
    public void setStickerPlayerListener(final OnStickerPlayerListener f) {
        this.f = f;
    }
    
    public DynamicStickerPlayController() {
        this.d = new ArrayList<StickerData>();
        this.c = new ArrayList<TuSDKDynamicStickerImage>();
        this.b = new ArrayList<TuSDKDynamicStickerImage>();
        this.a = new DynamicStickerLoader();
    }
    
    public DynamicStickerPlayController(final EGLContext eglContext) {
        this.d = new ArrayList<StickerData>();
        this.c = new ArrayList<TuSDKDynamicStickerImage>();
        this.b = new ArrayList<TuSDKDynamicStickerImage>();
        this.a = new DynamicStickerLoader(eglContext);
    }
    
    public DynamicStickerLoader getLiveStickerLoader() {
        return this.a;
    }
    
    public void destroy() {
        if (this.a != null) {
            this.a.destroy();
            this.a = null;
        }
        this.removeAllStickers();
        if (this.c != null) {
            this.c.clear();
            this.c = null;
        }
        this.b = null;
        this.d = null;
    }
    
    private boolean a(final StickerData stickerData) {
        if (this.a == null) {
            return false;
        }
        if (this.e) {
            this.removeAllStickers();
            this.e = false;
        }
        if (this.b(stickerData)) {
            return false;
        }
        this.d.add(stickerData);
        TuSDKDynamicStickerImage a = this.a();
        if (a == null) {
            a = new TuSDKDynamicStickerImage(this.a);
            a.setOnTextureLoadedListener(new TuSDKDynamicStickerImage.OnTextureLoadedListener() {
                @Override
                public void onAllBitmapLoader(final TuSDKDynamicStickerImage tuSDKDynamicStickerImage) {
                }
                
                @Override
                public void onAllTextureLoader(final TuSDKDynamicStickerImage tuSDKDynamicStickerImage) {
                }
                
                @Override
                public void onFirstBitmapLoader(final TuSDKDynamicStickerImage tuSDKDynamicStickerImage) {
                    DynamicStickerPlayController.this.updataStickers(System.currentTimeMillis());
                }
            });
        }
        a.updateSticker(stickerData);
        this.b.add(a);
        return true;
    }
    
    private boolean b(final StickerData stickerData) {
        return this.d != null && this.d.size() > 0 && this.d.contains(stickerData);
    }
    
    public void removeSticker(final StickerData stickerData) {
        if (stickerData == null || !this.b(stickerData)) {
            return;
        }
        this.d.remove(stickerData);
        final TuSDKDynamicStickerImage stickerImageBy = this.getStickerImageBy(stickerData);
        if (stickerImageBy == null) {
            return;
        }
        stickerImageBy.removeSticker();
        this.b.remove(stickerImageBy);
        this.c.add(stickerImageBy);
    }
    
    public TuSDKDynamicStickerImage getStickerImageBy(final StickerData stickerData) {
        if (this.b == null || this.b.size() <= 0) {
            return null;
        }
        for (int i = 0; i < this.b.size(); ++i) {
            final TuSDKDynamicStickerImage tuSDKDynamicStickerImage = this.b.get(i);
            if (stickerData.equals(tuSDKDynamicStickerImage.getSticker())) {
                return tuSDKDynamicStickerImage;
            }
        }
        return null;
    }
    
    private TuSDKDynamicStickerImage a() {
        if (this.c == null || this.c.size() == 0) {
            return null;
        }
        for (int i = 0; i < this.c.size(); ++i) {
            final TuSDKDynamicStickerImage tuSDKDynamicStickerImage = this.c.get(i);
            if (!tuSDKDynamicStickerImage.isActived() && !tuSDKDynamicStickerImage.isEnabled()) {
                return this.c.remove(i);
            }
        }
        return null;
    }
    
    public boolean showGroupSticker(final StickerDynamicData stickerDynamicData) {
        if (stickerDynamicData.getStickerData() == null || stickerDynamicData.getStickerData().positionInfo.resourceList.size() <= 0) {
            TLog.e("invalid sticker group", new Object[0]);
            return false;
        }
        this.e = false;
        this.a(stickerDynamicData.getStickerData());
        return this.e = true;
    }
    
    public void updataStickers(final long n) {
        if (this.f != null) {
            this.f.OnStickerUpdate(TuSdkSize.create(this.b.get(0).getCurrentBitmap(n)), this.b.get(0).getCurrentBitmap(n));
        }
    }
    
    public long getCurrentGroupId() {
        if (this.e && this.d != null && this.d.size() > 0) {
            return this.d.get(0).groupId;
        }
        return -1L;
    }
    
    public void removeAllStickers() {
        if (this.b == null || this.b.size() == 0) {
            return;
        }
        for (int i = 0; i < this.b.size(); ++i) {
            final TuSDKDynamicStickerImage tuSDKDynamicStickerImage = this.b.get(i);
            tuSDKDynamicStickerImage.reset();
            this.c.add(tuSDKDynamicStickerImage);
        }
        this.b.clear();
        this.d.clear();
        this.e = false;
    }
    
    public void pauseAllStickers() {
        this.a(true);
    }
    
    public void resumeAllStickers() {
        this.a(false);
    }
    
    private void a(final boolean b) {
        if (this.b == null || this.b.size() == 0) {
            return;
        }
        for (int i = 0; i < this.b.size(); ++i) {
            this.b.get(i).setEnableAutoplayMode(!b);
        }
    }
    
    public List<TuSDKDynamicStickerImage> getStickers() {
        return this.b;
    }
    
    public interface OnStickerPlayerListener
    {
        void onStickerLoaded();
        
        void OnStickerUpdate(final TuSdkSize p0, final Bitmap p1);
    }
}
