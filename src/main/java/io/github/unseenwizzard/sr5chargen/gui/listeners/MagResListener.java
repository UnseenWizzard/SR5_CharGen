package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MagicResonancePriority;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MagResListener implements ActionListener {
    private MainFrame mainFrame;
    MagicResonancePriority[] magResVals;

    public MagResListener(MainFrame mainFrame, MagicResonancePriority[] magResVals) {
        this.mainFrame = mainFrame;
        this.magResVals = magResVals;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainFrame.charMagicResonance == null) {
            mainFrame.charMagicResonance = magResVals;
            mainFrame.magResLocked = true;
            if ((JButton) e.getSource() == mainFrame.magResA) {
                mainFrame.ALocked = true;
                mainFrame.chosenMagResPriorityIndex = 0;

                mainFrame.typeA.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
                mainFrame.resA.setEnabled(false);

                mainFrame.magResB.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.magResB) {
                mainFrame.BLocked = true;
                mainFrame.chosenMagResPriorityIndex = 1;

                mainFrame.typeB.setEnabled(false);
                mainFrame.attrB.setEnabled(false);
                mainFrame.skillsB.setEnabled(false);
                mainFrame.resB.setEnabled(false);

                mainFrame.magResA.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.magResC) {
                mainFrame.CLocked = true;
                mainFrame.chosenMagResPriorityIndex = 2;

                mainFrame.typeC.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.resC.setEnabled(false);

                mainFrame.magResB.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.magResD) {
                mainFrame.DLocked = true;
                mainFrame.chosenMagResPriorityIndex = 3;

                mainFrame.typeD.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.resD.setEnabled(false);

                mainFrame.magResB.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.magResE) {
                mainFrame.ELocked = true;
                mainFrame.chosenMagResPriorityIndex = 4;

                mainFrame.typeE.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
                mainFrame.resE.setEnabled(false);

                mainFrame.magResB.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
            }
        } else {
            mainFrame.charMagicResonance = null;
            mainFrame.magResLocked = false;
            mainFrame.chosenMagResPriorityIndex = -1;

            if ((JButton) e.getSource() == mainFrame.magResA) {
                mainFrame.ALocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resA.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.magResE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.magResB) {
                mainFrame.BLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resB.setEnabled(true);

                if (!mainFrame.ALocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.magResE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.magResC) {
                mainFrame.CLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resC.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.magResE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.magResD) {
                mainFrame.DLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resD.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.magResE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.magResE) {
                mainFrame.ELocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeE.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrE.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsE.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resE.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.magResD.setEnabled(true);
            }
        }
    }
}
