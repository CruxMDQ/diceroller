package com.callisto.diceroller.persistence.objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class System
    extends RealmObject
{
    @PrimaryKey private long id;
    private String name;
    private RealmList<Stat> baseStats = new RealmList<>();

    public System()
    {
    }

    public System(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public System addBaseStat(Stat stat)
    {
        baseStats.add(stat);

        return this;
    }

    public RealmList<Stat> getBaseStats()
    {
        return baseStats;
    }

    public void setBaseStats(RealmList<Stat> baseStats)
    {
        this.baseStats = baseStats;
    }
}
