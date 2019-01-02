package io.github.unseenwizzard.sr5chargen.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JMenuBar {

    public JMenu advancement;
    public JMenu help;

    public MainMenu(MainFrame controller) {
//        JMenuBar menubar = new JMenuBar();
        /* Image */Icon iconNew = UIManager.getIcon("FileView.fileIcon");// new
        // ImageIcon("graphics/new.png");
        /* Image */Icon iconOpen = UIManager.getIcon("FileView.directoryIcon");// new
        // ImageIcon("graphics/open.png");
        /* Image */Icon iconSave = UIManager
                .getIcon("FileView.floppyDriveIcon");// new
        // ImageIcon("graphics/save.png");
        /* Image */Icon iconPrint = UIManager.getIcon("FileView.computerIcon");
        /* Image */Icon iconHelp = UIManager.getIcon("OptionPane.questionIcon");// new
        // ImageIcon("graphics/help.png");
        /* Image */Icon iconAbout = UIManager
                .getIcon("OptionPane.informationIcon");// new
        // ImageIcon("graphics/about.png");

        JMenu character = new JMenu("Character");
        JMenu group = new JMenu("Group");
        JMenu tools = new JMenu("Tools");
        advancement = new JMenu("Advancement");
        help = new JMenu("Help");

        JMenuItem newChar = new JMenuItem("New", iconNew);
        JMenuItem loadChar = new JMenuItem("Load", iconOpen);
        JMenuItem saveChar = new JMenuItem("Save", iconSave);
        JMenuItem printChar = new JMenuItem("Export(PDF)",iconPrint);

        JMenuItem newGroup = new JMenuItem("New", iconNew);
        JMenuItem loadGroup = new JMenuItem("Load", iconOpen);
        JMenuItem saveGroup = new JMenuItem("Save", iconSave);

        JMenuItem dieRoller = new JMenuItem("Die Roller");
        JMenuItem randomRunGen = new JMenuItem("Random Run Generator");

        JMenuItem addKarma = new JMenuItem("Add Karma");
        JMenuItem addMoney = new JMenuItem("Add Money");
        JMenuItem addExtraStreetCred = new JMenuItem("Raise Street Cred");
        JMenuItem addNotoriety = new JMenuItem("Raise Notoriety");
        JMenuItem buyOffNotoriety = new JMenuItem("Buy off Notoriety");
        JMenuItem addPublicAwareness = new JMenuItem("Raise Public Awareness");

        JMenuItem showHelp = new JMenuItem("Show help", iconHelp);
        JMenuItem about = new JMenuItem("About SR5 Chargen", iconAbout);

        newChar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startCharacterGeneration();
            }
        });

        loadChar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadCharacter();
            }
        });

        saveChar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveCharacter();
            }
        });

        printChar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.printCharacter();
            }
        });

        showHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showHelp();
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showAbout();
            }
        });

        newGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO CREATE NEW GROUP
            }
        });

        loadGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO LOAD AND DISPLAY A GROUP
            }
        });

        saveGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO SAVE A GROUP
            }
        });

        dieRoller.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rollDice();

            }
        });

        randomRunGen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rollRandomRun();

            }
        });

        addKarma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.raiseCharKarma();
            }
        });

        addMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.raiseCharMoney();
            }
        });

        addNotoriety.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.raiseCharNotoriety();
            }
        });

        addPublicAwareness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.raiseCharPublicAwareness();
            }
        });

        addExtraStreetCred.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.raiseCharStreetCred();
            }
        });

        buyOffNotoriety.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buyOffCharNotoriety();
            }
        });

        character.add(newChar);
        character.add(loadChar);
        character.add(saveChar);
        character.add(printChar);

        group.add(newGroup);
        group.add(loadGroup);
        group.add(saveGroup);

        tools.add(dieRoller);
        tools.add(randomRunGen);

        advancement.add(addKarma);
        advancement.add(addMoney);
        advancement.add(addExtraStreetCred);
        advancement.add(addNotoriety);
        advancement.add(buyOffNotoriety);
        advancement.add(addPublicAwareness);

        help.add(showHelp);
        help.add(about);

        this.add(character);
        this.add(group);
        this.add(tools);
        this.add(help);
    }
}
