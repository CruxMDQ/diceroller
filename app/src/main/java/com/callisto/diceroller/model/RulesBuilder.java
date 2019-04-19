package com.callisto.diceroller.model;

import android.support.v4.content.ContextCompat;

import com.callisto.diceroller.R;
import com.callisto.diceroller.persistence.RealmHelper;
import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Maneuver;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.System;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.tools.Constants;
import com.callisto.diceroller.tools.Constants.Maneuvers;
import com.callisto.diceroller.tools.Constants.Targets;

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
import static com.callisto.diceroller.tools.Constants.Feats.MELEE;
import static com.callisto.diceroller.tools.Constants.Feats.RANGED;
import static com.callisto.diceroller.tools.Constants.Feats.THROWN;
import static com.callisto.diceroller.tools.Constants.Feats.UNARMED;
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
    private static RealmHelper realmHelper = RealmHelper.getInstance();

    static public void build()
    {
        createSystems();

        createTemplates();

        if (realmHelper.getList(Maneuver.class).size() == 0)
        {
            createManeuvers();
        }
    }

    private static void createTemplates()
    {
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
        if (!realmHelper.exists(System.class, Systems.COFD.getText()))
        {
            System cofd = new System(
                RealmHelper.getInstance().getLastId(System.class),
                Systems.COFD.getText()
            );

            int color_power = ContextCompat
                .getColor(getInstance().getApplicationContext(), R.color.color_red_dark);
            int color_finesse = ContextCompat
                .getColor(getInstance().getApplicationContext(), R.color.color_green_dark);
            int color_resistance = ContextCompat
                .getColor(getInstance().getApplicationContext(), R.color.color_blue_dark);
            int color_skill = ContextCompat
                .getColor(getInstance().getApplicationContext(), R.color.color_purple_dark);

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

            realmHelper.add(intelligence);

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

            realmHelper.add(wits);

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

            realmHelper.add(resolve);

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

            realmHelper.add(strength);

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

            realmHelper.add(dexterity);

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

            realmHelper.add(stamina);

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

            realmHelper.add(presence);

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

            realmHelper.add(manipulation);

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

            realmHelper.add(composure);

            Stat academics = new Stat(
                realmHelper.getLastId(Stat.class),
                ACADEMICS.getText(),
                color_skill,
                0
            );

            academics
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(academics);

            Stat crafts = new Stat(
                realmHelper.getLastId(Stat.class),
                CRAFTS.getText(),
                color_skill,
                0
            );

            crafts
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(crafts);

            Stat computer = new Stat(
                realmHelper.getLastId(Stat.class),
                COMPUTER.getText(),
                color_skill,
                0
            );

            computer
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(computer);

            Stat investigation = new Stat(
                realmHelper.getLastId(Stat.class),
                INVESTIGATION.getText(),
                color_skill,
                0
            );

            investigation
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(investigation);

            Stat medicine = new Stat(
                realmHelper.getLastId(Stat.class),
                MEDICINE.getText(),
                color_skill,
                0
            );

            medicine
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(medicine);

            Stat occult = new Stat(
                realmHelper.getLastId(Stat.class),
                OCCULT.getText(),
                color_skill,
                0
            );

            occult
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(occult);

            Stat politics = new Stat(
                realmHelper.getLastId(Stat.class),
                POLITICS.getText(),
                color_skill,
                0
            );

            politics
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(politics);

            Stat science = new Stat(
                realmHelper.getLastId(Stat.class),
                SCIENCE.getText(),
                color_skill,
                0
            );

            science
                .addKeyword(SKILL.getText())
                .addKeyword(MENTAL.getText());

            realmHelper.add(science);

            Stat athletics = new Stat(
                realmHelper.getLastId(Stat.class),
                ATHLETICS.getText(),
                color_skill,
                0
            );

            athletics
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(athletics);

            Stat brawl = new Stat(
                realmHelper.getLastId(Stat.class),
                BRAWL.getText(),
                color_skill,
                0
            );

            brawl
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(brawl);

            Stat drive = new Stat(
                realmHelper.getLastId(Stat.class),
                DRIVE.getText(),
                color_skill,
                0
            );

            drive
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(drive);

            Stat firearms = new Stat(
                realmHelper.getLastId(Stat.class),
                FIREARMS.getText(),
                color_skill,
                0
            );

            firearms
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(firearms);

            Stat larceny = new Stat(
                realmHelper.getLastId(Stat.class),
                LARCENY.getText(),
                color_skill,
                0
            );

            larceny
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(larceny);

            Stat stealth = new Stat(
                realmHelper.getLastId(Stat.class),
                STEALTH.getText(),
                color_skill,
                0
            );

            stealth
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(stealth);

            Stat survival = new Stat(
                realmHelper.getLastId(Stat.class),
                SURVIVAL.getText(),
                color_skill,
                0
            );

            survival
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(survival);

            Stat weaponry = new Stat(
                realmHelper.getLastId(Stat.class),
                WEAPONRY.getText(),
                color_skill,
                0
            );

            weaponry
                .addKeyword(SKILL.getText())
                .addKeyword(PHYSICAL.getText());

            realmHelper.add(weaponry);

            Stat animalKen = new Stat(
                realmHelper.getLastId(Stat.class),
                ANIMALKEN.getText(),
                color_skill,
                0
            );

            animalKen
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(animalKen);

            Stat empathy = new Stat(
                realmHelper.getLastId(Stat.class),
                EMPATHY.getText(),
                color_skill,
                0
            );

            empathy
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(empathy);

            Stat expression = new Stat(
                realmHelper.getLastId(Stat.class),
                EXPRESSION.getText(),
                color_skill,
                0
            );

            expression
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(expression);

            Stat intimidation = new Stat(
                realmHelper.getLastId(Stat.class),
                INTIMIDATION.getText(),
                color_skill,
                0
            );

            intimidation
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(intimidation);

            Stat persuasion = new Stat(
                realmHelper.getLastId(Stat.class),
                PERSUASION.getText(),
                color_skill,
                0
            );

            persuasion
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(persuasion);

            Stat socialize = new Stat(
                realmHelper.getLastId(Stat.class),
                SOCIALIZE.getText(),
                color_skill,
                0
            );

            socialize
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(socialize);

            Stat streetwise = new Stat(
                realmHelper.getLastId(Stat.class),
                STREETWISE.getText(),
                color_skill,
                0
            );

            streetwise
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(streetwise);

            Stat subterfuge = new Stat(
                realmHelper.getLastId(Stat.class),
                SUBTERFUGE.getText(),
                color_skill,
                0
            );

            subterfuge
                .addKeyword(SKILL.getText())
                .addKeyword(SOCIAL.getText());

            realmHelper.add(subterfuge);

            Stat size = new Stat(
                realmHelper.getLastId(Stat.class),
                SIZE.getText(),
                DERIVED.getText(),
                5
            );

            size
                .addKeyword(DERIVED.getText());

            realmHelper.add(size);

            Stat defense = new Stat(
                realmHelper.getLastId(Stat.class),
                DEFENSE.getText(),
                DERIVED.getText(),
                0
            );

            defense
                .addKeyword(DERIVED.getText());

            realmHelper.add(defense);

            Stat health = new Stat(
                realmHelper.getLastId(Stat.class),
                HEALTH.getText(),
                DERIVED.getText(),
                0
            );

            health
                .addKeyword(DERIVED.getText());

            realmHelper.add(health);

            Stat initiative = new Stat(
                realmHelper.getLastId(Stat.class),
                INITIATIVE.getText(),
                DERIVED.getText(),
                0
            );

            initiative
                .addKeyword(DERIVED.getText());

            realmHelper.add(initiative);

            Stat speed = new Stat(
                realmHelper.getLastId(Stat.class),
                SPEED.getText(),
                DERIVED.getText(),
                0
            );

            speed
                .addKeyword(DERIVED.getText());

            realmHelper.add(speed);

            Stat willpower = new Stat(
                realmHelper.getLastId(Stat.class),
                WILLPOWER.getText(),
                DERIVED.getText(),
                0
            );

            willpower
                .addKeyword(DERIVED.getText());

            realmHelper.add(willpower);

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

            realmHelper.save(intelligence);
            realmHelper.save(wits);
            realmHelper.save(resolve);
            realmHelper.save(strength);
            realmHelper.save(dexterity);
            realmHelper.save(stamina);
            realmHelper.save(presence);
            realmHelper.save(manipulation);
            realmHelper.save(composure);
            realmHelper.save(academics);
            realmHelper.save(crafts);
            realmHelper.save(computer);
            realmHelper.save(investigation);
            realmHelper.save(medicine);
            realmHelper.save(occult);
            realmHelper.save(politics);
            realmHelper.save(science);
            realmHelper.save(athletics);
            realmHelper.save(brawl);
            realmHelper.save(drive);
            realmHelper.save(firearms);
            realmHelper.save(larceny);
            realmHelper.save(stealth);
            realmHelper.save(survival);
            realmHelper.save(weaponry);
            realmHelper.save(animalKen);
            realmHelper.save(empathy);
            realmHelper.save(expression);
            realmHelper.save(intimidation);
            realmHelper.save(persuasion);
            realmHelper.save(socialize);
            realmHelper.save(streetwise);
            realmHelper.save(subterfuge);
            realmHelper.save(size);
            realmHelper.save(defense);
            realmHelper.save(health);
            realmHelper.save(initiative);
            realmHelper.save(speed);
            realmHelper.save(willpower);

            cofd.addBaseStat(intelligence);
            cofd.addBaseStat(wits);
            cofd.addBaseStat(resolve);
            cofd.addBaseStat(strength);
            cofd.addBaseStat(dexterity);
            cofd.addBaseStat(stamina);
            cofd.addBaseStat(presence);
            cofd.addBaseStat(manipulation);
            cofd.addBaseStat(composure);
            cofd.addBaseStat(academics);
            cofd.addBaseStat(crafts);
            cofd.addBaseStat(computer);
            cofd.addBaseStat(investigation);
            cofd.addBaseStat(medicine);
            cofd.addBaseStat(occult);
            cofd.addBaseStat(politics);
            cofd.addBaseStat(science);
            cofd.addBaseStat(athletics);
            cofd.addBaseStat(brawl);
            cofd.addBaseStat(drive);
            cofd.addBaseStat(firearms);
            cofd.addBaseStat(larceny);
            cofd.addBaseStat(stealth);
            cofd.addBaseStat(survival);
            cofd.addBaseStat(weaponry);
            cofd.addBaseStat(animalKen);
            cofd.addBaseStat(empathy);
            cofd.addBaseStat(expression);
            cofd.addBaseStat(intimidation);
            cofd.addBaseStat(persuasion);
            cofd.addBaseStat(socialize);
            cofd.addBaseStat(streetwise);
            cofd.addBaseStat(subterfuge);
            cofd.addBaseStat(size);
            cofd.addBaseStat(defense);
            cofd.addBaseStat(health);
            cofd.addBaseStat(initiative);
            cofd.addBaseStat(speed);
            cofd.addBaseStat(willpower);

            RealmHelper.getInstance().save(cofd);
        }
    }

    private static void createManeuvers()
    {
        Maneuver unarmedAttack = new Maneuver(
            realmHelper.getLastId(Maneuver.class),
            UNARMED.getText(),
            realmHelper.get(System.class, Systems.COFD.getText()),
            Targets.ONE.getText());

        unarmedAttack.addKeyword(Maneuvers.ATTACK.getText());
        unarmedAttack.addStatToDicePool(realmHelper.get(Stat.class, STRENGTH.getText()));
        unarmedAttack.addStatToDicePool(realmHelper.get(Stat.class, BRAWL.getText()));
        unarmedAttack.addStatToDefensePool(realmHelper.get(Stat.class, DEFENSE.getText()));
        
        realmHelper.save(unarmedAttack);

        Maneuver meleeAttack = new Maneuver(
            realmHelper.getLastId(Maneuver.class),
            MELEE.getText(),
            realmHelper.get(System.class, Systems.COFD.getText()),
            Targets.ONE.getText());

        meleeAttack.addKeyword(Maneuvers.ATTACK.getText());
        meleeAttack.addStatToDicePool(realmHelper.get(Stat.class, STRENGTH.getText()));
        meleeAttack.addStatToDicePool(realmHelper.get(Stat.class, WEAPONRY.getText()));
        meleeAttack.addStatToDefensePool(realmHelper.get(Stat.class, DEFENSE.getText()));

        realmHelper.save(meleeAttack);

        Maneuver rangedAttack = new Maneuver(
            realmHelper.getLastId(Maneuver.class),
            RANGED.getText(),
            realmHelper.get(System.class, Systems.COFD.getText()),
            Targets.MANY.getText());

        rangedAttack.addKeyword(Maneuvers.ATTACK.getText());
        rangedAttack.addStatToDicePool(realmHelper.get(Stat.class, DEXTERITY.getText()));
        rangedAttack.addStatToDicePool(realmHelper.get(Stat.class, FIREARMS.getText()));

        realmHelper.save(rangedAttack);

        Maneuver thrownAttack = new Maneuver(
            realmHelper.getLastId(Maneuver.class),
            THROWN.getText(),
            realmHelper.get(System.class, Systems.COFD.getText()),
            Targets.ONE.getText());

        thrownAttack.addKeyword(Maneuvers.ATTACK.getText());
        thrownAttack.addStatToDicePool(realmHelper.get(Stat.class, DEXTERITY.getText()));
        thrownAttack.addStatToDicePool(realmHelper.get(Stat.class, ATHLETICS.getText()));
        thrownAttack.addStatToDefensePool(realmHelper.get(Stat.class, DEFENSE.getText()));

        realmHelper.save(thrownAttack);
    }

    public static void createNewCharacter(String name, String templateName)
    {
        Character character = new Character();

        character.setName(name);

        RealmHelper realmHelper = RealmHelper.getInstance();

        character.setId(realmHelper.getLastId(Character.class));

        Template template = realmHelper.get(Template.class, templateName);

        character.setTemplate(template);

        RealmList<Stat> baseStats = template.getSystem().getBaseStats();
        for (Stat baseStat : baseStats)
        {
            Stat stat = new Stat(
                realmHelper.getLastId(Stat.class),
                baseStat.getName()
            );

            for (String keyword : baseStat.getKeywords())
            {
                stat.addKeyword(keyword);
            }

            stat.setValue(baseStat.getValue());

            realmHelper.save(stat);

            character.addStat(stat);
        }

        realmHelper.save(character);

        Stat wits = character.getStatByName(WITS.getText());
        Stat resolve = character.getStatByName(RESOLVE.getText());
        Stat strength  = character.getStatByName(STRENGTH.getText());
        Stat dexterity = character.getStatByName(DEXTERITY.getText());
        Stat stamina = character.getStatByName(STAMINA.getText());
        Stat composure = character.getStatByName(COMPOSURE.getText());
        Stat defense = character.getStatByName(DEFENSE.getText());
        Stat health = character.getStatByName(DEFENSE.getText());
        Stat initiative = character.getStatByName(INITIATIVE.getText());
        Stat size = character.getStatByName(SIZE.getText());
        Stat speed = character.getStatByName(SPEED.getText());
        Stat willpower = character.getStatByName(WILLPOWER.getText());

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

        realmHelper.save(wits);
        realmHelper.save(resolve);
        realmHelper.save(strength);
        realmHelper.save(dexterity);
        realmHelper.save(stamina);
        realmHelper.save(composure);
        realmHelper.save(defense);
        realmHelper.save(health);
        realmHelper.save(initiative);
        realmHelper.save(speed);
        realmHelper.save(willpower);

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
