package com.callisto.diceroller.viewmanagers;

import com.callisto.diceroller.fragments.BaseFragment;

public interface FragmentNavigation {
    interface View {
        void attachPresenter(FragmentNavigation.Presenter presenter);
    }

    interface Presenter {
        void addFragment(BaseFragment fragment);
    }
}
