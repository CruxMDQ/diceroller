package com.callisto.diceroller.model;

import android.util.Pair;

import java.util.ArrayList;

public class CharacterSheetModel {
    private int diceNumber = 0;
    private int rerollThreshold = 0;

    private ArrayList<Pair<String, Integer>> stats;

    private final DiceRoller diceRoller;

    public CharacterSheetModel() {
        this.diceRoller = new DiceRoller();
        this.stats = new ArrayList<>();
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public void setDiceNumber(int diceNumber) {
        this.diceNumber = diceNumber;
    }

    public void calculateDiceNumber() {
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

    public void addOrRemoveStat(Pair<String, Integer> pair) {
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
}
