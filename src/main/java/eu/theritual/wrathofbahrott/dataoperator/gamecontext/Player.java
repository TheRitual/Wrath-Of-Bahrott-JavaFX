package eu.theritual.wrathofbahrott.dataoperator.gamecontext;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.CharacterClass;

public class Player {
    final private String name;
    final private CharacterClass characterClass;

    public Player(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }
}
