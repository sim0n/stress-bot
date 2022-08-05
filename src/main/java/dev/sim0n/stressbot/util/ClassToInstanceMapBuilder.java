package dev.sim0n.stressbot.util;

import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sim0n
 */
@Getter
public class ClassToInstanceMapBuilder<T> {
    private final Map<Class<?>, T> map = new HashMap<>();

    public ClassToInstanceMapBuilder<T> put(T instance) {
        this.map.put(instance.getClass(), instance);

        return this;
    }

    public ClassToInstanceMapBuilder<T> build() {
        return this;
    }

    public <Type extends T> Type getInstance(Class<Type> clazz) {
        return (Type) this.map.get(clazz);
    }

    public Collection<T> values() {
        return this.map.values();
    }


}
