package com.modcrafting.toolapi.lib;

import java.util.List;

import net.minecraft.server.NBTTagCompound;

public interface ToolInterface
{
	/**
	 * Gets the name of the Tool Will return null if not named
	 * 
	 * @returns name
	 */
	public String getName();

	/**
	 * Sets the name of the Tool
	 */
	public void setName(String name);

	/**
	 * Gets the cost to repair
	 * 
	 * @return repair cost
	 */
	public Integer getRepairCost();

	/**
	 * Sets the cost to repair
	 */
	public void setRepairCost(Integer i);

	/**
	 * Sets the lore of the Tool
	 */
	public void setLore(List<String> lore);

	/**
	 * Returns the lore of the Tool
	 * 
	 * @return lore
	 */
	public String[] getLore();

	/**
	 * Sets a single line for lore.
	 * 
	 * @param string
	 */
	public void addLore(String string);

	/**
	 * Get the lore of the Tool as a list
	 * 
	 * @return list
	 */
	public List<String> getLoreList();

	/**
	 * Sets the compound for the item.
	 * 
	 * @param tag
	 */
	public void setTag(NBTTagCompound tag);

	/**
	 * Gets the compound for the item.
	 * 
	 * @return tag
	 */
	public NBTTagCompound getTag();
}
