// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.widget.smudge.FilterSmudgeView;
import com.example.myvideoeditorapp.kore.components.widget.smudge.SmudgeView;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.blurs.TuSDKBlurFilter;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushSize;

import java.io.IOException;


public abstract class TuEditWipeAndFilterFragmentBase extends TuImageResultFragment implements SmudgeView.SmudgeActionDelegate, SmudgeView.SmudgeViewDelegate
{
    private float a;
    private boolean b;
    
    public TuEditWipeAndFilterFragmentBase() {
        this.a = 0.2f;
        this.b = true;
    }
    
    public abstract SmudgeView getSmudgeView();
    
    public abstract ImageView getZoomInImage();
    
    public boolean isDisplayMagnifier() {
        return this.b;
    }
    
    public void setDisplayMagnifier(final boolean b) {
        this.b = b;
    }
    
    public float getBrushStrength() {
        return this.a;
    }
    
    public void setBrushStrength(final float a) {
        if (a >= 0.0f && a <= 1.0f) {
            this.a = a;
        }
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editWipeAndFilterFragment);
        if (this.getZoomInImage() != null) {
            this.showView((View)this.getZoomInImage(), false);
        }
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        if (this.getSmudgeView() != null) {
            final FilterSmudgeView filterSmudgeView = (FilterSmudgeView)this.getSmudgeView();
            filterSmudgeView.setActionDelegate(this);
            final FilterWrap a = this.a();
            final SelesParameters filterParameter = a.getFilterParameter();
            if (filterParameter != null) {
                filterParameter.setFilterArg("blurSize", this.getBrushStrength());
                a.setFilterParameter(filterParameter);
            }
            filterSmudgeView.setFilterWrap(a);
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().destroy();
        }
    }
    
    protected void setBrushSize(final BrushSize.SizeType brushSize) {
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().setBrushSize(brushSize);
        }
    }
    
    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }
    
    protected void handleUndoButton() {
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().undo();
        }
    }
    
    protected void handleRedoButton() {
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().redo();
        }
    }
    
    protected void handleOrigianlButtonDown() {
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().showOriginalImage(true);
        }
    }
    
    protected void handleOrigianlButtonUp() {
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().showOriginalImage(false);
        }
    }
    
    @Override
    public void onRefreshStepStatesWithHistories(final int n, final int n2) {
    }
    
    @Override
    public void onSmudgeChanged(final PointF pointF, final PointF pointF2, final int n, final int n2) {
        if (this.getZoomInImage() == null || !this.isDisplayMagnifier()) {
            return;
        }
        final ImageView zoomInImage = this.getZoomInImage();
        final int dip2px = TuSdkContext.dip2px(90.0f);
        if (pointF.x < 0.0f || pointF.x > n || pointF.y < 0.0f || pointF.y > n2) {
            this.showView((View)zoomInImage, false);
            return;
        }
        if (zoomInImage.getVisibility() != View.VISIBLE) {
            this.showView((View)zoomInImage, true);
        }
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)zoomInImage.getLayoutParams();
        if (pointF.x < dip2px + 50 && pointF.y < dip2px + 50) {
            layoutParams.removeRule(9);
            layoutParams.addRule(11);
        }
        else {
            layoutParams.removeRule(11);
            layoutParams.addRule(9);
        }
        zoomInImage.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        zoomInImage.setImageBitmap(this.a(pointF, n));
    }
    
    private Bitmap a(final PointF pointF, final int n) {
        final int dip2px = TuSdkContext.dip2px(90.0f);
        final SmudgeView smudgeView = this.getSmudgeView();
        final Bitmap originalBitmap = smudgeView.getOriginalBitmap();
        final Bitmap smudgeBitmap = smudgeView.getSmudgeBitmap();
        final Bitmap bitmap = Bitmap.createBitmap(dip2px, dip2px, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Rect rect = new Rect(0, 0, dip2px, dip2px);
        final Rect rect2 = new Rect();
        final float n2 = originalBitmap.getWidth() / (float)n / 2.0f;
        rect2.left = (int)(pointF.x - dip2px * n2 / 2.0f);
        rect2.top = (int)(pointF.y - dip2px * n2 / 2.0f);
        rect2.right = rect2.left + (int)(dip2px * n2);
        rect2.bottom = rect2.top + (int)(dip2px * n2);
        canvas.drawBitmap(originalBitmap, rect2, rect, (Paint)null);
        final float n3 = smudgeBitmap.getWidth() / (float)n / 2.0f;
        rect2.left = (int)(pointF.x - dip2px * n3 / 2.0f);
        rect2.top = (int)(pointF.y - dip2px * n3 / 2.0f);
        rect2.right = rect2.left + (int)(dip2px * n3);
        rect2.bottom = rect2.top + (int)(dip2px * n3);
        canvas.drawBitmap(smudgeBitmap, rect2, rect, (Paint)null);
        final int brushSizePixel = smudgeView.getBrushSizePixel();
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-1);
        canvas.drawCircle((float)(dip2px / 2), (float)(dip2px / 2), (float)(brushSizePixel / 2), paint);
        return bitmap;
    }
    
    @Override
    public void onSmudgeEnd() {
        this.showView((View)this.getZoomInImage(), false);
    }
    
    private FilterWrap a() {
        final FilterOption filterOption = new FilterOption() {
            @Override
            public SelesOutInput getFilter() {
                return new TuSDKBlurFilter();
            }
        };
        filterOption.id = Long.MAX_VALUE;
        filterOption.canDefinition = true;
        filterOption.isInternal = true;
        return FilterWrap.creat(filterOption);
    }
    
    protected void handleCompleteButton() {
        if (this.getSmudgeView() == null) {
            this.handleBackButton();
            return;
        }
        final TuSdkResult tuSdkResult = new TuSdkResult();
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuEditWipeAndFilterFragmentBase.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        tuSdkResult.image = this.getSmudgeView().getCanvasImage(tuSdkResult.image, !this.isShowResultPreview());
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
