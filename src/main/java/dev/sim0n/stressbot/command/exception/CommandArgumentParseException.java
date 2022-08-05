package dev.sim0n.stressbot.command.exception;

/**
 * @author sim0n
 */
public class CommandArgumentParseException extends RuntimeException {
    public CommandArgumentParseException(Throwable cause) {
        super(cause);
    }
}
