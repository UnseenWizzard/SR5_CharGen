package io.github.unseenwizzard.sr5chargen.utils.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DieRoller {

    private Random random;

    public DieRoller() {
        this.generateRandomGenerator();
    }

    private void generateRandomGenerator() {

        this.random = new Random(System.currentTimeMillis());
    }

    public DieRoll rollDice(int numOfDice) {
        List<Die> rolledDice = new ArrayList<>();
        for (int i = 0; i < numOfDice; i++) {
            rolledDice.add(Die.create(random.nextInt(6) + 1));
        }

        long numberOfSuccesses = rolledDice.stream().filter(Die::isSuccess).count();
        long numberOfFailures = rolledDice.stream().filter(Die::isFailure).count();

        if (moreThanHalfOfRollAreFailures(numOfDice, numberOfFailures) && numberOfSuccesses == 0) {
            return DieRoll.createCriticallyGlitchedRoll(numberOfSuccesses, rolledDice);
        } else if (moreThanHalfOfRollAreFailures(numOfDice, numberOfFailures)) {
            return DieRoll.createGlitchedRoll(numberOfSuccesses, rolledDice);
        }
        return DieRoll.createSuccessfulRoll(numberOfSuccesses, rolledDice);
    }

    private boolean moreThanHalfOfRollAreFailures(int numOfDice, long numberOfFailures) {
        return numberOfFailures > numOfDice / 2;
    }


}