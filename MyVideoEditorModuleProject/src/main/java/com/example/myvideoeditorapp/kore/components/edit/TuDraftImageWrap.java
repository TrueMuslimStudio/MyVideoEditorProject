package com.example.myvideoeditorapp.kore.components.edit;

import java.util.ArrayList;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.TuSdkContext;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import com.example.myvideoeditorapp.kore.utils.FileHelper;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;

import java.io.File;
import java.util.List;

public class TuDraftImageWrap
{
    protected float mScreenSizeScale;
    private final List<File> a;
    private final List<File> b;
    private File c;
    private ImageSqlInfo d;
    private Bitmap e;
    
    public TuDraftImageWrap() {
        this.mScreenSizeScale = 0.75f;
        this.a = new ArrayList<File>();
        this.b = new ArrayList<File>();
    }
    
    public File getTempFilePath() {
        return this.c;
    }
    
    public void setTempFilePath(final File c) {
        this.c = c;
    }
    
    public ImageSqlInfo getImageSqlInfo() {
        return this.d;
    }
    
    public void setImageSqlInfo(final ImageSqlInfo d) {
        this.d = d;
    }
    
    public List<File> getHistories() {
        return this.a;
    }
    
    public void setHistories(final List<File> list) {
        this.a.clear();
        this.a.addAll(list);
    }
    
    public List<File> getBrushies() {
        return this.b;
    }
    
    public void addBrushie(final File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (this.b.contains(file)) {
            return;
        }
        this.b.add(file);
    }
    
    public void setBrushies(final List<File> list) {
        this.b.clear();
        this.b.addAll(list);
    }
    
    public int getHistoriesSize() {
        return this.getHistories().size();
    }
    
    public int getBrushiesSize() {
        return this.getBrushies().size();
    }
    
    public Bitmap getImage() {
        return this.getImage(1);
    }
    
    public Bitmap getImage(final int n) {
        Bitmap bitmap = null;
        final TuSdkSize tuSdkSize = new TuSdkSize(n, n);
        if (this.getLastSteps() != null) {
            bitmap = BitmapHelper.getBitmap(this.getLastSteps(), true);
        }
        if (bitmap == null) {
            bitmap = BitmapHelper.getBitmap(this.getTempFilePath(), tuSdkSize, true);
        }
        if (bitmap == null) {
            bitmap = BitmapHelper.getBitmap(this.getImageSqlInfo(), true, tuSdkSize);
        }
        if (bitmap == null) {
            return this.e;
        }
        return bitmap;
    }
    
    public Bitmap getThumbImage(final int n, final int n2) {
        final TuSdkSize tuSdkSize = new TuSdkSize(n, n2);
        Bitmap bitmap = null;
        if (this.getLastSteps() != null) {
            bitmap = BitmapHelper.getBitmap(this.getLastSteps(), tuSdkSize, true);
        }
        if (bitmap == null) {
            bitmap = BitmapHelper.getBitmap(this.getTempFilePath(), tuSdkSize, true);
        }
        if (bitmap == null) {
            bitmap = BitmapHelper.getBitmap(this.getImageSqlInfo(), true, tuSdkSize);
        }
        return bitmap;
    }
    
    public void setImage(final Bitmap e) {
        this.e = e;
    }
    
    public File getLastSteps() {
        if (this.a.size() == 0) {
            return null;
        }
        return this.a.get(this.a.size() - 1);
    }
    
    protected File popLastSteps() {
        final File lastSteps = this.getLastSteps();
        if (lastSteps != null) {
            this.a.remove(this.a.size() - 1);
        }
        return lastSteps;
    }
    
    protected ImageSqlInfo getOutputImageSqlInfo() {
        final ImageSqlInfo imageSqlInfo = new ImageSqlInfo();
        File file = this.popLastSteps();
        if (file == null) {
            file = this.getTempFilePath();
        }
        if (file == null) {
            return this.getImageSqlInfo();
        }
        imageSqlInfo.path = file.getAbsolutePath();
        return imageSqlInfo;
    }
    
    public TuSdkSize getImageDisplaySize() {
        final TuSdkSize screenSize = ContextUtils.getScreenSize(TuSdkContext.context());
        if (screenSize != null) {
            screenSize.width = (int)Math.floor(screenSize.width * this.mScreenSizeScale);
            screenSize.height = (int)Math.floor(screenSize.height * this.mScreenSizeScale);
        }
        return screenSize;
    }
    
    public boolean isChanged() {
        return (this.a != null && this.a.size() > 1) || this.isFromCarmera();
    }
    
    public boolean isFromCarmera() {
        return this.getImageSqlInfo() == null;
    }
    
    protected void clearAllSteps() {
        this.clearSteps(this.a);
        this.clearSteps(this.b);
    }
    
    protected void clearSteps(final List<File> list) {
        if (list == null) {
            return;
        }
        for (final File file : list) {
            FileHelper.delete(file);
        }
        list.clear();
    }
}
