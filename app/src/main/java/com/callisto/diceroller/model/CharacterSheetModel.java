package com.callisto.diceroller.model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.beans.Character;
import com.callisto.diceroller.beans.Stat;
import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.StatChangedEvent;
import com.callisto.diceroller.bus.events.StatUpdatedEvent;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.XMLParser;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import io.realm.RealmList;

public class CharacterSheetModel {
    private int diceNumber = 0;
    private int rerollThreshold = 0;

    private Character activeCharacter;

    private RealmList<Stat> statistics;

    private ArrayList<Pair<String, Integer>> stats;

    private final DiceRoller diceRoller;

    public CharacterSheetModel(Context context) {
        this.diceRoller = new DiceRoller();
        this.stats = new ArrayList<>();

        this.statistics = new RealmList<>();

        subscribeToEvents();

        activeCharacter = RealmHelper.getInstance().get(Character.class, "Test character");

        if (activeCharacter == null)
        {
            statistics = XMLParser.parseStats(context);

            saveStatModels();

            activeCharacter = new Character("Test character", statistics);
        }
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    private void saveStatModels()
    {
        RealmHelper realm = RealmHelper.getInstance();

        for (Stat stat : statistics)
        {
            if (!realm.exists(Stat.class, stat.getName()))
            {
                stat.setId(realm.getLastId(Stat.class));

                realm.save(stat);
            }
        }

    }

    public int getDiceNumber() {
        return diceNumber;
    }

    private void calculateDiceNumber() {
        int pool = 0;

        for (Pair<String, Integer> pair : stats) {
            pool += pair.second;
        }

        diceNumber = pool;
    }

    public void changeDiceNumber(int value) {
        this.diceNumber += value;
    }

    public ArrayList<Integer> rollDice(int rerollThreshold, int dicePool) {
        return diceRoller.rollDice(rerollThreshold, dicePool);
    }

    public ArrayList<Integer> rollDice(int rerollThreshold) {
        return diceRoller.rollDice(rerollThreshold, diceNumber);
    }

    public ArrayList<Integer> rollDice() {
        return diceRoller.rollDice(rerollThreshold, diceNumber);
    }

    public int getSuccessesCofd(ArrayList<Integer> rolls) {
        return diceRoller.getSuccessesCofd(rolls);
    }

    public void addOrRemoveStat(Stat stat)
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

    public void addOrRemovePair(Pair<String, Integer> pair) {
        if (stats.contains(pair)) {
            stats.remove(pair);
        } else {
            stats.add(pair);
        }

        calculateDiceNumber();
    }

    public ArrayList<Pair<String, Integer>> getStats() {
        return stats;
    }

    public ArrayList<Integer> rollExtended(int threshold) {
        return diceRoller.rollExtended(threshold, diceNumber);
    }

    public Stat getStat(String statName)
    {
        for (Stat stat : activeCharacter.getStats())
        {
            if (stat.getName().equals(statName)) {
                return stat;
            }
        }

        return null;
    }

    public Stat getStatByTag(Object tag)
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

    public void persistChanges()
    {
        RealmHelper.getInstance().save(activeCharacter);
    }

    @Subscribe public void updateStatValue(StatChangedEvent event)
    {
        Stat stat = getStat(event.name);

        RealmHelper.getInstance().getRealm().beginTransaction();
        stat.setValue(event.value);
        RealmHelper.getInstance().getRealm().commitTransaction();

        int newScore;

        for (String watcher : stat.getObservers())
        {
            Stat observer = getStat(watcher);

            newScore = updateStatScore(observer);

            RealmHelper.getInstance().getRealm().beginTransaction();
            observer.setValue(newScore);
            RealmHelper.getInstance().getRealm().commitTransaction();

            BusProvider.getInstance().post(new StatUpdatedEvent(observer.getName(), newScore));
        }
    }

    private int updateStatScore(Stat observer)
    {
        int newScore;
        ArrayList<Stat> observedStats = new ArrayList<>();

        for (String watchedStat : observer.getObservedStats())
        {
            observedStats.add(getStat(watchedStat));
        }

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
}
