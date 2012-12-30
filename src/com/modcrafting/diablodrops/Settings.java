package com.modcrafting.diablodrops;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
	private double socket;
	private double tome;
	private double standard;
	private double lore;
	private double custom;
	public Settings(FileConfiguration fc){
		socket = fc.getDouble("SocketItem.Chance",5.0);
		tome = fc.getDouble("IdentifyTome.Chance",2.0);
		standard = fc.getDouble("Percentages.ChancePerSpawn",2.0);
		lore = fc.getDouble("Lore.Chance",2.0);
		custom = fc.getDouble("Custom.Chance",2.0);
	}
	public int getSocketChance(){
		return (int) (socket*100);
	}
	public int getTomeChance(){
		return (int) (tome*100);
	}
	public int getStandardChance(){
		return (int) (standard*100);
	}
	public int getLoreChance(){
		return (int) (lore*100);
	}
	public int getCustomChance(){
		return (int) (custom*100);
	}
	

}
