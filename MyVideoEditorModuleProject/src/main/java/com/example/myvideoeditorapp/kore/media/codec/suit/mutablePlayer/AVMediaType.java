// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.suit.mutablePlayer;

public enum AVMediaType
{
    AVMediaTypeAudio("audio/"), 
    AVMediaTypeVideo("video/");
    
    private String a;
    
    private AVMediaType(final String a) {
        this.a = a;
    }
    
    public String getMime() {
        return this.a;
    }
}
