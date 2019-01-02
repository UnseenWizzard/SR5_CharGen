package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkillListener implements ActionListener {
    private MainFrame mainFrame;
    int skills, skillGroups;

    public SkillListener(MainFrame mainFrame, int skill, int skillGroup) {
        this.mainFrame = mainFrame;
        this.skills = skill;
        this.skillGroups = skillGroup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainFrame.charSkills == -1 && mainFrame.charSkillGroups == -1) {
            mainFrame.charSkills = skills;
            mainFrame.charSkillGroups = skillGroups;
            mainFrame.skillsLocked = true;
            if ((JButton) e.getSource() == mainFrame.skillsA) {
                mainFrame.ALocked = true;

                mainFrame.typeA.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.resA.setEnabled(false);

                mainFrame.skillsB.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.skillsB) {
                mainFrame.BLocked = true;

                mainFrame.typeB.setEnabled(false);
                mainFrame.attrB.setEnabled(false);
                mainFrame.magResB.setEnabled(false);
                mainFrame.resB.setEnabled(false);

                mainFrame.skillsA.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.skillsC) {
                mainFrame.CLocked = true;

                mainFrame.typeC.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.resC.setEnabled(false);

                mainFrame.skillsB.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.skillsD) {
                mainFrame.DLocked = true;

                mainFrame.typeD.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.resD.setEnabled(false);

                mainFrame.skillsB.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.skillsE) {
                mainFrame.ELocked = true;

                mainFrame.typeE.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
                mainFrame.resE.setEnabled(false);

                mainFrame.skillsB.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
            }
        } else {
            mainFrame.charSkills = -1;
            mainFrame.charSkillGroups = -1;
            mainFrame.skillsLocked = false;

            if ((JButton) e.getSource() == mainFrame.skillsA) {
                mainFrame.ALocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resA.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.skillsE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.skillsB) {
                mainFrame.BLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resB.setEnabled(true);

                if (!mainFrame.ALocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.skillsE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.skillsC) {
                mainFrame.CLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resC.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.skillsE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.skillsD) {
                mainFrame.DLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resD.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.skillsE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.skillsE) {
                mainFrame.ELocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeE.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrE.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResE.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resE.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.skillsD.setEnabled(true);
            }
        }
    }
}
