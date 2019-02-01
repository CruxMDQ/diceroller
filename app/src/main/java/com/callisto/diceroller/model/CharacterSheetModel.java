package com.callisto.diceroller.model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.beans.Stat;
import com.callisto.diceroller.tools.XMLParser;

import java.util.ArrayList;

public class CharacterSheetModel {
    private int diceNumber = 0;
    private int rerollThreshold = 0;

    private ArrayList<Stat> statistics;

    private ArrayList<Pair<String, Integer>> stats;

    private final DiceRoller diceRoller;

    public CharacterSheetModel(Context context) {
        this.diceRoller = new DiceRoller();
        this.stats = new ArrayList<>();

        this.statistics = new ArrayList<>();

        statistics = XMLParser.parseStats(context);
    }

    // Doesn't this kind of relation actually work better in a database?
    public void setWatches()
    {
        Stat composure = getStat(App.getRes().getString(R.string.label_attr_com));
        Stat dexterity = getStat(App.getRes().getString(R.string.label_attr_dex));
        Stat health = getStat(App.getRes().getString(R.string.label_derived_health));
        Stat initiative = getStat(App.getRes().getString(R.string.label_derived_initiative));
        Stat resolve = getStat(App.getRes().getString(R.string.label_attr_res));
        Stat speed = getStat(App.getRes().getString(R.string.label_derived_speed));
        Stat stamina = getStat(App.getRes().getString(R.string.label_attr_sta));
        Stat strength = getStat(App.getRes().getString(R.string.label_attr_str));
        Stat size = getStat(App.getRes().getString(R.string.label_derived_size));
        Stat willpower = getStat(App.getRes().getString(R.string.label_derived_willpower));

        // TODO Get defense working

        health
            .addOrRemoveObservedStat(stamina)
            .addOrRemoveObservedStat(size);
        stamina.addOrRemoveObserver(health);
        size.addOrRemoveObserver(health);

        initiative
            .addOrRemoveObservedStat(dexterity)
            .addOrRemoveObservedStat(composure);
        dexterity.addOrRemoveObserver(initiative);
        composure.addOrRemoveObserver(initiative);

        // TODO Figure out best way to pass on base value (in this case 5)
        speed
            .addOrRemoveObservedStat(strength)
            .addOrRemoveObservedStat(dexterity);
        strength.addOrRemoveObserver(speed);
        dexterity.addOrRemoveObserver(speed);

        willpower
            .addOrRemoveObservedStat(resolve)
            .addOrRemoveObservedStat(composure);
        resolve.addOrRemoveObserver(willpower);
        composure.addOrRemoveObserver(willpower);
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
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

    public int getRerollThreshold() {
        return rerollThreshold;
    }

    public void setRerollThreshold(int rerollThreshold) {
        this.rerollThreshold = rerollThreshold;
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
        if (statistics.contains(stat))
        {
            statistics.remove(stat);
        }
        else
        {
            statistics.add(stat);
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
        for (Stat stat : statistics)
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
            for (Stat stat : statistics)
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
        // TODO Implement Realm persistence here
    }
}
