package com.callisto.diceroller.viewmanagers;

import com.callisto.diceroller.fragments.BaseFragment;

public interface MainActivityNavigation {
    interface View {
        void setFragment(BaseFragment fragment);
    }

    interface Presenter {
        void getRandomFragment();
    }
}
