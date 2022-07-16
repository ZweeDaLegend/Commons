package me.zwee.commons.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Colour {

    public static String colour(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colourAll(List<String> sl){
        return new ArrayList<String>(){{sl.forEach(line -> add(Colour.colour(line)));}};

    }

}
