package com.callisto.diceroller.fragments.charactersheet;

import android.util.Log;
import android.util.Pair;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.DerivedStatUpdatedEvent;
import com.callisto.diceroller.bus.events.StatChangedEvent;
import com.callisto.diceroller.model.DiceRoller;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.XMLParser;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import io.realm.RealmList;

public class CharacterSheetModel
{
    private int diceNumber = 0;
    private int rerollThreshold = 0;

    private Character activeCharacter;

    private ArrayList<Pair<String, Integer>> stats;

    private final DiceRoller diceRoller;

    private CharacterSheetPresenter presenter;

    private CharacterSheetModel(String characterName, CharacterSheetPresenter presenter)
    {
        this.diceRoller = new DiceRoller();
        this.stats = new ArrayList<>();

        this.presenter = presenter;

        subscribeToEvents();

        activeCharacter = RealmHelper.getInstance().get(Character.class, characterName);

        if (activeCharacter == null)
        {
            RealmList<Stat> statistics = XMLParser.parseStats();

            activeCharacter = new Character(characterName, statistics);

            activeCharacter.setId(RealmHelper.getInstance().getLastId(Character.class));

            persistChanges();
        }
    }

    static CharacterSheetModel newInstance(String characterName, CharacterSheetPresenter presenter)
    {
        return new CharacterSheetModel(characterName, presenter);
    }

    static CharacterSheetModel newInstance(CharacterSheetPresenter presenter)
    {
        return new CharacterSheetModel("Test character", presenter);
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    int getDiceNumber()
    {
        return diceNumber;
    }

    private void calculateDiceNumber()
    {
        int pool = 0;

        for (Pair<String, Integer> pair : stats)
        {
            pool += pair.second;
        }

        diceNumber = pool;
    }

    void changeDiceNumber(int value)
    {
        this.diceNumber += value;
    }

    ArrayList<Integer> rollDice(int rerollThreshold, int dicePool)
    {
        return diceRoller.rollDice(rerollThreshold, dicePool);
    }

    ArrayList<Integer> rollDice(int rerollThreshold)
    {
        return diceRoller.rollDice(rerollThreshold, diceNumber);
    }

    ArrayList<Integer> rollDice()
    {
        return diceRoller.rollDice(rerollThreshold, diceNumber);
    }

    int getSuccessesCofd(ArrayList<Integer> rolls)
    {
        return diceRoller.getSuccessesCofd(rolls);
    }

    void addOrRemoveStat(Stat stat)
    {
        if (activeCharacter.getStats().contains(stat))
        {
            activeCharacter.getStats().remove(stat);
        }
        else
        {
            activeCharacter.getStats().add(stat);
        }
    }

    void addOrRemovePair(Pair<String, Integer> pair)
    {
        if (stats.contains(pair))
        {
            stats.remove(pair);
        }
        else
        {
            stats.add(pair);
        }

        calculateDiceNumber();
    }

    ArrayList<Pair<String, Integer>> getStats()
    {
        return stats;
    }

    ArrayList<Integer> rollExtended(int threshold)
    {
        return diceRoller.rollExtended(threshold, diceNumber);
    }

    Stat getStat(String statName)
    {
        for (Stat stat : activeCharacter.getStats())
        {
            if (stat.getName().equals(statName))
            {
                return stat;
            }
        }

        return null;
    }

    Stat getStat(long statId)
    {
        for (Stat stat : activeCharacter.getStats())
        {
            if (stat.getId() == statId)
            {
                return stat;
            }
        }

        return null;
    }

    Stat getStatByTag(Object tag)
    {
        try
        {
            for (Stat stat : activeCharacter.getStats())
            {
                if (stat.getName().equals(tag.toString()))
                {
                    return stat;
                }
            }
        }
        catch (NullPointerException npe)
        {
            Log.e(this.getClass().getName(), "Null tag!");
        }
        return null;
    }

    void persistChanges()
    {
        RealmHelper.getInstance().save(activeCharacter);
    }

    @Subscribe
    public void updateStatValue(StatChangedEvent event)
    {
        Stat stat = getStat(event.statId);

        if (stat != null)
        {
            RealmHelper.getInstance().getRealm().beginTransaction();
            stat.setValue(event.value);
            RealmHelper.getInstance().getRealm().commitTransaction();

            int newScore;

            for (Stat observer : stat.getObservers())
            {
                newScore = updateStatScore(observer);

                RealmHelper.getInstance().getRealm().beginTransaction();
                observer.setValue(newScore);
                RealmHelper.getInstance().getRealm().commitTransaction();

                BusProvider.getInstance()
                    .post(new DerivedStatUpdatedEvent(observer.getName(), observer.getId(), newScore));
            }
        }
    }

    private int updateStatScore(Stat observer)
    {
        int newScore;
        RealmList<Stat> observedStats = observer.getObservedStats();

        if (observer.getName().equals(App.getRes().getString(R.string.label_derived_defense)))
        {
            newScore = 100;

            for (Stat observedStat : observedStats)
            {
                if (observedStat.getValue() < newScore)
                {
                    newScore = observedStat.getValue();
                }
            }
        }
        else
        {
            newScore = 0;

            for (Stat observedStat : observedStats)
            {
                newScore += observedStat.getValue();
            }

            if (observer.getName().equals(App.getRes().getString(R.string.label_derived_speed)))
            {
                newScore += Constants.Values.COFD_SPEED_BASE.getValue();
            }
        }
        return newScore;
    }

    Stat getStatByName(String name)
    {
        try
        {
            for (Stat stat : activeCharacter.getStats())
            {
                if (stat.getName().equals(name))
                {
                    return stat;
                }
            }
        }
        catch (NullPointerException npe)
        {
            Log.e(this.getClass().getName(), "Null tag!");
        }
        return null;
    }
}
