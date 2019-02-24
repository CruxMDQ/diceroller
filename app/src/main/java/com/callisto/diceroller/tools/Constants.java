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
        CEZANNE("Cezanne.TTF");

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
        FONT("font"),
        STAT_ID("statId"),
        STAT_NAME("statname");

        private String text;

        Parameters(String text)
        {
            this.text = text;
        }

        public String getText()
        {
            return FONT_DIR + text;
        }
    }

    public enum FragmentTags
    {
        TAG_FRAGMENT_CHAR_LIST("list"),
        TAG_FRAGMENT_CHAR_SHEET("sheet"),
        TAG_FRAGMENT_DIALOG_STAT_EDIT("edit stat");

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

    public enum XmlTags
    {
        TAG_STAT_ID("id"),
        TAG_STAT_SINGLE("stat"),
        TAG_STAT_FIELD_NAME("name"),
        TAG_STAT_FIELD_CATEGORY("category"),
        TAG_STAT_FIELD_TYPE("type"),
        TAG_STAT_FIELD_KIND("kind"),
        TAG_STAT_FIELD_COLOR("color"),
        TAG_STAT_FIELD_VALUE("value"),
        TAG_STAT_FIELD_OBSERVER("observer"),
        TAG_STAT_FIELD_OBSERVES("observes");

        private String text;

        XmlTags(String text)
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
        KINDRED("Kindred");

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
        ICON_WRENCH("Designed by Dave Grandy from www.flaticon.com");

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
