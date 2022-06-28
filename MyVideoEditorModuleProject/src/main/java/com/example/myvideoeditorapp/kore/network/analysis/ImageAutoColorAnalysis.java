// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesContext;
import com.example.myvideoeditorapp.kore.seles.filters.SelesFilter;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;


import java.io.File;
import java.nio.FloatBuffer;

public class ImageAutoColorAnalysis
{
    private ImageColorArgument a;
    private ImageOnlineAnalysis b;
    private _ImageAutoColorAnalysisWrap c;
    
    public _ImageAutoColorAnalysisWrap getFilter() {
        if (this.c != null) {
            return this.c.clone();
        }
        return this.c = c.b();
    }
    
    public void reset() {
        if (this.b != null) {
            this.b.cancel();
            this.b = null;
        }
        this.c = null;
        this.a = null;
    }
    
    public void analysisWithImage(final Bitmap image, final ImageAutoColorAnalysisListener imageAutoColorAnalysisListener) {
        if (imageAutoColorAnalysisListener == null) {
            return;
        }
        if (image == null) {
            imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(null, ImageOnlineAnalysis.ImageAnalysisType.NotInputImage);
            return;
        }
        if (this.a != null) {
            this.a(image, imageAutoColorAnalysisListener);
            return;
        }
        (this.b = new ImageOnlineAnalysis()).setImage(image);
        this.b.analysisColor(new ImageOnlineAnalysis.ImageAnalysisListener() {
            @Override
            public <T extends JsonBaseBean> void onImageAnalysisCompleted(final T t, final ImageOnlineAnalysis.ImageAnalysisType imageAnalysisType) {
                if (imageAnalysisType != ImageOnlineAnalysis.ImageAnalysisType.Succeed) {
                    imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(null, imageAnalysisType);
                    return;
                }
                final ImageAnalysisResult imageAnalysisResult = (ImageAnalysisResult)t;
                if (imageAnalysisResult != null && imageAnalysisResult.color != null) {
                    ImageAutoColorAnalysis.this.a = imageAnalysisResult.color;
                    ImageAutoColorAnalysis.this.getFilter().a(ImageAutoColorAnalysis.this.a);
                    StatisticsManger.appendComponent(ComponentActType.image_Analysis_color);
                }
                ImageAutoColorAnalysis.this.a(image, imageAutoColorAnalysisListener);
            }
        });
    }
    
