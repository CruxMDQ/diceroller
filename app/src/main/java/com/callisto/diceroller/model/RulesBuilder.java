package com.callisto.diceroller.model;

import android.support.v4.content.ContextCompat;

import com.callisto.diceroller.R;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.System;
import com.callisto.diceroller.persistence.objects.Template;

import io.realm.RealmList;

import static com.callisto.diceroller.application.App.getInstance;
import static com.callisto.diceroller.application.App.getRes;
import static com.callisto.diceroller.tools.Constants.Fonts;
import static com.callisto.diceroller.tools.Constants.Systems;
import static com.callisto.diceroller.tools.Constants.Templates;

public class RulesBuilder
{
    static public void build()
    {
        createSystems();

        createTemplates();
    }

    private static void createTemplates()
    {
        RealmHelper realmHelper = RealmHelper.getInstance();

        if (!realmHelper.exists(Template.class, Templates.KINDRED.getText()))
        {
            Template kindred = new Template(
                realmHelper.getLastId(Template.class),
                Templates.KINDRED.getText(),
                Fonts.CEZANNE.getText(),
                realmHelper.get(System.class, Systems.COFD.getText())
            );

            Stat bloodPotency = new Stat(
                realmHelper.getLastId(Stat.class),
                getRes().getString(R.string.label_advantage_blood_potency)
            );

            bloodPotency.addKeyword(getRes().getString(R.string.label_advantage));
            bloodPotency.setValue(1);

            realmHelper.save(bloodPotency);

            Stat vitae = new Stat(
                realmHelper.getLastId(Stat.class),
                getRes().getString(R.string.label_resource_vitae)
            );

            vitae.addKeyword(getRes().getString(R.string.label_resource));
            vitae.setValue(10);

            realmHelper.save(vitae);

            Stat humanity = new Stat(
                realmHelper.getLastId(Stat.class),
                getRes().getString(R.string.label_morality_humanity)
            );

            humanity.addKeyword(getRes().getString(R.string.label_morality));
            humanity.setValue(7);

            realmHelper.save(humanity);

            kindred.addTrait(bloodPotency);
            kindred.addTrait(vitae);
            kindred.addTrait(humanity);

            realmHelper.save(kindred);
        }

        if (!realmHelper.exists(Template.class, Templates.NOBLE.getText()))
        {
            Template noble = new Template(
                realmHelper.getLastId(Template.class),
                Templates.NOBLE.getText(),
                Fonts.ITALIANNO.getText(),
                realmHelper.get(System.class, Systems.COFD.getText())
            );

            realmHelper.save(noble);
        }
    }

    private static void createSystems()
    {
        System cofd = new System(
            RealmHelper.getInstance().getLastId(System.class),
            Systems.COFD.getText()
        );

        RealmHelper.getInstance().save(cofd);
    }

