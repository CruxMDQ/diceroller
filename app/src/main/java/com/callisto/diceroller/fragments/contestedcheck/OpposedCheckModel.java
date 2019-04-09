package com.callisto.diceroller.fragments.contestedcheck;

import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Maneuver;

import java.util.List;

class OpposedCheckModel
{
    public List<Maneuver> getManeuvers()
    {
        return RealmHelper.getInstance().getList(Maneuver.class);
    }
}
