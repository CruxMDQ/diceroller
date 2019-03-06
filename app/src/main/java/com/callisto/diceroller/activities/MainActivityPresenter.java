package com.callisto.diceroller.activities;

import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.CharacterEditorRequestedEvent;
import com.callisto.diceroller.fragments.BaseFragment;
import com.callisto.diceroller.viewmanagers.FragmentNavigation;
import com.callisto.diceroller.viewmanagers.MainActivityNavigation;
import com.squareup.otto.Subscribe;

public class MainActivityPresenter
    implements
    FragmentNavigation.Presenter
{

    private final MainActivityNavigation.View view;

    MainActivityPresenter(MainActivityNavigation.View view)
    {
        this.view = view;

        subscribeToEvents();
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    @Override
    public void addFragment(BaseFragment fragment, String tag)
    {
        view.setFragment(fragment, tag);
    }

    @Subscribe
    public void loadCharacterEditor(CharacterEditorRequestedEvent event)
    {
        view.loadCharacterSheet(
                event.getFont(),
                event.getCharacterName()
        );
    }
}
