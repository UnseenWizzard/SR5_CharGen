package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.data.character.Skill;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SkillKarmaRaise implements ChangeListener {

    private MainFrame mainFrame;
    JSpinner karma = null;
    int skMax = 0;

    public SkillKarmaRaise(MainFrame mainFrame, JSpinner karma, int skMax) {
        this.mainFrame = mainFrame;
        this.karma = karma;
        this.skMax = skMax;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JSpinner sp = (JSpinner) arg0.getSource();
        int raise = (int) sp.getValue();
        Skill skill = null;
        for (Skill s : mainFrame.currentCharacter.getSkills()) {
            if (s.getName().equals(sp.getName())) {
                skill = s;
                if (s.isKnowledge()) {
                    raise /= 2;
                }
                break;
            }
        }
        if (mainFrame.currentCharacter.getPersonalData().getKarma() >= raise * 2) {
            skill.setValue((int) sp.getValue());
            sp.setModel(new SpinnerNumberModel(skill.getValue(), skill
                    .getValue(), skMax, 1));
            mainFrame.currentCharacter.getPersonalData().setKarma(
                    mainFrame.currentCharacter.getPersonalData().getKarma() - raise
                            * 2);
            karma.setValue(mainFrame.currentCharacter.getPersonalData().getKarma());

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
