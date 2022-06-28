// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuFilterResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.face.FaceAligment;
import com.example.myvideoeditorapp.kore.network.analysis.ImageMark5FaceArgument;
import com.example.myvideoeditorapp.kore.network.analysis.ImageMarkFaceAnalysis;
import com.example.myvideoeditorapp.kore.network.analysis.ImageMarkFaceResult;
import com.example.myvideoeditorapp.kore.network.analysis.ImageOnlineAnalysis;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.skins.TuSDKSkinWhiteningFilter;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.view.widget.ParameterConfigViewInterface;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class TuEditSkinFragmentBase extends TuFilterResultFragment
{
    private ImageMarkFaceAnalysis a;
    private TuSDKSkinWhiteningFilter b;
    private PointF[] c;
    protected float mRetouchSize;
    private int d;
    private float e;
    private ImageMarkFaceAnalysis.ImageFaceMarkAnalysisListener f;
    
    public TuEditSkinFragmentBase() {
        this.mRetouchSize = 1.0f;
        this.d = -1;
        this.f = new ImageMarkFaceAnalysis.ImageFaceMarkAnalysisListener() {
            @Override
            public void onImageFaceAnalysisCompleted(ImageMarkFaceResult var1, ImageOnlineAnalysis.ImageAnalysisType var2) {
                if (var2 == ImageOnlineAnalysis.ImageAnalysisType.Succeed) {
                    if (var1 != null && var1.count > 0) {
                        if (var1.count == 1) {
                            TuEditSkinFragmentBase.this.c = TuEditSkinFragmentBase.this.a(var1);
                            FaceAligment var3 = new FaceAligment();
                            var3.setOrginMarks(TuEditSkinFragmentBase.this.c);
                            TuEditSkinFragmentBase.this.b.updateFaceFeatures(new FaceAligment[]{var3}, 0.0F);
                            TuEditSkinFragmentBase.this.requestRender();
                            TuEditSkinFragmentBase.this.hubDismiss();
                            TuEditSkinFragmentBase.this.onFaceDetectionResult(true);
                            return;
                        }

                        TuEditSkinFragmentBase.this.hubError(TuSdkContext.getString("lsq_edit_process_error_multi_face"));
                    } else {
                        TuEditSkinFragmentBase.this.hubError(TuSdkContext.getString("lsq_edit_process_error_no_face"));
                    }
                } else if (var2 == ImageOnlineAnalysis.ImageAnalysisType.NoAccessRight) {
                    TLog.e("You are not allowed to use the face mark api, please see http://tusdk.com", new Object[0]);
                    TuEditSkinFragmentBase.this.hubError(TuSdkContext.getString("lsq_edit_process_error_no_face_access"));
                } else {
                    TLog.e("error on face mark :%s", new Object[]{var2});
                    TuEditSkinFragmentBase.this.hubError(TuSdkContext.getString("lsq_edit_process_skin_error"));
                }

                TuEditSkinFragmentBase.this.onFaceDetectionResult(false);
            }
        };
    }
    
    public void setRetouchSize(final float mRetouchSize) {
        this.mRetouchSize = mRetouchSize;
    }
    
    protected abstract void setConfigViewShowState(final boolean p0);
    
    protected abstract View buildActionButton(final String p0, final int p1);
    
    protected abstract void onFaceDetectionResult(final boolean p0);
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editSkinFragment);
        this.setFilterWrap(this.a());
        super.loadView(viewGroup);
        this.buildActionButtons();
    }
    
    protected void buildActionButtons() {
        final SelesParameters filterParameter = this.getFilterParameter();
        if (filterParameter == null || filterParameter.size() == 0) {
            return;
        }
        int n = 0;
        final Iterator<String> iterator = filterParameter.getArgKeys().iterator();
        while (iterator.hasNext()) {
            this.buildActionButton(iterator.next(), n);
            ++n;
        }
    }

    protected void handleAction(Integer var1) {
        this.d = var1;
        this.e = this.readParameterValue((ParameterConfigViewInterface)this.getConfigView(), this.d);
        if (this.getConfigView() != null) {
            SelesParameters var2 = this.getFilterParameter();
            if (var2.size() > this.d) {
                String var3 = (String)var2.getArgKeys().get(this.d);
                if (var3 != null) {
                    ArrayList var4 = new ArrayList();
                    var4.add(var3);
                    ((ParameterConfigViewInterface)this.getConfigView()).setParams(var4, 0);
                    this.setConfigViewShowState(true);
                }
            }
        }
    }
    
    public int getCurrentAction() {
        return this.d;
    }
    
    protected void handleConfigCompeleteButton() {
        this.setConfigViewShowState(false);
    }
    
    protected void handleConfigCancel() {
        this.getFilterArg(this.d).setPrecentValue(this.e);
        this.requestRender();
        this.setConfigViewShowState(false);
    }
    
    @Override
    public void onParameterConfigDataChanged(final ParameterConfigViewInterface parameterConfigViewInterface, final int n, final float n2) {
        super.onParameterConfigDataChanged(parameterConfigViewInterface, this.d, n2);
    }
    
    @Override
    public void onParameterConfigRest(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        super.onParameterConfigRest(parameterConfigViewInterface, this.d);
    }
    
    @Override
    public float readParameterValue(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        return super.readParameterValue(parameterConfigViewInterface, this.d);
    }
    
    private FilterWrap a() {
        final FilterOption filterOption = new FilterOption() {
            @Override
            public SelesOutInput getFilter() {
                final TuSDKSkinWhiteningFilter tuSDKSkinWhiteningFilter = new TuSDKSkinWhiteningFilter();
                tuSDKSkinWhiteningFilter.setRetouchSize(TuEditSkinFragmentBase.this.mRetouchSize);
                TuEditSkinFragmentBase.this.b = tuSDKSkinWhiteningFilter;
                return tuSDKSkinWhiteningFilter;
            }
        };
        filterOption.id = Long.MAX_VALUE;
        filterOption.canDefinition = true;
        filterOption.isInternal = true;
        final ArrayList<String> internalTextures = new ArrayList<String>();
        internalTextures.add("f8a6ed3ec939d6941c94a272aff1791b");
        filterOption.internalTextures = internalTextures;
        return FilterWrap.creat(filterOption);
    }
    
    @Override
    protected void asyncLoadImageCompleted(final Bitmap bitmap) {
        super.asyncLoadImageCompleted(bitmap);
        if (bitmap != null) {
            this.startImageMarkFaceAnalysis(bitmap);
        }
    }
    
    protected void startImageMarkFaceAnalysis(final Bitmap bitmap) {
        if (this.a == null) {
            this.a = new ImageMarkFaceAnalysis();
        }
        else {
            this.a.reset();
        }
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        ThreadHelper.runThread(new Runnable() {
            @Override
            public void run() {
                TuEditSkinFragmentBase.this.a.analysisWithThumb(bitmap, TuEditSkinFragmentBase.this.f);
            }
        });
    }
    
    private PointF[] a(final ImageMarkFaceResult imageMarkFaceResult) {
        final PointF[] array = new PointF[5];
        int n = 0;
        final ImageMark5FaceArgument.ImageItems imageItems = imageMarkFaceResult.items.get(0);
        final ImageMark5FaceArgument.ImageMarksPoints eye_left = imageItems.marks.eye_left;
        array[n++] = new PointF(eye_left.x, eye_left.y);
        final ImageMark5FaceArgument.ImageMarksPoints eye_right = imageItems.marks.eye_right;
        array[n++] = new PointF(eye_right.x, eye_right.y);
        final ImageMark5FaceArgument.ImageMarksPoints nose = imageItems.marks.nose;
        array[n++] = new PointF(nose.x, nose.y);
        final ImageMark5FaceArgument.ImageMarksPoints mouth_left = imageItems.marks.mouth_left;
        array[n++] = new PointF(mouth_left.x, mouth_left.y);
        final ImageMark5FaceArgument.ImageMarksPoints mouth_right = imageItems.marks.mouth_right;
        array[n++] = new PointF(mouth_right.x, mouth_right.y);
        return array;
    }
    
    @Override
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        if (tuSdkResult.filterWrap != null && tuSdkResult.image != null) {
            tuSdkResult.image = BitmapHelper.imageScale(tuSdkResult.image, TuSdkSize.create(tuSdkResult.image).limitScale());
            final FilterWrap clone = tuSdkResult.filterWrap.clone();
            final TuSDKSkinWhiteningFilter tuSDKSkinWhiteningFilter = (TuSDKSkinWhiteningFilter)clone.getFilter();
            if (tuSDKSkinWhiteningFilter != null) {
                final FaceAligment faceAligment = new FaceAligment();
                faceAligment.setOrginMarks(this.c);
                tuSDKSkinWhiteningFilter.updateFaceFeatures(new FaceAligment[] { faceAligment }, 0.0f);
            }
            tuSdkResult.image = clone.process(tuSdkResult.image);
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
