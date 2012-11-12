package com.modcrafting.diablodrops.socket;

import java.lang.reflect.Field;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.IMerchant;
import net.minecraft.server.InventoryMerchant;

import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.enchantments.Enchantment;

import com.modcrafting.skullapi.lib.Skull;
import com.modcrafting.toolapi.lib.Tool;

public class MerchantInv extends CraftInventory
{
	public MerchantInv(EntityHuman p, IMerchant imerchant, Tool item)
	{
		super(new Merch(p, null, item));
	}

	static class Merch extends InventoryMerchant implements IInventory
	{
		Tool tool;
		EntityHuman entityhuman;

		public Merch(EntityHuman entityhuman, IMerchant imerchant, Tool tool)
		{
			super(entityhuman, imerchant);
			this.entityhuman = entityhuman;
			this.tool = tool;
		}

		@Override
		public void g()
		{
			try
			{
				Field frecipe = InventoryMerchant.class
						.getDeclaredField("recipe");
				frecipe.setAccessible(true);
				// TODO: Determine what to allow in socket, Test I have no clue
				// if this will work.
				Tool newtool = (Tool) tool.clone();
				for (Enchantment ench : newtool.getEnchantments().keySet())
				{
					newtool.addEnchantment(ench, 10);
				}
				frecipe.set(entityhuman, new MerchantRec(tool.getHandle(),
						new Skull(397).getHandle(), newtool.getHandle()));
			}
			catch (Exception ex)
			{
				// entityhuman.closeInventory();
				ex.printStackTrace();
			}

		}
	}
}
