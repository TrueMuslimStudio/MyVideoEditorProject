// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;

public class ImageMarkFaceResult extends JsonBaseBean implements Serializable
{
    @DataBase("count")
    public int count;
    @DataBase("items")
    public ImageMark5FaceArgument<ImageMark5FaceArgument.ImageItems> items;
}
