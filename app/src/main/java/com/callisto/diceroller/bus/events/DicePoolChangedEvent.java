package com.callisto.diceroller.bus.events;

public class DicePoolChangedEvent
{
    private final String statName;
    private final int statValue;
    private final int colorSelected;

    public DicePoolChangedEvent(String statName, int statValue, int colorSelected)
    {
        this.statName = statName;
        this.statValue = statValue;
        this.colorSelected = colorSelected;
    }

    public String getStatName()
    {
        return statName;
    }

    public int getStatValue()
    {
        return statValue;
    }

    public int getColorSelected()
    {
        return colorSelected;
    }
}
