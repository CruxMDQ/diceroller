package com.callisto.diceroller.persistence.modules;

import com.callisto.diceroller.persistence.objects.Character;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.persistence.objects.Template;
import com.callisto.diceroller.persistence.objects.System;

import io.realm.annotations.RealmModule;

@RealmModule(classes = { Character.class, Stat.class, System.class, Template.class})
public class StorytellerModule { }
