package com.callisto.diceroller.fragments.contestedcheck;

import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.CheckForContestReadinessEvent;
import com.callisto.diceroller.model.DiceRoller;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

class CharacterContestModel
{
    private Character character;

    private List<Character> characters;

    private List<Stat> selectedStats;

    private final DiceRoller diceRoller;

    private ArrayList<Integer> rolls;
    private Integer diceRerollThreshold, successes;

    private List<String> statFilters;

    private boolean hasPickedStats;
    private boolean hasPickedCharacter;

    CharacterContestModel()
    {
        this.diceRoller = new DiceRoller();

        this.characters = new ArrayList<>();

        this.selectedStats = new ArrayList<>();

        this.statFilters = new ArrayList<>();

        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        characters.addAll(list);
    }

    List<Character> getCharacters()
    {
        return characters;
    }

    List<Stat> getCharacterStats()
    {
        if (statFilters.size() == 0)
        {
            return character.getStats();
        }
        else
        {
            List<Stat> result = new RealmList<>();

            for(Stat stat : character.getStats())
            {
                if (stat.getKeywords().containsAll(statFilters))
                {
                    result.add(stat);
                }
            }

            return result;
        }
    }

    void addOrRemoveSelectedStat(Stat stat)
    {
        if (!selectedStats.contains(stat))
        {
            selectedStats.add(stat);
        }
        else
        {
            selectedStats.remove(stat);
        }
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

    void calculateRolls(int miscMod)
    {
        int diceNumber = miscMod;

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

    Integer getDicePoolSize()
    {
        int result = 0;

        for (Stat stat : selectedStats)
        {
            // TODO Account for temporary stat changes in the future
            result += stat.getValue();
        }

        return result;
    }

    void addOrRemoveStatFilter(String filter)
    {
        if (!statFilters.contains(filter))
        {
            statFilters.add(filter);
        }
        else
        {
            statFilters.remove(filter);
        }
    }

    void clearFilters()
    {
        statFilters.clear();
    }

    boolean hasPickedStats()
    {
        return hasPickedStats;
    }

    void setHasPickedStats(boolean hasPickedStats)
    {
        this.hasPickedStats = hasPickedStats;

        BusProvider.getInstance().post(new CheckForContestReadinessEvent());
    }

    boolean hasPickedCharacter()
    {
        return hasPickedCharacter;
    }

    void setHasPickedCharacter(boolean hasPickedCharacter)
    {
        this.hasPickedCharacter = hasPickedCharacter;

        BusProvider.getInstance().post(new CheckForContestReadinessEvent());
    }
}
