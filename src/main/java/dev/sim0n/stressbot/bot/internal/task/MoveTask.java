package dev.sim0n.stressbot.bot.internal.task;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.task.AbstractTask;
import dev.sim0n.stressbot.util.RandomUtil;
import dev.sim0n.stressbot.util.location.Location;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 *
 * This task will automatically move the bot in a random direction
 */
public class MoveTask extends AbstractTask {
    private float direction = (float) RandomUtil.getRandomInt(0, 360);

    private int runs;

    @Override
    public void run(ChannelHandlerContext ctx, Bot bot) {
        this.updateRotation();
        this.move(bot);
    }

    /**
     * Updates the rotation of the bot
     */
    private void updateRotation() {
        if (++this.runs > RandomUtil.getRandomInt(20, 20 * 3)) {
            this.runs = 0;

            this.direction += RandomUtil.getRandomInt(-75, 75);
            this.direction %= 360F;
        }
    }

    /**
     * Moves the bot
     * @param bot The bot
     */
    private void move(Bot bot) {
        Location location = this.getMovedLocation(bot.getLocation(), bot.getMoveSpeed());

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        bot.updateLocation(x, y, z, direction, 0, true);
    }

    /**
     * Gets a location moved {@param offset} in front of it
     * @param input The location to move
     * @param offset The offset to move the location
     * @return The moved location
     */
    private Location getMovedLocation(Location input, double offset) {
        Location location = input.clone();

        return location.move(Math.toRadians(this.direction), offset);
    }
}
