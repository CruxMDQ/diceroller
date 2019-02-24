package com.callisto.diceroller.persistence.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Template extends RealmObject
{
    @PrimaryKey private long id;
    private String name;
    private String font;
    private System system;

    public Template()
    {
    }

    public Template(long id, String name, String font, System system)
    {
        this.id = id;
        this.name = name;
        this.font = font;
        this.system = system;
    }
}
