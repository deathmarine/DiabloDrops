package com.modcrafting.diablodrops.builders;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.rarity.Rarity;

public class RarityBuilder
{
    DiabloDrops plugin;

    public RarityBuilder(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Clears and then populates the plugin's tier list
     */
    public void build()
    {
        plugin.rarities.clear();
        FileConfiguration cs = new YamlConfiguration();
        File f = new File(plugin.getDataFolder(), "rarity.yml");
        if (f.exists())
            try
            {
                cs.load(f);
            }
            catch (Exception e)
            {
                if (plugin.getDebug())
                    plugin.log.warning(e.getMessage());
            }
        for (String name : cs.getKeys(false))
        {
            String displayName = cs.getString(name + ".displayName");
            String color = cs.getString(name + ".color", "WHITE");
            float dropChance = ((float) cs.getDouble(name + ".dropChance", 100)) / 100;
            ChatColor cc = ChatColor.valueOf(color);
            if (cc == null)
                cc = ChatColor.WHITE;
            double spawnChance = cs.getDouble(name + ".spawnChance", 100.0);
            Rarity rarity = new Rarity(name, displayName, cc, dropChance,
                    spawnChance);
            plugin.rarities.add(rarity);
        }
    }
}
