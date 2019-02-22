package com.callisto.diceroller.fragments.characterlist;

import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.XMLParser;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;

class CharacterListModel
{
    private List<Character> characters;

    public static CharacterListModel newInstance()
    {
        return new CharacterListModel();
    }

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

        RealmHelper.getInstance().save(character);
    }

//    RealmList<Stat> buildBaseStatList()
//    {
//        RealmList<Stat> stats = new RealmList<>();
//
//        int color_power = ContextCompat
//            .getColor(App.getInstance().getApplicationContext(), R.color.color_red_dark);
//        int color_finesse = ContextCompat
//            .getColor(App.getInstance().getApplicationContext(), R.color.color_green_dark);
//        int color_resistance = ContextCompat
//            .getColor(App.getInstance().getApplicationContext(), R.color.color_blue_dark);
//        int color_skill = ContextCompat
//            .getColor(App.getInstance().getApplicationContext(), R.color.color_purple_dark);
//
//        Stat intelligence = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_int),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_mental),
//            App.getRes().getString(R.string.stat_kind_power),
//            color_power,
//            0
//        );
//
//        RealmHelper.getInstance().add(intelligence);
//
//        Stat wits = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_wits),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_mental),
//            App.getRes().getString(R.string.stat_kind_finesse),
//            color_finesse,
//            0
//        );
//
//        RealmHelper.getInstance().add(wits);
//
//        Stat resolve = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_res),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_mental),
//            App.getRes().getString(R.string.stat_kind_resistance),
//            color_resistance,
//            0
//        );
//
//        RealmHelper.getInstance().add(resolve);
//
//        Stat strength = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_str),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_physical),
//            App.getRes().getString(R.string.stat_kind_power),
//            color_power,
//            0
//        );
//
//        RealmHelper.getInstance().add(strength);
//
//        Stat dexterity = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_dex),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_physical),
//            App.getRes().getString(R.string.stat_kind_finesse),
//            color_finesse,
//            0
//        );
//
//        RealmHelper.getInstance().add(dexterity);
//
//        Stat stamina = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_sta),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_physical),
//            App.getRes().getString(R.string.stat_kind_resistance),
//            color_resistance,
//            0
//        );
//
//        RealmHelper.getInstance().add(stamina);
//
//        Stat presence = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_pre),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_social),
//            App.getRes().getString(R.string.stat_kind_power),
//            color_power,
//            0
//        );
//
//        RealmHelper.getInstance().add(presence);
//
//        Stat manipulation = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_man),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_social),
//            App.getRes().getString(R.string.stat_kind_finesse),
//            color_finesse,
//            0
//        );
//
//        RealmHelper.getInstance().add(manipulation);
//
//        Stat composure = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_attr_com),
//            App.getRes().getString(R.string.stat_category_attr),
//            App.getRes().getString(R.string.stat_type_social),
//            App.getRes().getString(R.string.stat_kind_resistance),
//            color_resistance,
//            0
//        );
//
//        RealmHelper.getInstance().add(composure);
//
//        Stat academics = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_academics),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(academics);
//
//        Stat crafts = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_crafts),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(crafts);
//
//        Stat computer = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_computer),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(computer);
//
//        Stat investigation = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_investigation),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(investigation);
//
//        Stat medicine = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_medicine),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(medicine);
//
//        Stat occult = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_occult),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(occult);
//
//        Stat politics = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_politics),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(politics);
//
//        Stat science = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_science),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_mental),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(science);
//
//        Stat athletics = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_athletics),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(athletics);
//
//        Stat brawl = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_brawl),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(brawl);
//
//        Stat drive = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_athletics),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(drive);
//
//        Stat firearms = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_guns),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(firearms);
//
//        Stat larceny = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_larceny),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(larceny);
//
//        Stat stealth = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_stealth),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(stealth);
//
//        Stat survival = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_survival),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(survival);
//
//        Stat weaponry = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_weaponry),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(weaponry);
//
//        Stat animalKen = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_animal_ken),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_social),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(animalKen);
//
//        Stat empathy = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_empathy),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(empathy);
//
//        Stat expression = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_expression),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(expression);
//
//        Stat intimidation = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_intimidation),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(intelligence);
//
//        Stat persuasion = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_persuasion),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(persuasion);
//
//        Stat socialize = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_socialize),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(socialize);
//
//        Stat streetwise = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_streetwise),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(streetwise);
//
//        Stat subterfuge = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.skill_subterfuge),
//            App.getRes().getString(R.string.stat_category_skill),
//            App.getRes().getString(R.string.stat_type_physical),
//            color_skill
//        );
//
//        RealmHelper.getInstance().add(subterfuge);
//
//        Stat size = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_size),
//            App.getRes().getString(R.string.stat_category_derived),
//            5
//        );
//
//        RealmHelper.getInstance().add(size);
//
//        Stat defense = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_defense),
//            App.getRes().getString(R.string.stat_category_derived),
//            0
//        );
//
//        RealmHelper.getInstance().add(defense);
//
//        Stat health = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_health),
//            App.getRes().getString(R.string.stat_category_derived),
//            0
//        );
//
//        RealmHelper.getInstance().add(health);
//
//        Stat initiative = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_initiative),
//            App.getRes().getString(R.string.stat_category_derived),
//            0
//        );
//
//        RealmHelper.getInstance().add(initiative);
//
//        Stat speed = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_speed),
//            App.getRes().getString(R.string.stat_category_derived),
//            0
//        );
//
//        RealmHelper.getInstance().add(speed);
//
//        Stat willpower = new Stat(
//            RealmHelper.getInstance().getLastId(Stat.class),
//            App.getRes().getString(R.string.label_derived_willpower),
//            App.getRes().getString(R.string.stat_category_derived),
//            0
//        );
//
//        RealmHelper.getInstance().add(willpower);
//
//        wits.addObserver(defense);
//
//        resolve.addObserver(willpower);
//
//        strength.addObserver(speed);
//
//        dexterity.addObserver(initiative);
//        dexterity.addObserver(speed);
//        dexterity.addObserver(defense);
//
//        stamina.addObserver(health);
//
//        composure.addObserver(initiative);
//        composure.addObserver(willpower);
//
//        defense.addObservedStat(dexterity);
//        defense.addObservedStat(wits);
//
//        health.addObservedStat(size);
//        health.addObservedStat(stamina);
//
//        initiative.addObservedStat(composure);
//        initiative.addObservedStat(dexterity);
//
//        speed.addObservedStat(dexterity);
//        speed.addObservedStat(strength);
//
//        willpower.addObservedStat(composure);
//        willpower.addObservedStat(resolve);
//
//    }
}
