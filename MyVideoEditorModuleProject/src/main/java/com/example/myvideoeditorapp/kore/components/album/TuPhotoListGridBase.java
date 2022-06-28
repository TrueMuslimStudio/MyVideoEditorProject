// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.example.myvideoeditorapp.kore.task.AlbumTaskManager;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellRelativeLayout;

public abstract class TuPhotoListGridBase extends TuSdkCellRelativeLayout<ImageSqlInfo>
{
    public TuPhotoListGridBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuPhotoListGridBase(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuPhotoListGridBase(final Context context) {
        super(context);
    }
    
    public abstract ImageView getPosterView();
    
    @Override
    protected void bindModel() {
        AlbumTaskManager.shared.loadThumbImage(this.getPosterView(), this.getModel());
    }
    
    @Override
    public void viewNeedRest() {
        ViewCompat.setAlpha((View)this.getPosterView(), 1.0f);
        AlbumTaskManager.shared.cancelLoadImage(this.getPosterView());
        if (this.getPosterView() != null) {
            this.getPosterView().setImageBitmap((Bitmap)null);
        }
        super.viewNeedRest();
    }
    
    @Override
    public void viewWillDestory() {
        this.viewNeedRest();
        super.viewWillDestory();
    }
}
