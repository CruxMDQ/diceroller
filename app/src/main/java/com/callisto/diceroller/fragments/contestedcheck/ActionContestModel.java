package com.callisto.diceroller.fragments.contestedcheck;

import com.callisto.diceroller.model.DiceRoller;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

class ActionContestModel
{
    private Character character;

    private List<Character> characters;

    private List<Stat> selectedStats;

    private final DiceRoller diceRoller;

    private ArrayList<Integer> rolls;
    private Integer diceRerollThreshold, successes;

    ActionContestModel()
    {
        this.diceRoller = new DiceRoller();

        this.characters = new ArrayList<>();

        this.selectedStats = new ArrayList<>();

        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        characters.addAll(list);
    }

    List<Character> getCharacters()
    {
        return characters;
    }

    List<Stat> getCharacterStats()
    {
        return character.getStats();
    }

    void addSelectedStat(Stat stat)
    {
        selectedStats.add(stat);
    }
    void clearSelections()
    {
        selectedStats.clear();
    }

    void setSelectedCharacter(Character selectedCharacter)
    {
        this.character = selectedCharacter;
    }

    List<Stat> getSelectedStats()
    {
        return selectedStats;
    }

    void calculateRolls()
    {
        int diceNumber = 0;

        for (Stat stat : selectedStats)
        {
            diceNumber += stat.getValue();
        }

        rolls = diceRoller.rollDice(diceRerollThreshold, diceNumber);
    }

    void calculateSuccesses()
    {
        successes = diceRoller.getSuccessesCofd(rolls);
    }

    ArrayList<Integer> getRolls()
    {
        return rolls;
    }

    Integer getSuccesses()
    {
        return successes;
    }

    void setDiceRerollThreshold(Integer diceRerollThreshold)
    {
        this.diceRerollThreshold = diceRerollThreshold;
    }
}
