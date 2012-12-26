package com.modcrafting.diablodrops.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.utils.Biomes;

public class RuinBuilder
{
    DiabloDrops plugin;

    public RuinBuilder(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public void build()
    {
        plugin.materialsOfRuins.clear();
        plugin.ruinsCanSpawnOn.clear();
        FileConfiguration cs = new YamlConfiguration();
        File f = new File(plugin.getDataFolder(), "ruins.yml");
        if (f.exists())
        {
            try
            {
                cs.load(f);
            }
            catch (Exception e)
            {
                if (plugin.debug)
                {
                    plugin.log.warning(e.getMessage());
                }
            }
        }
        for (String name : cs.getKeys(false))
        {
            if (!cs.getBoolean(name + ".enabled", true))
            {
                continue;
            }
            HashMap<Biomes, List<String>> matTypes = new HashMap<Biomes, List<String>>();
            ConfigurationSection mats = cs.getConfigurationSection(name
                    + ".materialPerBiome");
            if (mats != null)
            {
                for (int x = 0; x < 22; x++)
                {
                    Biomes biome = Biomes.valueOf(x);
                    List<String> strings = mats
                            .getStringList(String.valueOf(x));
                    if ((strings == null) || strings.isEmpty())
                    {
                        strings = new ArrayList<String>();
                    }
                    matTypes.put(biome, strings);
                }
            }
            HashMap<Biomes, List<String>> spawnOnTypes = new HashMap<Biomes, List<String>>();
            ConfigurationSection spawnOns = cs.getConfigurationSection(name
                    + ".spawnOnPerBiome");
            if (spawnOns != null)
            {
                for (int x = 0; x < 22; x++)
                {
                    Biomes biome = Biomes.valueOf(x);
                    List<String> strings = spawnOns.getStringList(String
                            .valueOf(x));
                    if ((strings == null) || strings.isEmpty())
                    {
                        strings = new ArrayList<String>();
                    }
                    spawnOnTypes.put(biome, strings);
                }
            }
            plugin.materialsOfRuins.put(name, matTypes);
            plugin.ruinsCanSpawnOn.put(name, spawnOnTypes);
        }
    }
}
