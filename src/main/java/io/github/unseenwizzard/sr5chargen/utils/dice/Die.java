package io.github.unseenwizzard.sr5chargen.utils.dice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "create")
public class Die {

    private static final int MIN_VALUE_FOR_SUCCESS = 5;
    private static final int FAILURE_VALUE = 1;

    final int rolledValue;

    public boolean isSuccess() {
        return rolledValue >= MIN_VALUE_FOR_SUCCESS;
    }

    public boolean isFailure() {
        return rolledValue == FAILURE_VALUE;
    }
}
