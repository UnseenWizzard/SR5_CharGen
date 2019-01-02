package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.data.character.SkillGroup;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SkillGroupChange implements ChangeListener {

    private MainFrame mainFrame;

    public SkillGroupChange(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        JSpinner top = (JSpinner) arg0.getSource();
        int index = 0;
        for (SkillGroup sg : mainFrame.LR.getSkillGroupList()) {
            if (sg.getName().equals(top.getName())) {
                index = mainFrame.LR.getSkillGroupList().indexOf(sg);
            }
        }
        if (mainFrame.charSkillGroups > 0 || (int) top.getValue() < mainFrame.prevValues[index]) {
            mainFrame.charSkillGroups += mainFrame.prevValues[index] - (int) top.getValue();
            mainFrame.prevValues[index] = (int) top.getValue();
            mainFrame.atr.setValue(mainFrame.charSkillGroups);
            for (java.awt.Component sp : top.getParent().getComponents()) {
                if (sp.getName() != null
                        && sp.getName().equals(top.getName() + "Skill")) {
                    ((JSpinner) sp).setValue(top.getValue());
                    sp.revalidate();
                    sp.repaint();
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    ((JSpinner) arg0.getSource()).getParent(),
                    "You have no more skillgroup points left!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            top.removeChangeListener(this);
            top.setValue(top.getPreviousValue());
            top.addChangeListener(this);
            top.revalidate();
            top.repaint();
        }
    }
}
