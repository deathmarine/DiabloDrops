package com.modcrafting.diablodrops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.h31ix.updater.Updater;
import net.h31ix.updater.Updater.UpdateResult;
import net.h31ix.updater.Updater.UpdateType;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.modcrafting.diablodrops.commands.DiabloDropCommand;
import com.modcrafting.diablodrops.drops.CustomBuilder;
import com.modcrafting.diablodrops.drops.Drops;
import com.modcrafting.diablodrops.drops.DropsAPI;
import com.modcrafting.diablodrops.listeners.ChunkListener;
import com.modcrafting.diablodrops.listeners.EffectsListener;
import com.modcrafting.diablodrops.listeners.KillListener;
import com.modcrafting.diablodrops.listeners.SocketListener;
import com.modcrafting.diablodrops.listeners.TomeListener;
import com.modcrafting.diablodrops.name.NamesLoader;
import com.modcrafting.diablodrops.sets.ArmorSet;
import com.modcrafting.diablodrops.sets.ArmorSetBuilder;
import com.modcrafting.diablodrops.socket.SocketBuilder;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.TierBuilder;
import com.modcrafting.toolapi.lib.Tool;
import com.stirante.PrettyScaryLib.Namer;

public class DiabloDrops extends JavaPlugin
{
	public List<String> prefix = new ArrayList<String>();
	public List<String> suffix = new ArrayList<String>();
	public HashSet<Tier> tiers = new HashSet<Tier>();
	public HashSet<ArmorSet> armorSets = new HashSet<ArmorSet>();
	public List<Tool> custom = new ArrayList<Tool>();
	public List<String> multiW = new ArrayList<String>();
	public List<String> defenselore = new ArrayList<String>();
	public List<String> offenselore = new ArrayList<String>();
	public List<String> setBonus = new ArrayList<String>();
	public HashMap<Block, ItemStack> furnanceMap = new HashMap<Block, ItemStack>();
	private NamesLoader nameLoader;
	public Random gen = new Random();
	public FileConfiguration config;
	public DropsAPI dropsAPI;
	public Drops drop = new Drops();
	public Namer itemNamer;
	public boolean debugMode;

	private static DiabloDrops instance;

	public void onDisable()
	{
		prefix.clear();
		suffix.clear();
		offenselore.clear();
		defenselore.clear();
		tiers.clear();
		armorSets.clear();
		custom.clear();
		multiW.clear();
		furnanceMap.clear();
	}

	public void onEnable()
	{
		instance = this;
		this.getDataFolder().mkdir();
		nameLoader = new NamesLoader(this);
		nameLoader.writeDefault("config.yml");
		config = this.getConfig();
		debugMode = config.getBoolean("Debug.Enabled", false);
		nameLoader.writeDefault("custom.yml");
		nameLoader.writeDefault("set.yml");
		nameLoader.writeDefault("tier.yml");
		nameLoader.writeDefault("prefix.txt");
		nameLoader.writeDefault("suffix.txt");
		nameLoader.writeDefault("defenselore.txt");
		nameLoader.writeDefault("offensivelore.txt");
		nameLoader.writeDefault("setbonus.txt");
		nameLoader.loadFile(prefix, "prefix.txt");
		nameLoader.loadFile(suffix, "suffix.txt");
		nameLoader.loadFile(defenselore, "defenselore.txt");
		nameLoader.loadFile(offenselore, "offensivelore.txt");
		nameLoader.loadFile(setBonus, "setbonus.txt");
		new CustomBuilder(this).build();
		new SocketBuilder(this).build();
		new TierBuilder(this).build();
		if (debugMode)
		{
			List<String> tierNames = new ArrayList<String>();
			for (Tier t : this.tiers)
				tierNames.add(t.getName());
			getLogger().log(Level.INFO, tierNames.toString());
		}
		new ArmorSetBuilder(this).build();
		dropsAPI = new DropsAPI(this);
		itemNamer = new Namer();
		if (config.getBoolean("Worlds.Enabled", false))
		{
			List<String> fixCase = new ArrayList<String>();
			for (String s : config.getStringList("Worlds.Allowed"))
			{
				fixCase.add(s.toLowerCase());
			}
			if (fixCase.size() > 0)
				multiW = fixCase;
		}
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new KillListener(this), this);
		pm.registerEvents(new TomeListener(this), this);
		pm.registerEvents(new SocketListener(this), this);
		pm.registerEvents(new ChunkListener(this), this);
		pm.registerEvents(new EffectsListener(this), this);
		this.getCommand("diablodrops").setExecutor(new DiabloDropCommand(this));

		// AutoUpdater
		final PluginDescriptionFile pdf = this.getDescription();
		this.getServer().getScheduler()
				.scheduleAsyncDelayedTask(this, new Runnable()
				{

					@Override
					public void run()
					{
						if (config.getBoolean("Plugin.AutoUpdate", true))
						{
							Updater up = new Updater(getInstance(), pdf
									.getName().toLowerCase(), getFile(),
									UpdateType.DEFAULT, true);
							if (!up.getResult().equals(UpdateResult.SUCCESS)
									|| up.pluginFile(getFile().getName()))
							{
								if (up.getResult().equals(
										UpdateResult.FAIL_NOVERSION))
								{
									getLogger()
											.info("Unable to connect to dev.bukkit.org.");
								}
								else
								{
									getLogger()
											.info("No Updates found on dev.bukkit.org.");
								}
							}
							else
							{
								getLogger()
										.info("Update "
												+ up.getLatestVersionString()
												+ " found and downloaded please restart your server.");
							}
						}

					}

				});
	}

	/**
	 * Gets the instance of DiabloDrops
	 * 
	 * @return plugin's instance
	 */
	public static DiabloDrops getInstance()
	{
		return instance;
	}
}
