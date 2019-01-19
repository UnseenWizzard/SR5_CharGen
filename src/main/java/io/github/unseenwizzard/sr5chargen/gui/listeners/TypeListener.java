package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;
import io.github.unseenwizzard.sr5chargen.gui.TypePriority;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypeListener implements ActionListener {
    private MainFrame mainFrame;
    TypePriority tP;
    private final CharacterController characterController;

    public TypeListener(MainFrame mainFrame, TypePriority tP,CharacterController characterController) {
        this.mainFrame = mainFrame;
        this.tP = tP;
        this.characterController = characterController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mainFrame.charSpecialAttributes == -1) {
            for (int i = 0; i < tP.type.length; i++) {
                if (tP.type[i] == characterController.getCharacter().getPersonalData()
                        .getMetatype()) {
                    mainFrame.charSpecialAttributes = tP.specialAttributes[i];
                    mainFrame.initValueCharSpecialAttributes = mainFrame.charSpecialAttributes;
                }
            }
            if (mainFrame.charSpecialAttributes == -1) {
                JOptionPane
                        .showMessageDialog(
                                ((JButton) e.getSource()).getParent(),
                                "Your choice is not fitting to your chosen metatype!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainFrame.typeLocked = true;
            if ((JButton) e.getSource() == mainFrame.typeA) {
                mainFrame.ALocked = true;

                mainFrame.attrA.setEnabled(false);
                mainFrame.magResA.setEnabled(false);
                mainFrame.skillsA.setEnabled(false);
                mainFrame.resA.setEnabled(false);

                mainFrame.typeB.setEnabled(false);
                mainFrame.typeC.setEnabled(false);
                mainFrame.typeD.setEnabled(false);
                mainFrame.typeE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.typeB) {
                mainFrame.BLocked = true;

                mainFrame.attrB.setEnabled(false);
                mainFrame.magResB.setEnabled(false);
                mainFrame.skillsB.setEnabled(false);
                mainFrame.resB.setEnabled(false);

                mainFrame.typeA.setEnabled(false);
                mainFrame.typeC.setEnabled(false);
                mainFrame.typeD.setEnabled(false);
                mainFrame.typeE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.typeC) {
                mainFrame.CLocked = true;

                mainFrame.attrC.setEnabled(false);
                mainFrame.magResC.setEnabled(false);
                mainFrame.skillsC.setEnabled(false);
                mainFrame.resC.setEnabled(false);

                mainFrame.typeB.setEnabled(false);
                mainFrame.typeA.setEnabled(false);
                mainFrame.typeD.setEnabled(false);
                mainFrame.typeE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.typeD) {
                mainFrame.DLocked = true;

                mainFrame.attrD.setEnabled(false);
                mainFrame.magResD.setEnabled(false);
                mainFrame.skillsD.setEnabled(false);
                mainFrame.resD.setEnabled(false);

                mainFrame.typeB.setEnabled(false);
                mainFrame.typeC.setEnabled(false);
                mainFrame.typeA.setEnabled(false);
                mainFrame.typeE.setEnabled(false);
            } else if ((JButton) e.getSource() == mainFrame.typeE) {
                mainFrame.ELocked = true;

                mainFrame.attrE.setEnabled(false);
                mainFrame.magResE.setEnabled(false);
                mainFrame.skillsE.setEnabled(false);
                mainFrame.resE.setEnabled(false);

                mainFrame.typeB.setEnabled(false);
                mainFrame.typeC.setEnabled(false);
                mainFrame.typeD.setEnabled(false);
                mainFrame.typeA.setEnabled(false);
            }
        } else {
            mainFrame.charSpecialAttributes = -1;
            mainFrame.initValueCharSpecialAttributes = -1;
            mainFrame.typeLocked = false;

            if ((JButton) e.getSource() == mainFrame.typeA) {
                mainFrame.ALocked = false;

                if (!mainFrame.attrLocked)
                    mainFrame.attrA.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResA.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsA.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resA.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.typeE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.typeB) {
                mainFrame.BLocked = false;

                if (!mainFrame.attrLocked)
                    mainFrame.attrB.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResB.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsB.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resB.setEnabled(true);

                if (!mainFrame.ALocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.typeE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.typeC) {
                mainFrame.CLocked = false;

                if (!mainFrame.attrLocked)
                    mainFrame.attrC.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResC.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsC.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resC.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.typeD.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.typeE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.typeD) {
                mainFrame.DLocked = false;

                if (!mainFrame.attrLocked)
                    mainFrame.attrD.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResD.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsD.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resD.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.ELocked)
                    mainFrame.typeE.setEnabled(true);
            } else if ((JButton) e.getSource() == mainFrame.typeE) {
                mainFrame.ELocked = false;

                if (!mainFrame.attrLocked)
                    mainFrame.attrE.setEnabled(true);
                if (!mainFrame.magResLocked)
                    mainFrame.magResE.setEnabled(true);
                if (!mainFrame.skillsLocked)
                    mainFrame.skillsE.setEnabled(true);
                if (!mainFrame.resLocked)
                    mainFrame.resE.setEnabled(true);

                if (!mainFrame.BLocked)
                    mainFrame.typeB.setEnabled(true);
                if (!mainFrame.ALocked)
                    mainFrame.typeA.setEnabled(true);
                if (!mainFrame.CLocked)
                    mainFrame.typeC.setEnabled(true);
                if (!mainFrame.DLocked)
                    mainFrame.typeD.setEnabled(true);
            }
        }
    }
}
