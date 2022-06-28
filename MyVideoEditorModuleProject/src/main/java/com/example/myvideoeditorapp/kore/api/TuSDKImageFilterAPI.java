// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.api;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.secret.SdkValid;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;

import java.util.List;

public abstract class TuSDKImageFilterAPI
{
    protected abstract FilterWrap getFilterWrap();
    
    public List<String> getArgKeys() {
        if (this.getFilterWrap() == null) {
            return null;
        }
        return this.getFilterWrap().getFilterParameter().getArgKeys();
    }
    
    protected void submitFilterParameter() {
        if (this.getFilterWrap() == null) {
            return;
        }
        this.getFilterWrap().submitFilterParameter();
    }
    
    public boolean setFilterArgPrecentValue(final String s, final float precentValue) {
        if (this.getFilterWrap() == null) {
            return false;
        }
        final SelesParameters.FilterArg filterArg = this.getFilterWrap().getFilterParameter().getFilterArg(s);
        if (filterArg == null) {
            TLog.e("setFilterArgPrecentValue Key : %s  does not exist", s);
            return false;
        }
        filterArg.setPrecentValue(precentValue);
        return true;
    }
    
    public float getFilterArgPrecentValue(final String s) {
        if (this.getFilterWrap() == null) {
            return 0.0f;
        }
        final SelesParameters.FilterArg filterArg = this.getFilterWrap().getFilterParameter().getFilterArg(s);
        if (filterArg == null) {
            TLog.e("setFilterArg Invalid key : %s", s);
            return 0.0f;
        }
        return filterArg.getPrecentValue();
    }
    
    public void reset() {
        if (this.getFilterWrap() == null) {
            return;
        }
        this.getFilterWrap().getFilterParameter().reset();
    }
    
    public Bitmap process(final Bitmap bitmap) {
        return this.process(bitmap, ImageOrientation.Up);
    }
    
    public Bitmap process(final Bitmap bitmap, final ImageOrientation imageOrientation) {
        return this.process(bitmap, imageOrientation, 0.0f);
    }
    
    public final Bitmap process(final Bitmap bitmap, final ImageOrientation imageOrientation, final float n) {
        if (!this.a()) {
            return bitmap;
        }
        if (this.getFilterWrap() == null || bitmap == null) {
            return bitmap;
        }
        final Bitmap imageScale = BitmapHelper.imageScale(bitmap, TuSdkSize.create(bitmap).limitScale());
        this.submitFilterParameter();
        return this.getFilterWrap().process(imageScale, imageOrientation, n);
    }
    
    private boolean a() {
        if (!SdkValid.shared.sdkValid()) {
            TLog.e("Configuration not found! Please see: http://tusdk.com/docs/android/get-started", new Object[0]);
            return false;
        }
        if (SdkValid.shared.isExpired()) {
            TLog.e("Your account has expired Please see: http://tusdk.com/docs/android/get-started", new Object[0]);
            return false;
        }
        return true;
    }
}
