package com.callisto.diceroller.tools;

public class Constants
{
    private Constants()
    {
        throw new AssertionError("This class is NOT MEANT FOR INSTANTIATION!");
    }

    private static String FONT_DIR = "fonts/";

    public enum Systems
    {
        FIFTH_EDITION("5E"),
        OWOD("Old World of Darkness"),
        COFD("Chronicles of Darkness");

        private String text;

        Systems(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Values
    {
        COFD_SPEED_BASE(5),
        COFD_DIE_SIZE(10),
        COFD_BASE_DIFFICULTY_THRESHOLD(8),
        COFD_BASE_ROLL_AGAIN_THRESHOLD(10),
        STAT_CONTAINER_FONT_TITLE(30);

        private int value;

        Values(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public enum Fonts
    {
        CEZANNE("Cezanne.TTF"),
        ITALIANNO("Italianno.otf"),
        PERCOLATOR("PERCEXP.TTF");

        private String text;

        Fonts(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return FONT_DIR + text;
        }
    }

    public enum Parameters
    {
        ID("id"),
        CHARACTER_NAME("charname"),
        CHECK_TYPE_SKILL("Skill"),
        CHECK_TYPE_COMBAT("Combat"),
        CHECK_SUBTYPE_SKILL_CONTESTED("Contested"),
        CHECK_SUBTYPE_SKILL_RESISTED("Resisted"),
        CHECK_SUBTYPE_COMBAT_UNARMED("Unarmed"),
        CHECK_SUBTYPE_COMBAT_MELEE("Melee"),
        CHECK_SUBTYPE_COMBAT_RANGED("Ranged"),
        CHECK_SUBTYPE_COMBAT_THROWN("Thrown"),
        FONT("font"),
        STAT_ID("statId"),
        STAT_NAME("statname"),
        SELECTED_CHARACTERS("selected characters");

        private String text;

        Parameters(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum FragmentTags
    {
        TAG_FRAGMENT_CHAR_LIST("list"),
        TAG_FRAGMENT_CHAR_SHEET("sheet"),
        TAG_FRAGMENT_DIALOG_STAT_EDIT("edit stat"),
        TAG_FRAGMENT_OPPOSED_CHECK("opposed check"),
        TAG_FRAGMENT_COMBAT("combat"),
        TAG_FRAGMENT_OPPOSED_CHECK_PERFORMER("performer"),
        TAG_FRAGMENT_OPPOSED_CHECK_OPPONENT("opponent");
        private String text;

        FragmentTags(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Classes
    {
        CHARACTER("Character"),
        STAT("Stat"),
        SYSTEM("System"),
        TEMPLATE("Template"),
        MANEUVER("Maneuver");

        private String text;

        Classes(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Keywords
    {
        ATTRIBUTE("Attribute"),
        SKILL("Skill"),
        DERIVED("Derived"),
        MENTAL("Mental"),
        PHYSICAL("Physical"),
        SOCIAL("Social"),
        POWER("Maneuver"),
        FINESSE("Finesse"),
        RESISTANCE("Resistance"),
        MORALITY("Morality"),
        ADVANTAGE("Advantage"),
        RESOURCE("Resource");

        private String text;

        Keywords(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Feats
    {
        UNARMED("Unarmed"),
        MELEE("Melee"),
        RANGED("Ranged"),
        THROWN("Thrown"),
        OFFENSE("Offense"),
        DEFENSE("Defense"),
        PERCEPTION("Perception");

        private String text;

        Feats(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Moralities
    {
        HUMANITY("Humanity"),
        BELIEF("Belief");

        private String text;

        Moralities(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Resources
    {
        VITAE("Vitae"),
        WISPS("Wisps");

        private String text;

        Resources(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Advantages
    {
        BLOODPOTENCY("Blood Potency"),
        INNERLIGHT("Inner Light");

        private String text;

        Advantages(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }
    
    public enum Derived
    {
        DEFENSE("Defense"),
        HEALTH("Health"),
        INITIATIVE("Initiative"),
        SIZE("Size"),
        SPEED("Speed"),
        WILLPOWER("Willpower");

        private String text;

        Derived(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }

    }

    public enum Maneuvers
    {
        ATTACK("Attack"),
        ATTACK_UNARMED("Unarmed"),
        ATTACK_MELEE("Melee"),
        ATTACK_RANGED("Ranged"),
        ATTACK_THROWN("Thrown");

        private String text;

        public String getText()
        {
            return text;
        }

        Maneuvers(String text)
        {
            this.text = text;
        }
    }

    public enum Targets
    {
        SELF("Self"),
        NONE("None"),
        ONE("One"),
        MANY("Many");

        private String text;

        Targets(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Fields
    {
        ID("id"),
        ADVANTAGES("advantages"),
        FONT("font"),
        KEYWORDS("keywords"),
        NAME("name"),
        TEMPLATE("template"),
        RESOURCES("resources"),
        SYSTEM("system"),
        TRAITS("traits"),
        TEMPORARYVALUE("temporaryValue"),
        REQUIREMENTS("requirements"),
        DICEPOOL("dicePool"),
        DEFENSEPOOL("defensePool"),
        TARGETS("targets"),
        BASESTATS("baseStats");

        private String text;

        Fields(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Attributes
    {
        INTELLIGENCE("Intelligence"),
        WITS("Wits"),
        RESOLVE("Resolve"),
        STRENGTH("Strength"),
        DEXTERITY("Dexterity"),
        STAMINA("Stamina"),
        PRESENCE("Presence"),
        MANIPULATION("Manipulation"),
        COMPOSURE("Composure");

        private String text;

        Attributes(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Skills
    {
        // Mental
        ACADEMICS("Academics"),
        CRAFTS("Crafts"),
        COMPUTER("Computer"),
        INVESTIGATION("Investigation"),
        MEDICINE("Medicine"),
        OCCULT("Occult"),
        POLITICS("Politics"),
        SCIENCE("Science"),
        // Physical
        ATHLETICS("Athletics"),
        BRAWL("Brawl"),
        DRIVE("Drive"),
        FIREARMS("Firearms"),
        LARCENY("Larceny"),
        STEALTH("Stealth"),
        SURVIVAL("Survival"),
        WEAPONRY("Weaponry"),
        // Social
        ANIMALKEN("Animal Ken"),
        EMPATHY("Empathy"),
        EXPRESSION("Expression"),
        INTIMIDATION("Intimidation"),
        PERSUASION("Persuasion"),
        SOCIALIZE("Socialize"),
        STREETWISE("Streetwise"),
        SUBTERFUGE("Subterfuge");

        private String text;

        Skills(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Templates
    {
        KINDRED("Kindred"),
        NOBLE("Noble");

        private String text;

        Templates(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum Credits
    {
        ICON_FIGHT("Designed by Freepik from www.flaticon.com"),
        ICON_FEATHER_PEN("Designed by EpicCoders from www.flaticon.com"),
        ICON_D10("Designed by Skoll from www.game-icons.net"),
        ICON_D20("Designed by Skoll from www.game-icons.net"),
        ICON_WRENCH("Designed by Dave Grandy from www.flaticon.com"),
        ICON_SWORDS_CROSSED("Designed by Freepik from www.flaticon.com");

        private String text;

        Credits(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }

    }
}
