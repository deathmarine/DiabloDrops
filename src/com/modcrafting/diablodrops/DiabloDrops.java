package com.modcrafting.diablodrops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.modcrafting.diablodrops.commands.DiabloDropCommand;
import com.modcrafting.diablodrops.drops.Drops;
import com.modcrafting.diablodrops.drops.DropsAPI;
import com.modcrafting.diablodrops.listeners.ChunkListener;
import com.modcrafting.diablodrops.listeners.KillListener;
import com.modcrafting.diablodrops.listeners.SocketListener;
import com.modcrafting.diablodrops.listeners.TomeListener;
import com.modcrafting.diablodrops.name.NamesLoader;
import com.modcrafting.diablodrops.socket.SocketBonus;
import com.modcrafting.diablodrops.socket.SocketBuilder;
import com.modcrafting.diablodrops.socket.SocketBuilder.SocketType;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.TierBuilder;
import com.stirante.ItemNamer.Namer;

public class DiabloDrops extends JavaPlugin
{
	public List<String> prefix = new ArrayList<String>();
	public List<String> suffix = new ArrayList<String>();
	public List<String> lore = new ArrayList<String>();
	public HashSet<Tier> tiers = new HashSet<Tier>();
	public HashSet<Tier> usableTiers = new HashSet<Tier>();
	public HashMap<SocketType, SocketBonus> bonuses = new HashMap<SocketType, SocketBonus>();
	public HashMap<Block, ItemStack> furnanceMap = new HashMap<Block, ItemStack>();
	private NamesLoader nameLoader;
	public Random gen = new Random();
	public FileConfiguration config;
	public DropsAPI dropsAPI;
	public Drops drop = new Drops();
	public Namer itemNamer;

	public void onDisable()
	{
		prefix.clear();
		suffix.clear();
		lore.clear();
		tiers.clear();
		usableTiers.clear();
		furnanceMap.clear();
	}

	public void onEnable()
	{
		this.getDataFolder().mkdir();
		nameLoader = new NamesLoader(this);
		nameLoader.writeDefault("config.yml");
		nameLoader.writeDefault("prefix.txt");
		nameLoader.writeDefault("suffix.txt");
		nameLoader.writeDefault("lore.txt");
		nameLoader.loadFile(prefix, "prefix.txt");
		nameLoader.loadFile(suffix, "suffix.txt");
		nameLoader.loadFile(lore, "lore.txt");
		config = this.getConfig();
		dropsAPI = new DropsAPI(this);
		itemNamer = new Namer();
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new KillListener(this), this);
		pm.registerEvents(new TomeListener(this), this);
		pm.registerEvents(new SocketListener(this), this);
		pm.registerEvents(new ChunkListener(this), this);
		getCommand("diablodrops").setExecutor(new DiabloDropCommand(this));
		new SocketBuilder(this).build();
		new TierBuilder(this).build();
	}
}
