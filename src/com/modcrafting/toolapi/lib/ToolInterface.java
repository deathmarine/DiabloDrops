package com.modcrafting.toolapi.lib;

<<<<<<< HEAD
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
=======
import java.util.List;

public interface ToolInterface
{
	/**
	 * Gets the name of the Tool Will return null if not named
	 * 
	 * @returns String
	 */
	public String getName();

	/**
	 * Sets the name of the Tool
	 */
	public void setName(String name);

	/**
	 * Gets the cost to repair
	 * 
	 * @return Integer
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
	 */
	public String[] getLore();
>>>>>>> refs/remotes/fork/master
}
