// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.media.codec.audio;

import com.example.myvideoeditorapp.kore.utils.Complex;
import com.example.myvideoeditorapp.kore.utils.TLog;
import com.example.myvideoeditorapp.kore.utils.calc.FFT;


public class TuSdkAudioGainData
{
    public static final double AUDIO_GAIN_RE_WEIGHT = 0.75;
    public static final double AUDIO_GAIN_IM_WEIGHT = 0.25;
    public final Complex[] gain;
    public final int length;

    public TuSdkAudioGainData(int var1, float var2) {
        double var3 = Math.log((double)var1) / Math.log(2.0D);
        this.length = (int)Math.pow(2.0D, Math.ceil(var3));
        this.gain = new Complex[var1];
        double var5 = var2 < 1.0F ? (double)(-1.0F / var2 * 2.0F) : (double)(var2 * 2.0F);
        double var7 = Math.pow(10.0D, var5 / 20.0D);
        int var9 = 0;
        int var10 = this.length / 2;

        int var11;
        for(var11 = this.length - 1; var9 < var10; ++var9) {
            this.gain[var9] = new Complex(var7 * 0.75D, var7 * 0.25D);
            this.gain[var11 - var9] = new Complex(this.gain[var9].re(), this.gain[var9].im());
        }

        Complex[] var15 = FFT.ifft(this.gain);
        Complex[] var14 = var15;
        var11 = var15.length;

        for(int var12 = 0; var12 < var11; ++var12) {
            Complex var13 = var14[var12];
            var13.setImZero();
        }

        var15 = FFT.fft(var15);
        System.arraycopy(var15, 0, this.gain, 0, this.length);
    }
    public void gainWithData(final Complex[] array) {
        if (array == null) {
            TLog.w("%s gainWithData need %d datas", "TuSdkAudioGainData", this.length);
            return;
        }
        final int min = Math.min(array.length, this.length);
        int i = 0;
        final int n = min / 2;
        final int n2 = min - 1;
        while (i < n) {
            array[i] = this.gain[i].times(array[i]);
            array[n2 - i] = this.gain[n2 - i].times(array[n2 - i]);
            ++i;
        }
    }
    
    public void gainWithData(final Complex[] array, final Complex[] array2) {
        if (array == null || array2 == null) {
            TLog.w("%s gainWithData need %d datas", "TuSdkAudioGainData", this.length);
            return;
        }
        final int min = Math.min(Math.min(array.length, array2.length), this.length);
        int i = 0;
        final int n = min / 2;
        final int n2 = min - 1;
        while (i < n) {
            array[i] = this.gain[i].times(array[i]);
            array[n2 - i] = this.gain[n2 - i].times(array[n2 - i]);
            array2[i] = this.gain[i].times(array2[i]);
            array2[n2 - i] = this.gain[n2 - i].times(array2[n2 - i]);
            ++i;
        }
    }
}
