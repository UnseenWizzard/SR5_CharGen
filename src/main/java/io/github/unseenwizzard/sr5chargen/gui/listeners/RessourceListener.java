package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RessourceListener implements ActionListener {
    private MainFrame mainFrame;
    int ressources;

    public RessourceListener(MainFrame mainFrame, int ressources) {
        this.mainFrame = mainFrame;
        this.ressources = ressources;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainFrame.charRessources == -1) {
            mainFrame.charRessources = ressources;
            mainFrame.resLocked = true;
            if ((JButton) e.getSource() == mainFrame.resA) {
                mainFrame.ALocked = true;

                mainFrame.typeA.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);

                mainFrame.resB.setEnabled(false);
                mainFrame.resC.setEnabled(false);
                mainFrame.resD.setEnabled(false);
                mainFrame.resE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.resB) {
                mainFrame.BLocked = true;

                mainFrame.typeB.setEnabled(false);
                mainFrame.attrB.setEnabled(false);
                mainFrame.magResB.setEnabled(false);
                mainFrame.skillsB.setEnabled(false);

                mainFrame.resA.setEnabled(false);
                mainFrame.resC.setEnabled(false);
                mainFrame.resD.setEnabled(false);
                mainFrame.resE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.resC) {
                mainFrame.CLocked = true;

                mainFrame.typeC.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);

                mainFrame.resB.setEnabled(false);
                mainFrame.resA.setEnabled(false);
                mainFrame.resD.setEnabled(false);
                mainFrame.resE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.resD) {
                mainFrame.DLocked = true;

                mainFrame.typeD.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);

                mainFrame.resB.setEnabled(false);
                mainFrame.resC.setEnabled(false);
                mainFrame.resA.setEnabled(false);
                mainFrame.resE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.resE) {
                mainFrame.ELocked = true;

                mainFrame.typeE.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);

                mainFrame.resB.setEnabled(false);
                mainFrame.resC.setEnabled(false);
                mainFrame.resD.setEnabled(false);
                mainFrame.resA.setEnabled(false);
            }
        } else {
            mainFrame.charRessources = -1;
            mainFrame.resLocked = false;

            if ((JButton) e.getSource() == mainFrame.resA) {
                mainFrame.ALocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsA.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.resB.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.resC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.resD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.resE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.resB) {
                mainFrame.BLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsB.setEnabled(true);

                if (!mainFrame.ALocked)
                    mainFrame.resA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.resC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.resD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.resE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.resC) {
                mainFrame.CLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsC.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.resB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.resA.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.resD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.resE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.resD) {
                mainFrame.DLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsD.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.resB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.resA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.resC.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.resE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.resE) {
                mainFrame.ELocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeE.setEnabled(true);
                if (!mainFrame.attrLocked)
                    mainFrame.attrE.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResE.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsE.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.resB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.resA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.resC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.resD.setEnabled(true);
            }
        }
    }
}
