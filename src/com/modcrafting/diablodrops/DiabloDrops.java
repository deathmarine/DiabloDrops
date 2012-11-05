package com.modcrafting.diablodrops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import com.modcrafting.diablodrops.configuration.ConfigHelper;
import com.modcrafting.diablodrops.configuration.DiabloDropsConfigs;
import com.modcrafting.diablodrops.drops.DropsAPI;
import com.modcrafting.diablodrops.listeners.KillListener;
import com.modcrafting.diablodrops.name.NamesLoader;

public class DiabloDrops extends JavaPlugin
{
	public List<String> prefix = new ArrayList<String>();
	public List<String> suffix = new ArrayList<String>();
	private NamesLoader nameLoader;
	public Random gen = new Random();
	public DiabloDropsConfigs configManager;
	public ConfigHelper configHelper;
	public DropsAPI dropsAPI;

	public void onDisable()
	{
		prefix.clear();
		suffix.clear();
	}

	public void onEnable()
	{
		this.getDataFolder().mkdir();
		nameLoader = new NamesLoader(this);
		configManager = new DiabloDropsConfigs(this);
		configHelper = new ConfigHelper(this, configManager);
		dropsAPI = new DropsAPI(this);
		this.getServer().getPluginManager()
				.registerEvents(new KillListener(this), this);

	}

	public NamesLoader getNameLoader()
	{
		return nameLoader;
	}
}
