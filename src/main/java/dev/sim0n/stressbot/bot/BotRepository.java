package dev.sim0n.stressbot.bot;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sim0n
 */
@Getter
public class BotRepository {
    private final List<Bot> bots = new ArrayList<>();
}
