package com.callisto.diceroller.beans;

import com.callisto.diceroller.interfaces.StatContainer;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Stat
    extends RealmObject
{
    @PrimaryKey
    long id;

    private String name, category, type, kind;

    private int color, value;

    private RealmList<String> observers;
    private RealmList<String> observed;

    public static Stat newInstance()
    {
        return new Stat();
    }

    public Stat()
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();
    }

    public Stat(String name, String category, int value)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.name = name;
        this.category = category;
        this.value = value;
    }

    public Stat(String name, String category, String type)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.name = name;
        this.category = category;
        this.type = type;
    }

    public Stat(String name, String category, String type, String kind, int color)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.name = name;
        this.category = category;
        this.type = type;
        this.kind = kind;
        this.color = color;
    }

    public Stat(String name, String category, String type, String kind, int color, int value)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.name = name;
        this.category = category;
        this.type = type;
        this.kind = kind;
        this.color = color;
        this.value = value;
    }

    public boolean equals(Object o) {
        boolean result = false;

        if (o instanceof Stat)
        {
            Stat t = (Stat) o;

            result = t.getName().equals(this.getName());
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public String getName()
    {
        return name;
    }

    public Stat setName(String name)
    {
        this.name = name;

        return this;
    }

    public String getCategory()
    {
        return category;
    }

    public Stat setCategory(String category)
    {
        this.category = category;

        return this;
    }

    public String getType()
    {
        return type;
    }

    public Stat setType(String type)
    {
        this.type = type;

        return this;
    }

    public String getKind()
    {
        return kind;
    }

    public Stat setKind(String kind)
    {
        this.kind = kind;

        return this;
    }

    public int getColor()
    {
        return color;
    }

    public Stat setColor(int color)
    {
        this.color = color;

        return this;
    }

    public int getValue()
    {
        return value;
    }

    public Stat setValue(int value)
    {
        this.value = value;

        return this;
    }

    public Stat getStat()
    {
        return this;
    }

    public void setContainer(StatContainer container)
    {
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void addWatcher(String observer)
    {
        observers.add(observer);
    }

    public void removeWatcher(String observer)
    {
        if (observers.contains(observer))
        {
            observers.remove(observer);
        }
    }

    public Stat addWatchedStat(String observedStat)
    {
        observed.add(observedStat);

        return this;
    }

    public RealmList<String> getObservedStats()
    {
        return observed;
    }

    public RealmList<String> getObservers()
    {
        return observers;
    }
}
