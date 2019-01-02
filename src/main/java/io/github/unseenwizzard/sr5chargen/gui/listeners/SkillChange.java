package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.data.character.Skill;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SkillChange implements ChangeListener {

    private MainFrame mainFrame;

    public SkillChange(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JSpinner top = (JSpinner) arg0.getSource();
        int index = 0;
        for (Skill s : mainFrame.LR.getSkillList()) {
            if (s.getName().equals(top.getName())) {
                index = mainFrame.LR.getSkillList().indexOf(s);
            }
        }
        if (mainFrame.charSkills > 0 || (int) top.getValue() < mainFrame.prevValues[index]) {
            mainFrame.charSkills += mainFrame.prevValues[index] - (int) top.getValue();
            mainFrame.prevValues[index] = (int) top.getValue();
            mainFrame.atr.setValue(mainFrame.charSkills);
        } else {
            JOptionPane.showMessageDialog(
                    ((JSpinner) arg0.getSource()).getParent(),
                    "You have no more skill points left!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            top.removeChangeListener(this);
            top.setValue(top.getPreviousValue());
            top.addChangeListener(this);
            top.revalidate();
            top.repaint();
        }
    }
}
