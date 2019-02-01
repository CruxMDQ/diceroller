package com.callisto.diceroller.interfaces;

import com.callisto.diceroller.beans.Stat;

public interface StatObservable
{
    void notifyObservers();
    Stat getStat();
}
