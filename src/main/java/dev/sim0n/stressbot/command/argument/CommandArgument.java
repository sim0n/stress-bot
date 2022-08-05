package dev.sim0n.stressbot.command.argument;

/**
 * @author sim0n
 */
public interface CommandArgument<Argument> {
    Argument parse(String input);

    Class<?> getType();
}
