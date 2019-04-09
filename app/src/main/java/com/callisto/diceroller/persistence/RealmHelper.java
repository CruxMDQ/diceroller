package com.callisto.diceroller.persistence;

import com.callisto.diceroller.application.App;
import com.callisto.diceroller.tools.Constants;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper
{
    private static RealmHelper instance;

    private Realm realm;
    private final RealmConfiguration config;

    public static RealmHelper getInstance()
    {
        if (instance == null)
        {
            instance = new RealmHelper();
        }
        return instance;
    }

    private RealmHelper()
    {
        Realm.init(App.getInstance().getApplicationContext());

        config = buildRealmConfig();

        realm = Realm.getInstance(config);
    }

    private RealmConfiguration buildRealmConfig()
    {
        RealmConfiguration config;

//        config = new RealmConfiguration.Builder()
//            .name("cofd.realm")
//            .schemaVersion(0)
//            .modules(new StorytellerModule())
//            .build();

        config = new RealmConfiguration.Builder()
            .schemaVersion(10)
            .migration(new CofdMigration())
            .build();

        return config;
    }

    public <T extends RealmObject> RealmResults<T> getList(Class<T> klass)
    {
        return realm.where(klass).findAll();
    }

    public <T extends RealmObject> T get(Class<T> klass, long id)
    {
        T first = realm.where(klass).equalTo(Constants.Fields.ID.getText(), id)
            .findFirst();

        return first;
    }

    public <T extends RealmObject> T get(Class<T> klass, String name)
    {
        RealmQuery<T> query = realm.where(klass);

        T first = query.equalTo(Constants.Fields.NAME.getText(), name)
            .findFirst();

        return first;
    }

    public boolean exists(Class klass, long id)
    {
        RealmObject realmObject = get(klass, id);

        return realmObject != null;
    }

    public boolean exists(Class klass, String name)
    {
        RealmObject realmObject = get(klass, name);

        return realmObject != null;
    }

    public <T extends RealmObject> T add(T item)
    {
        realm.beginTransaction();
        realm.copyToRealm(item);
        realm.commitTransaction();
        return item;
    }

    public <T extends RealmObject> long save(T item)
    {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
        return 0;
    }

    public long getLastId(Class className)
    {
        long key;
        try
        {
            RealmQuery query = realm.where(className);
            Number max = query.max("id");
            key = max != null ? max.longValue() + 1 : 0;
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            key = 0;
        }
        return key;
    }

    public Realm getRealm()
    {
        return realm;
    }
}
