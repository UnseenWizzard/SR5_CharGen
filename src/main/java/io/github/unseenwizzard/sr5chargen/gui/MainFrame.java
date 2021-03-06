/**
 * SR5CharGen - a character generator for the pen&paper rpg Shadowrun (5. Ed.)
 * <p>
 * Copyright(c) 2013-2014 Nicola Michel Henry Riedmann
 * <p>
 * This program has no affiliation whatsoever with "Shadowrun", "Catalyst Game Labs" or any local publishing firm.
 * I am just a fan hoping to create a program that will be usefull to others enjoying Shadowrun.
 * <p>
 * Uses:
 * 1. XStream - XStream is a simple library to serialize objects to XML and back again.
 * 1.1. License:
 * Copyright (c) 2003-2006, Joe Walnes
 * Copyright (c) 2006-2009, 2011 XStream Committers
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <p>
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 * <p>
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 * <p>
 * 3. Neither the name of XStream nor the names of its contributors may be used to endorse
 * or promote products derived from this software without specific prior written
 * permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 * <p>
 * 2. Apache PDFBox - A Java PDF Library
 * The Apache PDFBox™ library is an open source Java tool for working with PDF documents. This project allows creation of new PDF documents, manipulation of existing documents and the ability to extract content from documents. Apache PDFBox also includes several command line utilities. Apache PDFBox is published under the Apache License v2.0.
 * <p>
 * 2.1. License:
 * Copyright © 2014 The Apache Software Foundation, Licensed under the Apache License, Version 2.0.
 * Apache PDFBox, PDFBox, Apache, the Apache feather logo and the Apache PDFBox project logos are trademarks of The Apache Software Foundation.
 * <p>
 * <p>
 * <p>
 * Notes:
 * TODO:
 * Numbers show importance, higher num-> higher priority
 * General:
 * 3 implement full char file loading
 * 3 display char's in char folder in start view
 * 3 implement group files, group display and special group actions (give money to all, etc)
 * 2 check if rituals work
 * 2 special handling in print
 * <p>
 * 0 Gear To-Strings need a space between cost & "NuYen"
 * General Gear Buy Screens:
 * 1 Make lists multiselect
 * 2 add Categories to weapons and similar gear
 * Augements:
 * 1 show essence in info bar
 * <p>
 * 1 Eyes, Arms, Ear, Legs etc. limited by number!
 * 1 Cybercommlink needs you to choose a commlink
 * Ammo:
 * <p>
 * Char Display Screen:
 * 0 condition boxes resize aqwardly when clicked
 */
package io.github.unseenwizzard.sr5chargen.gui;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.data.Contact;
import io.github.unseenwizzard.sr5chargen.data.character.Character;
import io.github.unseenwizzard.sr5chargen.data.character.*;
import io.github.unseenwizzard.sr5chargen.data.character.Skill.Attribute;
import io.github.unseenwizzard.sr5chargen.data.equipment.Gear;
import io.github.unseenwizzard.sr5chargen.data.equipment.Vehicle;
import io.github.unseenwizzard.sr5chargen.data.equipment.Vehicle.CraftType;
import io.github.unseenwizzard.sr5chargen.data.equipment.armor.Armor;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Deck;
import io.github.unseenwizzard.sr5chargen.data.equipment.decking.Program;
import io.github.unseenwizzard.sr5chargen.data.equipment.personal.Augmentation;
import io.github.unseenwizzard.sr5chargen.data.equipment.personal.ID;
import io.github.unseenwizzard.sr5chargen.data.equipment.weapon.*;
import io.github.unseenwizzard.sr5chargen.gui.listeners.*;
import io.github.unseenwizzard.sr5chargen.gui.views.creation.PersonalDataView;
import io.github.unseenwizzard.sr5chargen.utils.ListRoutine;
import io.github.unseenwizzard.sr5chargen.utils.RandomRunGenerator;
import io.github.unseenwizzard.sr5chargen.utils.dice.Die;
import io.github.unseenwizzard.sr5chargen.utils.dice.DieRoll;
import io.github.unseenwizzard.sr5chargen.utils.dice.DieRoller;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import static io.github.unseenwizzard.sr5chargen.utils.dice.DieRoll.Result.CRITICAL_GLITCH;

public class MainFrame extends JFrame {

    public int charSpecialAttributes;
    public int initValueCharSpecialAttributes = -1;
    public int charAttributes = -1;
    public MagicResonancePriority[] charMagicResonance;
    public int chosenMagResPriorityIndex = -1;
    public int charSkills = -1;
    public int charSkillGroups = -1;
    public double charRessources = -1;
    public int charKnowledgePoints;
    public int initValueCharKnowledgePoints = -1;
    public int maxAttributeIndex = -1; // 0 bod, 1 agi, 2 rea, 3 str, 4 wil, 5
    // log, 6 int, 7 cha
    public JSpinner atr;
    public JSpinner spAtr;
    public JButton typeA, typeB, typeC, typeD, typeE;
    public JButton attrA, attrB, attrC, attrD, attrE;
    public JButton magResA, magResB, magResC, magResD, magResE;
    public JButton skillsA, skillsB, skillsC, skillsD, skillsE;
    public JButton resA, resB, resC, resD, resE;
    // private JLabel SkillGroupInfo;
    public boolean ALocked;
    public boolean BLocked;
    public boolean CLocked;
    public boolean DLocked;
    public boolean ELocked;
    public boolean typeLocked;
    public boolean attrLocked;
    public boolean magResLocked;
    public boolean skillsLocked;
    public boolean resLocked = false;
    public ListRoutine LR;
    public MainMenu mainMenu;
    public int[] prevValues;
    public int prevValue = -1;
    SkillGroup tasking = null;
    private int frameWidth = 900;
    private int frameHeight = 600;
    private ArrayList<Skill> activeSkills;
    private ArrayList<Skill> knowledgeSkills;
    private ArrayList<Skill> magicSkills;
    private ArrayList<Skill> resonanceSkills;
    private int freeSpells = 0;
    private int freeContPoints = 0;

    private CharacterController characterController;

    public MainFrame() {
        this.LR = new ListRoutine();
        this.characterController = new CharacterController();
        initUI();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame ex = new MainFrame();
                ex.setVisible(true);
            }
        });
    }

    private void clearContents() {
        if (this.getContentPane().getComponentCount() > 0) {
            JPanel panel = (JPanel) this.getContentPane().getComponent(0);
            panel.removeAll();
            remove(panel);
            revalidate();
            repaint();
        }
    }

    public void startCharacterGeneration() {
        characterController.saveAllowed = false;

        clearContents();

        JPanel charInfo = new PersonalDataView(characterController, this);
        this.getContentPane().add(charInfo);
        charInfo.setVisible(true);
        this.setVisible(true);


        revalidate();
        repaint();
    }

    public void priorityChoosing() {

//		String charName = name.getText();
//		data.Sex charSex = (data.Sex) sex.getSelectedItem();
//		data.Metatype charType = (data.Metatype) metatype.getSelectedItem();
//		System.out.println("Name is:[" + charName + "]");
//		if (charName.isEmpty()) {
//			if (charSex == data.Sex.FEMALE) {
//				charName = "Jane Doe";
//			} else {
//				charName = "John Doe";
//			}
//		}
//
//		characterController.getCharacter() = new data.Character(charType, charName, charSex);
//		characterController.getCharacter().getPersonalData().addKarma(25);
//		characterController.getCharacter().getPersonalData().setName(charName);
        clearContents();
        final JPanel displayPanel = new JPanel(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        panel.requestFocus();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_START;


        TypePriority[] pType = new TypePriority[5];
        Metatype[] type = {Metatype.HUMAN, Metatype.ELF,
                Metatype.DWARF, Metatype.ORC, Metatype.TROLL};
        int[] specialAttributes = {9, 8, 7, 7, 5};
        int[] specialAttributes1 = {7, 6, 4, 4, 0};
        Metatype[] type2 = {Metatype.HUMAN, Metatype.ELF,
                Metatype.DWARF, Metatype.ORC};
        int[] specialAttributes2 = {5, 3, 1, 0};
        Metatype[] type3 = {Metatype.HUMAN, Metatype.ELF};
        int[] specialAttributes3 = {3, 0};
        Metatype[] type4 = {Metatype.HUMAN};
        int[] specialAttributes4 = {1};
        pType[0] = new TypePriority(type, specialAttributes);
        pType[1] = new TypePriority(type, specialAttributes1);
        pType[2] = new TypePriority(type2, specialAttributes2);
        pType[3] = new TypePriority(type3, specialAttributes3);
        pType[4] = new TypePriority(type4, specialAttributes4);

        int[] AttributePriority = {24, 20, 16, 14, 12};

        /*
         * [][0]..Magician/Wizardadept [][1]..Technomancer [][2]--Adept
         * [][3]..Aspectwizard
         *
         * MagicResonancePriority(MagicResonance, numOfMagicSkills,
         * valueOfMagicSkills, numOfSkills, valueOfSkills,
         * numOfMagicSkillGroups, valueOfMagicSkillGroups,
         * numOfSpellsComplexForms)
         */
        MagicResonancePriority[][] pMagicRes = new MagicResonancePriority[5][4];
        pMagicRes[0][0] = new MagicResonancePriority(6, 2, 5, 0, 0, 0, 0, 10);
        pMagicRes[0][1] = new MagicResonancePriority(6, 2, 5, 0, 0, 0, 0, 5);
        pMagicRes[0][2] = null;
        pMagicRes[0][3] = null;

        pMagicRes[1][0] = new MagicResonancePriority(4, 2, 4, 0, 0, 0, 0, 7);
        pMagicRes[1][1] = new MagicResonancePriority(4, 2, 4, 0, 0, 0, 0, 2);
        pMagicRes[1][2] = new MagicResonancePriority(6, 0, 0, 1, 4, 0, 0, 0);
        pMagicRes[1][3] = new MagicResonancePriority(5, 0, 0, 0, 0, 1, 4, 0);

        pMagicRes[2][0] = new MagicResonancePriority(3, 0, 0, 0, 0, 0, 0, 5);
        pMagicRes[2][1] = new MagicResonancePriority(3, 0, 0, 0, 0, 0, 0, 1);
        pMagicRes[2][2] = new MagicResonancePriority(4, 0, 0, 1, 2, 0, 0, 0);
        pMagicRes[2][3] = new MagicResonancePriority(3, 0, 0, 0, 0, 1, 2, 0);

        pMagicRes[3][0] = null;
        pMagicRes[3][1] = null;
        pMagicRes[3][2] = new MagicResonancePriority(2, 0, 0, 0, 0, 0, 0, 0);
        pMagicRes[3][3] = new MagicResonancePriority(2, 0, 0, 0, 0, 0, 0, 0);

        pMagicRes[4][0] = null;
        pMagicRes[4][1] = null;
        pMagicRes[4][2] = null;
        pMagicRes[4][3] = null;

        int[] SkillPriority = {46, 36, 28, 22, 18};
        int[] SkillGroupPriority = {10, 5, 2, 0, 0};
        int[] RessourcesPriority = {450000, 275000, 140000, 50000, 6000};

        JLabel A = new JLabel("A");
        JLabel B = new JLabel("B");
        JLabel C = new JLabel("C");
        JLabel D = new JLabel("D");
        JLabel E = new JLabel("E");
        A.setHorizontalAlignment(JLabel.CENTER);
        A.setVerticalAlignment(JLabel.CENTER);
        B.setHorizontalAlignment(JLabel.CENTER);
        B.setVerticalAlignment(JLabel.CENTER);
        C.setHorizontalAlignment(JLabel.CENTER);
        C.setVerticalAlignment(JLabel.CENTER);
        D.setHorizontalAlignment(JLabel.CENTER);
        D.setVerticalAlignment(JLabel.CENTER);
        E.setHorizontalAlignment(JLabel.CENTER);
        E.setVerticalAlignment(JLabel.CENTER);

        typeA = new JButton(
                "<html>Human (9)<br />Elf (8)<br />Orc (7)<br />Dwarf (7)<br />Troll (5)</html>");
        typeB = new JButton(
                "<html>Human (7)<br />Elf (6)<br />Orc (4)<br />Dwarf (4)<br />Troll (0)</html>");
        typeC = new JButton(
                "<html>Human (5)<br />Elf (3)<br />Dwarf (1)<br />Orc (0)</html>");
        typeD = new JButton("<html>Human (3)<br />Elf (0)</html>");
        typeE = new JButton("Human(1)");

        attrA = new JButton(AttributePriority[0] + "");
        attrB = new JButton(AttributePriority[1] + "");
        attrC = new JButton(AttributePriority[2] + "");
        attrD = new JButton(AttributePriority[3] + "");
        attrE = new JButton(AttributePriority[4] + "");

        magResA = new JButton(
                "<html>"
                        + "Magician: Magic 6, 2 magic skills (5), 10 Spells, Rituals and/or Alchemical Spells<br />"
                        + "Technomancer: Resonance 6, 2 resonance skills (5), 5 Complex Forms<br />"
                        + "</html>");
        magResB = new JButton(
                "<html>"
                        + "Magician: Magic 4, 2 magic skills (4), 7 Spells, Rituals and/or Alchemical Spells<br />"
                        + "Technomancer: Resonance 4, 2 resonance skills (4), 2 Complex Forms<br />"
                        + "Adept: Magic 6, 1 skill (4)<br />"
                        + "Aspected Magician:  Magic 5, 1 magic skill group (4)"
                        + "</html>");
        magResC = new JButton(
                "<html>"
                        + "Magician: Magic 3, 5 Spells, Rituals and/or Alchemical Spells<br />"
                        + "Technomancer: Resonance 3, 1 Complex Forms<br />"
                        + "Adept: Magic 4, 1 skill (2)<br />"
                        + "Aspected Magician:  Magic 3, 1 magic skill group (2)"
                        + "</html>");
        magResD = new JButton("<html>" + "Adept: Magic 2<br />"
                + "Aspected Magician:  Magic 2" + "</html>");
        magResE = new JButton("-");

        skillsA = new JButton(SkillPriority[0] + "/" + SkillGroupPriority[0]);
        skillsB = new JButton(SkillPriority[1] + "/" + SkillGroupPriority[1]);
        skillsC = new JButton(SkillPriority[2] + "/" + SkillGroupPriority[2]);
        skillsD = new JButton(SkillPriority[3] + "/" + SkillGroupPriority[3]);
        skillsE = new JButton(SkillPriority[4] + "/" + SkillGroupPriority[4]);

        resA = new JButton(RessourcesPriority[0] + "");
        resB = new JButton(RessourcesPriority[1] + "");
        resC = new JButton(RessourcesPriority[2] + "");
        resD = new JButton(RessourcesPriority[3] + "");
        resE = new JButton(RessourcesPriority[4] + "");

        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(A, constraints);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        panel.add(typeA, constraints);
        constraints.gridx = 2;
        panel.add(attrA, constraints);
        constraints.gridx = 3;
        constraints.weightx = 2.0;
        panel.add(magResA, constraints);
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        panel.add(skillsA, constraints);
        constraints.gridx = 5;
        panel.add(resA, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(B, constraints);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        panel.add(typeB, constraints);
        constraints.gridx = 2;
        panel.add(attrB, constraints);
        constraints.gridx = 3;
        constraints.weightx = 2.0;
        panel.add(magResB, constraints);
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        panel.add(skillsB, constraints);
        constraints.gridx = 5;
        panel.add(resB, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(C, constraints);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        panel.add(typeC, constraints);
        constraints.gridx = 2;
        panel.add(attrC, constraints);
        constraints.gridx = 3;
        constraints.weightx = 2.0;
        panel.add(magResC, constraints);
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        panel.add(skillsC, constraints);
        constraints.gridx = 5;
        panel.add(resC, constraints);

        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(D, constraints);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        panel.add(typeD, constraints);
        constraints.gridx = 2;
        panel.add(attrD, constraints);
        constraints.gridx = 3;
        constraints.weightx = 2.0;
        panel.add(magResD, constraints);
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        panel.add(skillsD, constraints);
        constraints.gridx = 5;
        panel.add(resD, constraints);

        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(E, constraints);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 1;
        panel.add(typeE, constraints);
        constraints.gridx = 2;
        panel.add(attrE, constraints);
        constraints.gridx = 3;
        constraints.weightx = 2.0;
        panel.add(magResE, constraints);
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        panel.add(skillsE, constraints);
        constraints.gridx = 5;
        panel.add(resE, constraints);


        JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
                + " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
                + characterController.getCharacter().getPersonalData().getMetatype());

        info.setHorizontalAlignment(JLabel.CENTER);
        info.setVerticalAlignment(JLabel.CENTER);

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (charAttributes == -1 || charRessources == -1
                        || charSkills == -1 || charSpecialAttributes == -1
                        || charMagicResonance == null) {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "Please choose one entry from each priority groups before proceeding!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    setAttributes();
                }
            }
        });

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                startCharacterGeneration();
            }
        });

        infoPanel.add(info, BorderLayout.CENTER);
        infoPanel.add(next, BorderLayout.EAST);
        infoPanel.add(back, BorderLayout.WEST);

        displayPanel.add(panel, BorderLayout.CENTER);
        displayPanel.add(infoPanel, BorderLayout.SOUTH);

        add(displayPanel);
        revalidate();
        repaint();

        typeA.addActionListener(new TypeListener(this, pType[0], characterController));
        typeB.addActionListener(new TypeListener(this, pType[1], characterController));
        typeC.addActionListener(new TypeListener(this, pType[2], characterController));
        typeD.addActionListener(new TypeListener(this, pType[3], characterController));
        typeE.addActionListener(new TypeListener(this, pType[4], characterController));

        attrA.addActionListener(new AttrListener(this, AttributePriority[0]));
        attrB.addActionListener(new AttrListener(this, AttributePriority[1]));
        attrC.addActionListener(new AttrListener(this, AttributePriority[2]));
        attrD.addActionListener(new AttrListener(this, AttributePriority[3]));
        attrE.addActionListener(new AttrListener(this, AttributePriority[4]));

        magResA.addActionListener(new MagResListener(this, pMagicRes[0]));
        magResB.addActionListener(new MagResListener(this, pMagicRes[1]));
        magResC.addActionListener(new MagResListener(this, pMagicRes[2]));
        magResD.addActionListener(new MagResListener(this, pMagicRes[3]));
        magResE.addActionListener(new MagResListener(this, pMagicRes[4]));

        skillsA.addActionListener(new SkillListener(this, SkillPriority[0],
                SkillGroupPriority[0]));
        skillsB.addActionListener(new SkillListener(this, SkillPriority[1],
                SkillGroupPriority[1]));
        skillsC.addActionListener(new SkillListener(this, SkillPriority[2],
                SkillGroupPriority[2]));
        skillsD.addActionListener(new SkillListener(this, SkillPriority[3],
                SkillGroupPriority[3]));
        skillsE.addActionListener(new SkillListener(this, SkillPriority[4],
                SkillGroupPriority[4]));

        resA.addActionListener(new RessourceListener(this, RessourcesPriority[0]));
        resB.addActionListener(new RessourceListener(this, RessourcesPriority[1]));
        resC.addActionListener(new RessourceListener(this, RessourcesPriority[2]));
        resD.addActionListener(new RessourceListener(this, RessourcesPriority[3]));
        resE.addActionListener(new RessourceListener(this, RessourcesPriority[4]));
    }

    private void setAttributes() {
        final JPanel displayPanel = new JPanel(new BorderLayout());;
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        clearContents();
        GridBagConstraints constraints = new GridBagConstraints();

        Attributes baseAttributes = new Attributes();
        if (characterController.getCharacter().getPersonalData().getMetatype() == Metatype.HUMAN) {
            baseAttributes.setBody(1);
            baseAttributes.setMAXbody(6);
            baseAttributes.setAgility(1);
            baseAttributes.setMAXagility(6);
            baseAttributes.setReaction(1);
            baseAttributes.setMAXreaction(6);
            baseAttributes.setStrength(1);
            baseAttributes.setMAXstrength(6);
            baseAttributes.setWillpower(1);
            baseAttributes.setMAXwillpower(6);
            baseAttributes.setLogic(1);
            baseAttributes.setMAXlogic(6);
            baseAttributes.setIntuition(1);
            baseAttributes.setMAXintuition(6);
            baseAttributes.setCharisma(1);
            baseAttributes.setMAXcharisma(6);
            baseAttributes.setEdge(2);
            baseAttributes.setMAXedge(7);
            baseAttributes.setEssence(6);
            baseAttributes.setResonance(0);
            baseAttributes.setMAXresonance(6);
            baseAttributes.setMagic(0);
            baseAttributes.setMAXmagic(6);
        } else if (characterController.getCharacter().getPersonalData().getMetatype() == Metatype.ELF) {
            baseAttributes.setBody(1);
            baseAttributes.setMAXbody(6);
            baseAttributes.setAgility(2);
            baseAttributes.setMAXagility(7);
            baseAttributes.setReaction(1);
            baseAttributes.setMAXreaction(6);
            baseAttributes.setStrength(1);
            baseAttributes.setMAXstrength(6);
            baseAttributes.setWillpower(1);
            baseAttributes.setMAXwillpower(6);
            baseAttributes.setLogic(1);
            baseAttributes.setMAXlogic(6);
            baseAttributes.setIntuition(1);
            baseAttributes.setMAXintuition(6);
            baseAttributes.setCharisma(3);
            baseAttributes.setMAXcharisma(8);
            baseAttributes.setEdge(1);
            baseAttributes.setMAXedge(6);
            baseAttributes.setEssence(6);
            baseAttributes.setResonance(0);
            baseAttributes.setMAXresonance(6);
            baseAttributes.setMagic(0);
            baseAttributes.setMAXmagic(6);
        } else if (characterController.getCharacter().getPersonalData().getMetatype() == Metatype.DWARF) {
            baseAttributes.setBody(3);
            baseAttributes.setMAXbody(8);
            baseAttributes.setAgility(1);
            baseAttributes.setMAXagility(6);
            baseAttributes.setReaction(1);
            baseAttributes.setMAXreaction(5);
            baseAttributes.setStrength(3);
            baseAttributes.setMAXstrength(8);
            baseAttributes.setWillpower(2);
            baseAttributes.setMAXwillpower(7);
            baseAttributes.setLogic(1);
            baseAttributes.setMAXlogic(6);
            baseAttributes.setIntuition(1);
            baseAttributes.setMAXintuition(6);
            baseAttributes.setCharisma(1);
            baseAttributes.setMAXcharisma(6);
            baseAttributes.setEdge(1);
            baseAttributes.setMAXedge(6);
            baseAttributes.setEssence(6);
            baseAttributes.setResonance(0);
            baseAttributes.setMAXresonance(6);
            baseAttributes.setMagic(0);
            baseAttributes.setMAXmagic(6);
        } else if (characterController.getCharacter().getPersonalData().getMetatype() == Metatype.ORC) {
            baseAttributes.setBody(4);
            baseAttributes.setMAXbody(9);
            baseAttributes.setAgility(1);
            baseAttributes.setMAXagility(6);
            baseAttributes.setReaction(1);
            baseAttributes.setMAXreaction(6);
            baseAttributes.setStrength(3);
            baseAttributes.setMAXstrength(8);
            baseAttributes.setWillpower(1);
            baseAttributes.setMAXwillpower(6);
            baseAttributes.setLogic(1);
            baseAttributes.setMAXlogic(5);
            baseAttributes.setIntuition(1);
            baseAttributes.setMAXintuition(6);
            baseAttributes.setCharisma(1);
            baseAttributes.setMAXcharisma(5);
            baseAttributes.setEdge(1);
            baseAttributes.setMAXedge(6);
            baseAttributes.setEssence(6);
            baseAttributes.setResonance(0);
            baseAttributes.setMAXresonance(6);
            baseAttributes.setMagic(0);
            baseAttributes.setMAXmagic(6);
        } else if (characterController.getCharacter().getPersonalData().getMetatype() == Metatype.TROLL) {
            baseAttributes.setBody(5);
            baseAttributes.setMAXbody(10);
            baseAttributes.setAgility(1);
            baseAttributes.setMAXagility(5);
            baseAttributes.setReaction(1);
            baseAttributes.setMAXreaction(6);
            baseAttributes.setStrength(5);
            baseAttributes.setMAXstrength(10);
            baseAttributes.setWillpower(1);
            baseAttributes.setMAXwillpower(6);
            baseAttributes.setLogic(1);
            baseAttributes.setMAXlogic(5);
            baseAttributes.setIntuition(1);
            baseAttributes.setMAXintuition(5);
            baseAttributes.setCharisma(1);
            baseAttributes.setMAXcharisma(4);
            baseAttributes.setEdge(1);
            baseAttributes.setMAXedge(6);
            baseAttributes.setEssence(6);
            baseAttributes.setResonance(0);
            baseAttributes.setMAXresonance(6);
            baseAttributes.setMagic(0);
            baseAttributes.setMAXmagic(6);
        }
        characterController.getCharacter().setAttributes(baseAttributes);

        //JButton next = new JButton("Next");
        System.out.println(chosenMagResPriorityIndex);
        if (chosenMagResPriorityIndex < 4) {
            System.out.println(chosenMagResPriorityIndex);
            final JComboBox<String> magicInput = new JComboBox<String>();
            if (chosenMagResPriorityIndex == 0) {
                magicInput.addItem("Magician");
                magicInput.addItem("Mystic Adept");
                magicInput.addItem("Technomancer");
            } else if (chosenMagResPriorityIndex == 1) {
                magicInput.addItem("Magician");
                magicInput.addItem("Mystic Adept");
                magicInput.addItem("Technomancer");
                magicInput.addItem("Adept");
                magicInput.addItem("Aspected Magician");
            } else if (chosenMagResPriorityIndex == 2) {
                magicInput.addItem("Magician");
                magicInput.addItem("Mystic Adept");
                magicInput.addItem("Technomancer");
                magicInput.addItem("Adept");
                magicInput.addItem("Aspected Magician");
            } else if (chosenMagResPriorityIndex == 3) {
                magicInput.addItem("Adept");
                magicInput.addItem("Aspected Magician");
            }
            magicInput.setSelectedIndex(0);
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(
                    new JLabel(
                            "Please choose whether the character is a (Aspected) Magician, (Mystic) Adpet or Technomancer now."),
                    constraints);
            constraints.gridy = 1;
            panel.add(magicInput, constraints);
            //	constraints.gridy = 2;
            //	panel.add(next, constraints);

            JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
                    + " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
                    + characterController.getCharacter().getPersonalData().getMetatype());

            info.setHorizontalAlignment(JLabel.CENTER);
            info.setVerticalAlignment(JLabel.CENTER);

            JButton next = new JButton("Next");

            JButton back = new JButton("Back");
            back.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    characterController.setCharacter(new Character(characterController.getCharacter().getPersonalData().getMetatype(), characterController.getCharacter().getPersonalData().getName(), characterController.getCharacter().getPersonalData().getSex()));
//                    characterController.getCharacter().getPersonalData().addKarma(25);
//                    characterController.getCharacter().getPersonalData().setName(characterController.getCharacter().getPersonalData().getName());
                    priorityChoosing();
                }
            });

            infoPanel.add(info, BorderLayout.CENTER);
            infoPanel.add(next, BorderLayout.EAST);
            infoPanel.add(back, BorderLayout.WEST);

            displayPanel.add(panel, BorderLayout.CENTER);
            displayPanel.add(infoPanel, BorderLayout.SOUTH);

            add(displayPanel);
            revalidate();
            repaint();

            next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*
                     * [][0]..Magician/Wizardadept [][1]..Technomancer
                     * [][2]--Adept [][3]..Aspectwizard
                     * MagicResonancePriority(MagicResonance, numOfMagicSkills,
                     * valueOfMagicSkills, numOfSkills, valueOfSkills,
                     * numOfMagicSkillGroups, valueOfMagicSkillGroups,
                     * numOfSpellsComplexForms)
                     */
                    switch (magicInput.getSelectedIndex()) {
                        case 0: // Magician
                            if (chosenMagResPriorityIndex == 3) {
                                // Adept
                                characterController.getCharacter().getAttributes().setMagic(
                                        charMagicResonance[2].MagicResonance);
                                characterController.getCharacter().setMagicalness(Magical.Adept);
                            } else {
                                characterController.getCharacter().getAttributes().setMagic(
                                        charMagicResonance[0].MagicResonance);
                                characterController.getCharacter().setMagicalness(Magical.Magician);
                            }
                            break;
                        case 1: // Myst Adept
                            if (chosenMagResPriorityIndex == 3) {
                                // Aspected magi
                                characterController.getCharacter().getAttributes().setMagic(
                                        charMagicResonance[3].MagicResonance);
                                characterController.getCharacter()
                                        .setMagicalness(Magical.AspectedMagician);
                            } else {
                                characterController.getCharacter().getAttributes().setMagic(
                                        charMagicResonance[0].MagicResonance);
                                characterController.getCharacter()
                                        .setMagicalness(Magical.MysticalAdept);
                            }
                            break;
                        case 2: // Technom
                            characterController.getCharacter().getAttributes().setResonance(
                                    charMagicResonance[1].MagicResonance);
                            characterController.getCharacter().setMagicalness(Magical.Technomancer);
                            break;
                        case 3: // Adept
                            characterController.getCharacter().getAttributes().setMagic(
                                    charMagicResonance[2].MagicResonance);
                            characterController.getCharacter().setMagicalness(Magical.Adept);
                            break;
                        case 4: // Aspected magi
                            characterController.getCharacter().getAttributes().setMagic(
                                    charMagicResonance[3].MagicResonance);
                            characterController.getCharacter()
                                    .setMagicalness(Magical.AspectedMagician);
                            break;
                    }
                    chooseAttributes();
                }
            });
        } else {
            characterController.getCharacter().setMagicalness(Magical.Mundane);
            chooseAttributes();
        }
    }

    private void chooseAttributes() {
        final JPanel displayPanel = new JPanel(new BorderLayout());;
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        clearContents();
        GridBagConstraints constraints = new GridBagConstraints();

        boolean magic = false;
        boolean techno = false;
        if (characterController.getCharacter().getAttributes().getMagic() > 0) {
            magic = true;
        } else if (characterController.getCharacter().getAttributes().getResonance() > 0) {
            techno = true;
        }

        SpinnerModel modelBod = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getBody(), characterController.getCharacter().getAttributes()
                .getBody(), characterController.getCharacter().getAttributes().getMAXbody(), 1);
        JSpinner spinnerBod = new JSpinner(modelBod);
        SpinnerModel modelAgi = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getAgility(), characterController.getCharacter().getAttributes()
                .getAgility(),
                characterController.getCharacter().getAttributes().getMAXagility(), 1);
        JSpinner spinnerAgi = new JSpinner(modelAgi);
        SpinnerModel modelRea = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getReaction(), characterController.getCharacter()
                .getAttributes().getReaction(), characterController.getCharacter()
                .getAttributes().getMAXreaction(), 1);
        JSpinner spinnerRea = new JSpinner(modelRea);
        SpinnerModel modelStr = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getStrength(), characterController.getCharacter()
                .getAttributes().getStrength(), characterController.getCharacter()
                .getAttributes().getMAXstrength(), 1);
        JSpinner spinnerStr = new JSpinner(modelStr);
        SpinnerModel modelWil = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getWillpower(), characterController.getCharacter()
                .getAttributes().getWillpower(), characterController.getCharacter()
                .getAttributes().getMAXwillpower(), 1);
        JSpinner spinnerWil = new JSpinner(modelWil);
        SpinnerModel modelLog = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getLogic(), characterController.getCharacter().getAttributes()
                .getLogic(), characterController.getCharacter().getAttributes().getMAXlogic(), 1);
        JSpinner spinnerLog = new JSpinner(modelLog);
        SpinnerModel modelInt = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getIntuition(), characterController.getCharacter()
                .getAttributes().getIntuition(), characterController.getCharacter()
                .getAttributes().getMAXintuition(), 1);
        JSpinner spinnerInt = new JSpinner(modelInt);
        SpinnerModel modelCha = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getCharisma(), characterController.getCharacter()
                .getAttributes().getCharisma(), characterController.getCharacter()
                .getAttributes().getMAXcharisma(), 1);
        JSpinner spinnerCha = new JSpinner(modelCha);
        SpinnerModel modelEdg = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getEdge(), characterController.getCharacter().getAttributes()
                .getEdge(), characterController.getCharacter().getAttributes().getMAXedge(), 1);
        JSpinner spinnerEdg = new JSpinner(modelEdg);
        SpinnerModel modelMag = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getMagic(), characterController.getCharacter().getAttributes()
                .getMagic(), characterController.getCharacter().getAttributes().getMAXmagic(), 1);
        JSpinner spinnerMag = new JSpinner(modelMag);
        SpinnerModel modelRes = new SpinnerNumberModel(characterController.getCharacter()
                .getAttributes().getResonance(), characterController.getCharacter()
                .getAttributes().getResonance(), characterController.getCharacter()
                .getAttributes().getMAXresonance(), 1);
        JSpinner spinnerRes = new JSpinner(modelRes);
        spinnerMag.setEnabled(magic);
        spinnerRes.setEnabled(techno);

        JLabel labelBod = new JLabel("body");
        JLabel labelAgi = new JLabel("agility");
        JLabel labelRea = new JLabel("reaction");
        JLabel labelStr = new JLabel("strength");
        JLabel labelWil = new JLabel("willpower");
        JLabel labelLog = new JLabel("logic");
        JLabel labelInt = new JLabel("intuition");
        JLabel labelCha = new JLabel("charisma");
        JLabel labelEdg = new JLabel("edge");
        JLabel labelMag = new JLabel("magic");
        JLabel labelRes = new JLabel("resonance");

        spinnerBod.setName("bod");
        spinnerAgi.setName("agi");
        spinnerRea.setName("rea");
        spinnerStr.setName("str");
        spinnerWil.setName("wil");
        spinnerLog.setName("log");
        spinnerInt.setName("int");
        spinnerCha.setName("cha");
        spinnerEdg.setName("edg");
        spinnerMag.setName("mag");
        spinnerRes.setName("res");

        JLabel attributes = new JLabel("attributes");
        JLabel specialAttributes = new JLabel("special attributes");

        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(attributes, constraints);
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        panel.add(labelBod, constraints);
        constraints.gridy = 2;
        panel.add(labelAgi, constraints);
        constraints.gridy = 3;
        panel.add(labelRea, constraints);
        constraints.gridy = 4;
        panel.add(labelStr, constraints);
        constraints.gridy = 5;
        panel.add(labelWil, constraints);
        constraints.gridy = 6;
        panel.add(labelLog, constraints);
        constraints.gridy = 7;
        panel.add(labelInt, constraints);
        constraints.gridy = 8;
        panel.add(labelCha, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(spinnerBod, constraints);
        constraints.gridy = 2;
        panel.add(spinnerAgi, constraints);
        constraints.gridy = 3;
        panel.add(spinnerRea, constraints);
        constraints.gridy = 4;
        panel.add(spinnerStr, constraints);
        constraints.gridy = 5;
        panel.add(spinnerWil, constraints);
        constraints.gridy = 6;
        panel.add(spinnerLog, constraints);
        constraints.gridy = 7;
        panel.add(spinnerInt, constraints);
        constraints.gridy = 8;
        panel.add(spinnerCha, constraints);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(specialAttributes, constraints);
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        panel.add(labelEdg, constraints);
        constraints.gridy = 2;
        panel.add(labelMag, constraints);
        constraints.gridy = 3;
        panel.add(labelRes, constraints);

        constraints.gridx = 5;
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        panel.add(spinnerEdg, constraints);
        constraints.gridy = 2;
        panel.add(spinnerMag, constraints);
        constraints.gridy = 3;
        panel.add(spinnerRes, constraints);

	/*	JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
				+ " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
				+ characterController.getCharacter().getPersonalData().getMetatype());*/
        SpinnerModel atrMod = new SpinnerNumberModel(this.charAttributes, 0,
                this.charAttributes, 1);
        atr = new JSpinner(atrMod);
        SpinnerModel spAtrMod = new SpinnerNumberModel(
                this.charSpecialAttributes, 0, this.charSpecialAttributes, 1);
        spAtr = new JSpinner(spAtrMod);
        atr.setEnabled(false);
        spAtr.setEnabled(false);

        //	info.setHorizontalAlignment(JLabel.CENTER);
        //	info.setVerticalAlignment(JLabel.CENTER);

        JPanel iPanel = new JPanel();
        //	iPanel.add(info);
        iPanel.add(new JLabel("Attr Pts"));
        iPanel.add(atr);
        iPanel.add(new JLabel("Special Attr Pts"));
        iPanel.add(spAtr);

        JButton next = new JButton("Next");

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                characterController.setCharacter( new Character(characterController.getCharacter().getPersonalData().getMetatype(), characterController.getCharacter().getPersonalData().getName(), characterController.getCharacter().getPersonalData().getSex()));
//                characterController.getCharacter().getPersonalData().addKarma(25);
//                characterController.getCharacter().getPersonalData().setName(characterController.getCharacter().getPersonalData().getName());

                priorityChoosing();
            }
        });

        infoPanel.add(iPanel, BorderLayout.CENTER);
        infoPanel.add(next, BorderLayout.EAST);
        infoPanel.add(back, BorderLayout.WEST);

        displayPanel.add(panel, BorderLayout.CENTER);
        displayPanel.add(infoPanel, BorderLayout.SOUTH);

		/*constraints.gridx = 3;
		constraints.gridy = 9;
		panel.add(info, constraints);
		constraints.gridx = 5;
		panel.add(atr, constraints);
		constraints.gridx = 6;
		panel.add(spAtr, constraints);*/


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (charAttributes < 0 || charSpecialAttributes < 0) {
                    JOptionPane
                            .showMessageDialog(
                                    panel,
                                    "You can't spend more than attributes point than you've got!",
                                    "Attribute points spent",
                                    JOptionPane.ERROR_MESSAGE);
                } else {
                    Attributes attr = characterController.getCharacter().getAttributes();
                    if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)) {
                        attr.setAdeptPowerPoints(attr.getMagic());
                    }
                    charKnowledgePoints = (attr.getIntuition() + attr
                            .getLogic()) * 2;
                    attr.evaluate();
                    initValueCharKnowledgePoints = charKnowledgePoints;
                    double t = (double) attr.getBody();
                    t = t / 2 + 8;
                    characterController.getCharacter().setPhysicalDamageMax((int) Math.ceil(t));
                    t = (double) attr.getWillpower();
                    t = t / 2 + 8;
                    characterController.getCharacter().setStunDamageMax((int) Math.ceil(t));
                    characterController.getCharacter().setAttributes(attr);
                    setSkillGroups();
                }
            }
        });

        spinnerBod.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerAgi.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerRea.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerStr.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerWil.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerLog.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerInt.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerCha.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerEdg.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerMag.addChangeListener(new AttrPointsChange(this, characterController));
        spinnerRes.addChangeListener(new AttrPointsChange(this, characterController));

        add(displayPanel);
        revalidate();
        repaint();
    }

    private void setQualities() {
        final JPanel displayPanel = new JPanel(new BorderLayout());;
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        clearContents();
        GridBagConstraints constraints = new GridBagConstraints();

        LR.loadQualityList();
        Quality array[] = new Quality[LR.getQualityList().size()];
        Quality chosenArray[] = new Quality[LR.getQualityList().size()];
        JLabel availableQual = new JLabel("Available Qualities");
        JLabel chosenQual = new JLabel("Chosen Qualities");
        final JList<Quality> qualities = new JList<Quality>(LR.getQualityList()
                .toArray(array));

        qualities.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                if (value instanceof Quality) {
                    Quality q = ((Quality) value);
                    setToolTipText(q.getNotes());
                    String text = q.getName();
                    if (q.isLeveled()) {
                        if (q.getLevel() > 0) {
                            text = text + " (" + q.getLevel() + ")";
                        } else {
                            text = text + " (max." + q.getMaxLevel() + ")";
                        }
                    }
                    text = text + " (";
                    if (!q.isPositive()) {
                        text = text + "+";
                    }
                    text = text + q.getAPcost_bonus() + ")";
                    value = text;
                } else {
                    setToolTipText(null);
                }
                return super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
            }
        });

        qualities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Quality> chosenQualities = new JList<Quality>(chosenArray);
        JScrollPane qualPane = new JScrollPane(qualities);
        JScrollPane chosenQualPane = new JScrollPane(chosenQualities);
        JButton addQuality = new JButton("Add");
        JButton removeQuality = new JButton("Remove");
        constraints.weightx = 1;
        constraints.weighty = 0.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(availableQual, constraints);
        constraints.gridy = 1;
        constraints.weightx = 2;
        constraints.weighty = 2.5;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(qualPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 0.0;
        constraints.gridx = 2;
        constraints.gridy = 0;
        panel.add(chosenQual, constraints);
        constraints.gridy = 1;
        constraints.weightx = 2;
        constraints.weighty = 2.5;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(chosenQualPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 1;
        constraints.weighty = 0.0;

		/*constraints.gridx = 1;
		panel.add(addQuality, constraints);
		constraints.gridy += 1;
		panel.add(removeQuality, constraints);*/

        /*
         * constraints.gridy=2; SkillGroupInfo=new JLabel();
         * panel.add(SkillGroupInfo,constraints);
         */
        //constraints.gridy += 1;
        final JSpinner karma = new JSpinner(new SpinnerNumberModel(
                characterController.getCharacter().getPersonalData().getKarma(), 0,
                characterController.getCharacter().getPersonalData().getKarma(), 1));
        karma.setEnabled(false);
        //	panel.add(karma, constraints);

        addQuality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean positive = qualities.getSelectedValue().isPositive();
                boolean allowedToAdd = true;
                if ((positive && characterController.getCharacter().getPersonalData().getKarma()
                        - qualities.getSelectedValue().getAPcost_bonus() >= 0)
                        || !positive) {
                    Quality q = qualities.getSelectedValue();
                    if (q.isLeveled()) {
                        JSpinner level = new JSpinner(new SpinnerNumberModel(1,
                                1, q.getMaxLevel(), 1));
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Set " + q.getName() + " level!"),
                                level};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setLevel((int) level.getValue());
                    }
                    if (q.getName().equals("Aptitude")
                            && !characterController.getCharacter().getQualities().contains(q)) {
                        Vector<Skill> skillList = new Vector<>(LR
                                .getSkillList().size());
                        skillList.addAll(activeSkills);
                        skillList.addAll(knowledgeSkills);
                        if (characterController.getCharacter().getMagicalness().equals(
                                Magical.Magician)
                                || characterController.getCharacter().getMagicalness().equals(
                                Magical.MysticalAdept)) {
                            skillList.addAll(magicSkills);
                        } else if (characterController.getCharacter().getMagicalness().equals(
                                Magical.Technomancer)) {
                            skillList.addAll(resonanceSkills);
                        } else if (characterController.getCharacter().getMagicalness().equals(
                                Magical.AspectedMagician)) {
                            skillList.addAll(characterController.getCharacter()
                                    .getChosenAspectedSkillGroup().getSkills());
                        }
                        JComboBox<Skill> skills = new JComboBox<Skill>(
                                skillList);
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Please choose a skill!"), skills};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setNotes(((Skill) skills.getSelectedItem()).getName());
                    } else if (q.getName().equals("Astral Chameleon")) {
                        if (!(characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                                || characterController.getCharacter().getMagicalness().equals(Magical.MysticalAdept)
                                || characterController.getCharacter().getMagicalness().equals(Magical.AspectedMagician))
                        ) {
                            allowedToAdd = false;
                            JOptionPane.showMessageDialog(panel,
                                    "Only magicians can choose this Quality!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (q.getName().equals("Bilingual")) {
                        JComboBox<Skill> skills = new JComboBox<Skill>();
                        for (Skill s : knowledgeSkills) {
                            if (s.getAttribute().equals(
                                    Skill.Attribute.INTUITION)
                                    && !(s.getName().contains("Street")
                                    || s.getName()
                                    .contains("Interests") || s
                                    .getName().equals("Language"))) {
                                skills.addItem(s);
                            }
                        }
                        JTextField language = new JTextField();
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please choose a second native language!"),
                                skills,
                                new JLabel(
                                        "OR enter a new Language in the field below!"),
                                language};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        Skill lang = null;
                        if (language.getText().isEmpty()) {
                            lang = (Skill) skills.getSelectedItem();
                        } else {
                            lang = new Skill();
                            lang.setName(language.getText());
                            lang.setAttribute(Skill.Attribute.INTUITION);
                            lang.setKnowledge(true);
                        }
                        lang.setValue(14);
                        q.setNotes(lang.getName());
                        if (characterController.getCharacter().getSkills().contains(lang)) {
                            characterController.getCharacter()
                                    .getSkills()
                                    .get(characterController.getCharacter().getSkills().indexOf(
                                            lang)).setValue(14);
                        } else {
                            characterController.getCharacter().addSkill(lang);
                        }
                    } else if (q.getName().equals("Blandness")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(-1);
                    } else if (q.getName().equals("Codeslinger")
                            || q.getName().equals("Codeblock")) {
                        JComboBox<String> actions = new JComboBox<String>();
                        actions.addItem("Brute Force");
                        actions.addItem("Check Overwatch Score");
                        actions.addItem("Controll device");
                        actions.addItem("Crack File");
                        actions.addItem("Crash Program");
                        actions.addItem("Data Spike");
                        actions.addItem("Disarm Data Bomb");
                        actions.addItem("Edit File");
                        actions.addItem("Erase Mark");
                        actions.addItem("Erase Matrix Signature");
                        actions.addItem("Format Device");
                        actions.addItem("Hack on the fly");
                        actions.addItem("Hide");
                        actions.addItem("Jack out");
                        actions.addItem("Jam Signals");
                        actions.addItem("Jump into rigged Device");
                        actions.addItem("Matrix Perception");
                        actions.addItem("Matrix search");
                        actions.addItem("Reboot Device");
                        actions.addItem("Set Data Bomb");
                        actions.addItem("Snoop");
                        actions.addItem("Spoof Command");
                        actions.addItem("Trace Icons");
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Please choose a matrix action!"),
                                actions};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        if (q.getName().equals("Codeslinger")) {
                            q.setNotes("+2 dice pool modifier to "
                                    + (String) actions.getSelectedItem());
                        } else {
                            q.setNotes("-2 dice pool modifier to "
                                    + (String) actions.getSelectedItem());
                        }
                    } else if (q.getName().equals("Exceptional Attribute")) {
                        JComboBox<Attribute> attributes = new JComboBox<Attribute>(
                                Attribute.values());
                        if (characterController.getCharacter().getMagicalness().equals(
                                Magical.Mundane)) {
                            attributes.removeItem(Attribute.MAGIC);
                            attributes.removeItem(Attribute.RESONANCE);
                        } else if (characterController.getCharacter().getMagicalness().equals(
                                Magical.Technomancer)) {
                            attributes.removeItem(Attribute.MAGIC);
                        }
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Please choose an attribute!"),
                                attributes};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        switch ((Attribute) attributes.getSelectedItem()) {
                            case BODY:
                                characterController.getCharacter().getAttributes().setMAXbody(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXbody() + 1);
                                break;
                            case AGILITY:
                                characterController.getCharacter().getAttributes().setMAXagility(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXagility() + 1);
                                break;
                            case REACTION:
                                characterController.getCharacter().getAttributes().setMAXreaction(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXreaction() + 1);
                                break;
                            case STRENGTH:
                                characterController.getCharacter().getAttributes().setMAXstrength(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXstrength() + 1);
                                break;
                            case WILLPOWER:
                                characterController.getCharacter().getAttributes().setMAXwillpower(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXwillpower() + 1);
                                break;
                            case LOGIC:
                                characterController.getCharacter().getAttributes().setMAXlogic(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXlogic() + 1);
                                break;
                            case INTUITION:
                                characterController.getCharacter().getAttributes().setMAXintuition(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXintuition() + 1);
                                break;
                            case CHARISMA:
                                characterController.getCharacter().getAttributes().setMAXcharisma(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXcharisma() + 1);
                                break;
                            case MAGIC:
                                characterController.getCharacter().getAttributes().setMAXmagic(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXmagic() + 1);
                                break;
                            case RESONANCE:
                                characterController.getCharacter().getAttributes().setMAXresonance(
                                        characterController.getCharacter().getAttributes()
                                                .getMAXresonance() + 1);
                                break;
                        }
                    } else if (q.getName().equals("First Impression")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(-1);
                    } else if (q.getName().equals("Home Ground")) {
                        JComboBox<String> choice = new JComboBox<String>();
                        choice.addItem("Astral Acclimation");
                        choice.addItem("You Know A Guy");
                        choice.addItem("Digital Turf");
                        choice.addItem("The Transporter");
                        choice.addItem("On the Lam");
                        choice.addItem("Street Politics");
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Please choose an Home Ground!"),
                                choice};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setNotes((String) choice.getSelectedItem());
                    } else if (q.getName().equals("Indomitable")) {
                        JComboBox<String> choice = new JComboBox<String>();
                        choice.addItem("Mental Limit");
                        choice.addItem("Physical Limit");
                        choice.addItem("Social Limit");
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Please choose limit to increase!"),
                                choice};
                        int lev = q.getLevel();
                        for (; lev > 0; lev--) {
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            if (choice.getSelectedItem().equals("Mental Limit")) {
                                characterController.getCharacter().getAttributes()
                                        .setMentalLimit(
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getMentalLimit() + 1);
                            } else if (choice.getSelectedItem().equals(
                                    "Physical Limit")) {
                                characterController.getCharacter()
                                        .getAttributes()
                                        .setPhysicalLimit(
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getPhysicalLimit() + 1);
                            } else if (choice.getSelectedItem().equals(
                                    "Social Limit")) {
                                characterController.getCharacter().getAttributes()
                                        .setSocialLimit(
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getSocialLimit() + 1);
                            }
                        }
                    } else if (q.getName().equals("Lucky")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(-1);
                        characterController.getCharacter().getAttributes().setEdge(
                                characterController.getCharacter().getAttributes().getEdge() + 1);
                    } else if (q.getName().equals("Magical Resistance")) {
                        if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                            allowedToAdd = false;
                            JOptionPane
                                    .showMessageDialog(
                                            panel,
                                            "This quality can't be chosen by magical characters!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            q.setNotes(q.getLevel()
                                    + " dice pool modifier to Spell Resistance Tests");
                        }
                    } else if (q.getName().equals("High Pain Tolerance")) {
                        q.setNotes("Ignore "
                                + q.getLevel()
                                + " boxes of damage when calculating wound modifiers");
                    } else if (q.getName().equals("Mentor Spirit")) {
                        if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                            class MentorSpirit {
                                String name;
                                String advAll;
                                String advMag;
                                String advAde;
                                String disAdv;

                                private MentorSpirit(String name,
                                                     String advAll, String advMag,
                                                     String advAde, String disAdv) {
                                    this.name = name;
                                    this.advAll = advAll;
                                    this.advMag = advMag;
                                    this.advAde = advAde;
                                    this.disAdv = disAdv;
                                }
                            }

                            MentorSpirit bear = new MentorSpirit(
                                    "Bear",
                                    "+2 d.p.m for test to resist damage (not including Drain)",
                                    "+2 d.p.m. for health spells, preperations and rituals",
                                    "1 free level of Rapid Healing",
                                    "Charisma+Willpower Test if taking physical damage, if failed go berserk for 3 turns -1 turn/hit");
                            MentorSpirit cat = new MentorSpirit(
                                    "Cat",
                                    "+2 d.p.m for Gymnastics or Infiltration Tests",
                                    "+2 d.p.m. for Illusion spells, preperations and rituals",
                                    "2 free levels of Light Body",
                                    "Charisma+Willpower(3) Test at  start of combat, if failed you cannot make an attack tha incapacitates your target until you take Physical damage");
                            MentorSpirit dog = new MentorSpirit(
                                    "Dog",
                                    "+2 d.p.m for Tracking tests",
                                    "+2 d.p.m. for Detection spells, preperations and rituals",
                                    "2 free Improved Sense powers",
                                    "Charisma+Willpower(3) Test, if failed you can never leave someone behind, betray your comrades, or let anyone sacrifice themselves for you");
                            MentorSpirit dragonslayer = new MentorSpirit(
                                    "Dragonslayer",
                                    "+2 d.p.m for one Social Skill of choice",
                                    "+2 d.p.m. for Combat spells, preperations and rituals",
                                    "1 free level of Enhanced Accuracy (skill) and 1 free level of Danger Sense",
                                    "-1 d.p.m on all actions if you break a promise, until you make good on it");
                            MentorSpirit eagle = new MentorSpirit("Eagle",
                                    "+2 d.p.m for Perception Tests",
                                    "+2 d.p.m. for summoning spirits of air",
                                    "1 free level of Combat Sense",
                                    "You get Allergy (pollutants, mild)");
                            MentorSpirit firebringer = new MentorSpirit(
                                    "Fire-Bringer",
                                    "+2 d.p.m for Artisan or Alchemy skill tests",
                                    "+2 d.p.m. for Manipulation spells, preperations and rituals",
                                    "1 free level of Improved Ability on a non-combat skill",
                                    "Charisma+Willpower(3) Test, if failed you can not refuse if sincerely asked for help");
                            MentorSpirit mountain = new MentorSpirit(
                                    "Mountain",
                                    "+2 d.p.m for Survival Tests",
                                    "+2 d.p.m. for Counterspelling Tests and anchored rituals",
                                    "1 free level of Mystic Armor",
                                    "Charisma+Willpower(3) Test if trying to abandon a plan in favor of another one or trying to do anything without a plan, if failed stick to the original plan/make a plan");
                            MentorSpirit rat = new MentorSpirit(
                                    "Rat",
                                    "+2 d.p.m for Sneaking Tests",
                                    "+2 d.p.m. for Alchemy tests when harvesting reagents, and may use reagents of any tradition",
                                    "2 free levels of Natural Immunity",
                                    "Charisma+Willpower(3) Test to not immediately flee or seek cover in combat situations");
                            MentorSpirit raven = new MentorSpirit(
                                    "Raven",
                                    "+2 d.p.m for Con Tests",
                                    "+2 d.p.m. for Manipulation spells, preperations and rituals",
                                    "Free Traceless Walk and 1 level of Voice Control",
                                    "Charisma+Willpower(3) Test to avoid exploiting someones misfortune or pull a trick or prank even if it's to your friends disadvantage");
                            MentorSpirit sea = new MentorSpirit(
                                    "Sea",
                                    "+2 d.p.m for Swimming Tests",
                                    "+2 d.p.m. for summoning spirits of water",
                                    "1 free level of Improved Ability on an athletic skill",
                                    "Charisma+Willpower(3) Test to give away something of your own, or be charitable in any way");
                            MentorSpirit seducer = new MentorSpirit(
                                    "Seducer",
                                    "+2 d.p.m for Con Tests",
                                    "+2 d.p.m. for Illusion spells, preperations and rituals",
                                    "1 free level of Improved Ability on a skill in the Acting or Influence skill group",
                                    "Charisma+Willpower(3) Test to avoid pursuing a vice or indlugence (drugs,BTLs,sex,etc.)");
                            MentorSpirit shark = new MentorSpirit(
                                    "Shark",
                                    "+2 d.p.m for Unarmed Combat tests",
                                    "+2 d.p.m. for Combat spells, preperations and rituals",
                                    "Free Killing Hands",
                                    "Charisma+Willpower Test if taking physical damage, if failed go berserk for 3 turns -1 turn/hit");
                            MentorSpirit snake = new MentorSpirit(
                                    "Snake",
                                    "+2 d.p.m for Arcana tests",
                                    "+2 d.p.m. for Detection spells, preperations and rituals",
                                    "2 free levels of Kinesics",
                                    "Charisma+Willpower(3) Test to avoid pursing secrets or knowledge that few know about when you receive hints fo its existence");
                            MentorSpirit thunderbird = new MentorSpirit(
                                    "Thunderbird",
                                    "+2 d.p.m for Intimidation Tests",
                                    "+2 d.p.m. for summoning spirits of air",
                                    "1 free level of Critical Strike (skill)",
                                    "Charisma+Willpower(3) Test to avoid responding to an insult in kind");
                            MentorSpirit wisewarrior = new MentorSpirit(
                                    "Wise Warrior",
                                    "+2 d.p.m for Leadership or Instruction Tests",
                                    "+2 d.p.m. for Combat spells, preperations and rituals",
                                    "1 free level of Improved Ability on a Combat skill",
                                    "-1 d.p.m on all actions if you act dishonorably or without courtesy, until you atone for your behavior");
                            MentorSpirit wolf = new MentorSpirit(
                                    "Wolf",
                                    "+2 d.p.m for Tracking Tests",
                                    "+2 d.p.m. for Combat spells, preperations and rituals",
                                    "2 free level of Attribute Boost(Agility)",
                                    "Charisma+Willpower(3) Test to retreat from a fight");

                            MentorSpirit spirits[] = {bear, cat, dog,
                                    dragonslayer, eagle, firebringer, mountain,
                                    rat, raven, sea, seducer, shark, snake,
                                    thunderbird, wisewarrior, wolf};
                            JComboBox<MentorSpirit> choice = new JComboBox<MentorSpirit>(
                                    spirits);
                            choice.setRenderer(new DefaultListCellRenderer() {
                                @Override
                                public Component getListCellRendererComponent(
                                        JList<?> list, Object value, int index,
                                        boolean isSelected, boolean cellHasFocus) {
                                    if (value instanceof MentorSpirit) {
                                        MentorSpirit m = ((MentorSpirit) value);
                                        String text = "<html><p>All: "
                                                + m.advAll + "</p>";
                                        if (characterController.getCharacter().getMagicalness()
                                                .equals(Magical.Magician)) {
                                            text += "<p>Magician: " + m.advMag
                                                    + "</p>";
                                        } else if (characterController.getCharacter()
                                                .getMagicalness().equals(
                                                        Magical.Adept)) {
                                            text += "<p>Adept: " + m.advAde
                                                    + "</p>";
                                        } else if (characterController.getCharacter()
                                                .getMagicalness().equals(
                                                        Magical.MysticalAdept)) {
                                            text += "<p>Magician: " + m.advMag
                                                    + "</p>";
                                            text += "<p>Adept: " + m.advAde
                                                    + "</p>";
                                        }
                                        text += "<p>Disadvantage: " + m.disAdv
                                                + "</p></html>";
                                        setToolTipText(text);
                                        value = m.name;
                                    } else {
                                        setToolTipText(null);
                                    }
                                    return super.getListCellRendererComponent(
                                            list, value, index, isSelected,
                                            cellHasFocus);
                                }
                            });

                            JComponent[] inputs = new JComponent[]{
                                    new JLabel("Please choose a mentor spirit!"),
                                    choice};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            String chosen = ((MentorSpirit) choice
                                    .getSelectedItem()).name;
                            MentorSpirit chosenSpirit = null;
                            // handle powers when choosing them!
                            if (chosen.equals("Bear")) {
                                chosenSpirit = bear;
                            } else if (chosen.equals("Cat")) {
                                chosenSpirit = cat;
                                JComboBox<String> skills = new JComboBox<String>(
                                        new String[]{"Gymnastics",
                                                "Infiltration"});
                                inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose which skill you want +2 dice for!"),
                                        skills};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                chosenSpirit.advAll = "+2 d.p.m for "
                                        + (String) skills.getSelectedItem()
                                        + " Tests";
                            } else if (chosen.equals("Dog")) {
                                chosenSpirit = dog;
                            } else if (chosen.equals("Dragonslayer")) {
                                chosenSpirit = dragonslayer;
                                JComboBox<Skill> skills = new JComboBox<Skill>();
                                for (Skill s : activeSkills) {
                                    if (s.getAttribute().equals(
                                            Skill.Attribute.CHARISMA)
                                            && !s.getName().equals(
                                            "Animal Handling")) {
                                        skills.addItem(s);
                                    }
                                }
                                inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose which skill you want +2 dice for!"),
                                        skills};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                chosenSpirit.advAll = "+2 d.p.m for "
                                        + ((Skill) skills.getSelectedItem())
                                        .getName();
                            } else if (chosen.equals("Eagle")) {
                                chosenSpirit = eagle;
                                Quality allergy = new Quality();
                                allergy.setName("Allergy (Pollutants)(Mild)");
                                allergy.setNotes("-2 d.p.m. to Physical Tests while experiencing symptoms");
                                allergy.setPositive(false);
                                allergy.setLeveled(false);
                                allergy.setAPcost_bonus(0);
                                characterController.getCharacter().addQuality(allergy);
                            } else if (chosen.equals("Fire-Bringer")) {
                                chosenSpirit = firebringer;
                                JComboBox<String> skills = new JComboBox<String>(
                                        new String[]{"Artisan", "Alchemy"});
                                inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose which skill you want +2 dice for!"),
                                        skills};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                chosenSpirit.advAll = "+2 d.p.m for "
                                        + (String) skills.getSelectedItem()
                                        + " Tests";
                            } else if (chosen.equals("Mountain")) {
                                chosenSpirit = mountain;
                            } else if (chosen.equals("Rat")) {
                                chosenSpirit = rat;
                            } else if (chosen.equals("Raven")) {
                                chosenSpirit = raven;
                            } else if (chosen.equals("Sea")) {
                                chosenSpirit = sea;
                            } else if (chosen.equals("Seducer")) {
                                chosenSpirit = seducer;
                            } else if (chosen.equals("Shark")) {
                                chosenSpirit = shark;
                            } else if (chosen.equals("Snake")) {
                                chosenSpirit = snake;
                            } else if (chosen.equals("Thunderbird")) {
                                chosenSpirit = thunderbird;
                            } else if (chosen.equals("Wise Warrior")) {
                                chosenSpirit = wisewarrior;
                                JComboBox<String> skills = new JComboBox<String>(
                                        new String[]{"Leadership",
                                                "Instruction"});
                                inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose which skill you want +2 dice for!"),
                                        skills};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                chosenSpirit.advAll = "+2 d.p.m for "
                                        + (String) skills.getSelectedItem()
                                        + " Tests";
                            } else if (chosen.equals("Wolf")) {
                                chosenSpirit = wolf;
                            }
                            if (characterController.getCharacter().getMagicalness().equals(
                                    Magical.Magician)) {
                                q.setName("Mentor Spirit: " + chosenSpirit.name);
                                q.setNotes(chosenSpirit.advAll + ", "
                                        + chosenSpirit.advMag + ", "
                                        + chosenSpirit.disAdv);
                            } else if (characterController.getCharacter().getMagicalness()
                                    .equals(Magical.Adept)) {
                                q.setName("Mentor Spirit: " + chosenSpirit.name);
                                q.setNotes(chosenSpirit.advAll + ", "
                                        + chosenSpirit.advAde + ", "
                                        + chosenSpirit.disAdv);
                            } else if (characterController.getCharacter().getMagicalness()
                                    .equals(Magical.MysticalAdept)) {
                                q.setName("Mentor Spirit: " + chosenSpirit.name);
                                int index = JOptionPane
                                        .showOptionDialog(
                                                panel,
                                                "Please choose if you wish to take the Magician or Adept advantage",
                                                "Magician or Adept",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null, new String[]{
                                                        "Magician", "Adept"},
                                                0);
                                if (index == 0) {
                                    q.setNotes(chosenSpirit.advAll + ", "
                                            + chosenSpirit.advMag + ", "
                                            + chosenSpirit.disAdv);
                                } else {
                                    q.setNotes(chosenSpirit.advAll + ", "
                                            + chosenSpirit.advAde + ", "
                                            + chosenSpirit.disAdv);
                                }
                            }
                        } else {
                            allowedToAdd = false;
                            JOptionPane
                                    .showMessageDialog(
                                            panel,
                                            "This quality can only be chosen by magical characters!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (q.getName().contains("Natural Immunity")) {
                        JTextField disease = new JTextField();
                        String label = null;
                        if (q.getName().contains("(natural")) {
                            label = "Please enter the name of the natural disease or toxin you want immunity against!";
                        } else {
                            label = "Please enter the name of the synthetic disease or toxin you want immunity against!";
                        }
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(label), disease};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setName("Natural Immunity (" + disease.getText()
                                + ")");
                    } else if (q.getName().equals("Spirit Affinity")
                            || q.getName().equals("Spirit Bane")) {
                        if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                            JComboBox<String> choice = new JComboBox<String>();
                            choice.addItem("Spirits of Air");
                            choice.addItem("Spirits of Beasts");
                            choice.addItem("Spirits of Earth");
                            choice.addItem("Spirits of Fire");
                            choice.addItem("Spirits of Man");
                            choice.addItem("Spirits of Water");
                            final JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose a type of Spirit!"),
                                    choice};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            if (q.getName().equals("Spirit Affinity")) {
                                q.setNotes("1 additional service for each "
                                        + (String) choice.getSelectedItem()
                                        + ". +1 d.p.m. for Binding tests");
                            } else {
                                characterController.getCharacter().getPersonalData().addNotoriety(1);
                                q.setNotes("-2 d.p.m. for Summoning or Binding "
                                        + (String) choice.getSelectedItem()
                                        + ". +2 d.p.m. for Spirit when trying to Banish");
                            }
                        } else {
                            allowedToAdd = false;
                            JOptionPane
                                    .showMessageDialog(
                                            panel,
                                            "This quality can only be chosen by magic users!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (q.getName().equals("Will to Live")) {
                        q.setNotes("+" + q.getLevel()
                                + " Damage Overflow Box(es)");
                        characterController.getCharacter().setDamageOverflow(characterController.getCharacter()
                                .getDamageOverflow() + q.getLevel());
                    } else if (q.getName().contains("Addiction")) {
                        JTextField addiction = new JTextField();
                        String label = "Please enter what drug or activity your character is addicted to!";
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(label), addiction};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        String kind = null;
                        if (q.getName().contains("(Mild)")) {
                            kind = "(Mild)";
                        } else if (q.getName().contains("(Moderate)")) {
                            kind = "(Moderate)";
                        } else if (q.getName().contains("(Severe)")) {
                            kind = "(Severe)";
                        } else if (q.getName().contains("(Burnout)")) {
                            kind = "(Burnout)";
                        }
                        q.setName("Addiction (" + addiction.getText() + ")"
                                + kind);
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().contains("Allergy")) {
                        JTextField allergy = new JTextField();
                        String environment = null;
                        String examples = null;
                        if (q.getName().contains("(Uncommon)")) {
                            environment = "rare for";
                            examples = "silver, gold, antibiotics, grass.";
                        } else {
                            environment = "prevalent in";
                            examples = "sunlight, seafood, bees, pollen, pollutants, Wi-Fi sensitivity, soy, wheat";
                        }
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please enter what your character is allergic to!"),
                                new JLabel("The sustance or condition is "
                                        + environment
                                        + " the local environment. Examples: "
                                        + examples), allergy};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        String kind = null;
                        if (q.getName().contains("(Mild)")) {
                            kind = "(Mild)";
                        } else if (q.getName().contains("(Moderate)")) {
                            kind = "(Moderate)";
                        } else if (q.getName().contains("(Severe)")) {
                            kind = "(Severe)";
                        } else if (q.getName().contains("(Extreme)")) {
                            kind = "(Extreme)";
                        }
                        q.setName("Allergy (" + allergy.getText() + ")" + kind);
                    } else if (q.getName().equals("Astral Beacon")
                            && !(characterController.getCharacter().getAttributes().getMagic() > 0)) {
                        allowedToAdd = false;
                        JOptionPane
                                .showMessageDialog(
                                        panel,
                                        "This quality can only be chosen by magic users!",
                                        "Error", JOptionPane.ERROR_MESSAGE);

                    } else if (q.getName().equals("Bad Luck")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().equals("Bad Rep")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(3);
                    } else if (q.getName().contains("Code of Honor")) {
                        JTextField code = new JTextField();
                        String label = "Please enter what group your character's code protects/what creed your character follows!";
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(label), code};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setNotes("Must make a Charisma+Willpower (4) Test when attempting to kill a "
                                + code + "/breaking the " + code);
                    } else if (q.getName().equals("Combat Paralysis")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().equals("Elf Poser")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().equals("Gremlins")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                        q.setNotes(q.getLevel()
                                + " less rolled 1s needed to get a glitch (per level) when using a moderatly sophisticated device.");
                    } else if (q.getName().equals("Incompetent")) {
                        JComboBox<SkillGroup> skillgroup = new JComboBox<SkillGroup>();
                        for (SkillGroup sg : LR.getSkillGroupList()) {
                            if (sg.getName().equals("TASKING")) {
                                if (characterController.getCharacter().getMagicalness().equals(
                                        Magical.Technomancer)) {
                                    skillgroup.addItem(sg);
                                }
                            } else if (sg.getName().equals("CONJURING")
                                    || sg.getName().equals("ENCHANTING")
                                    || sg.getName().equals("SORCERY")) {
                                if (characterController.getCharacter().getMagicalness().equals(
                                        Magical.Magician)
                                        || characterController.getCharacter().getMagicalness()
                                        .equals(Magical.MysticalAdept)
                                        || (characterController.getCharacter()
                                        .getMagicalness()
                                        .equals(Magical.AspectedMagician) && characterController.getCharacter()
                                        .getChosenAspectedSkillGroup()
                                        .equals(sg))) {
                                    skillgroup.addItem(sg);
                                }
                            } else {
                                skillgroup.addItem(sg);
                            }
                        }

                        final JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please choose the skillgroup your character is incompetent at!"),
                                skillgroup};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        SkillGroup selection = (SkillGroup) skillgroup
                                .getSelectedItem();
                        q.setNotes("Incompetent in " + selection.getName());
                        if (characterController.getCharacter().getSkillGroups().contains(
                                selection)) {
                            characterController.getCharacter()
                                    .getSkillGroups()
                                    .get(characterController.getCharacter().getSkillGroups()
                                            .indexOf(selection)).setValue(-1);
                            ;
                        } else {
                            selection.setValue(-1);
                            characterController.getCharacter().addSkillGroup(selection);
                        }
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().equals("Loss of Confidence")) {
                        JComboBox<Skill> skills = new JComboBox<Skill>();
                        for (Skill s : characterController.getCharacter().getSkills()) {
                            if (s.getValue() >= 4) {
                                skills.addItem(s);
                            }
                        }
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please choose the skill your character lost confidence in!"),
                                skills};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        Skill selection = (Skill) skills.getSelectedItem();
                        q.setNotes("-2 dice pool modificator to "
                                + selection.getName()
                                + ". Skill specialization not useable.");
                    } else if (q.getName().equals("Ork Poser")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().contains("Prejudiced")) {
                        JTextField prejudice = new JTextField();
                        String group = null;
                        String label = null;
                        if (q.getName().contains("(Common")) {
                            group = "common";
                            label = "e.g. humans, metahumans";
                        } else {
                            group = "specific";
                            label = "e.g. the Awakened, technomancers, shapeshifters, aspected magicians";
                        }
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please insert the "
                                                + group
                                                + " target group of your characters prejudice:"),
                                new JLabel(label), prejudice};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        String target = prejudice.getText();
                        if (q.getName().contains("(Biased)")) {
                            q.setName("Prejudiced (" + target + ")(Biased)");
                        } else if (q.getName().contains("(Outspoken)")) {
                            q.setName("Prejudiced (" + target + ")(Outspoken)");
                        } else {
                            q.setName("Prejudiced (" + target + ")(Radical)");
                        }
                    } else if (q.getName().equals("Scorched")) {
                        JComboBox<String> type = new JComboBox<String>();
                        JComboBox<String> effect = new JComboBox<String>();
                        type.addItem("BTL");
                        type.addItem("Black IC");
                        type.addItem("Psychotropic IC");
                        effect.addItem("Memory Loss (short term)");
                        effect.addItem("Memory Loss (long term)");
                        effect.addItem("Blackout");
                        effect.addItem("Migraines");
                        effect.addItem("Paranoia/Anxiety");
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please choose what scorched your character:"),
                                type,
                                new JLabel("Please choose the side effects:"),
                                effect};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        String typeText = (String) type.getSelectedItem();
                        String effectText = (String) effect.getSelectedItem();
                        if (typeText.equals("BTL")) {
                            if (0 != JOptionPane
                                    .showConfirmDialog(
                                            panel,
                                            "Have you allready chosen at least a Mild Addiction to BTL's for your character?",
                                            "Needs Addiction",
                                            JOptionPane.NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE)) {
                                Quality btl = new Quality();
                                btl.setName("Addiction (BTL)(Mild)");
                                btl.setNotes("1 dose/1 hour of activity once a month");
                                btl.setLeveled(false);
                                btl.setPositive(false);
                                btl.setAPcost_bonus(4);

                                characterController.getCharacter().getPersonalData().setKarma(
                                        characterController.getCharacter().getPersonalData()
                                                .getKarma()
                                                + btl.getAPcost_bonus());
                                characterController.getCharacter().addQuality(btl);
                                Quality array[] = new Quality[characterController.getCharacter()
                                        .getQualities().size()];
                                int i = 0;
                                for (Quality qua : characterController.getCharacter()
                                        .getQualities()) {
                                    array[i] = qua;
                                    i++;
                                }
                                chosenQualities.setListData(array);
                                karma.setValue(characterController.getCharacter()
                                        .getPersonalData().getKarma());
                            }
                        }
                        // char has to buy a deck if not a technomancer
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                        q.setName("Scorched (" + typeText + ")");
                        q.setNotes("Must pass a Body+Willpower(4) test not to suffer "
                                + effectText);
                    } else if (q.getName().equals("Sensitive System")) {
                        if (characterController.getCharacter().getMagicalness().equals(
                                Magical.AspectedMagician)
                                || characterController.getCharacter().getMagicalness().equals(
                                Magical.Magician) || characterController.getCharacter().getMagicalness().equals(Magical.MysticalAdept)
                        ) {
                            q.setNotes("Must make a Willpower(2) test before any Drain Tests. If failed +2 to Drain");
                        } else if (characterController.getCharacter().getMagicalness().equals(
                                Magical.Technomancer)) {
                            q.setNotes("Must make a Willpower(2) test before any Fading Tests. If failed +2 to Fading");
                        } else {
                            q.setNotes("Essence loss x2, Bioware rejected");
                            // resolve when buying cyber-/bioware
                        }
                    } else if (q.getName().equals("SINner (Criminal)")) {
                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                    } else if (q.getName().equals("Social Stress")) {
                        JTextField cause = new JTextField();
                        JTextField trigger = new JTextField();
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please enter a specific cause for your characters Social Stress:"),
                                cause,
                                new JLabel(
                                        "Please enter what triggers your characters Social Stress:"),
                                trigger};
                        JOptionPane.showMessageDialog(panel, inputs,
                                q.getName(), JOptionPane.QUESTION_MESSAGE);
                        q.setNotes("Cause: "
                                + cause.getText()
                                + "If triggered by "
                                + trigger
                                + ": Number of 1s for a glitch reduced by 1 when using Leadership or Ettiquette skills");
                    } else if (q.getName().equals("Weak Immune System")) {
                        int pathoIndex = -1;
                        int toxinIndex = -1;
                        for (Quality qua : characterController.getCharacter().getQualities()) {
                            if (qua.getName()
                                    .equals("Resistance to pathogenes")) {
                                pathoIndex = characterController.getCharacter().getQualities()
                                        .indexOf(qua);
                            } else if (qua.getName().equals(
                                    "Resistance to toxins")) {
                                toxinIndex = characterController.getCharacter().getQualities()
                                        .indexOf(qua);
                            }
                        }

                        if (pathoIndex > -1 || toxinIndex > -1) {
                            int index = JOptionPane
                                    .showOptionDialog(
                                            panel,
                                            "This quality can't be combined with Resistance qualities! Remove this quality or the Resistance quality?",
                                            "Error",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.ERROR_MESSAGE,
                                            null,
                                            new String[]{"Remove this",
                                                    "Remove Resistance Quality"},
                                            0);
                            if (index == 0) {
                                allowedToAdd = false;
                            } else {
                                characterController.getCharacter().getPersonalData().addNotoriety(1);
                                if (pathoIndex > -1) {
                                    Quality qua = characterController.getCharacter()
                                            .getQualities().get(pathoIndex);
                                    characterController.getCharacter()
                                            .getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - qua.getAPcost_bonus());
                                    characterController.getCharacter().removeQuality(qua);
                                }
                                if (toxinIndex > -1) {
                                    Quality qua = characterController.getCharacter()
                                            .getQualities().get(toxinIndex);
                                    characterController.getCharacter()
                                            .getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - qua.getAPcost_bonus());
                                    characterController.getCharacter().removeQuality(qua);
                                }
                                Quality array[] = new Quality[characterController.getCharacter()
                                        .getQualities().size()];
                                int i = 0;
                                for (Quality qual : characterController.getCharacter()
                                        .getQualities()) {
                                    array[i] = qual;
                                    i++;
                                }
                                chosenQualities.setListData(array);
                                karma.setValue(characterController.getCharacter()
                                        .getPersonalData().getKarma());
                                chosenQualities.revalidate();
                                chosenQualities.repaint();
                            }
                        }
                    } else if (q.getName().equals("Resistance to pathogenes")
                            || q.getName().equals("Resistance to toxins")) {
                        int weakImmuIndex = -1;
                        for (Quality qua : characterController.getCharacter().getQualities()) {
                            if (qua.getName().equals("Weak Immune System")) {
                                weakImmuIndex = characterController.getCharacter().getQualities()
                                        .indexOf(qua);
                            }
                        }

                        if (weakImmuIndex > -1) {
                            int index = JOptionPane
                                    .showOptionDialog(
                                            panel,
                                            "This quality can't be combined with the Weak Immune System quality! Remove this quality or the Weak Immune System quality?",
                                            "Error",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.ERROR_MESSAGE,
                                            null,
                                            new String[]{"Remove this",
                                                    "Remove Weak Immune System Quality"},
                                            0);
                            if (index == 0) {
                                allowedToAdd = false;
                            } else {
                                Quality qua = characterController.getCharacter().getQualities()
                                        .get(weakImmuIndex);
                                characterController.getCharacter().getPersonalData().setKarma(
                                        characterController.getCharacter().getPersonalData()
                                                .getKarma()
                                                - qua.getAPcost_bonus());
                                characterController.getCharacter().removeQuality(qua);
                                Quality array[] = new Quality[characterController.getCharacter()
                                        .getQualities().size()];
                                int i = 0;
                                for (Quality qual : characterController.getCharacter()
                                        .getQualities()) {
                                    array[i] = qual;
                                    i++;
                                }
                                chosenQualities.setListData(array);
                                karma.setValue(characterController.getCharacter()
                                        .getPersonalData().getKarma());
                                chosenQualities.revalidate();
                                chosenQualities.repaint();
                            }
                        }
                    }
                    //TODO ADD UNCOUTH AND UNEDUCATED AND BUILD INTO FURTHER SKILL LEVELING
                    // if Uncouth - 2x cost for social skills, no social skill
                    // groups!, all skills below rating 1 are unaware
                    //characterController.getCharacter().getPersonalData().addNotoriety(1);
                    // if Uneducated - 2x cost for technical, aca know, prof
                    // know
                    //characterController.getCharacter().getPersonalData().addNotoriety(1);

                    if (allowedToAdd) {
                        characterController.getCharacter().getPersonalData().setKarma(
                                characterController.getCharacter().getPersonalData().getKarma()
                                        + q.getAPcost_bonus());
                        characterController.getCharacter().addQuality(qualities
                                .getSelectedValue());
                        Quality array[] = new Quality[characterController.getCharacter()
                                .getQualities().size()];
                        int i = 0;
                        for (Quality qua : characterController.getCharacter().getQualities()) {
                            array[i] = qua;
                            i++;
                        }
                        chosenQualities.setListData(array);
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        panel.revalidate();
                        panel.repaint();
                    }
                }
            }
        });

        removeQuality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                boolean positive = chosenQualities.getSelectedValue()
                        .isPositive();
                if ((!positive && chosenQualities.getSelectedValue()
                        .getAPcost_bonus() <= characterController.getCharacter()
                        .getPersonalData().getKarma())
                        || positive) {
                    Quality q = chosenQualities.getSelectedValue();
                    characterController.getCharacter().getPersonalData().setKarma(
                            characterController.getCharacter().getPersonalData().getKarma()
                                    - q.getAPcost_bonus());
                    characterController.getCharacter().removeQuality(q);
                    Quality array[] = new Quality[characterController.getCharacter()
                            .getQualities().size()];
                    int i = 0;
                    for (Quality qua : characterController.getCharacter().getQualities()) {
                        array[i] = qua;
                        i++;
                    }
                    chosenQualities.setListData(array);
                    karma.setValue(characterController.getCharacter().getPersonalData()
                            .getKarma());
                    chosenQualities.revalidate();
                    chosenQualities.repaint();
                }
            }
        });

		/*JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
				+ " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
				+ characterController.getCharacter().getPersonalData().getMetatype());

		info.setHorizontalAlignment(JLabel.CENTER);
		info.setVerticalAlignment(JLabel.CENTER);*/

        JPanel iPanel = new JPanel();
        //iPanel.add(info);
        iPanel.add(new JLabel("Karma"));
        iPanel.add(karma);
        iPanel.add(addQuality);
        iPanel.add(removeQuality);

        JButton next = new JButton("Next");

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSkills(false);
            }
        });

        infoPanel.add(iPanel, BorderLayout.CENTER);
        infoPanel.add(next, BorderLayout.EAST);
        infoPanel.add(back, BorderLayout.WEST);

        displayPanel.add(panel, BorderLayout.CENTER);
        displayPanel.add(infoPanel, BorderLayout.SOUTH);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (characterController.getCharacter().getPersonalData().getKarma() >= 0) {
                    chooseGear();
                } else {
                    JOptionPane.showMessageDialog(panel,
                            "You can't spend that much karma!",
                            "Can't progress", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(displayPanel);
        revalidate();
        repaint();

    }

    private void setSkillGroups() {
        final JPanel displayPanel = new JPanel(new BorderLayout());;
        clearContents();
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        this.LR.loadSkillGroupList();
        prevValues = new int[LR.getSkillGroupList().size()];

        ArrayList<SkillGroup> magicalGroups = new ArrayList<SkillGroup>();

        for (SkillGroup sg : LR.getSkillGroupList()) {
            prevValues[LR.getSkillGroupList().indexOf(sg)] = 0;
            if (sg.getName().equals("CONJURING")
                    || sg.getName().equals("ENCHANTING")
                    || sg.getName().equals("SORCERY")) {
                magicalGroups.add(sg);
            } else if (sg.getName().equals("TASKING")) {
                tasking = sg;
            }
        }
        // IF ASPECTED WIZARD; THEN CHOOSE SKILL GROUP
        /*
         * [][0]..Magician/Wizardadept [][1]..Technomancer [][2]--Adept
         * [][3]..Aspectwizard MagicResonancePriority(MagicResonance,
         * numOfMagicSkills, valueOfMagicSkills, numOfSkills, valueOfSkills,
         * numOfMagicSkillGroups, valueOfMagicSkillGroups,
         * numOfSpellsComplexForms)
         */
        if (characterController.getCharacter().getMagicalness().equals(Magical.AspectedMagician)) {
            int index = JOptionPane.showOptionDialog(panel,
                    "Please choose your magic skill group!",
                    "Aspected Magician", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    magicalGroups.toArray(), 0);
            characterController.getCharacter().setChosenAspectedSkillGroup(magicalGroups
                    .get(index));
        }

        // PLACED SPINNERS VARIANT
        int skMax = 6;
        if (!characterController.getCharacter().getQualities().isEmpty()) {
            for (Quality q : characterController.getCharacter().getQualities()) {
                if (q.getName().equals("Aptitude")) {
                    skMax = 7;
                }
            }
        }
        constraints.gridx = 0;
        constraints.gridy = 0;
        int amount = 0;
        for (SkillGroup sg : LR.getSkillGroupList()) {
            if (!magicalGroups.contains(sg) && !sg.equals(tasking)) {
                constraints.gridwidth = 2;
                JLabel group = new JLabel(sg.getName());
                panel.add(group, constraints);
                constraints.gridx += 2;
                constraints.gridwidth = 1;
                JSpinner groupSp = new JSpinner(new SpinnerNumberModel(0, 0,
                        skMax, 1));
                groupSp.setName(sg.getName());
                // TO DO: add Listener to spinner
                groupSp.addChangeListener(new SkillGroupChange(this));
                group.setName(sg.getName() + "Label");
                panel.add(groupSp, constraints);
                for (Skill s : sg.getSkills()) {
                    constraints.gridy += 1;
                    constraints.gridx -= 1;
                    JLabel skill = new JLabel(s.getName() + " "
                            + s.getAttributeShorthand() + " ");
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp = new JSpinner(new SpinnerNumberModel(0,
                            0, skMax, 1));
                    skillSp.setName(sg.getName() + "Skill");
                    skillSp.setEnabled(false);
                    panel.add(skillSp, constraints);
                }
                constraints.gridy += 1;
                panel.add(new JLabel(" "), constraints);
                if (sg.getSkills().size() < 4) {
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                }
                if (amount == 3) {
                    amount = 0;
                    constraints.gridx += 1;
                    constraints.gridy = 0;
                    panel.add(new JLabel("   "), constraints);
                    constraints.gridy = 1;
                    panel.add(new JLabel("   "), constraints);
                    constraints.gridy = 2;
                    panel.add(new JLabel("   "), constraints);
                    constraints.gridy = 3;
                    panel.add(new JLabel("   "), constraints);
                    constraints.gridx += 1;
                    constraints.gridy = 0;
                } else {
                    amount++;
                    constraints.gridx -= 2;
                    constraints.gridy += 2;
                }
            }
        }
        System.out.println(constraints.gridx + " - " + constraints.gridy);
        if (!characterController.getCharacter().getMagicalness().equals(Magical.Adept)
                && !characterController.getCharacter().getMagicalness().equals(Magical.Mundane)) {
            constraints.gridx += 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            panel.add(new JLabel("   "), constraints);
            constraints.gridy = 1;
            panel.add(new JLabel("   "), constraints);
            constraints.gridy = 2;
            panel.add(new JLabel("   "), constraints);
            constraints.gridy = 3;
            panel.add(new JLabel("   "), constraints);
            constraints.gridx += 1;
            constraints.gridy = 0;
            if (characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                    || characterController.getCharacter().getMagicalness().equals(
                    Magical.MysticalAdept)) {
                for (SkillGroup sg : magicalGroups) {

                    constraints.gridwidth = 2;
                    JLabel group = new JLabel(sg.getName());
                    panel.add(group, constraints);
                    constraints.gridx += 2;
                    constraints.gridwidth = 1;
                    JSpinner groupSp = new JSpinner(new SpinnerNumberModel(0,
                            0, skMax, 1));
                    groupSp.setName(sg.getName());
                    // TO DO: add Listener to spinner
                    groupSp.addChangeListener(new SkillGroupChange(this));
                    group.setName(group.getText() + "Label");
                    panel.add(groupSp, constraints);
                    for (Skill s : sg.getSkills()) {
                        constraints.gridy += 1;
                        constraints.gridx -= 1;
                        JLabel skill = new JLabel(s.getName() + " "
                                + s.getAttributeShorthand() + " ");
                        skill.setName(skill.getText() + "Label");
                        panel.add(skill, constraints);
                        constraints.gridx += 1;
                        JSpinner skillSp = new JSpinner(new SpinnerNumberModel(
                                0, 0, skMax, 1));
                        skillSp.setName(sg.getName() + "Skill");
                        skillSp.setEnabled(false);
                        panel.add(skillSp, constraints);
                    }
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);

                    constraints.gridx -= 2;
                }

            } else if (characterController.getCharacter().getMagicalness().equals(
                    Magical.Technomancer)) {

                constraints.gridwidth = 2;
                JLabel group = new JLabel(tasking.getName());
                panel.add(group, constraints);
                constraints.gridx += 2;
                constraints.gridwidth = 1;
                JSpinner groupSp = new JSpinner(new SpinnerNumberModel(0, 0,
                        skMax, 1));
                groupSp.setName(tasking.getName());
                // TO DO: add Listener to spinner
                groupSp.addChangeListener(new SkillGroupChange(this));
                group.setName(group.getText() + "Label");
                panel.add(groupSp, constraints);
                for (Skill s : tasking.getSkills()) {
                    constraints.gridy += 1;
                    constraints.gridx -= 1;
                    JLabel skill = new JLabel(s.getName() + " "
                            + s.getAttributeShorthand() + " ");
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp = new JSpinner(new SpinnerNumberModel(0,
                            0, skMax, 1));
                    skillSp.setName(tasking.getName() + "Skill");
                    skillSp.setEnabled(false);
                    panel.add(skillSp, constraints);
                }
                constraints.gridy += 1;
                panel.add(new JLabel(" "), constraints);
                if (tasking.getSkills().size() < 4) {
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                }

            } else if (characterController.getCharacter().getMagicalness().equals(
                    Magical.AspectedMagician)) {
                for (SkillGroup sg : magicalGroups) {
                    if (sg.getName().equals(
                            characterController.getCharacter().getChosenAspectedSkillGroup()
                                    .getName())) {
                        constraints.gridwidth = 2;
                        JLabel group = new JLabel(sg.getName());
                        panel.add(group, constraints);
                        constraints.gridx += 2;
                        constraints.gridwidth = 1;
                        JSpinner groupSp = new JSpinner(new SpinnerNumberModel(
                                charMagicResonance[3].valueOfMagicSkillGroups,
                                charMagicResonance[3].valueOfMagicSkillGroups,
                                skMax, 1));
                        prevValues[LR.getSkillGroupList().indexOf(sg)] = charMagicResonance[3].valueOfMagicSkillGroups;
                        groupSp.setName(sg.getName());

                        groupSp.addChangeListener(new SkillGroupChange(this));
                        group.setName(group.getText() + "Label");

                        panel.add(groupSp, constraints);
                        for (Skill s : sg.getSkills()) {
                            constraints.gridy += 1;
                            constraints.gridx -= 1;
                            JLabel skill = new JLabel(s.getName() + " "
                                    + s.getAttributeShorthand() + " ");
                            skill.setName(skill.getText() + "Label");
                            panel.add(skill, constraints);
                            constraints.gridx += 1;
                            JSpinner skillSp = new JSpinner(
                                    new SpinnerNumberModel(
                                            charMagicResonance[3].valueOfMagicSkillGroups,
                                            charMagicResonance[3].valueOfMagicSkillGroups,
                                            skMax, 1));
                            skillSp.setName(sg.getName() + "Skill");
                            // System.out.println(skillSp.getName());
                            skillSp.setEnabled(false);

                            panel.add(skillSp, constraints);
                        }
                        constraints.gridy += 1;
                        panel.add(new JLabel(" "), constraints);
                        if (sg.getSkills().size() < 4) {
                            constraints.gridy += 1;
                            panel.add(new JLabel(" "), constraints);
                        }
                    }
                }
            }
        }


        atr = new JSpinner(new SpinnerNumberModel(charSkillGroups, 0,
                charSkillGroups, 1));
        atr.setEnabled(false);

		/*JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
				+ " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
				+ characterController.getCharacter().getPersonalData().getMetatype());

		info.setHorizontalAlignment(JLabel.CENTER);
		info.setVerticalAlignment(JLabel.CENTER);*/

        JPanel iPanel = new JPanel();
        //iPanel.add(info);
        iPanel.add(new JLabel("Skill Group Pts"));
        iPanel.add(atr);

        JButton next = new JButton("Next");

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAttributes();
            }
        });

        infoPanel.add(iPanel, BorderLayout.CENTER);
        infoPanel.add(next, BorderLayout.EAST);
        infoPanel.add(back, BorderLayout.WEST);

        displayPanel.add(panel, BorderLayout.CENTER);
        displayPanel.add(infoPanel, BorderLayout.SOUTH);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (java.awt.Component comp : panel.getComponents()) {
                    if (comp.getName() != null) {
                        for (SkillGroup sg : LR.getSkillGroupList()) {
                            if (comp.getName().equals(sg.getName())) {
                                if ((int) ((JSpinner) comp).getValue() > 0 || (characterController.getCharacter().getMagicalness().equals(Magical.AspectedMagician) && (comp.getName().equals("CONJURING") || comp.getName().equals("ENCHANTING") || comp.getName().equals("SORCERY")))) {
                                    System.out.println("ADDED: " + sg.getName());
                                    sg.setValue((int) ((JSpinner) comp)
                                            .getValue());
                                    characterController.getCharacter().addSkillGroup(sg);
                                    System.out.println(characterController.getCharacter()
                                            .getSkillGroups().isEmpty());
                                }
                            }
                        }
                    }
                }
                setSkills(true);
            }
        });

        add(displayPanel);
        revalidate();
        repaint();
    }

    private void setSkills(boolean firstTime) {
        final JPanel displayPanel = new JPanel(new BorderLayout());
        //this.getContentPane().remove(bigP);
        final JPanel panel = new JPanel(new GridBagLayout());
        final JScrollPane scroll = new JScrollPane(panel);
        final JPanel infoPanel = new JPanel(new BorderLayout());
        clearContents();
        //this.getContentPane().add(scroll);
        //this.revalidate();
        //this.repaint();
        GridBagConstraints constraints = new GridBagConstraints();
        this.LR.loadSkillList();
        prevValues = new int[LR.getSkillList().size()];
        for (Skill s : LR.getSkillList()) {
            prevValues[LR.getSkillList().indexOf(s)] = 0;
        }

        // PLACED SPINNERS VARIANT
        int skMax = 6;
        /*
         * if (!characterController.getCharacter().getQualities().isEmpty()){ for (Quality
         * q:characterController.getCharacter().getQualities()){ if
         * (q.getName().equals("Aptitude")){ skMax=7; } } }
         */
        constraints.gridx = 0;
        constraints.gridy = 0;

        JLabel activeLabel = new JLabel("Active Skills");
        activeLabel.setName("Active");
        panel.add(activeLabel, constraints);
        constraints.gridy += 1;
        panel.add(new JLabel("  "), constraints);
        constraints.gridy += 1;

        Skill.Attribute currentAttr = Skill.Attribute.AGILITY;
        JLabel attributeLabel = new JLabel(currentAttr.name());
        attributeLabel.setName(currentAttr.name());
        panel.add(attributeLabel, constraints);
        constraints.gridy += 1;
        panel.add(new JLabel("  "), constraints);
        constraints.gridy += 1;

        activeSkills = new ArrayList<Skill>();
        final ArrayList<Skill> exoticWeapSkills = new ArrayList<Skill>();
        knowledgeSkills = new ArrayList<Skill>();
        magicSkills = new ArrayList<Skill>();
        resonanceSkills = new ArrayList<Skill>();

        ArrayList<SkillGroup> charSkillGroups = (ArrayList<SkillGroup>) characterController.getCharacter()
                .getSkillGroups().clone();

        for (Skill s : LR.getSkillList()) {
            if (s.isKnowledge()) {
                knowledgeSkills.add(s);
            } else if (s.getAttribute() == Skill.Attribute.MAGIC) {
                magicSkills.add(s);
            } else if (s.getAttribute() == Skill.Attribute.RESONANCE) {
                resonanceSkills.add(s);
            } else if (!s.getName().contains("Exotic")) {
                activeSkills.add(s);
            } else {
                exoticWeapSkills.add(s);
            }
        }
        Skill lang = null;
        if (firstTime) {
            LR.loadSkillGroupList();
            LR.loadSkillList();
            /*
             * /HANDLE MAGICAL GIVEN SKILLS
             *
             * [][0]..Magician/Wizardadept [][1]..Technomancer [][2]--Adept
             * [][3]..Aspectwizard MagicResonancePriority(MagicResonance,
             * numOfMagicSkills, valueOfMagicSkills, numOfSkills, valueOfSkills,
             * numOfMagicSkillGroups, valueOfMagicSkillGroups,
             * numOfSpellsComplexForms)
             */
            if (characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                    || characterController.getCharacter().getMagicalness().equals(
                    Magical.MysticalAdept)) {
                int amount = charMagicResonance[0].numOfMagicSkills;
                final JComboBox<Skill> skills = new JComboBox<Skill>();
                for (Skill s : magicSkills) {
                    boolean inSG = false;
                    for (SkillGroup sg : characterController.getCharacter().getSkillGroups()) {
                        if (sg.getSkills().contains(s)) {
                            inSG = true;
                            break;
                        }
                    }
                    if (!inSG) {
                        skills.addItem(s);
                    }
                }
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Please choose one of your " + amount
                                + " magic skills!"), skills};
                while (amount > 0) {
                    JOptionPane.showMessageDialog(panel, inputs, "Choose skill",
                            JOptionPane.QUESTION_MESSAGE);
                    Skill s = (Skill) skills.getSelectedItem();
                    s.setValue(charMagicResonance[0].valueOfMagicSkills);
                    characterController.getCharacter().addSkill(s);
                    prevValues[LR.getSkillList().indexOf(s)] = s.getValue();
                    amount--;
                    skills.removeItemAt(skills.getSelectedIndex());
                }
            } else if (characterController.getCharacter().getMagicalness().equals(
                    Magical.Technomancer)
                    && !characterController.getCharacter().getSkillGroups().contains(tasking)) {
                int amount = charMagicResonance[1].numOfMagicSkills;
                final JComboBox<Skill> skills = new JComboBox<Skill>();
                for (Skill s : resonanceSkills) {
                    skills.addItem(s);
                }
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Please choose one of your " + amount
                                + " resonance skills!"), skills};
                while (amount > 0) {
                    JOptionPane.showMessageDialog(panel, inputs, "Choose skill",
                            JOptionPane.QUESTION_MESSAGE);
                    Skill s = (Skill) skills.getSelectedItem();
                    s.setValue(charMagicResonance[1].valueOfMagicSkills);
                    characterController.getCharacter().addSkill(s);
                    prevValues[LR.getSkillList().indexOf(s)] = s.getValue();
                    amount--;
                    skills.removeItemAt(skills.getSelectedIndex());
                }
            } else if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)) {
                System.out.println("Adept");
                int amount = charMagicResonance[2].numOfSkills;
                final JComboBox<Skill> skills = new JComboBox<Skill>();
                ArrayList<Skill> tList = (ArrayList<Skill>) activeSkills.clone();
                tList.addAll(exoticWeapSkills);
                for (Skill s : tList) {
                    boolean inSG = false;
                    for (SkillGroup sg : characterController.getCharacter().getSkillGroups()) {
                        if (sg.getSkills().contains(s)) {
                            inSG = true;
                            break;
                        }
                    }
                    if (!inSG) {
                        skills.addItem(s);
                    }
                }

                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Please choose one of your " + amount
                                + " active skills!"), skills};
                while (amount > 0) {
                    JOptionPane.showMessageDialog(panel, inputs, "Choose skill",
                            JOptionPane.QUESTION_MESSAGE);
                    Skill s = (Skill) skills.getSelectedItem();
                    if (s.getName().contains("Exotic")) {
                        String type = "Melee";
                        Attribute at = Attribute.AGILITY;
                        if (s.getName().contains("Ranged")) {
                            type = "Ranged";
                        }
                        String text = "Exotic " + type + " Weapon";
                        if (s.getName().contains("Vehicle")) {
                            text = "Exotic Vehicle";
                            at = Attribute.REACTION;
                        }

                        JTextField skillName = new JTextField();
                        final JComponent[] input = new JComponent[]{
                                new JLabel("Weapon Name"), skillName,};
                        JOptionPane.showMessageDialog(panel, input,
                                text + " Skill", JOptionPane.QUESTION_MESSAGE);
                        if (skillName.getText() != null) {
                            Skill skill = new Skill();
                            skill.setAttribute(at);
                            skill.setKnowledge(false);
                            if (text == "Exotic Ranged Weapon") {
                                text = "[Ex.R.W.]";
                            } else if (text == "Exotic Melee Weapon") {
                                text = "[Ex.M.W.]";
                            } else {
                                text = "[Ex.Veh.]";
                            }
                            skill.setName(text + " " + skillName.getText());
                            skill.setValue(charMagicResonance[2].valueOfSkills);
                            characterController.getCharacter().addSkill(skill);
                            amount--;
                        }
                    } else {
                        s.setValue(charMagicResonance[2].valueOfSkills);
                        characterController.getCharacter().addSkill(s);
                        prevValues[LR.getSkillList().indexOf(s)] = s.getValue();
                        skills.removeItemAt(skills.getSelectedIndex());
                        amount--;
                    }
                }
            }

            JComboBox<Skill> languages = new JComboBox<Skill>();
            for (Skill s : knowledgeSkills) {
                if (s.getAttribute().equals(Skill.Attribute.INTUITION)
                        && !(s.getName().contains("Street")
                        || s.getName().contains("Interests") || s.getName().contains("(S)")
                        || s.getName().contains("(I)") || s.getName()
                        .equals("Language"))) {
                    languages.addItem(s);
                }
            }
            JTextField language = new JTextField();
            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Please choose a Native language!"),
                    languages,
                    new JLabel("OR enter a new Language in the field below!"),
                    language};
            JOptionPane.showMessageDialog(panel, inputs, "Native language",
                    JOptionPane.QUESTION_MESSAGE);

            if (language.getText().isEmpty()) {
                lang = (Skill) languages.getSelectedItem();
                prevValues[LR.getSkillList().indexOf(lang)] = lang.getValue();
            } else {
                lang = new Skill();
                lang.setName(language.getText());
                lang.setAttribute(Skill.Attribute.INTUITION);
                lang.setKnowledge(true);
            }
            lang.setValue(14);
            characterController.getCharacter().addSkill(lang);
        }
        for (Skill s : activeSkills) {
            boolean inSkillGroup = false;
            for (SkillGroup sg : charSkillGroups) {
                if (sg.getSkills().contains(s)) {
                    inSkillGroup = true;
                    /*
                     * sg.getSkills().remove(s);
                     * System.out.println("Removed "+s.getName()); if
                     * (sg.getSkills().isEmpty()){ charSkillGroups.remove(sg); }
                     */
                    break;
                }
            }
            if (!inSkillGroup) {
                if (s.getAttribute() != currentAttr) {
                    panel.add(new JLabel("  "), constraints);
                    constraints.gridy += 1;
                    currentAttr = s.getAttribute();
                    JLabel label = new JLabel(currentAttr.name());
                    label.setName(currentAttr.name());
                    panel.add(label, constraints);
                    constraints.gridy += 1;
                    panel.add(new JLabel("  "), constraints);
                    constraints.gridy += 1;
                }
                JLabel skill = new JLabel(s.getName());
                skill.setName(skill.getText() + "Label");
                panel.add(skill, constraints);
                constraints.gridx += 1;
                JSpinner skillSp;
                if (characterController.getCharacter().getSkills().contains(s)) {
                    skillSp = new JSpinner(new SpinnerNumberModel(
                            characterController.getCharacter()
                                    .getSkills()
                                    .get(characterController.getCharacter().getSkills()
                                            .indexOf(s)).getValue(),
                            0, skMax, 1));
                    characterController.getCharacter().removeSkill(characterController.getCharacter().getSkills()
                            .get(characterController.getCharacter().getSkills().indexOf(s)));
                } else {
                    skillSp = new JSpinner(new SpinnerNumberModel(0, 0, skMax,
                            1));
                }
                skillSp.addChangeListener(new SkillChange(this));
                skillSp.setName(s.getName());
                panel.add(skillSp, constraints);
                constraints.gridy += 1;
                constraints.gridx -= 1;
            }
        }
        final int sk = skMax;
        JButton exMBut = new JButton("Add new Exotic Melee Weapon Skill");
        exMBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(1, 1,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Weapon Name"), skillName, skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Exotic Melee Weapon Skill",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.AGILITY);
                    skill.setKnowledge(false);
                    skill.setName("[Ex.M.W.] " + skillName.getText());
                    skill.setValue((int) skillValue.getValue());
                    if (charSkills - (skill.getValue()) >= 0) {
                        charSkills -= skill.getValue();

                        characterController.getCharacter().addSkill(skill);

                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charSkills + " skill points left",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton exRBut = new JButton("Add new Exotic Ranged Weapon Skill");
        exRBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(1, 1,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Weapon Name"), skillName, skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Exotic Ranged Weapon Skill",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.AGILITY);
                    skill.setKnowledge(false);
                    skill.setName("[Ex.R.W.] "
                            + skillName.getText());
                    skill.setValue((int) skillValue.getValue());
                    if (charSkills - (skill.getValue()) >= 0) {
                        charSkills -= skill.getValue();

                        characterController.getCharacter().addSkill(skill);

                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charSkills + " skill points left",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton exVBut = new JButton("Add new Exotic Vehicle Skill");
        exVBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(1, 1,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Vehicle Name"), skillName, skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Exotic Vehicle Skill", JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.REACTION);
                    skill.setKnowledge(false);
                    skill.setName("[Ex.Veh.] " + skillName.getText());
                    skill.setValue((int) skillValue.getValue());
                    if (charSkills - (skill.getValue()) >= 0) {
                        charSkills -= skill.getValue();

                        characterController.getCharacter().addSkill(skill);

                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charSkills + " skill points left",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        constraints.gridy++;
        panel.add(exMBut, constraints);
        constraints.gridy++;
        panel.add(exRBut, constraints);
        constraints.gridy++;
        panel.add(exVBut, constraints);
        constraints.gridx += 2;
        constraints.gridy = 0;

        JLabel knowledgeLabel = new JLabel("Knowledge Skills");
        activeLabel.setName("Knowledge");
        panel.add(knowledgeLabel, constraints);
        constraints.gridy += 1;

        for (Skill s : knowledgeSkills) {
            if (!(s.getName().equals("Interests Knowledge"))
                    && !(s.getName().equals("Language"))
                    && !s.equals(lang)
                    && !(s.getName().equals("Street Knowledge"))
                    && !(s.getName().equals("Academic Knowledge") && !s
                    .getName().equals("Professional Knowledge"))) {
                if (s.getAttribute() != currentAttr) {
                    panel.add(new JLabel("  "), constraints);
                    constraints.gridy += 1;
                    currentAttr = s.getAttribute();
                    JLabel label = new JLabel(currentAttr.name());
                    label.setName(currentAttr.name());
                    panel.add(label, constraints);
                    constraints.gridy += 1;
                    panel.add(new JLabel("  "), constraints);
                    constraints.gridy += 1;
                }
                JLabel skill = new JLabel(s.getName());
                skill.setName(skill.getText() + "Label");
                panel.add(skill, constraints);
                constraints.gridx += 1;
                JSpinner skillSp = new JSpinner(new SpinnerNumberModel(0, 0, skMax, 1));
                if (characterController.getCharacter().getSkills().contains(s) && (characterController.getCharacter().getSkills().get(characterController.getCharacter().getSkills().indexOf(s)).getValue() < skMax)) {
                    skillSp = new JSpinner(new SpinnerNumberModel(
                            characterController.getCharacter()
                                    .getSkills()
                                    .get(characterController.getCharacter().getSkills()
                                            .indexOf(s)).getValue(), 0, skMax, 1));
                    characterController.getCharacter().removeSkill(characterController.getCharacter().getSkills()
                            .get(characterController.getCharacter().getSkills().indexOf(s)));
                }
                skillSp.addChangeListener(new KnowledgeSkillChange(this));
                skillSp.setName(s.getName());
                panel.add(skillSp, constraints);
                constraints.gridy += 1;
                constraints.gridx -= 1;
            }
        }

        panel.add(new JLabel("  "), constraints);
        constraints.gridy += 1;
        constraints.gridwidth = 2;
        JButton intBut = new JButton("Add new Interest");
        intBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(0, 0,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Interest Knowledge Name"), skillName,
                        skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Create new Interest Knowledge",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.INTUITION);
                    skill.setKnowledge(true);
                    skill.setName(skillName.getText() + " (I)");
                    skill.setValue((int) skillValue.getValue());
                    if (charKnowledgePoints - skill.getValue() >= 0) {
                        charKnowledgePoints -= skill.getValue();
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                    } else if (charSkills
                            - (skill.getValue() - charKnowledgePoints) >= 0) {
                        charSkills -= skill.getValue() - charKnowledgePoints;
                        charKnowledgePoints = 0;
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charKnowledgePoints
                                        + " knowledge points & " + charSkills
                                        + " skill points left", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(intBut, constraints);
        constraints.gridy += 1;

        JButton langBut = new JButton("Add new Language");
        langBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(0, 0,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Language Name"), skillName, skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Create new Language", JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.INTUITION);
                    skill.setKnowledge(true);
                    skill.setName(skillName.getText());
                    skill.setValue((int) skillValue.getValue());
                    characterController.getCharacter().addSkill(skill);
                    if (charKnowledgePoints - skill.getValue() >= 0) {
                        charKnowledgePoints -= skill.getValue();
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                    } else if (charSkills
                            - (skill.getValue() - charKnowledgePoints) >= 0) {
                        charSkills -= skill.getValue() - charKnowledgePoints;
                        charKnowledgePoints = 0;
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charKnowledgePoints
                                        + " knowledge points & " + charSkills
                                        + " skill points left", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(langBut, constraints);
        constraints.gridy += 1;

        JButton strBut = new JButton("Add new Street Knowledge");
        strBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(0, 0,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Street Knowledge Name"), skillName,
                        skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Create new Street Knowledge",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.INTUITION);
                    skill.setKnowledge(true);
                    skill.setName(skillName.getText() + " (S)");
                    skill.setValue((int) skillValue.getValue());
                    characterController.getCharacter().addSkill(skill);
                    if (charKnowledgePoints - skill.getValue() >= 0) {
                        charKnowledgePoints -= skill.getValue();
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                    } else if (charSkills
                            - (skill.getValue() - charKnowledgePoints) >= 0) {
                        charSkills -= skill.getValue() - charKnowledgePoints;
                        charKnowledgePoints = 0;
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charKnowledgePoints
                                        + " knowledge points & " + charSkills
                                        + " skill points left", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(strBut, constraints);
        constraints.gridy += 1;

        JButton acBut = new JButton("Add new Academic Knowledge");
        acBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(0, 0,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Academic Knowledge Name"), skillName,
                        skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Create new Academic Knowledge",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.LOGIC);
                    skill.setKnowledge(true);
                    skill.setName(skillName.getText() + " (A)");
                    skill.setValue((int) skillValue.getValue());
                    characterController.getCharacter().addSkill(skill);
                    if (charKnowledgePoints - skill.getValue() >= 0) {
                        charKnowledgePoints -= skill.getValue();
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                    } else if (charSkills
                            - (skill.getValue() - charKnowledgePoints) >= 0) {
                        charSkills -= skill.getValue() - charKnowledgePoints;
                        charKnowledgePoints = 0;
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charKnowledgePoints
                                        + " knowledge points & " + charSkills
                                        + " skill points left", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(acBut, constraints);
        constraints.gridy += 1;

        JButton proBut = new JButton("Add new Professional Knowledge");
        proBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JTextField skillName = new JTextField();
                JSpinner skillValue = new JSpinner(new SpinnerNumberModel(0, 0,
                        sk, 1));
                final JComponent[] inputs = new JComponent[]{
                        new JLabel("Professional Knowledge Name"), skillName,
                        skillValue};
                JOptionPane.showMessageDialog(panel, inputs,
                        "Create new Professional Knowledge",
                        JOptionPane.QUESTION_MESSAGE);
                if (skillName.getText() != null) {
                    Skill skill = new Skill();
                    skill.setAttribute(Skill.Attribute.LOGIC);
                    skill.setKnowledge(true);
                    skill.setName(skillName.getText() + " (P)");
                    skill.setValue((int) skillValue.getValue());
                    characterController.getCharacter().addSkill(skill);
                    if (charKnowledgePoints - skill.getValue() >= 0) {
                        charKnowledgePoints -= skill.getValue();
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                    } else if (charSkills
                            - (skill.getValue() - charKnowledgePoints) >= 0) {
                        charSkills -= skill.getValue() - charKnowledgePoints;
                        charKnowledgePoints = 0;
                        characterController.getCharacter().addSkill(skill);
                        spAtr.setValue(charKnowledgePoints);
                        atr.setValue(charSkills);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                                "You don't have enough points to add this skill! Only "
                                        + charKnowledgePoints
                                        + " knowledge points & " + charSkills
                                        + " skill points left", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(proBut, constraints);
        constraints.gridy += 1;
        constraints.gridwidth = 1;

        if (characterController.getCharacter().getMagicalness() == Magical.Magician
                || characterController.getCharacter().getMagicalness() == Magical.MysticalAdept) {
            constraints.gridy = 0;
            constraints.gridx += 2;
            panel.add(new JLabel("Magical Skills"), constraints);
            constraints.gridy += 1;
            panel.add(new JLabel("   "), constraints);
            constraints.gridy += 1;
            JLabel label = new JLabel(Skill.Attribute.MAGIC.name());
            label.setName(Skill.Attribute.MAGIC.name());
            panel.add(label, constraints);
            constraints.gridy += 1;
            panel.add(new JLabel("  "), constraints);
            constraints.gridy += 1;
            for (Skill s : magicSkills) {
                boolean inSkillGroup = false;
                for (SkillGroup sg : charSkillGroups) {
                    if (sg.getSkills().contains(s)) {
                        inSkillGroup = true;
                        /*
                         * sg.getSkills().remove(s);
                         * System.out.println("Removed "+s.getName()); if
                         * (sg.getSkills().isEmpty()){
                         * charSkillGroups.remove(sg); }
                         */
                        break;
                    }
                }
                if (!inSkillGroup) {
                    JLabel skill = new JLabel(s.getName());
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp;
                    if (characterController.getCharacter().getSkills().contains(s)) {
                        skillSp = new JSpinner(new SpinnerNumberModel(
                                characterController.getCharacter()
                                        .getSkills()
                                        .get(characterController.getCharacter().getSkills()
                                                .indexOf(s)).getValue(),
                                characterController.getCharacter()
                                        .getSkills()
                                        .get(characterController.getCharacter().getSkills()
                                                .indexOf(s)).getValue(), skMax,
                                1));
                        characterController.getCharacter().removeSkill(characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(s)));
                    } else {
                        skillSp = new JSpinner(new SpinnerNumberModel(0, 0,
                                skMax, 1));
                    }
                    skillSp.addChangeListener(new SkillChange(this));
                    skillSp.setName(s.getName());
                    panel.add(skillSp, constraints);
                    constraints.gridy += 1;
                    constraints.gridx -= 1;
                }
            }
        } else if (characterController.getCharacter().getMagicalness() == Magical.Technomancer) {
            if (!charSkillGroups.contains(tasking)) {
                constraints.gridy = 0;
                constraints.gridx += 2;
                panel.add(new JLabel("Resonance Skills"), constraints);
                constraints.gridy += 1;
                panel.add(new JLabel("   "), constraints);
                constraints.gridy += 1;
                JLabel label = new JLabel(Skill.Attribute.RESONANCE.name());
                label.setName(Skill.Attribute.RESONANCE.name());
                panel.add(label, constraints);
                constraints.gridy += 1;
                panel.add(new JLabel("  "), constraints);
                constraints.gridy += 1;
                for (Skill s : resonanceSkills) {
                    JLabel skill = new JLabel(s.getName());
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp;
                    if (characterController.getCharacter().getSkills().contains(s)) {
                        skillSp = new JSpinner(new SpinnerNumberModel(
                                characterController.getCharacter()
                                        .getSkills()
                                        .get(characterController.getCharacter().getSkills()
                                                .indexOf(s)).getValue(),
                                characterController.getCharacter()
                                        .getSkills()
                                        .get(characterController.getCharacter().getSkills()
                                                .indexOf(s)).getValue(), skMax,
                                1));
                        characterController.getCharacter().removeSkill(characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(s)));
                    } else {
                        skillSp = new JSpinner(new SpinnerNumberModel(0, 0,
                                skMax, 1));
                    }
                    skillSp.setName(s.getName());
                    skillSp.addChangeListener(new SkillChange(this));
                    panel.add(skillSp, constraints);
                    constraints.gridy += 1;
                    constraints.gridx -= 1;
                }
            }
        }

        atr = new JSpinner(new SpinnerNumberModel(charSkills, 0, charSkills, 1));
        atr.setEnabled(false);
        //panel.add(atr, constraints);
        spAtr = new JSpinner(new SpinnerNumberModel(charKnowledgePoints, 0,
                charKnowledgePoints, 1));
        spAtr.setEnabled(false);
        constraints.gridx += 1;
        //panel.add(spAtr, constraints);

		/*JLabel info = new JLabel(characterController.getCharacter().getPersonalData().getName()
				+ " - " + characterController.getCharacter().getPersonalData().getSex() + " - "
				+ characterController.getCharacter().getPersonalData().getMetatype());

		info.setHorizontalAlignment(JLabel.CENTER);
		info.setVerticalAlignment(JLabel.CENTER);*/

        JPanel iPanel = new JPanel();
        //iPanel.add(info);
        iPanel.add(new JLabel("Skill Pts"));
        iPanel.add(atr);
        iPanel.add(new JLabel("Knowledge Skill Pts"));
        iPanel.add(spAtr);

        JButton next = new JButton("Next");

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSkillGroups();
            }
        });

        infoPanel.add(iPanel, BorderLayout.CENTER);
        infoPanel.add(next, BorderLayout.EAST);
        infoPanel.add(back, BorderLayout.WEST);

        displayPanel.add(scroll, BorderLayout.CENTER);
        displayPanel.add(infoPanel, BorderLayout.SOUTH);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (java.awt.Component comp : panel.getComponents()) {
                    if (comp.getName() != null) {
                        System.out.println(comp.getName());
                        for (Skill s : LR.getSkillList()) {
                            if (comp.getName().equals(s.getName())) {
                                int value = (int) ((JSpinner) comp).getValue();
                                if (value > 0) {
                                    s.setValue(value);
                                    characterController.getCharacter().addSkill(s);
                                    prevValues[LR.getSkillList().indexOf(s)] = s.getValue();
                                }
                            }
                        }
                    }
                }
                scroll.getParent().remove(scroll);
                setQualities();
            }
        });

        add(displayPanel);

        revalidate();
        repaint();
    }

    private void chooseGear() {
        final JPanel panel = new JPanel(new BorderLayout());;
        clearContents();
        //panel.setLayout(new BorderLayout());
        final JPanel tabPanel = new JPanel(new GridLayout(2, 0));
        final JPanel contentPanel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());

        panel.add(tabPanel, BorderLayout.PAGE_START);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.PAGE_END);

        JButton mWp = new JButton("Meele Weapons");
        JButton rWp = new JButton("Ranged Weapons");
        JButton am = new JButton("Ammunition");
        JButton cloth = new JButton("Clothing/Armor");
        JButton ware = new JButton("Cyberware/Bioware");
        JButton decks = new JButton("Cyberdecks");
        JButton gear = new JButton("Gear");
        JButton veh = new JButton("Vehicles/Drones");

        tabPanel.add(mWp);
        tabPanel.add(rWp);
        tabPanel.add(am);
        tabPanel.add(cloth);
        tabPanel.add(ware);
        tabPanel.add(decks);
        tabPanel.add(gear);
        tabPanel.add(veh);

        final JSpinner karma = new JSpinner(new SpinnerNumberModel(
                characterController.getCharacter().getPersonalData().getKarma(), characterController.getCharacter()
                .getPersonalData().getKarma() - 10, characterController.getCharacter()
                .getPersonalData().getKarma(), 1));
        final JSpinner money = new JSpinner(new SpinnerNumberModel(
                charRessources, 0.0, charRessources, 1.0));
        money.setEnabled(false);
        final JSpinner essence = new JSpinner(new SpinnerNumberModel(characterController.getCharacter().getAttributes().getEssence(), 0.0, 6.1, 0.1));
        essence.setEnabled(false);

        JPanel iPanel = new JPanel();
        iPanel.add(new JLabel("Karma"));
        iPanel.add(karma);
        iPanel.add(new JLabel("NuYen"));
        iPanel.add(money);
        iPanel.add(new JLabel("Essence"));
        iPanel.add(essence);

        JButton back = new JButton("Back");
        infoPanel.add(back, BorderLayout.WEST);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setQualities();
            }
        });

        infoPanel.add(iPanel, BorderLayout.CENTER);

        JButton next = new JButton("Next");
        infoPanel.add(next, BorderLayout.EAST);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (charRessources > 5000) {
                    charRessources = 5000;
                }
                LifeStyle[] lifeStyles = new LifeStyle[]{
                        new LifeStyle("Street", 0, 1, 20),
                        new LifeStyle("Squatter", 500, 2, 40),
                        new LifeStyle("Low", 2000, 3, 60),
                        new LifeStyle("Middle", 5000, 4, 100),
                        new LifeStyle("High", 10000, 5, 500),
                        new LifeStyle("Luxury", 100000, 6, 1000),};
                int index = JOptionPane.showOptionDialog(panel,
                        "Please choose a lifestyle:", "Lifestyle",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, lifeStyles, 0);
                characterController.getCharacter().getPersonalData().setLifestyle(
                        lifeStyles[index]);
                DieRoller roller = new DieRoller();
                ;
                DieRoll roll = roller.rollDice(lifeStyles[index]
                        .getCapitalDice());
                int plus = roll.getDice().stream().mapToInt(Die::getRolledValue).sum();
                JOptionPane.showMessageDialog(panel,
                        "You rolled " + plus + ", resulting in " + plus
                                * lifeStyles[index].getCapitalMod() + " NuYen!");
                plus *= lifeStyles[index].getCapitalMod();
                characterController.getCharacter().setMoney(charRessources + plus);
                finishingTouches();
            }
        });

        prevValue = (int) karma.getValue();
        karma.addChangeListener(new KarmaToMoney(this, money));

        mWp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showMeeleWeapList(contentPanel, money, true);
            }
        });
        rWp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showRangedWeapList(contentPanel, money, true);
            }
        });
        am.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showAmmunitionList(contentPanel, money, true);
            }
        });
        cloth.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showClothingList(contentPanel, money, true);
            }
        });
        ware.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showCyberwareList(contentPanel, essence, money, true);
            }
        });
        decks.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showDeckList(contentPanel, money, true);
            }
        });
        gear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showGearList(contentPanel, money, true);
            }
        });
        veh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                showVehicleList(contentPanel, money, true);
            }
        });
        mWp.doClick();

        add(panel);
        revalidate();
        repaint();

    }

    private void finishingTouches() {
        this.getContentPane().remove(0);
        clearContents();
        this.getContentPane().add(characterDisplay(true));

        revalidate();
        repaint();
    }

    public JPanel characterDisplay(final boolean characterGeneration) {
        final int skMax;

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        final JPanel tabPanel = new JPanel(new GridLayout(4, 4));
        final JPanel contentPanel = new JPanel(new GridBagLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());

        final JButton done = new JButton("Done");
        done.addActionListener(new DoneAction(this, contentPanel, characterController));
        if (characterGeneration) {

            if (characterController.getCharacter().getQualities().contains(
                    new Quality("Aptitude", null, true, 0))) {
                skMax = 7;
            } else {
                skMax = 6;
            }
            freeContPoints = characterController.getCharacter().getAttributes().getCharisma() * 3;
            if (characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                    || characterController.getCharacter().getMagicalness().equals(
                    Magical.MysticalAdept)) {
                freeSpells = charMagicResonance[0].numOfSpellsComplexForms;
            } else if (characterController.getCharacter().getMagicalness().equals(
                    Magical.Technomancer)) {
                freeSpells = charMagicResonance[1].numOfSpellsComplexForms;
            } else if (characterController.getCharacter().getMagicalness().equals(
                    Magical.AspectedMagician)) {
                freeSpells = charMagicResonance[3].numOfSpellsComplexForms;
            }
        } else {
            this.getJMenuBar().remove(mainMenu.help);
            this.getJMenuBar().add(mainMenu.advancement);
            this.getJMenuBar().add(mainMenu.help);
            if (characterController.getCharacter().getQualities().contains(
                    new Quality("Aptitude", null, true, 0))) {
                skMax = 13;
            } else {
                skMax = 12;
            }
        }


        panel.add(tabPanel, BorderLayout.PAGE_START);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.PAGE_END);

        final JButton persData = new JButton("Personal Data");
        final JButton ids = new JButton("IDs/Lifestyles/Currency");
        final JButton attributes = new JButton("Attributes");
        final JButton skills = new JButton("Skills");
        final JButton coreCombatInfo = new JButton("Core Combat Info");
        final JButton conditionMonitor = new JButton("Condition Monitor");
        final JButton qualities = new JButton("Qualities");
        final JButton contacts = new JButton("Contacts");
        JButton rangedWeap = new JButton("Ranged Weapons");
        JButton meleeWeap = new JButton("Melee Weapons");
        JButton armor = new JButton("Armor/Clothing");
        JButton cyberdecks = new JButton("Cyberdecks");
        JButton augmentations = new JButton("Augmentations");
        JButton vehicles = new JButton("Vehicles");
        JButton gear = new JButton("Gear");
        final JButton spells = new JButton("Spells/Perarations/Rituals");
        if (characterController.getCharacter().getMagicalness().equals(Magical.Technomancer))
            spells.setText("Complex Forms");
        final JButton powers = new JButton("(Adept) Powers");

        tabPanel.add(persData);
        tabPanel.add(ids);
        tabPanel.add(attributes);
        tabPanel.add(skills);
        tabPanel.add(coreCombatInfo);
        tabPanel.add(conditionMonitor);
        tabPanel.add(qualities);
        tabPanel.add(contacts);
        tabPanel.add(rangedWeap);
        tabPanel.add(meleeWeap);
        tabPanel.add(armor);
        tabPanel.add(cyberdecks);
        tabPanel.add(augmentations);
        tabPanel.add(vehicles);
        tabPanel.add(gear);
        if (characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.MysticalAdept)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.AspectedMagician)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.Technomancer)) {
            tabPanel.add(spells);
        }
        if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.MysticalAdept)) {
            tabPanel.add(powers);
        }

        final JSpinner karma = new JSpinner(new SpinnerNumberModel(
                characterController.getCharacter().getPersonalData().getKarma(), 0,
                characterController.getCharacter().getPersonalData().getKarma(), 1));
        karma.setEnabled(false);

        infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
        JPanel iPanel = new JPanel();
        iPanel.add(new JLabel("Karma "));
        iPanel.add(karma);
        iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
        infoPanel.add(iPanel, BorderLayout.CENTER);
        if (characterGeneration)
            infoPanel.add(done, BorderLayout.EAST);

        persData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                infoPanel.revalidate();
                infoPanel.repaint();
                PersData per = characterController.getCharacter().getPersonalData();
                GridBagConstraints con = new GridBagConstraints();
                con.fill = GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel(new ImageIcon(characterController.getCharacter().getCharPic())), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Name/Primary Alias:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getName()), con);
                con.gridx = 0;
                con.gridy = 1;
                contentPanel.add(new JLabel("Metatype:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getMetatype().name()), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Ethnicity:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getEthnicity()), con);
                con.gridx = 0;
                con.gridy = 2;
                contentPanel.add(new JLabel("Age:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getAge() + ""), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Sex:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getSex().name()), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Height:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getHeight() + ""), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Weight:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getWeight() + ""), con);
                con.gridx = 0;
                con.gridy = 3;
                contentPanel.add(new JLabel("Street Cred:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getStreetCred() + ""), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Notoriety:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getNotoriety() + ""), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Public Awareness:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getPublicAwareness() + ""), con);
                con.gridy = 4;
                con.gridx = 0;
                contentPanel.add(new JLabel("Karma:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getKarma() + ""), con);
                con.gridx += 1;
                contentPanel.add(new JLabel("Total Karma:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getTotalKarma() + ""), con);
                con.gridy = 5;
                con.gridx = 0;
                JButton changeName = new JButton("Change Name/Primary Alias");
                JButton changeEthnicity = new JButton("Change Ethnicity");
                JButton changeAge = new JButton("Change Age");
                JButton changeHeight = new JButton("Change Height");
                JButton changeWeight = new JButton("Change Weight");
                changeName.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField input = new JTextField(40);
                        AbstractDocument d = (AbstractDocument) input.getDocument();
                        d.setDocumentFilter(new DocumentSizeFilter(26));
                        JComponent[] in = new JComponent[]{
                                new JLabel(
                                        "Please enter new Name/Primary Alias:"),
                                input};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Change Name/Alias",
                                JOptionPane.QUESTION_MESSAGE);
                        characterController.getCharacter().getPersonalData().setName(
                                input.getText());
                        revalidate();
                        repaint();
                        persData.doClick();
                    }
                });
                changeEthnicity.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField input = new JTextField();
                        JComponent[] in = new JComponent[]{
                                new JLabel("Please enter new Ethnicity:"),
                                input};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Change Ethnicity",
                                JOptionPane.QUESTION_MESSAGE);
                        characterController.getCharacter().getPersonalData().setEthnicity(
                                input.getText());
                        revalidate();
                        repaint();
                        persData.doClick();
                    }
                });
                changeAge.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField input = new JTextField();
                        JComponent[] in = new JComponent[]{
                                new JLabel("Please enter new Age:"), input};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Change Age", JOptionPane.QUESTION_MESSAGE);
                        characterController.getCharacter().getPersonalData().setAge(
                                Integer.parseInt(input.getText()));
                        revalidate();
                        repaint();
                        persData.doClick();
                    }
                });
                changeHeight.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField input = new JTextField();
                        JComponent[] in = new JComponent[]{
                                new JLabel("Please enter new Height:"), input};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Change height", JOptionPane.QUESTION_MESSAGE);
                        characterController.getCharacter().getPersonalData().setHeight(
                                Integer.parseInt(input.getText()));
                        revalidate();
                        repaint();
                        persData.doClick();
                    }
                });
                changeWeight.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField input = new JTextField();
                        JComponent[] in = new JComponent[]{
                                new JLabel("Please enter new Weight:"), input};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Change Weight", JOptionPane.QUESTION_MESSAGE);
                        characterController.getCharacter().getPersonalData().setWeight(
                                Integer.parseInt(input.getText()));
                        revalidate();
                        repaint();
                        persData.doClick();
                    }
                });
                contentPanel.add(changeName, con);
                con.gridx += 1;
                contentPanel.add(changeEthnicity, con);
                con.gridx += 1;
                contentPanel.add(changeAge, con);
                con.gridx += 1;
                contentPanel.add(changeHeight, con);
                con.gridx += 1;
                contentPanel.add(changeWeight, con);

                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });
        ids.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                PersData per = characterController.getCharacter().getPersonalData();
                GridBagConstraints con = new GridBagConstraints();
                con.fill = GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel("Primary Lifestyle:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(per.getLifestyle().getName()), con);
                con.gridx = 0;
                con.gridy += 1;
                contentPanel.add(new JLabel("Nuyen:"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(characterController.getCharacter().getMoney() + ""),
                        con);
                con.gridx = 0;
                con.gridy += 1;
                contentPanel.add(new JLabel("Licenses & Fake IDs"), con);
                con.gridy += 1;
                for (ID id : characterController.getCharacter().getFakeIDs()) {
                    contentPanel.add(new JLabel(id.toString()), con);
                    con.gridy += 1;
                }
                JButton id = new JButton("New Fake ID/License");
                contentPanel.add(id, con);
                id.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JComboBox<String> type = new JComboBox<String>();
                        type.addItem("SIN");
                        type.addItem("License");
                        JTextField name = new JTextField();
                        JSpinner rating = new JSpinner(new SpinnerNumberModel(
                                1, 1, 6, 1));
                        JComponent[] in = new JComponent[]{
                                new JLabel("Creating fake :"), type,
                                new JLabel("Please enter name/title;"), name,
                                new JLabel("Please choose rating:"), rating};
                        JOptionPane.showMessageDialog(contentPanel, in,
                                "Add fake SIN/license",
                                JOptionPane.QUESTION_MESSAGE);
                        ID id = new ID();
                        id.setName(name.getText() + " " + (String) type.getSelectedItem());
                        id.setRating((int) rating.getValue());
                        id.setRatingLeveled(true);
                        if (type.getSelectedIndex() == 0) {
                            id.setPrice(2500);
                        } else {
                            id.setPrice(200);
                        }
                        id.setAvailability(3);
                        id.setAvailabilityType(2);
                        if (id.getPrice() < characterController.getCharacter().getMoney()) {
                            characterController.getCharacter().setMoney(characterController.getCharacter()
                                    .getMoney() - id.getPrice());
                            characterController.getCharacter().addFakeID(id);
                        }
                        revalidate();
                        repaint();
                        ids.doClick();
                    }
                });
                revalidate();
                repaint();

            }
        });
        attributes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                characterController.getCharacter().getAttributes().evaluate();

                Attributes atr = characterController.getCharacter().getAttributes();
                GridBagConstraints con = new GridBagConstraints();
                con.fill = GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel("Body"), con);
                con.gridx += 1;
                JSpinner body = new JSpinner(new SpinnerNumberModel(atr
                        .getBody(), atr.getBody(), atr.getMAXbody(), 1));
                contentPanel.add(body, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Agility"), con);
                con.gridx += 1;
                JSpinner agi = new JSpinner(
                        new SpinnerNumberModel(atr.getAgility(), atr
                                .getAgility(), atr.getMAXagility(), 1));
                contentPanel.add(agi, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Reaction"), con);
                con.gridx += 1;
                JSpinner rea = new JSpinner(new SpinnerNumberModel(atr
                        .getReaction(), atr.getReaction(),
                        atr.getMAXreaction(), 1));
                contentPanel.add(rea, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Strength"), con);
                con.gridx += 1;
                JSpinner str = new JSpinner(new SpinnerNumberModel(atr
                        .getStrength(), atr.getStrength(),
                        atr.getMAXstrength(), 1));
                contentPanel.add(str, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Willpower"), con);
                con.gridx += 1;
                JSpinner wil = new JSpinner(new SpinnerNumberModel(atr
                        .getWillpower(), atr.getWillpower(), atr
                        .getMAXwillpower(), 1));
                contentPanel.add(wil, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Logic"), con);
                con.gridx += 1;
                JSpinner logi = new JSpinner(new SpinnerNumberModel(atr
                        .getLogic(), atr.getLogic(), atr.getMAXlogic(), 1));
                contentPanel.add(logi, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Intuition"), con);
                con.gridx += 1;
                JSpinner intu = new JSpinner(new SpinnerNumberModel(atr
                        .getIntuition(), atr.getIntuition(), atr
                        .getMAXintuition(), 1));
                contentPanel.add(intu, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Charisma"), con);
                con.gridx += 1;
                JSpinner cha = new JSpinner(new SpinnerNumberModel(atr
                        .getCharisma(), atr.getCharisma(),
                        atr.getMAXcharisma(), 1));
                contentPanel.add(cha, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Edge"), con);
                con.gridx += 1;
                JSpinner edg = new JSpinner(new SpinnerNumberModel(atr
                        .getEdge(), atr.getEdge(), atr.getMAXedge(), 1));
                contentPanel.add(edg, con);

                con.gridx = 2;
                con.gridy = 0;
                contentPanel.add(new JLabel("Essence"), con);
                con.gridx += 1;
                JSpinner es = new JSpinner(new SpinnerNumberModel(atr
                        .getEssence(), 0.0, 6.0, 0.1));
                es.setEnabled(false);
                contentPanel.add(es, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Magic"), con);
                con.gridx += 1;
                final JSpinner mag = new JSpinner(new SpinnerNumberModel(atr
                        .getMagic(), atr.getMagic(), atr.getMAXmagic(), 1));
                contentPanel.add(mag, con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Resonance"), con);
                con.gridx += 1;
                JSpinner res = new JSpinner(new SpinnerNumberModel(atr
                        .getResonance(), atr.getResonance(), atr
                        .getMAXresonance(), 1));
                contentPanel.add(res, con);
                con.gridy += 1;
                con.gridx -= 1;

                final JSpinner app = new JSpinner(new SpinnerNumberModel(atr
                        .getAdeptPowerPoints(), atr.getAdeptPowerPoints(),
                        (double) atr.getMAXmagic(), 1.0));

                if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)
                        || characterController.getCharacter().getMagicalness().equals(
                        Magical.MysticalAdept)) {
                    contentPanel.add(new JLabel("Adept Power Points"), con);
                    con.gridx += 1;
                    contentPanel.add(app, con);
                    if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)) {
                        app.setEnabled(false);
                    } else {
                        app.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                double newVal = (double) ((JSpinner) e
                                        .getSource()).getValue();
                                int cost = 5;
                                if (cost <= characterController.getCharacter().getPersonalData()
                                        .getKarma()) {
                                    characterController.getCharacter().getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - cost);
                                    characterController.getCharacter().getAttributes()
                                            .setAdeptPowerPoints(newVal);
                                    ((JSpinner) e.getSource())
                                            .setModel(new SpinnerNumberModel(
                                                    characterController.getCharacter()
                                                            .getAttributes()
                                                            .getAdeptPowerPoints(),
                                                    characterController.getCharacter()
                                                            .getAttributes()
                                                            .getAdeptPowerPoints(),
                                                    (double) characterController.getCharacter()
                                                            .getAttributes()
                                                            .getMAXmagic(), 1.0));
                                } else {
                                    ((JSpinner) e.getSource())
                                            .removeChangeListener(this);
                                    ((JSpinner) e.getSource())
                                            .setValue(characterController.getCharacter()
                                                    .getAttributes()
                                                    .getAdeptPowerPoints());
                                    ((JSpinner) e.getSource())
                                            .addChangeListener(this);
                                    JOptionPane
                                            .showMessageDialog(contentPanel,
                                                    "Not enough Karma to raise Attribute!");
                                }
                                karma.setValue(characterController.getCharacter()
                                        .getPersonalData().getKarma());
                                revalidate();
                                repaint();
                            }
                        });
                    }
                }

                if (characterController.getCharacter().getMagicalness().equals(
                        Magical.Technomancer)) {
                    mag.setEnabled(false);
                } else if (characterController.getCharacter().getMagicalness().equals(
                        Magical.Mundane)) {
                    mag.setEnabled(false);
                    res.setEnabled(false);
                } else {
                    res.setEnabled(false);
                }

                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Initiative"), con);
                con.gridx += 1;
                contentPanel.add(
                        new JLabel(atr.getInitiative() + "+" + atr.getIniDice()
                                + "D6"), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Matrix Initiative"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getMatrixInitiative() + "+"
                        + atr.getIniDice() + "D6"), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Magic Initiative"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getAstralInitiative() + "+"
                        + atr.getIniDice() + "D6"), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Compusure"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getComposure() + ""), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Judge Intentions"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getJudgeIntentions() + ""), con);
                con.gridy += 1;

                con.gridx -= 1;
                contentPanel.add(new JLabel("Memory"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getMemory() + ""), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Lift/Carry"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getLiftCarry() + ""), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Movement"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getMovement() + ""), con);
                con.gridx = 5;
                con.gridy = 0;
                contentPanel.add(new JLabel("Physical Limit"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getPhysicalLimit() + ""), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Mental Limit"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getMentalLimit() + ""), con);
                con.gridy += 1;
                con.gridx -= 1;
                contentPanel.add(new JLabel("Social Limit"), con);
                con.gridx += 1;
                contentPanel.add(new JLabel(atr.getSocialLimit() + ""), con);
                revalidate();
                repaint();

                body.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getBody(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setBody(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getBody(),
                                            characterController.getCharacter().getAttributes()
                                                    .getBody(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXbody(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getBody());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                agi.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getAgility(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setAgility(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getAgility(),
                                            characterController.getCharacter().getAttributes()
                                                    .getAgility(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXagility(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getAgility());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                rea.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getReaction(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes()
                                    .setReaction(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getReaction(),
                                            characterController.getCharacter().getAttributes()
                                                    .getReaction(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXreaction(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getReaction());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                str.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getStrength(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes()
                                    .setStrength(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getStrength(),
                                            characterController.getCharacter().getAttributes()
                                                    .getStrength(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXstrength(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getStrength());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                wil.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getWillpower(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setWillpower(
                                    newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getWillpower(),
                                            characterController.getCharacter().getAttributes()
                                                    .getWillpower(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXwillpower(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getWillpower());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                logi.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getLogic(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setLogic(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getLogic(),
                                            characterController.getCharacter().getAttributes()
                                                    .getLogic(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXlogic(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getLogic());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                intu.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getIntuition(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setIntuition(
                                    newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getIntuition(),
                                            characterController.getCharacter().getAttributes()
                                                    .getIntuition(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXintuition(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getIntuition());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                cha.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getCharisma(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes()
                                    .setCharisma(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getCharisma(),
                                            characterController.getCharacter().getAttributes()
                                                    .getCharisma(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXcharisma(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getCharisma());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                edg.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getEdge(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setEdge(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getEdge(),
                                            characterController.getCharacter().getAttributes()
                                                    .getEdge(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXedge(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getEdge());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                mag.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getMagic(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setMagic(newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getMagic(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMagic(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXmagic(), 1));
                            if (characterController.getCharacter().getMagicalness().equals(
                                    Magical.Adept))
                                app.setValue(((JSpinner) e.getSource())
                                        .getValue());
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getMagic());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });
                res.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        int newVal = (int) ((JSpinner) e.getSource())
                                .getValue();
                        int calc = newVal;
                        int cost = 0;
                        for (; calc > characterController.getCharacter().getAttributes()
                                .getResonance(); calc--) {
                            cost += calc * 5;
                        }
                        if (cost <= characterController.getCharacter().getPersonalData()
                                .getKarma()) {
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            characterController.getCharacter().getAttributes().setResonance(
                                    newVal);
                            ((JSpinner) e.getSource())
                                    .setModel(new SpinnerNumberModel(
                                            characterController.getCharacter().getAttributes()
                                                    .getResonance(),
                                            characterController.getCharacter().getAttributes()
                                                    .getResonance(),
                                            characterController.getCharacter().getAttributes()
                                                    .getMAXresonance(), 1));
                        } else {
                            ((JSpinner) e.getSource())
                                    .removeChangeListener(this);
                            ((JSpinner) e.getSource())
                                    .setValue(characterController.getCharacter().getAttributes()
                                            .getResonance());
                            ((JSpinner) e.getSource()).addChangeListener(this);
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma to raise Attribute!");
                        }
                        karma.setValue(characterController.getCharacter().getPersonalData()
                                .getKarma());
                        revalidate();
                        repaint();
                    }
                });

            }
        });
        skills.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("SKILLS"
                        + characterController.getCharacter().getSkillGroups().isEmpty());
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                final JPanel panel = new JPanel(new GridBagLayout());
                final JScrollPane scroll = new JScrollPane(panel);
                scroll.setSize(100, 100);
                GridBagConstraints constraints = new GridBagConstraints();
                prevValues = new int[characterController.getCharacter().getSkillGroups().size()];
                for (SkillGroup s : characterController.getCharacter().getSkillGroups()) {
                    // System.out.println(s.getName());
                    prevValues[characterController.getCharacter().getSkillGroups().indexOf(s)] = s
                            .getValue();
                }
                constraints.gridx = 0;
                constraints.gridy = 0;
                int amount = 0;
                int biggestY = 0;
                for (SkillGroup sg : characterController.getCharacter().getSkillGroups()) {
                    System.out.println(sg.getName());
                    constraints.gridwidth = 2;
                    JLabel group = new JLabel(sg.getName());
                    panel.add(group, constraints);
                    constraints.gridx += 2;
                    constraints.gridwidth = 1;
                    JSpinner groupSp = new JSpinner(new SpinnerNumberModel(sg
                            .getValue(), sg.getValue(), skMax, 1));
                    groupSp.setName(sg.getName());

                    groupSp.addChangeListener(new SkillGroupKarmaRaise(karma,
                            skMax, characterController));
                    group.setName(sg.getName() + "Label");
                    panel.add(groupSp, constraints);
                    for (Skill s : sg.getSkills()) {
                        System.out.println("Skill: " + s.getName());
                        constraints.gridy += 1;
                        constraints.gridx -= 1;
                        JLabel skill = new JLabel(s.getName() + " "
                                + s.getAttributeShorthand() + " ");
                        skill.setName(skill.getText() + "Label");
                        panel.add(skill, constraints);
                        constraints.gridx += 1;
                        JSpinner skillSp = new JSpinner(new SpinnerNumberModel(
                                sg.getValue(), sg.getValue(), skMax, 1));
                        skillSp.setName(sg.getName() + "Skill");
                        skillSp.setEnabled(false);
                        panel.add(skillSp, constraints);
                    }
                    constraints.gridy += 1;
                    panel.add(new JLabel(" "), constraints);
                    if (sg.getSkills().size() < 4) {
                        constraints.gridy += 1;
                        panel.add(new JLabel(" "), constraints);
                    }
                    if (amount == 3) {
                        amount = 0;
                        if (biggestY < constraints.gridy) {
                            biggestY = constraints.gridy;
                        }
                        constraints.gridx += 1;
                        constraints.gridy = 0;
                        panel.add(new JLabel("   "), constraints);
                        constraints.gridy = 1;
                        panel.add(new JLabel("   "), constraints);
                        constraints.gridy = 2;
                        panel.add(new JLabel("   "), constraints);
                        constraints.gridy = 3;
                        panel.add(new JLabel("   "), constraints);
                        constraints.gridx += 1;
                        constraints.gridy = 0;
                    } else {
                        amount++;
                        constraints.gridx -= 2;
                        constraints.gridy += 2;
                        if (biggestY < constraints.gridy) {
                            biggestY = constraints.gridy;
                        }
                    }
                }

                ArrayList<Skill> knowSkills = new ArrayList<Skill>();
                ArrayList<Skill> activeSkills = new ArrayList<Skill>();
                for (Skill s : characterController.getCharacter().getSkills()) {
                    boolean inSkillGroup = false;
                    for (SkillGroup sg : characterController.getCharacter().getSkillGroups()) {
                        if (sg.getSkills().contains(s)) {
                            inSkillGroup = true;
                            /*
                             * sg.getSkills().remove(s);
                             * System.out.println("Removed "+s.getName()); if
                             * (sg.getSkills().isEmpty()){
                             * charSkillGroups.remove(sg); }
                             */
                            break;
                        }
                    }
                    if (!inSkillGroup) {
                        if (s.isKnowledge()) {
                            knowSkills.add(s);
                        } else {
                            activeSkills.add(s);
                        }
                    }
                }
                constraints.gridy = biggestY;
                constraints.gridx = 1;
                panel.add(new JLabel("Active Skills"), constraints);
                constraints.gridy += 1;
                Attribute currentAttr = Attribute.BODY;

                for (Skill s : activeSkills) {

                    if (s.getAttribute() != currentAttr) {
                        panel.add(new JLabel("  "), constraints);
                        constraints.gridy += 1;
                        currentAttr = s.getAttribute();
                        JLabel label = new JLabel(currentAttr.name());
                        label.setName(currentAttr.name());
                        panel.add(label, constraints);
                        constraints.gridy += 1;
                        panel.add(new JLabel("  "), constraints);
                        constraints.gridy += 1;
                    }

                    JLabel skill = new JLabel(s.getName());
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp = null;
                    if (s.getValue() >= skMax) {
                        skillSp = new JSpinner(new SpinnerNumberModel(s
                                .getValue(), s.getValue(), s.getValue(), 1));
                        skillSp.setEnabled(false);
                    } else {
                        skillSp = new JSpinner(new SpinnerNumberModel(s
                                .getValue(), s.getValue(), skMax, 1));
                    }
                    skillSp.addChangeListener(new SkillKarmaRaise(karma, skMax, characterController));
                    skillSp.setName(s.getName());
                    panel.add(skillSp, constraints);

                    constraints.gridy += 1;
                    constraints.gridx -= 1;

                }

                constraints.gridy = biggestY;
                constraints.gridx = 3;
                panel.add(new JLabel("Knowledge Skills"), constraints);
                constraints.gridy += 1;
                currentAttr = Attribute.BODY;

                for (Skill s : knowSkills) {

                    if (s.getAttribute() != currentAttr) {
                        panel.add(new JLabel("  "), constraints);
                        constraints.gridy += 1;
                        currentAttr = s.getAttribute();
                        JLabel label = new JLabel(currentAttr.name());
                        label.setName(currentAttr.name());
                        panel.add(label, constraints);
                        constraints.gridy += 1;
                        panel.add(new JLabel("  "), constraints);
                        constraints.gridy += 1;
                    }

                    JLabel skill = new JLabel(s.getName());
                    skill.setName(skill.getText() + "Label");
                    panel.add(skill, constraints);
                    constraints.gridx += 1;
                    JSpinner skillSp = null;
                    if (s.getValue() >= skMax) {
                        skillSp = new JSpinner(new SpinnerNumberModel(s
                                .getValue(), s.getValue(), s.getValue(), 1));
                        skillSp.setEnabled(false);
                    } else {
                        skillSp = new JSpinner(new SpinnerNumberModel(s
                                .getValue(), s.getValue(), skMax, 1));
                    }
                    skillSp.addChangeListener(new SkillKarmaRaise(karma, skMax, characterController));
                    skillSp.setName(s.getName());
                    panel.add(skillSp, constraints);

                    constraints.gridy += 1;
                    constraints.gridx -= 1;

                }

                JButton addSkill = new JButton("Add Skill");
                JButton addSG = new JButton("Add SkillGroup");

                addSkill.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LR.loadSkillList();
                        ArrayList<Skill> newSkills = new ArrayList<Skill>();
                        for (Skill s : LR.getSkillList()) {
                            boolean isNew = false;
                            if (!characterController.getCharacter().getSkills().contains(s)) {
                                isNew = true;
                                for (SkillGroup sg : characterController.getCharacter()
                                        .getSkillGroups()) {
                                    if (sg.getSkills().contains(s)) {
                                        isNew = false;
                                        break;
                                    }
                                }
                            }
                            if (isNew) {
                                if (s.getAttribute().equals(Attribute.MAGIC)) {
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Magician)
                                            || characterController.getCharacter()
                                            .getMagicalness()
                                            .equals(Magical.MysticalAdept)) {
                                        newSkills.add(s);
                                    }
                                } else if (s.getAttribute().equals(
                                        Attribute.RESONANCE)) {
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Technomancer)) {
                                        newSkills.add(s);
                                    }
                                } else {
                                    newSkills.add(s);
                                }
                            }
                        }

                        JComboBox<Skill> skilllist = new JComboBox<Skill>(
                                newSkills.toArray(new Skill[newSkills.size()]));
                        JSpinner level = new JSpinner(new SpinnerNumberModel(1,
                                1, skMax, 1));
                        JComponent[] input = new JComponent[]{
                                new JLabel(
                                        "Choose the Skill you wish to learn:"),
                                skilllist,
                                new JLabel("Set the new Skill's value:"), level};
                        JOptionPane.showMessageDialog(contentPanel, input,
                                "Add Skill", JOptionPane.QUESTION_MESSAGE);
                        int cost = 0;
                        int multiplier = 2;
                        if (((Skill) skilllist.getSelectedItem()).isKnowledge()) {
                            multiplier = 1;
                        }
                        for (int i = (int) level.getValue(); i > 0; i--) {
                            cost += i * multiplier;
                        }
                        if (characterController.getCharacter().getPersonalData().getKarma() >= cost) {
                            Skill sk = (Skill) skilllist.getSelectedItem();
                            sk.setValue((int) level.getValue());
                            characterController.getCharacter().addSkill(sk);
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            karma.setValue(characterController.getCharacter().getPersonalData()
                                    .getKarma());
                            revalidate();
                            repaint();
                            skills.doClick();
                        } else {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma! Skill not added!",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                addSG.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LR.loadSkillGroupList();
                        ArrayList<SkillGroup> newSkills = new ArrayList<SkillGroup>();
                        for (SkillGroup s : LR.getSkillGroupList()) {
                            boolean isNew = false;
                            if (!characterController.getCharacter().getSkillGroups().contains(s)) {
                                isNew = true;
                            }
                            if (isNew) {
                                if (s.getName().equals("CONJURING")
                                        || s.getName().equals("ENCHANTING")
                                        || s.getName().equals("SORCERY")) {
                                    if (characterController.getCharacter().getMagicalness().equals(Magical.Magician) || characterController.getCharacter().getMagicalness().equals(Magical.MysticalAdept)) {
                                        newSkills.add(s);
                                    }
                                } else if (s.getName().equals("TASKING")) {
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Technomancer)) {
                                        newSkills.add(s);
                                    }
                                } else {
                                    newSkills.add(s);
                                }
                            }
                        }

                        JComboBox<SkillGroup> skilllist = new JComboBox<SkillGroup>(
                                newSkills.toArray(new SkillGroup[newSkills
                                        .size()]));
                        JSpinner level = new JSpinner(new SpinnerNumberModel(1,
                                1, skMax, 1));
                        JComponent[] input = new JComponent[]{
                                new JLabel(
                                        "Choose the SkillGroup you wish to learn:"),
                                skilllist,
                                new JLabel("Set the new SkillGroup's value:"),
                                level};
                        JOptionPane.showMessageDialog(contentPanel, input,
                                "Add SkillGroup", JOptionPane.QUESTION_MESSAGE);
                        int cost = 0;
                        int multiplier = 5;
                        for (int i = (int) level.getValue(); i > 0; i--) {
                            cost += i * multiplier;
                        }
                        if (characterController.getCharacter().getPersonalData().getKarma() >= cost) {
                            SkillGroup sk = (SkillGroup) skilllist
                                    .getSelectedItem();
                            sk.setValue((int) level.getValue());
                            characterController.getCharacter().addSkillGroup(sk);
                            characterController.getCharacter().getPersonalData().setKarma(
                                    characterController.getCharacter().getPersonalData()
                                            .getKarma() - cost);
                            karma.setValue(characterController.getCharacter().getPersonalData()
                                    .getKarma());
                            revalidate();
                            repaint();
                            skills.doClick();
                        } else {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough Karma! SkillGroup not added!",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                iPanel.add(addSkill);
                iPanel.add(addSG);
                infoPanel.add(iPanel, BorderLayout.CENTER);

                constraints.gridx = 0;
                constraints.gridy = 0;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.weightx = 1.0;
                constraints.weighty = 1.0;
                contentPanel.add(scroll, constraints);
                revalidate();
                repaint();
            }
        });
        coreCombatInfo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                GridBagConstraints con = new GridBagConstraints();
                // con.fill=GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel("Primary Armor:"), con);
                if (characterController.getCharacter().getArmor().isEmpty()) {
                    con.gridx = 1;
                    con.gridy = 0;
                    contentPanel.add(new JLabel("You do not own any Armor"),
                            con);
                } else {
                    con.gridx = 1;
                    con.gridy = 0;
                    contentPanel.add(new JLabel(characterController.getCharacter().getArmor()
                            .get(0).getName()), con);
                    con.gridx = 2;
                    contentPanel.add(new JLabel("Rating: "
                                    + characterController.getCharacter().getArmor().get(0).getRating()),
                            con);
                }
                con.gridx = 0;
                con.gridy = 1;
                contentPanel.add(new JLabel("Primary Ranged Weapon:"), con);
                if (characterController.getCharacter().getRangedWeapons().isEmpty()) {
                    con.gridx = 1;
                    con.gridy = 1;
                    contentPanel.add(new JLabel(
                            "You do not own any Ranged Weapons"), con);
                } else {
                    RangedWeapon rwp = characterController.getCharacter().getRangedWeapons().get(
                            0);
                    String modeString = "";
                    for (Mode m : rwp.getMode()) {
                        if (rwp.getMode().indexOf(m) > 0) {
                            modeString += "/" + m.name();
                        } else {
                            modeString += m.name();
                        }
                    }
                    con.gridx = 1;
                    contentPanel.add(new JLabel(rwp.getName()), con);
                    con.gridy = 2;
                    contentPanel.add(
                            new JLabel("Dam: " + rwp.getRealDamage() + " Acc: "
                                    + rwp.getRealAccuracy() + " AP: "
                                    + rwp.getRealArmorPiercing() + " Mode: "
                                    + modeString + " RC: " + rwp.getRecoil()
                                    + " Ammo: " + rwp.getRealAmmo() + "("
                                    + rwp.getAmmotype() + ")"), con);
                }
                con.gridx = 0;
                con.gridy = 3;
                contentPanel.add(new JLabel("Primary Melee Weapon:"), con);
                if (characterController.getCharacter().getMeeleWeapons().isEmpty()) {
                    con.gridx = 1;
                    con.gridy = 3;
                    contentPanel.add(new JLabel(
                            "You do not own any Melee Weapons"), con);
                } else {
                    MeeleWeapon mwp = characterController.getCharacter().getMeeleWeapons().get(0);
                    con.gridx = 1;
                    contentPanel.add(new JLabel(mwp.getName()), con);
                    con.gridy = 4;
                    contentPanel.add(
                            new JLabel("Reach:" + mwp.getReach() + " Dam: "
                                    + mwp.getDamage() + " Acc: "
                                    + mwp.getAccuracy() + " AP: " + mwp.getAP()),
                            con);
                }
                JButton changeArm = new JButton("Change Primary Armor");
                JButton changeRWP = new JButton("Change Primary Ranged Weapon");
                JButton changeMWP = new JButton("Change Primary Melee Weapon");

                changeArm.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (characterController.getCharacter().getArmor().isEmpty()) {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "You have no Armor/Clothing!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            Armor t = characterController.getCharacter().getArmor().get(0);
                            JComboBox<Armor> choice = new JComboBox<Armor>(
                                    characterController.getCharacter().getArmor().toArray(
                                            new Armor[characterController.getCharacter()
                                                    .getArmor().size()]));
                            choice.setSelectedIndex(0);
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose new primary armor:"),
                                    choice};
                            JOptionPane.showMessageDialog(contentPanel, inputs,
                                    "Set Primary Armor",
                                    JOptionPane.QUESTION_MESSAGE);
                            characterController.getCharacter().getArmor().set(
                                    0,
                                    characterController.getCharacter().getArmor().get(
                                            choice.getSelectedIndex()));
                            characterController.getCharacter().getArmor().set(
                                    choice.getSelectedIndex(), t);
                            coreCombatInfo.doClick();
                        }
                    }
                });

                changeRWP.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (characterController.getCharacter().getRangedWeapons().isEmpty()) {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "You have no Ranged Weapons!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            RangedWeapon t = characterController.getCharacter()
                                    .getRangedWeapons().get(0);
                            JComboBox<RangedWeapon> choice = new JComboBox<RangedWeapon>(
                                    characterController.getCharacter()
                                            .getRangedWeapons()
                                            .toArray(
                                                    new RangedWeapon[characterController.getCharacter()
                                                            .getRangedWeapons()
                                                            .size()]));
                            choice.setSelectedIndex(0);
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose new primary ranged weapon:"),
                                    choice};
                            JOptionPane.showMessageDialog(contentPanel, inputs,
                                    "Set Primary Ranged Weapon",
                                    JOptionPane.QUESTION_MESSAGE);
                            characterController.getCharacter().getRangedWeapons().set(
                                    0,
                                    characterController.getCharacter().getRangedWeapons().get(
                                            choice.getSelectedIndex()));
                            characterController.getCharacter().getRangedWeapons().set(
                                    choice.getSelectedIndex(), t);
                            coreCombatInfo.doClick();
                        }
                    }
                });

                changeMWP.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        if (characterController.getCharacter().getMeeleWeapons().isEmpty()) {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "You have no Melee Weapons!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            MeeleWeapon t = characterController.getCharacter().getMeeleWeapons()
                                    .get(0);
                            JComboBox<MeeleWeapon> choice = new JComboBox<MeeleWeapon>(
                                    characterController.getCharacter().getMeeleWeapons().toArray(
                                            new MeeleWeapon[characterController.getCharacter()
                                                    .getMeeleWeapons().size()]));
                            choice.setSelectedIndex(0);
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose new primary melee weapon:"),
                                    choice};
                            JOptionPane.showMessageDialog(contentPanel, inputs,
                                    "Set Primary Melee Weapon",
                                    JOptionPane.QUESTION_MESSAGE);
                            characterController.getCharacter().getMeeleWeapons().set(
                                    0,
                                    characterController.getCharacter().getMeeleWeapons().get(
                                            choice.getSelectedIndex()));
                            characterController.getCharacter().getMeeleWeapons().set(
                                    choice.getSelectedIndex(), t);
                            coreCombatInfo.doClick();
                        }
                    }
                });

                con.gridy += 1;
                contentPanel.add(new JLabel(" "), con);
                con.gridy += 1;
                contentPanel.add(changeArm, con);
                con.gridy += 1;
                contentPanel.add(changeRWP, con);
                con.gridy += 1;
                contentPanel.add(changeMWP, con);

                revalidate();
                repaint();

            }
        });
        conditionMonitor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                final JPanel physical = new JPanel(new GridBagLayout());
                final JPanel stun = new JPanel(new GridBagLayout());
                GridBagConstraints con = new GridBagConstraints();
                con.gridx = 0;
                con.gridy = 0;
                // con.gridwidth=2;
                // con.gridheight=2;
                con.fill = GridBagConstraints.BOTH;

                class monitorListener implements MouseListener {

                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                        JLabel pan = (JLabel) arg0.getSource();
                        if (pan.getBackground() != Color.gray) {
                            pan.setText("X");
                            pan.setBackground(Color.gray);
                        } else {
                            pan.setText(pan.getName());
                            pan.setBackground(null);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent arg0) {

                    }

                    @Override
                    public void mouseExited(MouseEvent arg0) {

                    }

                    @Override
                    public void mousePressed(MouseEvent arg0) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent arg0) {

                    }

                }

                double attr = ((double) characterController.getCharacter().getAttributes()
                        .getBody()) / 2;
                int monitor = 8 + (int) Math.ceil(attr);
                int count = 0;
                int rightPosition = 3;
                if (characterController.getCharacter().getAdeptPowers().contains(new Power("Pain Resistance", 0, 0))) {
                    rightPosition += characterController.getCharacter().getAdeptPowers().get(characterController.getCharacter().getAdeptPowers().indexOf(new Power("Pain Resistance", 0, 0))).getLevel();
                }
                for (int i = 0; i < monitor; i++) {
                    JLabel dmg = new JLabel();
                    dmg.setBorder(BorderFactory
                            .createLineBorder(Color.BLACK, 2));
                    dmg.setSize(10, 10);
                    dmg.addMouseListener(new monitorListener());
                    count++;
                    if (count == rightPosition) {
                        dmg.setText("-" + (con.gridy + 1));
                        dmg.setName("-" + (con.gridy + 1));
                        count = 0;
                    } else {
                        dmg.setText("   ");
                        dmg.setName("   ");
                    }
                    physical.add(dmg, con);
                    if (con.gridx < 2) {
                        con.gridx += 1;
                    } else {
                        con.gridx = 0;
                        con.gridy += 1;
                    }
                }
                con.gridx = 0;
                con.gridy = 0;
                attr = ((double) characterController.getCharacter().getAttributes()
                        .getWillpower()) / 2;
                monitor = 8 + (int) Math.ceil(attr);
                for (int i = 0; i < monitor; i++) {
                    JLabel dmg = new JLabel();
                    dmg.setBorder(BorderFactory
                            .createLineBorder(Color.BLACK, 2));
                    dmg.setSize(10, 10);
                    dmg.addMouseListener(new monitorListener());
                    if (con.gridx < 2) {
                        dmg.setText("   ");
                        dmg.setName("   ");
                        stun.add(dmg, con);
                        con.gridx += 1;
                    } else {
                        dmg.setText("-" + (con.gridy + 1));
                        dmg.setName("-" + (con.gridy + 1));
                        stun.add(dmg, con);
                        con.gridx = 0;
                        con.gridy += 1;

                    }
                }
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel("Physical Damage Track"), con);
                con.gridy += 1;
                contentPanel.add(physical, con);
                con.gridx = 1;
                con.gridy = 0;
                contentPanel.add(new JLabel("Stun Damage Track"), con);
                con.gridy += 1;
                contentPanel.add(stun, con);
                revalidate();
                repaint();

            }
        });
        qualities.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                final JPanel panelN = new JPanel(new GridBagLayout());
                final JPanel panelP = new JPanel(new GridBagLayout());
                final JScrollPane scrollPos = new JScrollPane(panelP);
                final JScrollPane scrollNeg = new JScrollPane(panelN);
                scrollPos.setSize(100, 100);
                scrollNeg.setSize(100, 100);
                GridBagConstraints con = new GridBagConstraints();
                con.gridx = 0;
                con.gridy = 0;
                for (Quality q : characterController.getCharacter().getQualities()) {
                    if (q.isPositive()) {
                        panelP.add(new JLabel(q.toString()), con);
                    } else {
                        panelN.add(new JLabel(q.toString()), con);
                    }
                    con.gridy += 1;
                }
                con.fill = GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                contentPanel.add(new JLabel("Positive Qualities"), con);
                con.gridy += 1;
                con.weightx = 2;
                con.weighty = 1;
                contentPanel.add(scrollPos, con);
                con.weightx = 0;
                con.weighty = 0;
                JButton addPos = new JButton("New Positive Quality");
                con.gridy += 1;
                contentPanel.add(addPos, con);
                // con.gridx=1;
                con.gridy += 1;
                contentPanel.add(new JLabel("Negative Qualities"), con);
                con.gridy += 1;
                con.weightx = 2;
                con.weighty = 1;
                contentPanel.add(scrollNeg, con);
                con.weightx = 0;
                con.weighty = 0;
                JButton addNeg = new JButton("Add Negative Quality");
                con.gridy += 1;
                contentPanel.add(addNeg, con);
                JButton removeNeg = new JButton("Remove Negative Quality");
                con.gridy += 1;
                contentPanel.add(removeNeg, con);

                addPos.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LR.loadQualityList();
                        ArrayList<Quality> newQual = new ArrayList<Quality>();
                        for (Quality q : LR.getQualityList()) {
                            if (q.isPositive()) {
                                if (!characterController.getCharacter().getQualities()
                                        .contains(q)) {
                                    newQual.add(q);
                                }
                            }
                        }
                        JComboBox<Quality> INITchoice = new JComboBox<Quality>(
                                newQual.toArray(new Quality[newQual.size()]));
                        JComboBox<String> gmAdded = new JComboBox<String>();
                        gmAdded.addItem("no");
                        gmAdded.addItem("yes");
                        JComponent[] INITinputs = new JComponent[]{
                                new JLabel("Please choose new Quality:"),
                                INITchoice,
                                new JLabel("Is this added per GM request?"),
                                gmAdded};
                        if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(contentPanel, INITinputs,
                                "Add Quality", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            return;
                        }
                        if (characterController.getCharacter().getPersonalData().getKarma()
                                + ((Quality) INITchoice.getSelectedItem())
                                .getAPcost_bonus() >= 0) {
                            if (gmAdded.getSelectedIndex() == 0) {
                                int cost = characterController.getCharacter().getPersonalData()
                                        .getKarma()
                                        + ((Quality) INITchoice
                                        .getSelectedItem())
                                        .getAPcost_bonus();
                                characterController.getCharacter().getPersonalData().setKarma(
                                        cost);
                            }
                            Quality q = (Quality) INITchoice.getSelectedItem();
                            boolean allowedToAdd = true;
                            if (q.isLeveled()) {
                                JSpinner level = new JSpinner(
                                        new SpinnerNumberModel(1, 1, q
                                                .getMaxLevel(), 1));
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel("Set " + q.getName()
                                                + " level!"), level};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                q.setLevel((int) level.getValue());
                            }
                            if (q.getName().equals("Aptitude")
                                    && !characterController.getCharacter().getQualities()
                                    .contains(q)) {
                                Vector<Skill> skillList = new Vector<>(LR
                                        .getSkillList().size());
                                skillList.addAll(activeSkills);
                                skillList.addAll(knowledgeSkills);
                                if (characterController.getCharacter().getMagicalness().equals(
                                        Magical.Magician)
                                        || characterController.getCharacter().getMagicalness()
                                        .equals(Magical.MysticalAdept)) {
                                    skillList.addAll(magicSkills);
                                } else if (characterController.getCharacter().getMagicalness()
                                        .equals(Magical.Technomancer)) {
                                    skillList.addAll(resonanceSkills);
                                } else if (characterController.getCharacter().getMagicalness()
                                        .equals(Magical.AspectedMagician)) {
                                    skillList.addAll(characterController.getCharacter()
                                            .getChosenAspectedSkillGroup()
                                            .getSkills());
                                }
                                JComboBox<Skill> skills = new JComboBox<Skill>(
                                        skillList);
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel("Please choose a skill!"),
                                        skills};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                q.setNotes(((Skill) skills.getSelectedItem())
                                        .getName());
                            } else if (q.getName().equals("Astral Chameleon")) {
                                if (!(characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                                        || characterController.getCharacter().getMagicalness().equals(Magical.MysticalAdept)
                                        || characterController.getCharacter().getMagicalness().equals(Magical.AspectedMagician))) {
                                    allowedToAdd = false;
                                    JOptionPane
                                            .showMessageDialog(
                                                    panel,
                                                    "Only magicians can choose this Quality!",
                                                    "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            } else if (q.getName().equals("Bilingual")) {
                                JComboBox<Skill> skills = new JComboBox<Skill>();
                                for (Skill s : knowledgeSkills) {
                                    if (s.getAttribute().equals(
                                            Skill.Attribute.INTUITION)
                                            && !(s.getName().contains("(S)")
                                            || s.getName().contains("(I)")
                                            || s.getName().contains("Street")
                                            || s.getName().contains("Interests")
                                            || s.getName().equals("Language"))) {
                                        skills.addItem(s);
                                    }
                                }
                                JTextField language = new JTextField();
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose a second native language!"),
                                        skills,
                                        new JLabel(
                                                "OR enter a new Language in the field below!"),
                                        language};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                Skill lang = null;
                                if (language.getText().isEmpty()) {
                                    lang = (Skill) skills.getSelectedItem();
                                } else {
                                    lang = new Skill();
                                    lang.setName(language.getText());
                                    lang.setAttribute(Skill.Attribute.INTUITION);
                                    lang.setKnowledge(true);
                                }
                                lang.setValue(14);
                                q.setNotes(lang.getName());
                                if (characterController.getCharacter().getSkills().contains(lang)) {
                                    characterController.getCharacter()
                                            .getSkills()
                                            .get(characterController.getCharacter().getSkills()
                                                    .indexOf(lang))
                                            .setValue(14);
                                } else {
                                    characterController.getCharacter().addSkill(lang);
                                }
                            } else if (q.getName().equals("Codeslinger")
                                    || q.getName().equals("Codeblock")) {
                                JComboBox<String> actions = new JComboBox<String>();
                                actions.addItem("Brute Force");
                                actions.addItem("Check Overwatch Score");
                                actions.addItem("Controll device");
                                actions.addItem("Crack File");
                                actions.addItem("Crash Program");
                                actions.addItem("Data Spike");
                                actions.addItem("Disarm Data Bomb");
                                actions.addItem("Edit File");
                                actions.addItem("Erase Mark");
                                actions.addItem("Erase Matrix Signature");
                                actions.addItem("Format Device");
                                actions.addItem("Hack on the fly");
                                actions.addItem("Hide");
                                actions.addItem("Jack out");
                                actions.addItem("Jam Signals");
                                actions.addItem("Jump into rigged Device");
                                actions.addItem("Matrix Perception");
                                actions.addItem("Matrix search");
                                actions.addItem("Reboot Device");
                                actions.addItem("Set Data Bomb");
                                actions.addItem("Snoop");
                                actions.addItem("Spoof Command");
                                actions.addItem("Trace Icons");
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose a matrix action!"),
                                        actions};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                if (q.getName().equals("Codeslinger")) {
                                    q.setNotes("+2 dice pool modifier to "
                                            + (String) actions
                                            .getSelectedItem());
                                } else {
                                    q.setNotes("-2 dice pool modifier to "
                                            + (String) actions
                                            .getSelectedItem());
                                }
                            } else if (q.getName().equals(
                                    "Exceptional Attribute")) {
                                JComboBox<Attribute> attributes = new JComboBox<Attribute>(
                                        Attribute.values());
                                if (characterController.getCharacter().getMagicalness().equals(
                                        Magical.Mundane)) {
                                    attributes.removeItem(Attribute.MAGIC);
                                    attributes.removeItem(Attribute.RESONANCE);
                                } else if (characterController.getCharacter().getMagicalness()
                                        .equals(Magical.Technomancer)) {
                                    attributes.removeItem(Attribute.MAGIC);
                                }
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose an attribute!"),
                                        attributes};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                switch ((Attribute) attributes
                                        .getSelectedItem()) {
                                    case BODY:
                                        characterController.getCharacter().getAttributes()
                                                .setMAXbody(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXbody() + 1);
                                        break;
                                    case AGILITY:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXagility(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXagility() + 1);
                                        break;
                                    case REACTION:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXreaction(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXreaction() + 1);
                                        break;
                                    case STRENGTH:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXstrength(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXstrength() + 1);
                                        break;
                                    case WILLPOWER:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXwillpower(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXwillpower() + 1);
                                        break;
                                    case LOGIC:
                                        characterController.getCharacter().getAttributes()
                                                .setMAXlogic(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXlogic() + 1);
                                        break;
                                    case INTUITION:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXintuition(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXintuition() + 1);
                                        break;
                                    case CHARISMA:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXcharisma(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXcharisma() + 1);
                                        break;
                                    case MAGIC:
                                        characterController.getCharacter().getAttributes()
                                                .setMAXmagic(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXmagic() + 1);
                                        break;
                                    case RESONANCE:
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMAXresonance(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMAXresonance() + 1);
                                        break;
                                }
                            } else if (q.getName().equals("Blandness")) {
                                characterController.getCharacter().getPersonalData().addNotoriety(-1);
                            } else if (q.getName().equals("First Impression")) {
                                characterController.getCharacter().getPersonalData().addNotoriety(-1);
                            } else if (q.getName().equals("Home Ground")) {
                                JComboBox<String> choice = new JComboBox<String>();
                                choice.addItem("Astral Acclimation");
                                choice.addItem("You Know A Guy");
                                choice.addItem("Digital Turf");
                                choice.addItem("The Transporter");
                                choice.addItem("On the Lam");
                                choice.addItem("Street Politics");
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose an Home Ground!"),
                                        choice};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                q.setNotes((String) choice.getSelectedItem());
                            } else if (q.getName().equals("Indomitable")) {
                                JComboBox<String> choice = new JComboBox<String>();
                                choice.addItem("Mental Limit");
                                choice.addItem("Physical Limit");
                                choice.addItem("Social Limit");
                                final JComponent[] inputs = new JComponent[]{
                                        new JLabel(
                                                "Please choose limit to increase!"),
                                        choice};
                                int lev = q.getLevel();
                                for (; lev > 0; lev--) {
                                    JOptionPane.showMessageDialog(panel,
                                            inputs, q.getName(),
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (choice.getSelectedItem().equals(
                                            "Mental Limit")) {
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setMentalLimit(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getMentalLimit() + 1);
                                    } else if (choice.getSelectedItem().equals(
                                            "Physical Limit")) {
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setPhysicalLimit(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getPhysicalLimit() + 1);
                                    } else if (choice.getSelectedItem().equals(
                                            "Social Limit")) {
                                        characterController.getCharacter()
                                                .getAttributes()
                                                .setSocialLimit(
                                                        characterController.getCharacter()
                                                                .getAttributes()
                                                                .getSocialLimit() + 1);
                                    }
                                }
                            } else if (q.getName().equals("Lucky")) {
                                characterController.getCharacter().getPersonalData().addNotoriety(-1);
                                characterController.getCharacter().getAttributes().setEdge(
                                        characterController.getCharacter().getAttributes()
                                                .getEdge() + 1);
                            } else if (q.getName().equals("Magical Resistance")) {
                                if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                                    allowedToAdd = false;
                                    JOptionPane
                                            .showMessageDialog(
                                                    panel,
                                                    "This quality can't be chosen by magical characters!",
                                                    "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                } else {
                                    q.setNotes(q.getLevel()
                                            + " dice pool modifier to Spell Resistance Tests");
                                }
                            } else if (q.getName()
                                    .equals("High Pain Tolerance")) {
                                q.setNotes("Ignore "
                                        + q.getLevel()
                                        + " boxes of damage when calculating wound modifiers");
                            } else if (q.getName().equals("Mentor Spirit")) {
                                if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                                    class MentorSpirit {
                                        String name;
                                        String advAll;
                                        String advMag;
                                        String advAde;
                                        String disAdv;

                                        private MentorSpirit(String name,
                                                             String advAll, String advMag,
                                                             String advAde, String disAdv) {
                                            this.name = name;
                                            this.advAll = advAll;
                                            this.advMag = advMag;
                                            this.advAde = advAde;
                                            this.disAdv = disAdv;
                                        }
                                    }

                                    MentorSpirit bear = new MentorSpirit(
                                            "Bear",
                                            "+2 d.p.m for test to resist damage (not including Drain)",
                                            "+2 d.p.m. for health spells, preperations and rituals",
                                            "1 free level of Rapid Healing",
                                            "Charisma+Willpower Test if taking physical damage, if failed go berserk for 3 turns -1 turn/hit");
                                    MentorSpirit cat = new MentorSpirit(
                                            "Cat",
                                            "+2 d.p.m for Gymnastics or Infiltration Tests",
                                            "+2 d.p.m. for Illusion spells, preperations and rituals",
                                            "2 free levels of Light Body",
                                            "Charisma+Willpower(3) Test at  start of combat, if failed you cannot make an attack tha incapacitates your target until you take Physical damage");
                                    MentorSpirit dog = new MentorSpirit(
                                            "Dog",
                                            "+2 d.p.m for Tracking tests",
                                            "+2 d.p.m. for Detection spells, preperations and rituals",
                                            "2 free Improved Sense powers",
                                            "Charisma+Willpower(3) Test, if failed you can never leave someone behind, betray your comrades, or let anyone sacrifice themselves for you");
                                    MentorSpirit dragonslayer = new MentorSpirit(
                                            "Dragonslayer",
                                            "+2 d.p.m for one Social Skill of choice",
                                            "+2 d.p.m. for Combat spells, preperations and rituals",
                                            "1 free level of Enhanced Accuracy (skill) and 1 free level of Danger Sense",
                                            "-1 d.p.m on all actions if you break a promise, until you make good on it");
                                    MentorSpirit eagle = new MentorSpirit(
                                            "Eagle",
                                            "+2 d.p.m for Perception Tests",
                                            "+2 d.p.m. for summoning spirits of air",
                                            "1 free level of Combat Sense",
                                            "You get Allergy (pollutants, mild)");
                                    MentorSpirit firebringer = new MentorSpirit(
                                            "Fire-Bringer",
                                            "+2 d.p.m for Artisan or Alchemy skill tests",
                                            "+2 d.p.m. for Manipulation spells, preperations and rituals",
                                            "1 free level of Improved Ability on a non-combat skill",
                                            "Charisma+Willpower(3) Test, if failed you can not refuse if sincerely asked for help");
                                    MentorSpirit mountain = new MentorSpirit(
                                            "Mountain",
                                            "+2 d.p.m for Survival Tests",
                                            "+2 d.p.m. for Counterspelling Tests and anchored rituals",
                                            "1 free level of Mystic Armor",
                                            "Charisma+Willpower(3) Test if trying to abandon a plan in favor of another one or trying to do anything without a plan, if failed stick to the original plan/make a plan");
                                    MentorSpirit rat = new MentorSpirit(
                                            "Rat",
                                            "+2 d.p.m for Sneaking Tests",
                                            "+2 d.p.m. for Alchemy tests when harvesting reagents, and may use reagents of any tradition",
                                            "2 free levels of Natural Immunity",
                                            "Charisma+Willpower(3) Test to not immediately flee or seek cover in combat situations");
                                    MentorSpirit raven = new MentorSpirit(
                                            "Raven",
                                            "+2 d.p.m for Con Tests",
                                            "+2 d.p.m. for Manipulation spells, preperations and rituals",
                                            "Free Traceless Walk and 1 level of Voice Control",
                                            "Charisma+Willpower(3) Test to avoid exploiting someones misfortune or pull a trick or prank even if it's to your friends disadvantage");
                                    MentorSpirit sea = new MentorSpirit(
                                            "Sea",
                                            "+2 d.p.m for Swimming Tests",
                                            "+2 d.p.m. for summoning spirits of water",
                                            "1 free level of Improved Ability on an athletic skill",
                                            "Charisma+Willpower(3) Test to give away something of your own, or be charitable in any way");
                                    MentorSpirit seducer = new MentorSpirit(
                                            "Seducer",
                                            "+2 d.p.m for Con Tests",
                                            "+2 d.p.m. for Illusion spells, preperations and rituals",
                                            "1 free level of Improved Ability on a skill in the Acting or Influence skill group",
                                            "Charisma+Willpower(3) Test to avoid pursuing a vice or indlugence (drugs,BTLs,sex,etc.)");
                                    MentorSpirit shark = new MentorSpirit(
                                            "Shark",
                                            "+2 d.p.m for Unarmed Combat tests",
                                            "+2 d.p.m. for Combat spells, preperations and rituals",
                                            "Free Killing Hands",
                                            "Charisma+Willpower Test if taking physical damage, if failed go berserk for 3 turns -1 turn/hit");
                                    MentorSpirit snake = new MentorSpirit(
                                            "Snake",
                                            "+2 d.p.m for Arcana tests",
                                            "+2 d.p.m. for Detection spells, preperations and rituals",
                                            "2 free levels of Kinesics",
                                            "Charisma+Willpower(3) Test to avoid pursing secrets or knowledge that few know about when you receive hints fo its existence");
                                    MentorSpirit thunderbird = new MentorSpirit(
                                            "Thunderbird",
                                            "+2 d.p.m for Intimidation Tests",
                                            "+2 d.p.m. for summoning spirits of air",
                                            "1 free level of Critical Strike (skill)",
                                            "Charisma+Willpower(3) Test to avoid responding to an insult in kind");
                                    MentorSpirit wisewarrior = new MentorSpirit(
                                            "Wise Warrior",
                                            "+2 d.p.m for Leadership or Instruction Tests",
                                            "+2 d.p.m. for Combat spells, preperations and rituals",
                                            "1 free level of Improved Ability on a Combat skill",
                                            "-1 d.p.m on all actions if you act dishonorably or without courtesy, until you atone for your behavior");
                                    MentorSpirit wolf = new MentorSpirit(
                                            "Wolf",
                                            "+2 d.p.m for Tracking Tests",
                                            "+2 d.p.m. for Combat spells, preperations and rituals",
                                            "2 free level of Attribute Boost(Agility)",
                                            "Charisma+Willpower(3) Test to retreat from a fight");

                                    MentorSpirit spirits[] = {bear, cat, dog,
                                            dragonslayer, eagle, firebringer,
                                            mountain, rat, raven, sea, seducer,
                                            shark, snake, thunderbird,
                                            wisewarrior, wolf};
                                    JComboBox<MentorSpirit> choice = new JComboBox<MentorSpirit>(
                                            spirits);
                                    choice.setRenderer(new DefaultListCellRenderer() {
                                        @Override
                                        public Component getListCellRendererComponent(
                                                JList<?> list, Object value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
                                            if (value instanceof MentorSpirit) {
                                                MentorSpirit m = ((MentorSpirit) value);
                                                String text = "<html><p>All: "
                                                        + m.advAll + "</p>";
                                                if (characterController.getCharacter()
                                                        .getMagicalness()
                                                        .equals(Magical.Magician)) {
                                                    text += "<p>Magician: "
                                                            + m.advMag + "</p>";
                                                } else if (characterController.getCharacter()
                                                        .getMagicalness()
                                                        .equals(Magical.Adept)) {
                                                    text += "<p>Adept: "
                                                            + m.advAde + "</p>";
                                                } else if (characterController.getCharacter()
                                                        .getMagicalness()
                                                        .equals(Magical.MysticalAdept)) {
                                                    text += "<p>Magician: "
                                                            + m.advMag + "</p>";
                                                    text += "<p>Adept: "
                                                            + m.advAde + "</p>";
                                                }
                                                text += "<p>Disadvantage: "
                                                        + m.disAdv
                                                        + "</p></html>";
                                                setToolTipText(text);
                                                value = m.name;
                                            } else {
                                                setToolTipText(null);
                                            }
                                            return super
                                                    .getListCellRendererComponent(
                                                            list, value, index,
                                                            isSelected,
                                                            cellHasFocus);
                                        }
                                    });

                                    JComponent[] inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose a mentor spirit!"),
                                            choice};
                                    JOptionPane.showMessageDialog(panel,
                                            inputs, q.getName(),
                                            JOptionPane.QUESTION_MESSAGE);
                                    String chosen = ((MentorSpirit) choice
                                            .getSelectedItem()).name;
                                    MentorSpirit chosenSpirit = null;
                                    // handle powers when choosing them!
                                    if (chosen.equals("Bear")) {
                                        chosenSpirit = bear;
                                    } else if (chosen.equals("Cat")) {
                                        chosenSpirit = cat;
                                        JComboBox<String> skills = new JComboBox<String>(
                                                new String[]{"Gymnastics",
                                                        "Infiltration"});
                                        inputs = new JComponent[]{
                                                new JLabel(
                                                        "Please choose which skill you want +2 dice for!"),
                                                skills};
                                        JOptionPane.showMessageDialog(panel,
                                                inputs, q.getName(),
                                                JOptionPane.QUESTION_MESSAGE);
                                        chosenSpirit.advAll = "+2 d.p.m for "
                                                + (String) skills
                                                .getSelectedItem()
                                                + " Tests";
                                    } else if (chosen.equals("Dog")) {
                                        chosenSpirit = dog;
                                    } else if (chosen.equals("Dragonslayer")) {
                                        chosenSpirit = dragonslayer;
                                        JComboBox<Skill> skills = new JComboBox<Skill>();
                                        for (Skill s : activeSkills) {
                                            if (s.getAttribute().equals(
                                                    Skill.Attribute.CHARISMA)
                                                    && !s.getName().equals(
                                                    "Animal Handling")) {
                                                skills.addItem(s);
                                            }
                                        }
                                        inputs = new JComponent[]{
                                                new JLabel(
                                                        "Please choose which skill you want +2 dice for!"),
                                                skills};
                                        JOptionPane.showMessageDialog(panel,
                                                inputs, q.getName(),
                                                JOptionPane.QUESTION_MESSAGE);
                                        chosenSpirit.advAll = "+2 d.p.m for "
                                                + ((Skill) skills
                                                .getSelectedItem())
                                                .getName();
                                    } else if (chosen.equals("Eagle")) {
                                        chosenSpirit = eagle;
                                        Quality allergy = new Quality();
                                        allergy.setName("Allergy (Pollutants)(Mild)");
                                        allergy.setNotes("-2 d.p.m. to Physical Tests while experiencing symptoms");
                                        allergy.setPositive(false);
                                        allergy.setLeveled(false);
                                        allergy.setAPcost_bonus(0);
                                        characterController.getCharacter().addQuality(allergy);
                                    } else if (chosen.equals("Fire-Bringer")) {
                                        chosenSpirit = firebringer;
                                        JComboBox<String> skills = new JComboBox<String>(
                                                new String[]{"Artisan",
                                                        "Alchemy"});
                                        inputs = new JComponent[]{
                                                new JLabel(
                                                        "Please choose which skill you want +2 dice for!"),
                                                skills};
                                        JOptionPane.showMessageDialog(panel,
                                                inputs, q.getName(),
                                                JOptionPane.QUESTION_MESSAGE);
                                        chosenSpirit.advAll = "+2 d.p.m for "
                                                + (String) skills
                                                .getSelectedItem()
                                                + " Tests";
                                    } else if (chosen.equals("Mountain")) {
                                        chosenSpirit = mountain;
                                    } else if (chosen.equals("Rat")) {
                                        chosenSpirit = rat;
                                    } else if (chosen.equals("Raven")) {
                                        chosenSpirit = raven;
                                    } else if (chosen.equals("Sea")) {
                                        chosenSpirit = sea;
                                    } else if (chosen.equals("Seducer")) {
                                        chosenSpirit = seducer;
                                    } else if (chosen.equals("Shark")) {
                                        chosenSpirit = shark;
                                    } else if (chosen.equals("Snake")) {
                                        chosenSpirit = snake;
                                    } else if (chosen.equals("Thunderbird")) {
                                        chosenSpirit = thunderbird;
                                    } else if (chosen.equals("Wise Warrior")) {
                                        chosenSpirit = wisewarrior;
                                        JComboBox<String> skills = new JComboBox<String>(
                                                new String[]{"Leadership",
                                                        "Instruction"});
                                        inputs = new JComponent[]{
                                                new JLabel(
                                                        "Please choose which skill you want +2 dice for!"),
                                                skills};
                                        JOptionPane.showMessageDialog(panel,
                                                inputs, q.getName(),
                                                JOptionPane.QUESTION_MESSAGE);
                                        chosenSpirit.advAll = "+2 d.p.m for "
                                                + (String) skills
                                                .getSelectedItem()
                                                + " Tests";
                                    } else if (chosen.equals("Wolf")) {
                                        chosenSpirit = wolf;
                                    }
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Magician)) {
                                        q.setName("Mentor Spirit: "
                                                + chosenSpirit.name);
                                        q.setNotes(chosenSpirit.advAll + ", "
                                                + chosenSpirit.advMag + ", "
                                                + chosenSpirit.disAdv);
                                    } else if (characterController.getCharacter()
                                            .getMagicalness().equals(
                                                    Magical.Adept)) {
                                        q.setName("Mentor Spirit: "
                                                + chosenSpirit.name);
                                        q.setNotes(chosenSpirit.advAll + ", "
                                                + chosenSpirit.advAde + ", "
                                                + chosenSpirit.disAdv);
                                    } else if (characterController.getCharacter()
                                            .getMagicalness().equals(
                                                    Magical.MysticalAdept)) {
                                        q.setName("Mentor Spirit: "
                                                + chosenSpirit.name);
                                        int index = JOptionPane
                                                .showOptionDialog(
                                                        panel,
                                                        "Please choose if you wish to take the Magician or Adept advantage",
                                                        "Magician or Adept",
                                                        JOptionPane.DEFAULT_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE,
                                                        null, new String[]{
                                                                "Magician",
                                                                "Adept"}, 0);
                                        if (index == 0) {
                                            q.setNotes(chosenSpirit.advAll
                                                    + ", "
                                                    + chosenSpirit.advMag
                                                    + ", "
                                                    + chosenSpirit.disAdv);
                                        } else {
                                            q.setNotes(chosenSpirit.advAll
                                                    + ", "
                                                    + chosenSpirit.advAde
                                                    + ", "
                                                    + chosenSpirit.disAdv);
                                        }
                                    }
                                } else {
                                    allowedToAdd = false;
                                    JOptionPane
                                            .showMessageDialog(
                                                    panel,
                                                    "This quality can only be chosen by magical characters!",
                                                    "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            } else if (q.getName().contains("Natural Immunity")) {
                                JTextField disease = new JTextField();
                                String label = null;
                                if (q.getName().contains("(natural")) {
                                    label = "Please enter the name of the natural disease or toxin you want immunity against!";
                                } else {
                                    label = "Please enter the name of the synthetic disease or toxin you want immunity against!";
                                }
                                JComponent[] inputs = new JComponent[]{
                                        new JLabel(label), disease};
                                JOptionPane.showMessageDialog(panel, inputs,
                                        q.getName(),
                                        JOptionPane.QUESTION_MESSAGE);
                                q.setName("Natural Immunity ("
                                        + disease.getText() + ")");
                            } else if (q.getName().equals("Spirit Affinity")
                                    || q.getName().equals("Spirit Bane")) {
                                if (characterController.getCharacter().getAttributes().getMagic() > 0) {
                                    JComboBox<String> choice = new JComboBox<String>();
                                    choice.addItem("Spirits of Air");
                                    choice.addItem("Spirits of Beasts");
                                    choice.addItem("Spirits of Earth");
                                    choice.addItem("Spirits of Fire");
                                    choice.addItem("Spirits of Man");
                                    choice.addItem("Spirits of Water");
                                    final JComponent[] inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose a type of Spirit!"),
                                            choice};
                                    JOptionPane.showMessageDialog(panel,
                                            inputs, q.getName(),
                                            JOptionPane.QUESTION_MESSAGE);
                                    if (q.getName().equals("Spirit Affinity")) {
                                        q.setNotes("1 additional service for each "
                                                + (String) choice
                                                .getSelectedItem()
                                                + ". +1 d.p.m. for Binding tests");
                                    } else {
                                        characterController.getCharacter().getPersonalData().addNotoriety(1);
                                        q.setNotes("-2 d.p.m. for Summoning or Binding "
                                                + (String) choice
                                                .getSelectedItem()
                                                + ". +2 d.p.m. for Spirit when trying to Banish");
                                    }
                                } else {
                                    allowedToAdd = false;
                                    JOptionPane
                                            .showMessageDialog(
                                                    panel,
                                                    "This quality can only be chosen by magic users!",
                                                    "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            } else if (q.getName().equals("Will to Live")) {
                                q.setNotes("+" + q.getLevel()
                                        + " Damage Overflow Box(es)");
                                characterController.getCharacter()
                                        .setDamageOverflow(characterController.getCharacter()
                                                .getDamageOverflow()
                                                + q.getLevel());
                            }

                            if (allowedToAdd) {
                                characterController.getCharacter().addQuality(q);
                                karma.setValue(characterController.getCharacter()
                                        .getPersonalData().getKarma());
                                qualities.doClick();
                            }
                        } else {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough karma!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                addNeg.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LR.loadQualityList();
                        ArrayList<Quality> newQual = new ArrayList<Quality>();
                        for (Quality q : LR.getQualityList()) {
                            if (!q.isPositive()) {
                                if (!characterController.getCharacter().getQualities()
                                        .contains(q)) {
                                    newQual.add(q);
                                }
                            }
                        }
                        JComboBox<Quality> INITchoice = new JComboBox<Quality>(
                                newQual.toArray(new Quality[newQual.size()]));
                        JComponent[] INITinputs = new JComponent[]{
                                new JLabel(
                                        "Please choose new Quality: (This can only happen per GM request and you will get no additional karma!)"),
                                INITchoice,};
                        if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(contentPanel, INITinputs,
                                "Add Quality", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            return;
                        }
                        Quality q = (Quality) INITchoice.getSelectedItem();
                        boolean allowedToAdd = true;

                        if (q.getName().contains("Addiction")) {
                            JTextField addiction = new JTextField();
                            String label = "Please enter what drug or activity your character is addicted to!";
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(label), addiction};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            String kind = null;
                            if (q.getName().contains("(Mild)")) {
                                kind = "(Mild)";
                            } else if (q.getName().contains("(Moderate)")) {
                                kind = "(Moderate)";
                            } else if (q.getName().contains("(Severe)")) {
                                kind = "(Severe)";
                            } else if (q.getName().contains("(Burnout)")) {
                                kind = "(Burnout)";
                            }
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                            q.setName("Addiction (" + addiction.getText() + ")"
                                    + kind);
                        } else if (q.getName().equals("Bad Luck")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().equals("Combat Paralysis")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().equals("Elf Poser")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().equals("Ork Poser")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().equals("SINner (Criminal)")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().contains("Allergy")) {
                            JTextField allergy = new JTextField();
                            String environment = null;
                            String examples = null;
                            if (q.getName().contains("(Uncommon)")) {
                                environment = "rare for";
                                examples = "silver, gold, antibiotics, grass.";
                            } else {
                                environment = "prevalent in";
                                examples = "sunlight, seafood, bees, pollen, pollutants, Wi-Fi sensitivity, soy, wheat";
                            }
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please enter what your character is allergic to!"),
                                    new JLabel(
                                            "The sustance or condition is "
                                                    + environment
                                                    + " the local environment. Examples: "
                                                    + examples), allergy};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            String kind = null;
                            if (q.getName().contains("(Mild)")) {
                                kind = "(Mild)";
                            } else if (q.getName().contains("(Moderate)")) {
                                kind = "(Moderate)";
                            } else if (q.getName().contains("(Severe)")) {
                                kind = "(Severe)";
                            } else if (q.getName().contains("(Extreme)")) {
                                kind = "(Extreme)";
                            }
                            q.setName("Allergy (" + allergy.getText() + ")"
                                    + kind);
                        } else if (q.getName().equals("Astral Beacon")
                                && !(characterController.getCharacter().getAttributes()
                                .getMagic() > 0)) {
                            allowedToAdd = false;
                            JOptionPane
                                    .showMessageDialog(
                                            panel,
                                            "This quality can only be chosen by magic users!",
                                            "Error", JOptionPane.ERROR_MESSAGE);

                        } else if (q.getName().equals("Bad Rep")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(3);
                        } else if (q.getName().contains("Code of Honor")) {
                            JTextField code = new JTextField();
                            String label = "Please enter what group your character's code protects/what creed your character follows!";
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(label), code};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            q.setNotes("Must make a Charisma+Willpower (4) Test when attempting to kill a "
                                    + code + "/breaking the " + code);
                        } else if (q.getName().equals("Gremlins")) {
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                            q.setNotes(q.getLevel()
                                    + " less rolled 1s needed to get a glitch (per level) when using a moderatly sophisticated device.");
                        } else if (q.getName().equals("Incompetent")) {
                            JComboBox<SkillGroup> skillgroup = new JComboBox<SkillGroup>();
                            for (SkillGroup sg : LR.getSkillGroupList()) {
                                if (sg.getName().equals("TASKING")) {
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Technomancer)) {
                                        skillgroup.addItem(sg);
                                    }
                                } else if (sg.getName().equals("CONJURING")
                                        || sg.getName().equals("ENCHANTING")
                                        || sg.getName().equals("SORCERY")) {
                                    if (characterController.getCharacter().getMagicalness()
                                            .equals(Magical.Magician)
                                            || characterController.getCharacter()
                                            .getMagicalness()
                                            .equals(Magical.MysticalAdept)
                                            || (characterController.getCharacter()
                                            .getMagicalness()
                                            .equals(Magical.AspectedMagician) && characterController.getCharacter()
                                            .getChosenAspectedSkillGroup()
                                            .equals(sg))) {
                                        skillgroup.addItem(sg);
                                    }
                                } else {
                                    skillgroup.addItem(sg);
                                }

                            }

                            final JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose the skillgroup your character is incompetent at!"),
                                    skillgroup};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            SkillGroup selection = (SkillGroup) skillgroup
                                    .getSelectedItem();
                            q.setNotes("Incompetent in " + selection.getName());
                            if (characterController.getCharacter().getSkillGroups().contains(
                                    selection)) {
                                characterController.getCharacter()
                                        .getSkillGroups()
                                        .get(characterController.getCharacter().getSkillGroups()
                                                .indexOf(selection))
                                        .setValue(-1);
                                ;
                            } else {
                                selection.setValue(-1);
                                characterController.getCharacter().addSkillGroup(selection);
                            }
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                        } else if (q.getName().equals("Loss of Confidence")) {
                            JComboBox<Skill> skills = new JComboBox<Skill>();
                            for (Skill s : characterController.getCharacter().getSkills()) {
                                if (s.getValue() >= 4) {
                                    skills.addItem(s);
                                }
                            }
                            final JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose the skill your character lost confidence in!"),
                                    skills};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            Skill selection = (Skill) skills.getSelectedItem();
                            q.setNotes("-2 dice pool modificator to "
                                    + selection.getName()
                                    + ". Skill specialization not useable.");
                        } else if (q.getName().contains("Prejudiced")) {
                            JTextField prejudice = new JTextField();
                            String group = null;
                            String label = null;
                            if (q.getName().contains("(Common")) {
                                group = "common";
                                label = "e.g. humans, metahumans";
                            } else {
                                group = "specific";
                                label = "e.g. the Awakened, technomancers, shapeshifters, aspected magicians";
                            }
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please insert the "
                                                    + group
                                                    + " target group of your characters prejudice:"),
                                    new JLabel(label), prejudice};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            String target = prejudice.getText();
                            if (q.getName().contains("(Biased)")) {
                                q.setName("Prejudiced (" + target + ")(Biased)");
                            } else if (q.getName().contains("(Outspoken)")) {
                                q.setName("Prejudiced (" + target
                                        + ")(Outspoken)");
                            } else {
                                q.setName("Prejudiced (" + target
                                        + ")(Radical)");
                            }
                        } else if (q.getName().equals("Scorched")) {
                            JComboBox<String> type = new JComboBox<String>();
                            JComboBox<String> effect = new JComboBox<String>();
                            type.addItem("BTL");
                            type.addItem("Black IC");
                            type.addItem("Psychotropic IC");
                            effect.addItem("Memory Loss (short term)");
                            effect.addItem("Memory Loss (long term)");
                            effect.addItem("Blackout");
                            effect.addItem("Migraines");
                            effect.addItem("Paranoia/Anxiety");
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose what scorched your character:"),
                                    type,
                                    new JLabel(
                                            "Please choose the side effects:"),
                                    effect};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            String typeText = (String) type.getSelectedItem();
                            String effectText = (String) effect
                                    .getSelectedItem();
                            if (typeText.equals("BTL")) {
                                if (0 != JOptionPane
                                        .showConfirmDialog(
                                                panel,
                                                "Have you allready chosen at least a Mild Addiction to BTL's for your character?",
                                                "Needs Addiction",
                                                JOptionPane.NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE)) {
                                    Quality btl = new Quality();
                                    btl.setName("Addiction (BTL)(Mild)");
                                    btl.setNotes("1 dose/1 hour of activity once a month");
                                    btl.setLeveled(false);
                                    btl.setPositive(false);
                                    btl.setAPcost_bonus(4);

                                    characterController.getCharacter()
                                            .getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            + btl.getAPcost_bonus());
                                    characterController.getCharacter().addQuality(btl);
                                    Quality array[] = new Quality[characterController.getCharacter()
                                            .getQualities().size()];
                                    int i = 0;
                                    for (Quality qua : characterController.getCharacter()
                                            .getQualities()) {
                                        array[i] = qua;
                                        i++;
                                    }
                                    // chosenQualities.setListData(array);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                }
                            }
                            // char has to buy a deck if not a technomancer
                            characterController.getCharacter().getPersonalData().addNotoriety(1);
                            q.setName("Scorched (" + typeText + ")");
                            q.setNotes("Must pass a Body+Willpower(4) test not to suffer "
                                    + effectText);
                        } else if (q.getName().equals("Sensitive System")) {
                            if (characterController.getCharacter().getMagicalness().equals(
                                    Magical.AspectedMagician)
                                    || characterController.getCharacter().getMagicalness()
                                    .equals(Magical.Magician)) {
                                q.setNotes("Must make a Willpower(2) test before any Drain Tests. If failed +2 to Drain");
                            } else if (characterController.getCharacter().getMagicalness()
                                    .equals(Magical.Technomancer)) {
                                q.setNotes("Must make a Willpower(2) test before any Fading Tests. If failed +2 to Fading");
                            } else {
                                q.setNotes("Essence loss x2, Bioware rejected");
                                // resolve when buying cyber-/bioware
                            }
                        } else if (q.getName().equals("Social Stress")) {
                            JTextField cause = new JTextField();
                            JTextField trigger = new JTextField();
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel(
                                            "Please enter a specific cause for your characters Social Stress:"),
                                    cause,
                                    new JLabel(
                                            "Please enter what triggers your characters Social Stress:"),
                                    trigger};
                            JOptionPane.showMessageDialog(panel, inputs,
                                    q.getName(), JOptionPane.QUESTION_MESSAGE);
                            q.setNotes("Cause: "
                                    + cause.getText()
                                    + "If triggered by "
                                    + trigger
                                    + ": Number of 1s for a glitch reduced by 1 when using Leadership or Ettiquette skills");
                        } else if (q.getName().equals("Weak Immune System")) {
                            int pathoIndex = -1;
                            int toxinIndex = -1;
                            for (Quality qua : characterController.getCharacter().getQualities()) {
                                if (qua.getName().equals(
                                        "Resistance to pathogenes")) {
                                    pathoIndex = characterController.getCharacter()
                                            .getQualities().indexOf(qua);
                                } else if (qua.getName().equals(
                                        "Resistance to toxins")) {
                                    toxinIndex = characterController.getCharacter()
                                            .getQualities().indexOf(qua);
                                }
                            }

                            if (pathoIndex > -1 || toxinIndex > -1) {
                                int index = JOptionPane
                                        .showOptionDialog(
                                                panel,
                                                "This quality can't be combined with Resistance qualities! Remove this quality or the Resistance quality?",
                                                "Error",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.ERROR_MESSAGE,
                                                null,
                                                new String[]{"Remove this",
                                                        "Remove Resistance Quality"},
                                                0);
                                if (index == 0) {
                                    allowedToAdd = false;
                                } else {
                                    characterController.getCharacter().getPersonalData().addNotoriety(1);
                                    if (pathoIndex > -1) {
                                        Quality qua = characterController.getCharacter()
                                                .getQualities().get(pathoIndex);
                                        characterController.getCharacter()
                                                .getPersonalData()
                                                .setKarma(
                                                        characterController.getCharacter()
                                                                .getPersonalData()
                                                                .getKarma()
                                                                - qua.getAPcost_bonus());
                                        characterController.getCharacter().removeQuality(qua);
                                    }
                                    if (toxinIndex > -1) {
                                        Quality qua = characterController.getCharacter()
                                                .getQualities().get(toxinIndex);
                                        characterController.getCharacter()
                                                .getPersonalData()
                                                .setKarma(
                                                        characterController.getCharacter()
                                                                .getPersonalData()
                                                                .getKarma()
                                                                - qua.getAPcost_bonus());
                                        characterController.getCharacter().removeQuality(qua);
                                    }
                                    Quality array[] = new Quality[characterController.getCharacter()
                                            .getQualities().size()];
                                    int i = 0;
                                    for (Quality qual : characterController.getCharacter()
                                            .getQualities()) {
                                        array[i] = qual;
                                        i++;
                                    }
                                    // chosenQualities.setListData(array);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                    // chosenQualities.revalidate();
                                    // chosenQualities.repaint();
                                }
                            }
                        } else if (q.getName().equals(
                                "Resistance to pathogenes")
                                || q.getName().equals("Resistance to toxins")) {
                            int weakImmuIndex = -1;
                            for (Quality qua : characterController.getCharacter().getQualities()) {
                                if (qua.getName().equals("Weak Immune System")) {
                                    weakImmuIndex = characterController.getCharacter()
                                            .getQualities().indexOf(qua);
                                }
                            }

                            if (weakImmuIndex > -1) {
                                int index = JOptionPane
                                        .showOptionDialog(
                                                panel,
                                                "This quality can't be combined with the Weak Immune System quality! Remove this quality or the Weak Immune System quality?",
                                                "Error",
                                                JOptionPane.DEFAULT_OPTION,
                                                JOptionPane.ERROR_MESSAGE,
                                                null,
                                                new String[]{"Remove this",
                                                        "Remove Weak Immune System Quality"},
                                                0);
                                if (index == 0) {
                                    allowedToAdd = false;
                                } else {
                                    Quality qua = characterController.getCharacter()
                                            .getQualities().get(weakImmuIndex);
                                    characterController.getCharacter()
                                            .getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - qua.getAPcost_bonus());
                                    characterController.getCharacter().removeQuality(qua);
                                    Quality array[] = new Quality[characterController.getCharacter()
                                            .getQualities().size()];
                                    int i = 0;
                                    for (Quality qual : characterController.getCharacter()
                                            .getQualities()) {
                                        array[i] = qual;
                                        i++;
                                    }
                                    // chosenQualities.setListData(array);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                    // chosenQualities.revalidate();
                                    // chosenQualities.repaint();
                                }
                            }
                        }
                        if (allowedToAdd) {
                            characterController.getCharacter().addQuality(q);
                            karma.setValue(characterController.getCharacter().getPersonalData()
                                    .getKarma());
                            qualities.doClick();
                        }
                    }
                });

                removeNeg.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JComboBox<Quality> choice = new JComboBox<Quality>();
                        for (Quality q : characterController.getCharacter().getQualities()) {
                            if (!q.isPositive()) {
                                choice.addItem(q);
                            }
                        }
                        JComboBox<String> gmAdded = new JComboBox<String>();
                        gmAdded.addItem("no");
                        gmAdded.addItem("yes");
                        JComponent[] inputs = new JComponent[]{
                                new JLabel(
                                        "Please choose Quality you wish to buy off:"),
                                choice,
                                new JLabel("Is this added per GM request?"),
                                gmAdded};
                        if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(contentPanel, inputs,
                                "Remove Quality", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            return;
                        }
                        if (characterController.getCharacter().getPersonalData().getKarma()
                                - ((Quality) choice.getSelectedItem())
                                .getAPcost_bonus() * 2 >= 0) {
                            if (gmAdded.getSelectedIndex() == 0) {
                                int cost = characterController.getCharacter().getPersonalData()
                                        .getKarma()
                                        - ((Quality) choice.getSelectedItem())
                                        .getAPcost_bonus() * 2;
                                characterController.getCharacter().getPersonalData().setKarma(
                                        cost);
                            }
                            karma.setValue(characterController.getCharacter().getPersonalData()
                                    .getKarma());
                            characterController.getCharacter().removeQuality((Quality) choice
                                    .getSelectedItem());
                            qualities.doClick();
                        } else {
                            JOptionPane.showMessageDialog(contentPanel,
                                    "Not enough karma!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                revalidate();
                repaint();

            }
        });
        contacts.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner contPoints = new JSpinner(
                        new SpinnerNumberModel(freeContPoints, 0,
                                freeContPoints, 1));
                contPoints.setEnabled(false);
                if (freeContPoints > 0) {
                    iPanel.add(new JLabel("Conection Pts"));
                    iPanel.add(contPoints);
                }

                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                final JPanel panel = new JPanel(new GridBagLayout());
                final JScrollPane scroll = new JScrollPane(panel);
                scroll.setSize(100, 100);

                GridBagConstraints con = new GridBagConstraints();

                con.fill = GridBagConstraints.BOTH;
                con.anchor = GridBagConstraints.CENTER;
                con.gridx = 0;
                con.gridy = 0;
                panel.add(new JLabel("NAME"), con);
                con.gridx += 1;
                panel.add(new JLabel("   "), con);
                con.gridx += 1;
                panel.add(new JLabel("CONNECTION"), con);
                con.gridx += 1;
                panel.add(new JLabel("   "), con);
                con.gridx += 1;
                panel.add(new JLabel("LOYALTY"), con);
                con.gridx = 0;
                con.gridy += 1;
                for (Contact q : characterController.getCharacter().getContacts()) {
                    panel.add(new JLabel(q.getName()), con);
                    con.gridx += 2;
                    panel.add(new JLabel("" + q.getConnection()), con);
                    con.gridx += 2;
                    panel.add(new JLabel("" + q.getLoyalty()), con);
                    con.gridx = 0;
                    con.gridy += 1;
                }
                con.fill = GridBagConstraints.BOTH;
                con.gridx = 0;
                con.gridy = 0;
                con.gridwidth = 2;
                contentPanel.add(scroll, con);
                con.weighty = 0;
                JButton addCon = new JButton("New Contact");
                JButton editCon = new JButton("Edit Contact");
                con.gridwidth = 1;
                con.gridy += 1;
                contentPanel.add(addCon, con);
                con.gridx += 1;
                contentPanel.add(editCon, con);
                con.gridwidth = 2;

                addCon.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextField name = new JTextField();
                        JSpinner connection = new JSpinner(
                                new SpinnerNumberModel(1, 1, 12, 1));
                        JSpinner loyalty = new JSpinner(new SpinnerNumberModel(
                                1, 1, 6, 1));
                        JComboBox<String> gmAdded = new JComboBox<String>();
                        gmAdded.addItem("no");
                        gmAdded.addItem("yes");
                        JComponent[] inputs = new JComponent[]{
                                new JLabel("Please enter Connection name:"),
                                name, new JLabel("Connection Rating"),
                                connection, new JLabel("Loyalty Rating"),
                                loyalty,
                                new JLabel("Is this added per GM request?"),
                                gmAdded};
                        if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(contentPanel, inputs,
                                "Add Connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            return;
                        }
                        int cost = (int) connection.getValue()
                                + (int) loyalty.getValue();
                        if (characterGeneration && cost > 7) {
                            JOptionPane
                                    .showMessageDialog(
                                            contentPanel,
                                            "You cant have contacts with a combined rating above 7 at character generation!",
                                            "ERROR", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (characterGeneration) {
                            if (gmAdded.getSelectedIndex() == 0) {
                                if (freeContPoints >= cost) {
                                    freeContPoints -= cost;
                                    cost = 0;
                                } else {
                                    cost -= freeContPoints;
                                    freeContPoints = 0;
                                }
                            }
                            contPoints.setValue(freeContPoints);
                        }
                        if (characterController.getCharacter().getPersonalData().getKarma() >= cost) {
                            if (gmAdded.getSelectedIndex() == 0) {
                                cost = characterController.getCharacter().getPersonalData()
                                        .getKarma() - cost;
                                characterController.getCharacter().getPersonalData().setKarma(
                                        cost);
                            }
                            Contact cont = new Contact();
                            cont.setName(name.getText());
                            cont.setConnection((int) connection.getValue());
                            cont.setLoyalty((int) loyalty.getValue());
                            cont.setFavors(0);
                            characterController.getCharacter().addContact(cont);
                            karma.setValue(characterController.getCharacter().getPersonalData()
                                    .getKarma());
                            contacts.doClick();
                        }
                    }
                });

                editCon.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JComboBox<Contact> conBox = new JComboBox<Contact>(characterController.getCharacter().getContacts().toArray(new Contact[characterController.getCharacter().getContacts().size()]));
                        if (JOptionPane.CANCEL_OPTION != JOptionPane.showConfirmDialog(contentPanel, new JComponent[]{new JLabel("Please choose the Contact you wish to edit!"), conBox}, "Edit Contact", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                            Contact co = (Contact) conBox.getSelectedItem();
                            JSpinner connection = new JSpinner(
                                    new SpinnerNumberModel(co.getConnection(), 1, 12, 1));
                            JSpinner loyalty = new JSpinner(new SpinnerNumberModel(co.getLoyalty(), 1, 6, 1));
                            JSpinner favors = new JSpinner(new SpinnerNumberModel(co.getFavors(), 0, 6, 1));
                            JComboBox<String> gmAdded = new JComboBox<String>();
                            gmAdded.addItem("no");
                            gmAdded.addItem("yes");
                            JComponent[] inputs = new JComponent[]{
                                    new JLabel("Connection Rating"), connection,
                                    new JLabel("Loyalty Rating"), loyalty,
                                    new JLabel("Favors"), favors,
                                    new JLabel("Is this done per GM request?"),
                                    gmAdded
                            };
                            if (JOptionPane.CANCEL_OPTION != JOptionPane.showConfirmDialog(contentPanel, inputs, "Edit " + co.getName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                                boolean okay = true;
                                if (gmAdded.getSelectedIndex() == 0) {
                                    int con = (int) connection.getValue() - co.getConnection();
                                    int loy = (int) loyalty.getValue() - co.getLoyalty();
                                    int fav = (int) favors.getValue() - co.getFavors();
                                    con = con > 0 ? con : 0;
                                    loy = loy > 0 ? loy : 0;
                                    fav = fav > 0 ? fav : 0;
                                    if (characterController.getCharacter().getPersonalData().getKarma() >= (con + loy + fav)) {
                                        characterController.getCharacter().getPersonalData().setKarma(characterController.getCharacter().getPersonalData().getKarma() - (con + loy + fav));
                                        karma.setValue(characterController.getCharacter().getPersonalData().getKarma());
                                    } else {
                                        okay = false;
                                    }
                                }
                                if (okay) {
                                    co.setConnection((int) connection.getValue());
                                    co.setLoyalty((int) loyalty.getValue());
                                    co.setFavors((int) favors.getValue());
                                    revalidate();
                                    repaint();
                                    contacts.doClick();
                                }
                            }
                        }
                    }
                });

                revalidate();
                repaint();
            }
        });
        rangedWeap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();

                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);

                JButton ammuList = new JButton("Manage Ammunition");

                showRangedWeapList(contentPanel, money, characterGeneration);

                ammuList.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel ammu = new JPanel(new GridBagLayout());
                        showAmmunitionList(ammu, money, characterGeneration);
                        JOptionPane pane = new JOptionPane(ammu,
                                JOptionPane.INFORMATION_MESSAGE);
                        JDialog dialog = pane.createDialog(contentPanel,
                                "Ammunition");
                        dialog.setSize(frameWidth - 100, frameHeight - 100);
                        dialog.setLocation(50, 50);
                        dialog.setVisible(true);
                        // JOptionPane.showMessageDialog(contentPanel, ammu,
                        // "Ammunition", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = 5;
                constraints.gridy = 0;
                constraints.gridwidth = 2;
                constraints.weightx = 1.0;
                constraints.weighty = 0.5;
                contentPanel.add(ammuList, constraints);
                revalidate();
                repaint();
            }
        });
        meleeWeap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showMeeleWeapList(contentPanel, money, characterGeneration);
            }
        });
        armor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showClothingList(contentPanel, money, characterGeneration);
            }
        });
        cyberdecks.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showDeckList(contentPanel, money, characterGeneration);
            }
        });
        augmentations.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner essence = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getAttributes().getEssence(), 0.0, 6.1, 0.1));
                essence.setEnabled(false);
                iPanel.add(new JLabel("Essence"));
                iPanel.add(essence);

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showCyberwareList(contentPanel, essence, money, characterGeneration);
            }
        });
        vehicles.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showVehicleList(contentPanel, money, characterGeneration);
            }
        });
        gear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                infoPanel.removeAll();
                karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                JPanel iPanel = new JPanel();
                iPanel.add(new JLabel("Karma "));
                iPanel.add(karma);
                iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                final JSpinner money = new JSpinner(new SpinnerNumberModel(
                        characterController.getCharacter().getMoney(), 0.0, characterController.getCharacter()
                        .getMoney(), 0.5));
                money.setEnabled(false);
                iPanel.add(money);
                iPanel.add(new JLabel("NuYen"));
                infoPanel.add(iPanel, BorderLayout.CENTER);
                if (characterGeneration)
                    infoPanel.add(done, BorderLayout.EAST);
                showGearList(contentPanel, money, characterGeneration);
            }
        });
        if (characterController.getCharacter().getMagicalness().equals(Magical.Magician)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.MysticalAdept)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.AspectedMagician)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.Technomancer)) {
            spells.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    contentPanel.removeAll();
                    infoPanel.removeAll();

                    karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                    infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                    JPanel iPanel = new JPanel();
                    iPanel.add(new JLabel("Karma "));
                    iPanel.add(karma);
                    iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                    final JSpinner freeSpPoints = new JSpinner(
                            new SpinnerNumberModel(freeSpells, 0, freeSpells, 1));
                    freeSpPoints.setEnabled(false);
                    if (characterGeneration) {
                        iPanel.add(new JLabel("Free Spells"));
                        iPanel.add(freeSpPoints);
                        infoPanel.add(done, BorderLayout.EAST);
                    }
                    infoPanel.add(iPanel, BorderLayout.CENTER);

                    final JPanel spellPanel = new JPanel(new GridBagLayout());
                    final JScrollPane scroll = new JScrollPane(spellPanel);
                    final JPanel ritualPanel = new JPanel(new GridBagLayout());
                    final JPanel prepPanel = new JPanel(new GridBagLayout());
                    final JScrollPane ritScroll = new JScrollPane(ritualPanel);
                    final JScrollPane prepScroll = new JScrollPane(prepPanel);

                    scroll.setSize(100, 100);
                    GridBagConstraints con = new GridBagConstraints();
                    con.gridx = 0;
                    con.gridy = 0;
                    if (characterController.getCharacter().getMagicalness().equals(
                            Magical.Technomancer)) {
                        for (Spell sp : characterController.getCharacter().getComplexForms()) {
                            spellPanel.add(new JLabel(sp.toString()), con);
                            con.gridy += 1;
                        }
                    } else {
                        for (Spell sp : characterController.getCharacter().getSpells()) {
                            spellPanel.add(new JLabel(sp.toString()), con);
                            con.gridy += 1;
                        }
                        con.gridy = 0;
                        for (Spell sp : characterController.getCharacter().getRituals()) {
                            ritualPanel.add(new JLabel(sp.toString()), con);
                            con.gridy += 1;
                        }
                        con.gridy = 0;
                        for (Spell sp : characterController.getCharacter().getPreparations()) {
                            prepPanel.add(new JLabel(sp.toString()), con);
                            con.gridy += 1;
                        }
                    }
                    con.fill = GridBagConstraints.BOTH;
                    con.gridx = 0;
                    con.gridy = 0;
                    if (characterController.getCharacter().getMagicalness().equals(
                            Magical.Technomancer)) {
                        contentPanel.add(new JLabel("Complex Forms"), con);
                    } else {
                        contentPanel.add(new JLabel("Spells"), con);
                    }
                    con.gridy += 1;
                    con.gridwidth = 3;
                    con.weightx = 3;
                    con.weighty = 1;
                    contentPanel.add(scroll, con);
                    if (!characterController.getCharacter().getMagicalness().equals(
                            Magical.Technomancer)) {
                        con.gridy += 1;
                        con.weightx = 0;
                        con.weighty = 0;
                        contentPanel.add(new JLabel("Rituals"), con);
                        con.gridy += 1;
                        con.weightx = 3;
                        con.weighty = 1;
                        contentPanel.add(ritScroll, con);
                        con.gridy += 1;
                        con.weightx = 0;
                        con.weighty = 0;
                        contentPanel.add(new JLabel("Alchemical Preperations"),
                                con);
                        con.gridy += 1;
                        con.weightx = 3;
                        con.weighty = 1;
                        contentPanel.add(prepScroll, con);
                    }
                    con.gridwidth = 1;
                    con.weightx = 1;
                    con.weighty = 0;
                    JButton addSpell = new JButton("Learn new Spell");
                    if (characterController.getCharacter().getMagicalness().equals(
                            Magical.Technomancer)) {
                        addSpell.setText("Learn new Complex Form");
                    }
                    con.gridy += 1;
                    contentPanel.add(addSpell, con);
                    JButton addRit = new JButton("Learn new Ritual");
                    JButton addPrep = new JButton("Learn new Preperation");
                    if (!characterController.getCharacter().getMagicalness().equals(
                            Magical.Technomancer)) {
                        con.gridx++;
                        contentPanel.add(addRit, con);
                        con.gridx++;
                        contentPanel.add(addPrep, con);
                    }

                    addSpell.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox<Spell> spellBox = new JComboBox<Spell>();
                            String name = "Spell";
                            boolean notTooManySpells = false;
                            int karmaCost = 5;
                            if (characterController.getCharacter().getMagicalness().equals(
                                    Magical.Technomancer)) {
                                name = "Complex Form";
                                LR.loadCompFormList();
                                for (Spell s : LR.getComplexFormList()) {
                                    if (!characterController.getCharacter().getComplexForms()
                                            .contains(s)) {
                                        spellBox.addItem(s);
                                    }
                                }
                                notTooManySpells = (characterController.getCharacter()
                                        .getComplexForms().size() <= characterController.getCharacter()
                                        .getAttributes().getLogic());
                                karmaCost = 4;
                            } else {
                                LR.loadSpellList();
                                for (Spell s : LR.getSpellList()) {
                                    if (!characterController.getCharacter().getSpells().contains(
                                            s)) {
                                        spellBox.addItem(s);
                                    }
                                }
                                notTooManySpells = (characterController.getCharacter()
                                        .getSpells().size()
                                        + characterController.getCharacter().getPreparations()
                                        .size()
                                        + characterController.getCharacter().getRituals().size() <= characterController.getCharacter()
                                        .getAttributes().getMagic() * 2);
                            }
                            JComponent[] inputs = null;
                            if (characterGeneration) {
                                inputs = new JComponent[]{
                                        new JLabel("Please choose a " + name
                                                + " you wish to learn:"),
                                        spellBox};
                            } else {

                            }
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose " + name
                                            + " you wish to learn!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }
                            Spell s = (Spell) spellBox.getSelectedItem();
                            if (characterGeneration) {
                                if (notTooManySpells) {
                                    if (freeSpells > 0) {
                                        if (characterController.getCharacter().getMagicalness()
                                                .equals(Magical.Technomancer)) {
                                            characterController.getCharacter().getComplexForms()
                                                    .add(s);
                                        } else {
                                            characterController.getCharacter().getSpells().add(s);
                                        }
                                        freeSpells--;
                                        System.out.println(freeSpells);
                                        freeSpPoints.setValue(freeSpells);
                                        freeSpPoints.revalidate();
                                        freeSpPoints.repaint();
                                    } else if (characterController.getCharacter()
                                            .getPersonalData().getKarma()
                                            - karmaCost > 0) {
                                        if (characterController.getCharacter().getMagicalness()
                                                .equals(Magical.Technomancer)) {
                                            characterController.getCharacter().getComplexForms()
                                                    .add(s);
                                        } else {
                                            characterController.getCharacter().getSpells().add(s);
                                        }
                                        characterController.getCharacter()
                                                .getPersonalData()
                                                .setKarma(
                                                        characterController.getCharacter()
                                                                .getPersonalData()
                                                                .getKarma()
                                                                - karmaCost);
                                        karma.setValue(characterController.getCharacter()
                                                .getPersonalData().getKarma());
                                        karma.revalidate();
                                        karma.repaint();
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(
                                                        contentPanel,
                                                        "Not enough karma/free spell points to learn any more spells/complex forms!",
                                                        "Can't afford more "
                                                                + name + "s!",
                                                        JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Can't learn any more spells/complex forms at character generation!",
                                                    "Can't learn that many "
                                                            + name + "s!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                if (characterController.getCharacter().getPersonalData()
                                        .getKarma() - karmaCost > 0) {
                                    characterController.getCharacter().getSpells().add(s);
                                    characterController.getCharacter().getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - karmaCost);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                    karma.revalidate();
                                    karma.repaint();
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Not enough karma to learn any more spells/complex forms!",
                                                    "Can't afford more spells!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            spells.doClick();
                        }
                    });

                    addRit.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            JComboBox<Spell> spellBox = new JComboBox<Spell>();
                            String name = "Ritual";
                            boolean notTooManySpells = false;
                            int karmaCost = 5;
                            LR.loadRitualList();
                            for (Spell s : LR.getRitualList()) {
                                if (!characterController.getCharacter().getRituals().contains(s)) {
                                    spellBox.addItem(s);
                                }
                            }
                            notTooManySpells = (characterController.getCharacter().getSpells()
                                    .size()
                                    + characterController.getCharacter().getPreparations().size()
                                    + characterController.getCharacter().getRituals().size() <= characterController.getCharacter()
                                    .getAttributes().getMagic() * 2);
                            JComponent[] inputs = null;
                            if (characterGeneration) {
                                inputs = new JComponent[]{
                                        new JLabel("Please choose a " + name
                                                + " you wish to learn:"),
                                        spellBox};
                            } else {

                            }
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose " + name
                                            + " you wish to learn!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }
                            Spell s = (Spell) spellBox.getSelectedItem();
                            if (characterGeneration) {
                                if (notTooManySpells) {
                                    if (freeSpells > 0) {
                                        characterController.getCharacter().getRituals().add(s);
                                        freeSpells--;
                                        System.out.println(freeSpells);
                                        freeSpPoints.setValue(freeSpells);
                                        freeSpPoints.revalidate();
                                        freeSpPoints.repaint();
                                    } else if (characterController.getCharacter()
                                            .getPersonalData().getKarma()
                                            - karmaCost > 0) {
                                        characterController.getCharacter().getRituals().add(s);
                                        characterController.getCharacter()
                                                .getPersonalData()
                                                .setKarma(
                                                        characterController.getCharacter()
                                                                .getPersonalData()
                                                                .getKarma()
                                                                - karmaCost);
                                        karma.setValue(characterController.getCharacter()
                                                .getPersonalData().getKarma());
                                        karma.revalidate();
                                        karma.repaint();
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(
                                                        contentPanel,
                                                        "Not enough karma/free spell points to learn any more Rituals!",
                                                        "Can't afford more "
                                                                + name + "s!",
                                                        JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Can't learn any more rituals at character generation!",
                                                    "Can't learn that many "
                                                            + name + "s!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                if (characterController.getCharacter().getPersonalData()
                                        .getKarma() - karmaCost > 0) {
                                    characterController.getCharacter().getRituals().add(s);
                                    characterController.getCharacter().getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - karmaCost);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                    karma.revalidate();
                                    karma.repaint();
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Not enough karma to learn any more spells/complex forms!",
                                                    "Can't afford more spells!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            spells.doClick();
                        }

                    });

                    addPrep.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            JComboBox<Spell> spellBox = new JComboBox<Spell>();
                            String name = "Alchemical Spell";
                            boolean notTooManySpells = false;
                            int karmaCost = 5;
                            LR.loadSpellList();
                            for (Spell s : LR.getSpellList()) {
                                if (!characterController.getCharacter().getSpells().contains(s)) {
                                    spellBox.addItem(s);
                                }
                            }
                            notTooManySpells = (characterController.getCharacter().getSpells()
                                    .size()
                                    + characterController.getCharacter().getPreparations().size()
                                    + characterController.getCharacter().getRituals().size() <= characterController.getCharacter()
                                    .getAttributes().getMagic() * 2);
                            JComponent[] inputs = null;
                            if (characterGeneration) {
                                inputs = new JComponent[]{
                                        new JLabel("Please choose a " + name
                                                + " you wish to learn:"),
                                        spellBox};
                            } else {

                            }
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose " + name
                                            + " you wish to learn!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }
                            Spell s = (Spell) spellBox.getSelectedItem();
                            if (characterGeneration) {
                                if (notTooManySpells) {
                                    if (freeSpells > 0) {
                                        characterController.getCharacter().getPreparations().add(
                                                s);
                                        freeSpells--;
                                        System.out.println(freeSpells);
                                        freeSpPoints.setValue(freeSpells);
                                        freeSpPoints.revalidate();
                                        freeSpPoints.repaint();
                                    } else if (characterController.getCharacter()
                                            .getPersonalData().getKarma()
                                            - karmaCost > 0) {
                                        characterController.getCharacter().getPreparations().add(
                                                s);
                                        characterController.getCharacter()
                                                .getPersonalData()
                                                .setKarma(
                                                        characterController.getCharacter()
                                                                .getPersonalData()
                                                                .getKarma()
                                                                - karmaCost);
                                        karma.setValue(characterController.getCharacter()
                                                .getPersonalData().getKarma());
                                        karma.revalidate();
                                        karma.repaint();
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(
                                                        contentPanel,
                                                        "Not enough karma/free spell points to learn any more Preperations!",
                                                        "Can't afford more "
                                                                + name + "s!",
                                                        JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Can't learn any more Preperations at character generation!",
                                                    "Can't learn that many "
                                                            + name + "s!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                if (characterController.getCharacter().getPersonalData()
                                        .getKarma() - karmaCost > 0) {
                                    characterController.getCharacter().getPreparations().add(s);
                                    characterController.getCharacter().getPersonalData()
                                            .setKarma(
                                                    characterController.getCharacter()
                                                            .getPersonalData()
                                                            .getKarma()
                                                            - karmaCost);
                                    karma.setValue(characterController.getCharacter()
                                            .getPersonalData().getKarma());
                                    karma.revalidate();
                                    karma.repaint();
                                } else {
                                    JOptionPane
                                            .showMessageDialog(
                                                    contentPanel,
                                                    "Not enough karma to learn any more Preperations!",
                                                    "Can't afford more spells!",
                                                    JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }

                            spells.doClick();
                        }

                    });

                    revalidate();
                    repaint();

                }
            });
        }
        if (characterController.getCharacter().getMagicalness().equals(Magical.Adept)
                || characterController.getCharacter().getMagicalness().equals(
                Magical.MysticalAdept)) {
            powers.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    contentPanel.removeAll();
                    infoPanel.removeAll();
                    karma.setModel(new SpinnerNumberModel(characterController.getCharacter().getPersonalData().getKarma(), 0, characterController.getCharacter().getPersonalData().getKarma(), 1));
                    infoPanel.add(new JLabel(characterController.getCharacter().getPersonalData().getName()), BorderLayout.WEST);
                    JPanel iPanel = new JPanel();
                    iPanel.add(new JLabel("Karma "));
                    iPanel.add(karma);
                    iPanel.add(new JLabel("/" + characterController.getCharacter().getPersonalData().getTotalKarma()));

                    final JSpinner powerPoints = new JSpinner(
                            new SpinnerNumberModel(characterController.getCharacter()
                                    .getAttributes().getAdeptPowerPoints(), 0,
                                    characterController.getCharacter().getAttributes()
                                            .getAdeptPowerPoints(), 1));
                    powerPoints.setEnabled(false);
                    iPanel.add(new JLabel("Adept Power Points"));
                    iPanel.add(powerPoints);
                    infoPanel.add(iPanel, BorderLayout.CENTER);
                    if (characterGeneration)
                        infoPanel.add(done, BorderLayout.EAST);
                    final JPanel powerPanel = new JPanel(new GridBagLayout());
                    final JScrollPane scroll = new JScrollPane(powerPanel);

                    scroll.setSize(100, 100);
                    GridBagConstraints con = new GridBagConstraints();
                    con.gridx = 0;
                    con.gridy = 0;
                    for (Power sp : characterController.getCharacter().getAdeptPowers()) {
                        JLabel label = new JLabel(sp.toString());
                        label.setToolTipText(sp.getNotes());
                        powerPanel.add(label, con);
                        con.gridy += 1;
                    }

                    con.fill = GridBagConstraints.BOTH;
                    con.gridx = 0;
                    con.gridy = 0;
                    contentPanel.add(new JLabel("Adept Powers"), con);

                    con.gridy += 1;
                    con.gridwidth = 2;
                    con.weightx = 3;
                    con.weighty = 1;
                    contentPanel.add(scroll, con);
                    con.gridwidth = 1;
                    con.weightx = 1;
                    con.weighty = 0;
                    JButton addPower = new JButton("Learn new Power");
                    con.gridy += 1;
                    contentPanel.add(addPower, con);
                    JButton raisePower = new JButton("Raise Power Level");
                    con.gridx += 1;
                    contentPanel.add(raisePower, con);

                    addPower.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox<Power> powerBox = new JComboBox<Power>();
                            String name = "Adept Power";

                            LR.loadPowList();
                            for (Power s : LR.getPowerList()) {
                                if (!characterController.getCharacter().getAdeptPowers()
                                        .contains(s)) {
                                    powerBox.addItem(s);
                                }
                            }
                            powerBox.setRenderer(new DefaultListCellRenderer() {
                                @Override
                                public Component getListCellRendererComponent(
                                        JList<?> list, Object value, int index,
                                        boolean isSelected, boolean cellHasFocus) {
                                    if (value instanceof Power) {
                                        Power q = ((Power) value);
                                        setToolTipText(q.getNotes());
                                        value = q.toString();
                                    } else {
                                        setToolTipText(null);
                                    }
                                    return super.getListCellRendererComponent(
                                            list, value, index, isSelected,
                                            cellHasFocus);
                                }
                            });

                            JComponent[] inputs = new JComponent[]{
                                    new JLabel("Please choose a " + name
                                            + " you wish to learn:"), powerBox};
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose " + name
                                            + " you wish to learn!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }
                            Power p = (Power) powerBox.getSelectedItem();
                            if (p.isLeveled()) {
                                JSpinner levelSp = new JSpinner(
                                        new SpinnerNumberModel(1, 1,
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getMagic(), 1));
                                inputs = new JComponent[]{
                                        new JLabel("Please choose a level:"),
                                        levelSp};
                                if (JOptionPane.showConfirmDialog(contentPanel,
                                        inputs, "Choose level!",
                                        JOptionPane.OK_CANCEL_OPTION,
                                        JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                    return;
                                }
                                p.setLevel((int) levelSp.getValue());
                            }
                            if (characterController.getCharacter().getAttributes()
                                    .getAdeptPowerPoints() - p.getCost() >= 0) {
                                if (p.getName().equals("Adrenaline Boost")) {
                                    p.setNotes("+"
                                            + (2 * p.getLevel())
                                            + " Initiative for current Combat Turn. "
                                            + p.getLevel()
                                            + " Drain following turn.");
                                } else if (p.getName().equals(
                                        "Attribute Boost (Physical Attribute)")) {
                                    JComboBox<Attribute> box = new JComboBox<Attribute>();
                                    box.addItem(Attribute.AGILITY);
                                    box.addItem(Attribute.BODY);
                                    box.addItem(Attribute.REACTION);
                                    box.addItem(Attribute.STRENGTH);
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose an attribute:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Attribute!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    switch (box.getSelectedIndex()) {
                                        case 0:
                                            p.setName("Attribute Boost (Agility)");
                                            break;
                                        case 1:
                                            p.setName("Attribute Boost (Body)");
                                            break;
                                        case 2:
                                            p.setName("Attribute Boost (Reaction)");
                                            break;
                                        case 3:
                                            p.setName("Attribute Boost (Strength)");
                                            break;
                                    }
                                } else if (p.getName().equals("Combat Sense")) {
                                    p.setNotes("+"
                                            + p.getLevel()
                                            + " d.p.m. to defense tests against ranged and melee attacks. Always allowed a Perception Test before possible surprise situations.");
                                } else if (p.getName().equals(
                                        "Critical Strike (Skill)")) {
                                    JComboBox<String> box = new JComboBox<String>();
                                    box.addItem("Unarmed Combat");
                                    box.addItem("Clubs");
                                    box.addItem("Blades");
                                    box.addItem("Astral Combat");
                                    for (Skill w : characterController.getCharacter().getSkills()) {
                                        if (w.getName()
                                                .contains("Exotic Melee")) {
                                            box.addItem(w.getName());
                                        }
                                    }
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose a type of close combat weapon:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Weapon!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    p.setName("Critical Strike ("
                                            + (String) box.getSelectedItem()
                                            + ")");
                                    p.setNotes("+1 DV for attacks with "
                                            + (String) box.getSelectedItem());
                                } else if (p.getName().equals("Danger Sense")) {
                                    p.setNotes("+" + p.getLevel()
                                            + " d.p.m. on Surprise Tests");
                                } else if (p.getName().equals(
                                        "Enhanced Perception")) {
                                    p.setNotes("+"
                                            + p.getLevel()
                                            + " d.p.m. to all Perception and Assensing Tests");
                                } else if (p.getName().equals(
                                        "Enhanced Accuracy")) {
                                    JComboBox<String> box = new JComboBox<String>();
                                    box.addItem("Archery");
                                    box.addItem("Automatics");
                                    box.addItem("Blades");
                                    box.addItem("Clubs");
                                    for (Skill w : characterController.getCharacter().getSkills()) {
                                        if (w.getName().contains("Exotic")
                                                && !w.getName().contains(
                                                "Vehicle")) {
                                            box.addItem(w.getName());
                                        }
                                    }
                                    box.addItem("Heavy Weapons");
                                    box.addItem("Longarms");
                                    box.addItem("Pistols");
                                    box.addItem("Throwing Weapons");
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose a type of combat skill:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Combat Skill!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    p.setName("Enhanced Accuracy ("
                                            + (String) box.getSelectedItem()
                                            + ")");
                                    p.setNotes("+1 Accuracy for attacks with "
                                            + (String) box.getSelectedItem());
                                } else if (p.getName().equals(
                                        "Improved Ability (Skill)")) {
                                    JComboBox<Skill> box = new JComboBox<Skill>();
                                    for (Skill sk : characterController.getCharacter()
                                            .getSkills()) {
                                        if (!sk.isKnowledge()) {
                                            box.addItem(sk);
                                        }
                                    }

                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose the skill you wish to improve:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Skill!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    Skill s = (Skill) box.getSelectedItem();
                                    if (Math.ceil(((double) s.getValue()) * 1.5) >= s
                                            .getValue() + p.getLevel()) {
                                        p.setName("Improved Ability ("
                                                + s.getName() + ")");
                                        p.setNotes("+"
                                                + p.getLevel()
                                                + " to "
                                                + s.getName()
                                                + " [already factored into skill rating]");
                                        s.setValue(s.getValue() + p.getLevel());
                                    } else {
                                        JOptionPane
                                                .showMessageDialog(
                                                        contentPanel,
                                                        "Can't raise Skill further than "
                                                                + Math.ceil(((double) s
                                                                .getValue()) * 1.5)
                                                                + "!",
                                                        "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                } else if (p.getName().equals(
                                        "Improved Physical Attribute")) {
                                    JComboBox<Attribute> box = new JComboBox<Attribute>();
                                    box.addItem(Attribute.AGILITY);
                                    box.addItem(Attribute.BODY);
                                    box.addItem(Attribute.REACTION);
                                    box.addItem(Attribute.STRENGTH);
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose an attribute:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Attribute!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    switch (box.getSelectedIndex()) {
                                        case 0:
                                            p.setName("Improved Physical Attribut (Agility)");
                                            p.setNotes("+"
                                                    + p.getLevel()
                                                    + " to Agility [already factored into attribute rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setAgility(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getAgility()
                                                                    + p.getLevel());
                                            break;
                                        case 1:
                                            p.setName("Improved Physical Attribut (Body)");
                                            p.setNotes("+"
                                                    + p.getLevel()
                                                    + " to Body [already factored into attribute rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setBody(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getBody()
                                                                    + p.getLevel());
                                            break;
                                        case 2:
                                            p.setName("Improved Physical Attribut (Reaction)");
                                            p.setNotes("+"
                                                    + p.getLevel()
                                                    + " to Reaction [already factored into attribute rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setReaction(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getReaction()
                                                                    + p.getLevel());
                                            break;
                                        case 3:
                                            p.setName("Improved Physical Attribut (Strength)");
                                            p.setNotes("+"
                                                    + p.getLevel()
                                                    + " to Strength [already factored into attribute rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setStrength(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getStrength()
                                                                    + p.getLevel());
                                            break;
                                    }
                                } else if (p.getName().equals(
                                        "Improved Potential")) {
                                    JComboBox<String> box = new JComboBox<String>();
                                    box.addItem("Physical");
                                    box.addItem("Mental");
                                    box.addItem("Social");
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please choose a limit to raise:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Limit!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    switch (box.getSelectedIndex()) {
                                        case 0:
                                            p.setName("Improved Potential (Physical)");
                                            p.setNotes("+1 to Physical Limit [already factored into limit rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setPhysicalLimit(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getPhysicalLimit() + 1);
                                            break;
                                        case 1:
                                            p.setName("Improved Potential (Mental)");
                                            p.setNotes("+1 to Mental Limit [already factored into limit rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setMentalLimit(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getMentalLimit() + 1);
                                            break;
                                        case 2:
                                            p.setName("Improved Potential (Social)");
                                            p.setNotes("+1 to Social Limit [already factored into limit rating]");
                                            characterController.getCharacter()
                                                    .getAttributes()
                                                    .setSocialLimit(
                                                            characterController.getCharacter()
                                                                    .getAttributes()
                                                                    .getSocialLimit() + 1);
                                            break;
                                    }
                                } else if (p.getName().contains("Improved Reflexes")) {
                                    characterController.getCharacter().getAttributes()
                                            .setReaction(
                                                    characterController.getCharacter()
                                                            .getAttributes()
                                                            .getReaction()
                                                            + p.getLevel());
                                    characterController.getCharacter().getAttributes()
                                            .setIniDice(
                                                    characterController.getCharacter()
                                                            .getAttributes()
                                                            .getIniDice()
                                                            + p.getLevel());
                                    p.setNotes("+"
                                            + p.getLevel()
                                            + " to Reaction, +"
                                            + p.getLevel()
                                            + "D6 Initiative [already factored into ratings]");
                                } else if (p.getName().equals("Improved Sense")) {
                                    JComboBox<String> box = new JComboBox<String>();
                                    box.addItem("Direction Sense");
                                    box.addItem("Improved Tactile");
                                    box.addItem("Perfect Pitch");
                                    box.addItem("Human Scale");
                                    box.addItem("Low-light vision");
                                    box.addItem("Flare compensation");
                                    box.addItem("Thermographic vision");
                                    box.addItem("Vision enhancement");
                                    box.addItem("Vision magnification");
                                    box.addItem("Audio Enhancement");
                                    box.addItem("Damper");
                                    box.addItem("Select sound filter");
                                    box.addItem("Spacial recognizer");
                                    inputs = new JComponent[]{
                                            new JLabel(
                                                    "Please Kind of Improved Sense:"),
                                            box};
                                    if (JOptionPane.showConfirmDialog(
                                            contentPanel, inputs,
                                            "Choose Improved Sense!",
                                            JOptionPane.OK_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                        return;
                                    }
                                    p.setName((String) box.getSelectedItem());
                                    switch (box.getSelectedIndex()) {
                                        case 0:
                                            p.setNotes("+2 dpm to Naviagtional skill test when traveling. Perception+Intuition(2) to identify the direction your facing and if you're above or below sealevel.");
                                            break;
                                        case 1:
                                            p.setNotes("+2 dpm to tactile Perception Tests.");
                                            break;
                                        case 2:
                                            p.setNotes("Perception+Intuition(2) Test to recognize a musical tone either from hearing it or feeling the vibration frequency.");
                                            break;
                                        case 3:
                                            p.setNotes("Perception+Intuition(2) Test to figure out the exact weight of an object if your able to lift or carry the object.");
                                            break;
                                        case 4:
                                            p.setNotes("See normally in light levels as low as starlight.");
                                            break;
                                        case 5:
                                            p.setNotes("Protects you from blinding flashes of light as well as simple glare.");
                                            break;
                                        case 6:
                                            p.setNotes("Enables you to see in infrared spectrum an thus seeing heat signatures.");
                                            break;
                                        case 7:
                                            p.setNotes("+1 to your limit on visual Perception Tests.");
                                            break;
                                        case 8:
                                            p.setNotes("Allows to magnify vision by up to fifty times.");
                                            break;
                                        case 9:
                                            p.setNotes("+1 to your limit on audio Perception Tests.");
                                            break;
                                        case 10:
                                            p.setNotes("+2 d.p.m. to resisting sonic attacks, including flashbangs.");
                                            break;
                                        case 11:
                                            JTextField soundGroup = new JTextField();
                                            JComponent input[] = new JComponent[]{
                                                    new JLabel("Please enter sound group you want to be able to focus on: (i.e. footsteps, rotors of a distand helicopter)"),
                                                    soundGroup
                                            };
                                            JOptionPane.showMessageDialog(contentPanel, input, "Select Sound Filter", JOptionPane.QUESTION_MESSAGE);
                                            p.setName("Select sound filter (" + soundGroup.getText() + ")");
                                            p.setNotes("Able to block out backgroundnoise and focus on " + soundGroup.getText() + ".");
                                            break;
                                        case 12:
                                            p.setNotes("+2 to your limit on audio Perception Tests to find the source of a specific sound.");
                                            break;
                                    }
                                } else if (p.getName().equals("Kinesics")) {
                                    p.setNotes("+" + p.getLevel() + "d.p.m. to resist Social Tests and tests to read your emotions (Judge Intentions, Assensing, etc)");
                                } else if (p.getName().equals("Light Body")) {
                                    p.setNotes("+" + p.getLevel() + " d.p.m. to Gymnastics Test when making a jump. Add +" + p.getLevel() + " to Agility when calculation maximum jump distance. Reduce fall distance by " + p.getLevel() + " when calculating fall damage.");
                                } else if (p.getName().equals("Missile Parry")) {
                                    p.setNotes("+" + p.getLevel() + " to defense pool against an attackers ranged attack test. If you succeed, you pluck the missile out of the air.");
                                } else if (p.getName().equals("Mystic Armor")) {
                                    p.setNotes("+" + p.getLevel() + " points of Armor per Level. Also in astral combat.");
                                } else if (p.getName().equals("Natural Immunity")) {
                                    p.setNotes("+" + p.getLevel() + " d.p.m. to resist toxins and disease.");
                                } else if (p.getName().equals("Pain Resistance")) {
                                    p.setNotes("+" + p.getLevel() * 2 + " d.p.m. on any tests to withstand suffering. Condition Monitor wound modifiers move one box per level.");
                                } else if (p.getName().equals("Rapid Healing")) {
                                    p.setNotes("+" + p.getLevel() + " to Body for Healing Tests. +" + p.getLevel() + " d.p.m. to any tests made to heal you.");
                                } else if (p.getName().equals("Spell Resistance")) {
                                    p.setNotes("+" + p.getLevel() + " d.p.m. to Resistance Tests against spells, spell rituals, preparations, or Innate Spell critter powers.");
                                } else if (p.getName().equals("Voice Control")) {
                                    characterController.getCharacter().getAttributes().setSocialLimit(characterController.getCharacter().getAttributes().getSocialLimit() + p.getLevel());
                                    p.setNotes("Impersonation+Charisma+" + p.getLevel() + "[" + characterController.getCharacter().getAttributes().getMentalLimit() + "] against Perception+Intuition/Voice recognition Device Rating*2, when trying to trick or fool someone. +" + p.getLevel() + " to Social Limit [already factored into your limits].");
                                }

                                characterController.getCharacter().getAdeptPowers().add(p);
                                characterController.getCharacter().getAttributes()
                                        .setAdeptPowerPoints(
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getAdeptPowerPoints()
                                                        - p.getCost());
                                powerPoints.setValue(characterController.getCharacter()
                                        .getAttributes().getAdeptPowerPoints());
                            } else {
                                JOptionPane.showMessageDialog(contentPanel,
                                        "Can't learn any more powers!",
                                        "Not enough Power Points!",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            powers.doClick();
                        }
                    });

                    raisePower.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox<Power> powerBox = new JComboBox<Power>();
                            String name = "Adept Power";

                            for (Power s : characterController.getCharacter().getAdeptPowers()) {
                                if (s.isLeveled()
                                        && s.getLevel() < characterController.getCharacter()
                                        .getAttributes().getMagic()) {
                                    powerBox.addItem(s);
                                }
                            }

                            JComponent[] inputs = new JComponent[]{
                                    new JLabel("Please choose a " + name
                                            + " you wish to raise:"), powerBox};
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose " + name
                                            + " you wish to learn!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }
                            Power p = (Power) powerBox.getSelectedItem();
                            double oldCost = p.getCost();
                            JSpinner levelSp = new JSpinner(
                                    new SpinnerNumberModel(p.getLevel(), p
                                            .getLevel(), characterController.getCharacter()
                                            .getAttributes().getMagic(), 1));
                            inputs = new JComponent[]{
                                    new JLabel("Please choose a new level:"),
                                    levelSp};
                            if (JOptionPane.showConfirmDialog(contentPanel,
                                    inputs, "Choose level!",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) != JOptionPane.OK_OPTION) {
                                return;
                            }

                            if (characterController.getCharacter().getAttributes()
                                    .getAdeptPowerPoints() - p.getCost() >= 0) {
                                p.setLevel((int) levelSp.getValue());
                                characterController.getCharacter()
                                        .getAttributes()
                                        .setAdeptPowerPoints(
                                                characterController.getCharacter()
                                                        .getAttributes()
                                                        .getAdeptPowerPoints()
                                                        - (p.getCost() - oldCost));
                                powerPoints.setValue(characterController.getCharacter()
                                        .getAttributes().getAdeptPowerPoints());
                            } else {
                                JOptionPane.showMessageDialog(contentPanel,
                                        "Can't raise Power Level!",
                                        "Not enough Power Points!",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            powers.doClick();
                        }
                    });

                    revalidate();
                    repaint();
                }
            });
        }
        persData.doClick();
        panel.revalidate();
        panel.repaint();
        return (panel);
    }

    private void showMeeleWeapList(final JPanel contentPanel,
                                   final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadMeeleWpList();
        MeeleWeapon[] toBuyArray = new MeeleWeapon[100];
        int index = 0;
        for (MeeleWeapon mw : LR.getMeeleWpList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        MeeleWeapon[] ownedArray = new MeeleWeapon[100];
        if (!characterController.getCharacter().getMeeleWeapons().isEmpty()) {
            ownedArray = characterController.getCharacter().getMeeleWeapons().toArray(
                    new MeeleWeapon[characterController.getCharacter().getMeeleWeapons().size()]);
        }
        final JList<MeeleWeapon> toBuy = new JList<MeeleWeapon>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<MeeleWeapon> owned = new JList<MeeleWeapon>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen && toBuy.getSelectedValue().getAvailability() > 12) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().setMoney(mon);
                    characterController.getCharacter().getMeeleWeapons().add(
                            toBuy.getSelectedValue());
                    MeeleWeapon array[] = new MeeleWeapon[characterController.getCharacter()
                            .getMeeleWeapons().size()];
                    int i = 0;
                    for (MeeleWeapon item : characterController.getCharacter().getMeeleWeapons()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().setMoney(mon);
                characterController.getCharacter().getMeeleWeapons().remove(
                        owned.getSelectedValue());
                MeeleWeapon array[] = new MeeleWeapon[characterController.getCharacter()
                        .getMeeleWeapons().size()];
                int i = 0;
                for (MeeleWeapon item : characterController.getCharacter().getMeeleWeapons()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner dmg = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner reach = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JSpinner acc = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner AP = new JSpinner(
                        new SpinnerNumberModel(0, -50, 50, 1));
                JComboBox<Boolean> depStr = new JComboBox<Boolean>();
                depStr.addItem(true);
                depStr.addItem(false);
                JComboBox<Boolean> stun = new JComboBox<Boolean>();
                stun.addItem(false);
                stun.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(1, 1, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Accuracy:"), acc,
                        new JLabel("Reach:"), reach, new JLabel("Damage:"),
                        dmg, new JLabel("Armor Piercing:"), AP,
                        new JLabel("Depends on strength:"), depStr,
                        new JLabel("Does Stun damage:"), stun,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new MeleeWeapon", JOptionPane.QUESTION_MESSAGE,
                        null);
                MeeleWeapon wp = new MeeleWeapon();
                wp.setName(name.getText());
                wp.setDamage((int) dmg.getValue());
                wp.setReach((int) reach.getValue());
                wp.setAccuracy((int) acc.getValue());
                wp.setAP((int) AP.getValue());
                wp.setDependsOnStrength((Boolean) depStr.getSelectedItem());
                wp.setDoesStunDamage((Boolean) stun.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                if (!wp.getName().isEmpty() && wp.getDamage() > 0
                        && wp.getPrice() > 0) {
                    LR.getMeeleWpList().add(wp);
                    LR.saveMeeleWpList();
                    MeeleWeapon[] array = new MeeleWeapon[LR.getMeeleWpList()
                            .size()];
                    int i = 0;
                    for (MeeleWeapon weap : LR.getMeeleWpList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showRangedWeapList(final JPanel contentPanel,
                                    final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadRangedWpList();
        RangedWeapon[] toBuyArray = new RangedWeapon[LR.getRangedWpList()
                .size()];
        int index = 0;
        for (RangedWeapon mw : LR.getRangedWpList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        RangedWeapon[] ownedArray = new RangedWeapon[100];
        if (!characterController.getCharacter().getRangedWeapons().isEmpty()) {
            ownedArray = characterController.getCharacter().getRangedWeapons()
                    .toArray(
                            new RangedWeapon[characterController.getCharacter()
                                    .getRangedWeapons().size()]);
        }
        final JList<RangedWeapon> toBuy = new JList<RangedWeapon>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<RangedWeapon> owned = new JList<RangedWeapon>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");
        JButton mod = new JButton("Add Modification");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);
        constraints.gridy += 1;
        contentPanel.add(mod, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen && toBuy.getSelectedValue().getAvailability() > 12) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().getRangedWeapons().add(
                            toBuy.getSelectedValue());
                    RangedWeapon array[] = new RangedWeapon[characterController.getCharacter()
                            .getRangedWeapons().size()];
                    int i = 0;
                    for (RangedWeapon item : characterController.getCharacter()
                            .getRangedWeapons()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getRangedWeapons().remove(
                        owned.getSelectedValue());
                RangedWeapon array[] = new RangedWeapon[characterController.getCharacter()
                        .getRangedWeapons().size()];
                int i = 0;
                for (RangedWeapon item : characterController.getCharacter().getRangedWeapons()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        mod.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (owned.getSelectedValue() == null) {
                    JOptionPane.showMessageDialog(contentPanel,
                            "No owned weapon selected!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LR.loadWeaponModList();
                JComboBox<WeaponModification> mods = new JComboBox<WeaponModification>();
                for (WeaponModification m : LR.getWpModList()) {
                    mods.addItem(m);
                }
                JComponent[] inputs = new JComponent[]{
                        new JLabel(
                                "Please choose a Modifiaction from the list below:"),
                        mods};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Choose Modifiaction for selected weapon!",
                        JOptionPane.QUESTION_MESSAGE, null);
                WeaponModification m = (WeaponModification) mods
                        .getSelectedItem();
                if (m.getPrice() > (double) money.getModel().getValue()) {
                    JOptionPane.showMessageDialog(contentPanel,
                            "Not enough money to buy modification!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (WeaponModification attached : owned.getSelectedValue()
                        .getModifications()) {
                    if (attached.getMountPoint().equals(m.getMountPoint())) {
                        JOptionPane.showMessageDialog(contentPanel,
                                "No free mount point for modification!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                double mon = (double) money.getModel().getValue()
                        + m.getPrice();
                money.getModel().setValue(mon);
                characterController.getCharacter()
                        .getRangedWeapons()
                        .get(characterController.getCharacter().getRangedWeapons().indexOf(
                                owned.getSelectedValue())).addModification(m);
                RangedWeapon array[] = new RangedWeapon[characterController.getCharacter()
                        .getRangedWeapons().size()];
                int i = 0;
                for (RangedWeapon item : characterController.getCharacter().getRangedWeapons()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner dmg = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner recoil = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JSpinner acc = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner ammo = new JSpinner(
                        new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner AP = new JSpinner(
                        new SpinnerNumberModel(0, -50, 50, 1));
                JComboBox<AmmoType> ammotype = new JComboBox<AmmoType>(AmmoType
                        .values());
                JSpinner numOfModes = new JSpinner(new SpinnerNumberModel(0, 0,
                        50, 1));
                JComboBox<Boolean> stun = new JComboBox<Boolean>();
                stun.addItem(false);
                stun.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(1, 1, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Accuracy:"), acc,
                        new JLabel("Damage:"), dmg,
                        new JLabel("Armor Piercing:"), AP,
                        new JLabel("Number of Modes:"), numOfModes,
                        new JLabel("Recoil:"), recoil, new JLabel("Ammo:"),
                        ammo, new JLabel("Ammotype:"), ammotype,
                        new JLabel("Does Stun damage:"), stun,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new RangedWeapon",
                        JOptionPane.QUESTION_MESSAGE, null);

                RangedWeapon wp = new RangedWeapon();
                int modeCount = (int) numOfModes.getValue();
                JComboBox<Mode> mode = new JComboBox<Mode>(Mode.values());
                inputs = new JComponent[]{new JLabel("Choose Weapon Mode"),
                        mode};
                while (modeCount > 0) {
                    JOptionPane.showMessageDialog(contentPanel, inputs,
                            "Add Weapon Mode", JOptionPane.QUESTION_MESSAGE,
                            null);
                    wp.addMode((Mode) mode.getSelectedItem());
                    modeCount--;
                }

                wp.setName(name.getText());
                wp.setDamage((int) dmg.getValue());
                wp.setRecoil((int) recoil.getValue());
                wp.setAccuracy((int) acc.getValue());
                wp.setArmorPiercing((int) AP.getValue());
                wp.setAmmo((int) ammo.getValue());
                wp.setAmmotype((AmmoType) ammotype.getSelectedItem());
                wp.setDoesStunDamage((Boolean) stun.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                if (!wp.getName().isEmpty() && wp.getDamage() > 0
                        && wp.getPrice() > 0) {
                    LR.getRangedWpList().add(wp);
                    LR.saveRangedWpList();
                    RangedWeapon[] array = new RangedWeapon[LR
                            .getRangedWpList().size()];
                    int i = 0;
                    for (RangedWeapon weap : LR.getRangedWpList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showAmmunitionList(final JPanel contentPanel,
                                    final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadAmmuList();
        Ammunition[] toBuyArray = new Ammunition[100];
        int index = 0;
        for (Ammunition mw : LR.getAmmuList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Ammunition[] ownedArray = new Ammunition[100];
        if (!characterController.getCharacter().getMeeleWeapons().isEmpty()) {
            ownedArray = characterController.getCharacter().getAmmunition().toArray(
                    new Ammunition[characterController.getCharacter().getAmmunition().size()]);
        }
        final JList<Ammunition> toBuy = new JList<Ammunition>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Ammunition> owned = new JList<Ammunition>(ownedArray);
        owned.revalidate();
        owned.repaint();

        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen && toBuy.getSelectedValue().getAvailability() > 12) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JSpinner roundSp = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
                if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(contentPanel, new JComponent[]{new JLabel("How many 10 shot rounds would you like to buy?"), roundSp}, "How many rounds", JOptionPane.CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    return;
                }
                Ammunition ammu = (Ammunition) toBuy.getSelectedValue().clone();
                ammu.setRounds((int) roundSp.getValue());
                double cost = ammu.getPrice() * ammu.getRounds();

                if ((double) money.getModel().getValue() >= cost) {
                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    if (characterController.getCharacter().getAmmunition().contains(ammu)) {
                        characterController.getCharacter().getAmmunition().get(characterController.getCharacter().getAmmunition().indexOf(ammu)).addRounds(ammu.getRounds());
                    } else {
                        characterController.getCharacter().getAmmunition().add(ammu);
                    }
                    Ammunition array[] = new Ammunition[characterController.getCharacter()
                            .getAmmunition().size()];
                    int i = 0;
                    for (Ammunition item : characterController.getCharacter().getAmmunition()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getAmmunition().remove(
                        owned.getSelectedValue());
                Ammunition array[] = new Ammunition[characterController.getCharacter()
                        .getAmmunition().size()];
                int i = 0;
                for (Ammunition item : characterController.getCharacter().getAmmunition()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner dmgMod = new JSpinner(new SpinnerNumberModel(0, -50,
                        50, 1));
                JSpinner piercingMod = new JSpinner(new SpinnerNumberModel(0,
                        -50, 50, 1));
                JComboBox<Boolean> stun = new JComboBox<Boolean>();
                stun.addItem(false);
                stun.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Damage modificator:"), dmgMod,
                        new JLabel("Armor Piercing modificator:"), piercingMod,
                        new JLabel("Does Stun damage:"), stun,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new Ammunition", JOptionPane.QUESTION_MESSAGE,
                        null);
                Ammunition wp = new Ammunition();
                wp.setName(name.getText());
                wp.setDamageMod((int) dmgMod.getValue());
                wp.setPiercingMod((int) piercingMod.getValue());
                wp.setDoesStunDamage((Boolean) stun.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                if (!wp.getName().isEmpty() && wp.getDamageMod() > 0
                        && wp.getPrice() > 0) {
                    LR.getAmmuList().add(wp);
                    LR.saveAmmuList();
                    Ammunition[] array = new Ammunition[LR.getAmmuList().size()];
                    int i = 0;
                    for (Ammunition weap : LR.getAmmuList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showClothingList(final JPanel contentPanel,
                                  final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadArmorList();
        Armor[] toBuyArray = new Armor[100];
        int index = 0;
        for (Armor mw : LR.getArmorList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (mw.isAddable()) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Armor[] ownedArray = new Armor[100];
        if (!characterController.getCharacter().getArmor().isEmpty()) {
            ownedArray = characterController.getCharacter().getArmor().toArray(
                    new Armor[characterController.getCharacter().getArmor().size()]);
        }
        final JList<Armor> toBuy = new JList<Armor>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Armor> owned = new JList<Armor>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen && toBuy.getSelectedValue().getAvailability() > 12) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    Armor cloth = (Armor) toBuy.getSelectedValue().clone();
                    if (toBuy.getSelectedValue().getName().equals("Clothing")) {
                        JTextField name = new JTextField();
                        JSpinner price = new JSpinner(new SpinnerNumberModel(
                                20.0, 20.0, (double) money.getModel()
                                .getValue(), 0.5));
                        JComponent[] inputs = new JComponent[]{
                                new JLabel("Name:"), name,
                                new JLabel("Price:"), price,
                        };
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        inputs,
                                        "Please enter clothing name/description and price",
                                        JOptionPane.QUESTION_MESSAGE, null);
                        cloth.setName(name.getText());
                        cloth.setPrice((double) price.getValue());
                        cloth.setNotes("");
                        cost = cloth.getPrice();
                    }
                    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(contentPanel, "Do you wish to add any additions to your Armor/Clothing?", "Additons", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                        ArrayList<Armor> adds = new ArrayList<Armor>();
                        for (Armor a : LR.getArmorList()) {
                            if (a.isAddable())
                                adds.add(a);
                        }
                        JList<Armor> additions = new JList<Armor>(adds.toArray(new Armor[adds.size()]));
                        additions.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                        JOptionPane.showMessageDialog(contentPanel, new JComponent[]{new JLabel("Additions:"), additions},
                                "Please choose addition!",
                                JOptionPane.QUESTION_MESSAGE, null);
                        String addNames = "";
                        for (Armor a : additions.getSelectedValuesList()) {
                            addNames += "[" + a.getName() + "]";
                            cost += a.getPrice();
                            cloth.setAvailability(cloth.getAvailability() + a.getAvailability());
                            cloth.setRating(cloth.getRating() + a.getRating());
                        }
                        cloth.setName(cloth.getName() + addNames);
                        cloth.setPrice(cost);
                    }
                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().getArmor().add(cloth);
                    Armor array[] = new Armor[characterController.getCharacter().getArmor()
                            .size()];
                    int i = 0;
                    for (Armor item : characterController.getCharacter().getArmor()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getArmor().remove(owned.getSelectedValue());
                Armor array[] = new Armor[characterController.getCharacter().getArmor().size()];
                int i = 0;
                for (Armor item : characterController.getCharacter().getArmor()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner rating = new JSpinner(new SpinnerNumberModel(-1, -1,
                        50, 1));
                JSpinner maxRating = new JSpinner(new SpinnerNumberModel(-1,
                        -1, 50, 1));
                JComboBox<Boolean> ratingLeveled = new JComboBox<Boolean>();
                ratingLeveled.addItem(false);
                ratingLeveled.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");
                JComboBox<Boolean> addable = new JComboBox<Boolean>();
                addable.addItem(false);
                addable.addItem(true);

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Rating:"), rating,
                        new JLabel("Is cumulative with other armor:"), addable,
                        new JLabel("Max Rating:"), maxRating,
                        new JLabel("Is rating leveled:"), ratingLeveled,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new Armor/Clothing",
                        JOptionPane.QUESTION_MESSAGE, null);
                Armor wp = new Armor();
                wp.setName(name.getText());
                wp.setRating((int) rating.getValue());
                wp.setMaxRating((int) maxRating.getValue());
                wp.setRatingLeveled((Boolean) ratingLeveled.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                wp.setAddable((Boolean) addable.getSelectedItem());
                if (!wp.getName().isEmpty() && wp.getRating() > -1
                        && wp.getPrice() > 0) {
                    LR.getArmorList().add(wp);
                    LR.saveArmorList();
                    Armor[] array = new Armor[LR.getArmorList().size()];
                    int i = 0;
                    for (Armor weap : LR.getArmorList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showCyberwareList(final JPanel contentPanel, final JSpinner essence,
                                   final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadAugmentationList();
        Augmentation[] toBuyArray = new Augmentation[100];
        int index = 0;
        for (Augmentation mw : LR.getAugmentList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Augmentation[] ownedArray = new Augmentation[100];
        if (!characterController.getCharacter().getArmor().isEmpty()) {
            ownedArray = characterController.getCharacter().getAugmentations()
                    .toArray(
                            new Augmentation[characterController.getCharacter()
                                    .getAugmentations().size()]);
        }
        final JList<Augmentation> toBuy = new JList<Augmentation>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Augmentation> owned = new JList<Augmentation>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Augmentation.Grade> grade = new JComboBox<Augmentation.Grade>(
                        Augmentation.Grade.values());
                JComponent[] inputs = new JComponent[]{
                        new JLabel("Please choose cyber-/bioware grade"), grade};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Choose Grade", JOptionPane.QUESTION_MESSAGE);
                Augmentation aug = toBuy.getSelectedValue();
                aug.setGrade((Augmentation.Grade) grade.getSelectedItem());
                if (chargen
                        && (toBuy.getSelectedValue().getAvailability() > 12 || toBuy
                        .getSelectedValue().getRating() > 6)) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ((double) money.getModel().getValue() >= aug.getPrice()
                        && characterController.getCharacter().getAttributes().getEssence() > aug
                        .getEssenceLoss()) {
                    double mon = (double) money.getModel().getValue()
                            - aug.getPrice();
                    if (aug.canBeBuildIn() && JOptionPane.showConfirmDialog(contentPanel, "Do you wish to build this into another piece of cyberware?", "Built into Cyberlimb", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.NO_OPTION) {
                        JComboBox<Augmentation> augments = new JComboBox<Augmentation>();
                        for (Augmentation a : characterController.getCharacter()
                                .getAugmentations()) {
                            if (!a.canBeBuildIn()
                                    && a.getCapacity() > aug.getCapacity()) {
                                augments.addItem(a);
                            }
                        }
                        if (augments.getItemCount() < 1) {
                            JOptionPane
                                    .showMessageDialog(
                                            contentPanel,
                                            "You have no augmentations to build this into!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            inputs = new JComponent[]{
                                    new JLabel(
                                            "Please choose augmentation to build into!"),
                                    augments};
                            JOptionPane.showMessageDialog(contentPanel, inputs,
                                    "Choose Augmentation",
                                    JOptionPane.QUESTION_MESSAGE);
                            characterController.getCharacter().getAugmentations().get(characterController.getCharacter().getAugmentations().indexOf((Augmentation) augments.getSelectedItem())).setNotes(
                                    characterController.getCharacter().getAugmentations().get(characterController.getCharacter().getAugmentations().indexOf((Augmentation) augments.getSelectedItem()))
                                            .getNotes() + " | " + aug.getName());
                            if (characterController.getCharacter().getAugmentations().get(characterController.getCharacter().getAugmentations().indexOf((Augmentation) augments.getSelectedItem())).getNotes().startsWith(" | ")) {
                                characterController.getCharacter().getAugmentations().get(characterController.getCharacter().getAugmentations().indexOf((Augmentation) augments.getSelectedItem())).setNotes(characterController.getCharacter().getAugmentations().get(characterController.getCharacter().getAugmentations().indexOf((Augmentation) augments.getSelectedItem())).getNotes().substring(3));
                            }
                            characterController.getCharacter()
                                    .getAugmentations()
                                    .get(characterController.getCharacter().getAugmentations()
                                            .indexOf(
                                                    (Augmentation) augments
                                                            .getSelectedItem()))
                                    .setCapacity(
                                            characterController.getCharacter()
                                                    .getAugmentations()
                                                    .get(characterController.getCharacter()
                                                            .getAugmentations()
                                                            .indexOf(
                                                                    (Augmentation) augments
                                                                            .getSelectedItem()))
                                                    .getCapacity()
                                                    - aug.getCapacity());
                            aug.setEssenceLoss(0.0);
                        }
                    }
                    money.getModel().setValue(mon);
                    double threshold = Math.floor(characterController.getCharacter()
                            .getAttributes().getEssence());
                    characterController.getCharacter().getAttributes().setEssence(
                            characterController.getCharacter().getAttributes().getEssence()
                                    - aug.getEssenceLoss());
                    if (characterController.getCharacter().getAttributes().getEssence() < threshold
                            && characterController.getCharacter().getAttributes().getMagic() > 0) {
                        characterController.getCharacter().getAttributes()
                                .setMagic(
                                        characterController.getCharacter().getAttributes()
                                                .getMagic() - 1);
                    }
                    characterController.getCharacter().getAugmentations().add(aug);
                    essence.setValue(characterController.getCharacter().getAttributes().getEssence());
                    Augmentation array[] = new Augmentation[characterController.getCharacter()
                            .getAugmentations().size()];
                    int i = 0;
                    for (Augmentation item : characterController.getCharacter()
                            .getAugmentations()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getAugmentations().remove(
                        owned.getSelectedValue());
                Augmentation array[] = new Augmentation[characterController.getCharacter()
                        .getAugmentations().size()];
                int i = 0;
                for (Augmentation item : characterController.getCharacter().getAugmentations()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner rating = new JSpinner(new SpinnerNumberModel(-1, -1,
                        50, 1));
                JSpinner maxRating = new JSpinner(new SpinnerNumberModel(-1,
                        -1, 50, 1));
                JComboBox<Boolean> isLeveled = new JComboBox<Boolean>();
                isLeveled.addItem(false);
                isLeveled.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");
                JComboBox<Boolean> builtIn = new JComboBox<Boolean>();
                builtIn.addItem(false);
                builtIn.addItem(true);
                JSpinner capacity = new JSpinner(new SpinnerNumberModel(0.0,
                        0.0, 50.0, 0.1));
                JSpinner essenseLoss = new JSpinner(new SpinnerNumberModel(0.0,
                        0.0, 50.0, 0.1));

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Rating:"), rating,
                        new JLabel("Max Rating:"), maxRating,
                        new JLabel("Is leveled:"), isLeveled,
                        new JLabel("Can be built in"), builtIn,
                        new JLabel("Capacity (cost if built in):"), capacity,
                        new JLabel("Essence loss:"), essenseLoss,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new Augmentation",
                        JOptionPane.QUESTION_MESSAGE, null);
                Augmentation wp = new Augmentation();
                wp.setName(name.getText());
                wp.setRating((int) rating.getValue());
                wp.setMaxRating((int) maxRating.getValue());
                wp.setLeveled((Boolean) isLeveled.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                wp.setcanBeBuildIn((Boolean) builtIn.getSelectedItem());
                wp.setEssenceLoss((double) essenseLoss.getValue());
                wp.setCapacity((double) capacity.getValue());
                if (!wp.getName().isEmpty() && wp.getEssenceLoss() > -1
                        && wp.getPrice() > 0) {
                    LR.getAugmentList().add(wp);
                    LR.saveAugmentationList();
                    Augmentation[] array = new Augmentation[LR.getAugmentList()
                            .size()];
                    int i = 0;
                    for (Augmentation weap : LR.getAugmentList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });
        repaint();
        revalidate();
    }

    private void showDeckList(final JPanel contentPanel, final JSpinner money,
                              final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadDeckList();
        Deck[] toBuyArray = new Deck[100];
        int index = 0;
        for (Deck mw : LR.getDeckList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Deck[] ownedArray = new Deck[100];
        if (!characterController.getCharacter().getCyberdecks().isEmpty()) {
            ownedArray = characterController.getCharacter().getCyberdecks().toArray(
                    new Deck[characterController.getCharacter().getCyberdecks().size()]);
        }
        final JList<Deck> toBuy = new JList<Deck>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Deck> owned = new JList<Deck>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");
        JButton prog = new JButton("Add Program");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);
        constraints.gridy += 1;
        contentPanel.add(prog, constraints);
        JButton mainDeck = new JButton("Choose main Deck");
        constraints.gridy += 1;
        contentPanel.add(mainDeck, constraints);
        mainDeck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Deck> decks = new JComboBox<Deck>(characterController.getCharacter().getCyberdecks().toArray(new Deck[characterController.getCharacter().getCyberdecks().size()]));
                if (JOptionPane.CANCEL_OPTION != JOptionPane.showConfirmDialog(contentPanel, new JComponent[]{new JLabel("Please choose a primary Deck:"), decks}, "Choose Deck", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    Deck t = characterController.getCharacter().getCyberdecks().get(0);
                    characterController.getCharacter().getCyberdecks().set(0, (Deck) decks.getSelectedItem());
                    characterController.getCharacter().getCyberdecks().set(decks.getSelectedIndex(), t);
                }

            }
        });


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen
                        && (toBuy.getSelectedValue().getAvailability() > 12 || toBuy
                        .getSelectedValue().getDeviceRating() > 6)) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    Deck deck = toBuy.getSelectedValue();
                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().getCyberdecks().add(deck);
                    Deck array[] = new Deck[characterController.getCharacter().getCyberdecks()
                            .size()];
                    int i = 0;
                    for (Deck item : characterController.getCharacter().getCyberdecks()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getCyberdecks().remove(
                        owned.getSelectedValue());
                Deck array[] = new Deck[characterController.getCharacter().getCyberdecks().size()];
                int i = 0;
                for (Deck item : characterController.getCharacter().getCyberdecks()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        prog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                LR.loadProgList();
                JComboBox<Program> progs = new JComboBox<Program>();
                for (Program P : characterController.getCharacter().getPrograms()) {
                    System.out.println(P);
                }
                for (Program p : LR.getProgramList()) {
                    if (!(characterController.getCharacter().getPrograms().contains(p))) {
                        progs.addItem(p);
                    } else {
                        System.out.println(p);
                    }
                }
                JComponent[] inputs = new JComponent[]{
                        new JLabel(
                                "Please choose a Program from the list below:"),
                        progs};
                if (JOptionPane.CANCEL_OPTION == JOptionPane.showConfirmDialog(contentPanel, inputs,
                        "Choose Program you wish to purchase!", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE)) {
                    return;
                }
                Program p = (Program) progs.getSelectedItem();
                if (p.getPrice() > characterController.getCharacter().getMoney()) {
                    JOptionPane.showMessageDialog(contentPanel,
                            "Not enough money to buy program!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double mon = characterController.getCharacter().getMoney() - p.getPrice();
                money.getModel().setValue(mon);
                characterController.getCharacter().setMoney(mon);
                characterController.getCharacter().addProgram(p);
                ;
				/*Deck array[] = new Deck[characterController.getCharacter().getCyberdecks().size()];
				int i = 0;
				for (Deck item : characterController.getCharacter().getCyberdecks()) {
					array[i] = item;
					i++;
				}
				owned.setListData(array);
				owned.revalidate();
				owned.repaint();*/
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField model = new JTextField();
                JSpinner deviceRating = new JSpinner(new SpinnerNumberModel(0,
                        0, 20, 1));
                JSpinner atr1 = new JSpinner(
                        new SpinnerNumberModel(0, 0, 20, 1));
                JSpinner atr2 = new JSpinner(
                        new SpinnerNumberModel(0, 0, 20, 1));
                JSpinner atr3 = new JSpinner(
                        new SpinnerNumberModel(0, 0, 20, 1));
                JSpinner atr4 = new JSpinner(
                        new SpinnerNumberModel(0, 0, 20, 1));
                JSpinner programs = new JSpinner(new SpinnerNumberModel(0, 0,
                        20, 1));
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{
                        new JLabel("Model name:"), model,
                        new JLabel("Device Rating:"), deviceRating,
                        new JLabel("Attribute Array Value 1:"), atr1,
                        new JLabel("Attribute Array Value 2:"), atr2,
                        new JLabel("Attribute Array Value 3:"), atr3,
                        new JLabel("Attribute Array Value 4:"), atr4,
                        new JLabel("Programs:"), programs,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new Cyberdeck", JOptionPane.QUESTION_MESSAGE,
                        null);
                Deck wp = new Deck();
                wp.setModel(model.getText());
                wp.setDeviceRating((int) deviceRating.getValue());
                wp.setSimultaniousPrograms((int) programs.getValue());
                wp.setAttributeArray(new int[]{(int) atr1.getValue(),
                        (int) atr3.getValue(), (int) atr3.getValue(),
                        (int) atr4.getValue()});
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());

                if (!wp.getModel().isEmpty() && wp.getAttributeArray() != null
                        && wp.getPrice() > 0) {
                    LR.getDeckList().add(wp);
                    LR.saveDeckList();
                    Deck[] array = new Deck[LR.getDeckList().size()];
                    int i = 0;
                    for (Deck weap : LR.getDeckList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showGearList(final JPanel contentPanel, final JSpinner money,
                              final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadGearList();
        Gear[] toBuyArray = new Gear[100];
        int index = 0;
        for (Gear mw : LR.getGearList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Gear[] ownedArray = new Gear[100];
        if (!characterController.getCharacter().getGear().isEmpty()) {
            ownedArray = characterController.getCharacter().getGear().toArray(
                    new Gear[characterController.getCharacter().getGear().size()]);
        }
        final JList<Gear> toBuy = new JList<Gear>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Gear> owned = new JList<Gear>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen
                        && (toBuy.getSelectedValue().getAvailability() > 12 || toBuy
                        .getSelectedValue().getRating() > 6)) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    Gear gear = toBuy.getSelectedValue();

                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().getGear().add(gear);
                    Gear array[] = new Gear[characterController.getCharacter().getGear().size()];
                    int i = 0;
                    for (Gear item : characterController.getCharacter().getGear()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getGear().remove(owned.getSelectedValue());
                Gear array[] = new Gear[characterController.getCharacter().getGear().size()];
                int i = 0;
                for (Gear item : characterController.getCharacter().getGear()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField name = new JTextField();
                JSpinner rating = new JSpinner(new SpinnerNumberModel(-1, -1,
                        50, 1));
                JSpinner maxRating = new JSpinner(new SpinnerNumberModel(-1,
                        -1, 50, 1));
                JComboBox<Boolean> ratingLeveled = new JComboBox<Boolean>();
                ratingLeveled.addItem(false);
                ratingLeveled.addItem(true);
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name, new JLabel("Rating:"), rating,
                        new JLabel("Max Rating:"), maxRating,
                        new JLabel("Is rating leveled:"), ratingLeveled,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new piece of gear",
                        JOptionPane.QUESTION_MESSAGE, null);
                Gear wp = new Gear();
                wp.setName(name.getText());
                wp.setRating((int) rating.getValue());
                wp.setMaxRating((int) maxRating.getValue());
                wp.setRatingLeveled((Boolean) ratingLeveled.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                if (!wp.getName().isEmpty() && wp.getRating() > -1
                        && wp.getPrice() > 0) {
                    LR.getGearList().add(wp);
                    LR.saveGearList();
                    Gear[] array = new Gear[LR.getGearList().size()];
                    int i = 0;
                    for (Gear weap : LR.getGearList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });

        repaint();
        revalidate();
    }

    private void showVehicleList(final JPanel contentPanel,
                                 final JSpinner money, final boolean chargen) {
        contentPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        LR.loadVehicleList();
        Vehicle[] toBuyArray = new Vehicle[100];
        int index = 0;
        for (Vehicle mw : LR.getVehicleList()) {
            boolean okay = true;
            if (chargen && mw.getAvailability() > 12) {
                okay = false;
            }
            if (okay) {
                toBuyArray[index] = mw;
                index++;
            }
        }
        Vehicle[] ownedArray = new Vehicle[100];
        if (!characterController.getCharacter().getVehicles().isEmpty()) {
            ownedArray = characterController.getCharacter().getVehicles().toArray(
                    new Vehicle[characterController.getCharacter().getVehicles().size()]);
        }
        final JList<Vehicle> toBuy = new JList<Vehicle>(toBuyArray);
        toBuy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JList<Vehicle> owned = new JList<Vehicle>(ownedArray);
        JScrollPane toBuyPane = new JScrollPane(toBuy);
        JScrollPane ownedPane = new JScrollPane(owned);
        JButton buy = new JButton("Buy");
        JButton sell = new JButton("Sell");
        JButton create = new JButton("ADD");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(toBuyPane, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        contentPanel.add(buy, constraints);
        constraints.gridx = 3;
        contentPanel.add(sell, constraints);
        constraints.gridx = 5;
        contentPanel.add(create, constraints);
        JButton mainVeh = new JButton("Choose main Vehicle");
        constraints.gridy += 1;
        contentPanel.add(mainVeh, constraints);
        mainVeh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Vehicle> vehs = new JComboBox<Vehicle>(characterController.getCharacter().getVehicles().toArray(new Vehicle[characterController.getCharacter().getVehicles().size()]));
                if (JOptionPane.CANCEL_OPTION != JOptionPane.showConfirmDialog(contentPanel, new JComponent[]{new JLabel("Please choose a primary Vehicle:"), vehs}, "Choose Vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    Vehicle t = characterController.getCharacter().getVehicles().get(0);
                    characterController.getCharacter().getVehicles().set(0, (Vehicle) vehs.getSelectedItem());
                    characterController.getCharacter().getVehicles().set(vehs.getSelectedIndex(), t);
                }

            }
        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.weightx = 2.0;
        constraints.weighty = 2.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(ownedPane, constraints);
        constraints.fill = GridBagConstraints.NONE;

        buy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (chargen && toBuy.getSelectedValue().getAvailability() > 12) {
                    JOptionPane
                            .showMessageDialog(
                                    contentPanel,
                                    "You can't purchase any items with a rating greater than 6 or an availabilty rating above 12 during character generation!",
                                    "Character Generation",
                                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double cost = toBuy.getSelectedValue().getPrice();

                if ((double) money.getModel().getValue() >= cost) {
                    Vehicle vehicle = toBuy.getSelectedValue();

                    double mon = (double) money.getModel().getValue() - cost;
                    money.getModel().setValue(mon);
                    characterController.getCharacter().getVehicles().add(vehicle);
                    Vehicle array[] = new Vehicle[characterController.getCharacter()
                            .getVehicles().size()];
                    int i = 0;
                    for (Vehicle item : characterController.getCharacter().getVehicles()) {
                        array[i] = item;
                        i++;
                    }
                    owned.setListData(array);
                    owned.revalidate();
                    owned.repaint();
                }

            }
        });

        sell.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double cost = 0;

                if (chargen) {
                    cost = owned.getSelectedValue().getPrice();

                } else {
                    JSpinner nego = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner cha = new JSpinner(new SpinnerNumberModel(0, 0,
                            13, 1));
                    JSpinner social = new JSpinner(new SpinnerNumberModel(0, 0,
                            50, 1));
                    JComponent[] input = new JComponent[]{
                            new JLabel(
                                    "If you haven't found a buyer yet, make a check according to the rules on page 418 of core rulebook now!"),
                            new JLabel(
                                    "Please enter buyer Negotiation Skill rating:"),
                            nego,
                            new JLabel("Please enter buyer Charisma rating:"),
                            cha,
                            new JLabel("Please enter buyer Social Limit:"),
                            social,};
                    JOptionPane.showMessageDialog(contentPanel, input,
                            "Fencing Item", JOptionPane.INFORMATION_MESSAGE);
                    DieRoller roller = new DieRoller();
                    Skill negotiation = new Skill();
                    negotiation.setName("Negotiation");
                    SkillGroup influence = new SkillGroup();
                    influence.setName("INFLUENCE");
                    int you = characterController.getCharacter().getAttributes().getCharisma();
                    if (characterController.getCharacter().getSkills().contains(negotiation)) {
                        you += characterController.getCharacter()
                                .getSkills()
                                .get(characterController.getCharacter().getSkills().indexOf(
                                        negotiation)).getValue();
                    } else if (characterController.getCharacter().getSkillGroups().contains(
                            influence)) {
                        you += characterController.getCharacter()
                                .getSkillGroups()
                                .get(characterController.getCharacter().getSkillGroups().indexOf(
                                        influence)).getValue();
                    }
                    DieRoll roll = roller.rollDice(you);
                    if (roll.isGlitched()) {
                        String crit = "";
                        if (roll.getResult() == CRITICAL_GLITCH) {
                            crit += " critical";
                        }
                        JOptionPane
                                .showMessageDialog(
                                        contentPanel,
                                        "You rolled a"
                                                + crit
                                                + " glitch! The deal is off - or worse!",
                                        "Glitch", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    you = (int) roll.getNumberOfSuccesses();
                    if (you > characterController.getCharacter().getAttributes().getSocialLimit()) {
                        you = characterController.getCharacter().getAttributes().getSocialLimit();
                    }
                    roll = roller.rollDice((int) nego.getValue()
                            + (int) cha.getValue());
                    int buy = (int) roll.getNumberOfSuccesses();
                    if (roll.isGlitched()) {
                        buy = 0;
                    }
                    if (buy > (int) social.getValue()) {
                        buy = (int) social.getValue();
                    }
                    int netSucc = you - buy;
                    double percentage = ((double) (25 + (5 * netSucc))) / 100;
                    System.out.println("Player success:" + you
                            + " Buyer success:" + buy + " Net Success:"
                            + netSucc + " Percentage:" + percentage);
                    cost = owned.getSelectedValue().getPrice() * percentage;
                    JOptionPane.showMessageDialog(contentPanel,
                            "You managed to sell the item for " + percentage
                                    * 100 + "% of the orignal cost at " + cost
                                    + " NuYen!", "Sold!",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                double mon = (double) money.getModel().getValue() + cost;
                money.getModel().setValue(mon);
                characterController.getCharacter().getVehicles().remove(owned.getSelectedValue());
                Vehicle array[] = new Vehicle[characterController.getCharacter().getVehicles()
                        .size()];
                int i = 0;
                for (Vehicle item : characterController.getCharacter().getVehicles()) {
                    array[i] = item;
                    i++;
                }
                owned.setListData(array);
                owned.revalidate();
                owned.repaint();
            }
        });

        create.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * private int handlingOnRoad, handlingOffRoad, speedOnRoad,
                 * speedOffRoad, acceleration, body, armor, pilot, sensor,
                 * seats; craftType
                 */
                JTextField name = new JTextField();
                JSpinner handlingOnRoad = new JSpinner(new SpinnerNumberModel(
                        0, 0, 50, 1));
                JSpinner handlingOffRoad = new JSpinner(new SpinnerNumberModel(
                        -1, -1, 50, 1));
                JSpinner speedOnRoad = new JSpinner(new SpinnerNumberModel(0,
                        0, 50, 1));
                JSpinner speedOffRoad = new JSpinner(new SpinnerNumberModel(-1,
                        -1, 50, 1));
                JSpinner acceleration = new JSpinner(new SpinnerNumberModel(0,
                        0, 50, 1));
                JSpinner body = new JSpinner(
                        new SpinnerNumberModel(0, 0, 50, 1));
                JSpinner armor = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JSpinner pilot = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JSpinner sensor = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JSpinner seats = new JSpinner(new SpinnerNumberModel(0, 0, 50,
                        1));
                JComboBox<Vehicle.CraftType> craft = new JComboBox<Vehicle.CraftType>(Vehicle.CraftType.values());
                JSpinner price = new JSpinner(new SpinnerNumberModel(0.0, 0.0,
                        1000000.0, 0.5));
                JSpinner avail = new JSpinner(new SpinnerNumberModel(0, 0, 20,
                        1));
                JComboBox<String> avtype = new JComboBox<String>();
                avtype.addItem("Legal");
                avtype.addItem("Restricted");
                avtype.addItem("Forbidden");

                JComponent[] inputs = new JComponent[]{new JLabel("Name:"),
                        name,
                        new JLabel("Type of Craft:"), craft,
                        new JLabel("Price:"), price,
                        new JLabel("Availability:"), avail,
                        new JLabel("Restriction:"), avtype};
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new vehicle", JOptionPane.QUESTION_MESSAGE,
                        null);
                if (((Vehicle.CraftType) craft.getSelectedItem()).equals(Vehicle.CraftType.Groundcraft)) {
                    inputs = new JComponent[]{
                            new JLabel("Handling (on road):"),
                            handlingOnRoad,
                            new JLabel("Handling (off road):"),
                            handlingOffRoad,
                            new JLabel("Speed (on road):"),
                            speedOnRoad,
                            new JLabel("Speed (off road):"),
                            speedOffRoad, new JLabel("Acceleration:"),
                            acceleration, new JLabel("Body:"), body,
                            new JLabel("Armor:"), armor, new JLabel("Pilot:"),
                            pilot, new JLabel("Sensor:"), sensor,
                            new JLabel("Seats:"), seats,
                    };
                } else {
                    inputs = new JComponent[]{
                            new JLabel("Handling:"),
                            handlingOnRoad,

                            new JLabel("Speed:"),
                            speedOnRoad,
                            new JLabel("Acceleration:"),
                            acceleration, new JLabel("Body:"), body,
                            new JLabel("Armor:"), armor, new JLabel("Pilot:"),
                            pilot, new JLabel("Sensor:"), sensor,
                            new JLabel("Seats:"), seats,
                    };
                }
                JOptionPane.showMessageDialog(contentPanel, inputs,
                        "Create new vehicle", JOptionPane.QUESTION_MESSAGE,
                        null);
                Vehicle wp = new Vehicle();
                wp.setName(name.getText());
                wp.setHandling((int) handlingOnRoad.getValue());
                wp.setHandlingOffRoad((int) handlingOffRoad.getValue());
                wp.setSpeed((int) speedOnRoad.getValue());
                wp.setSpeedOffRoad((int) speedOffRoad.getValue());
                wp.setAcceleration((int) acceleration.getValue());
                wp.setBody((int) body.getValue());
                wp.setArmor((int) armor.getValue());
                wp.setPilot((int) pilot.getValue());
                wp.setSensor((int) sensor.getValue());
                wp.setSeats((int) seats.getValue());
                wp.setType((Vehicle.CraftType) craft.getSelectedItem());
                wp.setPrice((double) price.getValue());
                wp.setAvailability((int) avail.getValue());
                wp.setAvailabilityType(avtype.getSelectedIndex());
                if (!wp.getName().isEmpty() && wp.getType() != null
                        && wp.getPrice() > 0) {
                    LR.getVehicleList().add(wp);
                    LR.saveVehicleList();
                    Vehicle[] array = new Vehicle[LR.getVehicleList().size()];
                    int i = 0;
                    for (Vehicle weap : LR.getVehicleList()) {
                        array[i] = weap;
                        i++;
                    }
                    toBuy.setListData(array);
                }
                toBuy.revalidate();
                toBuy.repaint();
            }
        });

        repaint();
        revalidate();
    }



    private ArrayList<Character> loadAllCharacters() {
        ArrayList<Character> chars = new ArrayList<Character>();
        try {
            File path = new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String pathText = "characters";
            if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
                pathText = (path.getAbsolutePath().substring(0, path.getAbsolutePath().length() - 14) + "characters");
            File directory = new File(pathText);
            for (File f : directory.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".char")) {
                        return true;
                    }
                    return false;
                }
            })) {
//TODO build properly//				chars.add(loadCharacterDirectly(f.getAbsolutePath()));
            }
            return chars;
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private Character loadCharacterDirectly(String filepath) {
        ObjectInputStream loadFile;
        try {
            loadFile = new ObjectInputStream(new FileInputStream(filepath));
            Character loaded = (Character) loadFile.readObject();
            loadFile.close();
            return loaded;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    //TODO add filters to only show .char/.pdf!
    public void loadCharacter() {
        int retVal = 0;
        if (characterController.getCharacter() != null) {
            retVal = JOptionPane.showConfirmDialog(this.getContentPane(), new JComponent[]{
                            new JLabel("If you don't save your current character all changes will be forfeit once you load a new one!"),
                            new JLabel("Do you wish to proceed?")}
                    , "Really load new Character?", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        if (retVal == JOptionPane.YES_OPTION) {
            characterController.saveAllowed = true;
            JFileChooser c = new JFileChooser();
            try {
                File path = new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                String pathText = "characters";
                if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
                    pathText = (path.getAbsolutePath().substring(0, path.getAbsolutePath().length() - 14) + "characters");
                c.setCurrentDirectory(new File(pathText));
            } catch (Exception e) {

            }
            retVal = c.showOpenDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                String filepath = c.getSelectedFile().getAbsolutePath();
                if (!filepath.toLowerCase().endsWith(".char")) {
                    JOptionPane.showMessageDialog(this.getContentPane(), "File must be a valid SR5Chargen .char file!", "Failure opening file!", JOptionPane.ERROR_MESSAGE);
                    characterController.saveAllowed = false;
                    return;
                }
                ObjectInputStream loadFile;
                try {
                    loadFile = new ObjectInputStream(new FileInputStream(filepath));
                    characterController.setCharacter( (Character) loadFile.readObject() );
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (characterController.getCharacter() != null) {
                    JOptionPane.showMessageDialog(this.getContentPane(), "Character " + characterController.getCharacter().getPersonalData().getName() + " loaded successfully!");
                    this.getContentPane().removeAll();
                    clearContents();
                    this.getContentPane().add(characterDisplay(false));

                    revalidate();
                    repaint();
                    this.getContentPane().revalidate();
                    this.getContentPane().repaint();
                    this.revalidate();
                    this.repaint();
                }
            } else {

            }
        }
    }

    public void saveCharacter() {
        if (characterController.saveAllowed) {
            JFileChooser c = new JFileChooser();
            try {
                File path = new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                String pathText = "characters";
                if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
                    pathText = (path.getAbsolutePath().substring(0, path.getAbsolutePath().length() - 14) + "characters");
                c.setCurrentDirectory(new File(pathText));
            } catch (Exception e) {

            }
            int retVal = c.showSaveDialog(this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                ObjectOutputStream saveFile = null;
                try {
                    String filepath = c.getSelectedFile().getAbsolutePath();
                    if (!filepath.toLowerCase().endsWith(".char")) {
                        filepath += ".char";
                    }
                    saveFile = new ObjectOutputStream(new FileOutputStream(filepath));
                } catch (Exception e) {
                    System.err.println("Failure opening/creating save file!");
                }
                try {
                    this.characterController.getCharacter().openedChar();
                    saveFile.writeObject(this.characterController.getCharacter());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                try {
                    saveFile.close();
                } catch (IOException e) {

                }
                JOptionPane.showMessageDialog(this.getContentPane(), "Character " + characterController.getCharacter().getPersonalData().getName() + " saved successfully!");
            } else {

            }
        } else {
            JOptionPane.showMessageDialog(this.getContentPane(), "Can't save at this point of character generation, please continue with generation!", "Can't save now", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawRowBox(PDPageContentStream contentStream, float[] fillRect, float[] drawPolygonX, float[] drawPolygonY, float[] drawLine, int rows) throws IOException {
        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.fillRect(fillRect[0], fillRect[1], fillRect[2], fillRect[3]);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.drawPolygon(drawPolygonX, drawPolygonY);
        float y = drawLine[1] - 28;
        contentStream.setStrokingColor(Color.GRAY);
        for (int i = 0; i < rows; i++) {
            contentStream.drawLine(drawLine[0], y, drawLine[2], y);
            y -= 13;
        }
        contentStream.setStrokingColor(Color.BLACK);
    }

    private void drawCyberdeckBox(PDPageContentStream contentStream, float[] fillRect, float[] drawPolygonX, float[] drawPolygonY, float[] drawLine, float curX, int rows) throws IOException {
        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.fillRect(fillRect[0], fillRect[1], fillRect[2], fillRect[3]);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.drawPolygon(drawPolygonX, drawPolygonY);
        float y = drawLine[1] - 28;
        contentStream.setStrokingColor(Color.GRAY);
        for (int i = 0; i < rows; i++) {
            contentStream.drawLine(drawLine[0], y, drawLine[2], y);
            y -= 13;
        }
        float x = curX + 100;
        y -= 2.5;
        for (int i = 1; i < 13; i++) {
            contentStream.drawPolygon(new float[]{x, x + 6.5f, x + 13, x + 6.5f}, new float[]{y + 6.5f, y, y + 6.5f, y + 13});
            x += 15;
        }
        contentStream.setStrokingColor(Color.BLACK);
    }

    private float drawSkillBox(PDPageContentStream contentStream, float pageWidth, float standardBoxWidth, float curX, float curY) throws IOException {
        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.fillRect(pageWidth / 2 - 95, curY - 15, 90, 15);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - 29 * 13, curY - 29 * 13, curY, curY, curY - 10});
        curY -= 33;
        contentStream.setStrokingColor(Color.GRAY);
        for (int i = 0; i < 27; i++) {
            contentStream.drawLine(curX + 5, curY, curX + standardBoxWidth / 2 - 5, curY);
            contentStream.drawLine(curX + standardBoxWidth / 2 + 5, curY, curX + pageWidth / 2 - 20, curY);
            curY -= 13;
        }
        contentStream.setStrokingColor(Color.BLACK);
        return curY;
    }

    private void drawVehicleBox(PDPageContentStream contentStream, float pageWidth, float standardBoxWidth, float curX, float curY) throws IOException {
        contentStream.setNonStrokingColor(Color.GRAY);
        contentStream.fillRect(pageWidth / 2 + 5, curY - 15, 110, 15);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - 8 * 13 - 8, curY - 8 * 13 - 8, curY - 10, curY, curY});
        float y = curY - 28;
        contentStream.setStrokingColor(Color.GRAY);
        contentStream.drawLine(curX + 5, y, curX + pageWidth / 2 - 20, y);
        y -= 13;
        for (int i = 0; i < 4; i++) {
            contentStream.drawLine(curX + 5, y, curX + standardBoxWidth / 2 - 2.5f, y);
            contentStream.drawLine(curX + standardBoxWidth / 2 + 2.5f, y, curX + pageWidth / 2 - 20, y);
            y -= 13;
        }
        for (int i = 0; i < 2; i++) {
            contentStream.drawLine(curX + 5, y, curX + pageWidth / 2 - 20, y);
            y -= 13;
        }
        contentStream.setStrokingColor(Color.BLACK);
    }

    public void printCharacter() {
        if (characterController.saveAllowed && characterController.getCharacter() != null) {
            boolean showNotes = false;
            JFileChooser c = new JFileChooser();
            try {
                File path = new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                String pathText = "characters";
                if (path.getAbsolutePath().endsWith("SR5CharGen.jar"))
                    pathText = (path.getAbsolutePath().substring(0, path.getAbsolutePath().length() - 14) + "characters");
                c.setCurrentDirectory(new File(pathText));
            } catch (Exception e) {

            }
            c.setDialogTitle("Export Character Sheet PDF");
            int retVal = c.showSaveDialog(this);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (retVal == JFileChooser.APPROVE_OPTION) {
                String filepath = c.getSelectedFile().getAbsolutePath();
                if (!filepath.toLowerCase().endsWith(".pdf")) {
                    filepath += ".pdf";
                }
                try {
                    // Create a document and add a page to it
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
                    float pageWidth = PDPage.PAGE_SIZE_A4.getWidth();
                    float pageHeight = PDPage.PAGE_SIZE_A4.getHeight();
                    document.addPage(page);

                    //Additional Box counters
                    int maxSkillRows = 26;//*2 (2 columns!)
                    int maxQualitiyRows = 15;
                    int maxIDRows = 5;
                    int maxContactRows = 7;
                    int maxRweapRows = 8;
                    int maxMweapRows = 8;
                    int maxArmorRows = 8;
                    int maxAugmentRows = 8;
                    int maxSpellRows = 11;
                    int maxPowerRows = 9;
                    int maxGearRows = 27;

                    boolean spaceLeft = true;

                    int SkillRows = 0, QualitiyRows = 0, IDRows = 0, ContactRows = 0, RweapRows = 0, MweapRows = 0, ArmorRows = 0, AugmentRows = 0, SpellRows = 0, PowerRows = 0, GearRows = 0;
                    //int addSkillBoxes=0, addQualitiyBoxes=0, addIDBoxes=0, addContactBoxes=0, addRweapBoxes=0, addMweapBoxes=0, addArmorBoxes=0, addAugmentBoxes=0, addSpellBoxes=0, addPowerBoxes=0, addGearBoxes=0;
                    int lastSkillGroupIndex = -1, lastSkillIndex = -1, lastQualitiyIndex = -1, lastIDIndex = -1, lastContactIndex = -1, lastRweapIndex = -1, lastMweapIndex = -1, lastArmorIndex = -1, lastAugmentIndex = -1, lastSpellIndex = -1, lastPowerIndex = -1, lastGearIndex = -1;

                    // Create a new font object selecting one of the PDF base fonts
                    PDFont font = PDType1Font.COURIER;

                    // Start a new content stream which will "hold" the to be created content
                    PDPageContentStream contentStream = new PDPageContentStream(document, page);

                    // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"

                    float curX = 10;
                    float curY = pageHeight - 22;
                    float startX = curX;
                    float startY = curY;
                    float standardBoxWidth = pageWidth / 2 - 15;

                    curX = startX;
                    curY = startY - 20;
                    //PersData Box
                    contentStream.setNonStrokingColor(Color.GRAY);
                    contentStream.fillRect(pageWidth / 2 - 95, curY - 15, 90, 15);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - 7 * 13, curY - 7 * 13, curY, curY, curY - 10});

                    curX = pageWidth / 2 + 5;
                    //Core Combat Box
                    contentStream.setNonStrokingColor(Color.GRAY);
                    contentStream.fillRect(pageWidth / 2 + 5, curY - 15, 110, 15);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - 7 * 13, curY - 7 * 13, curY - 10, curY, curY});

                    curX = startX;
                    curY = curY - 7 * 13 - 10;
                    //Attributes Box
                    contentStream.setNonStrokingColor(Color.GRAY);
                    contentStream.fillRect(pageWidth / 2 - 95, curY - 15, 90, 15);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - 12 * 13, curY - 12 * 13, curY, curY, curY - 10});

                    curX = pageWidth / 2 + 5;
                    //Condition Monitor Box
                    contentStream.setNonStrokingColor(Color.GRAY);
                    contentStream.fillRect(pageWidth / 2 + 5, curY - 15, 110, 15);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    float trackWidth = standardBoxWidth / 2 - 20;
                    int pRows = 6;//(int)Math.ceil(Math.ceil((8+((float)characterController.getCharacter().getAttributes().getBody())/2))/3);
                    int mRows = 4;//(int)Math.ceil(Math.ceil((8+((float)characterController.getCharacter().getAttributes().getWillpower())/2))/3);
                    int rows = pRows > mRows ? pRows : mRows;
                    contentStream.drawPolygon(new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - trackWidth / 3 * rows - 60, curY - trackWidth / 3 * rows - 60, curY - 10, curY, curY});
                    curY -= 14;
                    rows = pRows;
                    float boxX = curX + 10, boxY = curY - 20;
                    int amount = (int) Math.ceil((8 + ((float) characterController.getCharacter().getAttributes().getBody()) / 2));
                    for (; rows > 0; rows--) {
                        System.out.println(rows);
                        for (int i = 3; i > 0; i--) {

                            contentStream.drawPolygon(new float[]{boxX, boxX + trackWidth / 3, boxX + trackWidth / 3, boxX}, new float[]{boxY, boxY, boxY - trackWidth / 3, boxY - trackWidth / 3});
                            amount--;
                            if (amount < 0) {
                                contentStream.drawLine(boxX + 2, boxY - 2, boxX - 2 + trackWidth / 3, boxY + 2 - trackWidth / 3);
                                contentStream.drawLine(boxX - 2 + trackWidth / 3, boxY - 2, boxX + 2, boxY + 2 - trackWidth / 3);
                            }
                            boxX += trackWidth / 3;
                        }
                        boxX -= trackWidth;
                        boxY -= trackWidth / 3;
                    }
                    rows = mRows;
                    boxX = curX + trackWidth + 20;
                    boxY = curY - 20;
                    amount = (int) Math.ceil((8 + ((float) characterController.getCharacter().getAttributes().getWillpower()) / 2));
                    for (; rows > 0; rows--) {
                        for (int i = 3; i > 0; i--) {
                            contentStream.drawPolygon(new float[]{boxX, boxX + trackWidth / 3, boxX + trackWidth / 3, boxX}, new float[]{boxY, boxY, boxY - trackWidth / 3, boxY - trackWidth / 3});
                            amount--;
                            if (amount < 0) {
                                contentStream.drawLine(boxX + 2, boxY - 2, boxX - 2 + trackWidth / 3, boxY + 2 - trackWidth / 3);
                                contentStream.drawLine(boxX - 2 + trackWidth / 3, boxY - 2, boxX + 2, boxY + 2 - trackWidth / 3);
                            }
                            boxX += trackWidth / 3;
                        }
                        boxX -= trackWidth;
                        boxY -= trackWidth / 3;
                    }
                    rows = pRows > mRows ? pRows : mRows;
                    curY = boxY - trackWidth / 3;
                    curX += trackWidth + 80;
                    contentStream.drawLine(curX, curY, curX + 60, curY);

                    curX = startX;
                    curY += 70;
                    //Skill Box
                    curY = drawSkillBox(contentStream, pageWidth, standardBoxWidth, curX, curY);

                    curX = pageWidth / 2 + 5;
                    curY += 19 * 13 - 11;

                    //Qualities Box
                    drawRowBox(contentStream, new float[]{pageWidth / 2 + 5, curY - 15, 110, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - 17 * 13 - 8, curY - 17 * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, 16);
                    curY -= (28 + 13 * 16);

                    curX = startX;
                    //curY=curY-13*13-8-10;
                    curY -= 5;
                    //IDs Box
                    drawRowBox(contentStream, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - 9 * 13 - 8, curY - 9 * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, 8);

                    curX = pageWidth / 2 + 5;
                    //Contacts Box
                    drawRowBox(contentStream, new float[]{pageWidth / 2 + 5, curY - 15, 110, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - 9 * 13 - 8, curY - 9 * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, 8);

                    curX = startX;
                    curY = startY;

                    contentStream.beginText();
                    //Title
                    contentStream.setFont(font, 12);
                    String title = "SR5 CharGen Character Sheet";
                    contentStream.moveTextPositionByAmount(curX + (pageWidth / 2) - (font.getStringWidth(title) / 1000 * 12) / 2, curY + 3);
                    contentStream.drawString(title);
                    contentStream.moveTextPositionByAmount(+(font.getStringWidth(title) / 1000 * 12) / 2, -14);
                    contentStream.setFont(font, 14);
                    title = characterController.getCharacter().getPersonalData().getName();
                    contentStream.moveTextPositionByAmount(-(font.getStringWidth(title) / 1000 * 14) / 2, 0);
                    contentStream.drawString(title);


                    //Body
                    contentStream.moveTextPositionByAmount(-(pageWidth / 2) + (font.getStringWidth(title) / 1000 * 14) / 2, -19);
                    contentStream.setFont(font, 10);

                    //Personal Data Text
                    contentStream.moveTextPositionByAmount(195, 0);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Personal Data");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(/*90-pageWidth/2*/-193, -14);
                    contentStream.drawString("Name/Primary Alias: " + characterController.getCharacter().getPersonalData().getName());
                    contentStream.moveTextPositionByAmount(0, -12);
                    contentStream.drawString("Metatype: " + characterController.getCharacter().getPersonalData().getMetatype());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 2, 0);
                    contentStream.drawString("Ethnicity: " + characterController.getCharacter().getPersonalData().getEthnicity());
                    contentStream.moveTextPositionByAmount(-standardBoxWidth / 2, -12);
                    contentStream.drawString("Age: " + characterController.getCharacter().getPersonalData().getAge());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    contentStream.drawString("Sex: " + characterController.getCharacter().getPersonalData().getSex());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    contentStream.drawString("Height: " + characterController.getCharacter().getPersonalData().getHeight());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    contentStream.drawString("Weight: " + characterController.getCharacter().getPersonalData().getWeight());
                    contentStream.moveTextPositionByAmount(-standardBoxWidth * 3 / 4, -12);
                    contentStream.drawString("StreetCred: " + characterController.getCharacter().getPersonalData().getStreetCred());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 3, 0);
                    contentStream.drawString("Notoriety: " + characterController.getCharacter().getPersonalData().getNotoriety());
                    contentStream.moveTextPositionByAmount(-standardBoxWidth / 3, -12);
                    contentStream.drawString("Public Awareness: " + characterController.getCharacter().getPersonalData().getPublicAwareness());
                    contentStream.moveTextPositionByAmount(0, -12);
                    contentStream.drawString("Karma: " + characterController.getCharacter().getPersonalData().getKarma());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 3, 0);
                    contentStream.drawString("Total Karma: " + characterController.getCharacter().getPersonalData().getTotalKarma());


                    //CoreCombatInfo Text
                    contentStream.moveTextPositionByAmount((-standardBoxWidth * 1 / 3) + pageWidth / 2, +12 * 5 + 14);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Core Combat Info");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(-5, -14);
                    String text = !characterController.getCharacter().getArmor().isEmpty() ? characterController.getCharacter().getArmor().get(0).getName() : " ";
                    contentStream.drawString("Primary Armor: " + text);
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = !characterController.getCharacter().getArmor().isEmpty() ? "" + characterController.getCharacter().getArmor().get(0).getRating() : " ";
                    contentStream.drawString("Rating: " + text);
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? characterController.getCharacter().getRangedWeapons().get(0).getName() : " ";
                    contentStream.drawString("Primary Ranged Weapon: " + text);
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "Dam " + characterController.getCharacter().getRangedWeapons().get(0).getRealDamage() : "Dam ";
                    contentStream.drawString(text);
                    float length = 0;
                    length += font.getStringWidth(text) / 100 + 6;
                    contentStream.moveTextPositionByAmount(font.getStringWidth(text) / 100 + 6, 0);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "Acc " + characterController.getCharacter().getRangedWeapons().get(0).getRealAccuracy() : "Acc ";
                    contentStream.drawString(text);
                    length += font.getStringWidth(text) / 100 + 6;
                    contentStream.moveTextPositionByAmount(font.getStringWidth(text) / 100 + 6, 0);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "AP " + characterController.getCharacter().getRangedWeapons().get(0).getRealArmorPiercing() : "AP ";
                    contentStream.drawString(text);
                    length += font.getStringWidth(text) / 100 + 6;
                    contentStream.moveTextPositionByAmount(font.getStringWidth(text) / 100 + 6, 0);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "Mode " + characterController.getCharacter().getRangedWeapons().get(0).getModeShorthandString() : "Mode ";
                    contentStream.drawString(text);
                    length += font.getStringWidth(text) / 100 + 6;
                    contentStream.moveTextPositionByAmount(font.getStringWidth(text) / 100 + 6, 0);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "RC " + characterController.getCharacter().getRangedWeapons().get(0).getRealRecoil() : "RC ";
                    contentStream.drawString(text);
                    length += font.getStringWidth(text) / 100 + 6;
                    contentStream.moveTextPositionByAmount(font.getStringWidth(text) / 100 + 6, 0);
                    text = !characterController.getCharacter().getRangedWeapons().isEmpty() ? "Ammo " + characterController.getCharacter().getRangedWeapons().get(0).getRealAmmo() : "Ammo ";
                    contentStream.drawString(text);
                    contentStream.moveTextPositionByAmount(-length, -12);
                    text = !characterController.getCharacter().getMeeleWeapons().isEmpty() ? characterController.getCharacter().getMeeleWeapons().get(0).getName() : " ";
                    contentStream.drawString("Primary Melee Weapon: " + text);
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = !characterController.getCharacter().getMeeleWeapons().isEmpty() ? "" + characterController.getCharacter().getMeeleWeapons().get(0).getRealReach() : " ";
                    contentStream.drawString("Reach: " + text);
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    text = !characterController.getCharacter().getMeeleWeapons().isEmpty() ? "" + characterController.getCharacter().getMeeleWeapons().get(0).getRealDamage() : " ";
                    contentStream.drawString("Dam: " + text);
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    text = !characterController.getCharacter().getMeeleWeapons().isEmpty() ? "" + characterController.getCharacter().getMeeleWeapons().get(0).getRealAccuracy() : " ";
                    contentStream.drawString("Acc: " + text);
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 4, 0);
                    text = !characterController.getCharacter().getMeeleWeapons().isEmpty() ? "" + characterController.getCharacter().getMeeleWeapons().get(0).getAP() : " ";
                    contentStream.drawString("AP: " + text);

                    //Attributes Text
                    contentStream.moveTextPositionByAmount(-(standardBoxWidth * 3 / 4 + pageWidth / 2 - 217), -27);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Attributes");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(-160, -14);
                    text = "Body " + characterController.getCharacter().getAttributes().getBody();
                    float endPos = font.getStringWidth(text) / 100;
                    int startPos = 65;
                    contentStream.drawString(text);
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Agility " + characterController.getCharacter().getAttributes().getAgility());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Reaction " + characterController.getCharacter().getAttributes().getReaction());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Strength " + characterController.getCharacter().getAttributes().getStrength());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Willpower " + characterController.getCharacter().getAttributes().getWillpower());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Logic " + characterController.getCharacter().getAttributes().getLogic());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Intuition " + characterController.getCharacter().getAttributes().getIntuition());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Charisma " + characterController.getCharacter().getAttributes().getCharisma());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Edge " + characterController.getCharacter().getAttributes().getEdge());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(endPos + 80, 12 * 8);
                    text = ("Essence " + (int) characterController.getCharacter().getAttributes().getEssence());
                    endPos = font.getStringWidth(text) / 100;
                    text = ("Essence " + characterController.getCharacter().getAttributes().getEssence());
                    contentStream.drawString(text);

                    if (!characterController.getCharacter().getMagicalness().equals(Magical.Mundane)) {
                        contentStream.moveTextPositionByAmount(0, -12);
                        if (characterController.getCharacter().getMagicalness().equals(Magical.Technomancer)) {
                            text = ("Resonance " + characterController.getCharacter().getAttributes().getResonance());
                        } else {
                            text = ("Magic " + characterController.getCharacter().getAttributes().getMagic());
                        }
                        if (startPos + font.getStringWidth(text) / 100 > endPos) {
                            contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                        } else {
                            contentStream.drawString(text);
                        }
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Initiative " + characterController.getCharacter().getAttributes().getInitiative());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text + "+" + characterController.getCharacter().getAttributes().getIniDice() + "D6");
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Matrix Initiative " + characterController.getCharacter().getAttributes().getMatrixInitiative());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text + "+" + characterController.getCharacter().getAttributes().getIniDice() + "D6");
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Astral Initiative " + characterController.getCharacter().getAttributes().getAstralInitiative());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text + "+" + characterController.getCharacter().getAttributes().getIniDice() + "D6");
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Composure " + characterController.getCharacter().getAttributes().getComposure());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Judge Intentions " + characterController.getCharacter().getAttributes().getJudgeIntentions());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Memory " + characterController.getCharacter().getAttributes().getMemory());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Lift/Carry " + characterController.getCharacter().getAttributes().getLiftCarry());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(-(endPos - (font.getStringWidth(text) / 100)), 0);
                    } else {
                        contentStream.drawString(text);
                    }
                    contentStream.moveTextPositionByAmount(0, -12);
                    text = ("Movement " + characterController.getCharacter().getAttributes().getWalk());
                    if (startPos + font.getStringWidth(text) / 100 > endPos) {
                        contentStream.moveTextPositionByAmount(endPos - (font.getStringWidth(text) / 100), 0);
                        text = ("Movement " + characterController.getCharacter().getAttributes().getMovement());
                        contentStream.drawString(text);
                        text = ("Movement " + characterController.getCharacter().getAttributes().getWalk());
                        contentStream.moveTextPositionByAmount(-endPos + (font.getStringWidth(text) / 100), 0);
                    } else {
                        text = ("Movement " + characterController.getCharacter().getAttributes().getMovement());
                        contentStream.drawString(text);
                    }
                    if (characterController.getCharacter().getMagicalness().equals(Magical.Mundane)) {
                        contentStream.moveTextPositionByAmount(0, -12);
                    }
                    contentStream.moveTextPositionByAmount(-(25 + standardBoxWidth / 2), -14);
                    contentStream.drawString("Physical Limit " + characterController.getCharacter().getAttributes().getPhysicalLimit());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 3 + 7, 0);
                    contentStream.drawString("Mental Limit " + characterController.getCharacter().getAttributes().getMentalLimit());
                    contentStream.moveTextPositionByAmount(standardBoxWidth / 3 - 3, 0);
                    contentStream.drawString("Social Limit " + characterController.getCharacter().getAttributes().getSocialLimit());

                    //Condition Monitor
                    contentStream.moveTextPositionByAmount(105, +12 * 10 + 14);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Condition Monitor");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(0, -14);
                    contentStream.drawString("Physical Damage Track");
                    contentStream.moveTextPositionByAmount(trackWidth + 15, 0);
                    contentStream.drawString("Stun Damage Track");
                    contentStream.moveTextPositionByAmount(-trackWidth * 4 / 3 + 12, -trackWidth / 3 - 2);
                    int pos = characterController.getCharacter().getAdeptPowers().contains(new Power("Pain Resistance", 0, 0)) ? 3 + characterController.getCharacter().getAdeptPowers().get(characterController.getCharacter().getAdeptPowers().indexOf(new Power("Pain Resistance", 0, 0))).getLevel() : 3;
                    int num = 1;
                    for (int i = 1; i <= 6 * 3/*(int)Math.ceil((8+((float)characterController.getCharacter().getAttributes().getBody())/2))*/; i++) {
                        contentStream.moveTextPositionByAmount(trackWidth / 3, 0);
                        if (i % pos == 0) {
                            contentStream.drawString("-" + num);
                            num++;
                        }
                        if (i % 3 == 0) {
                            contentStream.moveTextPositionByAmount(-trackWidth, -trackWidth / 3);
                        }
                    }
                    contentStream.moveTextPositionByAmount(trackWidth + 10, +trackWidth / 3 * (6));
                    pos = 3;
                    num = 1;
                    for (int i = 1; i <= 4 * 3/*(int)Math.ceil((8+((float)characterController.getCharacter().getAttributes().getWillpower())/2))*/; i++) {
                        contentStream.moveTextPositionByAmount(trackWidth / 3, 0);
                        if (i % pos == 0) {
                            contentStream.drawString("-" + num);
                            num++;
                        }
                        if (i % 3 == 0) {
                            contentStream.moveTextPositionByAmount(-trackWidth, -trackWidth / 3);
                        }
                    }
                    contentStream.setFont(font, 9);
                    contentStream.moveTextPositionByAmount(20, -6);
                    contentStream.drawString("Overflow");

                    //Skill Text
                    contentStream.moveTextPositionByAmount(-pageWidth / 2 + 100, 59);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Skills");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(-235, -18);
                    contentStream.drawString("Skill");
                    contentStream.moveTextPositionByAmount(90, 0);
                    contentStream.drawString("RTG");
                    contentStream.moveTextPositionByAmount(20, 0);
                    contentStream.drawString("Type");
                    contentStream.moveTextPositionByAmount(30, 0);
                    contentStream.drawString("Skill");
                    contentStream.moveTextPositionByAmount(90, 0);
                    contentStream.drawString("RTG");
                    contentStream.moveTextPositionByAmount(20, 0);
                    contentStream.drawString("Type");
                    contentStream.setFont(font, 8);
                    contentStream.moveTextPositionByAmount(-(110 + 140), -13);
                    int Scount = 0;
                    int Scols = 1;
                    for (SkillGroup sg : characterController.getCharacter().getSkillGroups()) {
                        if (!spaceLeft) {
                            break;
                        }
                        if (Scount > 25 && Scols < 2) {
                            contentStream.moveTextPositionByAmount(140, 13 * 26);
                            Scount = 0;
                            SkillRows = 0;
                            Scols++;
                        }
                        contentStream.drawString(sg.getName());
                        if (font.getStringWidth(sg.getName()) / 100 > 110) {
                            contentStream.moveTextPositionByAmount(0, -13);
                            Scount++;
                            SkillRows++;
                            if (Scount > 25 && Scols < 2) {
                                contentStream.moveTextPositionByAmount(140, 13 * 26);
                                Scount = 0;
                                Scols++;
                                SkillRows = 0;
                            }
                        }
                        contentStream.moveTextPositionByAmount(95, 0);
                        contentStream.drawString("" + sg.getValue());
                        contentStream.moveTextPositionByAmount(25, 0);
                        contentStream.drawString("SG");
                        Scount++;

                        SkillRows++;
                        if (SkillRows >= maxSkillRows && Scols > 1) {
                            lastSkillGroupIndex = characterController.getCharacter().getSkillGroups().indexOf(sg) + 1;
                            spaceLeft = false;
                        }

                        for (Skill s : sg.getSkills()) {
                            if (!spaceLeft) {
                                break;
                            }
                            if (Scount > 25 && Scols < 2) {
                                contentStream.moveTextPositionByAmount(140, 13 * 26);
                                Scount = 0;
                                Scols++;
                                SkillRows = 0;
                            }
                            contentStream.moveTextPositionByAmount(-115, -13);
                            contentStream.drawString(s.getName());
                            if (font.getStringWidth(s.getName()) / 100 > 110) {
                                contentStream.moveTextPositionByAmount(0, -13);
                                Scount++;
                                SkillRows++;
                                if (Scount > 25 && Scols < 2) {
                                    contentStream.moveTextPositionByAmount(140, 13 * 26);
                                    Scount = 0;
                                    Scols++;
                                    SkillRows = 0;
                                }
                            }
                            contentStream.moveTextPositionByAmount(90, 0);
                            contentStream.drawString("" + sg.getValue() + s.getAttributeShorthand());
                            contentStream.moveTextPositionByAmount(30, 0);
                            contentStream.drawString("A");
                            contentStream.moveTextPositionByAmount(-5, 0);
                            Scount++;
                            SkillRows++;
                            if (SkillRows >= maxSkillRows && Scols > 1) {
                                lastSkillGroupIndex = characterController.getCharacter().getSkillGroups().indexOf(sg) + 1;
                                spaceLeft = false;
                            }
                        }
                        contentStream.moveTextPositionByAmount(-120, -13);
                    }
                    for (Skill s : characterController.getCharacter().getSkills()) {
                        if (!spaceLeft) {
                            break;
                        }
                        if (Scount > 25 && Scols < 2) {
                            contentStream.moveTextPositionByAmount(140, 13 * 26);
                            Scount = 0;
                            Scols++;
                            SkillRows = 0;
                        }
                        contentStream.drawString(s.getName());
                        if (font.getStringWidth(s.getName()) / 100 > 110) {
                            contentStream.moveTextPositionByAmount(0, -13);
                            Scount++;
                            SkillRows++;
                            if (Scount > 25 && Scols < 2) {
                                contentStream.moveTextPositionByAmount(140, 13 * 26);
                                Scount = 0;
                                Scols++;
                                SkillRows = 0;
                            }
                        }
                        contentStream.moveTextPositionByAmount(95, 0);
                        contentStream.drawString("" + s.getValue() + s.getAttributeShorthand());
                        contentStream.moveTextPositionByAmount(30, 0);
                        String type = s.isKnowledge() ? "K" : "A";
                        contentStream.drawString(type);
                        Scount++;
                        SkillRows++;
                        contentStream.moveTextPositionByAmount(-125, -13);
                        if (SkillRows >= maxSkillRows && Scols > 1) {
                            lastSkillIndex = characterController.getCharacter().getSkills().indexOf(s) + 1;
                            spaceLeft = false;
                        }
                    }
                    for (; Scount < 26; Scount++) {
                        contentStream.moveTextPositionByAmount(0, -13);
                    }
                    if (Scols < 2) {
                        contentStream.moveTextPositionByAmount(140, 0);
                    }
                    contentStream.setFont(font, 10);


                    //Qualities
                    spaceLeft = true;
                    contentStream.moveTextPositionByAmount(155, (-12 + 13 * 18));
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Qualities");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(-2, -15);
                    contentStream.setFont(font, 9);
                    contentStream.drawString("Quality");
                    contentStream.moveTextPositionByAmount(standardBoxWidth - 35, 0);
                    contentStream.drawString("Type");
                    contentStream.setFont(font, 8);
                    contentStream.moveTextPositionByAmount(-standardBoxWidth + 35, -13);
                    int Qcount = 0;
                    showNotes = (JOptionPane.showConfirmDialog(this.getContentPane(), "Do you wish to show notes for your qualities?", "Show Notes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) ? true : false;
                    for (Quality q : characterController.getCharacter().getQualities()) {
                        if (showNotes) {
                            text = q.isLeveled() ? q.getName() + " " + q.getLevel() + " " + q.getNotes() : q.getName() + " " + q.getNotes();
                        } else {
                            text = q.isLeveled() ? q.getName() + " " + q.getLevel() : q.getName();
                        }
                        if (Qcount < 15 && ((font.getStringWidth(text) / 1000 * 8) < standardBoxWidth - 35) || !showNotes) {
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(standardBoxWidth - 30, 0);
                            text = q.isPositive() ? "P" : "N";
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(-standardBoxWidth + 30, -13);
                            Qcount++;
                        } else if (Qcount < 15) {
                            text = q.isLeveled() ? q.getName() + " " + q.getLevel() : q.getName();
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(standardBoxWidth - 30, 0);
                            text = q.isPositive() ? "P" : "N";
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(-standardBoxWidth + 35, -13);
                            Qcount++;
                            text = q.getNotes();
                            if ((font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 10) {
                                String part = q.getNotes();
                                length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                                System.out.println(length);
                                int start = 0;
                                int end = (int) length;

                                while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                    part = text.substring(end, text.length());
                                    contentStream.drawString(text.substring(start, end));
                                    start += (int) length;
                                    end += (int) length;
                                    if (end > text.length()) {
                                        end = text.length();
                                    }
                                    contentStream.moveTextPositionByAmount(0, -13);
                                    Qcount++;
                                }
                                contentStream.drawString(part);

                            } else {
                                contentStream.drawString(text);
                            }
                            contentStream.moveTextPositionByAmount(-5, -13);
                            Qcount++;
                        } else {
                            lastQualitiyIndex = characterController.getCharacter().getQualities().indexOf(q) + 1;
                            break;
                        }

                    }
                    for (; Qcount < 15; Qcount++) {
                        contentStream.moveTextPositionByAmount(0, -13);
                    }
                    contentStream.setFont(font, 10);

                    //IDs&Co
                    contentStream.moveTextPositionByAmount(-190, -19);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("IDs | Lifestyles | Currency");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.moveTextPositionByAmount(+190 - standardBoxWidth - 10, -14);
                    contentStream.setFont(font, 9);
                    contentStream.drawString("Primary Lifestyle: " + characterController.getCharacter().getPersonalData().getLifestyle().getName());
                    contentStream.moveTextPositionByAmount(0, -13);
                    contentStream.drawString("Nuyen: " + characterController.getCharacter().getMoney());
                    contentStream.moveTextPositionByAmount(0, -13);
                    contentStream.drawString("Fake IDs/Licenses:");
                    contentStream.moveTextPositionByAmount(0, -13);
                    text = "";
                    int IDCount = 0;
                    for (ID id : characterController.getCharacter().getFakeIDs()) {
                        String addition = id.getName() + "(" + id.getRating() + "),";
                        if (IDCount < 4 && font.getStringWidth(text + addition) / 1000 * 9 > standardBoxWidth - 5) {
                            text = text.substring(0, text.length() - 1);
                            contentStream.drawString(text);
                            contentStream.moveTextPositionByAmount(0, -13);
                            IDCount++;
                            text = addition;
                        } else if (IDCount < 4) {
                            text += addition;
                        } else {
                            lastIDIndex = characterController.getCharacter().getFakeIDs().indexOf(id) + 1;
                            break;
                        }
                    }
                    if (IDCount < 4 && !text.isEmpty()) {
                        text = text.substring(0, text.length() - 1);
                        contentStream.drawString(text);
                        contentStream.moveTextPositionByAmount(0, -13);
                        IDCount++;
                    }
                    if (IDCount < 4) {
                        contentStream.moveTextPositionByAmount(0, -13 * (4 - IDCount));
                    }

                    //Contacts
                    contentStream.setFont(font, 10);
                    contentStream.moveTextPositionByAmount(standardBoxWidth + 12, +1 + 13 * 8);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.drawString("Contacts");
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.setFont(font, 9);
                    contentStream.moveTextPositionByAmount(-3, -13);
                    contentStream.drawString("Name");
                    contentStream.moveTextPositionByAmount(115, 0);
                    contentStream.drawString("Loyalty");
                    contentStream.moveTextPositionByAmount(55, 0);
                    contentStream.drawString("Connection");
                    contentStream.moveTextPositionByAmount(70, 0);
                    contentStream.drawString("Favor");
                    contentStream.moveTextPositionByAmount(-240, -13);
                    int Ccount = 0;
                    for (Contact co : characterController.getCharacter().getContacts()) {
                        if (Ccount < 7) {
                            contentStream.drawString(co.getName());
                            contentStream.moveTextPositionByAmount(130, 0);
                            contentStream.drawString("" + co.getLoyalty());
                            contentStream.moveTextPositionByAmount(70, 0);
                            contentStream.drawString("" + co.getConnection());
                            contentStream.moveTextPositionByAmount(55, 0);
                            contentStream.drawString("" + co.getFavors());
                            contentStream.moveTextPositionByAmount(-255, -13);
                            Ccount++;
                        } else {
                            lastContactIndex = characterController.getCharacter().getContacts().indexOf(co) + 1;
                        }
                    }
                    if (Ccount < 7) {
                        contentStream.moveTextPositionByAmount(0, -13 * (7 - Ccount));
                    }
                    contentStream.moveTextPositionByAmount(-pageWidth / 2, -10);
                    contentStream.drawString("(c)2014 Nicolá Michel Henry Riedmann");
                    contentStream.endText();
                    // Make sure that the content stream is closed:
                    contentStream.close();

                    //Page 2
                    PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
                    document.addPage(page2);
                    PDPageContentStream contentStream2 = new PDPageContentStream(document, page2);

                    //BOXES

                    curX = 10;
                    curY = pageHeight - 42;

                    //Ranged Weap Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 - 100, curY - 15, 95, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX},
                            new float[]{curY - 10 * 13 - 8, curY - 10 * 13 - 8, curY, curY, curY - 10},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            9
                    );

                    curX = pageWidth / 2 + 5;
                    //Melee Weap Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 + 5, curY - 15, 110, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX},
                            new float[]{curY - 10 * 13 - 8, curY - 10 * 13 - 8, curY - 10, curY, curY},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            9
                    );

                    curX -= (pageWidth / 2 - 5);
                    curY -= (10 * 13 + 19);
                    //Armor Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 - 100, curY - 15, 95, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX},
                            new float[]{curY - 10 * 13 - 8, curY - 10 * 13 - 8, curY, curY, curY - 10},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            9
                    );

                    curX = pageWidth / 2 + 5;
                    //Cyberdeck Box
                    drawCyberdeckBox(
                            contentStream2,
                            new float[]{pageWidth / 2 + 5, curY - 15, 110, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX},
                            new float[]{curY - 8 * 13 - 8, curY - 8 * 13 - 8, curY - 10, curY, curY},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            curX,
                            6
                    );

                    curX -= (pageWidth / 2 - 5);
                    curY -= (10 * 13 + 19);
                    //Augment Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 - 100, curY - 15, 95, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX},
                            new float[]{curY - 10 * 13 - 8, curY - 10 * 13 - 8, curY, curY, curY - 10},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            9
                    );

//				contentStream2.setNonStrokingColor(Color.GRAY);
//				contentStream2.fillRect(pageWidth/2-100, curY-15, 95, 15);
//				contentStream2.setNonStrokingColor(Color.BLACK);
//				contentStream2.drawPolygon(new float[]{curX,curX+pageWidth/2-15,curX+pageWidth/2-15,curX+20,curX}, new float[]{curY-10*13-8,curY-10*13-8,curY,curY,curY-10});
//				y=curY-28;
//				contentStream2.setStrokingColor(Color.GRAY);
//				for (int i=0;i<9;i++){
//					contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
//				contentStream2.setStrokingColor(Color.BLACK);

                    curX = pageWidth / 2 + 5;
                    curY += (2 * 13);
                    //Vehicle Box
                    drawVehicleBox(contentStream2, pageWidth, standardBoxWidth, curX, curY);

//				contentStream2.setNonStrokingColor(Color.GRAY);
//				contentStream2.fillRect(pageWidth/2+5, curY-15, 110, 15);
//				contentStream2.setNonStrokingColor(Color.BLACK);
//				contentStream2.drawPolygon(new float[]{curX,curX+pageWidth/2-15,curX+pageWidth/2-15,curX+pageWidth/2-40,curX}, new float[]{curY-8*13-8,curY-8*13-8,curY-10,curY,curY});
//				y=curY-28;
//				contentStream2.setStrokingColor(Color.GRAY);
//				contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//				y-=13;
//				for (int i=0;i<4;i++){
//					contentStream2.drawLine(curX+5,y, curX+standardBoxWidth/2-2.5f, y);
//					contentStream2.drawLine(curX+standardBoxWidth/2+2.5f,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
//				for (int i=0;i<2;i++){
//					contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
                    contentStream2.setStrokingColor(Color.BLACK);

                    curX -= (pageWidth / 2 - 5);
                    curY -= (12 * 13 + 19);
                    //Spells/Preps/Rituals/Compl Forms Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 - 145, curY - 15, 140, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX},
                            new float[]{curY - 13 * 13 - 8, curY - 13 * 13 - 8, curY, curY, curY - 10},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            12
                    );

//				contentStream2.setNonStrokingColor(Color.GRAY);
//				contentStream2.fillRect(pageWidth/2-145, curY-15, 140, 15);
//				contentStream2.setNonStrokingColor(Color.BLACK);
//				contentStream2.drawPolygon(new float[]{curX,curX+pageWidth/2-15,curX+pageWidth/2-15,curX+20,curX}, new float[]{curY-13*13-8,curY-13*13-8,curY,curY,curY-10});
//				y=curY-28;
//				contentStream2.setStrokingColor(Color.GRAY);
//				for (int i=0;i<12;i++){
//					contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
//				contentStream2.setStrokingColor(Color.BLACK);

                    curY -= (13 * 13 + 19);
                    //Powers Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 - 100, curY - 15, 95, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX},
                            new float[]{curY - 11 * 13 - 8, curY - 11 * 13 - 8, curY, curY, curY - 10},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            10
                    );

//				contentStream2.setNonStrokingColor(Color.GRAY);
//				contentStream2.fillRect(pageWidth/2-100, curY-15, 95, 15);
//				contentStream2.setNonStrokingColor(Color.BLACK);
//				contentStream2.drawPolygon(new float[]{curX,curX+pageWidth/2-15,curX+pageWidth/2-15,curX+20,curX}, new float[]{curY-11*13-8,curY-11*13-8,curY,curY,curY-10});
//				y=curY-28;
//				contentStream2.setStrokingColor(Color.GRAY);
//				for (int i=0;i<10;i++){
//					contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
//				contentStream2.setStrokingColor(Color.BLACK);

                    curX = pageWidth / 2 + 5;
                    curY += (18 * 13);
                    //Gear Box
                    drawRowBox(
                            contentStream2,
                            new float[]{pageWidth / 2 + 5, curY - 15, 110, 15},
                            new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX},
                            new float[]{curY - 29 * 13 - 8, curY - 29 * 13 - 8, curY - 10, curY, curY},
                            new float[]{curX + 5, curY, curX + pageWidth / 2 - 20},
                            28
                    );
//
//				contentStream2.setNonStrokingColor(Color.GRAY);
//				contentStream2.fillRect(pageWidth/2+5, curY-15, 110, 15);
//				contentStream2.setNonStrokingColor(Color.BLACK);
//				contentStream2.drawPolygon(new float[]{curX,curX+pageWidth/2-15,curX+pageWidth/2-15,curX+pageWidth/2-40,curX}, new float[]{curY-29*13-8,curY-29*13-8,curY-10,curY,curY});
//				y=curY-28;
//				contentStream2.setStrokingColor(Color.GRAY);
//				for (int i=0;i<28;i++){
//					contentStream2.drawLine(curX+5,y, curX+pageWidth/2-20, y);
//					y-=13;
//				}
//				contentStream2.setStrokingColor(Color.BLACK);


                    //TEXT
                    curX = startX;
                    curY = startY;

                    contentStream2.beginText();

                    //Title
                    contentStream2.setFont(font, 12);
                    title = "SR5 CharGen Character Sheet";
                    contentStream2.moveTextPositionByAmount(curX + (pageWidth / 2) - (font.getStringWidth(title) / 1000 * 12) / 2, curY + 3);
                    contentStream2.drawString(title);
                    contentStream2.moveTextPositionByAmount(+(font.getStringWidth(title) / 1000 * 12) / 2, -14);
                    contentStream2.setFont(font, 14);
                    title = characterController.getCharacter().getPersonalData().getName();
                    contentStream2.moveTextPositionByAmount(-(font.getStringWidth(title) / 1000 * 14) / 2, 0);
                    contentStream2.drawString(title);


                    //Body
                    contentStream2.moveTextPositionByAmount(-(pageWidth / 2) + (font.getStringWidth(title) / 1000 * 14) / 2, -19);
                    contentStream2.setFont(font, 10);

                    //Ranged Weap Text
                    contentStream2.moveTextPositionByAmount(195, 0);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Ranged Weapons");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-188, -14);
                    contentStream2.drawString("Weapon");
                    contentStream2.moveTextPositionByAmount(68, 0);
                    contentStream2.drawString("Dam");
                    contentStream2.moveTextPositionByAmount(30, 0);
                    contentStream2.drawString("Acc");
                    contentStream2.moveTextPositionByAmount(30, 0);
                    contentStream2.drawString("AP");
                    contentStream2.moveTextPositionByAmount(25, 0);
                    contentStream2.drawString("Mode");
                    contentStream2.moveTextPositionByAmount(60, 0);
                    contentStream2.drawString("RC");
                    contentStream2.moveTextPositionByAmount(30, 0);
                    contentStream2.drawString("Ammo");
                    contentStream2.moveTextPositionByAmount(-243, -13);
                    int Rcount = 1;
                    for (RangedWeapon wp : characterController.getCharacter().getRangedWeapons()) {
                        contentStream2.setFont(font, 8);
                        contentStream2.drawString(wp.getName());
                        if ((font.getStringWidth(wp.getName()) / 1000 * 8) > 68) {
                            contentStream2.moveTextPositionByAmount(0, -13);
                            Rcount++;
                        }
                        contentStream2.setFont(font, 9);
                        contentStream2.moveTextPositionByAmount(68, 0);
                        text = wp.getDamage() != wp.getRealDamage() ? wp.getDamage() + "(" + wp.getRealDamage() + ")" : "" + wp.getDamage();
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(30, 0);
                        text = wp.getAccuracy() != wp.getRealAccuracy() ? wp.getAccuracy() + "(" + wp.getRealAccuracy() + ")" : "" + wp.getAccuracy();
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(30, 0);
                        text = wp.getArmorPiercing() != wp.getRealArmorPiercing() ? wp.getArmorPiercing() + "(" + wp.getRealArmorPiercing() + ")" : "" + wp.getArmorPiercing();
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(25, 0);
                        contentStream2.drawString(wp.getModeShorthandString());
                        contentStream2.moveTextPositionByAmount(60, 0);
                        text = wp.getRecoil() != wp.getRealRecoil() ? wp.getRecoil() + "(" + wp.getRealRecoil() + ")" : "" + wp.getRecoil();
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(30, 0);
                        contentStream2.drawString(wp.getRealAmmo() + "[" + wp.getAmmoTypeShorthand() + "]");
                        contentStream2.moveTextPositionByAmount(-243, -13);
                        Rcount++;
                    }
                    if (Rcount < 8) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (8 - Rcount));
                    }

                    //Melee Weap Text
                    contentStream2.setFont(font, 10);

                    contentStream2.moveTextPositionByAmount(pageWidth / 2, 13 * 9);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Melee Weapons");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-2, -14);
                    contentStream2.drawString("Weapon");
                    contentStream2.moveTextPositionByAmount(138, 0);
                    contentStream2.drawString("Reach");
                    contentStream2.moveTextPositionByAmount(38, 0);
                    contentStream2.drawString("Dam");
                    contentStream2.moveTextPositionByAmount(27, 0);
                    contentStream2.drawString("Acc");
                    contentStream2.moveTextPositionByAmount(35, 0);
                    contentStream2.drawString("AP");
                    contentStream2.moveTextPositionByAmount(-238, -13);
                    int Mcount = 0;
                    for (MeeleWeapon wp : characterController.getCharacter().getMeeleWeapons()) {
                        contentStream2.setFont(font, 8);
                        contentStream2.drawString(wp.getName());
                        if ((font.getStringWidth(wp.getName()) / 1000 * 8) > 148) {
                            contentStream2.moveTextPositionByAmount(0, -13);
                            Mcount++;
                        }
                        contentStream2.setFont(font, 9);
                        contentStream2.moveTextPositionByAmount(148, 0);
                        contentStream2.drawString("" + wp.getReach());
                        contentStream2.moveTextPositionByAmount(30, 0);
                        text = wp.isDependsOnStrength() ? "" + (wp.getDamage() + characterController.getCharacter().getAttributes().getStrength()) : "" + wp.getDamage();
                        String dam = wp.isDoesStunDamage() ? "S" : "P";
                        contentStream2.drawString(text + dam);
                        contentStream2.moveTextPositionByAmount(30, 0);
                        contentStream2.drawString("" + wp.getAccuracy());
                        contentStream2.moveTextPositionByAmount(30, 0);
                        contentStream2.drawString("" + wp.getAP());
                        contentStream2.moveTextPositionByAmount(-238, -13);
                        Mcount++;
                    }
                    if (Mcount < 8) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (8 - Mcount));
                    }

                    //armor text
                    contentStream2.moveTextPositionByAmount(-pageWidth / 2 + standardBoxWidth - 40, -16);
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Armor");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-standardBoxWidth + 42, -15);
                    contentStream2.drawString("Armor");
                    contentStream2.moveTextPositionByAmount(150, 0);
                    contentStream2.drawString("Rating");
                    contentStream2.moveTextPositionByAmount(40, 0);
                    contentStream2.drawString("Notes");
                    contentStream2.moveTextPositionByAmount(-190, -13);
                    int Acount = 0;
                    showNotes = (JOptionPane.showConfirmDialog(this.getContentPane(), "Do you wish to show notes for your Armor?", "Show Notes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) ? true : false;
                    for (Armor a : characterController.getCharacter().getArmor()) {
                        contentStream2.setFont(font, 8);
                        //contentStream2.drawString(a.getName());
                        if ((font.getStringWidth(a.getName()) / 1000 * 8) > 150) {
                            text = a.getName();
                            if ((font.getStringWidth(a.getName()) / 1000 * 8) > standardBoxWidth - 5) {
                                String part = a.getName();
                                length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                                System.out.println("Name! " + length);
                                int start = 0;
                                int end = (int) length;
                                while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                    part = text.substring(end, text.length());
                                    contentStream2.drawString(text.substring(start, end));
                                    start += (int) length;
                                    end += (int) length;
                                    if (end > text.length()) {
                                        end = text.length();
                                    }
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Acount++;
                                }
                                contentStream2.drawString(part);
                            } else {
                                contentStream2.drawString(text);
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Acount++;
                            }
                        } else {
                            contentStream2.drawString(a.getName());
                        }
                        //	contentStream2.setFont(font, 9);
                        contentStream2.moveTextPositionByAmount(155, 0);
                        contentStream2.drawString("" + a.getRating());
                        contentStream2.moveTextPositionByAmount(40, 0);
                        text = a.getNotes() == null ? "" : a.getNotes();
                        if (showNotes && (font.getStringWidth(text) / 1000 * 8) > 70) {
                            length = (70) / (font.getAverageFontWidth() / 1000 * 8);
                            contentStream2.drawString(text.substring(0, (int) length));
                            text = text.substring((int) length);
                            contentStream2.moveTextPositionByAmount(-190, -13);
                            Acount++;
                            if ((font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 5) {
                                String part = a.getNotes();
                                length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                                System.out.println(length);
                                int start = 0;
                                int end = (int) length;
                                while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                    part = text.substring(end, text.length());
                                    contentStream2.drawString(text.substring(start, end));
                                    start += (int) length;
                                    end += (int) length;
                                    if (end > text.length()) {
                                        end = text.length();
                                    }
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Acount++;
                                }
                                contentStream2.drawString(part);
                            } else {
                                contentStream2.drawString(text);
                            }
                            contentStream2.moveTextPositionByAmount(190, 0);
                        } else if (showNotes) {
                            contentStream2.drawString(text);
                        }

                        contentStream2.moveTextPositionByAmount(-195, -13);
                        Acount++;
                    }
                    if (Acount < 7) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (7 - Acount));
                    }
//				contentStream2.setNonStrokingColor(Color.RED);
//				contentStream2.drawString("AAAAAAAAAAAAAAAAA");

                    //Cyberdecks
                    contentStream2.moveTextPositionByAmount(pageWidth / 2, 13 * 8 + 14);
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Cyberdeck");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-2, -14);
                    int ProgCount = 0;
                    boolean gotDeck = true;
                    if (characterController.getCharacter().getCyberdecks().isEmpty()) {
                        gotDeck = false;
                    }
                    Deck mainDeck = gotDeck ? characterController.getCharacter().getCyberdecks().get(0) : null;
                    text = gotDeck ? "Model " + mainDeck.getModel() : "Model ";
                    contentStream2.drawString(text);
				/*if ((font.getStringWidth(text)/1000*8)>standardBoxWidth-15){
					contentStream2.moveTextPositionByAmount(0, -13);
				}*/
                    contentStream2.moveTextPositionByAmount(0, -13);
                    text = gotDeck ? "Device Rating " + mainDeck.getDeviceRating() : "Device Rating ";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(120, 0);
                    text = gotDeck ? "Simultanious Programs " + mainDeck.getSimultaniousPrograms() : "Simultanious Programs ";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-120, -13);
                    text = gotDeck ? "Attribute Array   " + mainDeck.getAttributeArray()[0] + " | " + mainDeck.getAttributeArray()[1] + " | " + mainDeck.getAttributeArray()[2] + " | " + mainDeck.getAttributeArray()[3] : "Attribute Array   ";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(0, -13);
                    contentStream2.drawString("Programs: ");
                    text = "";
                    for (Program id : characterController.getCharacter().getPrograms()) {
                        String addition = id.getName() + ", ";
                        if (font.getStringWidth(text + addition) / 1000 * 9 > standardBoxWidth - 5) {
                            text = text.substring(0, text.length() - 2);
                            contentStream2.drawString(text);
                            contentStream2.moveTextPositionByAmount(0, -13);
                            ProgCount++;
                            text = addition;
                        } else {
                            text += addition;
                        }
                    }
                    text = text.length() > 2 ? text.substring(0, text.length() - 2) : text;
                    contentStream2.drawString(text);

                    if (ProgCount < 3) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (3 - ProgCount));
                    }
                    contentStream2.moveTextPositionByAmount(0, -2);
                    contentStream2.setFont(font, 6);
                    contentStream2.drawString("Matrix Condition Monitor");
                    contentStream2.moveTextPositionByAmount(94.75f, 0);
                    for (int i = 1; i < 13; i++) {
                        contentStream2.drawString(i + "");
                        float move = 15;
                        if (i == 9)
                            move = 13.5f;
                        contentStream2.moveTextPositionByAmount(move, 0);
                    }

                    //Augments
                    contentStream2.moveTextPositionByAmount(-pageWidth / 2 - standardBoxWidth + 200, -31 - 23);
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Augmentations");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-190, -14);
                    contentStream2.drawString("Augmentation");
                    contentStream2.moveTextPositionByAmount(130, 0);
                    contentStream2.drawString("Rating");
                    contentStream2.moveTextPositionByAmount(35, 0);
                    contentStream2.drawString("Essence");
                    contentStream2.moveTextPositionByAmount(40, 0);
                    contentStream2.drawString("Notes");
                    contentStream2.moveTextPositionByAmount(-205, -13);
                    contentStream2.setFont(font, 8);
                    int AugCount = 0;
                    showNotes = (JOptionPane.showConfirmDialog(this.getContentPane(), "Do you wish to show notes for your Augementations?", "Show Notes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) ? true : false;

                    for (Augmentation a : characterController.getCharacter().getAugmentations()) {
                        contentStream2.drawString(a.getName());
                        if (font.getStringWidth(a.getName()) / 1000 * 8 > 130) {
                            contentStream2.moveTextPositionByAmount(0, -13);
                            AugCount++;
                        }
                        contentStream2.moveTextPositionByAmount(140, 0);
                        contentStream2.drawString("" + a.getRating());
                        contentStream2.moveTextPositionByAmount(35, 0);
                        contentStream2.drawString("-" + a.getEssenceLoss());
                        contentStream2.moveTextPositionByAmount(30, 0);
                        text = a.getNotes() == null ? "" : a.getNotes();
                        if (showNotes && (font.getStringWidth(text) / 1000 * 8) > 60) {
                            length = (60) / (font.getAverageFontWidth() / 1000 * 8);
                            contentStream2.drawString(text.substring(0, (int) length));
                            text = text.substring((int) length);
                            contentStream2.moveTextPositionByAmount(-205, -13);
                            AugCount++;
                            if ((font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 5) {
                                String part = a.getNotes();
                                length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                                System.out.println(length);
                                int start = 0;
                                int end = (int) length;
                                while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                    part = text.substring(end, text.length());
                                    contentStream2.drawString(text.substring(start, end));
                                    start += (int) length;
                                    end += (int) length;
                                    if (end > text.length()) {
                                        end = text.length();
                                    }
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    AugCount++;
                                }
                                contentStream2.drawString(part);
                            } else {
                                contentStream2.drawString(text);
                            }
                            contentStream2.moveTextPositionByAmount(205, 0);
                        } else if (showNotes) {
                            contentStream2.drawString(text);
                        }

                        contentStream2.moveTextPositionByAmount(-205, -13);
                        AugCount++;
                    }
                    if (AugCount < 7) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (7 - AugCount));
                    }


                    contentStream2.setFont(font, 10);
                    contentStream2.moveTextPositionByAmount(pageWidth / 2, 13 * 7 + 28 + 23);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Vehicle");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 8);
                    int Ncount = 0;
                    boolean gotVeh = true;
                    if (characterController.getCharacter().getVehicles().isEmpty()) {
                        gotVeh = false;
                    }
                    Vehicle mainVeh = gotVeh ? characterController.getCharacter().getVehicles().get(0) : null;
                    contentStream2.moveTextPositionByAmount(-2, -14);
                    contentStream2.drawString("Vehicle");
                    contentStream2.moveTextPositionByAmount(40, 0);
                    text = gotVeh ? mainVeh.getName() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-40, -13);
                    contentStream2.drawString("Handling");
                    contentStream2.moveTextPositionByAmount(60, 0);
                    if (gotVeh)
                        text = (mainVeh.getType() == CraftType.Groundcraft) ? mainVeh.getHandling() + "/" + mainVeh.getHandlingOffRoad() : "" + mainVeh.getHandling();
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-60 + standardBoxWidth / 2, 0);
                    contentStream2.drawString("Acceleration");
                    contentStream2.moveTextPositionByAmount(80, 0);
                    text = gotVeh ? "" + mainVeh.getAcceleration() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-80 - standardBoxWidth / 2, -13);
                    contentStream2.drawString("Speed");
                    contentStream2.moveTextPositionByAmount(60, 0);
                    if (gotVeh)
                        text = ((mainVeh.getType() == CraftType.Groundcraft) && mainVeh.getSpeed() != mainVeh.getSpeedOffRoad()) ? mainVeh.getSpeed() + "/" + mainVeh.getSpeedOffRoad() : "" + mainVeh.getSpeed();
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-60 + standardBoxWidth / 2, 0);
                    contentStream2.drawString("Pilot");
                    contentStream2.moveTextPositionByAmount(80, 0);
                    text = gotVeh ? "" + mainVeh.getPilot() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-80 - standardBoxWidth / 2, -13);
                    contentStream2.drawString("Body");
                    contentStream2.moveTextPositionByAmount(60, 0);
                    text = gotVeh ? "" + mainVeh.getBody() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-60 + standardBoxWidth / 2, 0);
                    contentStream2.drawString("Armor");
                    contentStream2.moveTextPositionByAmount(80, 0);
                    text = gotVeh ? "" + mainVeh.getArmor() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-80 - standardBoxWidth / 2, -13);
                    contentStream2.drawString("Sensor");
                    contentStream2.moveTextPositionByAmount(60, 0);
                    text = gotVeh ? "" + mainVeh.getSensor() : "";
                    contentStream2.drawString(text);
                    contentStream2.moveTextPositionByAmount(-60 + standardBoxWidth / 2, 0);
                    contentStream2.drawString("Notes");
                    contentStream2.moveTextPositionByAmount(35, 0);

                    text = (!gotVeh || mainVeh.getNotes() == null) ? "" : mainVeh.getNotes();
                    showNotes = (JOptionPane.showConfirmDialog(this.getContentPane(), "Do you wish to show notes for your Vehicle?", "Show Notes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) ? true : false;
                    if (showNotes && (font.getStringWidth(text) / 1000 * 8) > 95) {
                        length = (95) / (font.getAverageFontWidth() / 1000 * 8);
                        contentStream2.drawString(text.substring(0, (int) length));
                        text = text.substring((int) length);
                        contentStream2.moveTextPositionByAmount(-standardBoxWidth / 2 - 35, -13);
                        Ncount++;
                        if ((font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 5) {
                            String part = mainVeh.getNotes();
                            length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                            System.out.println(length);
                            int start = 0;
                            int end = (int) length;
                            while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                part = text.substring(end, text.length());
                                contentStream2.drawString(text.substring(start, end));
                                start += (int) length;
                                end += (int) length;
                                if (end > text.length()) {
                                    end = text.length();
                                }
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Ncount++;
                            }
                            contentStream2.drawString(part);
                        } else {
                            contentStream2.drawString(text);
                        }
                        contentStream2.moveTextPositionByAmount(205, 0);
                    } else if (showNotes) {
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(-standardBoxWidth / 2 - 35 + 205, -13);

                        Ncount++;
                    } else {
                        contentStream2.moveTextPositionByAmount(-standardBoxWidth / 2 - 35 + 205, 0);
                    }
                    if (Ncount < 2) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (2 - Ncount));
                    }

                    //SpellPrepRitCompForm
                    contentStream2.moveTextPositionByAmount(-pageWidth / 2 - 60, -13 * 5 - 17);
                    text = "Spless/Preps./Rituals";
                    int magi = 1;
                    if (characterController.getCharacter().getMagicalness().equals(Magical.Technomancer)) {
                        text = "Complex Forms";
                        contentStream2.moveTextPositionByAmount(+50, 0);
                        magi = 2;
                    } else if (characterController.getCharacter().getMagicalness().equals(Magical.Mundane)) {
                        text = "";
                        magi = 0;
                    }
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString(text);
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    if (magi == 2) {
                        contentStream2.moveTextPositionByAmount(+80 + 10 - standardBoxWidth, -14);
                        text = "Target";
                    } else {
                        contentStream2.moveTextPositionByAmount(+80 + 60 - standardBoxWidth, -14);
                        text = "Type";
                    }
                    if (magi > 0) {
                        contentStream2.drawString("Name");
                        contentStream2.moveTextPositionByAmount(105, 0);
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(45, 0);
                        contentStream2.drawString("Range");
                        contentStream2.moveTextPositionByAmount(40, 0);
                        contentStream2.drawString("Duration");
                        contentStream2.moveTextPositionByAmount(55, 0);
                        contentStream2.drawString("Drain");
                        contentStream2.moveTextPositionByAmount(-245, -13);
                        contentStream2.setFont(font, 8);

                        ArrayList<Spell> list = magi == 1 ? characterController.getCharacter().getSpells() : characterController.getCharacter().getComplexForms();
                        if (magi == 1) {
                            list.addAll(characterController.getCharacter().getRituals());
                            list.addAll(characterController.getCharacter().getPreparations());
                        }
                        Mcount = 0;
                        for (Spell sp : list) {
                            contentStream2.drawString(sp.getName());
                            contentStream2.moveTextPositionByAmount(105, 0);
                            if (font.getStringWidth(sp.getName()) / 1000 * 8 > 100) {
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Mcount++;
                            }
                            contentStream2.drawString(sp.getType().name());
                            contentStream2.moveTextPositionByAmount(45, 0);
                            contentStream2.drawString("" + sp.getRange());
                            contentStream2.moveTextPositionByAmount(40, 0);
                            contentStream2.drawString("" + sp.getDuration());
                            contentStream2.moveTextPositionByAmount(55, 0);
                            text = magi == 1 ? "F" : "L";
                            text = sp.getDrain() >= 0 ? text + "+" + sp.getDrain() : text + sp.getDrain();
                            contentStream2.drawString(text);
                            contentStream2.moveTextPositionByAmount(-245, -13);
                            Mcount++;
                        }
                    }
                    if (Mcount < 14) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (14 - Mcount));
                    }
//				contentStream2.setNonStrokingColor(Color.RED);
//				contentStream2.drawString("AAAAAAAAAAAAAAAAAAAAAA");

                    //powers
                    contentStream2.moveTextPositionByAmount(+standardBoxWidth - 50, -31);
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Powers");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    contentStream2.moveTextPositionByAmount(-standardBoxWidth + 50, -14);
                    contentStream2.drawString("Name");
                    contentStream2.moveTextPositionByAmount(235, 0);
                    contentStream2.drawString("Rating");
                    contentStream2.moveTextPositionByAmount(-235, -13);
//				contentStream2.drawString("Notes");
//				contentStream2.moveTextPositionByAmount(-140,-13);
                    ArrayList<Power> powers = characterController.getCharacter().getAdeptPowers();
                    powers.addAll(characterController.getCharacter().getOtherPowers());
                    int Pcount = 0;
                    contentStream2.setFont(font, 8);
                    showNotes = (JOptionPane.showConfirmDialog(this.getContentPane(), "Do you wish to show notes for your Powers?", "Show Notes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) ? true : false;
                    for (Power p : powers) {
                        contentStream2.drawString(p.getName());
                        if ((font.getStringWidth(p.getName()) / 1000 * 8) > 235) {
                            contentStream2.moveTextPositionByAmount(0, -13);
                            Pcount++;
                        }

                        contentStream2.moveTextPositionByAmount(240, 0);
                        contentStream2.drawString("" + p.getLevel());
                        contentStream2.moveTextPositionByAmount(-235, 0);
                        if (showNotes)
                            contentStream2.moveTextPositionByAmount(0, -13);
                        text = p.getNotes() == null ? "" : p.getNotes();

                        if (showNotes && (font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 5) {
                            String part = p.getNotes();
                            length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                            System.out.println(length);
                            int start = 0;
                            int end = (int) length;
                            while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                part = text.substring(end, text.length());
                                contentStream2.drawString(text.substring(start, end));
                                start += (int) length;
                                end += (int) length;
                                if (end > text.length()) {
                                    end = text.length();
                                }
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Pcount++;
                            }
                            contentStream2.drawString(part);

                        } else if (showNotes) {
                            contentStream2.drawString(text);
                        }

                        contentStream2.moveTextPositionByAmount(-5, -13);
                        Pcount++;
                    }
                    if (Pcount < 8) {
                        contentStream2.moveTextPositionByAmount(0, -13 * (8 - Pcount));
                    }

//				contentStream2.setNonStrokingColor(Color.RED);
//				contentStream2.drawString("AAAAAAAAAAAAAAAAAAAAAA");
                    contentStream2.moveTextPositionByAmount(+pageWidth / 2, 13 * 28);
                    contentStream2.setFont(font, 10);
                    contentStream2.setNonStrokingColor(Color.WHITE);
                    contentStream2.drawString("Gear");
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.setFont(font, 9);
                    int numGear = characterController.getCharacter().getGear().size();
                    int numCars = characterController.getCharacter().getVehicles().size() > 1 ? characterController.getCharacter().getVehicles().size() - 1 : 0;
                    int numDecks = characterController.getCharacter().getCyberdecks().size() > 1 ? characterController.getCharacter().getCyberdecks().size() - 1 : 0;
                    int Gcount = 0;
                    contentStream2.moveTextPositionByAmount(-2, -14);
                    if (numGear + numCars + numDecks < 23) {

                        contentStream2.drawString("Name");
                        contentStream2.moveTextPositionByAmount(130, 0);
                        contentStream2.drawString("Rating");
                        contentStream2.moveTextPositionByAmount(50, 0);
                        contentStream2.drawString("Notes");
                        contentStream2.moveTextPositionByAmount(-180, -13);
                        for (Gear g : characterController.getCharacter().getGear()) {
                            contentStream2.drawString(g.getName());
                            if (font.getStringWidth(g.getName()) / 1000 * 9 > 140) {
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Gcount++;
                            }
                            contentStream2.moveTextPositionByAmount(140, 0);
                            contentStream2.drawString("" + g.getRating());
                            contentStream2.moveTextPositionByAmount(40, 0);
                            contentStream2.drawString(g.getNotes());
                            contentStream2.moveTextPositionByAmount(-180, -13);
                        }
                        if (numDecks > 0) {
                            for (Deck g : characterController.getCharacter().getCyberdecks().subList(1, characterController.getCharacter().getCyberdecks().size() - 1)) {
                                contentStream2.drawString(g.getModel());
                                if (font.getStringWidth(g.getModel()) / 1000 * 9 > 140) {
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Gcount++;
                                }
                                contentStream2.moveTextPositionByAmount(140, 0);
                                contentStream2.drawString("" + g.getDeviceRating());
                                contentStream2.moveTextPositionByAmount(40, 0);
                                contentStream2.drawString("[" + g.getAttributeArray()[0] + "|" + g.getAttributeArray()[1] + "|" + g.getAttributeArray()[2] + "|" + g.getAttributeArray()[3] + "]" + "P:" + g.getSimultaniousPrograms());
                                contentStream2.moveTextPositionByAmount(-180, -13);
                            }
                        }
                        if (numCars > 0) {
                            for (Vehicle g : characterController.getCharacter().getVehicles().subList(1, characterController.getCharacter().getVehicles().size() - 1)) {
                                contentStream2.drawString(g.getName());
                                if (font.getStringWidth(g.getName()) / 1000 * 9 > 140) {
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Gcount++;
                                }
                                contentStream2.moveTextPositionByAmount(140, 0);
                                contentStream2.drawString("" + g.getType().name());
                                contentStream2.moveTextPositionByAmount(40, 0);
                                contentStream2.drawString(g.getNotes());
                                contentStream2.moveTextPositionByAmount(-180, -13);
                            }
                        }
                    } else {
                        text = "";
                        for (Gear g : characterController.getCharacter().getGear()) {
                            String addition = g.getName() + "(" + g.getRating() + "), ";
                            if (font.getStringWidth(text + addition) / 1000 * 9 > standardBoxWidth - 5) {
                                text = text.substring(0, text.length() - 2);
                                contentStream2.drawString(text);
                                contentStream2.moveTextPositionByAmount(0, -13);
                                Gcount++;
                                text = addition;
                            } else {
                                text += addition;
                            }
                        }
                        text = text.length() > 2 ? text.substring(0, text.length() - 2) : text;
                        contentStream2.drawString(text);
                        contentStream2.moveTextPositionByAmount(0, -13);
                        text = "";
                        if (numDecks > 0) {
                            for (Deck id : characterController.getCharacter().getCyberdecks().subList(1, characterController.getCharacter().getCyberdecks().size() - 1)) {
                                String addition = id.getModel() + ", ";
                                if (font.getStringWidth(text + addition) / 1000 * 9 > standardBoxWidth - 5) {
                                    text = text.substring(0, text.length() - 2);
                                    contentStream2.drawString(text);
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Gcount++;
                                    text = addition;
                                } else {
                                    text += addition;
                                }
                            }
                            text = text.length() > 2 ? text.substring(0, text.length() - 2) : text;
                            contentStream2.drawString(text);
                        }
                        contentStream2.moveTextPositionByAmount(0, -13);
                        text = "";
                        if (numCars > 0) {
                            for (Vehicle id : characterController.getCharacter().getVehicles().subList(1, characterController.getCharacter().getVehicles().size() - 1)) {
                                String addition = id.getName() + ", ";
                                if (font.getStringWidth(text + addition) / 1000 * 9 > standardBoxWidth - 5) {
                                    text = text.substring(0, text.length() - 2);
                                    contentStream2.drawString(text);
                                    contentStream2.moveTextPositionByAmount(0, -13);
                                    Gcount++;
                                    text = addition;
                                } else {
                                    text += addition;
                                }
                            }
                            text = text.length() > 2 ? text.substring(0, text.length() - 2) : text;
                            contentStream2.drawString(text);
                        }
                    }
                    if (Gcount < 26)
                        contentStream2.moveTextPositionByAmount(0, -13 * (26 - Gcount));
                    contentStream2.moveTextPositionByAmount(-pageWidth / 2, -8);
                    contentStream2.drawString("(c)2014 Nicolá Michel Henry Riedmann");

                    contentStream2.endText();
                    // Make sure that the content stream is closed:
                    contentStream2.close();

                    if (lastSkillGroupIndex > -1 || lastSkillIndex > -1/*|| ALLE ANDEREN*/) {


                        PDPage page3 = new PDPage(PDPage.PAGE_SIZE_A4);
                        PDPage page4 = new PDPage(PDPage.PAGE_SIZE_A4);
                        document.addPage(page3);

                        PDPageContentStream contentStream3 = new PDPageContentStream(document, page3);

                        boolean left = true;
                        //boolean additionalPage=false;

                        curX = startX;
                        curY = startY;
                        contentStream3.beginText();
                        //Title
                        contentStream3.setFont(font, 12);
                        contentStream3.setStrokingColor(Color.BLACK);
                        title = "SR5 CharGen Character Sheet";
                        contentStream3.moveTextPositionByAmount(curX + (pageWidth / 2) - (font.getStringWidth(title) / 1000 * 12) / 2, curY + 3);
                        contentStream3.drawString(title);
                        contentStream3.moveTextPositionByAmount(+(font.getStringWidth(title) / 1000 * 12) / 2, -14);
                        contentStream3.setFont(font, 14);
                        title = characterController.getCharacter().getPersonalData().getName();
                        contentStream3.moveTextPositionByAmount(-(font.getStringWidth(title) / 1000 * 14) / 2, 0);
                        contentStream3.drawString(title);
                        contentStream3.endText();

                        curX = startX;
                        curY = startY - 20;

                        if (lastSkillGroupIndex > -1 || lastSkillIndex > -1) {
                            drawSkillBox(contentStream3, pageWidth, standardBoxWidth, curX, curY);


                            contentStream3.beginText();
                            if (left) {
                                contentStream3.moveTextPositionByAmount(curX + pageWidth / 2 - 57, curY - 11);
                            } else {
                                contentStream3.moveTextPositionByAmount(curX + 5, curY - 11);
                            }

                            contentStream3.setFont(font, 10);
                            contentStream3.setNonStrokingColor(Color.RED);
                            contentStream3.drawString("Skills");
                            contentStream3.setFont(font, 9);

                            contentStream3.moveTextPositionByAmount(-235, -18);
                            contentStream3.drawString("Skill");
                            contentStream3.moveTextPositionByAmount(90, 0);
                            contentStream3.drawString("RTG");
                            contentStream3.moveTextPositionByAmount(20, 0);
                            contentStream3.drawString("Type");
                            contentStream3.moveTextPositionByAmount(30, 0);
                            contentStream3.drawString("Skill");
                            contentStream3.moveTextPositionByAmount(90, 0);
                            contentStream3.drawString("RTG");
                            contentStream3.moveTextPositionByAmount(20, 0);
                            contentStream3.drawString("Type");
                            contentStream3.setFont(font, 8);
                            contentStream3.moveTextPositionByAmount(-(110 + 140), -13);
                            Scount = 0;
                            Scols = 1;
                            if (lastSkillGroupIndex > -1) {
                                for (SkillGroup sg : characterController.getCharacter().getSkillGroups().subList(lastSkillGroupIndex, characterController.getCharacter().getSkillGroups().size())) {
                                    if (!spaceLeft) {
                                        break;
                                    }
                                    if (Scount > 25 && Scols < 2) {
                                        contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                        Scount = 0;
                                        SkillRows = 0;
                                        Scols++;
                                    }
                                    contentStream3.drawString(sg.getName());
                                    if (font.getStringWidth(sg.getName()) / 100 > 110) {
                                        contentStream3.moveTextPositionByAmount(0, -13);
                                        Scount++;
                                        SkillRows++;
                                        if (Scount > 25 && Scols < 2) {
                                            contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                            Scount = 0;
                                            Scols++;
                                            SkillRows = 0;
                                        }
                                    }
                                    contentStream3.moveTextPositionByAmount(95, 0);
                                    contentStream3.drawString("" + sg.getValue());
                                    contentStream3.moveTextPositionByAmount(25, 0);
                                    contentStream3.drawString("SG");
                                    Scount++;

                                    SkillRows++;
                                    if (SkillRows >= maxSkillRows && Scols > 1) {
                                        lastSkillGroupIndex = LR.getSkillGroupList().indexOf(sg);
                                        spaceLeft = false;
                                    }

                                    for (Skill s : sg.getSkills()) {
                                        if (!spaceLeft) {
                                            break;
                                        }
                                        if (Scount > 25 && Scols < 2) {
                                            contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                            Scount = 0;
                                            Scols++;
                                            SkillRows = 0;
                                        }
                                        contentStream3.moveTextPositionByAmount(-115, -13);
                                        contentStream3.drawString(s.getName());
                                        if (font.getStringWidth(s.getName()) / 100 > 110) {
                                            contentStream3.moveTextPositionByAmount(0, -13);
                                            Scount++;
                                            SkillRows++;
                                            if (Scount > 25 && Scols < 2) {
                                                contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                                Scount = 0;
                                                Scols++;
                                                SkillRows = 0;
                                            }
                                        }
                                        contentStream3.moveTextPositionByAmount(90, 0);
                                        contentStream3.drawString("" + sg.getValue() + s.getAttributeShorthand());
                                        contentStream3.moveTextPositionByAmount(30, 0);
                                        contentStream3.drawString("A");
                                        contentStream3.moveTextPositionByAmount(-5, 0);
                                        Scount++;
                                        SkillRows++;
                                        if (SkillRows >= maxSkillRows && Scols > 1) {
                                            lastSkillGroupIndex = LR.getSkillGroupList().indexOf(sg);
                                            spaceLeft = false;
                                        }
                                    }
                                    contentStream3.moveTextPositionByAmount(-120, -13);
                                }
                            }
                            if (lastSkillIndex > -1) {
                                for (Skill s : characterController.getCharacter().getSkills().subList(lastSkillIndex, characterController.getCharacter().getSkills().size())) {
                                    if (!spaceLeft) {
                                        break;
                                    }
                                    if (Scount > 25 && Scols < 2) {
                                        contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                        Scount = 0;
                                        Scols++;
                                        SkillRows = 0;
                                    }
                                    contentStream3.drawString(s.getName());
                                    if (font.getStringWidth(s.getName()) / 100 > 110) {
                                        contentStream3.moveTextPositionByAmount(0, -13);
                                        Scount++;
                                        SkillRows++;
                                        if (Scount > 25 && Scols < 2) {
                                            contentStream3.moveTextPositionByAmount(140, 13 * 26);
                                            Scount = 0;
                                            Scols++;
                                            SkillRows = 0;
                                        }
                                    }
                                    contentStream3.moveTextPositionByAmount(95, 0);
                                    contentStream3.drawString("" + s.getValue() + s.getAttributeShorthand());
                                    contentStream3.moveTextPositionByAmount(30, 0);
                                    String type = s.isKnowledge() ? "K" : "A";
                                    contentStream3.drawString(type);
                                    Scount++;
                                    SkillRows++;
                                    contentStream3.moveTextPositionByAmount(-125, -13);
                                    if (SkillRows >= maxSkillRows && Scols > 1) {
                                        lastSkillIndex = LR.getSkillGroupList().indexOf(s);
                                        spaceLeft = false;
                                    }
                                }
                            }
                            contentStream3.endText();


                            curY -= 30 * 13;

                        }
                        if (lastQualitiyIndex > -1) {
                            int BoxRows = 16;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }

                            contentStream3.beginText();
                            if (left) {
                                contentStream3.moveTextPositionByAmount(curX + pageWidth / 2 - 65, curY - 11);
                            } else {
                                contentStream3.moveTextPositionByAmount(curX + 5, curY - 11);
                            }
                            contentStream3.setFont(font, 10);
                            contentStream3.setNonStrokingColor(Color.WHITE);
                            contentStream3.drawString("Qualities");
                            contentStream3.setNonStrokingColor(Color.BLACK);
                            contentStream3.moveTextPositionByAmount(-standardBoxWidth + 55, -15);
                            contentStream3.setFont(font, 9);
                            contentStream3.drawString("Quality");
                            contentStream3.moveTextPositionByAmount(standardBoxWidth - 35, 0);
                            contentStream3.drawString("Type");
                            contentStream3.setFont(font, 8);
                            contentStream3.moveTextPositionByAmount(-standardBoxWidth + 35, -13);
                            Qcount = 0;
                            for (Quality q : characterController.getCharacter().getQualities().subList(lastQualitiyIndex, characterController.getCharacter().getQualities().size())) {
                                if (showNotes) {
                                    text = q.isLeveled() ? q.getName() + " " + q.getLevel() + " " + q.getNotes() : q.getName() + " " + q.getNotes();
                                } else {
                                    text = q.isLeveled() ? q.getName() + " " + q.getLevel() : q.getName();
                                }
                                if (Qcount < 15 && ((font.getStringWidth(text) / 1000 * 8) < standardBoxWidth - 35) || !showNotes) {
                                    contentStream3.drawString(text);
                                    contentStream3.moveTextPositionByAmount(standardBoxWidth - 30, 0);
                                    text = q.isPositive() ? "P" : "N";
                                    contentStream3.drawString(text);
                                    contentStream3.moveTextPositionByAmount(-standardBoxWidth + 30, -13);
                                    Qcount++;
                                } else if (Qcount < 15) {
                                    text = q.isLeveled() ? q.getName() + " " + q.getLevel() : q.getName();
                                    contentStream3.drawString(text);
                                    contentStream3.moveTextPositionByAmount(standardBoxWidth - 30, 0);
                                    text = q.isPositive() ? "P" : "N";
                                    contentStream3.drawString(text);
                                    contentStream3.moveTextPositionByAmount(-standardBoxWidth + 35, -13);
                                    Qcount++;
                                    text = q.getNotes();
                                    if ((font.getStringWidth(text) / 1000 * 8) > standardBoxWidth - 10) {
                                        String part = q.getNotes();
                                        length = (standardBoxWidth - 15) / (font.getAverageFontWidth() / 1000 * 8);
                                        System.out.println(length);
                                        int start = 0;
                                        int end = (int) length;

                                        while ((font.getStringWidth(part) / 1000 * 8) > standardBoxWidth - 10) {
                                            part = text.substring(end, text.length());
                                            contentStream3.drawString(text.substring(start, end));
                                            start += (int) length;
                                            end += (int) length;
                                            if (end > text.length()) {
                                                end = text.length();
                                            }
                                            contentStream3.moveTextPositionByAmount(0, -13);
                                            Qcount++;
                                        }
                                        contentStream3.drawString(part);

                                    } else {
                                        contentStream3.drawString(text);
                                    }
                                    contentStream3.moveTextPositionByAmount(-5, -13);
                                    Qcount++;
                                } else {
                                    lastQualitiyIndex = characterController.getCharacter().getQualities().indexOf(q) + 1;
                                    break;
                                }

                            }
                            contentStream3.endText();

                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastIDIndex > -1) {
                            int BoxRows = 6;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastContactIndex > -1) {
                            int BoxRows = 8;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastRweapIndex > -1) {
                            int BoxRows = 9;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastMweapIndex > -1) {
                            int BoxRows = 9;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastArmorIndex > -1) {
                            int BoxRows = 9;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastAugmentIndex > -1) {
                            int BoxRows = 9;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        //IF ALL ARE THERE; PAGEBREAK HERE
                        if (lastSpellIndex > -1) {
                            int BoxRows = 12;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            } else if ((curY - (BoxRows + 1) * 13 - 8) < 10) {
                                contentStream3.close();
                                document.addPage(page4);
                                contentStream3 = new PDPageContentStream(document, page4);
                                curX = startX;
                                curY = startY - 20;
                                left = true;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastPowerIndex > -1) {
                            int BoxRows = 10;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            } else if ((curY - (BoxRows + 1) * 13 - 8) < 10) {
                                contentStream3.close();
                                document.addPage(page4);
                                contentStream3 = new PDPageContentStream(document, page4);
                                curX = startX;
                                curY = startY - 20;
                                left = true;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }
                        if (lastGearIndex > -1) {
                            int BoxRows = 28;
                            if (left && ((curY - (BoxRows + 1) * 13 - 8) < 10)) {
                                left = false;
                                curX = pageWidth / 2 + 5;
                                curY = startY - 20;
                            } else if ((curY - (BoxRows + 1) * 13 - 8) < 10) {
                                contentStream3.close();
                                document.addPage(page4);
                                contentStream3 = new PDPageContentStream(document, page4);
                                curX = startX;
                                curY = startY - 20;
                                left = true;
                            }
                            if (left) {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 - 190, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + 20, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY, curY, curY - 10}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            } else {
                                drawRowBox(contentStream3, new float[]{pageWidth / 2 + 5, curY - 15, 185, 15}, new float[]{curX, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 15, curX + pageWidth / 2 - 40, curX}, new float[]{curY - (BoxRows + 1) * 13 - 8, curY - (BoxRows + 1) * 13 - 8, curY - 10, curY, curY}, new float[]{curX + 5, curY, curX + pageWidth / 2 - 20}, (BoxRows));
                            }
                            curY -= (BoxRows + 2) * 13 + 8;
                        }


//					// Make sure that the content stream is closed:
                        contentStream3.close();

                    }


                    // Save the results and ensure that the document is properly closed:
                    //add file chooser
                    System.out.println(filepath);
                    document.save(filepath);
                    document.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(this.getContentPane(), "Charactersheet for " + characterController.getCharacter().getPersonalData().getName() + " created!");
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            JOptionPane.showMessageDialog(this.getContentPane(), new JComponent[]{new JLabel("You either can't save your character right now, or there is no open character!"), new JLabel("Please finish character creation, or load a character!")});
        }
    }

    public void raiseCharKarma() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        JComponent[] input = new JComponent[]{
                new JLabel("Raise character karma by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Raise Karma", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().getPersonalData().addKarma((int) sp.getValue());
        }
    }

    public void raiseCharMoney() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, 9000000, 50));
        JComponent[] input = new JComponent[]{
                new JLabel("Raise character Currency by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Raise Currency", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().setMoney(characterController.getCharacter().getMoney() + (int) sp.getValue());
        }
    }

    public void raiseCharNotoriety() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        JComponent[] input = new JComponent[]{
                new JLabel("Raise character Notoriety by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Raise Notoriety", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().getPersonalData().addNotoriety((int) sp.getValue());
        }
    }

    public void raiseCharPublicAwareness() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        JComponent[] input = new JComponent[]{
                new JLabel("Raise character Public Awareness by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Raise Public Awareness", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().getPersonalData().addPublicAwareness((int) sp.getValue());
        }
    }

    public void buyOffCharNotoriety() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, (int) Math.floor((double) characterController.getCharacter().getPersonalData().getStreetCred() / 2), 1));
        JComponent[] input = new JComponent[]{
                new JLabel("You can buy off 1 point of Notoriety by spending 2 points of StreetCred"),
                new JLabel("Lower character Notoriety by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Lower Notoriety", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().getPersonalData().lowerNotoriety((int) sp.getValue());
        }
    }

    public void raiseCharStreetCred() {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        JComponent[] input = new JComponent[]{
                new JLabel("Raise character StreetCred by"),
                sp
        };
        if (JOptionPane.showConfirmDialog(this.getContentPane(), input, "Raise StreetCred", JOptionPane.CANCEL_OPTION) != JOptionPane.CANCEL_OPTION) {
            characterController.getCharacter().getPersonalData().addExtraStreetCred((int) sp.getValue());
        }
    }

    public void rollDice() {
        JSpinner dice = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        JComboBox<String> limit = new JComboBox<String>();
        limit.addItem("None");
        limit.addItem("Physical Limit");
        limit.addItem("Mental Limit");
        limit.addItem("Social Limit");

        if (JOptionPane.CANCEL_OPTION == JOptionPane.showConfirmDialog(this.getContentPane(), new JComponent[]{new JLabel("Please choose how many dice to roll:"), dice, new JLabel("Please choose Limit to use:"), limit}, "Roll Dice!", JOptionPane.OK_CANCEL_OPTION)) {
            return;
        }
        DieRoller roller = new DieRoller();
        DieRoll roll = roller.rollDice((int) dice.getValue());
        int limitThresh = 0;
        switch (limit.getSelectedIndex()) {
            case 0:
                limitThresh = 0;
                break;
            case 1:
                limitThresh = characterController.getCharacter().getAttributes().getPhysicalLimit();
                break;
            case 2:
                limitThresh = characterController.getCharacter().getAttributes().getMentalLimit();
                break;
            case 3:
                limitThresh = characterController.getCharacter().getAttributes().getSocialLimit();
                break;
        }
        int successes =
                (limitThresh > 0 && (int) roll.getNumberOfSuccesses() > limitThresh) ?
                        limitThresh :
                        (int) roll.getNumberOfSuccesses();
        String crit = roll.getResult().equals(CRITICAL_GLITCH) ? "Critical " : "";
        String text = roll.isGlitched() ? crit + "Glitch!" : (int) roll.getNumberOfSuccesses() + " Successes!";
        JOptionPane.showMessageDialog(this.getContentPane(), text);

    }

    public void rollRandomRun() {
        System.out.println(RandomRunGenerator.generateRandomRun());
        JOptionPane.showMessageDialog(this.getContentPane(), RandomRunGenerator.generateRandomRun());
    }

    public void showHelp() {
        JOptionPane.showMessageDialog(this.getContentPane(), "No one can help you!");
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(this.getContentPane(), new JLabel("<html><p>(c)2014 Nicolá Michel Henry Riedmann</p><p>This free software has no affiliation whatsoever with Shadowrun or Catalyst Games Labs.</p></html>"));
    }

    //
//	3x3 - 1,1 - 128px - <  9 chars
//  5x5 - 2,2 - 100px - < 25 chars
//  7x7 - 3,3 - 70ox  - < 49 chars
//  9x9 - 4,4 - 60px  - < 81 chars
//
    private void generateButtons(JPanel panel) {

        int middleX = 4, middleY = 4;
        int size = 9;
        int imageSize = 60;

        ArrayList<Character> chars = this.loadAllCharacters();
        //Collections.shuffle(chars);
        Collections.sort(chars);
        if (chars.size() < 9) {
            middleX = 1;
            middleY = 1;
            size = 3;
            imageSize = 128;
        } else if (chars.size() < 25) {
            middleX = 2;
            middleY = 2;
            size = 5;
            imageSize = 100;
        } else if (chars.size() < 49) {
            middleX = 3;
            middleY = 3;
            size = 7;
            imageSize = 70;
        }

        JButton newCharButton = new JButton("+");
        newCharButton.setPreferredSize(new Dimension(imageSize, imageSize));
        newCharButton.setToolTipText("Create a new character");
        newCharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCharacterGeneration();
            }
        });

        GridBagConstraints con = new GridBagConstraints();
        con.fill = GridBagConstraints.BOTH;
        con.anchor = GridBagConstraints.CENTER;

        con.gridx = middleX;
        con.gridy = middleY;
        panel.add(newCharButton, con);

        con.gridx = 0;
        con.gridy = 0;

        for (Character c : chars) {
            final Character cha = c;
            JButton charButton = new JButton();
            charButton.setIcon(new ImageIcon(c.getCharPic()));
            charButton.setPreferredSize(new Dimension(imageSize, imageSize));
            charButton.setToolTipText(c.getPersonalData().getName());
            charButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    characterController.setCharacter( cha );
                    displayPreloadedChar();
                }
            });
            if ((con.gridx == middleX && con.gridy == middleY)) {
                con.gridx++;
            }
            panel.add(charButton, con);
            if (con.gridx < size - 1) {
                con.gridx++;
            } else {
                con.gridx = 0;
                con.gridy++;
            }
            if (con.gridy >= size) {
                break;
            }
        }
        while (con.gridy < size) {
            JButton but = new JButton(" ");
            but.setEnabled(false);
            but.setPreferredSize(new Dimension(imageSize, imageSize));
            panel.add(but, con);
            if (con.gridx < size - 1) {
                con.gridx++;
            } else {
                con.gridx = 0;
                con.gridy++;
            }
        }

    }

    private void displayPreloadedChar() {
        this.getContentPane().removeAll();
        clearContents();
        this.getContentPane().add(characterDisplay(false));


        this.getContentPane().revalidate();
        this.getContentPane().repaint();
        this.revalidate();
        this.repaint();
    }

    private void generateMenu() {
        this.mainMenu = new MainMenu(this);
        this.setJMenuBar(mainMenu);
    }

    private void initUI() {

        JPanel panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(panel);

        this.generateButtons(panel);
        this.generateMenu();

        setTitle("Shadowrun 5 - Character Generator");
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
