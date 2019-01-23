package com.callisto.diceroller.presenters;

import com.callisto.diceroller.model.DiceRoller;
import com.callisto.diceroller.viewmanagers.DiceRollerNavigation;

import java.util.ArrayList;

public class DiceRollerPresenter {

    private final DiceRollerNavigation.View view;

    private DiceRoller roller;

    public DiceRollerPresenter(DiceRollerNavigation.View view) {
        this.view = view;
        this.roller = new DiceRoller();
    }

    public void rollDice(
        int dicePool,           // How many dice are rolled?
        int rerollThreshold)    // What is the minimum die roll required for rerolls?
    {
        ArrayList<Integer> rolls = roller.rollDice(rerollThreshold, dicePool);

        int successes = roller.getSuccessesCofd(rolls);

        view.showResults(rolls, successes);
    }
}
