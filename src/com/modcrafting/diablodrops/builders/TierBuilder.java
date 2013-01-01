package com.modcrafting.diablodrops.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.tier.Tier;

public class TierBuilder
{
    DiabloDrops plugin;

    public TierBuilder(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public void build()
    {
        plugin.tiers.clear();
        FileConfiguration cs = new YamlConfiguration();
        File f = new File(plugin.getDataFolder(), "tier.yml");
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
            int amt = cs.getInt(name + ".Enchantments.Amt");
            int lvl = cs.getInt(name + ".Enchantments.Levels");
            double chance = cs.getDouble(name + ".Chance");
            String color = cs.getString(name + ".Color");
            List<Material> l = new ArrayList<Material>();
            for (String s : cs.getStringList(name + ".Materials"))
            {
                Material mat = Material.getMaterial(s.toUpperCase());
                if (mat != null)
                    l.add(mat);
            }
            for (String s : cs.getStringList(name + ".Material"))
            {
                Material mat = Material.getMaterial(s.toUpperCase());
                if (mat != null)
                    l.add(mat);
            }
            List<String> lore = new ArrayList<String>();
            for (String s : cs.getStringList(name + ".Lore"))
                if (s != null)
                    lore.add(ChatColor.translateAlternateColorCodes('&', s));
            plugin.tiers.add(new Tier(name, ChatColor.valueOf(color
                    .toUpperCase()), Math.abs(amt), Math.abs(lvl), Math
                    .abs((int) (chance * 100)), l, lore, cs.getString(name
                    + ".DisplayName", name), ((float) cs.getDouble(name
                    + ".DropChance", 100)) / 100));
        }
    }
}
