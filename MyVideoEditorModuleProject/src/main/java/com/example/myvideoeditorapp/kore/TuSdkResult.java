// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore;

import android.graphics.RectF;

import android.net.Uri;
import android.graphics.Bitmap;

import com.example.myvideoeditorapp.kore.exif.ExifInterface;
import com.example.myvideoeditorapp.kore.seles.SelesParameters;
import com.example.myvideoeditorapp.kore.seles.tusdk.FilterWrap;
import com.example.myvideoeditorapp.kore.struct.TuSdkSize;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.image.BitmapHelper;
import com.example.myvideoeditorapp.kore.utils.image.ExifHelper;
import com.example.myvideoeditorapp.kore.utils.image.ImageOrientation;
import com.example.myvideoeditorapp.kore.utils.sqllite.ImageSqlInfo;
import com.example.myvideoeditorapp.kore.view.widget.sticker.StickerResult;

import java.io.File;
import java.util.List;

public class TuSdkResult
{
    public byte[] imageData;
    public File imageFile;
    public Bitmap image;
    public String filterCode;
    public FilterWrap filterWrap;
    public ExifInterface metadata;
    public Uri uri;
    public ImageSqlInfo imageSqlInfo;
    public List<ImageSqlInfo> images;
    public TuSdkSize outputSize;
    public float imageSizeRatio;
    public Object extendData;
    public ImageOrientation imageOrientation;
    public RectF cutRect;
    public int cutRatioType;
    public float cutScale;
    public SelesParameters filterParams;
    public List<StickerResult> stickers;
    
    public void fixedMetadata() {
        if (this.metadata == null || this.image == null) {
            return;
        }
        this.metadata.setTagValue(ExifInterface.TAG_IMAGE_WIDTH, this.image.getWidth());
        this.metadata.setTagValue(ExifInterface.TAG_IMAGE_LENGTH, this.image.getHeight());
        this.metadata.setTagValue(ExifInterface.TAG_ORIENTATION, ImageOrientation.Up.getExifOrientation());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        if (this.image != null) {
            sb.append(String.format("image (%s): %s\n", this.imageOrientation, TuSdkSize.create(this.image)));
        }
        if (this.imageFile != null) {
            sb.append(String.format("imageFile: %s\n", this.imageFile));
        }
        if (this.filterCode != null) {
            sb.append(String.format("filterCode: %s\n", this.filterCode));
        }
        if (this.uri != null) {
            sb.append(String.format("uri: %s\n", this.uri));
        }
        if (this.imageSqlInfo != null) {
            sb.append(String.format("sqlInfo: %s\n", this.imageSqlInfo));
        }
        if (this.outputSize != null) {
            sb.append(String.format("outputSize: %s\n", this.outputSize));
        }
        if (this.cutRect != null) {
            sb.append(String.format("cutRect: %s\n", this.cutRect));
        }
        if (this.images != null) {
            sb.append(String.format("images: %s\n", this.images));
        }
        return sb.toString();
    }
    
    public void destroy() {
        this.imageData = null;
        this.imageFile = null;
        BitmapHelper.recycled(this.image);
        this.filterWrap = null;
        this.metadata = null;
        this.uri = null;
        this.imageSqlInfo = null;
        this.images = null;
        this.outputSize = null;
        this.extendData = null;
        this.imageOrientation = null;
        this.cutRect = null;
        this.filterParams = null;
        this.stickers = null;
    }
    
    public void logInfo() {
        this.logInfo(false);
    }
    
    public void logInfo(final boolean b) {
        TLog.d("TuSdkResult:\r%s", this.toString());
        if (b && this.metadata != null) {
            ExifHelper.log(this.metadata.getAllTags());
        }
    }
}
