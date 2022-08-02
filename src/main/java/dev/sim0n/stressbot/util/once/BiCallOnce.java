package dev.sim0n.stressbot.util.once;

import lombok.RequiredArgsConstructor;

import java.util.function.BiConsumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class BiCallOnce<T, U> {
    private final BiConsumer<T, U> action;

    private boolean once;

    public void call(T t, U u) {
        if (this.once) {
            return;
        }

        this.once = true;

        this.action.accept(t, u);
    }
}
