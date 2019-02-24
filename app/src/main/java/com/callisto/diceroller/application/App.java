package com.callisto.diceroller.application;

import android.app.Application;
import android.content.res.Resources;

import com.callisto.diceroller.persistence.BaseDataBuilder;

public class App extends Application
{
    private static App mInstance;
    private static Resources res;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        res = getResources();

        BaseDataBuilder.build();
    }

    public static App getInstance()
    {
        return mInstance;
    }

    public static Resources getRes()
    {
        return res;
    }
}
