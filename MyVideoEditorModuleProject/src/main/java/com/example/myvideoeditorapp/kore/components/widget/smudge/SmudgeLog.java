// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.widget.smudge;

import java.io.File;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdk;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;

public class SmudgeLog
{
    private Bitmap a;
    private int b;
    private int c;
    private boolean d;
    private String e;
    
    public SmudgeLog(final Bitmap a) {
        this.e = StringHelper.timeStampString();
        this.a = a;
        this.b = a.getWidth();
        this.c = a.getHeight();
    }
    
    public String getName() {
        return this.e;
    }
    
    public String getFileName() {
        return String.format("smudgeCache_%s.tmp", this.e);
    }
    
    public synchronized Bitmap getBitmap() {
        if (this.d) {
            return BitmapHelper.getBitmap(new File(TuSdk.getAppTempPath(), this.getFileName()), TuSdkSize.create(this.b, this.c), true);
        }
        return this.a;
    }
    
    public boolean hasCached() {
        return this.d;
    }
    
    public synchronized void markAsCached() {
        this.d = true;
        BitmapHelper.recycled(this.a);
        this.a = null;
    }
    
    public void destroy() {
        if (this.d) {
            FileHelper.delete(new File(TuSdk.getAppTempPath(), this.getFileName()));
        }
        else {
            BitmapHelper.recycled(this.a);
        }
    }
}
