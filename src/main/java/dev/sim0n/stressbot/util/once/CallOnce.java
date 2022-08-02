package dev.sim0n.stressbot.util.once;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class CallOnce<T> {
    private final Consumer<T> action;

    private boolean once;

    public void call(T t) {
        if (this.once) {
            return;
        }

        this.once = true;

        this.action.accept(t);
    }
}
