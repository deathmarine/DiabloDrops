package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;

public class Tier
{
	String name;
	ChatColor color;
	int amt;
	int lvl;
	int chance;

	public Tier(String name, ChatColor color, int amt, int lvl, int chance)
	{
		this.name = name;
		this.color = color;
		this.amt = amt;
		this.lvl = lvl;
		this.chance = chance;
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
	 * Get the amount of the tier
	 * 
	 * @return amount
	 */
	public Integer getAmount()
	{
		return amt;
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
	 * Get chance of the tier
	 * 
	 * @return chance
	 */
	public Integer getChance()
	{
		return chance;
	}
}
