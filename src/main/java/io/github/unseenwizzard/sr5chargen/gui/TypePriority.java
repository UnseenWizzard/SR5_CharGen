package io.github.unseenwizzard.sr5chargen.gui;

import io.github.unseenwizzard.sr5chargen.data.character.Metatype;

public class TypePriority {
    public Metatype[] type;
    public int[] specialAttributes;

    TypePriority(Metatype[] type, int[] specialAttributes) {
        this.type = type;
        this.specialAttributes = specialAttributes;
    }
}
