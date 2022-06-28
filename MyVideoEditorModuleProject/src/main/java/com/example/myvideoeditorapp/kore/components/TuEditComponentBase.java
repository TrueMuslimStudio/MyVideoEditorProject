// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components;

import android.app.Activity;

import com.example.myvideoeditorapp.kore.secret.StatisticsManger;

public abstract class TuEditComponentBase extends TuSdkInputComponent
{
    public TuEditComponentBase(final Activity activity) {
        super(activity);
        StatisticsManger.appendComponent(ComponentActType.editComponent);
    }
}
