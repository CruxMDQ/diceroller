package com.callisto.diceroller.fragments.contestedcheck;

import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.List;

class CharacterContestPresenter
{
    private final CharacterContestModel model;
    private final CharacterContestFragment view;

    CharacterContestPresenter(CharacterContestFragment view)
    {
        this.model = new CharacterContestModel();
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

    void addOrRemoveSelectedStat(Stat selectedStat)
    {
        model.addOrRemoveSelectedStat(selectedStat);
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

//    void performRoll(int diceRerollThreshold)
//    {
//        model.setDiceRerollThreshold(diceRerollThreshold);
//        model.calculateRolls(0);
//        model.calculateSuccesses();
//    }

    void performRoll(int diceRerollThreshold, int miscModifier)
    {
        model.setDiceRerollThreshold(diceRerollThreshold);
        model.calculateRolls(miscModifier);
        model.calculateSuccesses();
    }

    Integer getDicePoolSize()
    {
        return model.getDicePoolSize();
    }

    void addOrRemoveStatFilter(String filter)
    {
        model.addOrRemoveStatFilter(filter);
    }

    void clearFilters()
    {
        model.clearFilters();
    }

    public void setHasPickedStats(boolean hasPickedStats)
    {
        model.setHasPickedStats(hasPickedStats);
    }

    public boolean hasPickedStats()
    {
        return model.hasPickedStats();
    }

    public void setHasPickedCharacter(boolean hasPickedCharacter)
    {
        model.setHasPickedCharacter(hasPickedCharacter);
    }

    public boolean hasPickedCharacter()
    {
        return model.hasPickedCharacter();
    }
}
