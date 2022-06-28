// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils.hardware;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import android.annotation.TargetApi;
import android.os.Build;
import com.example.myvideoeditorapp.kore.utils.NetworkHelper;
import android.net.wifi.WifiManager;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import java.io.IOException;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.AssetsHelper;
import android.media.MediaPlayer;
import android.os.Vibrator;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;
import android.media.AudioManager;
import android.content.Context;

public class HardwareHelper
{
    public static final String qcom = "qcom";
    public static final String hisi = "hi";
    public static final String kirin = "kirin";
    public static final String mt = "mt";
    public static final String samsung = "samsung";
    
    public static long appMemoryBit() {
        return Runtime.getRuntime().maxMemory();
    }
    
    public static AudioManager getAudioManager(final Context context) {
        return ContextUtils.getSystemService(context, "audio");
    }
    
    public static Vibrator getVibrator(final Context context) {
        return ContextUtils.getSystemService(context, "vibrator");
    }
    
    public static int getAlertVolume(final Context context, final int n) {
        final AudioManager audioManager = getAudioManager(context);
        if (audioManager == null) {
            return 100;
        }
        return (int)(audioManager.getStreamVolume(n) * 100.0f / audioManager.getStreamMaxVolume(n));
    }
    
    public static MediaPlayer loadMediaAsset(final Context context, final String s) {
        final AssetFileDescriptor assetFileDescriptor = AssetsHelper.getAssetFileDescriptor(context, s);
        if (assetFileDescriptor == null) {
            return null;
        }
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            assetFileDescriptor.close();
            mediaPlayer.prepare();
            return mediaPlayer;
        }
        catch (IllegalArgumentException ex) {
            TLog.e(ex, "loadMediaAsset: %s", s);
        }
        catch (IllegalStateException ex2) {
            TLog.e(ex2, "loadMediaAsset: %s", s);
        }
        catch (IOException ex3) {
            TLog.e(ex3, "loadMediaAsset: %s", s);
        }
        return null;
    }
    
    public static void playSound(final Context context, final int n) {
        if (context == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final MediaPlayer create = MediaPlayer.create(context, n);
                create.setOnCompletionListener((MediaPlayer.OnCompletionListener)new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(final MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
                create.start();
            }
        }).start();
    }
    
    public static Intent getLaunchIntent(final Context context) {
        if (context == null) {
            return null;
        }
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }
    
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) {
            return false;
        }
        final ConnectivityManager connectivityManager = ContextUtils.getSystemService(context, "connectivity");
        if (connectivityManager == null) {
            return false;
        }
        final NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return false;
        }
        final NetworkInfo[] array = allNetworkInfo;
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i].isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkWifi(Context var0) {
        if (var0 == null) {
            return false;
        } else {
            ConnectivityManager var1 = (ConnectivityManager)ContextUtils.getSystemService(var0, "connectivity");
            NetworkInfo var2 = var1.getActiveNetworkInfo();
            return var2 != null && var2.getType() == 1 ? var2.isConnected() : false;
        }
    }
    
    public static String getWifiIp(final Context context) {
        if (!isNetworkWifi(context)) {
            return null;
        }
        final WifiManager wifiManager = ContextUtils.getSystemService(context, "wifi");
        if (wifiManager == null || !wifiManager.isWifiEnabled()) {
            return null;
        }
        final int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        if (ipAddress == 0) {
            return null;
        }
        return NetworkHelper.longToIP(ipAddress);
    }
    
    public static boolean isMatchDeviceModel(final String anotherString) {
        return anotherString != null && Build.MODEL != null && Build.MODEL.equalsIgnoreCase(anotherString);
    }
    
    public static boolean isMatchDeviceManuFacturer(final String anotherString) {
        return anotherString != null && Build.MANUFACTURER != null && Build.MANUFACTURER.equalsIgnoreCase(anotherString);
    }
    
    public static boolean isMatchDeviceModelAndManuFacturer(final String s, final String s2) {
        return isMatchDeviceModel(s) && isMatchDeviceManuFacturer(s2);
    }
    
    public static boolean isSupportAbi(final String... array) {
        if (array == null || array.length < 1) {
            return false;
        }
        String[] array2;
        if (Build.VERSION.SDK_INT < 21) {
            array2 = a();
        }
        else {
            array2 = b();
        }
        if (array2 == null) {
            return false;
        }
        for (final String s : array) {
            final String[] array3 = array2;
            for (int length2 = array3.length, j = 0; j < length2; ++j) {
                if (s.equalsIgnoreCase(array3[j])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static String[] a() {
        final String cpu_ABI = Build.CPU_ABI;
        if (cpu_ABI == null) {
            return null;
        }
        return new String[] { cpu_ABI };
    }
    
    @TargetApi(21)
    private static String[] b() {
        return Build.SUPPORTED_ABIS;
    }
    
    public static String getHardWareInfo() {
        final String fileName = "/proc/cpuinfo";
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Hardware")) {
                    return line.split(":")[1];
                }
            }
            bufferedReader.close();
        }
        catch (IOException ex) {}
        return null;
    }
}
