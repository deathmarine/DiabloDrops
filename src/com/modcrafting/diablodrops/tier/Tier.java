package com.modcrafting.diablodrops.tier;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Tier
{
    String name;
    ChatColor color;
    int amt;
    int lvl;
    int chance;
    List<Material> l;

    public Tier(String name, ChatColor color, int amt, int lvl, int chance,
            List<Material> l)
    {
        this.name = name;
        this.color = color;
        this.amt = amt;
        this.lvl = lvl;
        this.chance = chance;
        this.l = l;
    }

    /**
     * Get the amount of the tier
     * 
     * @return amount
     */
    public Integer getAmount()
    {
        return amt;
    }

    /**
     * Get chance of the tier
     * 
     * @return Integer
     */
    public Integer getChance()
    {
        return chance;
    }

    /**
     * Get the color of the tier
     * 
     * @return color
     */
    public ChatColor getColor()
    {
        return color;
    }

    /**
     * Get the levels of the tier
     * 
     * @return levels
     */
    public Integer getLevels()
    {
        return lvl;
    }

    /**
     * Gets a list of acceptable materials
     * 
     * @return Material
     */
    public List<Material> getMaterials()
    {
        return l;
    }

    /**
     * Get the name of the tier
     * 
     * @return name
     */
    public String getName()
    {
        return name;
    }
}
