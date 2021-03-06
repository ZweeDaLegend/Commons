package me.zwee.commons.command;

import com.sun.istack.internal.Nullable;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import me.zwee.commons.utils.Colour;
import me.zwee.commons.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCommand extends Command {


    public BaseCommand(String commandName) {
        super(commandName);
        this.setPermission(permission());
        this.setAliases(new ArrayList<>(Arrays.asList(aliases())));
        this.description = setDescription();
    }

    public abstract void onCommand(CommandSender sender, String[] args);


    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if(getPermission() == null || !(sender instanceof Player) || sender.hasPermission(getPermission())) {
            if (args.length == 0) {
                    onCommand(sender, args);
                    return true;
                }
            SubCommand subCommand = get(args[0]);
            if (subCommand == null) {
                return true;
            }
            ArrayList<String> a = new ArrayList<>(Arrays.asList(args));
            a.remove(0);
            args = a.toArray(new String[0]);
                if(subCommand.permission() == null || !(sender instanceof Player) || sender.hasPermission(subCommand.permission())){
                    subCommand.onCommand(sender, args);
                    return true;
                }
            }
            sender.sendMessage(Messages.getMessage("NO_PERMISSION").equals("")
                    ? Colour.colour("&4You do not have permission to use that command!") : Messages.getMessage("NO_PERMISSION"));
        return true;
    }


    private SubCommand get(String subcommandName) {

        for (SubCommand cmd : subCommandList()) {
            String cmdName = cmd.name();
            if (cmdName.equalsIgnoreCase(subcommandName)) {
                return cmd;
            }
            String[] args = cmd.aliases();

            for (String alias : args) {
                if (subcommandName.equalsIgnoreCase(alias)) {
                    return cmd;
                }
            }
        }
        return null;
    }


    public abstract List<SubCommand> subCommandList();

    public abstract String setDescription();

    public abstract String[] aliases();

    public abstract String permission();
}
