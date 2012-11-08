package com.modcrafting.skullapi.lib;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagString;

import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class Skull extends CraftItemStack implements SkullInterface{
	
	public Skull(int type) throws Exception {
		super(type);
		if(type!=397) throw new Exception("Item must be a skull."); 
	}
	public enum SkullType{
		SKELETON(0),
		WITHER(1),
		ZOMBIE(2),
		PLAYER(3),
		CREEPER(4);
		public int type;
		private SkullType(int i){
			type=i;
		}
		public int getData(){
			return type;
		}
		
	}
	@Override
	public void setOwner(String name){
    	this.setDurability((short)3);
    	NBTTagCompound tag = new NBTTagCompound();
    	tag.set("SkullOwner", new NBTTagString("SkullOwner", name));
    	this.getHandle().setTag(tag);
	}
	@Override
	public String getOwner(String name){
		if(this.getHandle().tag!=null&&this.getHandle().tag.getString("SkullOwner")!=null)
			return this.getHandle().tag.getString("SkullOwner");
		return null;
	}
	@Override
	public void setSkullType(SkullType type){
		this.getHandle().setData(type.getData());
	}
	@Override
	public SkullType getSkullType(){
		return SkullType.values()[this.getHandle().getData()];
	}

}
