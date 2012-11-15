package com.modcrafting.diablodrops.drops;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.toolapi.lib.Tool;

public class DropsCustom
{
	DiabloDrops plugin;

	public DropsCustom(DiabloDrops instance)
	{
		plugin = instance;
		try
		{
			load();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	public void load() throws FileNotFoundException, IOException,
			InvalidConfigurationException
	{
		FileConfiguration fc = new YamlConfiguration();
		File f = new File(plugin.getDataFolder(), "custom.yml");
		if (f.exists())
		{
			fc.load(f);
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
			for (String ench : cs1.getKeys(false))
			{
				tool.addUnsafeEnchantment(
						Enchantment.getByName(ench.toUpperCase()),
						cs1.getInt(ench));
			}
			plugin.custom.add(tool);
		}
	}
}
