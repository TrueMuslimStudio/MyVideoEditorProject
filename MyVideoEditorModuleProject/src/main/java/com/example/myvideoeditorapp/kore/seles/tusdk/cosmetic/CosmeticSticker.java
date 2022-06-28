// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

import com.example.myvideoeditorapp.kore.sticker.StickerPositionInfo;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerData;

public class CosmeticSticker
{
    public CosmeticStickerState state;
    public StickerPositionInfo.StickerPositionType type;
    public StickerData data;
    public CosmeticLipFilter.CosmeticLipType lipType;
    public int lipColor;
    
    private CosmeticSticker(Builder builder1, final Builder builder) {
        this.state = builder.a;
        this.type = builder.b;
        this.data = builder.c;
        this.lipType = builder.lipType;
        this.lipColor = builder.d;
    }
    
    public static class Builder
    {
        private CosmeticStickerState a;
        private StickerPositionInfo.StickerPositionType b;
        private StickerData c;
        public CosmeticLipFilter.CosmeticLipType lipType;
        private int d;
        
        public Builder state(final CosmeticStickerState a) {
            this.a = a;
            return this;
        }
        
        public Builder type(final StickerPositionInfo.StickerPositionType b) {
            this.b = b;
            return this;
        }
        
        public Builder data(final StickerData c) {
            this.c = c;
            return this;
        }
        
        public Builder lipType(final CosmeticLipFilter.CosmeticLipType lipType) {
            this.lipType = lipType;
            return this;
        }
        
        public Builder lipColor(final int d) {
            this.d = d;
            return this;
        }
        
        public CosmeticSticker build() {
            return new CosmeticSticker(this, null);
        }
    }
    
    public enum CosmeticStickerState
    {
        NoChange, 
        Update, 
        Close;
    }
}
