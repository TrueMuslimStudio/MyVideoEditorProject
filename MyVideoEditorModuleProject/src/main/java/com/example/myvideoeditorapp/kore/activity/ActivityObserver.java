// 
// Decompiled by Procyon v0.5.36
// 

package com.example.myvideoeditorapp.kore.activity;

import android.view.inputmethod.InputMethodManager;
import android.os.IBinder;
import android.content.Context;

import java.util.Hashtable;
import java.util.Iterator;
import android.os.Process;
import android.view.MotionEvent;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.app.Activity;

import com.example.myvideoeditorapp.kore.TuAnimType;
import com.example.myvideoeditorapp.kore.activity.TuFragmentActivity;
import com.example.myvideoeditorapp.kore.type.ActivityAnimType;
import com.example.myvideoeditorapp.kore.utils.ContextUtils;

import java.util.ArrayList;

public class ActivityObserver
{
    public static final ActivityObserver ins;
    private ArrayList<Activity> a;
    private Fragment b;
    private EditText c;
    private Class<?> d;
    @SuppressLint({ "ClickableViewAccessibility" })
    private View.OnTouchListener e;
    private Hashtable<String, ActivityAnimType> f;
    private ActivityAnimType g;
    private ActivityAnimType h;
    private ActivityAnimType i;
    private ActivityAnimType j;
    
    public Class<?> getMainActivityClazz() {
        if (this.d == null) {
            this.d = TuFragmentActivity.class;
        }
        return this.d;
    }
    
    public void setMainActivityClazz(final Class<?> d) {
        this.d = d;
    }
    
    private ActivityObserver() {
        TuAnimType[] var1 = TuAnimType.values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TuAnimType var4 = var1[var3];
            this.f.put(var4.name(), var4);
        }
    }
    
    public void register(final Activity activity) {
        if (activity == null || this.a.contains(activity)) {
            return;
        }
        this.a.add(activity);
    }
    
    public void remove(final Activity o) {
        if (o == null) {
            return;
        }
        this.a.remove(o);
    }
    
    public Activity getTopActivity() {
        if (this.a == null || this.a.isEmpty()) {
            return null;
        }
        return this.a.get(this.a.size() - 1);
    }
    
    public void exitApp() {
        final Iterator<Activity> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
        }
        this.a.clear();
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
    
    public void setTransmit(final Fragment b) {
        this.b = b;
    }
    
    public Fragment getTransmit() {
        final Fragment b = this.b;
        this.b = null;
        return b;
    }
    
    public View.OnTouchListener getHiddenKeyboardListener() {
        return this.e;
    }
    
    public void bindAutoHiddenKeyboard(final View view) {
        if (view == null) {
            return;
        }
        view.setOnTouchListener(this.e);
    }
    
    public void editTextFocus(final EditText c) {
        this.showSoftInput(c.getContext(), c);
        this.c = c;
    }
    
    public void editTextFocusLost(final EditText obj) {
        if (this.c != null && obj != null && this.c.equals(obj)) {
            this.c = null;
        }
    }
    
    public boolean cancelEditTextFocus(final View view) {
        if (view != null) {
            this.cancelEditTextFocusBinder(view.getContext(), view.getWindowToken());
        }
        if (this.c == null) {
            return false;
        }
        this.cancelEditTextFocusBinder(this.c.getContext(), this.c.getWindowToken());
        this.c.clearFocus();
        this.c = null;
        return true;
    }
    
    public boolean cancelEditTextFocus() {
        return this.cancelEditTextFocus(null);
    }
    
    public boolean cancelEditTextFocusBinder(final Context context, final IBinder binder) {
        if (binder == null) {
            return false;
        }
        final InputMethodManager inputMethodManager = ContextUtils.getSystemService(context, "input_method");
        if (inputMethodManager == null) {
            return false;
        }
        inputMethodManager.hideSoftInputFromWindow(binder, 2);
        return true;
    }
    
    public void showSoftInput(final Context context, final EditText editText) {
        if (editText == null) {
            return;
        }
        final InputMethodManager inputMethodManager = ContextUtils.getSystemService(context, "input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.showSoftInput((View)editText, 0);
    }
    
    public Hashtable<String, ActivityAnimType> getActivityAnims() {
        return this.f;
    }
    
    public ActivityAnimType getAnimPresent() {
        if (this.g == null) {
            this.g = TuAnimType.up;
        }
        return this.g;
    }
    
    public void setAnimPresent(final ActivityAnimType g) {
        this.g = g;
    }
    
    public ActivityAnimType getAnimDismiss() {
        if (this.h == null) {
            this.h = TuAnimType.down;
        }
        return this.h;
    }
    
    public void setAnimDismiss(final ActivityAnimType h) {
        this.h = h;
    }
    
    public ActivityAnimType getAnimPush() {
        if (this.i == null) {
            this.i = TuAnimType.push;
        }
        return this.i;
    }
    
    public void setAnimPush(final ActivityAnimType i) {
        this.i = i;
    }
    
    public ActivityAnimType getAnimPop() {
        if (this.j == null) {
            this.j = TuAnimType.pop;
        }
        return this.j;
    }
    
    public void setAnimPop(final ActivityAnimType j) {
        this.j = j;
    }
    
    protected ActivityAnimType getAnimType(final String key) {
        if (key == null) {
            return null;
        }
        return this.f.get(key);
    }
    
    static {
        ins = new ActivityObserver();
    }
}
