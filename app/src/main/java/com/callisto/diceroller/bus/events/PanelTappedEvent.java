package com.callisto.diceroller.bus.events;

public class PanelTappedEvent
{
    final int viewId;

    public PanelTappedEvent(int viewId)
    {
        this.viewId = viewId;
    }

    public int getViewId()
    {
        return viewId;
    }

}
