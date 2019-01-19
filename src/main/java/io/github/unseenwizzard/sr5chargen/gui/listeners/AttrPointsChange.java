package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AttrPointsChange implements ChangeListener {

    private MainFrame mainFrame;
    private final CharacterController characterController;

    public AttrPointsChange(MainFrame mainFrame, CharacterController characterController) {
        this.mainFrame = mainFrame;
        this.characterController = characterController;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner sp = (JSpinner) e.getSource();
        JPanel panel = (JPanel) sp.getParent();
        if (sp.getName() == "bod") {
            if ((int) sp.getValue() == characterController.getCharacter().getAttributes()
                    .getMAXbody()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 0;
                    mainFrame.charAttributes += characterController.getCharacter().getAttributes()
                            .getBody() - (int) sp.getValue();
                    characterController.getCharacter().getAttributes().setBody(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 0) {
                    mainFrame.charAttributes += characterController.getCharacter().getAttributes()
                            .getBody() - (int) sp.getValue();
                    characterController.getCharacter().getAttributes().setBody(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += characterController.getCharacter().getAttributes()
                            .getBody() - (int) sp.getValue();
                    characterController.getCharacter().getAttributes().setBody(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "agi") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXagility()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 1;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getAgility() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setAgility(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 1) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getAgility() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setAgility(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getAgility() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setAgility(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "rea") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXreaction()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 2;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getReaction() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setReaction(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 2) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getReaction() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setReaction(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getReaction() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setReaction(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "str") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXstrength()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 3;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getStrength() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setStrength(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 3) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getStrength() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setStrength(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getStrength() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setStrength(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "wil") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXwillpower()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 4;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getWillpower() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setWillpower(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 4) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getWillpower() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setWillpower(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getWillpower() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setWillpower(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "log") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXintuition()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 5;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getLogic() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setLogic(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 5) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getLogic() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setLogic(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getLogic() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setLogic(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "int") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXintuition()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 6;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getIntuition() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setIntuition(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 6) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getIntuition() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setIntuition(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getIntuition() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setIntuition(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "cha") {
            if ((int) sp.getValue() ==  characterController.getCharacter().getAttributes()
                    .getMAXcharisma()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 7;
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getCharisma() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setCharisma(
                            (int) sp.getValue());
                } else {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Only one attribute can be raised to it's natural maximum during character generation!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                    sp.setValue(sp.getPreviousValue());
                }
            } else {
                if (mainFrame.maxAttributeIndex == 7) {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getCharisma() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setCharisma(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getCharisma() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setCharisma(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "edg") {
            if ((int) sp.getValue() <  characterController.getCharacter().getAttributes()
                    .getEdge()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes +=  characterController.getCharacter().getAttributes()
                        .getEdge() - (int) sp.getValue();
                 characterController.getCharacter().getAttributes().setEdge(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() >  characterController.getCharacter()
                                .getAttributes().getEdge()))) {
                    mainFrame.charSpecialAttributes +=  characterController.getCharacter()
                            .getAttributes().getEdge()
                            - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setEdge(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getEdge() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setEdge(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "mag") {
            if ((int) sp.getValue() <  characterController.getCharacter().getAttributes()
                    .getMagic()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes +=  characterController.getCharacter().getAttributes()
                        .getMagic() - (int) sp.getValue();
                 characterController.getCharacter().getAttributes().setMagic(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() >  characterController.getCharacter()
                                .getAttributes().getMagic()))) {
                    mainFrame.charSpecialAttributes +=  characterController.getCharacter()
                            .getAttributes().getMagic()
                            - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setMagic(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getMagic() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setMagic(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "res") {
            if ((int) sp.getValue() <  characterController.getCharacter().getAttributes()
                    .getResonance()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes +=  characterController.getCharacter().getAttributes()
                        .getResonance() - (int) sp.getValue();
                 characterController.getCharacter().getAttributes().setResonance(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() >  characterController.getCharacter()
                                .getAttributes().getResonance()))) {
                    mainFrame.charSpecialAttributes +=  characterController.getCharacter()
                            .getAttributes().getResonance()
                            - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setResonance(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes +=  characterController.getCharacter().getAttributes()
                            .getResonance() - (int) sp.getValue();
                     characterController.getCharacter().getAttributes().setResonance(
                            (int) sp.getValue());
                }
            }
        }
        mainFrame.atr.setValue(mainFrame.charAttributes);
        mainFrame.spAtr.setValue(mainFrame.charSpecialAttributes);
    }

}
