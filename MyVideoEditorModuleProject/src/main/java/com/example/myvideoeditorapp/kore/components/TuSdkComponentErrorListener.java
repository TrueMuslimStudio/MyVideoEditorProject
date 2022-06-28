package com.example.myvideoeditorapp.kore.components;


import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.activity.TuFragment;

public interface TuSdkComponentErrorListener
{
    void onComponentError(final TuFragment p0, final TuSdkResult p1, final Error p2);
}
