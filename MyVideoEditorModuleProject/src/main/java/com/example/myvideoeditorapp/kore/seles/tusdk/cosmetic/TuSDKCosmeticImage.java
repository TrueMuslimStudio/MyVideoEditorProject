// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.SelesFramebuffer;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerGroup;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerLocalPackage;

import java.io.File;

public class TuSDKCosmeticImage
{
    private TuSdkSize a;
    private int b;
    private SelesFramebuffer c;
    
    public void updateStickerSync(final StickerData stickerData) {
        if (stickerData.getType() != StickerData.StickerType.TypeCosmetic) {
            return;
        }
        Bitmap bitmap;
        if (stickerData.getImage() != null) {
            bitmap = stickerData.getImage();
        }
        else if (stickerData.stickerImageName.toLowerCase().endsWith(".png")) {
            final StickerGroup stickerGroup = StickerLocalPackage.shared().getStickerGroup(stickerData.groupId);
            bitmap = BitmapHelper.getBitmap(new File(TuSdk.getAppTempPath() + File.separator + stickerGroup.file.substring(0, stickerGroup.file.lastIndexOf(".")) + File.separator + stickerData.stickerId + File.separator + stickerData.stickerImageName));
        }
        else {
            bitmap = StickerLocalPackage.shared().loadSmartStickerItem(stickerData, stickerData.stickerImageName);
        }
        this.a(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
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
        (this.c = SelesContext.sharedFramebufferCache().fetchTexture(tuSdkSize, false)).bindTexture(bitmap, b, true);
        this.a = this.c.getSize();
        this.b = this.c.getTexture();
    }
    
    public int getTextureID() {
        return this.b;
    }
    
    public TuSdkSize getTextureSize() {
        return this.a;
    }
    
    public void reset() {
        if (this.c != null) {
            this.c.destroy();
        }
    }
}
