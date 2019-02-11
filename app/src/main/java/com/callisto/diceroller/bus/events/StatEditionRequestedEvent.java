package com.callisto.diceroller.bus.events;

public class StatEditionRequestedEvent
{
    private final int id;
    private final String name;

    public StatEditionRequestedEvent(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
