package com.modcrafting.diablodrops.tier;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Tier {
	String name;
	ChatColor color;
	int amt;
	int lvl;
	int chance;
	List<Material> l;
	public Tier(String name, ChatColor color, int amt, int lvl, int chance,List<Material> l){
		this.name=name;
		this.color=color;
		this.amt=amt;
		this.lvl=lvl;
		this.chance=chance;
		this.l=l;
	}
	public String getName(){
		return name;
	}
	public ChatColor getColor(){
		return color;
	}
	public Integer getAmount(){
		return amt;
	}
	public Integer getLevels(){
		return lvl;
	}
	public Integer getChance(){
		return chance;
	}
	public List<Material> getMaterials(){
		return l;
	}
}
