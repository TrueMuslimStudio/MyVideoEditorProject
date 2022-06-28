// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.sticker;

import java.util.Iterator;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.CosmeticSticker;
import java.util.HashMap;
import com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic.TuSDKCosmeticImage;
import java.util.Map;

public class CosmeticStickerPlayController
{
    public Map<StickerPositionInfo.StickerPositionType, TuSDKCosmeticImage> mCosmeticImageMap;
    
    public CosmeticStickerPlayController() {
        this.mCosmeticImageMap = new HashMap<StickerPositionInfo.StickerPositionType, TuSDKCosmeticImage>();
    }
    
    public void runTask(final CosmeticSticker cosmeticSticker) {
        if (cosmeticSticker.state == CosmeticSticker.CosmeticStickerState.Update) {
            if (cosmeticSticker.data == null) {
                return;
            }
            final TuSDKCosmeticImage tuSDKCosmeticImage = this.mCosmeticImageMap.get(cosmeticSticker.type);
            if (tuSDKCosmeticImage == null) {
                final TuSDKCosmeticImage tuSDKCosmeticImage2 = new TuSDKCosmeticImage();
                tuSDKCosmeticImage2.updateStickerSync(cosmeticSticker.data);
                this.mCosmeticImageMap.put(cosmeticSticker.type, tuSDKCosmeticImage2);
            }
            else {
                tuSDKCosmeticImage.reset();
                tuSDKCosmeticImage.updateStickerSync(cosmeticSticker.data);
            }
        }
        else if (cosmeticSticker.state == CosmeticSticker.CosmeticStickerState.Close) {
            final TuSDKCosmeticImage tuSDKCosmeticImage3 = this.mCosmeticImageMap.get(cosmeticSticker.type);
            if (tuSDKCosmeticImage3 != null) {
                tuSDKCosmeticImage3.reset();
            }
            this.mCosmeticImageMap.remove(cosmeticSticker.type);
        }
    }
    
    public Map<StickerPositionInfo.StickerPositionType, TuSDKCosmeticImage> getCosmeticImage() {
        return this.mCosmeticImageMap;
    }
    
    public void removeAllStickers() {
        if (this.mCosmeticImageMap == null || this.mCosmeticImageMap.size() == 0) {
            return;
        }
        final Iterator<TuSDKCosmeticImage> iterator = this.mCosmeticImageMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().reset();
        }
        this.mCosmeticImageMap.clear();
    }
    
    public void destroy() {
        this.removeAllStickers();
        this.mCosmeticImageMap = null;
    }
}
