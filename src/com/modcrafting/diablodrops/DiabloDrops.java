package com.modcrafting.diablodrops;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.modcrafting.diablodrops.drops.DropsAPI;
import com.modcrafting.diablodrops.listeners.KillListener;
import com.modcrafting.diablodrops.listeners.TomeListener;
import com.modcrafting.diablodrops.name.NamesLoader;
import com.modcrafting.diablodrops.socket.SocketWindow;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.TierBuilder;

public class DiabloDrops extends JavaPlugin
{
	public List<String> prefix = new ArrayList<String>();
	public List<String> suffix = new ArrayList<String>();
	public HashSet<Tier> tiers = new HashSet<Tier>();
	
	private NamesLoader nameLoader;
	public Random gen = new Random();
	public FileConfiguration config;
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
		nameLoader.writeDefault("config.yml");
		nameLoader.writeDefault("prefix.txt");
		nameLoader.writeDefault("suffix.txt");
		nameLoader.loadFile(prefix, "prefix.txt");
		nameLoader.loadFile(suffix, "suffix.txt");
		config = this.getConfig();
		dropsAPI = new DropsAPI(this);
		this.getServer().getPluginManager().registerEvents(new KillListener(this), this);
		this.getServer().getPluginManager().registerEvents(new SocketWindow(this), this);
		new TierBuilder(this).build();

	}
}
