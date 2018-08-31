package io.github.unseenwizzard.sr5chargen.utils.dice;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DieRoll {

    public enum Result {
        SUCCESS,
        GLITCH,
        CRITICAL_GLITCH;
    }

    private final Result result;
    private final long numberOfSuccesses;
    private final List<Die> dice;

    public boolean isSuccess() {
        return result.equals(Result.SUCCESS);
    }

    public boolean isGlitched() {
        return !isSuccess();
    }

    static DieRoll createSuccessfulRoll(long numberOfSuccesses, List<Die> dice) {
        return new DieRoll(Result.SUCCESS, numberOfSuccesses, dice);
    }

    static DieRoll createGlitchedRoll(long numberOfSuccesses, List<Die> dice) {
        return new DieRoll(Result.GLITCH, numberOfSuccesses, dice);
    }

    static DieRoll createCriticallyGlitchedRoll(long numberOfSuccesses, List<Die> dice) {
        return new DieRoll(Result.CRITICAL_GLITCH, numberOfSuccesses, dice);
    }
}
