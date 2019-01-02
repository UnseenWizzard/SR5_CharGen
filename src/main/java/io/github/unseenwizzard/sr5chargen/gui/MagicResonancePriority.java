package io.github.unseenwizzard.sr5chargen.gui;

public class MagicResonancePriority {
    public int MagicResonance;
    public int numOfMagicSkills;
    public int valueOfMagicSkills;
    public int numOfSkills;
    public int valueOfSkills;
    public int numOfMagicSkillGroups;
    public int valueOfMagicSkillGroups;
    public int numOfSpellsComplexForms;

    public MagicResonancePriority(int MagicResonance,
                           int numOfMagicSkills, int valueOfMagicSkills, int numOfSkills,
                           int valueOfSkills, int numOfMagicSkillGroups,
                           int valueOfMagicSkillGroups, int numOfSpellsComplexForms) {
        this.MagicResonance = MagicResonance;
        this.numOfMagicSkills = numOfMagicSkills;
        this.valueOfMagicSkills = valueOfMagicSkills;
        this.numOfSkills = numOfSkills;
        this.valueOfSkills = valueOfSkills;
        this.numOfMagicSkillGroups = numOfMagicSkillGroups;
        this.valueOfMagicSkillGroups = valueOfMagicSkillGroups;
        this.numOfSpellsComplexForms = numOfSpellsComplexForms;
    }
}
