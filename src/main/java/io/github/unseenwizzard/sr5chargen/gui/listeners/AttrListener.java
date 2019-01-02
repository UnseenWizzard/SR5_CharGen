package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttrListener implements ActionListener {
    private MainFrame mainFrame;
    int attr;

    public AttrListener(MainFrame mainFrame, int attr) {
        this.mainFrame = mainFrame;
        this.attr = attr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainFrame.charAttributes == -1) {
            System.out.println("Clicked, attributes not set");
            mainFrame.charAttributes = attr;
            mainFrame.attrLocked = true;
            if ((JButton) e.getSource() == mainFrame.attrA) {
                mainFrame.ALocked = true;

                mainFrame.typeA.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
                mainFrame.resA.setEnabled(false);

                mainFrame.attrB.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.attrB) {
                mainFrame.BLocked = true;

                mainFrame.typeB.setEnabled(false);
                mainFrame.magResB.setEnabled(false);
                mainFrame.skillsB.setEnabled(false);
                mainFrame.resB.setEnabled(false);

                mainFrame.attrA.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.attrC) {
                mainFrame.CLocked = true;

                mainFrame.typeC.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.resC.setEnabled(false);

                mainFrame.attrB.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.attrD) {
                mainFrame.DLocked = true;

                mainFrame.typeD.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.resD.setEnabled(false);

                mainFrame.attrB.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
                mainFrame.attrE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.attrE) {
                mainFrame.ELocked = true;

                mainFrame.typeE.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
                mainFrame.resE.setEnabled(false);

                mainFrame.attrB.setEnabled(false);
                mainFrame.attrC.setEnabled(false);
                mainFrame.attrD.setEnabled(false);
                mainFrame.attrA.setEnabled(false);
            }
        } else {
            mainFrame.charAttributes = -1;
            mainFrame.attrLocked = false;

            if ((JButton) e.getSource() == mainFrame.attrA) {
                mainFrame.ALocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resA.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.attrE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.attrB) {
                mainFrame.BLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resB.setEnabled(true);

                if (!mainFrame.ALocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.attrE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.attrC) {
                mainFrame.CLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resC.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.attrE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.attrD) {
                mainFrame.DLocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resD.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.attrE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.attrE) {
                mainFrame.ELocked = false;

                if (!mainFrame.typeLocked)
                    mainFrame.typeE.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResE.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsE.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resE.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.attrD.setEnabled(true);
            }
        }
    }
}
