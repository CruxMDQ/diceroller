package com.callisto.diceroller.persistence.objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Maneuver
    extends RealmObject
{
    @PrimaryKey
    private long id;
    private String name;
    private System system;
    private String targets;
    private RealmList<String> keywords = new RealmList<>();
    private RealmList<Stat> requirements = new RealmList<>();
    private RealmList<Stat> dicePool = new RealmList<>();
    private RealmList<Stat> defensePool = new RealmList<>();

    public Maneuver() {}

    public Maneuver(long id, String name, System system, String targets)
    {
        this.id = id;
        this.name = name;
        this.system = system;
        this.targets = targets;
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

    public RealmList<Stat> getRequirements()
    {
        return requirements;
    }

    public void setRequirements(RealmList<Stat> requirements)
    {
        this.requirements = requirements;
    }

    public Maneuver addRequirement(Stat requirement)
    {
        this.requirements.add(requirement);

        return this;
    }

    public RealmList<Stat> getDicePool()
    {
        return dicePool;
    }

    public void setDicePool(RealmList<Stat> dicePool)
    {
        this.dicePool = dicePool;
    }

    public Maneuver addStatToDicePool(Stat stat)
    {
        this.dicePool.add(stat);

        return this;
    }

    public void setDefensePool(RealmList<Stat> defensePool)
    {
        this.defensePool = defensePool;
    }

    public RealmList<Stat> getDefensePool()
    {
        return defensePool;
    }

    public Maneuver addStatToDefensePool(Stat stat)
    {
        defensePool.add(stat);

        return this;
    }

    public System getSystem()
    {
        return system;
    }

    public void setSystem(System system)
    {
        this.system = system;
    }

    public String getTargets()
    {
        return targets;
    }

    public void setTargets(String targets)
    {
        this.targets = targets;
    }

    public RealmList<String> getKeywords()
    {
        return keywords;
    }

    public Maneuver addKeyword(String keyword)
    {
        keywords.add(keyword);

        return this;
    }
}
