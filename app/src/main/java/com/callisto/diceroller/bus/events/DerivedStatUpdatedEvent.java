package com.callisto.diceroller.bus.events;

public class DerivedStatUpdatedEvent
{
    public final String name;
    public final long statId;
    public final int value;

    public DerivedStatUpdatedEvent(String name, long statId, int value)
    {
        this.name = name;
        this.statId = statId;
        this.value = value;
    }
}
