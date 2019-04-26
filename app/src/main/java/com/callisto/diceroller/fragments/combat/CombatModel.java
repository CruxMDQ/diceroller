package com.callisto.diceroller.fragments.combat;

import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.ArrayList;

class CombatModel
{
    private ArrayList<Character> characters = new ArrayList<>();

    private RealmHelper helper = RealmHelper.getInstance();

    private CombatPresenter presenter;

    static CombatModel newInstance(CombatPresenter combatPresenter, ArrayList<Integer> characterIds)
    {
        return new CombatModel(combatPresenter, characterIds);
    }

    private CombatModel(CombatPresenter presenter, ArrayList<Integer> characterIds)
    {
        this.presenter = presenter;

        for (Integer id : characterIds)
        {
            Character character = helper.get(Character.class, id);

            characters.add(character);
        }
    }
}
