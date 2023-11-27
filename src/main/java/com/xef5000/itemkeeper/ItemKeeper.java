package com.xef5000.itemkeeper;

import com.xef5000.itemkeeper.commands.KeepCommand;
import com.xef5000.itemkeeper.listeners.PlayerDeathListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemKeeper extends JavaPlugin {

    @Override
    public void onEnable() {
        // COMMANDS
        getCommand("keep").setExecutor(new KeepCommand());

        // LISTENERS
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        // Plugin startup logic
        getLogger().info("ItemKeeper has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ItemKeeper has been disabled!");
    }
}
