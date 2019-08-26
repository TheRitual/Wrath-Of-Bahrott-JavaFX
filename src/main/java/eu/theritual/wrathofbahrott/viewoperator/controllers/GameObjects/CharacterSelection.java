package eu.theritual.wrathofbahrott.viewoperator.controllers.GameObjects;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.CharacterClass;

public class CharacterSelection {
    private CharacterClass characterClass;
    private String name;
    private int number;

    public CharacterSelection(int number) {
        this.number = number;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
