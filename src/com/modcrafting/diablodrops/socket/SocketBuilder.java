package com.modcrafting.diablodrops.socket;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;

public class SocketBuilder
{
	DiabloDrops plugin;

	public SocketBuilder(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}
	public static enum SocketType{
		ARMOR(0),WEAPON(1),TOOL(2),ITEM(3);
		int n;
		SocketType(int i){
			n=i;
		}
		public Integer getType(){
			return n;
		}
	}
	public void build()
	{
		//Catch on FurnaceSmeltEvent
		List<String> l = plugin.config.getStringList("SocketItem.Items");
		for(String name: l){
			for(Material mat:plugin.drop.allItems()){
				FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(mat), Material.valueOf(name.toUpperCase()));
			    recipe.setInput(mat);
			    plugin.getServer().addRecipe(recipe);
				
			}
		}
	}
}
