// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.video.editor;

import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import android.graphics.Bitmap;
import com.example.myvideoeditorapp.kore.seles.tusdk.textSticker.TuSdkImage2DSticker;

public class TuSdkMediaStickerImageEffectData extends TuSdkMediaTileEffectDataBase
{
    public TuSdkMediaStickerImageEffectData(final TuSdkImage2DSticker tuSdkImage2DSticker) {
        super(tuSdkImage2DSticker);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage);
    }
    
    @Deprecated
    public TuSdkMediaStickerImageEffectData(final Bitmap bitmap, final float n, final float n2, final float n3, final TuSdkSize tuSdkSize, final TuSdkSize tuSdkSize2) {
        super(bitmap, n, n2, n3, tuSdkSize, tuSdkSize2);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage);
    }
    
    public TuSdkMediaStickerImageEffectData(final Bitmap bitmap, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        super(bitmap, n, n2, n3, n4, n5, n6);
        this.setMediaEffectType(TuSdkMediaEffectDataType.TuSdkMediEffectDataTypeStickerImage);
    }
}
