package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AttrPointsChange implements ChangeListener {

    private MainFrame mainFrame;

    public AttrPointsChange(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner sp = (JSpinner) e.getSource();
        JPanel panel = (JPanel) sp.getParent();
        if (sp.getName() == "bod") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXbody()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 0;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getBody() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setBody(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getBody() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setBody(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getBody() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setBody(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "agi") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXagility()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 1;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getAgility() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setAgility(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getAgility() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setAgility(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getAgility() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setAgility(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "rea") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXreaction()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 2;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getReaction() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setReaction(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getReaction() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setReaction(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getReaction() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setReaction(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "str") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXstrength()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 3;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getStrength() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setStrength(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getStrength() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setStrength(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getStrength() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setStrength(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "wil") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXwillpower()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 4;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getWillpower() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setWillpower(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getWillpower() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setWillpower(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getWillpower() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setWillpower(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "log") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXintuition()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 5;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getLogic() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setLogic(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getLogic() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setLogic(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getLogic() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setLogic(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "int") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXintuition()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 6;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getIntuition() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setIntuition(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getIntuition() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setIntuition(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getIntuition() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setIntuition(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "cha") {
            if ((int) sp.getValue() == mainFrame.currentCharacter.getAttributes()
                    .getMAXcharisma()) {
                if (mainFrame.maxAttributeIndex == -1) {
                    mainFrame.maxAttributeIndex = 7;
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getCharisma() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setCharisma(
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
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getCharisma() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setCharisma(
                            (int) sp.getValue());
                    mainFrame.maxAttributeIndex = -1;
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getCharisma() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setCharisma(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "edg") {
            if ((int) sp.getValue() < mainFrame.currentCharacter.getAttributes()
                    .getEdge()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes += mainFrame.currentCharacter.getAttributes()
                        .getEdge() - (int) sp.getValue();
                mainFrame.currentCharacter.getAttributes().setEdge(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() > mainFrame.currentCharacter
                                .getAttributes().getEdge()))) {
                    mainFrame.charSpecialAttributes += mainFrame.currentCharacter
                            .getAttributes().getEdge()
                            - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setEdge(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getEdge() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setEdge(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "mag") {
            if ((int) sp.getValue() < mainFrame.currentCharacter.getAttributes()
                    .getMagic()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes += mainFrame.currentCharacter.getAttributes()
                        .getMagic() - (int) sp.getValue();
                mainFrame.currentCharacter.getAttributes().setMagic(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() > mainFrame.currentCharacter
                                .getAttributes().getMagic()))) {
                    mainFrame.charSpecialAttributes += mainFrame.currentCharacter
                            .getAttributes().getMagic()
                            - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setMagic(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getMagic() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setMagic(
                            (int) sp.getValue());
                }
            }
        } else if (sp.getName() == "res") {
            if ((int) sp.getValue() < mainFrame.currentCharacter.getAttributes()
                    .getResonance()
                    && mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes) {
                mainFrame.charSpecialAttributes += mainFrame.currentCharacter.getAttributes()
                        .getResonance() - (int) sp.getValue();
                mainFrame.currentCharacter.getAttributes().setResonance(
                        (int) sp.getValue());
            } else {
                if (mainFrame.charSpecialAttributes > 0
                        && (mainFrame.charSpecialAttributes < mainFrame.initValueCharSpecialAttributes || ((int) sp
                                .getValue() > mainFrame.currentCharacter
                                .getAttributes().getResonance()))) {
                    mainFrame.charSpecialAttributes += mainFrame.currentCharacter
                            .getAttributes().getResonance()
                            - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setResonance(
                            (int) sp.getValue());
                } else {
                    mainFrame.charAttributes += mainFrame.currentCharacter.getAttributes()
                            .getResonance() - (int) sp.getValue();
                    mainFrame.currentCharacter.getAttributes().setResonance(
                            (int) sp.getValue());
                }
            }
        }
        mainFrame.atr.setValue(mainFrame.charAttributes);
        mainFrame.spAtr.setValue(mainFrame.charSpecialAttributes);
    }

}
