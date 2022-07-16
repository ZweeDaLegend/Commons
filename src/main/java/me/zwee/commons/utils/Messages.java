package me.zwee.commons.utils;

import me.zwee.commons.config.ConfigManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {

    private static final ConfigManager.Config messageConfig = ConfigManager.getFile("messages");

    private static Map<String, String> messages = new HashMap<String, String>() {{
        for (String messageName : messageConfig.getConfig().getConfigurationSection("Messages").getKeys(false)) {
            put(messageName, messageConfig.getConfig().getString("Messages." + messageName));
        }
    }};

    private static Map<String, List<String>> multilineMessages = new HashMap<String, List<String>>() {{
        if (messageConfig.getConfig().getConfigurationSection("MultilineMessages") != null) {
            for (String messageName : messageConfig.getConfig().getConfigurationSection("MultilineMessages").getKeys(false)) {
                put(messageName, messageConfig.getConfig().getStringList("MultilineMessages." + messageName));
            }
        }
    }};

    public void reloadMessages() {
        if (messageConfig.getConfig().getConfigurationSection("Messages") != null) {
            messages = new HashMap<String, String>() {{
                for (String messageName : messageConfig.getConfig().getConfigurationSection("Messages").getKeys(false)) {
                    put(messageName, messageConfig.getConfig().getString("Messages." + messageName));
                }
            }};
            if (messageConfig.getConfig().getConfigurationSection("MultilineMessages") != null) {
                multilineMessages = new HashMap<String, List<String>>() {{
                    for (String messageName : messageConfig.getConfig().getConfigurationSection("MultilineMessages").getKeys(false)) {
                        put(messageName, messageConfig.getConfig().getStringList("MultilineMessages." + messageName));
                    }
                }};
            }
        }
    }

    public static String getMessage(String messageName) {
        if (messages.get(messageName) == null) {
            System.out.print("Missing message in message.yml");
            return "";
        }
        return Colour.colour(messages.get(messageName));
    }


    public void reloadMultilineMessages() {
        multilineMessages = new HashMap<String, List<String>>() {{
            for (String messageName : messageConfig.getConfig().getConfigurationSection("Messages").getKeys(false)) {
                put(messageName, messageConfig.getConfig().getStringList("Messages." + messageName));
            }
        }};
    }

    public static List<String> getMultilineMessage(String messageName) {
        if (multilineMessages.get(messageName) == null) {
            System.out.print("Missing message in message.yml");
            return new ArrayList<>();
        }
        return Colour.colourAll(multilineMessages.get(messageName));
    }


}
