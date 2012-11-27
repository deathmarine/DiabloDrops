package com.modcrafting.diablodrops.drops;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.toolapi.lib.Tool;

public class CustomBuilder
{
	DiabloDrops plugin;

	public CustomBuilder(DiabloDrops instance)
	{
		plugin = instance;
	}

	public void build()
	{
		FileConfiguration fc = new YamlConfiguration();
		File f = new File(plugin.getDataFolder(), "custom.yml");
		if (f.exists())
		{
			try {
				fc.load(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String name : fc.getKeys(false))
		{
			ConfigurationSection cs = fc.getConfigurationSection(name);
			Material mat = Material.matchMaterial(cs.getString("Material"));
			ChatColor color = ChatColor.valueOf(cs.getString("Color")
					.toUpperCase());
			List<String> lore = cs.getStringList("Lore");
			Tool tool = new Tool(mat);
			tool.setName(color + name);
			for (String s : lore)
			{
				tool.setLore(ChatColor.translateAlternateColorCodes(
						"&".toCharArray()[0], s));
			}
			ConfigurationSection cs1 = cs
					.getConfigurationSection("Enchantments");
			if (cs1 != null)
			{
				for (String ench : cs1.getKeys(false))
				{
					Enchantment encha = Enchantment.getByName(ench
							.toUpperCase());
					if (encha == null)
						continue;
					tool.addUnsafeEnchantment(encha, cs1.getInt(ench));
				}
			}
			plugin.custom.add(tool);
		}
	}
}
