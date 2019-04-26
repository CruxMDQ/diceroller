package com.callisto.diceroller.fragments.combat;

import java.util.ArrayList;

class CombatPresenter
{
    private final CombatFragment view;
    private final CombatModel model;

    CombatPresenter(CombatFragment view, ArrayList<Integer> characterIds)
    {
        this.view = view;
        this.model = CombatModel.newInstance(this, characterIds);
    }
}