    public static RealmList<Stat> generateEmptyStatList()
    {
        RealmList<Stat> list = new RealmList<>();

        int color_power = ContextCompat
            .getColor(getInstance().getApplicationContext(), R.color.color_red_dark);
        int color_finesse = ContextCompat
            .getColor(getInstance().getApplicationContext(), R.color.color_green_dark);
        int color_resistance = ContextCompat
            .getColor(getInstance().getApplicationContext(), R.color.color_blue_dark);
        int color_skill = ContextCompat
            .getColor(getInstance().getApplicationContext(), R.color.color_purple_dark);

        RealmHelper realmHelper = RealmHelper.getInstance();

        Stat intelligence = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_int),
            color_power,
            0
        );

        intelligence
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_mental))
            .addKeyword(getRes().getString(R.string.stat_kind_power));

        list.add(realmHelper.add(intelligence));

        Stat wits = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_wits),
            color_finesse,
            0
        );

        wits
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_mental))
            .addKeyword(getRes().getString(R.string.stat_kind_finesse));

        list.add(realmHelper.add(wits));

        Stat resolve = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_res),
            color_resistance,
            0
        );

        resolve
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_mental))
            .addKeyword(getRes().getString(R.string.stat_kind_resistance));

        list.add(realmHelper.add(resolve));

        Stat strength = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_str),
            color_power,
            0
        );

        strength
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_physical))
            .addKeyword(getRes().getString(R.string.stat_kind_power));

        list.add(realmHelper.add(strength));

        Stat dexterity = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_dex),
            color_finesse,
            0
        );

        dexterity
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_physical))
            .addKeyword(getRes().getString(R.string.stat_kind_finesse));

        list.add(realmHelper.add(dexterity));

        Stat stamina = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_sta),
            color_resistance,
            0
        );

        stamina
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_physical))
            .addKeyword(getRes().getString(R.string.stat_kind_resistance));

        list.add(realmHelper.add(stamina));

        Stat presence = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_pre),
            color_power,
            0
        );

        presence
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_social))
            .addKeyword(getRes().getString(R.string.stat_kind_power));

        list.add(realmHelper.add(presence));

        Stat manipulation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_man),
            color_finesse,
            0
        );

        manipulation
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_social))
            .addKeyword(getRes().getString(R.string.stat_kind_finesse));

        list.add(realmHelper.add(manipulation));

        Stat composure = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_com),
            color_resistance,
            0
        );

        composure
            .addKeyword(getRes().getString(R.string.stat_category_attr))
            .addKeyword(getRes().getString(R.string.stat_type_social))
            .addKeyword(getRes().getString(R.string.stat_kind_resistance));

        list.add(realmHelper.add(composure));

        Stat academics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_academics),
            color_skill,
            0
        );
        
        academics
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));
        
        list.add(realmHelper.add(academics));

        Stat crafts = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_crafts),
            color_skill,
            0
        );

        crafts
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(crafts));

        Stat computer = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_computer),
            color_skill,
            0
        );

        computer
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(computer));

        Stat investigation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_investigation),
            color_skill,
            0
        );

        investigation
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(investigation));

        Stat medicine = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_medicine),
            color_skill,
            0
        );

        medicine
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(medicine));

        Stat occult = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_occult),
            color_skill,
            0
        );

        occult
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(occult));

        Stat politics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_politics),
            color_skill,
            0
        );

        politics
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(politics));

        Stat science = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_science),
            color_skill,
            0
        );

        science
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_mental));

        list.add(realmHelper.add(science));

        Stat athletics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_athletics),
            color_skill,
            0
        );

        athletics
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(athletics));

        Stat brawl = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_brawl),
            color_skill,
            0
        );

        brawl
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(brawl));

        Stat drive = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_drive),
            color_skill,
            0
        );

        drive
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(drive));

        Stat firearms = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_firearms),
            color_skill,
            0
        );

        firearms
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(firearms));

        Stat larceny = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_larceny),
            color_skill,
            0
        );

        larceny
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(larceny));

        Stat stealth = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_stealth),
            color_skill,
            0
        );

        stealth
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(stealth));

        Stat survival = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_survival),
            color_skill,
            0
        );

        survival
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(survival));

        Stat weaponry = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_weaponry),
            color_skill,
            0
        );

        weaponry
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_physical));

        list.add(realmHelper.add(weaponry));

        Stat animalKen = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_animal_ken),
            color_skill,
            0
        );

        animalKen
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(animalKen));

        Stat empathy = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_empathy),
            color_skill,
            0
        );

        empathy
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(empathy));

        Stat expression = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_expression),
            color_skill,
            0
        );

        expression
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(expression));

        Stat intimidation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_intimidation),
            color_skill,
            0
        );

        intimidation
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(intimidation));

        Stat persuasion = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_persuasion),
            color_skill,
            0
        );

        persuasion
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(persuasion));

        Stat socialize = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_socialize),
            color_skill,
            0
        );

        socialize
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(socialize));

        Stat streetwise = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_streetwise),
            color_skill,
            0
        );

        streetwise
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(streetwise));

        Stat subterfuge = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_subterfuge),
            color_skill,
            0
        );

        subterfuge
            .addKeyword(getRes().getString(R.string.stat_category_skill))
            .addKeyword(getRes().getString(R.string.stat_type_social));

        list.add(realmHelper.add(subterfuge));

        Stat size = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_core_size),
            getRes().getString(R.string.stat_category_derived),
            5
        );

        list.add(realmHelper.add(size));

        Stat defense = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_derived_defense),
            getRes().getString(R.string.stat_category_derived),
            0
        );

        list.add(realmHelper.add(defense));

        Stat health = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_derived_health),
            getRes().getString(R.string.stat_category_derived),
            0
        );

        list.add(realmHelper.add(health));

        Stat initiative = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_derived_initiative),
            getRes().getString(R.string.stat_category_derived),
            0
        );

        list.add(realmHelper.add(initiative));

        Stat speed = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_derived_speed),
            getRes().getString(R.string.stat_category_derived),
            0
        );

        list.add(realmHelper.add(speed));

        Stat willpower = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_derived_willpower),
            getRes().getString(R.string.stat_category_derived),
            0
        );

        list.add(realmHelper.add(willpower));

        wits.addObserver(defense);

        resolve.addObserver(willpower);

        strength.addObserver(speed);

        dexterity.addObserver(initiative);
        dexterity.addObserver(speed);
        dexterity.addObserver(defense);

        stamina.addObserver(health);

        composure.addObserver(initiative);
        composure.addObserver(willpower);

        defense.addObservedStat(dexterity);
        defense.addObservedStat(wits);

        health.addObservedStat(size);
        health.addObservedStat(stamina);

        initiative.addObservedStat(composure);
        initiative.addObservedStat(dexterity);

        speed.addObservedStat(dexterity);
        speed.addObservedStat(strength);

        willpower.addObservedStat(composure);
        willpower.addObservedStat(resolve);

        return list;
    }

    public static void createNewCharacter(String name, String templateName)
    {
        Character character = new Character();

        character.setName(name);

        character.setStats(generateEmptyStatList());

        RealmHelper realmHelper = RealmHelper.getInstance();

        character.setId(realmHelper.getLastId(Character.class));

        Template template = realmHelper.get(Template.class, templateName);

        character.setTemplate(template);

        RealmList<Stat> traits = template.getTraits();
        for (Stat stat : traits)
        {
            Stat trait = new Stat(
                realmHelper.getLastId(Stat.class),
                stat.getName()
            );

            for (String keyword : stat.getKeywords())
            {
                trait.addKeyword(keyword);
            }

            trait.setValue(stat.getValue());

            realmHelper.save(trait);

            character.getStats().add(trait);
        }

        realmHelper.save(character);
    }
}
