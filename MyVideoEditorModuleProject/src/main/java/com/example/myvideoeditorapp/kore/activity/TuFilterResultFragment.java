package com.example.myvideoeditorapp.kore.activity;

import android.graphics.Bitmap;
import android.os.Handler;

import android.view.ViewGroup;
import android.content.Context;

import android.widget.RelativeLayout;

import android.view.View;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.components.TuSdkComponentErrorListener;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageView;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageViewInterface;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterLocalPackage;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;
import com.example.myvideoeditorapp.kore.view.widget.ParameterConfigViewInterface;

import java.io.IOException;

public abstract class TuFilterResultFragment extends TuImageResultFragment implements ParameterConfigViewInterface.ParameterConfigViewDelegate
{
    private TuFilterResultFragmentDelegate a;
    private FilterWrap b;
    private FilterImageViewInterface c;
    protected View.OnClickListener mButtonClickListener;

    public TuFilterResultFragment() {
        this.mButtonClickListener = (View.OnClickListener)new TuSdkViewHelper.OnSafeClickListener() {
            @Override
            public void onSafeClick(final View view) {
                TuFilterResultFragment.this.dispatcherViewClick(view);
            }
        };
    }

    public TuFilterResultFragmentDelegate getDelegate() {
        return this.a;
    }

    public void setDelegate(final TuFilterResultFragmentDelegate a) {
        this.setErrorListener(this.a = a);
    }

    @Override
    protected void notifyProcessing(final TuSdkResult tuSdkResult) {
        if (this.showResultPreview(tuSdkResult)) {
            return;
        }
        if (this.a == null) {
            return;
        }
        this.a.onTuFilterResultFragmentEdited(this, tuSdkResult);
    }

    @Override
    protected boolean asyncNotifyProcessing(final TuSdkResult tuSdkResult) {
        return this.a != null && this.a.onTuFilterResultFragmentEditedAsync(this, tuSdkResult);
    }

    private FilterWrap a() {
        return this.b;
    }

    protected final void setFilterWrap(final FilterWrap b) {
        this.b = b;
    }

    protected SelesParameters getFilterParameter() {
        if (this.a() == null) {
            return null;
        }
        return this.a().getFilterParameter();
    }

    public abstract RelativeLayout getImageWrapView();

    public abstract View getCancelButton();

    public abstract View getCompleteButton();

    public abstract <T extends View> T getConfigView();

    public <T extends View> T getImageView() {
        if (this.c == null && this.getImageWrapView() != null) {
            (this.c = new FilterImageView(this.getActivity())).enableTouchForOrigin();
            final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.getImageWrapView().addView((View)this.c, 0, (ViewGroup.LayoutParams)layoutParams);
        }
        return (T)this.c;
    }

    protected void dispatcherViewClick(final View view) {
        if (this.equalViewIds(view, this.getCancelButton())) {
            this.handleBackButton();
        }
        else if (this.equalViewIds(view, this.getCompleteButton())) {
            this.handleCompleteButton();
        }
    }

    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }

    @Override
    protected void loadView(final ViewGroup viewGroup) {
        this.getCancelButton();
        this.getCompleteButton();
        this.getConfigView();
        this.getImageView();
    }

    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
        this.loadImageWithThread();
        if (this.getConfigView() == null) {
            return;
        }
        this.refreshConfigView();
    }

        protected void refreshConfigView() {
            (new Handler()).postDelayed(new Runnable() {
                public void run() {
                    SelesParameters var1 = TuFilterResultFragment.this.a().getFilterParameter();
                    if (var1 != null && var1.size() != 0) {
                        ((ParameterConfigViewInterface)TuFilterResultFragment.this.getConfigView()).setParams(var1.getArgKeys(), 0);
                    }
                }
            }, 1L);
        }

    @Override
    protected void asyncLoadImageCompleted(Bitmap var1) {
        super.asyncLoadImageCompleted(var1);
        if (var1 != null) {
            if (this.getImageView() != null && this.getConfigView() != null) {
                ((FilterImageViewInterface)this.getImageView()).setImage(var1);
                ((FilterImageViewInterface)this.getImageView()).setFilterWrap(this.a());
            }
        }
    }

    protected void setImageViewFilter(FilterWrap var1) {
        this.setFilterWrap(var1);
        if (this.getImageView() != null) {
            if (var1 == null) {
                var1 = FilterLocalPackage.shared().getFilterWrap((String)null);
            }

            ((FilterImageViewInterface)this.getImageView()).setFilterWrap(var1);
        }
    }

    @Override
    public void onParameterConfigDataChanged(final ParameterConfigViewInterface parameterConfigViewInterface, final int n, final float precentValue) {
        final SelesParameters.FilterArg filterArg = this.getFilterArg(n);
        if (filterArg == null) {
            return;
        }
        filterArg.setPrecentValue(precentValue);
        this.requestRender();
    }

    @Override
    public void onParameterConfigRest(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        final SelesParameters.FilterArg filterArg = this.getFilterArg(n);
        if (filterArg == null) {
            return;
        }
        filterArg.reset();
        this.requestRender();
        parameterConfigViewInterface.seekTo(filterArg.getPrecentValue());
    }

    @Override
    public float readParameterValue(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        final SelesParameters.FilterArg filterArg = this.getFilterArg(n);
        if (filterArg == null) {
            return 0.0f;
        }
        return filterArg.getPrecentValue();
    }

    protected SelesParameters.FilterArg getFilterArg(final int n) {
        if (n < 0) {
            return null;
        }
        final SelesParameters filterParameter = this.a().getFilterParameter();
        if (filterParameter == null || n >= filterParameter.size()) {
            return null;
        }
        return filterParameter.getArgs().get(n);
    }

    protected void requestRender() {
        if (this.getImageView() != null) {
            ((FilterImageViewInterface)this.getImageView()).requestRender();
        }

    }

    protected void handleCompleteButton() {
        final TuSdkResult tuSdkResult = new TuSdkResult();
        tuSdkResult.filterWrap = this.a();
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuFilterResultFragment.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        if (tuSdkResult.filterWrap != null) {
            tuSdkResult.image = BitmapHelper.imageScale(tuSdkResult.image, TuSdkSize.create(tuSdkResult.image).limitScale());
            tuSdkResult.image = tuSdkResult.filterWrap.clone().process(tuSdkResult.image);
        }
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }

    public interface TuFilterResultFragmentDelegate extends TuSdkComponentErrorListener
    {
        void onTuFilterResultFragmentEdited(final TuFilterResultFragment p0, final TuSdkResult p1);

        boolean onTuFilterResultFragmentEditedAsync(final TuFilterResultFragment p0, final TuSdkResult p1);
    }
}
