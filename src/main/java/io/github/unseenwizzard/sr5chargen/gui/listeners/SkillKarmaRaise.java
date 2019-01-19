package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.data.character.Skill;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SkillKarmaRaise implements ChangeListener {

    JSpinner karma = null;
    int skMax = 0;
    private final CharacterController characterController;

    public SkillKarmaRaise(JSpinner karma, int skMax, CharacterController characterController) {
        this.karma = karma;
        this.skMax = skMax;
        this.characterController = characterController;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JSpinner sp = (JSpinner) arg0.getSource();
        int raise = (int) sp.getValue();
        Skill skill = null;
        for (Skill s : characterController.getCharacter().getSkills()) {
            if (s.getName().equals(sp.getName())) {
                skill = s;
                if (s.isKnowledge()) {
                    raise /= 2;
                }
                break;
            }
        }
        if ( characterController.getCharacter().getPersonalData().getKarma() >= raise * 2) {
            skill.setValue((int) sp.getValue());
            sp.setModel(new SpinnerNumberModel(skill.getValue(), skill
                    .getValue(), skMax, 1));
             characterController.getCharacter().getPersonalData().setKarma(
                     characterController.getCharacter().getPersonalData().getKarma() - raise
                            * 2);
            karma.setValue( characterController.getCharacter().getPersonalData().getKarma());

        } else {
            sp.removeChangeListener(this);
            sp.setValue(skill.getValue());
            sp.addChangeListener(this);
            JOptionPane.showMessageDialog(sp.getParent(),
                    "Not enough Karma to raise Skill!");
        }

        sp.revalidate();
        sp.repaint();
        karma.revalidate();
        karma.repaint();
    }

}
