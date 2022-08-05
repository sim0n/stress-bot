package dev.sim0n.stressbot.command.internal.argument;

import dev.sim0n.stressbot.command.argument.AbstractCommandArgument;
import dev.sim0n.stressbot.command.exception.CommandArgumentParseException;
import dev.sim0n.stressbot.command.parameter.CommandParameter;
import lombok.Getter;

/**
 * @author sim0n
 */
@Getter
public class IntegerArgument extends AbstractCommandArgument<Integer> {
    private final Class<?> type = Integer.class;

    @Override
    public Integer parse(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CommandArgumentParseException(e);
        }
    }
}
