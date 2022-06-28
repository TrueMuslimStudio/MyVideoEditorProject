// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;

public class ImageAnalysisResult extends JsonBaseBean implements Serializable
{
    @DataBase("color")
    public ImageColorArgument color;
}
