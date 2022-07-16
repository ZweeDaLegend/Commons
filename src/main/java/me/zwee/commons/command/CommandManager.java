package me.zwee.commons.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {

    private CommandMap cmdMap;

    private static final List<BaseCommand> commands = new ArrayList<>();

    public static void AddBaseCommand(BaseCommand command) {
        commands.add(command);
    }

    public void SetupCommands(JavaPlugin plugin) {
        for (BaseCommand command : commands) {

            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommandMap.setAccessible(true);

                this.cmdMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ignored) {
            }

            this.cmdMap.register(command.getName(), command);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        ArrayList<String> subCommands = new ArrayList<>();

        for (Command main : commands) {
            if (command.getLabel().equals(main.getName())) {
                if (args.length != 1) {
                    return null;
                }
            }
        }
        return subCommands;
    }

    public static void unregisterCommand(String label) {

        try {
            SimplePluginManager spm = (SimplePluginManager) Bukkit.getServer()
                    .getPluginManager();

            Field f = SimplePluginManager.class.getDeclaredField("commandMap");
            f.setAccessible(true);

            SimpleCommandMap scm = (SimpleCommandMap) f.get(spm);
            Collection<Command> coll = scm.getCommands();
            Map<String, Command> map = new HashMap<>();

            for (Command c : coll) {
                if (!c.getName().equals(label)) {
                    map.put(c.getName(), c);
                }
            }
            scm.clearCommands();
            map.keySet().forEach(key -> scm.register(key, map.get(key)));

            f.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void RemoveAlias(String command, String aliasToRemove) {
        try {
            SimplePluginManager spm = (SimplePluginManager) Bukkit.getServer()
                    .getPluginManager();

            Field f = SimplePluginManager.class.getDeclaredField("commandMap");
            f.setAccessible(true);

            SimpleCommandMap scm = (SimpleCommandMap) f.get(spm);

            Collection<Command> coll = scm.getCommands();
            Map<String, Command> map = new HashMap<>();

            for (Command c : coll) {

                if (c.getName().equals(command)) {
                    List<String> aliases = c.getAliases();
                    aliases.remove(aliasToRemove);
                    c.setAliases(aliases);
                }
                map.put(c.getName(), c);
            }
            scm.clearCommands();
            map.keySet().forEach(key -> scm.register(key, map.get(key)));
            f.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
