package com.callisto.diceroller.fragments.characterlist;

import android.support.v4.content.ContextCompat;

import com.callisto.diceroller.R;
import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.BaseDataBuilder;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.tools.Constants;

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
//        RealmList<Stat> statistics = XMLParser.parseStats();
        RealmList<Stat> statistics = BaseDataBuilder.generateEmptyStatList();

        Character character = new Character(
            name,
            statistics);

        character.setId(RealmHelper.getInstance().getLastId(Character.class));

        character.setTemplate(RealmHelper.getInstance().get(Template.class, Constants.Templates.KINDRED.getText()));

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

//        character.setStats(XMLParser.parseStats());
        character.setStats(BaseDataBuilder.generateEmptyStatList());

        RealmHelper realmHelper = RealmHelper.getInstance();

        character.setId(realmHelper.getLastId(Character.class));

        character.setTemplate(realmHelper.get(Template.class, template));

        if (character.getTemplate().getName().equals(Constants.Templates.KINDRED.getText()))
        {
            Stat bloodPotency = new Stat(
                realmHelper.getLastId(Stat.class),
                App.getRes().getString(R.string.stat_kindred_blood_potency),
                App.getRes().getString(R.string.stat_category_template),
                App.getRes().getString(R.string.stat_type_core),
                ContextCompat.getColor(App.getInstance().getApplicationContext(), R.color.color_red_dark)
            );

            bloodPotency.setValue(1);

            realmHelper.save(bloodPotency);

            Stat vitae = new Stat(
                realmHelper.getLastId(Stat.class),
                App.getRes().getString(R.string.stat_kindred_vitae),
                App.getRes().getString(R.string.stat_category_template),
                App.getRes().getString(R.string.stat_type_core),
                App.getRes().getString(R.string.stat_kind_resource),
                ContextCompat.getColor(App.getInstance().getApplicationContext(), R.color.color_red_dark),
                10
            );

            realmHelper.save(vitae);
        }

        if (character.getTemplate().getName().equals(Constants.Templates.NOBLE.getText()))
        {
            Stat innerLight = new Stat(
                realmHelper.getLastId(Stat.class),
                App.getRes().getString(R.string.label_attr_int),
                App.getRes().getString(R.string.stat_category_template),
                App.getRes().getString(R.string.stat_type_core),
                ContextCompat.getColor(App.getInstance().getApplicationContext(), R.color.color_red_dark)
            );

            realmHelper.save(innerLight);
        }

        realmHelper.save(character);
    }
}
