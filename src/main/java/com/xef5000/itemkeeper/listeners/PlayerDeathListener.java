package com.xef5000.itemkeeper.listeners;

import com.xef5000.itemkeeper.utils.KeepUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDeathListener implements Listener {

    private final Map<Player, Map<Integer, ItemStack>> keptItemsMap = new HashMap<>();


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Create a separate list to avoid concurrent modification issues
        List<ItemStack> itemsToRemove = new ArrayList<>();

        // Check each item and keep it if necessary
        for (ItemStack item : event.getDrops()) {
            if (KeepUtils.isItemKeepable(item)) {
                itemsToRemove.add(item);
                keptItemsMap.computeIfAbsent(player, k -> new HashMap<>()).put(item.hashCode(), item);
            }
        }

        // Remove the items after iterating
        event.getDrops().removeAll(itemsToRemove);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Give back kept items and clear the map for the player
        Map<Integer, ItemStack> keptItems = keptItemsMap.remove(player);
        if (keptItems != null) {
            for (ItemStack item : keptItems.values()) {
                player.getInventory().addItem(item);
            }
        }
    }
}
