package com.modcrafting.diablodrops.rarity;

import org.bukkit.ChatColor;

public class Rarity
{
    private final String name;
    private final String displayName;
    private final ChatColor color;
    private final float dropChance;

    public Rarity(final String name, final String displayName,
            final ChatColor color, final float dropChance)
    {
        this.name = name;
        this.displayName = displayName;
        this.color = color;
        this.dropChance = dropChance;
    }

    /**
     * @return the color
     */
    public ChatColor getColor()
    {
        return color;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * @return the dropChance
     */
    public float getDropChance()
    {
        return dropChance;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
}
