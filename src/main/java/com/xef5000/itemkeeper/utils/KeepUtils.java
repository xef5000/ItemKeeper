package com.xef5000.itemkeeper.utils;

import com.xef5000.itemkeeper.ItemKeeper;
import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KeepUtils {

    private static ItemKeeper plugin;

    public static void setPlugin(ItemKeeper plugin) {
        KeepUtils.plugin = plugin;
    }

    public static ItemStack makeItemKeepable(ItemStack itemStack, boolean value) {
        if (value) {
            NBT.modify(itemStack, nbt -> {
                nbt.setBoolean("keep", value);
            });
        } else {
            NBT.modify(itemStack, nbt -> {
                nbt.removeKey("keep");
            });
        }

        return changeLore(changeItemName(itemStack, value), value);
    }

    private static ItemStack changeItemName(ItemStack itemStack, boolean value) {
        boolean prefix = plugin.getConfig().getBoolean("item.name-prefix");
        boolean suffix = plugin.getConfig().getBoolean("item.name-suffix");
        if (!prefix && !suffix) return itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        String displayName = (meta.hasDisplayName()) ? meta.getDisplayName() : WordUtils.capitalizeFully(itemStack.getType().name().replace("_", " "));
        if ((suffix && !prefix) && !displayName.startsWith("§r")) displayName = "§r"+displayName;
        if (prefix && displayName.startsWith("§r")) displayName = displayName.substring(2);

        if (suffix) {
            String suffixStringOn = plugin.getConfig().getString("item.suffix-on").replace("&", "§");
            String suffixStringOff = plugin.getConfig().getString("item.suffix-off").replace("&", "§");

            if (value) {  // When toggling to "ON"
                if (displayName.endsWith(suffixStringOff)) {
                    displayName = displayName.substring(0, displayName.length() - suffixStringOff.length()) + suffixStringOn;
                } else {
                    displayName = displayName + suffixStringOn;
                }
            } else {  // When toggling to "OFF"
                if (displayName.endsWith(suffixStringOn)) {
                    displayName = displayName.substring(0, displayName.length() - suffixStringOn.length()) + suffixStringOff;
                }
            }
        }

        if (prefix) {
            String prefixStringOn = plugin.getConfig().getString("item.prefix-on").replace("&", "§");
            String prefixStringOff = plugin.getConfig().getString("item.prefix-off").replace("&", "§");

            if (value) { // When toggling to "ON"
                if (displayName.startsWith(prefixStringOff)) {
                    displayName = prefixStringOn + displayName.substring(prefixStringOff.length());
                } else {
                    displayName = prefixStringOn + displayName;
                }
            } else { // When toggling to "OFF"
                if (displayName.startsWith(prefixStringOn)) {
                    displayName = prefixStringOff + displayName.substring(prefixStringOn.length());
                }
            }
        }

        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack changeLore(ItemStack itemStack, boolean value) {
        if (!(plugin.getConfig().getBoolean("item.lore"))) return itemStack;

        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

        String newLastLore = "";
        String lastLore = getLastLoreNBT(itemStack);

        String loreStringOn = plugin.getConfig().getString("item.lore-on").replace("&", "§");
        String loreStringOff = plugin.getConfig().getString("item.lore-off").replace("&", "§");

        if (lastLore != null && !lore.isEmpty()) {
            lore.remove(lastLore);
        }

        if (value) { // When toggling to "ON"
            if (!lore.isEmpty() && lore.get(0).equals(loreStringOff)) {
                lore.remove(0);
            }
            if (!loreStringOn.isEmpty()) {
                lore.add(0, loreStringOn);
                newLastLore = loreStringOn;
            }
        } else { // When toggling to "OFF"
            if (!lore.isEmpty() && lore.get(0).equals(loreStringOn)) {
                lore.remove(0);
            }
            if (!loreStringOff.isEmpty()) {
                lore.add(0, loreStringOff);
                newLastLore = loreStringOff;
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return setLastLoreNBT(itemStack, newLastLore);
    }

    private static ItemStack setLastLoreNBT(ItemStack itemStack, String lastLore) {
        ItemStack newStack = itemStack.clone();
        NBT.modify(newStack, nbt -> {
            nbt.setString("itemkeeper_lastLore", lastLore);
        });
        return newStack;
    }

    private static String getLastLoreNBT(ItemStack itemStack) {
        ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
        ReadWriteNBT tags = nbt.getCompound("tag");
        if (tags != null) {
            if (tags.hasTag("itemkeeper_lastLore"))
                return tags.getString("itemkeeper_lastLore");
        } else {
            if (nbt.hasTag("itemkeeper_lastLore"))
                return nbt.getString("itemkeeper_lastLore");
        }
        return null;
    }

    public static boolean isItemKeepable(ItemStack itemStack) {
        ReadWriteNBT nbt = NBT.itemStackToNBT(itemStack);
        ReadWriteNBT tags = nbt.getCompound("tag");
        if (tags != null) {
            return tags.hasTag("keep");
        } else {
            return nbt.hasTag("keep");
        }

    }
}
