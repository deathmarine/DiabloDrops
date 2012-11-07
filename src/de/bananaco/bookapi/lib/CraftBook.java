package de.bananaco.bookapi.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

public class CraftBook implements Book
{

	private final ItemStack s;

	public CraftBook(CraftItemStack itemstack) throws Exception
	{
		if (itemstack.getType() == Material.WRITTEN_BOOK
				|| itemstack.getType() == Material.BOOK_AND_QUILL)
		{
			// do nothing
		}
		else
		{
			throw new Exception(
					"CraftItemStack not Material.WRITTEN_BOOK or Material.BOOK_AND_QUILL");
		}
		s = itemstack.getHandle();
		if (s.tag == null)
		{
			s.tag = new NBTTagCompound();
		}
	}

	@Override
	public boolean hasTitle()
	{
		return s.tag.hasKey("title");
	}

	@Override
	public boolean hasAuthor()
	{
		return s.tag.hasKey("author");
	}

	@Override
	public boolean hasPages()
	{
		return s.tag.hasKey("pages");
	}

	@Override
	public String getTitle()
	{
		return s.tag.getString("title");
	}

	@Override
	public String getAuthor()
	{
		return s.tag.getString("author");
	}

	@Override
	public String[] getPages()
	{
		NBTTagList list = (NBTTagList) s.tag.get("pages");
		String[] pages = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			pages[i] = ((NBTTagString) list.get(i)).data;
		}
		return pages;
	}

	@Override
	public List<String> getListPages()
	{
		NBTTagList list = (NBTTagList) s.tag.get("pages");
		List<String> pages = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++)
		{
			pages.add(((NBTTagString) list.get(i)).data);
		}
		return pages;
	}

	@Override
	public void setTitle(String title)
	{
		String t = title;
		// sanity checking on the title
		if (t.length() > 16)
		{
			t = title.substring(0, 16);
		}
		s.tag.setString("title", t);
	}

	@Override
	public void setAuthor(String author)
	{
		String a = author;
		// sanity checking on the author
		if (a.length() > 16)
		{
			a = author.substring(0, 16);
		}
		s.tag.setString("author", a);
	}

	@Override
	public void setPages(String[] pages)
	{
		NBTTagList list = new NBTTagList();
		int size = pages.length;
		for (int i = 0; i < size; i++)
		{
			String page = pages[i];
			// sanity checking on the page
			if (page.length() > 256)
			{
				page = page.substring(0, 256);
			}
			if (page != null && !page.equals("") && !page.isEmpty())
			{
				NBTTagString p = new NBTTagString(page);
				p.setName(page);
				p.data = page;
				list.add(p);
			}
		}
		list.setName("pages");
		s.tag.set("pages", list);
	}

	@Override
	public void setPages(List<String> pages)
	{
		NBTTagList list = new NBTTagList();
		int size = pages.size();
		for (int i = 0; i < size; i++)
		{
			String page = pages.get(i);
			if (page.length() > 256)
			{
				page = page.substring(0, 256);
			}
			if (page != null && !page.equals("") && !page.isEmpty())
			{
				NBTTagString p = new NBTTagString(page);
				p.setName(page);
				p.data = page;
				list.add(p);
			}
		}
		list.setName("pages");
		s.tag.set("pages", list);
	}

	@Override
	public ItemStack getItemStack()
	{
		return s;
	}

}
