package com.callisto.diceroller.model;

import android.support.annotation.NonNull;

import com.callisto.diceroller.tools.Constants;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller {

    @NonNull
    public ArrayList<Integer> rollDice
        (int rollAgainThreshold,
         int dicePool) {

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < dicePool; i++) {
            int integer = getD10();

            result.add(integer);

            if (integer >= rollAgainThreshold) i--;
        }

        return result;
    }

    private int getD10() {
        return 1 + ThreadLocalRandom.current().nextInt(1, Constants.Values.COFD_DIE_SIZE.getValue());
    }

    public int getSuccessesCofd(ArrayList<Integer> rolls) {
        return getSuccessesCofd(rolls, Constants.Values.COFD_BASE_DIFFICULTY_THRESHOLD.getValue());
    }

    private int getSuccessesCofd(ArrayList<Integer> rolls, int threshold) {
        int successes = 0;

        for (Integer integer : rolls) {
            if (integer >= threshold) successes++;
        }

        return successes;
    }
}
