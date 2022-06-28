// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.http.RequestHandle;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpEngine;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpHandler;
import com.example.myvideoeditorapp.kore.network.TuSdkHttpParams;
import com.example.myvideoeditorapp.kore.utils.StringHelper;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageOnlineAnalysis
{
    public static final int ERROR_CODE_NO_FACE_FOUND = -601;
    public static final int ERROR_CODE_NO_ACCESS_RIGHT = -303;
    private Bitmap a;
    private RequestHandle b;
    
    public void setImage(final Bitmap a) {
        this.a = a;
    }
    
    public void cancel() {
        if (this.b == null) {
            return;
        }
        if (!this.b.isCancelled()) {
            this.b.cancel(true);
        }
        this.b = null;
    }
    
    public void analysisColor(final ImageAnalysisListener imageAnalysisListener) {
        this.a("/image/infos", null, ImageAnalysisResult.class, imageAnalysisListener);
    }

    public void analysisFaces(ImageAnalysisListener var1) {
        HashMap var2 = new HashMap();
        var2.put("marks", 5);
        var2.put("normalize", 1);
        var2.put("mutiple", 1);
        this.a("/face/landmark", var2, ImageMarkFaceResult.class, var1);
    }
    
    private <T extends JsonBaseBean> void a(final String s, final Map<String, Object> map, final Class<T> clazz, final ImageAnalysisListener imageAnalysisListener) {
        if (StringHelper.isBlank(s) || imageAnalysisListener == null) {
            return;
        }
        if (this.a == null) {
            imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)null, ImageAnalysisType.NotInputImage);
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                ImageOnlineAnalysis.this.b(s, map, (Class<JsonBaseBean>)clazz, imageAnalysisListener);
            }
        });
    }
    
    private <T extends JsonBaseBean> void b(final String s, final Map<String, Object> map, final Class<T> clazz, final ImageAnalysisListener imageAnalysisListener) {
        final Bitmap imageLimit = BitmapHelper.imageLimit(this.a, 512);
        this.a = null;
        ThreadHelper.post(new Runnable() {
            final /* synthetic */ InputStream a = BitmapHelper.bitmap2InputStream(imageLimit, 70);
            
            @Override
            public void run() {
                ImageOnlineAnalysis.this.mainThreadRequest(this.a, s, map, (Class<JsonBaseBean>)clazz, imageAnalysisListener);
            }
        });
    }
    
    protected <T extends JsonBaseBean> void mainThreadRequest(final InputStream inputStream, final String s, final Map<String, Object> map, final Class<T> clazz, final ImageAnalysisListener imageAnalysisListener) {
        if (inputStream == null) {
            imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)null, ImageAnalysisType.NotInputImage);
            return;
        }
        this.cancel();
        final TuSdkHttpHandler tuSdkHttpHandler = new TuSdkHttpHandler() {
            @Override
            protected void onRequestedSucceed(final TuSdkHttpHandler tuSdkHttpHandler) {
                imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)tuSdkHttpHandler.getJson().getJsonWithType((Class<T>)clazz), ImageAnalysisType.Succeed);
            }
            
            @Override
            protected void onRequestedFailed(final TuSdkHttpHandler tuSdkHttpHandler) {
                if (tuSdkHttpHandler.getError() != null && tuSdkHttpHandler.getError().getErrorCode() == -601) {
                    imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)null, ImageAnalysisType.Succeed);
                }
                else if (tuSdkHttpHandler.getError() != null && tuSdkHttpHandler.getError().getErrorCode() == -303) {
                    imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)null, ImageAnalysisType.NoAccessRight);
                }
                else {
                    imageAnalysisListener.onImageAnalysisCompleted((JsonBaseBean)null, ImageAnalysisType.ServiceFailed);
                }
            }
            
            @Override
            protected void onRequestedFinish(final TuSdkHttpHandler tuSdkHttpHandler) {
                ImageOnlineAnalysis.this.b = null;
            }
        };
        final TuSdkHttpParams tuSdkHttpParams = new TuSdkHttpParams();
        tuSdkHttpParams.put("pic", inputStream);
        if (map != null) {
            for (final String s2 : map.keySet()) {
                tuSdkHttpParams.put(s2, map.get(s2));
            }
        }
        TuSdkHttpEngine.service().postService(s, tuSdkHttpParams, tuSdkHttpHandler);
    }
    
    public interface ImageAnalysisListener
    {
         <T extends JsonBaseBean> void onImageAnalysisCompleted(final T p0, final ImageAnalysisType p1);
    }
    
    public enum ImageAnalysisType
    {
        Unknow, 
        Succeed, 
        NotInputImage, 
        ServiceFailed, 
        NoAccessRight;
    }
}
