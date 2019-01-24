package com.callisto.diceroller.tools;

public class Constants {
    private Constants() {
        throw new AssertionError("This class is NOT MEANT FOR INSTANTIATION!");
    }

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
        TAG_STAT_INT("Intelligence"),
        TAG_STAT_WIT("Wits"),
        TAG_STAT_RES("Resolve"),
        TAG_STAT_STR("Strength"),
        TAG_STAT_DEX("Dexterity"),
        TAG_STAT_STA("Stamina"),
        TAG_STAT_PRE("Presence"),
        TAG_STAT_MAN("Manipulation"),
        TAG_STAT_COM("Composure");

        private String text;

        Tags(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
