// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.sqllite;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import android.database.Cursor;
import com.example.myvideoeditorapp.kore.utils.image.AlbumHelper;
import java.io.File;

public class AlbumSqlInfo extends SqlLiteInfo
{
    public static final String BUCKET_TOTAL = "bucket_total";
    public static final String CAMERA_FOLDER = "Camera";
    public long id;
    private boolean a;
    public String title;
    public int total;
    public ImageSqlInfo cover;
    
    public static File cameraFolder() {
        return AlbumHelper.getAblumPath("Camera");
    }
    
    public AlbumSqlInfo() {
    }
    
    public AlbumSqlInfo(final Cursor cursor) {
        this(cursor, true);
    }
    
    public AlbumSqlInfo(final Cursor infoWithCursor, final boolean a) {
        this.a = a;
        this.setInfoWithCursor(infoWithCursor);
    }
    
    @Override
    public void setInfoWithCursor(final Cursor cursor) {
        if (cursor == null) {
            return;
        }
        this.id = this.getCursorLong(cursor, "bucket_id");
        this.title = this.getCursorString(cursor, "bucket_display_name");
        if (this.a) {
            this.total = this.getCursorInt(cursor, "bucket_total");
        }
    }
    
    @Override
    public String toString() {
        return String.format("{id: %s, title: %s, total: %s, cover: %s}", this.id, this.title, this.total, this.cover);
    }

    public static void sortTitle(ArrayList<AlbumSqlInfo> var0) {
        if (var0 != null) {
            Collections.sort(var0, new Comparator<AlbumSqlInfo>() {
                public int compare(AlbumSqlInfo var1, AlbumSqlInfo var2) {
                    return var1.title != null && var2.title != null ? var1.title.compareToIgnoreCase(var2.title) : -1;
                }
            });
        }
    }}
