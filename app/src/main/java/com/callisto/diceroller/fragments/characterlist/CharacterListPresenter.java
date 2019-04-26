package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.bus.BusProvider;
import com.callisto.diceroller.bus.events.CharacterEditorRequestedEvent;
import com.callisto.diceroller.bus.events.CombatScreenRequestedEvent;
import com.callisto.diceroller.bus.events.OpposedCheckScreenRequestedEvent;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Template;

import java.util.List;

class CharacterListPresenter
{
    private final CharacterListModel model;
    private final CharacterListFragment view;

    CharacterListPresenter(CharacterListFragment view)
    {
        this.model = new CharacterListModel();
        this.view = view;

        subscribeToEvents();
    }

    List<Character> getCharacters()
    {
        return model.getCharacters();
    }

    List<Character> getSelectedCharacters()
    {
        return model.getSelectedCharacters();
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

    List<Template> getTemplates()
    {
        return model.getTemplates();
    }

    void requestOpposedCheckFragment()
    {
        BusProvider.getInstance().post(new OpposedCheckScreenRequestedEvent());
    }

    void addOrRemoveSelectedCharacter(Character character)
    {
        model.addOrRemoveSelectedCharacter(character);
        view.checkIfCombatButtonShouldBeVisible(model.getSelectedCharacters().size());
    }

    Character getCharacter(int position)
    {
        return model.getCharacters().get(position);
    }

    void requestCombatFragment()
    {
        BusProvider.getInstance().post(new CombatScreenRequestedEvent(model.getSelectedCharacters()));
    }
}
