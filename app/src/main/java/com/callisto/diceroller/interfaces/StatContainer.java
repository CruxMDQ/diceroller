package com.callisto.diceroller.interfaces;

import com.callisto.diceroller.persistence.objects.Stat;

public interface StatContainer
{
    void setValue(int statValue);
    void setStat(Stat stat);
    void performValueChange(int value);
}
