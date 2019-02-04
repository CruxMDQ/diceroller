package com.callisto.diceroller.beans;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Character
    extends RealmObject
{
    @PrimaryKey
    long id;
    String name;
    RealmList<Stat> stats;

    public Character() {}

    public Character(String name, RealmList<Stat> stats)
    {
        this.name = name;
        this.stats = stats;
    }

    public RealmList<Stat> getStats()
    {
        return stats;
    }
}
