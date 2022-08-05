package dev.sim0n.stressbot.runnable;

import dev.sim0n.stressbot.StressBot;

import java.util.Scanner;

/**
 * @author sim0n
 */
public class ConsoleRunnable implements Runnable {
    private final StressBot app = StressBot.getInstance();

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            this.app.getCommandManager().execute(scanner.nextLine());
        }
    }
}
