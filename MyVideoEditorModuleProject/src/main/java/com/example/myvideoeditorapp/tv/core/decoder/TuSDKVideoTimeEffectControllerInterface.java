// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.tv.core.decoder;

import com.example.myvideoeditorapp.tv.core.common.TuSDKAVPacket;

import java.util.LinkedList;

public interface TuSDKVideoTimeEffectControllerInterface
{
    void doPacketTimeEffectExtract(final LinkedList<TuSDKAVPacket> p0);
    
    void reset();
}
