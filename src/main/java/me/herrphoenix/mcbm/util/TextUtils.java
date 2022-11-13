package me.herrphoenix.mcbm.util;

import net.kyori.adventure.text.Component;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>Minecraft Bots Manager</h3>
 * TextUtils
 *
 * @author HerrPhoenix
 */
public class TextUtils {
    private static final String COLOR_CODE_REGEX = "\\u00A7[0-9a-fk-or]";

    private static Pattern colorCodePattern;

    public static String parseComponent(Component component) {
        return parseComponent(component.toString());
    }

    /**
     * Gets text from a JSON Text Component.
     * There is probably a better way to do this.
     *
     * @param full Raw JSON String
     * @return Text contained in the JSON component.
     */
    public static String parseComponent(String full) {
        String[] implementations = full.split("TextComponentImpl");

        StringBuilder rawMessage = new StringBuilder();

        for (String impl : implementations) {
            String[] contents = impl.split("\", ");
            String message = contents[0].replace("{content=\"", "");
            rawMessage.append(message);
        }

        return stripColors(rawMessage.toString());
    }

    /**
     * Removes color codes from a String using RegEx.
     *
     * @param colored The String containing color codes
     * @return The String with no color codes
     */
    public static String stripColors(String colored) {
        if (colorCodePattern == null) {
            colorCodePattern = Pattern.compile(COLOR_CODE_REGEX);
        }

        Matcher match = colorCodePattern.matcher(colored);
        return match.replaceAll("");
    }

    /**
     * Gets the current time in a String using the hh:mm:ss format used in log messages.
     *
     * @return Current time in a String
     */
    public static String getCurrentTime() {
        Date date = new Date();
        StringBuilder timeString = new StringBuilder();

        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();

        timeString.append(hours < 10 ? "0" + hours : hours)
                .append(":").append(minutes < 10 ? "0" + minutes : minutes)
                .append(":").append(seconds < 10 ? "0" + seconds : seconds);

        return timeString.toString();
    }
}
