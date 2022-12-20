package dev.sim0n.stressbot.command;

import dev.sim0n.stressbot.StressBot;
import dev.sim0n.stressbot.command.parameter.CommandParameter;
import lombok.Getter;

import java.lang.reflect.*;
import java.util.Optional;

/**
 * @author sim0n
 */
public final class CommandExecutor {
    private final Object instance;
    private final Method method;
    private final Command command;

    @Getter
    private final String label;

    @Getter
    private final CommandParameter[] parameters;

    public CommandExecutor(Object instance, Method method, Command command) {
        this.instance = instance;
        this.method = method;
        this.command = command;

        int parameterCount = method.getParameterCount();

        this.parameters = new CommandParameter[parameterCount];

        for (int i = 0; i < parameterCount; i++) {
            Class<?> parameterType = method.getParameters()[i].getType();

            if (parameterType == Optional.class) {
                Type genericType = method.getGenericParameterTypes()[i];

                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedGenericType = (ParameterizedType) genericType;

                    parameterType = (Class<?>) parameterizedGenericType.getActualTypeArguments()[0];
                }
            }

            this.parameters[i] = new CommandParameter(parameterType);
        }

        this.label = command.value();
    }

    public void execute(Object... arguments) {
        try {
            method.invoke(this.instance, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            StressBot.LOGGER.warning("Unable to execute command " + this.command.value() + " due to " + e.getMessage());
        }
    }
}
