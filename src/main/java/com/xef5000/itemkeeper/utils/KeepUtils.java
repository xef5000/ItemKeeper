package com.xef5000.itemkeeper.utils;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class KeepUtils {

    public static ItemStack makeItemKeepable(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsItem.getTag();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        // Add your custom tag
        tagCompound.setBoolean("itemkeeper_keep", true);

        // Set the modified tag back to the item
        nmsItem.setTag(tagCompound);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static ItemStack makeItemNotKeepable(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsItem.getTag();

        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
        }

        // Add your custom tag
        tagCompound.setBoolean("itemkeeper_keep", false);

        // Set the modified tag back to the item
        nmsItem.setTag(tagCompound);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean isItemKeepable(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nmsItem.getTag();

        if (tagCompound == null)
            return false;

        return tagCompound.getBoolean("itemkeeper_keep");
    }
}
