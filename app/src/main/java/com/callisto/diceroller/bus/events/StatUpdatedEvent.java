package com.callisto.diceroller.bus.events;

public class StatUpdatedEvent
{
    public final String name;
    public final int value;

    public StatUpdatedEvent(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
}
