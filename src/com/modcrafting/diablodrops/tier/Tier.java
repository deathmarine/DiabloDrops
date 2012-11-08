package com.modcrafting.diablodrops.tier;

import org.bukkit.ChatColor;

public class Tier {
	String name;
	ChatColor color;
	int amt;
	int lvl;
	int chance;
	public Tier(String name, ChatColor color, int amt, int lvl, int chance){
		this.name=name;
		this.color=color;
		this.amt=amt;
		this.lvl=lvl;
		this.chance=chance;
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
}
