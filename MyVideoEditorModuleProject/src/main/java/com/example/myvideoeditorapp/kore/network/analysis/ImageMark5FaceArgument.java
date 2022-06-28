// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.network.analysis;

import com.example.myvideoeditorapp.kore.utils.json.DataBase;
import com.example.myvideoeditorapp.kore.utils.json.JsonBaseBean;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageMark5FaceArgument<T extends JsonBaseBean> extends ArrayList<T>
{
    public static class ImageMarksPoints extends JsonBaseBean implements Serializable
    {
        @DataBase("x")
        public float x;
        @DataBase("y")
        public float y;
    }
    
    public static class ImageMarks extends JsonBaseBean implements Serializable
    {
        @DataBase("eye_left")
        public ImageMarksPoints eye_left;
        @DataBase("eye_right")
        public ImageMarksPoints eye_right;
        @DataBase("nose")
        public ImageMarksPoints nose;
        @DataBase("mouth_left")
        public ImageMarksPoints mouth_left;
        @DataBase("mouth_right")
        public ImageMarksPoints mouth_right;
    }
    
    public static class ImageItems extends JsonBaseBean implements Serializable
    {
        @DataBase("marks")
        public ImageMarks marks;
    }
}
