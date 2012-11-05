package com.modcrafting.diablodrops.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;

public class DiabloDropsConfigs
{
	private final HashMap<DiabloDropsConfFile, YamlConfiguration> _configurations;

	private final DiabloDrops _plugin;

	public DiabloDropsConfigs(DiabloDrops plugin)
	{
		_plugin = plugin;

		_configurations = new HashMap<DiabloDropsConfFile, YamlConfiguration>();

		this.loadConfig();
	}

	private void createConfig(DiabloDropsConfFile config, File file)
	{
		switch (config)
		{
		case GENERAL:
			CommentedYamlConfiguration generalConf = new CommentedYamlConfiguration();
			generalConf
					.addComment("Reason",
							"Reasons to not add special equipment",
							"If true disables mobs dropping equipment if spawned by that reason");
			generalConf.set("Reason.Spawner", true);
			generalConf.set("Reason.Egg", true);
			generalConf.addComment("DropFix",
					"Mobs drop all the equipment they're wearing",
					"If false, only drops special items");
			generalConf.set("DropFix.Equipment", false);
			generalConf.set("Percentages.ChancePerSpawn", new Integer(3));
			try
			{
				generalConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, generalConf);
			break;

		case TIERS:
			CommentedYamlConfiguration tierConf = new CommentedYamlConfiguration();
			tierConf.set("Legendary.Enchantments.Amt", new Integer(7));
			tierConf.set("Legendary.Enchantments.Levels", new Integer(10));
			tierConf.set("Legendary.Color", "GOLD");
			tierConf.set("Lore.Enchantments.Amt", new Integer(7));
			tierConf.set("Lore.Enchantments.Levels", new Integer(9));
			tierConf.set("Lore.Color", "GREEN");
			tierConf.set("Magical.Enchantments.Amt", new Integer(3));
			tierConf.set("Magical.Enchantments.Levels", new Integer(4));
			tierConf.set("Magical.Color", "BLUE");
			tierConf.set("Rare.Enchantments.Amt", new Integer(5));
			tierConf.set("Rare.Enchantments.Levels", new Integer(5));
			tierConf.set("Rare.Color", "YELLOW");
			tierConf.set("Set.Enchantments.Amt", new Integer(7));
			tierConf.set("Set.Enchantments.Levels", new Integer(6));
			tierConf.set("Set.Color", "RED");
			try
			{
				tierConf.save(file);
			}
			catch (IOException e2)
			{
			}

			_configurations.put(config, tierConf);
			break;
		}

	}

	/**
	 * Gets a value for path in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return String Value contained by path
	 */
	public String getProperty(DiabloDropsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			String prop = conf.getString(path, "NULL");

			if (!prop.equalsIgnoreCase("NULL"))
				return prop;
			conf.set(path, null);
		}

		return null;
	}

	/**
	 * Gets a value for path in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return List<String> Value contained by path
	 */
	public List<String> getPropertyList(DiabloDropsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			List<String> prop = conf.getStringList(path);
			if (!prop.contains("NULL"))
				return prop;
			conf.set(path, null);
		}

		return null;
	}

	/**
	 * Loads the plugin's configuration files
	 */
	public void loadConfig()
	{
		for (DiabloDropsConfFile file : DiabloDropsConfFile.values())
		{
			File confFile = new File(file.getPath());

			if (confFile.exists())
			{
				if (_configurations.containsKey(file))
					_configurations.remove(file);

				YamlConfiguration conf = YamlConfiguration
						.loadConfiguration(confFile);
				_configurations.put(file, conf);
			}
			else
			{
				File parentFile = confFile.getParentFile();

				if (!parentFile.exists())
					parentFile.mkdirs();

				this.createConfig(file, confFile);
			}
		}

		File preFile = new File("plugins/DiabloDrops/prefix.txt");
		File sufFile = new File("plugins/DiabloDrops/suffix.txt");

		if (!preFile.exists())
			_plugin.getNameLoader().writeDefault("prefix.txt");
		if (!sufFile.exists())
			_plugin.getNameLoader().writeDefault("suffix.txt");

		_plugin.getNameLoader().loadFile(_plugin.prefix, "prefix.txt");
		_plugin.getNameLoader().loadFile(_plugin.suffix, "suffix.txt");

	}

	/**
	 * Checks if path exists in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to search in
	 * @param String
	 *            Path to search for
	 * @return boolean Property exists
	 */
	public boolean propertyExists(DiabloDropsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			if (conf.contains(path))
				return true;
		}

		return false;
	}

	public Set<String> getKeys(DiabloDropsConfFile file, boolean deep)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			return conf.getKeys(deep);
		}

		return new HashSet<String>();
	}

	/**
	 * Sets path to null in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set null
	 * @return boolean If completed
	 */
	public boolean removeProperty(DiabloDropsConfFile file, String path)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, null);
			return true;
		}

		return false;
	}

	/**
	 * Saves the plugin's configs
	 */
	public void saveConfig()
	{
		for (DiabloDropsConfFile file : DiabloDropsConfFile.values())
		{
			if (_configurations.containsKey(file))
				try
				{
					_configurations.get(file).save(new File(file.getPath()));
				}
				catch (IOException e)
				{

				}
		}
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param boolean Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(DiabloDropsConfFile file, String path,
			boolean value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param double Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(DiabloDropsConfFile file, String path,
			Double value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param int Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(DiabloDropsConfFile file, String path, int value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to value in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param String
	 *            Value to set
	 * @return boolean If completed
	 */
	public boolean setProperty(DiabloDropsConfFile file, String path,
			String value)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, value);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{

			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to list of values in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param ArrayList
	 *            <String> List of values
	 * @return boolean If completed
	 */
	public boolean setPropertyList(DiabloDropsConfFile file, String path,
			ArrayList<String> list)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, list);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}

	/**
	 * Sets path to list of values in file
	 * 
	 * @param DiabloDropsConfFile
	 *            File to set in
	 * @param String
	 *            Path to set
	 * @param List
	 *            <String> List of values to set
	 * @return boolean If completed
	 */
	public boolean setPropertyList(DiabloDropsConfFile file, String path,
			List<String> list)
	{
		FileConfiguration conf = _configurations.get(file);

		if (conf != null)
		{
			conf.set(path, list);
			try
			{
				conf.save(new File(file.getPath()));
			}
			catch (IOException e)
			{
			}
			return true;
		}

		return false;
	}

	public DiabloDrops getPlugin()
	{
		return _plugin;
	}
}
