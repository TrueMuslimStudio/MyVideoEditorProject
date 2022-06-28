// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.components.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.myvideoeditorapp.kore.task.AlbumTaskManager;
import com.example.myvideoeditorapp.kore.utils.sqllite.AlbumSqlInfo;
import com.example.myvideoeditorapp.kore.view.listview.TuSdkCellRelativeLayout;

public abstract class TuAlbumListCellBase extends TuSdkCellRelativeLayout<AlbumSqlInfo>
{
    public TuAlbumListCellBase(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public TuAlbumListCellBase(final Context context, final AttributeSet set) {
        super(context, set);
    }
    
    public TuAlbumListCellBase(final Context context) {
        super(context);
    }
    
    public abstract ImageView getPosterView();
    
    @Override
    protected void bindModel() {
        final AlbumSqlInfo albumSqlInfo = this.getModel();
        if (albumSqlInfo == null) {
            return;
        }
        this.a(albumSqlInfo);
    }
    
    private void a(final AlbumSqlInfo albumSqlInfo) {
        final ImageView posterView = this.getPosterView();
        if (posterView == null) {
            return;
        }
        if (albumSqlInfo == null) {
            posterView.setImageBitmap((Bitmap)null);
            return;
        }
        AlbumTaskManager.shared.loadThumbImage(posterView, albumSqlInfo.cover);
    }
    
    @Override
    public void viewNeedRest() {
        AlbumTaskManager.shared.cancelLoadImage(this.getPosterView());
        this.a((AlbumSqlInfo)null);
        super.viewNeedRest();
    }
    
    @Override
    public void viewWillDestory() {
        this.viewNeedRest();
        super.viewWillDestory();
    }
}
