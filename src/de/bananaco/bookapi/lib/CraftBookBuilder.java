package de.bananaco.bookapi.lib;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import de.bananaco.bookapi.lib.Book;
import de.bananaco.bookapi.lib.BookBuilder;
import de.bananaco.bookapi.lib.CraftBook;

/**
 * The implementation of BookBuilder
 */
public class CraftBookBuilder extends BookBuilder
{

	static
	{
		// create the instance of the thing we need
		BookBuilder.instance = new CraftBookBuilder();
	}

	@Override
	public Book getBook(ItemStack itemstack)
	{
		try
		{
			return new CraftBook((CraftItemStack) itemstack);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
