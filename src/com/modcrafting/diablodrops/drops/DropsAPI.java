package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Drop;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.Tome;

public class DropsAPI
{

	private Random gen = new Random();
	private Drops drops = new Drops();
	private DiabloDrops plugin;
	
	public DropsAPI(DiabloDrops instance)
	{
		plugin = instance;
	}

	/**
	 * Returns a specific type of item randomly generated
	 * 
	 * @param material
	 *            Material of itemstack
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(Material mat)
	{
		if (mat == null)
			return null;
		for(Tier tier: plugin.tiers)
		{
			if(gen.nextInt(100)<=tier.getChance())
			{
				int e = tier.getAmount();
				int l = tier.getLevels();
				CraftItemStack ci = new Drop(mat,tier.getColor(),name());
				for (; e > 0; e--)
				{
					int lvl = plugin.gen.nextInt(l + 1);
					Enchantment ench = drops.enchant();
					if (lvl != 0 && ench != null)
						makeSafe(ench, ci, lvl);
				}
				return ci;
			}
		}
		if(plugin.config.getBoolean("IdentifyTome.Enabled",true)&&
				gen.nextInt(100)<=plugin.config.getInt("IdentifyTome.Chance",3))
			return new Tome();
		return null;
	}

	/**
	 * Returns an specific typename of itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(String type)
	{
		Material mat = dropPicker();
		if(mat==null) return null;
		for(Tier tier:plugin.tiers){
			if(tier.getName().equalsIgnoreCase(type)){
				if(gen.nextInt(100)<=tier.getChance())
				{
					int e = tier.getAmount();
					int l = tier.getLevels();
					CraftItemStack ci = new Drop(mat,tier.getColor(),name());
					for (; e > 0; e--)
					{
						int lvl = plugin.gen.nextInt(l + 1);
						Enchantment ench = drops.enchant();
						if (lvl != 0 && ench != null)
							makeSafe(ench, ci, lvl);
					}
					return ci;
				}
			}
		}
		return null;
	}
	/**
	 * Returns an itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem()
	{
		Material mat = dropPicker();
		if(mat==null) return null;
		return getItem(mat);
	}
	/**
	 * Returns a Material that was randomly picked
	 * 
	 * @return Material
	 */
	public Material dropPicker(){
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				return mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			return drops.weaponPicker();
		}
		return null;
	}
	public boolean canBeItem(Material material)
	{
		if (drops.isArmor(material) || drops.isTool(material))
			return true;
		return false;
	}

	public String name()
	{
		String prefix = plugin.prefix.get(plugin.gen.nextInt(plugin.prefix
				.size() - 1));
		String suffix = plugin.suffix.get(plugin.gen.nextInt(plugin.suffix
				.size() - 1));
		return prefix + " " + suffix;
	}

	public void makeSafe(Enchantment ench, CraftItemStack citem, int level){
		try{
			citem.addEnchantment(ench, 1);
		}catch (IllegalArgumentException e){
			//Do nothing.
		}
	}
}
