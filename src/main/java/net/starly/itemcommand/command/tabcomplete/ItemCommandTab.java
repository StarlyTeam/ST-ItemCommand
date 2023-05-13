package net.starly.itemcommand.command.tabcomplete;

import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.*;

public class ItemCommandTab implements TabCompleter {

    private final Server server;

    public ItemCommandTab(Server server) {
        this.server = server;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (Plugin plugin : server.getPluginManager().getPlugins()) completions.addAll(plugin.getDescription().getCommands().keySet());
            return completions;
        } else {
            PluginCommand command = server.getPluginCommand(args[0]);
            if (command == null) return null;
            else {
                TabCompleter tabCompleter = command.getTabCompleter();
                if (tabCompleter == null) return null;
                else return tabCompleter.onTabComplete(sender, command, args[0], Arrays.copyOfRange(args, 1, args.length));
            }
        }
    }
}
