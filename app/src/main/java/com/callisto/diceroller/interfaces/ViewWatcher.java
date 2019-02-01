package com.callisto.diceroller.interfaces;

public interface ViewWatcher
{
    void spawnStatEditionDialog(int id, String statName);

    void changeDicePool(String statName, int value, int colorSelected);

    void setStatContainer(Object tag);
}
