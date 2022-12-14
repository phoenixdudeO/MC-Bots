package me.herrphoenix.mcbm.bot;

import com.github.steveice10.packetlib.ProxyInfo;
import me.herrphoenix.mcbm.util.TextUtils;
import me.herrphoenix.mcbm.util.LogColor;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Minecraft Bots Manager</h3>
 * BotClientManager
 *
 * @author HerrPhoenix
 */
public class BotClientManager {
    private static List<BotClient> bots = new ArrayList<>();
    private static ProxyInfo proxy;

    /**
     * Creates a new Bot and registers it.
     *
     * @param username The username of the bot
     * @param host The server ip the bot will connect to
     * @param port The server port the bot will connect to
     * @return A Minecraft Bot
     */
    public static BotClient createBot(String username, String host, int port) {
        BotClient bot = new BotClient(bots.size() + 1, username, host, port);
        bots.add(bot);

        return bot;
    }

    public static void setProxy(ProxyInfo.Type proxyType, String host, int port) {
        proxy = new ProxyInfo(proxyType, new InetSocketAddress(host, port));
    }

    /**
     * Gets a bot, given an ID.
     *
     * @param id The ID to which the bot is bound to
     * @return The bot bound to the given ID
     */
    public static BotClient getBot(int id) {
        for (BotClient bot : bots) {
            if (bot.getId() == id) {
                return bot;
            }
        }
        return null;
    }

    public static ProxyInfo getProxy() {
        return proxy;
    }

    /**
     * Returns an ArrayList containing all registered bots.
     *
     * @return ArraList with all registered bots
     */
    public static List<BotClient> getBots() {
        return bots;
    }
}
