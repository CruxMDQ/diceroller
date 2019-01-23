package com.callisto.diceroller.model;

import android.support.annotation.NonNull;

import com.callisto.diceroller.tools.Constants;

import java.util.ArrayList;
import java.util.Random;

public class DiceRoller {
//    private int getDicePoolCofd
//        (int attribute,
//        int skill,
//        int misc,
//        int penalties) {
//
//        return attribute + skill + misc - penalties;
//    }
//
//    public ArrayList<Integer> getRollsCofd
//        (int attribute,
//         int skill,
//         int misc,
//         int penalties) {
//        return getRollsCofd(attribute, skill, misc, penalties,
//            Constants.Values.COFD_BASE_ROLL_AGAIN_THRESHOLD.getValue());
//    }
//
//    public ArrayList<Integer> getRollsCofd
//        (int attribute,
//         int skill,
//         int misc,
//         int penalties,
//         int rollAgainThreshold) {
//
//        int dicePool = getDicePoolCofd(attribute, skill, misc, penalties);
//
//        if (dicePool < 1) dicePool = 1;
//
//        return rollDice(rollAgainThreshold, dicePool);
//    }

    @NonNull
    public ArrayList<Integer> rollDice
        (int dicePool) {

        return rollDice(Constants.Values.COFD_BASE_ROLL_AGAIN_THRESHOLD.getValue(), dicePool);
    }

    @NonNull
    public ArrayList<Integer> rollDice
        (int rollAgainThreshold,
         int dicePool) {

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < dicePool; i++) {
            int integer = new Random().nextInt(Constants.Values.COFD_DIE_SIZE.getValue());

            result.add(integer);

            while (integer >= rollAgainThreshold) {
                result.add(integer);
                integer = new Random().nextInt(Constants.Values.COFD_DIE_SIZE.getValue());
            }
        }

        return result;
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
