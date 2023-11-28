package com.xef5000.itemkeeper.commands;

import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import com.xef5000.itemkeeper.utils.KeepUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

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

        XMaterial material = XMaterial.matchXMaterial(itemStack);
        Map<String, Object> newstack = XItemStack.serialize(player.getItemInHand());

        System.out.println("Newstack: "+newstack);
        System.out.println("Newstackitem: " +XItemStack.deserialize(newstack));
        System.out.println("comparison with old: "+itemStack);


        ItemStack newItem;
        if (KeepUtils.isItemKeepable(itemStack)) {
            newItem = KeepUtils.makeItemKeepable(itemStack, false);
            player.sendMessage("§cYour Item will no longer kept with you at all times!");
        } else {
            newItem = KeepUtils.makeItemKeepable(itemStack, true);
            player.sendMessage("§aYour Item will now kept with you at all times!");
        }

        player.setItemInHand(newItem);




        return true;
    }


}
