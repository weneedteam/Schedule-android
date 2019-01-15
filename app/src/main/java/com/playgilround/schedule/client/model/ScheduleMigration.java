package com.playgilround.schedule.client.model;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class ScheduleMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        // version 0
        if (oldVersion == 0) {
            schema.create("Event")
                    .addField("id", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("name", String.class)
                    .addField("color", int.class)
                    .addField("icon", int.class)
                    .addField("isChecked", boolean.class);
            oldVersion++;
        }
        if (oldVersion == 1) {
            schema.create("Schedule")
                    .addField("id", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("title", String.class, FieldAttribute.REQUIRED)
                    .addField("location", String.class)
                    .addField("latitude", double.class)
                    .addField("longitude", double.class)
                    .addField("time", long.class);
            oldVersion++;
        }
        if (oldVersion == 2) {
            schema.create("Schedule")
                    .addField("id", int.class, FieldAttribute.PRIMARY_KEY)
                    .addField("date", String.class, FieldAttribute.REQUIRED)
                    .addField("title", String.class, FieldAttribute.REQUIRED)
                    .addField("location", String.class)
                    .addField("latitude", double.class)
                    .addField("longitude", double.class)
                    .addField("time", long.class)
                    .addField("desc", String.class);
        }
        if (oldVersion == 3) {
            schema.create("Schedule")
                    .addField("dateDay", String.class, FieldAttribute.REQUIRED);
        }
    }
}
