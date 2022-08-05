package dev.sim0n.stressbot.util.primitive;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author sim0n
 */
@Getter
@RequiredArgsConstructor
public enum Primitives {
    INTEGER(Integer.TYPE, Integer.class),
    DOUBLE(Double.TYPE, Double.class);

    private final Class<?> type;
    private final Class<?> wrapperClass;
}
