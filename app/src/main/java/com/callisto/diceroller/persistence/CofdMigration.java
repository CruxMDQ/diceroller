package com.callisto.diceroller.persistence;

import android.support.annotation.NonNull;

import com.callisto.diceroller.tools.Constants.Classes;
import com.callisto.diceroller.tools.Constants.Fields;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

import static com.callisto.diceroller.tools.Constants.Classes.CHARACTER;
import static com.callisto.diceroller.tools.Constants.Classes.MANEUVER;
import static com.callisto.diceroller.tools.Constants.Classes.STAT;
import static com.callisto.diceroller.tools.Constants.Classes.SYSTEM;
import static com.callisto.diceroller.tools.Constants.Classes.TEMPLATE;
import static com.callisto.diceroller.tools.Constants.Fields.ADVANTAGES;
import static com.callisto.diceroller.tools.Constants.Fields.BASESTATS;
import static com.callisto.diceroller.tools.Constants.Fields.DEFENSEPOOL;
import static com.callisto.diceroller.tools.Constants.Fields.DICEPOOL;
import static com.callisto.diceroller.tools.Constants.Fields.FONT;
import static com.callisto.diceroller.tools.Constants.Fields.ID;
import static com.callisto.diceroller.tools.Constants.Fields.KEYWORDS;
import static com.callisto.diceroller.tools.Constants.Fields.NAME;
import static com.callisto.diceroller.tools.Constants.Fields.REQUIREMENTS;
import static com.callisto.diceroller.tools.Constants.Fields.RESOURCES;
import static com.callisto.diceroller.tools.Constants.Fields.TARGETS;
import static com.callisto.diceroller.tools.Constants.Fields.TEMPORARYVALUE;
import static com.callisto.diceroller.tools.Constants.Fields.TRAITS;

public class CofdMigration implements RealmMigration
{
    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion)
    {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0)
        {
            schema.create(SYSTEM.getText())
                .addField(ID.getText(), long.class, FieldAttribute.PRIMARY_KEY)
                .addField(NAME.getText(), String.class);

            schema.create(TEMPLATE.getText())
                .addField(ID.getText(), long.class, FieldAttribute.PRIMARY_KEY)
                .addField(NAME.getText(), String.class)
                .addRealmObjectField(Fields.SYSTEM.getText(), schema.get(SYSTEM.getText()));

            oldVersion++;
        }

        if (oldVersion == 1)
        {
            schema.get(TEMPLATE.getText())
                .addField(FONT.getText(), String.class);

            oldVersion++;
        }

        if (oldVersion == 2)
        {
            schema.get(CHARACTER.getText())
                .removeField(Fields.TEMPLATE.getText())
                .addRealmObjectField(Fields.TEMPLATE.getText(), schema.get(Classes.TEMPLATE.getText()));

            oldVersion++;
        }

        if (oldVersion == 3)
        {
            schema.get(Classes.TEMPLATE.getText())
                .addRealmListField(ADVANTAGES.getText(), schema.get(STAT.getText()))
                .addRealmListField(RESOURCES.getText(), schema.get(STAT.getText()));

            schema.get(STAT.getText())
                .removeField("watched")
                .removeField("watchers");

            oldVersion++;
        }

        if (oldVersion == 4)
        {
            schema.get(STAT.getText())
                .addField(TEMPORARYVALUE.getText(), Integer.class)
                .addRealmListField(KEYWORDS.getText(), String.class);

            oldVersion++;
        }

        if (oldVersion == 5)
        {
            schema.get(STAT.getText())
                .removeField("type")
                .removeField("kind");
        }

        if (oldVersion == 6)
        {
            schema.get(TEMPLATE.getText())
                .addRealmListField(TRAITS.getText(), schema.get(STAT.getText()));
        }

        if (oldVersion == 7)
        {
            schema.get(TEMPLATE.getText())
                .removeField(ADVANTAGES.getText())
                .removeField(RESOURCES.getText());
        }

        if (oldVersion == 8)
        {
            schema.create(MANEUVER.getText())
                .addField(ID.getText(), long.class, FieldAttribute.PRIMARY_KEY)
                .addField(NAME.getText(), String.class)
                .addField(TARGETS.getText(), String.class)
                .addRealmObjectField(Fields.SYSTEM.getText(), schema.get(SYSTEM.getText()))
                .addRealmListField(KEYWORDS.getText(), String.class)
                .addRealmListField(REQUIREMENTS.getText(), schema.get(STAT.getText()))
                .addRealmListField(DICEPOOL.getText(), schema.get(STAT.getText()))
                .addRealmListField(DEFENSEPOOL.getText(), schema.get(STAT.getText()));
        }

        if (oldVersion == 9)
        {
            schema.get(SYSTEM.getText())
                .addRealmListField(BASESTATS.getText(), schema.get(STAT.getText()));
        }
    }
}
