package com.modcrafting.diablodrops.socket;

import org.bukkit.configuration.ConfigurationSection;

import com.modcrafting.diablodrops.DiabloDrops;

public class SocketBuilder
{
	DiabloDrops plugin;

	public SocketBuilder(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	public void build()
	{
		plugin.weaponBonuses.clear();
		plugin.armorBonuses.clear();
		ConfigurationSection cs = plugin.config
				.getConfigurationSection("SocketBonuses");
		for (String s : cs.getStringList("Weapon"))
		{
			String[] bonus = s.split(" ");
			int amt = 0;
			String type;
			try
			{
				amt = Integer.parseInt(bonus[0]);
			}
			catch (NumberFormatException e)
			{
			}
			if (bonus.length >= 2)
				type = bonus[1];
			else
				type = "damage";
			SocketBonus sb = new SocketBonus(s, amt, type);
			plugin.weaponBonuses.add(sb);
		}
		for (String s : cs.getStringList("Armor"))
		{
			String[] bonus = s.split(" ");
			int amt = 0;
			String type;
			try
			{
				amt = Integer.parseInt(bonus[0]);
			}
			catch (NumberFormatException e)
			{
			}
			if (bonus.length >= 2)
				type = bonus[1];
			else
				type = "damage";
			SocketBonus sb = new SocketBonus(s, amt, type);
			plugin.armorBonuses.add(sb);
		}
	}
}
