package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.model.RulesBuilder;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Template;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

class CharacterListModel
{
    private List<Character> characters = new ArrayList<>();

    private List<Character> selectedCharacters = new ArrayList<>();

    CharacterListModel()
    {
        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        characters.addAll(list);
    }

    List<Character> getCharacters()
    {
        return characters;
    }

    List<Character> getSelectedCharacters()
    {
        return selectedCharacters;
    }

    void createNewCharacter(String name, String template)
    {
        RulesBuilder.createNewCharacter(name, template);
    }

    List<Template> getTemplates()
    {
        return RealmHelper.getInstance().getList(Template.class);
    }

    void addOrRemoveSelectedCharacter(Character character)
    {
        if (!selectedCharacters.contains(character))
        {
            selectedCharacters.add(character);
        }
        else
        {
            selectedCharacters.remove(character);
        }
    }
}
