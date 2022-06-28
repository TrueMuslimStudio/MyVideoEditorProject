// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.utils;

import java.io.IOException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.content.ComponentName;
import java.util.concurrent.LinkedBlockingQueue;
import android.content.ServiceConnection;
import android.os.Parcel;
import android.os.IBinder;
import android.os.IInterface;
import java.util.ArrayList;
import com.example.myvideoeditorapp.kore.TuSdk;
import android.content.pm.PackageInfo;
import java.util.List;
import android.app.ActivityManager;
import android.location.Location;
import android.provider.Settings;
import java.util.Iterator;
import java.util.Enumeration;
import java.net.InterfaceAddress;
import android.text.TextUtils;
import java.net.SocketException;
import java.net.NetworkInterface;
import java.net.InetAddress;
import android.telephony.TelephonyManager;
import com.example.myvideoeditorapp.kore.TuSdkContext;
import android.annotation.TargetApi;
import android.os.Build;
import android.content.Context;

public class TuSdkDeviceInfo
{
    public static final String VENDER_HUAWEI = "HUAWEI";
    public static final String MODEL_HUAWEI_NXTAL10 = "HUAWEI NXT-AL10";
    public static final String VENDER_OPPO = "OPPO";
    public static final String MODEL_OPPO_A3 = "PADM00";
    public static final String VENDER_MEITU = "Meitu";
    public static final String VENDER_XIAOMI = "XiaoMi";
    public static final String MODEL_XIAOMI_MI_NOTE_LTE = "MI NOTE LTE";
    
