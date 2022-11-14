package me.herrphoenix.mcbm;

import com.github.steveice10.packetlib.ProxyInfo;
import me.herrphoenix.mcbm.bot.BotClient;
import me.herrphoenix.mcbm.bot.BotClientManager;
import me.herrphoenix.mcbm.util.TextUtils;
import me.herrphoenix.mcbm.util.LogColor;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h3>Minecraft Bots Manager</h3>
 * Main
 *
 * @author HerrPhoenix
 */
public class Main {
    private static AtomicReference<BotClient> bot;
    private static AtomicBoolean running;
    private static String host;
    private static int port;

    public static void main(String[] args) {
        System.out.println(LogColor.WHITE_BOLD_BRIGHT + "Minecraft Bots Manager");
        System.out.println(LogColor.WHITE_BRIGHT + "Version: 2.3.0 | Author: HerrPhoenix");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        running = new AtomicBoolean(true);
        bot = new AtomicReference<>();

        Scanner setup = new Scanner(System.in);
        System.out.print(LogColor.GREEN + "[SETUP] " + LogColor.WHITE_BRIGHT + "Insert Server IP: ");
        host = setup.nextLine();

        System.out.print(LogColor.GREEN + "[SETUP] " + LogColor.WHITE_BRIGHT + "Insert Server Port: ");
        port = setup.nextInt();

        System.out.println(LogColor.GREEN + "[SETUP] " + LogColor.WHITE_BRIGHT + "Type \".help\" for a list" +
                " of commands");

        new Thread(() -> {
            while(running.get()) {
                if (bot.get() != null && bot.get().getLatestLog() != null) {
                    System.out.println(bot.get().getLatestLog());
                    bot.get().clearLog();
                }
            }
            setup.close();
        }).start();

        new Thread(() -> {
            Scanner console = new Scanner(System.in);
            while(running.get()) {
                if (console.hasNextLine()) {
                    String input = console.nextLine();
                    handleInput(input);
                }
            }
            console.close();
        }).start();
    }

    /**
     * Handles Console line input, whether it's a command or not.
     *
     * @param input The String input into the command line
     */
    private static void handleInput(String input) {
        if (input.startsWith(".")) {
            String[] arguments = input.split(" ");
            String command = arguments[0].replace(".", "");

            switch (command) {
                case "help":
                    System.out.println(getHelp());
                    break;
                case "disconnect":
                    bot.get().disconnect("Disconnected");
                    break;
                case "exit":
                    for (BotClient client : BotClientManager.getBots()) {
                        client.disconnect("Disconnected");
                    }
                    running.set(false);
                    System.exit(0);
                    break;
                case "connect":
                    bot.get().connect(true);
                    break;
                case "add":
                    if (arguments.length > 1) {
                        String ign = arguments[1];

                        BotClient newBot = BotClientManager.createBot(ign, host, port);
                        newBot.initializeProtocol();
                        newBot.createSession();
                        newBot.connect(false);

                        bot.set(newBot);
                    }
                    break;
                case "proxy":
                    if (arguments.length > 3) {
                        ProxyInfo.Type type;
                        String host = arguments[2];
                        int port;

                        try {
                            port = Integer.parseInt(arguments[3]);
                            type = ProxyInfo.Type.valueOf(arguments[1]);
                        } catch (NumberFormatException e) {
                            System.out.println(LogColor.RED_BRIGHT + "[ERR] Port \"" + arguments[3] + "\"" +
                                    " is in incorrect format.");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(LogColor.RED_BRIGHT + "[ERR] Type \"" + arguments[1] + "\"" +
                                    " is incorrect.");
                            System.out.println(LogColor.WHITE_BRIGHT + "Proxy Types are: HTTP, SOCKS4, SOCKS5\n");
                            break;
                        }

                        BotClientManager.setProxy(type, host, port);
                        System.out.println(LogColor.GREEN_BRIGHT + "[SUCCESS] " + LogColor.WHITE_BRIGHT +
                                type.toString().toUpperCase() + " Proxy set to " + host + ":" + port);
                    }
                    break;
                default:
                    int id;

                    try {
                        id = Integer.parseInt(command);
                    } catch (NumberFormatException e) {
                        System.out.println(LogColor.RED_BRIGHT + "[ERR] Command \"" + command + "\"" +
                                " does not exist.");
                        break;
                    }

                    BotClient toSet = BotClientManager.getBot(id);

                    if (toSet == null) {
                        System.out.println(LogColor.RED_BRIGHT + "[ERR] Bot on channel " +
                                id + " not found.");
                        break;
                    }

                    bot.set(BotClientManager.getBot(id));

                    System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" +
                            LogColor.GREEN_BRIGHT + "[HANDLER] " + LogColor.WHITE_BRIGHT +
                            "Switched to channel " + id + " \"" + bot.get().getUsername() +
                            "\"" + LogColor.RESET);
                    break;
            }
        } else {
            if (bot.get() != null) {
                bot.get().sendMessage(input);
            }
        }
    }

    /**
     * Gets the help message printed when running the '.help' command
     *
     * @return The application's help message
     */
    private static String getHelp() {
        return LogColor.RESET + "\n" +
                "   .help                          -  Show this message.\n\n" +
                "   .disconnect                    -  Disconnect current bot from the server.\n\n" +
                "   .connect                       -  Connect current bot to the server.\n\n" +
                "   .close                         -  Close current bot channel.\n\n" +
                "   .exit                          -  Close Application.\n\n" +
                "   .add <username>                -  Add a bot to automatically connect \n" +
                "                                     to the server.\n\n" +
                "   .proxy <type> <address> <port> -  Set proxy to use to connect to the server.\n" +
                "                                     Types: HTTP, SOCKS4, SOCKS5.\n" +
                "                                     Can be used to bypass AntiVPNs if the server's\n" +
                "                                     vpn database does not contain the used proxy.\n\n" +
                "   .<id>                          -  Switch channel to the given ID.\n\n" +
                "Note: Anything else you type in the console \n" +
                "      is sent by the bot as a chat message.\n";
    }
}
