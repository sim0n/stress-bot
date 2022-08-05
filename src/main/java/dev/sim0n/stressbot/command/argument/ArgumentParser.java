package dev.sim0n.stressbot.command.argument;

import dev.sim0n.stressbot.command.internal.argument.DoubleArgument;
import dev.sim0n.stressbot.command.internal.argument.IntegerArgument;
import dev.sim0n.stressbot.command.internal.argument.StringArgument;
import dev.sim0n.stressbot.util.ClassToInstanceMapBuilder;

import java.util.Optional;

/**
 * @author sim0n
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ArgumentParser {
    private final ClassToInstanceMapBuilder<CommandArgument> commandArguments = new ClassToInstanceMapBuilder<CommandArgument>()
            .put(new IntegerArgument())
            .put(new DoubleArgument())
            .put(new StringArgument())
            .build();

    public <T> Optional<CommandArgument<T>> getCommandArgument(Class<T> type) {
        for (CommandArgument<T> argument : this.commandArguments.values()) {
            if (argument.getType().isAssignableFrom(type)) {
                return Optional.of(argument);
            }
        }

        return Optional.empty();
    }

}
