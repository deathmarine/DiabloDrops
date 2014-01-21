package us.deathmarine.diablodrops;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import us.deathmarine.diablodrops.builders.ArmorSetBuilder;
import us.deathmarine.diablodrops.builders.CustomBuilder;
import us.deathmarine.diablodrops.builders.SocketBuilder;
import us.deathmarine.diablodrops.builders.TierBuilder;
import us.deathmarine.diablodrops.commands.DiabloDropCommand;
import us.deathmarine.diablodrops.drops.DropsAPI;
import us.deathmarine.diablodrops.items.ItemAPI;
import us.deathmarine.diablodrops.listeners.ChunkListener;
import us.deathmarine.diablodrops.listeners.EffectsListener;
import us.deathmarine.diablodrops.listeners.MobListener;
import us.deathmarine.diablodrops.listeners.SetListener;
import us.deathmarine.diablodrops.listeners.SocketListener;
import us.deathmarine.diablodrops.listeners.TomeListener;
import us.deathmarine.diablodrops.name.NamesLoader;
import us.deathmarine.diablodrops.sets.ArmorSet;
import us.deathmarine.diablodrops.sets.SetsAPI;
import us.deathmarine.diablodrops.tier.Tier;
import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

public class DiabloDrops extends JavaPlugin {
	private static DiabloDrops instance;

	/**
	 * Gets the instance of DiabloDrops
	 * 
	 * @return plugin's instance
	 */
	public static DiabloDrops getInstance() {
		return instance;
	}

	public HashSet<ArmorSet> armorSets = new HashSet<ArmorSet>();
	public List<ItemStack> custom = new ArrayList<ItemStack>();
	private boolean debug;
	public List<String> defenselore = new ArrayList<String>();
	private ItemAPI drop;
	private DropsAPI dropsAPI;
	public HashMap<Block, ItemStack> furnanceMap = new HashMap<Block, ItemStack>();
	private final Random gen = new Random();

	public HashMap<Material, List<String>> hmprefix = new HashMap<Material, List<String>>();
	public HashMap<Material, List<String>> hmsuffix = new HashMap<Material, List<String>>();
	public Logger log;
	private NamesLoader nameLoader;
	public List<String> offenselore = new ArrayList<String>();
	public List<String> prefix = new ArrayList<String>();
	private SetsAPI setsAPI;

	private Settings settings;

	public List<String> suffix = new ArrayList<String>();

	public HashSet<Tier> tiers = new HashSet<Tier>();

	public List<String> worlds = new ArrayList<String>();

	public boolean getDebug() {
		return debug;
	}

	public DropsAPI getDropAPI() {
		return dropsAPI;
	}

	public ItemAPI getItemAPI() {
		return drop;
	}

	public SetsAPI getSetAPI() {
		return setsAPI;
	}

	public Settings getSettings() {
		return settings;
	}

	public Random getSingleRandom() {
		return gen;
	}

	/**
	 * Stops all tasks for the plugin.
	 */
	public void killTasks() {
		getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public void onDisable() {
		killTasks();
		prefix.clear();
		suffix.clear();
		hmprefix.clear();
		hmsuffix.clear();
		tiers.clear();
		armorSets.clear();
		custom.clear();
		worlds.clear();
		offenselore.clear();
		defenselore.clear();
		furnanceMap.clear();
	}

	@Override
	public void onEnable() {
		instance = this;
		getDataFolder().mkdir();
		log = getLogger();
		nameLoader = new NamesLoader(this);
		nameLoader.writeDefault("config.yml", false);
		nameLoader.writeDefault("custom.yml", false);
		nameLoader.writeDefault("tier.yml", false);
		nameLoader.writeDefault("set.yml", false);
		nameLoader.writeDefault("prefix.txt", false);
		nameLoader.writeDefault("suffix.txt", false);
		nameLoader.writeDefault("defenselore.txt", false);
		nameLoader.writeDefault("offenselore.txt", false);
		FileConfiguration config = getConfig();
		settings = new Settings(config);
		if (config.getBoolean("Display.ItemMaterialExtras", false)) {
			File loc = new File(getDataFolder(), "/NamesPrefix/");
			if (!loc.exists()) {
				loc.mkdir();
			}
			for (File f : loc.listFiles())
				if (f.getName().endsWith(".txt")) {
					getLogger().info("Loading Prefix File:" + f.getName());
					nameLoader.loadMaterialFile(hmprefix,
							new File(loc, f.getName()));
				}
			File sloc = new File(getDataFolder(), "/NamesSuffix/");
			if (!sloc.exists()) {
				sloc.mkdir();
			}
			for (File f : sloc.listFiles())
				if (f.getName().endsWith(".txt")) {
					getLogger().info("Loading Suffix File:" + f.getName());
					nameLoader.loadMaterialFile(hmsuffix,
							new File(sloc, f.getName()));
				}
		}
		
		nameLoader.loadFile(prefix, "prefix.txt");
		nameLoader.loadFile(suffix, "suffix.txt");
		
		nameLoader.loadFile(defenselore, "defenselore.txt");
		nameLoader.loadFile(offenselore, "offenselore.txt");
		custom = new ArrayList<ItemStack>();
		drop = new ItemAPI();
		new CustomBuilder(this).build();
		new SocketBuilder(this).build();
		new TierBuilder(this).build();
		new ArmorSetBuilder(this).build();
		dropsAPI = new DropsAPI(this);
		setsAPI = new SetsAPI(this);
		if (config.getBoolean("Worlds.Enabled", false)) {
			for (String s : config.getStringList("Worlds.Allowed")) {
				worlds.add(s.toLowerCase());
			}
		}
		debug = config.getBoolean("Plugin.Debug", false);

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new MobListener(this), this);
		pm.registerEvents(new TomeListener(this), this);
		pm.registerEvents(new SocketListener(this), this);
		pm.registerEvents(new ChunkListener(this), this);
		pm.registerEvents(new EffectsListener(this), this);
		pm.registerEvents(new SetListener(this), this);

		getCommand("diablodrops").setExecutor(new DiabloDropCommand(this));

		if (config.getBoolean("Plugin.AutoUpdate", true)) {
			getServer().getScheduler().runTask(this, new Runnable() {
				@Override
				public void run() {
					Updater up = new Updater(getInstance(), 46631, getFile(), UpdateType.DEFAULT, true);
					if (!up.getResult().equals(UpdateResult.SUCCESS)) {
						if (up.getResult().equals(
								Updater.UpdateResult.FAIL_NOVERSION)) {
							log.info("Unable to connect to dev.bukkit.org.");
						} else {
							log.info("No Updates found on dev.bukkit.org.");
						}
					} else {
						log.info("Update "
								+ up.getLatestName()
								+ " found and downloaded please restart your server.");
					}
				}

			});
		}
	}
}
