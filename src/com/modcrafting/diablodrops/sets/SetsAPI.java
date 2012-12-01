package com.modcrafting.diablodrops.sets;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.drops.DropUtils;

public class SetsAPI
{
    private final Random gen;
    private final DropUtils drops;
    private final DiabloDrops plugin;

    public SetsAPI(DiabloDrops instance)
    {
        plugin = instance;
        drops = instance.drop;
        gen = plugin.gen;
    }

    /**
     * Gets the armor set represented by name
     * 
     * @param name
     *            of set
     * @return armor set
     */
    public ArmorSet getArmorSet(String name)
    {
        for (ArmorSet as : plugin.armorSets)
        {
            if (as.getName().equalsIgnoreCase(name))
                return as;
        }
        return null;
    }

    public DropUtils getDrops()
    {
        return drops;
    }

    public Random getGen()
    {
        return gen;
    }

    /**
     * Gets the name of the set a player could be wearing
     * 
     * @param Player
     *            to check
     * @return name of the set
     */
    public String getNameOfSet(Player player)
    {
        ItemStack his = player.getInventory().getHelmet();
        if (his == null)
            return null;
        String potentialSet = new String();
        CraftItemStack chis = ((CraftItemStack) his);
        net.minecraft.server.ItemStack mistack = chis.getHandle();
        NBTTagCompound tag = mistack.tag;
        if (tag == null)
        {
            tag = new NBTTagCompound();
            tag.setCompound("display", new NBTTagCompound());
            mistack.tag = tag;
        }
        String[] ss = ChatColor.stripColor(tag.getString("Name")).split(" ");
        potentialSet = ss[0];
        return potentialSet;
    }

    public DiabloDrops getPlugin()
    {
        return plugin;
    }

    /**
     * Is player wearing a set of matching armor?
     * 
     * @param player
     * @return is set
     */
    public boolean wearingSet(Player player)
    {
        ItemStack his = player.getInventory().getHelmet();
        ItemStack cis = player.getInventory().getChestplate();
        ItemStack lis = player.getInventory().getLeggings();
        ItemStack bis = player.getInventory().getBoots();
        if (his == null || cis == null || lis == null || bis == null)
            return false;
        Set<ItemStack> sis = new HashSet<ItemStack>();
        sis.add(cis);
        sis.add(lis);
        sis.add(bis);
        String potentialSet = new String();
        CraftItemStack chis = ((CraftItemStack) his);
        net.minecraft.server.ItemStack mistack = chis.getHandle();
        NBTTagCompound tag = mistack.tag;
        if (tag == null)
        {
            tag = new NBTTagCompound();
            tag.setCompound("display", new NBTTagCompound());
            mistack.tag = tag;
        }
        String[] ss = ChatColor.stripColor(tag.getString("Name")).split(" ");
        potentialSet = ss[0];
        for (ItemStack is : sis)
        {
            CraftItemStack c = ((CraftItemStack) is);
            net.minecraft.server.ItemStack cstack = c.getHandle();
            NBTTagCompound cstacktag = cstack.tag;
            if (cstacktag == null)
            {
                cstacktag = new NBTTagCompound();
                cstacktag.setCompound("display", new NBTTagCompound());
                cstack.tag = cstacktag;
            }
            String[] splits = ChatColor.stripColor(cstacktag.getString("Name"))
                    .split(" ");
            if (!splits[0].equalsIgnoreCase(potentialSet))
                return false;
        }
        return true;
    }
}
