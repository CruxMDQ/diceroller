package com.callisto.diceroller.persistence;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class CofdMigration implements RealmMigration
{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion)
    {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0)
        {
            schema.create("System")
                .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                .addField("name", String.class);

            schema.create("Template")
                .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                .addField("name", String.class)
                .addRealmObjectField("system", schema.get("System"));

            oldVersion++;
        }

        if (oldVersion == 1)
        {
            schema.get("Template")
                .addField("font", String.class);

            oldVersion++;
        }

        if (oldVersion == 2)
        {
            schema.get("Character")
                .removeField("template")
                .addRealmObjectField("template", schema.get("Template"));

            oldVersion++;
        }
    }
}
