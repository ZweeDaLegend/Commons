package me.zwee.commons.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarUtils {

    public static void SendActionBar(Player player,String message){

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Colour.colour(message)));
    }
}
