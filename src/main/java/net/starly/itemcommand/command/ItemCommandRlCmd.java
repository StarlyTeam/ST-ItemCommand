package net.starly.itemcommand.command;

import net.starly.itemcommand.ItemCommandMain;
import net.starly.itemcommand.context.MessageContent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ItemCommandRlCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("starly.itemcommand.reload")) {
            sender.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.ERROR, "noPermission"));
            return false;
        }

        if (args.length == 0) {
            JavaPlugin plugin = ItemCommandMain.getInstance();

            if (!new File(plugin.getDataFolder(), "config.yml").exists()) plugin.saveDefaultConfig();
            plugin.reloadConfig();
            MessageContent.getMessageContent().initializing(plugin.getConfig());
            sender.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.NORMAL, "reloadConfig"));
            return true;
        }
        return false;
    }
}
