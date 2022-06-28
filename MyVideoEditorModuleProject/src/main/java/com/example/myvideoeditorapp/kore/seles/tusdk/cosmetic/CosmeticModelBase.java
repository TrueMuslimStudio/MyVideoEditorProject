// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.seles.tusdk.cosmetic;

public abstract class CosmeticModelBase
{
    protected static final float[] DEFAULT_VERTEX;
    public static final int DEFAULT_VERTEX_LENGTH;
    protected static final float[] DEFAULT_TEXTURE;
    public static final int DEFAULT_TEXTURE_LENGTH;
    
    static {
        DEFAULT_VERTEX = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f };
        DEFAULT_VERTEX_LENGTH = CosmeticModelBase.DEFAULT_VERTEX.length;
        DEFAULT_TEXTURE = new float[] { 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f };
        DEFAULT_TEXTURE_LENGTH = CosmeticModelBase.DEFAULT_TEXTURE.length;
    }
}
