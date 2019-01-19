package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.data.character.SkillGroup;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SkillGroupKarmaRaise implements ChangeListener {

    JSpinner karma = null;
    int skMax = 0;
    private final CharacterController characterController;

    public SkillGroupKarmaRaise(JSpinner karma, int skMax, CharacterController characterController) {
        this.karma = karma;
        this.skMax = skMax;
        this.characterController = characterController;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JSpinner top = (JSpinner) arg0.getSource();
        SkillGroup sg = null;
        for (SkillGroup s :  characterController.getCharacter().getSkillGroups()) {
            if (s.getName().equals(top.getName())) {
                sg = s;
            }
        }
        if ( characterController.getCharacter().getPersonalData().getKarma() >= (int) top
                .getValue() * 5) {
            sg.setValue((int) top.getValue());
            top.setModel(new SpinnerNumberModel(sg.getValue(), sg
                    .getValue(), skMax, 1));
             characterController.getCharacter().getPersonalData().setKarma(
                     characterController.getCharacter().getPersonalData().getKarma()
                            - (int) top.getValue() * 5);
            karma.setValue( characterController.getCharacter().getPersonalData().getKarma());
            for (java.awt.Component sp : top.getParent().getComponents()) {
                if (sp.getName() != null
                        && sp.getName().equals(top.getName() + "Skill")) {
                    ((JSpinner) sp).setValue(top.getValue());
                    sp.revalidate();
                    sp.repaint();
                }
            }
        } else {
            JOptionPane.showMessageDialog(top.getParent(),
                    "Not enough Karma to raise Skill!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            top.removeChangeListener(this);
            top.setValue(sg.getValue());
            top.addChangeListener(this);
        }
        top.revalidate();
        top.repaint();
        karma.revalidate();
        karma.repaint();
    }

}
