package com.callisto.diceroller.viewmanagers;

import java.util.ArrayList;

public interface DiceRollerNavigation {

    interface View {
        void showResults(ArrayList<Integer> rolls, int successes, boolean isExtended);
    }

    interface Presenter {
        int getReRollThreshold();
        int getDiceNumber();
    }

}
