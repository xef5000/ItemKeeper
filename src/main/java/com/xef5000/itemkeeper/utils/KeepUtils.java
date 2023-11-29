package com.xef5000.itemkeeper.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;
import org.bukkit.inventory.ItemStack;

public class KeepUtils {

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
        return itemStack;
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
