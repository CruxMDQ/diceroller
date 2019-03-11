package com.callisto.diceroller.fragments.contestedcheck;

import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.List;

class ActionContestPresenter
{
    private final ActionContestModel model;
    private final ActionContestFragment view;

    ActionContestPresenter(ActionContestFragment view) {
        this.model = new ActionContestModel();
        this.view = view;
    }

    List<Character> getCharacters()
    {
        return model.getCharacters();
    }

    void setSelectedCharacter(Character selectedCharacter)
    {
        model.setSelectedCharacter(selectedCharacter);
    }

    List<Stat> getCharacterStats()
    {
        return model.getCharacterStats();
    }

    void addSelectedStat(Stat selectedStat)
    {
        model.addSelectedStat(selectedStat);
    }

    void clearSelections()
    {
        model.clearSelections();
    }

    List<Stat> getSelectedStats()
    {
        return model.getSelectedStats();
    }

    ArrayList<Integer> getRolls()
    {
        return model.getRolls();
    }

    Integer getSuccesses()
    {
        return model.getSuccesses();
    }

    void performRoll(int diceRerollThreshold)
    {
        model.setDiceRerollThreshold(diceRerollThreshold);
        model.calculateRolls();
        model.calculateSuccesses();
    }
}
