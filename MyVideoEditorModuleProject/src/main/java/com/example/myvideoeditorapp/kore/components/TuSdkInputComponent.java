// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;

import java.io.File;

public abstract class TuSdkInputComponent extends TuSdkComponent
{
    private File a;
    private ImageSqlInfo b;
    private Bitmap c;
    
    public TuSdkInputComponent(final Activity activity) {
        super(activity);
    }
    
    public File getTempFilePath() {
        return this.a;
    }
    
    public TuSdkInputComponent setTempFilePath(final File a) {
        this.a = a;
        return this;
    }
    
    public ImageSqlInfo getImageSqlInfo() {
        return this.b;
    }
    
    public TuSdkInputComponent setImageSqlInfo(final ImageSqlInfo b) {
        this.b = b;
        return this;
    }
    
    public Bitmap getImage() {
        return this.c;
    }
    
    public TuSdkInputComponent setImage(final Bitmap c) {
        this.c = c;
        return this;
    }
}
