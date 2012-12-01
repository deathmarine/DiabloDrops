package com.modcrafting.skullapi.lib;

import java.util.List;

import com.modcrafting.skullapi.lib.Skull.SkullType;

public interface SkullInterface
{

    public String[] getLore();

    public String getName();

    public String getOwner();

    public SkullType getSkullType();

    public void setLore(List<String> lore);

    public void setName(String name);

    public void setOwner(String name);

    public void setSkullType(SkullType type);
}
