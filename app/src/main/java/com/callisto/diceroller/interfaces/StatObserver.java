package com.callisto.diceroller.interfaces;

public interface StatObserver {
    void spawnStatEditionDialog(int id, String statName);

    void changeDicePool(String statName, int value);
}
