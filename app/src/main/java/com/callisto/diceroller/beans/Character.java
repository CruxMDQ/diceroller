package com.callisto.diceroller.beans;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Character
    extends RealmObject
{
    @PrimaryKey
    long id;
    private String name;
    private RealmList<Stat> stats;

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

    public void setStats(RealmList<Stat> stats)
    {
        this.stats = stats;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
