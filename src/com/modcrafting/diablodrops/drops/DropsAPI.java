package com.modcrafting.diablodrops.drops;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.configuration.DiabloDropsConfFile;
import com.modcrafting.diablodrops.tier.DiabloDropItem;

public class DropsAPI
{

	private Random gen = new Random();
	private String[] types;
	private Drops drops = new Drops();
	private DiabloDrops plugin;

	public DropsAPI(DiabloDrops instance)
	{
		plugin = instance;
		types = plugin.configHelper.stringSetToArray(DiabloDropsConfFile.TIERS,
				plugin.configManager.getKeys(DiabloDropsConfFile.TIERS, false),
				new String[]
				{
						"legendary", "lore", "magical", "rare", "set"
				});
	}

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
		if (!typesContains(type))
		{
			return null;
		}
		int e = plugin.configHelper.getInt(DiabloDropsConfFile.TIERS, type
				+ ".Enchantments.Amt", 0);
		int l = plugin.configHelper.getInt(DiabloDropsConfFile.TIERS, type
				+ ".Enchantments.Levels", 0);
		CraftItemStack ci = new DiabloDropItem(mat, plugin,
				plugin.configHelper
						.getString(
								DiabloDropsConfFile.TIERS,
								type + ".Color",
								ChatColor.values()[gen.nextInt(ChatColor
										.values().length - 1)].name())
						.toUpperCase());
		for (; e > 0; e--)
		{
			int lvl = plugin.gen.nextInt(l + 1);
			Enchantment ench = drops.enchant();
			if (lvl != 0 && ench != null)
				ci.addUnsafeEnchantment(ench, lvl);
		}
		return ci;
	}

	private boolean typesContains(String type)
	{
		for (String t : types)
		{
			if (!t.equalsIgnoreCase(type))
				continue;
			return true;
		}
		return false;
	}

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
		int e = plugin.configHelper.getInt(DiabloDropsConfFile.TIERS, type
				+ ".Enchantments.Amt", 0);
		int l = plugin.configHelper.getInt(DiabloDropsConfFile.TIERS, type
				+ ".Enchantments.Levels", 0);
		CraftItemStack ci = new DiabloDropItem(mat, plugin,
				plugin.configHelper
						.getString(
								DiabloDropsConfFile.TIERS,
								type + ".Color",
								ChatColor.values()[gen.nextInt(ChatColor
										.values().length - 1)].name())
						.toUpperCase());
		for (; e > 0; e--)
		{
			int lvl = plugin.gen.nextInt(l + 1);
			Enchantment ench = drops.enchant();
			if (lvl != 0 && ench != null)
				ci.addUnsafeEnchantment(ench, lvl);
		}
		return ci;
	}
}
