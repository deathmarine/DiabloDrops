package com.modcrafting.toolapi.lib;

import com.modcrafting.diablodrops.drops.DropType;

public interface ToolInterface {
	/**
	 * Gets the name of the Tool
	 * Will return null if not named
	 * @returns String 
	 */
	public String getName();
	/**
	 * Sets the name of the Tool
	 */
	public void setName(String name);
	/**
	 * Gets the cost to repair
	 * @return Integer
	 */
	public Integer getRepairCost();
	/**
	 * Sets the cost to repair
	 */
	public void setRepairCost(Integer i);
	/**
	 * Gets the type of drop
	 * @return DropType
	 */
	public DropType getDropType();
}
