package com.callisto.diceroller.bus.events;

public class StatChangedEvent
{
    public final long statId;
    public final String name;
    public final int value;

    public StatChangedEvent(long statId, String name, int value)
    {
        this.statId = statId;
        this.name = name;
        this.value = value;
    }
}
