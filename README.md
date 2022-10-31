# stress-bot

Stress Bot is a fully customizable Minecraft server stress testing tool.

## Features

* Customizable amount of bots
* Customizable login delay between bots
* Bots can move around
* Bots can swing
* Extremely lightweight (5-6% CPU usage on a Ryzen 9 5950x for 500 bots)

## Requirements

* Stress Bot only supports Minecraft: Java Edition version 1.8.
* Your server must be in offline mode: `online-mode` must be set to `false` in `server.properties`.
* Bukkit throttles connections to 1 per 4000 milliseconds by default. It's recommended to temporarily set `connection-throttle` to `0` in `bukkit.yml` to speed up stress testing.

## Options

```
Option                       Description
------                       -----------
-A, --address <String>       the server host address
-C, --bot-count <Integer>    bot count
-D, --login-delay <Integer>  the bot login delay
-H, -h, --help               displays a help menu
-P, --port <Integer>         the server port
-U, --username <String>      username prefix
```

## Example Usage

```
java -jar stress-bot.jar -A 127.0.0.1 -P 25565 -C 100 -D 50
```

This command will make 100 bots join 127.0.0.1:25565 with a delay of 50 milliseconds.

## TODO

* Refactor code and clean it up
* Add support for other versions besides 1.8
* Load a shared world system
* Integrated Minecraft physics
* Make commands
* Make a GUI (possibly)
