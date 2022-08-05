package dev.sim0n.stressbot.command.internal.argument;

import dev.sim0n.stressbot.command.argument.AbstractCommandArgument;
import dev.sim0n.stressbot.command.exception.CommandArgumentParseException;
import dev.sim0n.stressbot.command.parameter.CommandParameter;
import lombok.Getter;

/**
 * @author sim0n
 */
@Getter
public class DoubleArgument extends AbstractCommandArgument<Double> {
    private final Class<?> type = Double.class;

    @Override
    public Double parse(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CommandArgumentParseException(e);
        }
    }
}
