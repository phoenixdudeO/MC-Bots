package me.herrphoenix.mcbm.bot;

import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundPlayerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import me.herrphoenix.mcbm.util.TextUtils;
import me.herrphoenix.mcbm.util.LogColor;

import java.net.Proxy;
import java.time.Instant;

/**
 * <h3>Minecraft Bots Manager</h3>
 * BotClient
 *
 * @author HerrPhoenix
 */
public class BotClient {
    private final int id;
    private final String username;
    private final String host;
    private final int port;

    private MinecraftProtocol protocol;
    private Session session;
    private String log;

    public BotClient(int id, String username, String host, int port) {
        this.id = id;
        this.username = username;
        this.host = host;
        this.port = port;

        System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.GREEN_BRIGHT +
                "[HANDLER] " + LogColor.WHITE_BRIGHT + "Created bot \"" + username + "\" on channel " + id);
    }

    /**
     * Creates the Minecraft Protocol for the bot's username, this is cracked.
     */
    public void initializeProtocol() {
        protocol = new MinecraftProtocol(getUsername());
    }

    /**
     * Initializes the TCP Client Session for the server and registers listeners.
     */
    public void createSession() {
        session = new TcpClientSession(getHost(), getPort(), protocol, null);

        session.addListener(new SessionAdapter() {
            @Override
            public void packetReceived(Session session, Packet packet) {
                if (packet instanceof ClientboundSystemChatPacket) {
                    ClientboundSystemChatPacket sysChatPacket = (ClientboundSystemChatPacket) packet;
                    log = LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.CYAN_BRIGHT +
                            "[CHAT_SYS" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id +
                            LogColor.CYAN_BRIGHT + "] " + LogColor.RESET +
                            TextUtils.parseComponent(sysChatPacket.getContent());
                } else if (packet instanceof ClientboundPlayerChatPacket) {
                    ClientboundPlayerChatPacket playerChatPacket = (ClientboundPlayerChatPacket) packet;
                    log = LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.CYAN_BRIGHT +
                            "[CHAT_USR" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id +
                            LogColor.CYAN_BRIGHT + "] " + LogColor.RESET +
                            TextUtils.parseComponent(playerChatPacket.getSignedContent());
                }
            }
        });

        session.addListener(new SessionAdapter() {
            @Override
            public void disconnected(DisconnectedEvent event) {
                System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.PURPLE_BRIGHT +
                        "[EVENT" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id +
                        LogColor.PURPLE_BRIGHT + "] " + LogColor.WHITE_BRIGHT +  "Disconnected: " +
                        LogColor.RESET + TextUtils.parseComponent(event.getReason()));

                if (!event.getReason().equals("Disconnected")) {
                    System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" +
                            LogColor.RED_BRIGHT + "[ERR" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT +
                            "ch-" + id + LogColor.RED_BRIGHT + "] " + LogColor.RED_BRIGHT +
                            "The connection to the server was abruptly interrupted. A re-connection " +
                            "attempt will be made soon");

                    try {
                        Thread.sleep(5000);
                        System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" +
                                LogColor.BLUE_BRIGHT + "[BOT" + LogColor.RED + "@" +
                                LogColor.YELLOW_BRIGHT + "ch-" + id + LogColor.BLUE_BRIGHT + "] " +
                                LogColor.WHITE_BRIGHT + "Attempting to reconnect...");
                        new Thread(() -> connect(true)).start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Disconnect the client (if connected) and creates a new session.
     */
    public void resetSession() {
        if (session != null && session.isConnected()) {
            disconnect("Disconnected");
        }

        System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.BLUE_BRIGHT +
                "[BOT" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id + LogColor.BLUE_BRIGHT +
                "] " + LogColor.WHITE_BRIGHT + "Session reset.");

        createSession();
    }

    /**
     * Disconnect the client for a given reason.
     *
     * @param reason Reason to disconnect the client for.
     */
    public void disconnect(String reason) {
        System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.BLUE_BRIGHT +
                "[BOT" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id + LogColor.BLUE_BRIGHT +
                "] " + LogColor.WHITE_BRIGHT + "Closing connection to server for \"" + reason + "\"");
        getSession().disconnect(reason);
    }

    /**
     * Connect to the server.
     *
     * @param reset Reset the current session, if there's any
     */
    public void connect(boolean reset) {
        if (reset) {
            resetSession();
        }

        System.out.println(LogColor.WHITE + "[" + TextUtils.getCurrentTime() + "]" + LogColor.BLUE_BRIGHT +
                "[BOT" + LogColor.RED + "@" + LogColor.YELLOW_BRIGHT + "ch-" + id + LogColor.BLUE_BRIGHT +
                "]" + LogColor.WHITE_BRIGHT + " Connecting to " + host + ":" + port + " with \"" + username + "\"");

        SessionService sessionService = new SessionService();
        sessionService.setProxy(Proxy.NO_PROXY);
        session.setFlag(MinecraftConstants.SESSION_SERVICE_KEY, sessionService);
        session.connect();
    }

    /**
     * Send a message from the client.
     *
     * @param message The message to send
     */
    public void sendMessage(String message) {
        session.send(new ServerboundChatPacket(message, Instant.now().toEpochMilli(), 0, new byte[0],
                false));
    }

    /**
     * Get latest received chat message.
     *
     * @return The latest chat message received by the client
     */
    public String getLatestLog() {
        return log;
    }

    /**
     * Resets the last chat message.
     */
    public void clearLog() {
        log = null;
    }

    /**
     * Gets the bot username.
     *
     * @return The bot username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the port that the bot will connect to.
     *
     * @return The server port to connect to
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the server ip that the bot will connect to.
     *
     * @return The server ip to connect to.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the TCP Client Session.
     *
     * @return The TCP Client Session.
     */
    public Session getSession() {
        return session;
    }

    /**
     * Gets the channel ID the bot is on.
     *
     * @return The channel ID the bot is on
     */
    public int getId() {
        return id;
    }
}
