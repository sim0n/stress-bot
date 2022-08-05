package dev.sim0n.stressbot.util.primitive;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author sim0n
 */
public class PrimitivesParser {
    private final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER = new HashMap<>();

    public PrimitivesParser() {
        Arrays.stream(Primitives.values()).forEach(primitive -> {
            PRIMITIVE_TO_WRAPPER.put(primitive.getType(), primitive.getWrapperClass());
        });
    }

    public Class<?> toWrapper(Class<?> primitive) {
        Optional<Class<?>> wrapper = Optional.ofNullable(PRIMITIVE_TO_WRAPPER.get(primitive));

        return wrapper.orElse(primitive);
    }
}
