// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.paintdraw;

import android.view.ViewGroup;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuImageResultFragment;
import com.example.myvideoeditorapp.kore.components.ComponentActType;
import com.example.myvideoeditorapp.kore.components.widget.paintdraw.PaintDrawView;
import com.example.myvideoeditorapp.kore.components.widget.smudge.TuBrushSizeAnimView;
import com.example.myvideoeditorapp.kore.secret.StatisticsManger;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.view.widget.paintdraw.PaintData;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public abstract class TuEditPaintFragmentBase extends TuImageResultFragment implements PaintDrawView.PaintDrawViewDelagate
{
    public abstract PaintDrawView getPaintDrawView();
    
    public abstract TuBrushSizeAnimView getSizeAnimView();
    
    protected abstract List<PaintData> getColorList();
    
    @Override
    protected void notifyProcessing(final TuSdkResult tuSdkResult) {
    }
    
    @Override
    protected boolean asyncNotifyProcessing(final TuSdkResult tuSdkResult) {
        return false;
    }
    
    @Override
    protected void loadView(final ViewGroup viewGroup) {
        StatisticsManger.appendComponent(ComponentActType.editPaintSmudgeFragment);
        if (this.getSizeAnimView() != null) {
            this.showView(this.getSizeAnimView(), false);
        }
    }
    
    @Override
    protected void viewDidLoad(final ViewGroup viewGroup) {
    }
    
    @Override
    public void onRefreshStepStatesWithHistories(final int n, final int n2) {
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getPaintDrawView() != null) {
            this.getPaintDrawView().destroy();
        }
    }
    
    protected void handleBackButton() {
        this.navigatorBarBackAction(null);
    }
    
    public boolean selectPaint(final PaintData paintData) {
        if (paintData == null) {
            return false;
        }
        final Iterator<PaintData> iterator = this.getColorList().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getData().equals(paintData.getData())) {
                this.getPaintDrawView().setPaintColor((int)paintData.getData());
                return true;
            }
        }
        return false;
    }
    
    protected void handleUndoButton() {
        if (this.getPaintDrawView() != null) {
            this.getPaintDrawView().undo();
        }
    }
    
    protected void handleRedoButton() {
        if (this.getPaintDrawView() != null) {
            this.getPaintDrawView().redo();
        }
    }
    
    protected void handleOrigianlButtonDown() {
        if (this.getPaintDrawView() != null) {
            this.getPaintDrawView().showOriginalImage(true);
        }
    }
    
    protected void handleOrigianlButtonUp() {
        if (this.getPaintDrawView() != null) {
            this.getPaintDrawView().showOriginalImage(false);
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
    
    protected void handleCompleteButton() {
        if (this.getPaintDrawView() == null) {
            this.handleBackButton();
            return;
        }
        final TuSdkResult tuSdkResult = new TuSdkResult();
        this.hubStatus(TuSdkContext.getString("lsq_edit_processing"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TuEditPaintFragmentBase.this.asyncEditWithResult(tuSdkResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    protected void asyncEditWithResult(final TuSdkResult tuSdkResult) throws IOException {
        this.loadOrginImage(tuSdkResult);
        tuSdkResult.image = this.getPaintDrawView().getCanvasImage(tuSdkResult.image, !this.isShowResultPreview());
        TLog.d("TuEditEntryFragment editCompleted:%s", tuSdkResult);
        this.asyncProcessingIfNeedSave(tuSdkResult);
    }
}
