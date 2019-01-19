package io.github.unseenwizzard.sr5chargen.control;

import io.github.unseenwizzard.sr5chargen.data.character.Character;
import io.github.unseenwizzard.sr5chargen.data.character.Metatype;
import io.github.unseenwizzard.sr5chargen.data.character.Sex;

public class CharacterController {

    private Character character;
    public boolean saveAllowed;

    public Character createNewCharacter(String name, Metatype metatype, Sex sex) {
        character = new Character(metatype, name, sex);
        return character;
    }

    public Character getCharacter() {
        return character;
    }

    @Deprecated
    public void setCharacter(Character character) {
        this.character = character;
    }

}
