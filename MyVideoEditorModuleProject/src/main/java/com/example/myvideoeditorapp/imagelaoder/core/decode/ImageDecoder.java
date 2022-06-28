package com.example.myvideoeditorapp.imagelaoder.core.decode;

import android.graphics.Bitmap;

import java.io.IOException;

public interface ImageDecoder {
    Bitmap decode(ImageDecodingInfo var1) throws IOException;
}