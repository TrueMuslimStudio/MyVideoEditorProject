// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.graphics.RectF;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.components.ComponentErrorType;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.hardware.TuSdkGPU;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;

import java.io.File;

public abstract class TuImageResultFragment extends TuResultFragment
{
    protected float mScreenSizeScale;
    private File a;
    private ImageSqlInfo b;
    private Bitmap c;
    private boolean d;
    private boolean e;
    private ImageView f;
    private View.OnClickListener g;
    
    public TuImageResultFragment() {
        this.mScreenSizeScale = 0.75f;
        this.g = (View.OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                if (TuImageResultFragment.this.equalViewIds(view, (View)TuImageResultFragment.this.getPreview())) {
                    TuImageResultFragment.this.handleTest();
                }
            }
        };
    }
    
    public File getTempFilePath() {
        return this.a;
    }
    
    public void setTempFilePath(final File a) {
        this.a = a;
    }
    
    public ImageSqlInfo getImageSqlInfo() {
        return this.b;
    }
    
    public void setImageSqlInfo(final ImageSqlInfo b) {
        this.b = b;
    }
    
    public Bitmap getImage() {
        if (this.c == null) {
            final TuSdkSize imageDisplaySize = this.getImageDisplaySize();
            this.c = BitmapHelper.getBitmap(this.getTempFilePath(), imageDisplaySize, true);
            if (this.c == null) {
                this.c = BitmapHelper.getBitmap(this.getImageSqlInfo(), true, imageDisplaySize);
            }
        }
        return this.c;
    }
    
    public TuSdkSize getImageDisplaySize() {
        final TuSdkSize screenSize = ContextUtils.getScreenSize((Context)this.getActivity());
        if (screenSize != null) {
            screenSize.width = (int)Math.floor(screenSize.width * this.mScreenSizeScale);
            screenSize.height = (int)Math.floor(screenSize.height * this.mScreenSizeScale);
        }
        return screenSize;
    }
    
    public void setImage(final Bitmap c) {
        this.c = c;
    }
    
    public boolean isShowResultPreview() {
        return this.d;
    }
    
    public void setShowResultPreview(final boolean d) {
        this.d = d;
    }
    
    public boolean isAutoRemoveTemp() {
        return this.e;
    }
    
    public void setAutoRemoveTemp(final boolean e) {
        this.e = e;
    }
    
    @Override
    public void onDestroyView() {
        if (this.isAutoRemoveTemp()) {
            FileHelper.delete(this.getTempFilePath());
        }
        super.onDestroyView();
    }
    
    public void loadOrginImage(final TuSdkResult tuSdkResult) {
        if (tuSdkResult == null) {
            return;
        }
        final TuSdkSize limitSize = TuSdkSize.create(TuSdkGPU.getMaxTextureOptimizedSize()).limitSize();
        tuSdkResult.image = BitmapHelper.getBitmap(this.getTempFilePath(), limitSize, true);
        if (tuSdkResult.image == null) {
            tuSdkResult.image = BitmapHelper.getBitmap(this.getImageSqlInfo(), true, limitSize);
        }
        if (tuSdkResult.image == null) {
            tuSdkResult.image = this.getImage();
        }
    }
    
    public void processingImage() {
    }
    
    protected void loadImageWithThread() {
        this.hubStatus(TuSdkContext.getString("lsq_edit_loading"));
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                final Bitmap asyncLoadImage = TuImageResultFragment.this.asyncLoadImage();
                TuImageResultFragment.this.runOnUiThread(new Runnable() {
                    final /* synthetic */ boolean a = TuImageResultFragment.this.preProcessWithImage(asyncLoadImage);
                    
                    @Override
                    public void run() {
                        if (!this.a) {
                            TuImageResultFragment.this.hubDismiss();
                        }
                        TuImageResultFragment.this.asyncLoadImageCompleted(asyncLoadImage);
                        if (this.a) {
                            TuImageResultFragment.this.postProcessWithImage(asyncLoadImage);
                        }
                    }
                });
            }
        });
    }
    
    protected boolean preProcessWithImage(final Bitmap bitmap) {
        return false;
    }
    
    protected Bitmap asyncLoadImage() {
        return this.getImage();
    }
    
    protected void asyncLoadImageCompleted(final Bitmap bitmap) {
        if (bitmap == null) {
            this.notifyError(null, ComponentErrorType.TypeInputImageEmpty);
            TLog.e("Can not find input image.", new Object[0]);
        }
    }
    
    protected void postProcessWithImage(final Bitmap bitmap) {
        this.hubDismiss();
    }
    
    public Bitmap getCuterImage(final Bitmap bitmap, final TuSdkResult tuSdkResult) {
        if (tuSdkResult == null) {
            return bitmap;
        }
        return this.getCuterImage(bitmap, tuSdkResult.cutRect, tuSdkResult.imageOrientation, 0.0f);
    }
    
    public Bitmap getCuterImage(Bitmap bitmap, final RectF rectF, final ImageOrientation imageOrientation, final float n) {
        if (bitmap == null) {
            return bitmap;
        }
        if (rectF != null) {
            bitmap = BitmapHelper.imageCorp(bitmap, rectF, imageOrientation);
        }
        else if (imageOrientation != null) {
            bitmap = BitmapHelper.imageRotaing(bitmap, imageOrientation);
        }
        else if (n > 0.0f) {
            bitmap = BitmapHelper.imageCorp(bitmap, n);
        }
        return bitmap;
    }
    
    public ImageView getPreview() {
        if (this.f == null && this.getRootView() != null && this.isShowResultPreview()) {
            (this.f = new ImageView((Context)this.getActivity())).setScaleType(ImageView.ScaleType.FIT_CENTER);
            this.f.setBackgroundColor(-7829368);
            this.getRootView().addView((View)this.f, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
            this.f.setOnClickListener(this.g);
            this.showViewIn((View)this.f, false);
        }
        return this.f;
    }
    
    protected boolean showResultPreview(final TuSdkResult tuSdkResult) {
        if (!this.isShowResultPreview()) {
            return false;
        }
        final ImageView preview = this.getPreview();
        if (this.isSaveToAlbum()) {
            tuSdkResult.image = BitmapHelper.getBitmap(tuSdkResult.imageSqlInfo);
        }
        else if (this.isSaveToTemp()) {
            tuSdkResult.image = BitmapHelper.getBitmap(tuSdkResult.imageFile);
        }
        else {
            this.hubDismiss();
        }
        TLog.d("showResultPreview: %s", tuSdkResult);
        preview.setImageBitmap(tuSdkResult.image);
        this.showViewIn((View)preview, true);
        return true;
    }
    
    protected void handleTest() {
        this.showViewIn((View)this.getPreview(), false);
    }
}
