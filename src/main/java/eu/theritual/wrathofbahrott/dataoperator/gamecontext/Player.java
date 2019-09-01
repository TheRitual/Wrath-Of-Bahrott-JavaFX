package eu.theritual.wrathofbahrott.dataoperator.gamecontext;

import eu.theritual.wrathofbahrott.dataoperator.gameenums.CharacterClass;

import java.util.Objects;
import java.util.UUID;

public class Player {
    final private String name;
    final private CharacterClass characterClass;
    final private UUID uuid;

    public Player(String name, CharacterClass characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name) &&
                characterClass == player.characterClass &&
                uuid.equals(player.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, characterClass, uuid);
    }
}
