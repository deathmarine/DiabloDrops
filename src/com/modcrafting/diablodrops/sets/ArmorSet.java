package com.modcrafting.diablodrops.sets;

import java.util.List;

public class ArmorSet
{

	private final String name;
	private final List<String> bonuses;

	public ArmorSet(String name, List<String> bonuses)
	{
		this.name = name;
		this.bonuses = bonuses;
	}

	public String getName()
	{
		return name;
	}

	public List<String> getBonuses()
	{
		return bonuses;
	}

}
