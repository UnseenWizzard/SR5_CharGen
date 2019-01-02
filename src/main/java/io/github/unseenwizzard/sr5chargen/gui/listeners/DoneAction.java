package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoneAction implements ActionListener {
    private MainFrame mainFrame;
    private JPanel contentPanel=null;
    public DoneAction(MainFrame mainFrame, JPanel panel){
        this.mainFrame = mainFrame;
        this.contentPanel=panel;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (
                JOptionPane.showConfirmDialog(
                        contentPanel,
                        new JComponent[]{
                                new JLabel("You can save your character now."),
                                new JLabel("Nevertheless, you can only carry up to 7 Karma out of character generation, and any free contacts, spells or anything else you'd get at generation will be lost."),
                                new JLabel("Are you sure you're done?")
                        },
                        "Really done?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                        ==JOptionPane.YES_OPTION)
        {
            if (mainFrame.currentCharacter.getPersonalData().getKarma()>7)
                mainFrame.currentCharacter.getPersonalData().setKarma(7);
            mainFrame.saveAllowed=true;
            mainFrame.saveCharacter();
            mainFrame.characterDisplay(false);
        }
    }
}
