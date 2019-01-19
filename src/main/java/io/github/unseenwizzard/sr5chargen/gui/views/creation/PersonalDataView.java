package io.github.unseenwizzard.sr5chargen.gui.views.creation;

import io.github.unseenwizzard.sr5chargen.control.CharacterController;
import io.github.unseenwizzard.sr5chargen.data.character.Character;
import io.github.unseenwizzard.sr5chargen.data.character.Metatype;
import io.github.unseenwizzard.sr5chargen.data.character.Sex;
import io.github.unseenwizzard.sr5chargen.gui.DocumentSizeFilter;
import io.github.unseenwizzard.sr5chargen.gui.MainFrame;
import io.github.unseenwizzard.sr5chargen.utils.ListRoutine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersonalDataView extends JPanel {
    private final JPanel panel;
    private final JPanel infoPanel;
    private final JTextField nameInput;
    private final JComboBox<Sex> sexInput;
    private final JComboBox<Metatype> metatypeInput;

    private final CharacterController characterController;
    private final MainFrame characterCreationWizard;

    public PersonalDataView(CharacterController characterController, MainFrame characterCreationWizard) {
        super(new BorderLayout());
        this.characterController = characterController;
        this.characterCreationWizard = characterCreationWizard;

        panel = new JPanel(new GridBagLayout());
        infoPanel = new JPanel(new BorderLayout());

        nameInput = new JTextField(40);
        AbstractDocument d = (AbstractDocument) nameInput.getDocument();
        d.setDocumentFilter(new DocumentSizeFilter(26));

        sexInput = new JComboBox<>(Sex.values());
        sexInput.setSelectedIndex(0);

        metatypeInput = new JComboBox<>(Metatype.values());
        metatypeInput.setSelectedIndex(0);

        addContents();

        add(panel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.PAGE_END);
        panel.setVisible(true);
        infoPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void addContents() {
        addLabels();
        addInputFields();
        addContinueButton();
    }

    private void addLabels() {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.LINE_START;
        labelConstraints.gridx = 0;
        labelConstraints.gridy = 0;
        labelConstraints.gridwidth = 2;

        panel.add(new JLabel("name"), labelConstraints);

        labelConstraints.gridy++;
        panel.add(new JLabel("sex"), labelConstraints);

        labelConstraints.gridy++;
        panel.add(new JLabel("metatype"), labelConstraints);
    }

    private void addInputFields() {
        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.anchor = GridBagConstraints.LINE_START;
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.gridx = 2;
        inputConstraints.gridy = 0;
        inputConstraints.insets = new Insets(0, 10, 0, 0);
        inputConstraints.gridwidth = 2;

        panel.add(nameInput, inputConstraints);

        inputConstraints.gridwidth = 1;
        inputConstraints.gridy++;
        panel.add(sexInput, inputConstraints);

        inputConstraints.gridy++;
        panel.add(metatypeInput, inputConstraints);
    }

    private void addContinueButton() {
        JButton next = new JButton("Next");
        next.addActionListener(this::setCharacterPersonalData);
        infoPanel.add(next,BorderLayout.EAST);
    }

    private void setCharacterPersonalData(ActionEvent event) {

        String charName = nameInput.getText();
        System.out.println("Name is:[" + charName + "]");

        Sex charSex = (Sex) sexInput.getSelectedItem();
        Metatype charType = (Metatype) metatypeInput.getSelectedItem();

        String characterPortraitFilePath = "images/female.png";
        if (charName.isEmpty()) {
            if (charSex == Sex.FEMALE) {
                charName = "Jane Doe";
            } else {
                charName = "John Doe";
                characterPortraitFilePath = "images/male.png";
            }
        }

        File path = null;
        try {
            path = new File(ListRoutine.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (path != null && path.getAbsolutePath().endsWith("SR5CharGen.jar"))
            characterPortraitFilePath = (path.getAbsolutePath().substring(0, path.getAbsolutePath().length() - 14) + characterPortraitFilePath);

        Character createdCharacter = characterController.createNewCharacter(charName, charType, charSex);


        if (JOptionPane.showConfirmDialog(panel, "Do you want to choose a character picture now?", "Character Picture", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            setCharPic();
        } else {
            try {
                byte[] byterep = Files.readAllBytes(Paths.get(characterPortraitFilePath));
                createdCharacter.setCharPicData(byterep);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(panel, new JLabel(new ImageIcon(createdCharacter.getCharPic())), "Character picture set!", JOptionPane.INFORMATION_MESSAGE);


        characterCreationWizard.priorityChoosing();
    }

    private void setCharPic() {
        JFileChooser c = new JFileChooser();
        int retVal = c.showOpenDialog(this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            String filepath = c.getSelectedFile().getAbsolutePath();
            if (!(filepath.toLowerCase().endsWith(".png") || filepath.toLowerCase().endsWith(".gif") || filepath.toLowerCase().endsWith(".jpg"))) {
                JOptionPane.showMessageDialog(this, "File must be an image file (.png,.jpg,.gif)!", "Failure opening file!", JOptionPane.ERROR_MESSAGE);
                characterController.saveAllowed = false;
                return;
            }
            File picFile = new File(filepath);
            BufferedImage pic = null;
            try {
                pic = ImageIO.read(picFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            BufferedImage resizedImage = new BufferedImage(128, 128, pic.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(pic, 0, 0, 128, 128, null);
            g.dispose();

            try {
                File small = File.createTempFile("tempPic", ".png");
                small.deleteOnExit();
                ImageIO.write(resizedImage, "png", small);
                byte[] byterep = Files.readAllBytes(Paths.get(small.toURI()));
                if (byterep.length == 0)
                    System.out.println("byte rep empty");
                characterController.getCharacter().setCharPicData(byterep);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


        }
    }


}
