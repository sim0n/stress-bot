package dev.sim0n.stressbot.command.parameter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Parameter;
import java.util.Optional;

/**
 * @author sim0n
 */
@Getter
@RequiredArgsConstructor
public class CommandParameter {
    private final Class<?> type;
}
