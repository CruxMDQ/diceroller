package com.callisto.diceroller.persistence.objects;

import com.callisto.diceroller.persistence.RealmHelper;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Character
    extends RealmObject
{
    @PrimaryKey
    private long id;
    private String name;
    private Template template;
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

    public Template getTemplate()
    {
        return template;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }

    @Override
    public String toString()
    {
        return RealmHelper.getInstance().get(Character.class, this.id).getName();
    }
}
