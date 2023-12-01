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


        if (!(commandSender.hasPermission("itemkeeper.keep"))) {
            commandSender.sendMessage(plugin.getConfig().getString("messages.permissionDenied").replace("&", "§"));
            return false;
        }

        if (strings.length > 0) {
            // If username is provided, target the specified player
            Player targetPlayer = plugin.getServer().getPlayer(strings[0]);
            if (targetPlayer == null || !targetPlayer.isOnline()) {
                commandSender.sendMessage(plugin.getConfig().getString("messages.playerNotFound").replace("&", "§"));
                return false;
            }

            ItemStack itemStack = targetPlayer.getItemInHand();
            ItemStack newItem;

            if (KeepUtils.isItemKeepable(itemStack)) {
                newItem = KeepUtils.makeItemKeepable(itemStack, false);
                targetPlayer.sendMessage(plugin.getConfig().getString("messages.itemNotKeepable").replace("&", "§"));
            } else {
                newItem = KeepUtils.makeItemKeepable(itemStack, true);
                targetPlayer.sendMessage(plugin.getConfig().getString("messages.itemKeepable").replace("&", "§"));
            }

            targetPlayer.setItemInHand(newItem);
            return true;
        } else if (commandSender instanceof Player) {
            // If no username provided, use the command sender's player
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
        } else {
            // If no username provided and not a player, show error message
            commandSender.sendMessage(plugin.getConfig().getString("messages.playerOnly").replace("&", "§"));
            return false;
        }
    }


}
