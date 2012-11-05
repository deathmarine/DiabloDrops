package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Legendary;
import com.modcrafting.diablodrops.tier.Lore;
import com.modcrafting.diablodrops.tier.Magical;
import com.modcrafting.diablodrops.tier.Rare;
import com.modcrafting.diablodrops.tier.Set;

public class DropsAPI
{

	private Random gen = new Random();
	private String[] types =
	{
			"legendary", "lore", "magical", "rare", "set"
	};
	private Drops drops = new Drops();
	private DiabloDrops plugin;

	public DropsAPI(DiabloDrops instance)
	{
		plugin = instance;
	}

	/**
	 * Returns an specific type of itemstack that was randomly generated
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(String type)
	{
		Material mat = Material.DIAMOND_SWORD;
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				mat = mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			mat = drops.weaponPicker();
		}
		if (mat == null)
			return null;
		if (type.equalsIgnoreCase("legendary"))
		{
			int e = plugin.config.getInt("Legendary.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Legendary.Enchaments.Levels", 10);
			CraftItemStack ci = new Legendary(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equalsIgnoreCase("lore"))
		{
			int e = plugin.config.getInt("Lore.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Lore.Enchaments.Levels", 9);
			CraftItemStack ci = new Lore(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equalsIgnoreCase("magical"))
		{
			int e = plugin.config.getInt("Magical.Enchaments.Amt", 3);
			int l = plugin.config.getInt("Magical.Enchaments.Levels", 4);
			CraftItemStack ci = new Magical(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equalsIgnoreCase("rare"))
		{
			int e = plugin.config.getInt("Rare.Enchaments.Amt", 5);
			int l = plugin.config.getInt("Rare.Enchaments.Levels", 5);
			CraftItemStack ci = new Rare(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		if (type.equalsIgnoreCase("set"))
		{
			int e = plugin.config.getInt("Set.Enchaments.Amt", 7);
			int l = plugin.config.getInt("Set.Enchaments.Levels", 6);
			CraftItemStack ci = new Set(mat, name());
			for (; e > 0; e--)
			{
				int lvl = plugin.gen.nextInt(l + 1);
				Enchantment ench = drops.enchant();
				if (lvl != 0 && ench != null)
					ci.addUnsafeEnchantment(ench, lvl);
			}
			return ci;
		}
		return null;
	}
	/**
	 * Returns an itemstack that was randomly generated
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem()
	{
		Material mat = Material.DIAMOND_SWORD;
		if (gen.nextBoolean())
		{
			Material[] mats = drops.armorPicker();
			if (mats != null)
				mat = mats[gen.nextInt(mats.length - 1)];
		}
		else
		{
			mat = drops.weaponPicker();
		}
		if (mat == null)
			return null;
		String type = types[gen.nextInt(types.length - 1)];
		return getItem(type);
	}
	public String name(){
		String prefix = plugin.prefix.get(plugin.gen.nextInt(plugin.prefix.size()-1));
		String suffix = plugin.suffix.get(plugin.gen.nextInt(plugin.suffix.size()-1));
		return prefix+" "+suffix;
	}
}
