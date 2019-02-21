package com.callisto.diceroller.fragments.charactersheet;

import android.util.Pair;

import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.PersistCharacterEvent;
import com.callisto.diceroller.persistence.objects.Stat;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class CharacterSheetPresenter
{
    private final CharacterSheet.View view;
    private final CharacterSheetModel model;

    CharacterSheetPresenter(CharacterSheet.View view)
    {
        this.view = view;
        this.model = CharacterSheetModel.newInstance(this);
    }

    CharacterSheetPresenter(CharacterSheet.View view, String characterName)
    {
        this.view = view;
        this.model = CharacterSheetModel.newInstance(characterName, this);

        subscribeToEvents();
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    public void rollDice(
        int dicePool,           // How many dice are rolled?
        int rerollThreshold)    // What is the minimum die roll required for rerolls?
    {
        showRolls(model.rollDice(rerollThreshold, dicePool));
    }

    void rollDice(int threshold)
    {
        showRolls(model.rollDice(threshold));
    }

    public void rollDice()
    {
        showRolls(model.rollDice());
    }

    void rollExtended(int threshold)
    {
        ArrayList<Integer> rolls = model.rollExtended(threshold);

        view.showResults(rolls, model.getSuccessesCofd(rolls), true);
    }

    private void showRolls(ArrayList<Integer> rolls)
    {
        view.showResults(rolls, model.getSuccessesCofd(rolls), false);
    }

    public void changeDicePool(int value)
    {
        model.changeDiceNumber(value);
    }

    void addOrRemoveStat(Pair<String, Integer> pair)
    {
        model.addOrRemovePair(pair);
    }

    ArrayList<Pair<String, Integer>> getStats()
    {
        return model.getStats();
    }

    boolean hasDiceToRoll()
    {
        return model.getDiceNumber() > 0;
    }

    Stat getStatDetails(String statName)
    {
        return model.getStat(statName);
    }

    Stat getStatByTag(Object tag)
    {
        return model.getStatByTag(tag);
    }

    void persistChanges()
    {
        model.persistChanges();
    }

    Stat getStatByName(String name)
    {
        return model.getStatByName(name);
    }

    @Subscribe void performPersistence(PersistCharacterEvent event)
    {
        model.persistChanges();
    }
}
