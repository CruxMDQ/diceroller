package com.callisto.diceroller.bus.events;

public class CharacterEditorRequestedEvent
{
    private final String characterName;

    public CharacterEditorRequestedEvent(String characterName)
    {
        this.characterName = characterName;
    }

    public String getCharacterName()
    {
        return characterName;
    }
}
