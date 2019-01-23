package com.callisto.diceroller.presenters;

import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.viewmanagers.FragmentNavigation;
import com.callisto.diceroller.viewmanagers.MainActivityNavigation;

public class MainActivityPresenter
    implements MainActivityNavigation.Presenter,
    FragmentNavigation.Presenter {

    private final MainActivityNavigation.View view;

    public MainActivityPresenter(MainActivityNavigation.View view) {
        this.view = view;
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        view.setFragment(fragment);
    }

    @Override
    public void getRandomFragment() {

    }
}
