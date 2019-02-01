package com.callisto.diceroller.model;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.beans.Stat;
import com.callisto.diceroller.interfaces.StatObservable;
import com.callisto.diceroller.tools.Constants;

public class GameLogic
{
    public static int processNewValue(Stat observer, Stat observed)
    {
        int result;

        if (!observed.getName().equals(App.getRes().getString(R.string.label_derived_defense)))
        {
            int newScore = 0;

            for (StatObservable observable : observer.getObservedStats())
            {
                newScore += observable.getStat().getValue();
            }

            if (observed.getName().equals(App.getRes().getString(R.string.label_derived_speed)))
            {
                newScore += Constants.Values.COFD_SPEED_BASE.getValue();
            }

            result = newScore;
        }
        else
        {
            int newScore = 100;

            for (StatObservable observable : observer.getObservedStats())
            {
                if (observable.getStat().getValue() < newScore)
                {
                    newScore = observable.getStat().getValue();
                }
            }
            result = newScore;
        }
        return result;
    }
}
