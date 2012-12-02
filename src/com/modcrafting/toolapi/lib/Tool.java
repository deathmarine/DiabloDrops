package com.modcrafting.toolapi.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class Tool extends CraftItemStack implements ToolInterface
{
    public Tool(ItemStack item)
    {
        super(item);
        tag();
    }

    public Tool(Material mat)
    {
        super(mat, 1);
        tag();
    }

    public Tool(org.bukkit.inventory.ItemStack source)
    {
        super(((CraftItemStack) source).getHandle());
        tag();
    }

    @Override
    public void addLore(String string)
    {
    	NBTTagCompound tag = getTag();
        NBTTagCompound ntag = tag.getCompound("display");
        if (ntag == null)
            ntag = new NBTTagCompound("display");
        NBTTagList p = ntag.getList("Lore");
        if (p == null)
            p = new NBTTagList("Lore");
        p.add(new NBTTagString("", string));
        ntag.set("Lore", p);
        tag.setCompound("display", ntag);
        setTag(tag);
    }

    @Override
    public String[] getLore()
    {
    	NBTTagCompound tag = getTag();
        ArrayList<String> strings = new ArrayList<String>();
        String[] lores = new String[] {};
        if (tag == null || tag.getCompound("display") == null
                || tag.getCompound("display").getList("Lore") == null)
            return lores;
        NBTTagList list = tag.getCompound("display").getList("Lore");
        for (int i = 0; i < list.size(); i++)
            strings.add(((NBTTagString) list.get(i)).data);
        strings.toArray(lores);
        return lores;
    }

    @Override
    public List<String> getLoreList()
    {
    	NBTTagCompound tag = getTag();
        ArrayList<String> strings = new ArrayList<String>();
        if (tag == null || tag.getCompound("display") == null
                || tag.getCompound("display").getList("Lore") == null)
            return strings;
        NBTTagList list = tag.getCompound("display").getList("Lore");
        for (int i = 0; i < list.size(); i++)
        {
            NBTTagString n = (NBTTagString) list.get(i);
            if (n != null)
            {
                if (n.data != null || !(n.data.length() < 1))
                    strings.add(n.data);
            }
        }
        return strings;
    }

    @Override
    public String getName()
    {
        return this.getHandle().r();
    }

    @Override
    public void setName(String name)
    {
    	this.getHandle().c(name);
    }

    @Override
    public Integer getRepairCost()
    {
        return this.getHandle().getRepairCost();
    }

    @Override
    public void setRepairCost(Integer i)
    {
    	this.getHandle().setRepairCost(i);
    }

    @Override
    public void setLore(List<String> lore)
    {
    	NBTTagCompound tag = getTag();
        NBTTagCompound ntag = tag.getCompound("display");
        if (ntag == null)
            ntag = new NBTTagCompound();
        NBTTagList p = new NBTTagList();
        for (String s : lore)
        {
            p.add(new NBTTagString("", s.trim()));
        }
        ntag.set("Lore", p);
        tag.setCompound("display", ntag);
        setTag(tag);
    }

    @Override
    public NBTTagCompound getTag()
    {
        return this.getHandle().getTag();
    }

    @Override
    public void setTag(NBTTagCompound tag)
    {
    	this.getHandle().setTag(tag);
    }

    private void tag()
    {
    	ItemStack oItem = this.getHandle();
    	if(!oItem.hasTag()){
            setTag(new NBTTagCompound());
        }
    }
}
