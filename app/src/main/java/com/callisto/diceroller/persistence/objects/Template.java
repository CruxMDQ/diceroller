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

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFont()
    {
        return font;
    }

    public void setFont(String font)
    {
        this.font = font;
    }

    public System getSystem()
    {
        return system;
    }

    public void setSystem(System system)
    {
        this.system = system;
    }
}
