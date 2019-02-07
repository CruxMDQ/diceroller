package com.callisto.diceroller.viewmanagers;

import com.callisto.diceroller.persistence.objects.Stat;

import java.util.ArrayList;

public interface CharacterSheet
{
    interface View {
        void showResults(ArrayList<Integer> rolls, int successes, boolean isExtended);
        void setStatPanelColor(Stat stat);
        void addSelectedStatToPanel(Stat stat);
    }
}
