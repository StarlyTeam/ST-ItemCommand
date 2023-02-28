package net.starly.itemcommand.command;

import net.starly.core.jb.version.nms.tank.NmsItemStackUtil;
import net.starly.core.jb.version.nms.wrapper.ItemStackWrapper;
import net.starly.core.jb.version.nms.wrapper.NBTTagCompoundWrapper;
import net.starly.itemcommand.context.MessageContent;
import net.starly.itemcommand.data.ItemCommandData;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemCommandCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.ERROR, "noConsole"));
            return false;
        }

        if (!player.hasPermission("staly.itemcommand.create")) {
            player.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.ERROR, "noPermission"));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.ERROR, "noCommandName"));
            return false;
        }

        String cmd = String.join(" ", args);
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            player.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.ERROR, "noItemInHand"));
            return false;
        }

        ItemStackWrapper itemStackWrapper = NmsItemStackUtil.getInstance().asNMSCopy(itemStack);
        NBTTagCompoundWrapper nbtTagCompoundWrapper = itemStackWrapper.getTag();

        if (nbtTagCompoundWrapper == null) nbtTagCompoundWrapper = NmsItemStackUtil.getInstance().getNbtCompoundUtil().newInstance();

        ItemCommandData itemCommandData = nbtTagCompoundWrapper.getObject(ItemCommandData.class);
        if (itemCommandData == null) itemCommandData = new ItemCommandData(new ArrayList<>());
        itemCommandData.setItemCommand(cmd);

        nbtTagCompoundWrapper.setObject(itemCommandData, ItemCommandData.class);
        itemStackWrapper.setTag(nbtTagCompoundWrapper);
        itemStack.setItemMeta(NmsItemStackUtil.getInstance().asBukkitCopy(itemStackWrapper).getItemMeta());
        player.sendMessage(MessageContent.getMessageContent().getMessageAfterPrefix(MessageContent.MessageType.NORMAL, "createItemCommand")
                .replace("{command}", cmd));
        return true;
    }
}
