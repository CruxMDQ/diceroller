package com.callisto.diceroller.views;

import com.callisto.diceroller.beans.Stat;

interface StatObservable
{
    void notifyObservers();
    Stat getStat();
}
