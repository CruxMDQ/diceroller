package com.callisto.diceroller.beans;

import com.callisto.diceroller.interfaces.StatContainer;
import com.callisto.diceroller.interfaces.StatObservable;
import com.callisto.diceroller.interfaces.StatObserver;
import com.callisto.diceroller.model.GameLogic;

import java.util.ArrayList;

public class Stat
    implements StatObserver,
    StatObservable
{
    private String name, category, type, kind;

    private int color, value;

    private StatContainer container;
    
    private ArrayList<StatObserver> observers;
    private ArrayList<StatObservable> observedStats;

    public static Stat newInstance()
    {
        return new Stat();
    }

    public Stat()
    {
        this.observers = new ArrayList<>();
        this.observedStats = new ArrayList<>();
    }

    public Stat(String name, String category, int value)
    {
        this.observers = new ArrayList<>();
        this.observedStats = new ArrayList<>();
        this.name = name;
        this.category = category;
        this.value = value;
    }

    public Stat(String name, String category, String type)
    {
        this.observers = new ArrayList<>();
        this.observedStats = new ArrayList<>();
        this.name = name;
        this.category = category;
        this.type = type;
    }

    public Stat(String name, String category, String type, String kind, int color)
    {
        this.observers = new ArrayList<>();
        this.observedStats = new ArrayList<>();
        this.name = name;
        this.category = category;
        this.type = type;
        this.kind = kind;
        this.color = color;
    }

    public Stat(String name, String category, String type, String kind, int color, int value)
    {
        this.observers = new ArrayList<>();
        this.observedStats = new ArrayList<>();
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

    public void addOrRemoveObserver(StatObserver observer)
    {
        if (observers.contains(observer))
        {
            observers.remove(observer);
        }
        else
        {
            observers.add(observer);
        }

    }

    public Stat addOrRemoveObservedStat(StatObservable observedStat)
    {
        if (observedStats.contains(observedStat))
        {
            observedStats.remove(observedStat);
        }
        else
        {
            observedStats.add(observedStat);
        }

        return this;
    }

    @Override
    public void notifyObservers()
    {
        for (StatObserver observer : observers)
        {
            observer.processNewValue(getStat());
        }
    }

    @Override
    public Stat getStat()
    {
        return this;
    }

    public ArrayList<StatObservable> getObservedStats()
    {
        return observedStats;
    }

    @Override
    public void processNewValue(Stat stat)
    {
        container.setValue(GameLogic.processNewValue(this, stat));
    }

    public void setContainer(StatContainer container)
    {
        this.container = container;
    }
}
