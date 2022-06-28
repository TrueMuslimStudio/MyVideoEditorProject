// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components;

import android.app.Activity;

import com.example.myvideoeditorapp.kore.secret.StatisticsManger;

public abstract class TuAlbumComponentBase extends TuSdkComponent
{
    public TuAlbumComponentBase(final Activity activity) {
        super(activity);
        if (this.getClass().getSimpleName().contains("vatar")) {
            StatisticsManger.appendComponent(ComponentActType.avatarComponent);
        }
        else {
            StatisticsManger.appendComponent(ComponentActType.albumComponent);
        }
    }
}
