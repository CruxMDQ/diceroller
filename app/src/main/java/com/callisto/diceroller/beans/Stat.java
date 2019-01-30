package com.callisto.diceroller.beans;

public class Stat {
    private String name, category, type, kind;

    private int color, value;

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

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getKind()
    {
        return kind;
    }

    public void setKind(String kind)
    {
        this.kind = kind;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }
}
