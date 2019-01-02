package io.github.unseenwizzard.sr5chargen.gui.listeners;

import io.github.unseenwizzard.sr5chargen.gui.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class KarmaToMoney implements ChangeListener {
    private MainFrame mainFrame;
    JSpinner money = null;

    public KarmaToMoney(MainFrame mainFrame, JSpinner money) {
        this.mainFrame = mainFrame;
        this.money = money;
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        if ((int) ((JSpinner) arg0.getSource()).getValue() < mainFrame.prevValue) {
            money.getModel().setValue(
                    (double) money.getModel().getValue() + 2000);

        } else {
            money.getModel().setValue(
                    (double) money.getModel().getValue() - 2000);
        }
        mainFrame.prevValue = (int) ((JSpinner) arg0.getSource()).getValue();
        money.revalidate();money.repaint();
        ((JSpinner) arg0.getSource()).revalidate();((JSpinner) arg0.getSource()).repaint();
    }
}
