package com.callisto.diceroller.interfaces;

import com.callisto.diceroller.beans.Stat;

public interface StatObserver
{
    void processNewValue(Stat stat);
}
