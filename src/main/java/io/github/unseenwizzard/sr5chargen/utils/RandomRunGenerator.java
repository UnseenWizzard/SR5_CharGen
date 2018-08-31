package io.github.unseenwizzard.sr5chargen.utils;

import io.github.unseenwizzard.sr5chargen.utils.dice.Die;
import io.github.unseenwizzard.sr5chargen.utils.dice.DieRoller;

public class RandomRunGenerator {

    private static String generateVenue(Die die) {
        String venue = "The runners go to a meeting ";
        switch (die.getRolledValue()) {
            case (1):
                venue += "at a bar, club, or restaurant ";
                break;
            case (2):
                venue += "at a warehouse, dock, factory or another scarcely frequented place ";
                break;
            case (3):
                venue += "in the Barrens or a similiar urban hellhole ";
                break;
            case (4):
                venue += "in a moving vehicle ";
                break;
            case (5):
                venue += "in the matrix ";
                break;
            case (6):
                venue += "in the astral plane ";
                break;
        }
        venue += "for their next job.\n";
        return venue;
    }

    private static String generateEmployer(Die firstDie, Die secondDie) {
        String employer = "";
        int rolledValue = firstDie.getRolledValue() + secondDie.getRolledValue();
        switch (rolledValue) {
            case (2):
                employer += "A secret society (e.g. Black Lodge, Human Nation) ";
                break;
            case (3):
                employer += "A political group or activists (e.g. Humanis Policlub, Mothers of Metahumans) ";
                break;
            case (4):
                employer += "A government agency ";
                break;
            case (5):
                employer += "A small corporation (A-corp or smaller) ";
                break;
            case (6):
                employer += "A small corporation (A-corp or smaller) ";
                break;
            case (7):
                employer += "A megacorporation (AA-corp or bigger) ";
                break;
            case (8):
                employer += "A megacorporation (AA-corp or bigger) ";
                break;
            case (9):
                employer += "A crime syndicate (e.g. Yakuza, Mafia) ";
                break;
            case (10):
                employer += "A magical group (e.g. Illuminates of the New Dawn) ";
                break;
            case (11):
                employer += "A private individual ";
                break;
            case (12):
                employer += "An exotic or mysterious being (e.g. free spirit, dragon, AI) ";
                break;
        }
        employer += "hires them to ";
        return employer;
    }

    private static String generateRunType(Die die) {
        String string = "";
        switch (die.getRolledValue()) {
            case (1):
                string += "steal data from ";
                break;
            case (2):
                string += "assasinate or destroy ";
                break;
            case (3):
                string += "extract or infiltrate ";
                break;
            case (4):
                string += "distract ";
                break;
            case (5):
                string += "provide protection for ";
                break;
            case (6):
                string += "transport ";
                break;
        }
        return string;
    }

    private static String generateTarget(Die die) {
        String string = "";
        switch (die.getRolledValue()) {
            case (1):
                string += "an important employee";
                break;
            case (2):
                string += "a prototype";
                break;
            case (3):
                string += "revolutionary research findings";
                break;
            case (4):
                string += "a genetically modified life-form";
                break;
            case (5):
                string += "a magical artefact";
                break;
            case (6):
                string += "a building, rural location or public building";
                break;
        }
        string += ".\n";
        return string;
    }

    private static String generateComplication(Die die) {
        String string = "The run gets complicated when ";
        switch (die.getRolledValue()) {
            case (1):
                string += "security is higher than expected";
                break;
            case (2):
                string += "a third party is also interested";
                break;
            case (3):
                string += "the target is not what it seemed to be. (Employer lied)";
                break;
            case (4):
                string += "the runners figure out that they need a special piece of equipment for the job";
                break;
            case (5):
                string += "the target is being or has been moved to another location";
                break;
            case (6):
                string += "the Johnson pulls a fast one on the team and tries to ripp them off";
                break;
        }
        string += ".\n";
        return string;
    }

    public static String generateRandomRun() {
        StringBuilder runStringBuilder = new StringBuilder();
        DieRoller roller = new DieRoller();

        String venue = generateVenue(rollSingleDie(roller));
        String employer = generateEmployer(rollSingleDie(roller), rollSingleDie(roller));
        String runType = generateRunType(rollSingleDie(roller));
        String target = generateTarget(rollSingleDie(roller));
        String complication = generateComplication(rollSingleDie(roller));

        return runStringBuilder
                .append(venue)
                .append(employer)
                .append(runType)
                .append(target)
                .append(complication)
                .toString();
    }

    private static Die rollSingleDie(DieRoller roller) {
        return roller.rollDice(1).getDice().get(0);
    }
}
