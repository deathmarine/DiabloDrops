package com.modcrafting.skullapi.lib;

import java.util.List;

import com.modcrafting.skullapi.lib.Skull.SkullType;

public interface SkullInterface
{

	public void setName(String name);

	public String getName();

	public void setOwner(String name);

	public String getOwner();

	public void setSkullType(SkullType type);

	public SkullType getSkullType();

	public void setLore(List<String> lore);

	public String[] getLore();
}
