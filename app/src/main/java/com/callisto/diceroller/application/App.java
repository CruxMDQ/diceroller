package com.callisto.diceroller.application;

import android.app.Application;
import android.content.res.Resources;

import com.callisto.diceroller.model.RulesBuilder;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;

import io.realm.RealmResults;

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

        RulesBuilder.build();

        generateTestCharacters();
    }

    private void generateTestCharacters()
    {
        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        if (list.size() == 0)
        {
            RulesBuilder.createNewCharacter("Andrzej Kryszycha", "Kindred");
            RulesBuilder.createNewCharacter("Satsuki Kiryuin", "Noble");
        }
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
