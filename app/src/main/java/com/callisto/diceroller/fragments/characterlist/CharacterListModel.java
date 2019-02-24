package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.XMLParser;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.Template;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

class CharacterListModel
{
    private List<Character> characters;

    CharacterListModel()
    {
        this.characters = new ArrayList<>();

        RealmResults<Character> list = RealmHelper.getInstance().getList(Character.class);

        characters.addAll(list);

        if (characters.size() == 0)
        {
            createSampleCharacter("Andrzej Kryszycha");
            createSampleCharacter("Matthias Kreitmann");
        }
    }

    private void createSampleCharacter(String name)
    {
        RealmList<Stat> statistics = XMLParser.parseStats();

        Character character = new Character(
            name,
            statistics);

        character.setId(RealmHelper.getInstance().getLastId(Character.class));

        RealmHelper.getInstance().save(character);

        characters.add(character);
    }

    List<Character> getCharacters()
    {
        return characters;
    }

    void createNewCharacter(String name, String template)
    {
        Character character = new Character();

        character.setName(name);

        character.setStats(XMLParser.parseStats());

        character.setId(RealmHelper.getInstance().getLastId(Character.class));

        character.setTemplate(RealmHelper.getInstance().get(Template.class, template));

        RealmHelper.getInstance().save(character);
    }
}
