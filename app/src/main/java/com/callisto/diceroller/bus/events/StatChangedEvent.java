package com.callisto.diceroller.bus.events;

public class StatChangedEvent
{
    public final String name;
    public final int value;

    public StatChangedEvent(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
}
