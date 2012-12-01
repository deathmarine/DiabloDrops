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
    private NBTTagCompound tag;

	public Tool(Material mat)
	{
		super(mat, 1);
		tag();
	}
	public Tool(ItemStack item)
	{
		super(item);
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
	public void setName(String name)
	{
		NBTTagCompound nc = tag.getCompound("display");
		if (nc != null)
			nc = new NBTTagCompound();
		NBTTagString p = new NBTTagString(name);
		p.setName(name);
		p.data = name;
		nc.set("Name", p);
		nc.setString("Name", name);
		tag.setCompound("display", nc);
		update();
	}
    @Override
    public String[] getLore()
    {
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
                if (n.getName() != null || !(n.getName().length() < 1))
                    strings.add(n.getName());
                if (n.data != null || !(n.data.length() < 1))
                    strings.add(n.data);
                if (n.toString() != null || !(n.toString().length() < 1))
                    strings.add(n.toString());
            }
        }
        return strings;
    }

	@Override
	public void setLore(List<String> lore)
	{
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
		update();
	}

    @Override
    public String getName()
    {
        NBTTagCompound nc = tag.getCompound("display");
        if (nc != null)
        {
            String s = nc.getString("Name");
            if (s != null)
                return s;
        }
        return null;
    }

    @Override
    public Integer getRepairCost()
    {
    	tag();
        return tag.getInt("RepairCost");
    }
	@Override
	public void setRepairCost(Integer i) {
		tag.setInt("RepairCost", i);
		update();
	}
	@Override
	public NBTTagCompound getTag() {
		return this.tag;
	}
    @Override
    public void setTag(NBTTagCompound tag)
    {
        this.tag = tag;
        update();
    }
	private void tag(){
		ItemStack mitem = this.getHandle();
		if (mitem == null)
		{
			return;
		}
		if (mitem.tag == null)
		{
			mitem.tag = new NBTTagCompound();
		}
		this.tag = mitem.tag;
	}
	private void update(){
		this.getHandle().tag=tag;
	}
}
