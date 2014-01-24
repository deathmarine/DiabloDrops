package us.deathmarine.diablodrops.builders;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

import us.deathmarine.diablodrops.DiabloDrops;

public class SocketBuilder {
	DiabloDrops plugin;

	public SocketBuilder(DiabloDrops plugin) {
		this.plugin = plugin;
	}

	/**
	 * Clears and then populates plugin's socket list; also adds identification
	 * tome
	 */
	public void build() {
		List<String> l = plugin.getConfig().getStringList("SocketItem.Items");
		for (String name : l) {
			for (Material mat : plugin.getItemAPI().allItems()) {
				FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(mat),
						Material.valueOf(name.toUpperCase()));
				recipe.setInput(mat);
				plugin.getServer().addRecipe(recipe);

			}
		}
	}
}
