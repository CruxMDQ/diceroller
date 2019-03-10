package com.callisto.diceroller.persistence.objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Stat
    extends RealmObject
{
    @PrimaryKey
    private long id;

    private String name;
    private String category;

    private int color, value;
    private int temporaryValue = 0;

    private RealmList<Stat> observers;
    private RealmList<Stat> observed;
    
    private RealmList<String> keywords;

    public Stat()
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();
        
        this.keywords = new RealmList<>();
    }

    public Stat(long id, String name)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.keywords = new RealmList<>();

        this.id = id;
        this.name = name;
    }

    public Stat(long id, String name, String category, int value)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.keywords = new RealmList<>();

        this.id = id;
        this.name = name;
        this.category = category;
        this.value = value;
    }


    public Stat(long lastId, String name, int color, int value)
    {
        this.observers = new RealmList<>();
        this.observed = new RealmList<>();

        this.keywords = new RealmList<>();

        this.id = lastId;
        this.name = name;
        this.color = color;
        this.value = value;
    }

    public boolean equals(Object o)
    {
        boolean result = false;

        if (o instanceof Stat)
        {
            Stat t = (Stat) o;

            result = t.getName().equals(this.getName());
        }

        return result;
    }

    @Override
    public int hashCode()
    {
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

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void addObserver(Stat observer)
    {
        observers.add(observer);
    }

    public void addObservedStat(Stat observedStat)
    {
        observed.add(observedStat);

    }

    public RealmList<Stat> getObservers()
    {
        return observers;
    }

    public RealmList<Stat> getObservedStats()
    {
        return observed;
    }

    public RealmList<String> getKeywords()
    {
        return keywords;
    }
    
    public Stat addKeyword(String keyword)
    {
        keywords.add(keyword);

        return this;
    }

    public int getTemporaryValue()
    {
        return temporaryValue;
    }

    public void setTemporaryValue(int temporaryValue)
    {
        this.temporaryValue = temporaryValue;
    }
}
