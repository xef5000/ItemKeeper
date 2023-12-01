package com.xef5000.itemkeeper;

import com.xef5000.itemkeeper.commands.KeepCommand;
import com.xef5000.itemkeeper.commands.ReloadCommand;
import com.xef5000.itemkeeper.listeners.PlayerDeathListener;
import com.xef5000.itemkeeper.utils.KeepUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemKeeper extends JavaPlugin {

    public Config config;

    @Override
    public void onEnable() {
        // COMMANDS
        getCommand("keep").setExecutor(new KeepCommand(this));
        getCommand("itemkeeper").setExecutor(new ReloadCommand(this));

        // LISTENERS
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        // Plugin startup logic

        config = new Config(this);
        config.init();

        KeepUtils.setPlugin(this);

        getLogger().info("ItemKeeper has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ItemKeeper has been disabled!");
    }
}
