package com.callisto.diceroller.persistence;

import android.support.v4.content.ContextCompat;

import com.callisto.diceroller.R;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.System;
import com.callisto.diceroller.persistence.objects.Template;

import io.realm.RealmList;

import static com.callisto.diceroller.application.App.getInstance;
import static com.callisto.diceroller.application.App.getRes;
import static com.callisto.diceroller.tools.Constants.Fonts;
import static com.callisto.diceroller.tools.Constants.Systems;
import static com.callisto.diceroller.tools.Constants.Templates;

public class BaseDataBuilder
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
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_mental),
            getRes().getString(R.string.stat_kind_power),
            color_power,
            0
        );

        list.add(realmHelper.add(intelligence));

        Stat wits = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_wits),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_mental),
            getRes().getString(R.string.stat_kind_finesse),
            color_finesse,
            0
        );

        list.add(realmHelper.add(wits));

        Stat resolve = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_res),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_mental),
            getRes().getString(R.string.stat_kind_resistance),
            color_resistance,
            0
        );

        list.add(realmHelper.add(resolve));

        Stat strength = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_str),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_physical),
            getRes().getString(R.string.stat_kind_power),
            color_power,
            0
        );

        list.add(realmHelper.add(strength));

        Stat dexterity = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_dex),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_physical),
            getRes().getString(R.string.stat_kind_finesse),
            color_finesse,
            0
        );

        list.add(realmHelper.add(dexterity));

        Stat stamina = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_sta),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_physical),
            getRes().getString(R.string.stat_kind_resistance),
            color_resistance,
            0
        );

        list.add(realmHelper.add(stamina));

        Stat presence = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_pre),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_social),
            getRes().getString(R.string.stat_kind_power),
            color_power,
            0
        );

        list.add(realmHelper.add(presence));

        Stat manipulation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_man),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_social),
            getRes().getString(R.string.stat_kind_finesse),
            color_finesse,
            0
        );

        list.add(realmHelper.add(manipulation));

        Stat composure = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_attr_com),
            getRes().getString(R.string.stat_category_attr),
            getRes().getString(R.string.stat_type_social),
            getRes().getString(R.string.stat_kind_resistance),
            color_resistance,
            0
        );

        list.add(realmHelper.add(composure));

        Stat academics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_academics),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(academics));

        Stat crafts = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_crafts),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(crafts));

        Stat computer = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_computer),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(computer));

        Stat investigation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_investigation),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(investigation));

        Stat medicine = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_medicine),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(medicine));

        Stat occult = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_occult),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(occult));

        Stat politics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_politics),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(politics));

        Stat science = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_science),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_mental),
            color_skill
        );

        list.add(realmHelper.add(science));

        Stat athletics = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_athletics),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(athletics));

        Stat brawl = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_brawl),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(brawl));

        Stat drive = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_drive),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(drive));

        Stat firearms = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_guns),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(firearms));

        Stat larceny = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_larceny),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(larceny));

        Stat stealth = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_stealth),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(stealth));

        Stat survival = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_survival),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(survival));

        Stat weaponry = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_weaponry),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(weaponry));

        Stat animalKen = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_animal_ken),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_social),
            color_skill
        );

        list.add(realmHelper.add(animalKen));

        Stat empathy = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_empathy),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(empathy));

        Stat expression = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_expression),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(expression));

        Stat intimidation = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_intimidation),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(intimidation));

        Stat persuasion = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_persuasion),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(persuasion));

        Stat socialize = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_socialize),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(socialize));

        Stat streetwise = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_streetwise),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

        list.add(realmHelper.add(streetwise));

        Stat subterfuge = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.skill_subterfuge),
            getRes().getString(R.string.stat_category_skill),
            getRes().getString(R.string.stat_type_physical),
            color_skill
        );

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

        Stat integrity = new Stat(
            realmHelper.getLastId(Stat.class),
            getRes().getString(R.string.label_core_integrity),
            getRes().getString(R.string.stat_category_derived),
            7
        );

        list.add(realmHelper.add(integrity));

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
}
