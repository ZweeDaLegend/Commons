package me.zwee.commons.config;

import me.zwee.commons.utils.Colour;
import me.zwee.commons.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ConfigManager {

    private final static Map<String, Config> configs = new HashMap<>();

//    private final static Plugin p = Commons.getPlugin(Commons.class);
    private static JavaPlugin p;

    public ConfigManager(JavaPlugin plugin) {
        p = plugin;
    }

    public static Config getFile(String configFileName) { return configs.get(configFileName); }

    public void RegisterConfigs(List<String> configFiles){
            if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
            }

            for(String string : configFiles){ configs.put(string, new Config(string)); }
    }


    public static void ReloadConfigs(){
        p.reloadConfig();
        for(Config config : ConfigManager.configs.values()){
            config.reloadConfig();
            if(config.fileName.equals("messages")){
                new Messages().reloadMessages();
            }
        }
    }
    

    public static void loadRegularConfig(){
        p.getConfig().options().copyDefaults(true);
        p.saveDefaultConfig();
//        p.saveConfig();
    }


    public static class Config {
        private FileConfiguration config;
        private final File configFile;
        private String fileName = "";

        public Config(String name) {
            fileName = name.split("/")[name.split("/").length-1];
            if(name.contains("/")){
                configFile = new File(p.getDataFolder()+"/"+name.substring(0, name.length()- fileName.length()), fileName+".yml");
            }else{
                configFile = new File(p.getDataFolder(), fileName+".yml");
            }

            if(!configFile.exists()){
                try {
                    config = YamlConfiguration.loadConfiguration(new InputStreamReader(p.getResource(name+".yml")));

                    configFile.createNewFile();
                    p.saveResource(fileName+".yml", true);

                    Bukkit.getServer().getConsoleSender()
                            .sendMessage(Colour.colour("&2The "+fileName+".yml file has been created"));
                } catch (IOException e) {
                    Bukkit.getServer().getConsoleSender().sendMessage(Colour.colour("&cCould not create the "+fileName+".yml file"+"\n"+e));
                }
                }else{
                    config = YamlConfiguration.loadConfiguration(configFile);
                }
            saveConfig();
        }

        public FileConfiguration getConfig() {
            return config;
        }

        public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Colour.colour("&4"+e));
        }
    }
    public void reloadConfig(){
            config = YamlConfiguration.loadConfiguration(configFile);
    }


    }
}
