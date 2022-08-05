package dev.sim0n.stressbot.command.internal.bot;

import dev.sim0n.stressbot.StressBot;
import dev.sim0n.stressbot.bot.controller.BotController;
import dev.sim0n.stressbot.command.Command;
import dev.sim0n.stressbot.command.Namespace;

/**
 * @author sim0n
 */
@Namespace("bot")
public class BotCommands {
    private final StressBot app = StressBot.getInstance();

    @Command("chat")
    public void sendChatMessage(String message) {
        this.app.getBotController().getBots().forEach(bot -> bot.sendMessage(message));
    }

    @Command("movement")
    public void toggleMovement() {

    }

    @Command("slot")
    public void changeSlot(int slot) {

    }

    @Command("swing")
    public void toggleSwing() {
        System.out.println("swung!");
    }

    @Command("speed")
    public void changeMoveSpeed(double value) {
        this.app.getBotController().getBots().forEach(bot -> bot.setMoveSpeed(value));
    }
}