    public void copyAnalysis(final File file, final File file2, final ImageAutoColorAnalysisCopyListener imageAutoColorAnalysisCopyListener) {
        if (imageAutoColorAnalysisCopyListener == null || this.a == null) {
            return;
        }
        if (file == null || !file.exists() || file2 == null) {
            imageAutoColorAnalysisCopyListener.onImageAutoColorAnalysisCopyCompleted(null);
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                ThreadHelper.post(new Runnable() {
                    final /* synthetic */ boolean a = BitmapHelper.saveBitmap(file2, ImageAutoColorAnalysis.this.getFilter().process(BitmapHelper.getBitmap(file, TuSdkSize.create(TuSdkGPU.getMaxTextureOptimizedSize()), true)), 95);
                    
                    @Override
                    public void run() {
                        imageAutoColorAnalysisCopyListener.onImageAutoColorAnalysisCopyCompleted(this.a ? file2 : null);
                    }
                });
            }
        });
    }
    
    private void a(final Bitmap bitmap, final ImageAutoColorAnalysisListener imageAutoColorAnalysisListener) {
        if (bitmap == null) {
            imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(null, ImageOnlineAnalysis.ImageAnalysisType.NotInputImage);
            return;
        }
        if (this.a == null) {
            imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(null, ImageOnlineAnalysis.ImageAnalysisType.ServiceFailed);
            return;
        }
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                ThreadHelper.post(new Runnable() {
                    final /* synthetic */ Bitmap a = ImageAutoColorAnalysis.this.getFilter().process(bitmap);
                    
                    @Override
                    public void run() {
                        imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(this.a, ImageOnlineAnalysis.ImageAnalysisType.Succeed);
                    }
                });
            }
        });
    }
    
    public void analysisWithThumb(final Bitmap bitmap, final File file, final File file2, final ImageAutoColorAnalysisListener imageAutoColorAnalysisListener, final ImageAutoColorAnalysisCopyListener imageAutoColorAnalysisCopyListener) {
        if (imageAutoColorAnalysisListener == null) {
            return;
        }
        this.analysisWithImage(bitmap, new ImageAutoColorAnalysisListener() {
            @Override
            public void onImageAutoColorAnalysisCompleted(final Bitmap bitmap, final ImageOnlineAnalysis.ImageAnalysisType imageAnalysisType) {
                imageAutoColorAnalysisListener.onImageAutoColorAnalysisCompleted(bitmap, imageAnalysisType);
                if (imageAnalysisType != ImageOnlineAnalysis.ImageAnalysisType.Succeed) {
                    if (imageAutoColorAnalysisCopyListener != null) {
                        imageAutoColorAnalysisCopyListener.onImageAutoColorAnalysisCopyCompleted(null);
                    }
                    return;
                }
                ImageAutoColorAnalysis.this.copyAnalysis(file, file2, imageAutoColorAnalysisCopyListener);
            }
        });
    }
    
    private static class _ImageAutoColorAnalysisFiler extends SelesFilter
    {
        private int a;
        private int b;
        private int c;
        private ImageColorArgument d;
        
        private _ImageAutoColorAnalysisFiler() {
            super("precision lowp float;varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;uniform lowp vec4 maxRGBA;uniform lowp vec4 minRGBA;uniform lowp vec3 midRGB;highp vec3 handleAutoTone(highp vec3 color){\thighp vec3 nColor = (color - minRGBA.rgb) / (maxRGBA.rgb - minRGBA.rgb);\treturn nColor;}highp vec3 handleAutoColor(highp vec3 color){\thighp vec3 nColor = handleAutoTone(color);\thighp vec3 alphaM = nColor * (0.5 / midRGB);\thighp vec3 alphaP = (nColor - midRGB) * (0.5 / (1.0 - midRGB)) + 0.5;\thighp float r = nColor.r < midRGB.r ? alphaM.r : alphaP.r;\thighp float g = nColor.g < midRGB.g ? alphaM.g : alphaP.g;\thighp float b = nColor.b < midRGB.b ? alphaM.b : alphaP.b;\treturn vec3(r,g,b);}void main(){\thighp vec3 textureColor = texture2D(inputImageTexture, textureCoordinate).rgb;\ttextureColor = handleAutoColor(textureColor);\tgl_FragColor = vec4(textureColor, 1.0);}");
            this.d = new ImageColorArgument();
            this.d.maxR = 1.0f;
            this.d.maxG = 1.0f;
            this.d.maxB = 1.0f;
            this.d.maxY = 1.0f;
            this.d.minR = 0.0f;
            this.d.minG = 0.0f;
            this.d.minB = 0.0f;
            this.d.minY = 0.0f;
            this.d.midR = 0.5f;
            this.d.midG = 0.5f;
            this.d.midB = 0.5f;
        }
        
        @Override
        protected void onInitOnGLThread() {
            super.onInitOnGLThread();
            this.a = this.mFilterProgram.uniformIndex("maxRGBA");
            this.b = this.mFilterProgram.uniformIndex("minRGBA");
            this.c = this.mFilterProgram.uniformIndex("midRGB");
            this.a(this.d);
        }
        
        private void a(final float n, final float n2, final float n3, final float n4) {
            this.runOnDraw(new Runnable() {
                @Override
                public void run() {
                    final float[] array = { n, n2, n3, n4 };
                    SelesContext.setActiveShaderProgram(_ImageAutoColorAnalysisFiler.this.mFilterProgram);
                    GLES20.glUniform4fv(_ImageAutoColorAnalysisFiler.this.a, 1, FloatBuffer.wrap(array));
                }
            });
        }
        
        private void b(final float n, final float n2, final float n3, final float n4) {
            this.runOnDraw(new Runnable() {
                @Override
                public void run() {
                    final float[] array = { n, n2, n3, n4 };
                    SelesContext.setActiveShaderProgram(_ImageAutoColorAnalysisFiler.this.mFilterProgram);
                    GLES20.glUniform4fv(_ImageAutoColorAnalysisFiler.this.b, 1, FloatBuffer.wrap(array));
                }
            });
        }
        
        private void a(final float n, final float n2, final float n3) {
            this.runOnDraw(new Runnable() {
                @Override
                public void run() {
                    final float[] array = { n, n2, n3 };
                    SelesContext.setActiveShaderProgram(_ImageAutoColorAnalysisFiler.this.mFilterProgram);
                    GLES20.glUniform3fv(_ImageAutoColorAnalysisFiler.this.c, 1, FloatBuffer.wrap(array));
                }
            });
        }
        
        private ImageColorArgument a() {
            return this.d;
        }
        
        private void a(final ImageColorArgument d) {
            if (d == null) {
                return;
            }
            this.d = d;
            this.a(d.maxR, d.maxG, d.maxB, d.maxY);
            this.b(d.minR, d.minG, d.minB, d.minY);
            this.a(d.midR, d.midG, d.midB);
        }
    }
    
    private static class _ImageAutoColorAnalysisWrap extends FilterWrap
    {
        private static _ImageAutoColorAnalysisWrap b() {
            return new _ImageAutoColorAnalysisWrap(new FilterOption() {
                @Override
                public SelesOutInput getFilter() {
                    return new _ImageAutoColorAnalysisFiler();
                }
            });
        }
        
        private _ImageAutoColorAnalysisWrap(final FilterOption filterOption) {
            super(filterOption);
        }
        
        @Override
        public _ImageAutoColorAnalysisWrap clone() {
            final _ImageAutoColorAnalysisWrap b = b();
            if (b != null) {
                b.setFilterParameter(this.getFilterParameter());
                ((_ImageAutoColorAnalysisFiler)b.getFilter()).a(((_ImageAutoColorAnalysisFiler)this.getFilter()).a());
            }
            return b;
        }
        
        private void a(final ImageColorArgument imageColorArgument) {
            ((_ImageAutoColorAnalysisFiler)this.getFilter()).a(imageColorArgument);
        }
    }
    
    public interface ImageAutoColorAnalysisCopyListener
    {
        void onImageAutoColorAnalysisCopyCompleted(final File p0);
    }
    
    public interface ImageAutoColorAnalysisListener
    {
        void onImageAutoColorAnalysisCompleted(final Bitmap p0, final ImageOnlineAnalysis.ImageAnalysisType p1);
    }
}