    @TargetApi(23)
    public static boolean hasRequiredPermissions(final Context context, final String[] array) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (array != null && array.length > 0) {
            for (int length = array.length, i = 0; i < length; ++i) {
                if (context.checkSelfPermission(array[i]) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String getIMEI() {
        final String s = "";
        final Context context = TuSdkContext.context();
        if (context == null) {
            return s;
        }
        if (hasRequiredPermissions(context, getRequiredPermissions())) {
            return readPhoneInfo(context, 10);
        }
        return s;
    }
    
    protected static String readPhoneInfo(final Context context, final int n) {
        String s = "";
        try {
            final TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) {
                return s;
            }
            switch (n) {
                case 10: {
                    s = telephonyManager.getDeviceId();
                    break;
                }
                case 20: {
                    s = telephonyManager.getSubscriberId();
                    break;
                }
            }
        }
        catch (Exception ex) {}
        return s;
    }
    
    public static String getIMSI() {
        final String s = "";
        final Context context = TuSdkContext.context();
        if (context == null) {
            return s;
        }
        if (hasRequiredPermissions(context, getRequiredPermissions())) {
            return readPhoneInfo(context, 20);
        }
        return s;
    }
    
    @TargetApi(23)
    protected static String[] getRequiredPermissions() {
        return new String[] { "android.permission.READ_PHONE_STATE" };
    }
    
    public static String getMac() {
        final String s = "";
        if (TuSdkContext.context() == null) {
            return s;
        }
        String byte2hex;
        try {
            final NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(InetAddress.getByName(getIP()));
            if (byInetAddress == null) {
                return getMac2();
            }
            final byte[] hardwareAddress = byInetAddress.getHardwareAddress();
            if (hardwareAddress == null) {
                return getMac2();
            }
            byte2hex = byte2hex(hardwareAddress);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return getMac2();
        }
        return byte2hex;
    }
    
    public static String getMac2() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException ex) {}
        String byte2hex = null;
        if (networkInterfaces == null) {
            return null;
        }
        final String ip = getIP();
        while (networkInterfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = networkInterfaces.nextElement();
            try {
                if (TextUtils.isEmpty((CharSequence)ip)) {
                    continue;
                }
                final Iterator<InterfaceAddress> iterator = networkInterface.getInterfaceAddresses().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getAddress().getHostAddress().equals(ip) && networkInterface.getHardwareAddress() != null) {
                        byte2hex = byte2hex(networkInterface.getHardwareAddress());
                        break;
                    }
                }
                if (byte2hex != null) {
                    break;
                }
                continue;
            }
            catch (Exception ex2) {}
        }
        return (byte2hex == null) ? "" : byte2hex;
    }
    
    public static String byte2hex(final byte[] array) {
        if (array == null) {
            return "";
        }
        StringBuffer obj = new StringBuffer(array.length);
        for (int length = array.length, i = 0; i < length; ++i) {
            final String hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() == 1) {
                obj = obj.append("0").append(hexString);
            }
            else {
                obj = obj.append(hexString);
            }
        }
        return String.valueOf(obj);
    }
    
    public static String getIP() {
        String string = "";
        try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                final Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    final InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        string = inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (Exception ex) {}
        return string;
    }
    
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }
    
    public static String getModel() {
        return Build.MODEL;
    }
    
    public static String getVender() {
        return Build.MANUFACTURER;
    }
    
    public static String getAndroidID() {
        final String s = "";
        final Context context = TuSdkContext.context();
        if (context == null) {
            return s;
        }
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }
    
    public static String getLocation() {
        final Location lastLocation = TuSdkLocation.getLastLocation();
        if (lastLocation == null) {
            return "";
        }
        return lastLocation.getLongitude() + "," + lastLocation.getLatitude();
    }
    
    public static boolean isSupportPbo() {
        return (((ActivityManager)TuSdkContext.context().getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion & 0xFFFF0000) >> 16 >= 3 && Build.VERSION.SDK_INT >= 18;
    }
    /*
    public static List<PackageInfo> getInstallAppInfoList() {
        final List installedPackages = TuSdk.appContext().getContext().getPackageManager().getInstalledPackages(0);
        final ArrayList<PackageInfo> list = new ArrayList<PackageInfo>();
        for (final Object packageInfo : installedPackages) {
            if ((packageInfo.applicationInfo.flags & 0x1) == 0x0) {
                list.add((PackageInfo) packageInfo);
            }
        }
        return list;
    }

     */
    
    public static String getAdvertisingIdInfo(final Context context) throws PackageManager.NameNotFoundException, IOException, InterruptedException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
        }
        catch (Exception ex) {
            throw ex;
        }
        final Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        class AdvertisingConnection implements ServiceConnection
        {
            private boolean a;
            private final LinkedBlockingQueue<IBinder> b;
            
            AdvertisingConnection() {
                this.a = false;
                this.b = new LinkedBlockingQueue<IBinder>(1);
            }
            
            public void onServiceConnected(final ComponentName componentName, final IBinder e) {
                try {
                    this.b.put(e);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            public void onServiceDisconnected(final ComponentName componentName) {
            }
            
            public IBinder getBinder() throws InterruptedException {
                if (this.a) {
                    throw new IllegalStateException();
                }
                this.a = true;
                return this.b.take();
            }
        }
        final AdvertisingConnection AdvertisingConnection = new AdvertisingConnection();
        if (context.bindService(intent, (ServiceConnection)AdvertisingConnection, Context.BIND_AUTO_CREATE)) {
            try {
                class AdvertisingInterface implements IInterface
                {
                    private IBinder a = AdvertisingConnection.getBinder();
                    
                    public AdvertisingInterface() throws InterruptedException {
                    }
                    
                    public IBinder asBinder() {
                        return this.a;
                    }
                    
                    public String getId() {
                        final Parcel obtain = Parcel.obtain();
                        final Parcel obtain2 = Parcel.obtain();
                        String string = null;
                        try {
                            obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                            this.a.transact(1, obtain, obtain2, 0);
                            obtain2.readException();
                            string = obtain2.readString();
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        finally {
                            obtain2.recycle();
                            obtain.recycle();
                        }
                        return string;
                    }
                }
                return new AdvertisingInterface().getId();
            }
            catch (Exception ex2) {
                throw ex2;
            }
            finally {
                context.unbindService((ServiceConnection)AdvertisingConnection);
            }
        }
        throw new IOException("Google Play connection failed");
    }
}
