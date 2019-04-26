package com.callisto.diceroller.viewmanagers;

import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public interface MainActivityNavigation {
    interface View {
        void setFragment(BaseFragment fragment, String tag);
        void loadCharacterList();
        void loadCharacterSheet(String text, String characterName);
        void loadOpposedCheckScreen();
        void loadCombatScreen(List<Character> selectedCharacters);
    }
}
