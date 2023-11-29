package com.xef5000.itemkeeper.commands;

import com.xef5000.itemkeeper.ItemKeeper;
import com.xef5000.itemkeeper.utils.KeepUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeepCommand implements CommandExecutor {

    private final ItemKeeper plugin;

    public KeepCommand(ItemKeeper plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(plugin.getConfig().getString("messages.playerOnly").replace("&", "§"));
            return false;
        }

        if (!(commandSender.hasPermission("itemkeeper.keep"))) {
            commandSender.sendMessage(plugin.getConfig().getString("messages.permissionDenied").replace("&", "§"));
            return false;
        }

        Player player = (Player) commandSender;
        ItemStack itemStack = player.getItemInHand();

        ItemStack newItem;
        if (KeepUtils.isItemKeepable(itemStack)) {
            newItem = KeepUtils.makeItemKeepable(itemStack, false);
            player.sendMessage(plugin.getConfig().getString("messages.itemNotKeepable").replace("&", "§"));
        } else {
            newItem = KeepUtils.makeItemKeepable(itemStack, true);
            player.sendMessage(plugin.getConfig().getString("messages.itemKeepable").replace("&", "§"));
        }

        player.setItemInHand(newItem);

        return true;
    }


}
