package com.xef5000.itemkeeper.commands;

import com.xef5000.itemkeeper.ItemKeeper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final ItemKeeper plugin;

    public ReloadCommand(ItemKeeper plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ("reload".equalsIgnoreCase(args[0]) && sender.hasPermission("itemkeeper.reload")) {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getConfig().getString("messages.reload").replace("&", "§"));
            return true;
        }
        return false;
    }
}
