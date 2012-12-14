package com.modcrafting.diablodrops.builders;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.items.IdentifyTome;

public class SocketBuilder
{
    DiabloDrops plugin;

    public SocketBuilder(DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    public void build()
    {
        // Catch on FurnaceSmeltEvent
        List<String> l = plugin.config.getStringList("SocketItem.Items");
        for (String name : l)
        {
            for (Material mat : plugin.drop.allItems())
            {
                FurnaceRecipe recipe = new FurnaceRecipe(new ItemStack(mat),
                        Material.valueOf(name.toUpperCase()));
                recipe.setInput(mat);
                plugin.getServer().addRecipe(recipe);

            }
        }
        ShapelessRecipe re = new ShapelessRecipe(new IdentifyTome());
        re.addIngredient(3, Material.BOOK);
        re.addIngredient(Material.EYE_OF_ENDER);
        plugin.getServer().addRecipe(re);

    }
}
