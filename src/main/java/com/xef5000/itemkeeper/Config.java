package com.xef5000.itemkeeper;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private FileConfiguration config;
    private final ItemKeeper instance;

    public Config(ItemKeeper instance) {
        this.instance = instance;
    }

    public void init() {
        instance.saveDefaultConfig();
        config = instance.getConfig();
    }

    public FileConfiguration getPluginConfig() {
        return config;
    }

}
