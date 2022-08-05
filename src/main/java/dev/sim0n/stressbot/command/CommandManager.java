package dev.sim0n.stressbot.command;

import dev.sim0n.stressbot.StressBot;
import dev.sim0n.stressbot.command.argument.ArgumentParser;
import dev.sim0n.stressbot.command.parameter.CommandParameter;
import dev.sim0n.stressbot.util.primitive.PrimitivesParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author sim0n
 */
public class CommandManager {
    private final Map<String, List<CommandExecutor>> executors = new HashMap<>();

    private final PrimitivesParser primitivesParser = new PrimitivesParser();
    private final ArgumentParser argumentParser = new ArgumentParser();

    public void registerCommands(Object instance) {
        Class<?> clazz = instance.getClass();

        if (!clazz.isAnnotationPresent(Namespace.class)) {
            StressBot.LOGGER.warning(clazz.getSimpleName() + " Doesn't have a valid namespace");
            return;
        }

        this.executors.put(clazz.getAnnotation(Namespace.class).value(), Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Command.class))
                .map(method -> new CommandExecutor(instance, method, method.getAnnotation(Command.class)))
                .collect(Collectors.toList()));
    }

    public void execute(String input) {
        String[] inputArgs = input.split(" ");

        List<CommandExecutor> commandExecutors = this.executors.get(inputArgs[0].toLowerCase());

        if (commandExecutors == null) {
            StressBot.LOGGER.warning(String.format("Couldn't find a command by the name of \"%s\"", inputArgs[0]));
            return;
        }

        Optional<CommandExecutor> commandExecutorOpt = commandExecutors.stream()
                .filter(executor -> executor.getLabel().equals(inputArgs[1].toLowerCase()))
                .findFirst();

        if (!commandExecutorOpt.isPresent()) {
            StressBot.LOGGER.warning(String.format("Couldn't find a command executor by the name of \"%s\"", inputArgs[1]));
            return;
        }

        // skip the first 2 inputs as we only want the arguments for the parameters
        String[] args = Arrays.stream(inputArgs)
                .skip(2)
                .toArray(String[]::new);

        CommandExecutor executor = commandExecutorOpt.get();

        CommandParameter[] parameters = executor.getParameters();
        Object[] arguments = new Object[parameters.length];

        IntStream.range(0, parameters.length).forEach(i -> {
            try {
                arguments[i] = this.argumentParser.getCommandArgument(primitivesParser.toWrapper(parameters[i].getType()))
                        .orElseThrow(() -> new RuntimeException("Unsupported parameter type"))
                        .parse(Arrays.stream(args).skip(i).findFirst().orElseThrow(() -> new RuntimeException("Unable to find argument")));
            } catch (Exception e) {
                StressBot.LOGGER.warning(String.format("Unable to parse parameter %d. %s", i, e.getMessage()));
                throw new RuntimeException(e);
            }
        });

        executor.execute(arguments);
    }
}
