package com.modcrafting.skullapi.lib;

import com.modcrafting.skullapi.lib.Skull.SkullType;

public interface SkullInterface {

	public void setOwner(String name);

	public String getOwner(String name);

	public void setSkullType(SkullType type);

	public SkullType getSkullType();

}
