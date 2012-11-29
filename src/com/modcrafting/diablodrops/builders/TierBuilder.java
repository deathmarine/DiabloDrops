package com.modcrafting.diablodrops.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.log.Logging;
import com.modcrafting.diablodrops.tier.Tier;

public class TierBuilder
{
    DiabloDrops plugin;

    public TierBuilder(DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public void build()
    {
        plugin.tiers.clear();
        FileConfiguration cs = new YamlConfiguration();
        File f = new File(plugin.getDataFolder(), "tier.yml");
        if (f.exists())
        {
            try
            {
                cs.load(f);
            }
            catch (Exception e)
            {
                Logging.debug("", e, true);
            }
        }
        for (String name : cs.getKeys(false))
        {
            int amt = cs.getInt(name + ".Enchantments.Amt");
            int lvl = cs.getInt(name + ".Enchantments.Levels");
            int chance = cs.getInt(name + ".Chance");
            String color = cs.getString(name + ".Color");
            List<Material> l = new ArrayList<Material>();
            for (String s : cs.getStringList(name + ".Materials"))
            {
                Material mat = Material.matchMaterial(s);
                if (mat != null)
                    l.add(mat);
            }
            plugin.tiers.add(new Tier(name, ChatColor.valueOf(color
                    .toUpperCase()), Math.abs(amt), Math.abs(lvl), Math
                    .abs(chance), l));
        }
    }
}
