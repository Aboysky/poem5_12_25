package com.example.poem5_12_25.observer;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.poem5_12_25.activity.BaseActivity;

/**
 * @Classname LifecycleObserver
 * @Description TODO
 * @Author Huan
 * @Date 2020/12/27 19:59
 * @Version 1.0
 */
public class MyLifecycleObserver implements LifecycleObserver {

    private AppCompatActivity activity;

    public MyLifecycleObserver(AppCompatActivity activity) {
        this.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void Onstart(){
//        new ViewModelProvider(activity).get()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void OnPause(){
        Log.println(Log.DEBUG,"","on pause");
    }
}
