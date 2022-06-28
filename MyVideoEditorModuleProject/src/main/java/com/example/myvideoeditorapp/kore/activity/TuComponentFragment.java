// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;

import com.example.myvideoeditorapp.kore.TuSdkResult;
import com.example.myvideoeditorapp.kore.components.ComponentErrorType;
import com.example.myvideoeditorapp.kore.components.TuSdkComponentErrorListener;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.ThreadHelper;
import com.example.myvideoeditorapp.kore.view.TuSdkViewHelper;

public abstract class TuComponentFragment extends TuFragment
{
    private TuSdkComponentErrorListener a;
    protected TuSdkViewHelper.AlertDelegate permissionAlertDelegate;
    
    public TuComponentFragment() {
        this.permissionAlertDelegate = new TuSdkViewHelper.AlertDelegate() {
            @Override
            public void onAlertConfirm(final AlertDialog alertDialog) {
                final Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", TuComponentFragment.this.getContext().getPackageName(), (String)null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                TuComponentFragment.this.startActivity(intent);
            }
            
            @Override
            public void onAlertCancel(final AlertDialog alertDialog) {
                TuComponentFragment.this.dismissActivityWithAnim();
            }
        };
    }
    
    public TuSdkComponentErrorListener getErrorListener() {
        return this.a;
    }
    
    public void setErrorListener(final TuSdkComponentErrorListener a) {
        this.a = a;
    }
    
    protected void notifyError(final TuSdkResult tuSdkResult, final ComponentErrorType componentErrorType) {
        if (componentErrorType == null || this.getErrorListener() == null) {
            return;
        }
        if (!ThreadHelper.isMainThread()) {
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    TuComponentFragment.this.notifyError(tuSdkResult, componentErrorType);
                }
            });
            return;
        }
        this.getErrorListener().onComponentError(this, tuSdkResult, componentErrorType.getError(this));
    }
    
    @TargetApi(23)
    protected String[] getRequiredPermissions() {
        return new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_FINE_LOCATION" };
    }
    
    public int getRequestPermissionCode() {
        return 1;
    }
    
    @TargetApi(23)
    public boolean hasRequiredPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        final String[] requiredPermissions = this.getRequiredPermissions();
        if (requiredPermissions != null && requiredPermissions.length > 0) {
            for (final String s : requiredPermissions) {
                if (this.getActivity().checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                    if (s != "android.permission.ACCESS_FINE_LOCATION") {
                        return false;
                    }
                    TLog.d("Access to fine location has been denied", new Object[0]);
                }
            }
        }
        return true;
    }
    
    public void requestRequiredPermissions() {
        final String[] requiredPermissions = this.getRequiredPermissions();
        if (requiredPermissions != null && requiredPermissions.length > 0) {
            this.requestPermissions(requiredPermissions, this.getRequestPermissionCode());
        }
    }
    
    public void onRequestPermissionsResult(final int n, final String[] array, final int[] array2) {
        super.onRequestPermissionsResult(n, array, array2);
        if (n == this.getRequestPermissionCode()) {
            for (int length = array2.length, n2 = 0; n2 < length && array2[n2] == 0; ++n2) {}
            this.onPermissionGrantedResult(this.hasRequiredPermission());
        }
    }
    
    protected void onPermissionGrantedResult(final boolean b) {
    }
}
