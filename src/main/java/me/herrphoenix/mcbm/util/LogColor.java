package me.herrphoenix.mcbm.util;

/**
 * <h3>Minecraft Bots Manager</h3>
 * LogColor
 *
 * @author HerrPhoenix
 */
public class LogColor {
    // these are not compatible with the windows command prompt
    private static final boolean compatible = !System.getProperty("os.name").startsWith("Windows");

    // Reset
    public static final String RESET = !compatible ? "" : ("\u001B[0m");  // Text Reset

    // Regular Colors
    public static final String BLACK = !compatible ? "" : ("\001[30m");   // BLACK
    public static final String RED = !compatible ? "" : ("\u001B[31m");     // RED
    public static final String GREEN = !compatible ? "" : ("\u001B[32m");   // GREEN
    public static final String YELLOW = !compatible ? "" : ("\001[33m");  // YELLOW
    public static final String BLUE = !compatible ? "" : ("\033[34m");    // BLUE
    public static final String PURPLE = !compatible ? "" : ("\033[35m");  // PURPLE
    public static final String CYAN = !compatible ? "" : ("\033[36m");    // CYAN
    public static final String WHITE = !compatible ? "" : ("\u001B[37m");   // WHITE

    // Bold
    public static final String BLACK_BOLD = !compatible ? "" : ("\033[30m");  // BLACK
    public static final String RED_BOLD = !compatible ? "" : ("\033[31m");    // RED
    public static final String GREEN_BOLD = !compatible ? "" : ("\033[32m");  // GREEN
    public static final String YELLOW_BOLD = !compatible ? "" : ("\033[33m"); // YELLOW
    public static final String BLUE_BOLD = !compatible ? "" : ("\033[1;34m");   // BLUE
    public static final String PURPLE_BOLD = !compatible ? "" : ("\033[1;35m"); // PURPLE
    public static final String CYAN_BOLD = !compatible ? "" : ("\033[1;36m");   // CYAN
    public static final String WHITE_BOLD = !compatible ? "" : ("\033[1;37m");  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = !compatible ? "" : ("\033[4;30m");  // BLACK
    public static final String RED_UNDERLINED = !compatible ? "" : ("\033[4;31m");    // RED
    public static final String GREEN_UNDERLINED = !compatible ? "" : ("\033[4;32m");  // GREEN
    public static final String YELLOW_UNDERLINED = !compatible ? "" : ("\033[4;33m"); // YELLOW
    public static final String BLUE_UNDERLINED = !compatible ? "" : ("\033[4;34m");   // BLUE
    public static final String PURPLE_UNDERLINED = !compatible ? "" : ("\033[4;35m"); // PURPLE
    public static final String CYAN_UNDERLINED = !compatible ? "" : ("\033[4;36m");   // CYAN
    public static final String WHITE_UNDERLINED = !compatible ? "" : ("\033[4;37m");  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = !compatible ? "" : ("\033[40m");  // BLACK
    public static final String RED_BACKGROUND = !compatible ? "" : ("\033[41m");    // RED
    public static final String GREEN_BACKGROUND = !compatible ? "" : ("\033[42m");  // GREEN
    public static final String YELLOW_BACKGROUND = !compatible ? "" : ("\033[43m"); // YELLOW
    public static final String BLUE_BACKGROUND = !compatible ? "" : ("\033[44m");   // BLUE
    public static final String PURPLE_BACKGROUND = !compatible ? "" : ("\033[45m"); // PURPLE
    public static final String CYAN_BACKGROUND = !compatible ? "" : ("\033[46m");   // CYAN
    public static final String WHITE_BACKGROUND = !compatible ? "" : ("\033[47m");  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = !compatible ? "" : ("\033[90m");  // BLACK
    public static final String RED_BRIGHT = !compatible ? "" : ("\u001B[91m");    // RED
    public static final String GREEN_BRIGHT = !compatible ? "" : ("\u001B[92m");  // GREEN
    public static final String YELLOW_BRIGHT = !compatible ? "" : ("\u001B[93m"); // YELLOW
    public static final String BLUE_BRIGHT = !compatible ? "" : ("\u001B[94m");   // BLUE
    public static final String PURPLE_BRIGHT = !compatible ? "" : ("\u001B[95m"); // PURPLE
    public static final String CYAN_BRIGHT = !compatible ? "" : ("\u001B[96m");   // CYAN
    public static final String WHITE_BRIGHT = !compatible ? "" : ("\u001B[97m");  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = !compatible ? "" : ("\033[90m"); // BLACK
    public static final String RED_BOLD_BRIGHT = !compatible ? "" : ("\033[91m");   // RED
    public static final String GREEN_BOLD_BRIGHT = !compatible ? "" : ("\033[92m"); // GREEN
    public static final String YELLOW_BOLD_BRIGHT = !compatible ? "" : ("\033[93m");// YELLOW
    public static final String BLUE_BOLD_BRIGHT = !compatible ? "" : ("\033[94m");  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = !compatible ? "" : ("\u001B[95m");// PURPLE
    public static final String CYAN_BOLD_BRIGHT = !compatible ? "" : ("\u001B[96m");  // CYAN
    public static final String WHITE_BOLD_BRIGHT = !compatible ? "" : ("\u001B[97m"); // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;100m");// BLACK
    public static final String RED_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;101m");// RED
    public static final String GREEN_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;102m");// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;103m");// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;104m");// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;105m"); // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;106m");  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = !compatible ? "" : ("\033[0;107m");   // WHITE
}
