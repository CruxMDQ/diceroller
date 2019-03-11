package com.callisto.diceroller.model;

import android.support.v4.content.ContextCompat;

import com.callisto.diceroller.R;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.System;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.tools.Constants;

import io.realm.RealmList;

import static com.callisto.diceroller.application.App.getInstance;
import static com.callisto.diceroller.tools.Constants.Advantages.BLOODPOTENCY;
import static com.callisto.diceroller.tools.Constants.Advantages.INNERLIGHT;
import static com.callisto.diceroller.tools.Constants.Attributes.COMPOSURE;
import static com.callisto.diceroller.tools.Constants.Attributes.DEXTERITY;
import static com.callisto.diceroller.tools.Constants.Attributes.INTELLIGENCE;
import static com.callisto.diceroller.tools.Constants.Attributes.MANIPULATION;
import static com.callisto.diceroller.tools.Constants.Attributes.PRESENCE;
import static com.callisto.diceroller.tools.Constants.Attributes.RESOLVE;
import static com.callisto.diceroller.tools.Constants.Attributes.STAMINA;
import static com.callisto.diceroller.tools.Constants.Attributes.STRENGTH;
import static com.callisto.diceroller.tools.Constants.Attributes.WITS;
import static com.callisto.diceroller.tools.Constants.Derived.DEFENSE;
import static com.callisto.diceroller.tools.Constants.Derived.HEALTH;
import static com.callisto.diceroller.tools.Constants.Derived.INITIATIVE;
import static com.callisto.diceroller.tools.Constants.Derived.SIZE;
import static com.callisto.diceroller.tools.Constants.Derived.SPEED;
import static com.callisto.diceroller.tools.Constants.Derived.WILLPOWER;
import static com.callisto.diceroller.tools.Constants.Fonts;
import static com.callisto.diceroller.tools.Constants.Keywords.ADVANTAGE;
import static com.callisto.diceroller.tools.Constants.Keywords.ATTRIBUTE;
import static com.callisto.diceroller.tools.Constants.Keywords.DERIVED;
import static com.callisto.diceroller.tools.Constants.Keywords.FINESSE;
import static com.callisto.diceroller.tools.Constants.Keywords.MENTAL;
import static com.callisto.diceroller.tools.Constants.Keywords.MORALITY;
import static com.callisto.diceroller.tools.Constants.Keywords.PHYSICAL;
import static com.callisto.diceroller.tools.Constants.Keywords.POWER;
import static com.callisto.diceroller.tools.Constants.Keywords.RESISTANCE;
import static com.callisto.diceroller.tools.Constants.Keywords.RESOURCE;
import static com.callisto.diceroller.tools.Constants.Keywords.SKILL;
import static com.callisto.diceroller.tools.Constants.Keywords.SOCIAL;
import static com.callisto.diceroller.tools.Constants.Moralities.BELIEF;
import static com.callisto.diceroller.tools.Constants.Moralities.HUMANITY;
import static com.callisto.diceroller.tools.Constants.Skills.ACADEMICS;
import static com.callisto.diceroller.tools.Constants.Skills.ANIMALKEN;
import static com.callisto.diceroller.tools.Constants.Skills.ATHLETICS;
import static com.callisto.diceroller.tools.Constants.Skills.BRAWL;
import static com.callisto.diceroller.tools.Constants.Skills.COMPUTER;
import static com.callisto.diceroller.tools.Constants.Skills.CRAFTS;
import static com.callisto.diceroller.tools.Constants.Skills.DRIVE;
import static com.callisto.diceroller.tools.Constants.Skills.EMPATHY;
import static com.callisto.diceroller.tools.Constants.Skills.EXPRESSION;
import static com.callisto.diceroller.tools.Constants.Skills.FIREARMS;
import static com.callisto.diceroller.tools.Constants.Skills.INTIMIDATION;
import static com.callisto.diceroller.tools.Constants.Skills.INVESTIGATION;
import static com.callisto.diceroller.tools.Constants.Skills.LARCENY;
import static com.callisto.diceroller.tools.Constants.Skills.MEDICINE;
import static com.callisto.diceroller.tools.Constants.Skills.OCCULT;
import static com.callisto.diceroller.tools.Constants.Skills.PERSUASION;
import static com.callisto.diceroller.tools.Constants.Skills.POLITICS;
import static com.callisto.diceroller.tools.Constants.Skills.SCIENCE;
import static com.callisto.diceroller.tools.Constants.Skills.SOCIALIZE;
import static com.callisto.diceroller.tools.Constants.Skills.STEALTH;
import static com.callisto.diceroller.tools.Constants.Skills.STREETWISE;
import static com.callisto.diceroller.tools.Constants.Skills.SUBTERFUGE;
import static com.callisto.diceroller.tools.Constants.Skills.SURVIVAL;
import static com.callisto.diceroller.tools.Constants.Skills.WEAPONRY;
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
                BLOODPOTENCY.getText()
            );

            bloodPotency.addKeyword(ADVANTAGE.getText());
            bloodPotency.setValue(1);

            realmHelper.save(bloodPotency);

            Stat vitae = new Stat(
                realmHelper.getLastId(Stat.class),
                Constants.Resources.VITAE.getText()
            );

            vitae.addKeyword(RESOURCE.getText());
            vitae.setValue(10);

            realmHelper.save(vitae);

            Stat humanity = new Stat(
                realmHelper.getLastId(Stat.class),
                HUMANITY.getText()
            );

            humanity.addKeyword(MORALITY.getText());
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

            Stat innerLight = new Stat(
                realmHelper.getLastId(Stat.class),
                INNERLIGHT.getText()
            );

            innerLight.addKeyword(ADVANTAGE.getText());
            innerLight.setValue(1);

            realmHelper.save(innerLight);

            Stat wisps = new Stat(
                realmHelper.getLastId(Stat.class),
                Constants.Resources.WISPS.getText()
            );

            wisps.addKeyword(RESOURCE.getText());
            wisps.setValue(10);

            realmHelper.save(wisps);

            Stat belief = new Stat(
                realmHelper.getLastId(Stat.class),
                BELIEF.getText()
            );

            belief.addKeyword(MORALITY.getText());
            belief.setValue(7);

            realmHelper.save(belief);

            noble.addTrait(innerLight);
            noble.addTrait(wisps);
            noble.addTrait(belief);

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
            INTELLIGENCE.getText(),
            color_power,
            0
        );

        intelligence
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(MENTAL.getText())
            .addKeyword(POWER.getText());

        list.add(realmHelper.add(intelligence));

        Stat wits = new Stat(
            realmHelper.getLastId(Stat.class),
            WITS.getText(),
            color_finesse,
            0
        );

        wits
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(MENTAL.getText())
            .addKeyword(FINESSE.getText());

        list.add(realmHelper.add(wits));

        Stat resolve = new Stat(
            realmHelper.getLastId(Stat.class),
            RESOLVE.getText(),
            color_resistance,
            0
        );

        resolve
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(MENTAL.getText())
            .addKeyword(RESISTANCE.getText());

        list.add(realmHelper.add(resolve));

        Stat strength = new Stat(
            realmHelper.getLastId(Stat.class),
            STRENGTH.getText(),
            color_power,
            0
        );

        strength
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(PHYSICAL.getText())
            .addKeyword(POWER.getText());

        list.add(realmHelper.add(strength));

        Stat dexterity = new Stat(
            realmHelper.getLastId(Stat.class),
            DEXTERITY.getText(),
            color_finesse,
            0
        );

        dexterity
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(PHYSICAL.getText())
            .addKeyword(FINESSE.getText());

        list.add(realmHelper.add(dexterity));

        Stat stamina = new Stat(
            realmHelper.getLastId(Stat.class),
            STAMINA.getText(),
            color_resistance,
            0
        );

        stamina
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(PHYSICAL.getText())
            .addKeyword(RESISTANCE.getText());

        list.add(realmHelper.add(stamina));

        Stat presence = new Stat(
            realmHelper.getLastId(Stat.class),
            PRESENCE.getText(),
            color_power,
            0
        );

        presence
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(SOCIAL.getText())
            .addKeyword(POWER.getText());

        list.add(realmHelper.add(presence));

        Stat manipulation = new Stat(
            realmHelper.getLastId(Stat.class),
            MANIPULATION.getText(),
            color_finesse,
            0
        );

        manipulation
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(SOCIAL.getText())
            .addKeyword(FINESSE.getText());

        list.add(realmHelper.add(manipulation));

        Stat composure = new Stat(
            realmHelper.getLastId(Stat.class),
            COMPOSURE.getText(),
            color_resistance,
            0
        );

        composure
            .addKeyword(ATTRIBUTE.getText())
            .addKeyword(SOCIAL.getText())
            .addKeyword(RESISTANCE.getText());

        list.add(realmHelper.add(composure));

        Stat academics = new Stat(
            realmHelper.getLastId(Stat.class),
            ACADEMICS.getText(),
            color_skill,
            0
        );
        
        academics
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());
        
        list.add(realmHelper.add(academics));

        Stat crafts = new Stat(
            realmHelper.getLastId(Stat.class),
            CRAFTS.getText(),
            color_skill,
            0
        );

        crafts
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(crafts));

        Stat computer = new Stat(
            realmHelper.getLastId(Stat.class),
            COMPUTER.getText(),
            color_skill,
            0
        );

        computer
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(computer));

        Stat investigation = new Stat(
            realmHelper.getLastId(Stat.class),
            INVESTIGATION.getText(),
            color_skill,
            0
        );

        investigation
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(investigation));

        Stat medicine = new Stat(
            realmHelper.getLastId(Stat.class),
            MEDICINE.getText(),
            color_skill,
            0
        );

        medicine
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(medicine));

        Stat occult = new Stat(
            realmHelper.getLastId(Stat.class),
            OCCULT.getText(),
            color_skill,
            0
        );

        occult
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(occult));

        Stat politics = new Stat(
            realmHelper.getLastId(Stat.class),
            POLITICS.getText(),
            color_skill,
            0
        );

        politics
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(politics));

        Stat science = new Stat(
            realmHelper.getLastId(Stat.class),
            SCIENCE.getText(),
            color_skill,
            0
        );

        science
            .addKeyword(SKILL.getText())
            .addKeyword(MENTAL.getText());

        list.add(realmHelper.add(science));

        Stat athletics = new Stat(
            realmHelper.getLastId(Stat.class),
            ATHLETICS.getText(),
            color_skill,
            0
        );

        athletics
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(athletics));

        Stat brawl = new Stat(
            realmHelper.getLastId(Stat.class),
            BRAWL.getText(),
            color_skill,
            0
        );

        brawl
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(brawl));

        Stat drive = new Stat(
            realmHelper.getLastId(Stat.class),
            DRIVE.getText(),
            color_skill,
            0
        );

        drive
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(drive));

        Stat firearms = new Stat(
            realmHelper.getLastId(Stat.class),
            FIREARMS.getText(),
            color_skill,
            0
        );

        firearms
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(firearms));

        Stat larceny = new Stat(
            realmHelper.getLastId(Stat.class),
            LARCENY.getText(),
            color_skill,
            0
        );

        larceny
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(larceny));

        Stat stealth = new Stat(
            realmHelper.getLastId(Stat.class),
            STEALTH.getText(),
            color_skill,
            0
        );

        stealth
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(stealth));

        Stat survival = new Stat(
            realmHelper.getLastId(Stat.class),
            SURVIVAL.getText(),
            color_skill,
            0
        );

        survival
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(survival));

        Stat weaponry = new Stat(
            realmHelper.getLastId(Stat.class),
            WEAPONRY.getText(),
            color_skill,
            0
        );

        weaponry
            .addKeyword(SKILL.getText())
            .addKeyword(PHYSICAL.getText());

        list.add(realmHelper.add(weaponry));

        Stat animalKen = new Stat(
            realmHelper.getLastId(Stat.class),
            ANIMALKEN.getText(),
            color_skill,
            0
        );

        animalKen
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(animalKen));

        Stat empathy = new Stat(
            realmHelper.getLastId(Stat.class),
            EMPATHY.getText(),
            color_skill,
            0
        );

        empathy
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(empathy));

        Stat expression = new Stat(
            realmHelper.getLastId(Stat.class),
            EXPRESSION.getText(),
            color_skill,
            0
        );

        expression
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(expression));

        Stat intimidation = new Stat(
            realmHelper.getLastId(Stat.class),
            INTIMIDATION.getText(),
            color_skill,
            0
        );

        intimidation
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(intimidation));

        Stat persuasion = new Stat(
            realmHelper.getLastId(Stat.class),
            PERSUASION.getText(),
            color_skill,
            0
        );

        persuasion
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(persuasion));

        Stat socialize = new Stat(
            realmHelper.getLastId(Stat.class),
            SOCIALIZE.getText(),
            color_skill,
            0
        );

        socialize
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(socialize));

        Stat streetwise = new Stat(
            realmHelper.getLastId(Stat.class),
            STREETWISE.getText(),
            color_skill,
            0
        );

        streetwise
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(streetwise));

        Stat subterfuge = new Stat(
            realmHelper.getLastId(Stat.class),
            SUBTERFUGE.getText(),
            color_skill,
            0
        );

        subterfuge
            .addKeyword(SKILL.getText())
            .addKeyword(SOCIAL.getText());

        list.add(realmHelper.add(subterfuge));

        Stat size = new Stat(
            realmHelper.getLastId(Stat.class),
            SIZE.getText(),
            DERIVED.getText(),
            5
        );

        size
            .addKeyword(DERIVED.getText());

        list.add(realmHelper.add(size));

        Stat defense = new Stat(
            realmHelper.getLastId(Stat.class),
            DEFENSE.getText(),
            DERIVED.getText(),
            0
        );

        defense
            .addKeyword(DERIVED.getText());

        list.add(realmHelper.add(defense));

        Stat health = new Stat(
            realmHelper.getLastId(Stat.class),
            HEALTH.getText(),
            DERIVED.getText(),
            0
        );

        health
            .addKeyword(DERIVED.getText());

        list.add(realmHelper.add(health));

        Stat initiative = new Stat(
            realmHelper.getLastId(Stat.class),
            INITIATIVE.getText(),
            DERIVED.getText(),
            0
        );

        initiative
            .addKeyword(DERIVED.getText());

        list.add(realmHelper.add(initiative));

        Stat speed = new Stat(
            realmHelper.getLastId(Stat.class),
            SPEED.getText(),
            DERIVED.getText(),
            0
        );

        speed
            .addKeyword(DERIVED.getText());

        list.add(realmHelper.add(speed));

        Stat willpower = new Stat(
            realmHelper.getLastId(Stat.class),
            WILLPOWER.getText(),
            DERIVED.getText(),
            0
        );

        willpower
            .addKeyword(DERIVED.getText());

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
