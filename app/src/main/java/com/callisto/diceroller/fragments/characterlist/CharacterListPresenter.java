package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.CharacterEditorRequestedEvent;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

class CharacterListPresenter
{
    private final CharacterListModel model;
    private final CharacterListView view;

    CharacterListPresenter(CharacterListView view)
    {
        this.model = new CharacterListModel();
        this.view = view;

        subscribeToEvents();
    }

    List<Character> getCharacters()
    {
        return model.getCharacters();
    }

    void createNewCharacter(String name, String template)
    {
        model.createNewCharacter(name, template);
    }

    private void subscribeToEvents()
    {
        BusProvider.getInstance().register(this);
    }

    void requestCharacterEditor(String characterName, String font)
    {
        BusProvider.getInstance().post(new CharacterEditorRequestedEvent(characterName, font));
    }
}
