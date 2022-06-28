// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.smudge;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.widget.smudge.SmudgeView;
import com.example.myvideoeditorapp.kore.components.widget.smudge.TuBrushSizeAnimView;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushData;
import com.example.myvideoeditorapp.kore.view.widget.smudge.BrushLocalPackage;

import java.io.IOException;


public abstract class TuEditSmudgeFragmentBase extends TuImageResultFragment implements SmudgeView.SmudgeViewDelegate
{
    public abstract SmudgeView getSmudgeView();
    
    public abstract TuBrushSizeAnimView getSizeAnimView();
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editSmudgeFragment);
        if (this.getSizeAnimView() != null) {
            this.showView(this.getSizeAnimView(), false);
        }
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().destroy();
        }
    }
    
    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }
    
    public boolean selectBrushCode(final String s) {
        BrushData brush;
        if (s.equals("Eraser")) {
            brush = BrushLocalPackage.shared().getEeaserBrush();
        }
        else {
            brush = BrushLocalPackage.shared().getBrushWithCode(s);
        }
        if (brush == null) {
            return false;
        }
        if (this.getSmudgeView() != null) {
            this.getSmudgeView().setBrush(brush);
        }
        return true;
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
    
    protected void startSizeAnimation(final int n, final int n2) {
        final TuBrushSizeAnimView sizeAnimView = this.getSizeAnimView();
        if (sizeAnimView == null) {
            return;
        }
        sizeAnimView.changeRadius(n, n2);
        this.showView(this.getSizeAnimView(), true);
    }
    
    @Override
    public void onRefreshStepStatesWithHistories(final int n, final int n2) {
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
                    TuEditSmudgeFragmentBase.this.asyncEditWithResult(tuSdkResult);
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
