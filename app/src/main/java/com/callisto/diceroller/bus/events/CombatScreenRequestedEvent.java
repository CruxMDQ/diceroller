package com.callisto.diceroller.bus.events;

import com.callisto.diceroller.persistence.objects.Character;

import java.util.List;

public class CombatScreenRequestedEvent
{
    private final List<Character> selectedCharacters;

    public CombatScreenRequestedEvent(List<Character> selectedCharacters)
    {
        this.selectedCharacters = selectedCharacters;
    }

    public List<Character> getSelectedCharacters()
    {
        return selectedCharacters;
    }
}
