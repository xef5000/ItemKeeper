package com.xef5000.itemkeeper.commands;

import com.xef5000.itemkeeper.utils.KeepUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeepCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(" §cYou must be a player to run this command!");
            return false;
        }

        if (!(commandSender.hasPermission("itemkeeper.keep"))) return false;



        Player player = (Player) commandSender;
        ItemStack itemStack = player.getItemInHand();


        ItemStack newItem;
        if (KeepUtils.isItemKeepable(itemStack)) {
            newItem = KeepUtils.makeItemNotKeepable(itemStack);
            player.sendMessage("§cYour Item will no longer kept with you at all times!");
        } else {
            newItem = KeepUtils.makeItemKeepable(itemStack);
            player.sendMessage("§aYour Item will now kept with you at all times!");
        }

        player.setItemInHand(newItem);




        return true;
    }


}
