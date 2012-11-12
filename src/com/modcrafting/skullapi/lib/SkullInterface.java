package com.modcrafting.skullapi.lib;

<<<<<<< HEAD
import com.modcrafting.skullapi.lib.Skull.SkullType;

public interface SkullInterface {

	public void setOwner(String name);

	public String getOwner(String name);

	public void setSkullType(SkullType type);

	public SkullType getSkullType();

=======
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
>>>>>>> refs/remotes/fork/master
}
