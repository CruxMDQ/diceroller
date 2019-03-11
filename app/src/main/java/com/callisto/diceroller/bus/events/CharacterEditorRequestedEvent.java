package com.callisto.diceroller.bus.events;

public class CharacterEditorRequestedEvent
{
    private final String characterName;
    private final String font;

    public CharacterEditorRequestedEvent(String characterName, String font)
    {
        this.characterName = characterName;
        this.font = font;
    }

    public String getCharacterName()
    {
        return characterName;
    }

    public String getFont()
    {
        return font;
    }
}
