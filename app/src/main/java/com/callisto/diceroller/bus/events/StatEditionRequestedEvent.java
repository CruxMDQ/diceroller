package com.callisto.diceroller.bus.events;

public class StatEditionRequestedEvent
{
    private final int viewId;
    private final long statId;
    private final String name;

    public StatEditionRequestedEvent(int id, long statId, String name)
    {
        this.viewId = id;
        this.statId = statId;
        this.name = name;
    }

    public int getViewId()
    {
        return viewId;
    }

    public String getName()
    {
        return name;
    }

    public long getStatId()
    {
        return statId;
    }
}
