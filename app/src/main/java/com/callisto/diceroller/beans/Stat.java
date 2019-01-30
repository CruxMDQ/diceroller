package com.callisto.diceroller.beans;

public class Stat {
    private String name, category, type, kind;

    private int color, value;

    public static Stat newInstance()
    {
        return new Stat();
    }

    public Stat() {}

    public Stat(String name, String category, String type)
    {
        this.name = name;
        this.category = category;
        this.type = type;
    }

    public Stat(String name, String category, String type, String kind, int color)
    {
        this.name = name;
        this.category = category;
        this.type = type;
        this.kind = kind;
        this.color = color;
    }

    public Stat(String name, String category, String type, String kind, int color, int value)
    {
        this.name = name;
        this.category = category;
        this.type = type;
        this.kind = kind;
        this.color = color;
        this.value = value;
    }

    public boolean equals(Object o) {
        boolean result = false;

        if (o instanceof Stat)
        {
            Stat t = (Stat) o;

            result = t.getName().equals(this.getName());
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public String getName()
    {
        return name;
    }

    public Stat setName(String name)
    {
        this.name = name;

        return this;
    }

    public String getCategory()
    {
        return category;
    }

    public Stat setCategory(String category)
    {
        this.category = category;

        return this;
    }

    public String getType()
    {
        return type;
    }

    public Stat setType(String type)
    {
        this.type = type;

        return this;
    }

    public String getKind()
    {
        return kind;
    }

    public Stat setKind(String kind)
    {
        this.kind = kind;

        return this;
    }

    public int getColor()
    {
        return color;
    }

    public Stat setColor(int color)
    {
        this.color = color;

        return this;
    }
}
