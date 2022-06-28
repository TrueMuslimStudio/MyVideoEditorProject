// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.filter;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import com.example.myvideoeditorapp.kore.activity.TuFilterResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.sources.SelesOutInput;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterImageViewInterface;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterOption;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.seles.tusdk.filters.blurs.TuSDKApertureFilter;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.utils.TuSdkGestureRecognizer;
import com.example.myvideoeditorapp.kore.view.widget.ParameterConfigViewInterface;


import java.util.ArrayList;

public abstract class TuEditApertureFragmentBase extends TuFilterResultFragment
{
    private int a;
    private MaskAnimation b;
    private boolean c;
    private Runnable d;
    private TuSdkGestureRecognizer e;
    
    public TuEditApertureFragmentBase() {
        this.d = new Runnable() {
            @Override
            public void run() {
                TuEditApertureFragmentBase.this.b();
            }
        };
        this.e = new TuSdkGestureRecognizer() {
            @Override
            public void onTouchBegin(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent) {
                TuEditApertureFragmentBase.this.a(true);
            }
            
            @Override
            public void onTouchEnd(final TuSdkGestureRecognizer tuSdkGestureRecognizer, final View view, final MotionEvent motionEvent, final StepData stepData) {
                TuEditApertureFragmentBase.this.a(false);
            }

            public void onTouchSingleMove(TuSdkGestureRecognizer var1, View var2, MotionEvent var3, StepData var4) {
                SelesParameters var5 = TuEditApertureFragmentBase.this.getFilterParameter();
                if (var5 != null) {
                    var5.stepFilterArg("centerX", var1.getStepPoint().x / (float)var2.getWidth());
                    var5.stepFilterArg("centerY", var1.getStepPoint().y / (float)var2.getHeight());
                    TuEditApertureFragmentBase.this.requestRender();
                }
            }
            public void onTouchMultipleMove(TuSdkGestureRecognizer var1, View var2, MotionEvent var3, StepData var4) {
                SelesParameters var5 = TuEditApertureFragmentBase.this.getFilterParameter();
                if (var5 != null) {
                    var5.stepFilterArg("radius", var1.getStepSpace() / var1.getSpace());
                    SelesParameters.FilterArg var6 = var5.getFilterArg("degree");
                    if (var6 != null) {
                        float var7 = var6.getPrecentValue() + var1.getStepDegree() / 360.0F;
                        if (var7 < 0.0F) {
                            ++var7;
                        } else if (var7 > 1.0F) {
                            --var7;
                        }

                        var6.setPrecentValue(var7);
                    }

                    TuEditApertureFragmentBase.this.requestRender();
                }
            }
        };
    }
    
    protected abstract void setConfigViewShowState(final boolean p0);

    protected void loadView(ViewGroup var1) {
        super.loadView(var1);
        StatisticsManger.appendComponent(ComponentActType.editApertureFragment);
        this.setFilterWrap(this.f());
        if (this.getImageView() != null) {
            ((FilterImageViewInterface)this.getImageView()).disableTouchForOrigin();
        }

        if (this.getImageWrapView() != null) {
            this.getImageWrapView().setOnTouchListener(this.e);
        }

    }
    
    @Override
    public void onParameterConfigDataChanged(final ParameterConfigViewInterface parameterConfigViewInterface, final int n, final float n2) {
        super.onParameterConfigDataChanged(parameterConfigViewInterface, 0, n2);
    }
    
    @Override
    public void onParameterConfigRest(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        super.onParameterConfigRest(parameterConfigViewInterface, 0);
    }
    
    @Override
    public float readParameterValue(final ParameterConfigViewInterface parameterConfigViewInterface, final int n) {
        return super.readParameterValue(parameterConfigViewInterface, 0);
    }
    
    protected void handleSelectiveAction(final int a, final float n) {
        if (this.a == a) {
            if (this.a > 0) {
                this.a();
            }
            return;
        }
        this.a = a;
        this.a(n);
    }
    
    private void a(final float n) {
        final SelesParameters filterParameter = this.getFilterParameter();
        if (filterParameter == null) {
            return;
        }
        filterParameter.reset();
        filterParameter.setFilterArg("selective", n);
        this.onParameterConfigRest(this.getConfigView(), 0);
        if (n > 0.0f) {
            this.a();
        }
    }

    private void a() {
        if (this.getConfigView() != null) {
            ArrayList var1 = new ArrayList();
            var1.add("aperture");
            ((ParameterConfigViewInterface)this.getConfigView()).setParams(var1, 0);
            this.setConfigViewShowState(true);
            this.a(true);
            this.c();
        }
    }
    
    private void b() {
        this.a(false);
    }
    
    private void c() {
        ThreadHelper.postDelayed(this.d, 1000L);
    }
    
    private void d() {
        ThreadHelper.cancel(this.d);
    }
    
    private void a(final boolean c) {
        this.d();
        if (this.c == c) {
            return;
        }
        this.c = c;
        this.getImageWrapView().startAnimation((Animation)this.e());
    }
    
    private MaskAnimation e() {
        if (this.b == null) {
            (this.b = new MaskAnimation()).setDuration(260L);
            this.b.setInterpolator((Interpolator)new AccelerateDecelerateInterpolator());
        }
        this.b.cancel();
        this.b.reset();
        return this.b;
    }
    
    private void b(float n) {
        final SelesParameters filterParameter = this.getFilterParameter();
        if (filterParameter == null) {
            return;
        }
        if (!this.c) {
            n = 1.0f - n;
        }
        filterParameter.setFilterArg("maskAlpha", n);
        this.requestRender();
    }
    
    protected void handleConfigCompeleteButton() {
        this.setConfigViewShowState(false);
    }
    
    @Override
    protected void handleCompleteButton() {
        this.d();
        this.e().cancel();
        final SelesParameters filterParameter = this.getFilterParameter();
        if (filterParameter == null) {
            return;
        }
        filterParameter.reset("maskAlpha");
        this.requestRender();
        super.handleCompleteButton();
    }
    
    private FilterWrap f() {
        final FilterOption filterOption = new FilterOption() {
            @Override
            public SelesOutInput getFilter() {
                return new TuSDKApertureFilter();
            }
        };
        filterOption.id = Long.MAX_VALUE;
        filterOption.canDefinition = true;
        filterOption.isInternal = true;
        return FilterWrap.creat(filterOption);
    }
    
    private class MaskAnimation extends Animation
    {
        protected void applyTransformation(final float n, final Transformation transformation) {
            TuEditApertureFragmentBase.this.b(n);
        }
    }
}
