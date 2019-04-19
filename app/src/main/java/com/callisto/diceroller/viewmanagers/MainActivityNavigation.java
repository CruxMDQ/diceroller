package com.callisto.diceroller.viewmanagers;

import com.callisto.diceroller.fragments.BaseFragment;

public interface MainActivityNavigation {
    interface View {
        void setFragment(BaseFragment fragment, String tag);
        void loadCharacterList();
        void loadCharacterSheet(String text, String characterName);
        void loadOpposedCheckScreen();
    }
}
