package com.callisto.diceroller.tools;

public class Constants {
    private Constants() {
        throw new AssertionError("This class is NOT MEANT FOR INSTANTIATION!");
    }

    public static String TAG_TYPE_MENTAL = "Mental";
    public static String TAG_TYPE_PHYSICAL = "Physical";
    public static String TAG_TYPE_SOCIAL = "Social";

    public enum Systems {
        FIFTH_EDITION("5E"),
        OWOD("Old World of Darkness"),
        COFD("Chronicles of Darkness");

        private String text;

        Systems(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum Values {
        COFD_DIE_SIZE(10),
        COFD_BASE_DIFFICULTY_THRESHOLD(8),
        COFD_BASE_ROLL_AGAIN_THRESHOLD(10);

        private int value;

        Values(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Tags {
        TAG_TYPE_MENTAL("Mental"),
        TAG_TYPE_PHYSICAL("Physical"),
        TAG_TYPE_SOCIAL("Social");

        private String text;

        Tags(String text) {
            this.text = text;
        }

        public String getText()
        {
            return text;
        }
    }

    public enum XmlTags {
        TAG_STAT_SINGLE("stat"),
        TAG_STAT_FIELD_NAME("name"),
        TAG_STAT_FIELD_CATEGORY("category"),
        TAG_STAT_FIELD_TYPE("type"),
        TAG_STAT_FIELD_KIND("kind"),
        TAG_STAT_FIELD_COLOR("color");

        private String text;

        XmlTags(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
