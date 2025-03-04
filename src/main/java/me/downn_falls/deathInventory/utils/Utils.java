package me.downn_falls.deathInventory.utils;

import me.downn_falls.deathInventory.DeathInventory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String colorize(String s) {
        if (s == null || s.isEmpty())
            return "";
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            String hexColor = s.substring(matcher.start(), matcher.end());
            s = s.replace(hexColor, net.md_5.bungee.api.ChatColor.of(hexColor.substring(1)).toString());
            matcher = pattern.matcher(s);
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getColoredMessage(String path, boolean prefix) {
        return colorize((prefix ? DeathInventory.getInstance().getConfig().getString("message.prefix") + " " : "") + DeathInventory.getInstance().getConfig().getString("message."+path));
    }

    public static String getMessage(String path, boolean prefix) {
        return (prefix ? DeathInventory.getInstance().getConfig().getString("message.prefix") + " " : "") + DeathInventory.getInstance().getConfig().getString("message."+path);
    }

    public static String getDateFormatted(long timestamp, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                .withZone(ZoneId.systemDefault());

        return formatter.format(Instant.ofEpochMilli(timestamp));
    }
}
