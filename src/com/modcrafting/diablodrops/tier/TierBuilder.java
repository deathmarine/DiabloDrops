package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.modcrafting.diablodrops.DiabloDrops;

public class TierBuilder
{
	DiabloDrops plugin;

	public TierBuilder(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	public void build()
	{
		ConfigurationSection cs = plugin.config.getConfigurationSection("Tier");
		for (String name : cs.getKeys(false))
		{
			int amt = cs.getInt(name + ".Enchantments.Amt");
			int lvl = cs.getInt(name + ".Enchantments.Levels");
			int chance = cs.getInt(name + ".Chance");
			String color = cs.getString(name + ".Color");
			plugin.tiers.add(new Tier(name, ChatColor.valueOf(color
					.toUpperCase()), amt, lvl, chance));
			if (!ChatColor.valueOf(color.toUpperCase()).equals(ChatColor.MAGIC))
				plugin.usableTiers.add(new Tier(name, ChatColor.valueOf(color
						.toUpperCase()), amt, lvl, chance));
		}
	}
}
