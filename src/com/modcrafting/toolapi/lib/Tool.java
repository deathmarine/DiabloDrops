package com.modcrafting.toolapi.lib;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

import com.modcrafting.diablodrops.drops.DropType;
public class Tool extends CraftItemStack implements ToolInterface {
	private NBTTagCompound tag;
	private DropType type;
	public Tool(Material mat, DropType kind) {
		super(mat, 1);
		ItemStack mitem = this.getHandle();
        if(mitem.tag == null) {
            mitem.tag = new NBTTagCompound();
        }
        this.tag=mitem.tag;
        this.type=kind;
	}

	@Override
	public String getName() {
		NBTTagCompound nc = tag.getCompound("display");
		if(nc!=null){
			String s = nc.getString("Name");
			if(s!=null) return s;
		}
		return null;
	}

	@Override
	public void setName(String name) {
			NBTTagCompound ntag = new NBTTagCompound();
	        NBTTagString p = new NBTTagString(name);
	        p.setName(name);
	        p.data = name;
	        ntag.set("Name",p);
	        ntag.setString("Name", name);
	        tag.setCompound("display", ntag);		
	}

	@Override
	public Integer getRepairCost() {
		return tag.getInt("RepairCost");	
	}

	@Override
	public void setRepairCost(Integer i) {
		tag.setInt("RepairCost",i);		
	}
	
	@Override
	public DropType getDropType(){
		return type;
	}

}