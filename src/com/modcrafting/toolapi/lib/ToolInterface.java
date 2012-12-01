package com.modcrafting.toolapi.lib;

import java.util.List;

import net.minecraft.server.NBTTagCompound;

public interface ToolInterface
{
    /**
     * Sets a single line for lore.
     * 
     * @param string
     */
    public void addLore(String string);

    /**
     * Returns the lore of the Tool
     * 
     * @return lore
     */
    public String[] getLore();

    /**
     * Get the lore of the Tool as a list
     * 
     * @return list
     */
    public List<String> getLoreList();

    /**
     * Gets the name of the Tool Will return null if not named
     * 
     * @returns name
     */
    public String getName();

    /**
     * Gets the cost to repair
     * 
     * @return repair cost
     */
    public Integer getRepairCost();

    /**
     * Gets the compound for the item.
     * 
     * @return tag
     */
    public NBTTagCompound getTag();

    /**
     * Sets the lore of the Tool
     */
    public void setLore(List<String> lore);

    /**
     * Sets the name of the Tool
     */
    public void setName(String name);

    /**
     * Sets the cost to repair
     */
    public void setRepairCost(Integer i);

    /**
     * Sets the compound for the item.
     * 
     * @param tag
     */
    public void setTag(NBTTagCompound tag);
}
