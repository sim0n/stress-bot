package dev.sim0n.stressbot;

import com.google.common.collect.ImmutableList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * @author sim0n
 */
public class Start {
    public static void main(String[] args) {
        OptionParser optionParser = new OptionParser();

        OptionSpec<Void> helpSpec = optionParser
                .acceptsAll(ImmutableList.of("H", "h", "help"), "displays a help menu")
                .forHelp();

        OptionSpec<Integer> botCountSpec = optionParser
                .acceptsAll(ImmutableList.of("C", "bot-count"), "bot count")
                .withRequiredArg()
                .ofType(Integer.class);

        OptionSpec<Integer> loginDelaySpec = optionParser
                .acceptsAll(ImmutableList.of("D", "login-delay"), "the bot login delay")
                .withRequiredArg()
                .ofType(Integer.class);

        OptionSpec<String> serverAddressSpec = optionParser
                .acceptsAll(ImmutableList.of("A", "address"), "the server host address")
                .withRequiredArg()
                .ofType(String.class);

        OptionSpec<Integer> serverPortSpec = optionParser
                .acceptsAll(ImmutableList.of("P", "port"), "the server port")
                .withRequiredArg()
                .ofType(Integer.class);

        try {
            OptionSet options = optionParser.parse(args);

            if (options.has(helpSpec)) {
                optionParser.printHelpOn(System.out);
                return;
            }

            int loginDelay = 4000;
            int botCount = 25;

            String address = "127.0.0.1";
            int port = 25565;

            if (options.has("C")) {
                botCount = botCountSpec.value(options);
            }

            if (options.has("D")) {
                loginDelay = loginDelaySpec.value(options);
            }

            if (options.has("A")){
                address = serverAddressSpec.value(options);
            }

            if (options.has("P")){
                port = serverPortSpec.value(options);
            }

            StressBot stressBot = new StressBot(address, port, botCount, loginDelay);
            stressBot.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
