package dev.sim0n.stressbot.command.internal.argument;

import dev.sim0n.stressbot.command.argument.AbstractCommandArgument;
import dev.sim0n.stressbot.command.parameter.CommandParameter;
import lombok.Getter;

/**
 * @author sim0n
 */
@Getter
public class StringArgument extends AbstractCommandArgument<String> {
    private final Class<?> type = String.class;

    @Override
    public String parse(String input) {
        return input;
    }

}
