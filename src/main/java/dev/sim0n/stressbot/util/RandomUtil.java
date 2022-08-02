package dev.sim0n.stressbot.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sim0n
 */
@UtilityClass
public class RandomUtil {
    public int getRandomInt(int lowerbound, int upperbound) {
        return ThreadLocalRandom.current().nextInt(lowerbound, upperbound);
    }
}
