package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.model.RulesBuilder;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

class CharacterListModel
{
    private List<Character> characters;

    CharacterListModel()
    {
        this.characters = new ArrayList<>();

        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        characters.addAll(list);
    }

    List<Character> getCharacters()
    {
        return characters;
    }

    void createNewCharacter(String name, String template)
    {
        RulesBuilder.createNewCharacter(name, template);
    }
}
