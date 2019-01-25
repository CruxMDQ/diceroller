package com.callisto.diceroller.presenters;

import android.util.Pair;

import com.callisto.diceroller.model.CharacterSheetModel;
import com.callisto.diceroller.viewmanagers.DiceRollerNavigation;

import java.util.ArrayList;

public class CharacterSheetPresenter {
    private final DiceRollerNavigation.View view;
    private final CharacterSheetModel model;

    public CharacterSheetPresenter(DiceRollerNavigation.View view) {
        this.view = view;
        this.model = new CharacterSheetModel();
    }

    public void rollDice(
            int dicePool,           // How many dice are rolled?
            int rerollThreshold)    // What is the minimum die roll required for rerolls?
    {

        ArrayList<Integer> rolls = model.rollDice(rerollThreshold, dicePool);

        int successes = model.getSuccessesCofd(rolls);

        view.showResults(rolls, successes, false);
    }

    public void rollDice(int threshold) {
        ArrayList<Integer> rolls = model.rollDice(threshold);

        int successes = model.getSuccessesCofd(rolls);

        view.showResults(rolls, successes, false);
    }

    public void rollDice() {
        ArrayList<Integer> rolls = model.rollDice();

        int successes = model.getSuccessesCofd(rolls);

        view.showResults(rolls, successes, false);
    }


    public void changeDicePool(int value) {
        model.changeDiceNumber(value);
    }

    public void addOrRemoveStat(Pair<String, Integer> pair) {
        model.addOrRemoveStat(pair);
    }

    public ArrayList<Pair<String, Integer>> getStats() {
        return model.getStats();
    }

    public boolean hasDiceToRoll() {
        return model.getDiceNumber() > 0;
    }

    public void rollExtended(int threshold) {
        ArrayList<Integer> rolls = model.rollExtended(threshold);

        int successes = model.getSuccessesCofd(rolls);

        view.showResults(rolls, successes, true);
    }
}
