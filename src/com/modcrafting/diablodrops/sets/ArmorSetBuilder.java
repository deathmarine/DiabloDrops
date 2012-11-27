package com.modcrafting.diablodrops.sets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;

public class ArmorSetBuilder
{
	DiabloDrops plugin;

	public ArmorSetBuilder(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	public void build()
	{
		plugin.armorSets.clear();
		FileConfiguration cs = new YamlConfiguration();
		File f = new File(plugin.getDataFolder(), "set.yml");
		if (f.exists())
		{
			try
			{
				cs.load(f);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		for (String name : cs.getKeys(false))
		{
			List<String> bonuses = cs.getStringList(name + ".Bonuses");
			if (bonuses == null)
				bonuses = new ArrayList<String>();
			plugin.armorSets.add(new ArmorSet(name, bonuses));
		}
	}
}
