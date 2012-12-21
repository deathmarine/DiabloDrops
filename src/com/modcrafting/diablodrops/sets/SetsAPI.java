package com.modcrafting.diablodrops.sets;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.modcrafting.diablodrops.DiabloDrops;

public class SetsAPI
{
    private final Random gen;
    private final DiabloDrops plugin;

    public SetsAPI(final DiabloDrops instance)
    {
        plugin = instance;
        gen = plugin.gen;
    }

    /**
     * Gets the armor set represented by name
     * 
     * @param name
     *            of set
     * @return armor set
     */
    public ArmorSet getArmorSet(final String name)
    {
        for (ArmorSet as : plugin.armorSets)
        {
            if (as.getName().equalsIgnoreCase(name))
                return as;
        }
        return null;
    }

    public Random getGen()
    {
        return gen;
    }

    /**
     * Gets the name of the set a player could be wearing
     * 
     * @param player
     *            Player to check
     * @return name of the set
     */
    public String getNameOfSet(final LivingEntity entity)
    {
        ItemStack his = entity.getEquipment().getHelmet();
        if (his == null)
            return null;
        if(his.hasItemMeta()){
            ItemMeta meta = his.getItemMeta();
            if(meta.getDisplayName()!=null){
            	String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
            	return splits[0];
            }        	
        }
        return null;
    }

    public DiabloDrops getPlugin()
    {
        return plugin;
    }

    public boolean wearingSet(final LivingEntity entity)
    {
        ItemStack his = entity.getEquipment().getHelmet();
        ItemStack cis = entity.getEquipment().getChestplate();
        ItemStack lis = entity.getEquipment().getLeggings();
        ItemStack bis = entity.getEquipment().getBoots();
        if ((his == null) || (cis == null) || (lis == null) || (bis == null))
            return false;
        Set<ItemStack> sis = new HashSet<ItemStack>();
        sis.add(cis);
        sis.add(lis);
        sis.add(bis);
        if(his.hasItemMeta()){
            ItemMeta meta = his.getItemMeta();
            String potentialSet = "";
            if(meta.getDisplayName()!=null){
            	String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
            	potentialSet = splits[0];
                for (ItemStack is : sis)
                {
                    ItemMeta ism = is.getItemMeta();
                    if(ism!=null){
                        if(ism.getDisplayName()!=null){
                        	String[] splits1 = ChatColor.stripColor(ism.getDisplayName()).split(" ");
                        	if (!splits1[0].equalsIgnoreCase(potentialSet))
                        		return false;
                        }
                    }
                }
            }        	
        }
        return true;
    }

    /**
     * Is player wearing a set of matching armor?
     * 
     * @param player
     * @return is set
     */
    public boolean wearingSet(final Player player)
    {
        ItemStack his = player.getInventory().getHelmet();
        ItemStack cis = player.getInventory().getChestplate();
        ItemStack lis = player.getInventory().getLeggings();
        ItemStack bis = player.getInventory().getBoots();
        if ((his == null) || (cis == null) || (lis == null) || (bis == null))
            return false;
        Set<ItemStack> sis = new HashSet<ItemStack>();
        sis.add(cis);
        sis.add(lis);
        sis.add(bis);
        if(his.hasItemMeta()){
            ItemMeta meta = his.getItemMeta();
            String potentialSet = "";
            if(meta.getDisplayName()!=null){
            	String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
            	potentialSet = splits[0];
                for (ItemStack is : sis)
                {
                    ItemMeta ism = is.getItemMeta();
                    if(ism!=null){
                        if(ism.getDisplayName()!=null){
                        	String[] splits1 = ChatColor.stripColor(ism.getDisplayName()).split(" ");
                        	if (!splits1[0].equalsIgnoreCase(potentialSet))
                        		return false;
                        }
                    }
                }
            }        	
        }
        return true;
    }


    /**
     * Gets the name of the set a player could be wearing
     * 
     * @param player
     *            Player to check
     * @return name of the set
     */
	public String getNameOfSet(Player player)
    {
    	ItemStack his = player.getInventory().getHelmet();
        if (his == null)
            return null;
        if(his.hasItemMeta()){
            ItemMeta meta = his.getItemMeta();
            if(meta.getDisplayName()!=null){
            	String[] splits = ChatColor.stripColor(meta.getDisplayName()).split(" ");
            	return splits[0];
            }        	
        }
        return null;
    }
}
