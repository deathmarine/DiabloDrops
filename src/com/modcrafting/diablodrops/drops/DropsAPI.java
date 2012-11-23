package com.modcrafting.diablodrops.drops;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.socket.gem.SocketItem;
import com.modcrafting.diablodrops.tier.Drop;
import com.modcrafting.diablodrops.tier.Tier;
import com.modcrafting.diablodrops.tier.Tome;
import com.modcrafting.toolapi.lib.Tool;
import com.stirante.PrettyScaryLib.Namer;

public class DropsAPI
{
	private Random gen = new Random();
	private Drops drops;
	private DiabloDrops plugin;
	public DropsAPI(DiabloDrops instance)
	{
		plugin = instance;
		drops = instance.drop;
	}

	/**
	 * Returns an itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem()
	{
		if (gen.nextBoolean()
			&&plugin.config.getBoolean("IdentifyTome.Enabled", true)
			&& gen.nextInt(100) <= plugin.config.getInt("IdentifyTome.Chance", 3))
		{
				return new Tome();
		}
		if (gen.nextBoolean()
			&&plugin.config.getBoolean("SocketItem.Enabled", true)
			&&gen.nextInt(100) <= plugin.config.getInt("SocketItem.Chance", 5))
		{
				List<String> l = plugin.config.getStringList("SocketItem.Items");
				return new SocketItem(Material.valueOf(l.get(gen.nextInt(l.size())).toUpperCase()));
		}
		if (gen.nextBoolean()
			&&plugin.config.getBoolean("Custom.Enabled", true)
			&& gen.nextInt(100) <= plugin.config.getInt("Custom.Chance", 1))
		{
			return plugin.custom.get(plugin.gen.nextInt(plugin.custom.size()));
		}
		return getItem(dropPicker());
	}

	/**
	 * Returns a specific type of item randomly generated
	 * 
	 * @param material
	 *            Material of itemstack
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(Material mat)
	{
		if (mat == null)
			return null;
		CraftItemStack ci = null;
		int attempts = 0;
		while (ci == null && attempts < 10)
		{
			for (Tier tier : plugin.tiers)
			{
				if (gen.nextInt(100) <= tier.getChance())
				{
					if(tier.getMaterials().size()>0&&!tier.getMaterials().contains(mat))
						return null;
					int e = tier.getAmount();
					int l = tier.getLevels();
					short damage = 0;
					if (plugin.config.getBoolean("DropFix.Damage", true))
					{
						damage = damageItemStack(mat);
					}
					if (plugin.config.getBoolean("Display.TierName", true)&&!tier.getColor().equals(ChatColor.MAGIC))
					{
						ci = new Drop(mat, tier.getColor(),
								ChatColor.stripColor(name()),
								damage, tier.getColor()
										+ tier.getName());
					}
					else
					{
						ci = new Drop(mat, tier.getColor(),
								ChatColor.stripColor(name()),
								damage);
					}
					if(tier.getColor().equals(ChatColor.MAGIC)) return ci;
					for (; e > 0; e--)
					{
						int lvl = gen.nextInt(l + 1);
						Enchantment ench = drops.enchant();
						if (lvl != 0 && ench != null
								&& !tier.getColor().equals(ChatColor.MAGIC))
						{
							if (plugin.config.getBoolean("SafeEnchant.Enabled",
									true))
							{
								if (ench.canEnchantItem(ci))
								{
									if (lvl >= ench.getStartLevel()
											&& lvl <= ench.getMaxLevel())
									{
										try
										{
											ci.addEnchantment(ench, lvl);
										}
										catch (Exception e1)
										{
											e++;
										}
									}
								}
							}
							else
							{
								ci.addUnsafeEnchantment(ench, lvl);
							}
						}
					}
					if (plugin.config.getBoolean("SocketItem.Enabled", true)
							&& gen.nextInt(100) <= plugin.config.getInt(
									"SocketItem.Chance", 5)
							&& !tier.getColor().equals(ChatColor.MAGIC))
					{
						Namer.addLore(ci, "(Socket)");
						return ci;
					}
					if (plugin.config.getBoolean("Lore.Enabled", true)
							&& gen.nextInt(100) <= plugin.config.getInt(
									"Lore.Chance", 5)
							&& !tier.getColor().equals(ChatColor.MAGIC))
					{
						Tool tool = new Tool(ci);
						for (int i = 0; i < plugin.config.getInt(
								"Lore.EnhanceAmount", 2); i++)
						{
							tool.setLore(plugin.lore.get(plugin.gen
									.nextInt(plugin.lore.size())));
						}
						return tool;
					}
					return ci;
				}
			}
			attempts++;
		}
		return null;
	}

	public CraftItemStack getIdItem(Material mat, String name)
	{
		if (mat == null)
			return null;
		CraftItemStack ci = null;
		while (ci == null)
		{
			for (Tier tier : plugin.tiers)
			{
				if (gen.nextInt(100) <= tier.getChance())
				{
					if(tier.getMaterials().size()>0&&!tier.getMaterials().contains(mat)){
						mat = tier.getMaterials().get(gen.nextInt(tier.getMaterials().size()));
					}
					int e = tier.getAmount();
					int l = tier.getLevels();
					short damage = 0;
					if (plugin.config.getBoolean("DropFix.Damage", true))
					{
						damage = damageItemStack(mat);
					}
					if (plugin.config.getBoolean("Display.TierName", true)&&!tier.getColor().equals(ChatColor.MAGIC))
					{
						ci = new Drop(mat, tier.getColor(),
								ChatColor.stripColor(name()),
								damage, tier.getColor()
										+ tier.getName());
					}
					else
					{
						ci = new Drop(mat, tier.getColor(),
								ChatColor.stripColor(name()),
								damage);
					}
					if(tier.getColor().equals(ChatColor.MAGIC)) return ci;
					for (; e > 0; e--)
					{
						int lvl = gen.nextInt(l + 1);
						Enchantment ench = drops.enchant();
						if (lvl != 0 && ench != null)
						{
							if (plugin.config.getBoolean("SafeEnchant.Enabled",
									true))
							{
								if (ench.canEnchantItem(ci))
								{
									if (lvl >= ench.getStartLevel()
											&& lvl <= ench.getMaxLevel())
									{
										try
										{
											ci.addEnchantment(ench, lvl);
										}
										catch (Exception e1)
										{
											e++;
										}
									}
								}
							}
							else
							{
								ci.addUnsafeEnchantment(ench, lvl);
							}
						}
					}
					if (plugin.config.getBoolean("SocketItem.Enabled", true)
							&& gen.nextInt(100) <= plugin.config.getInt(
									"SocketItem.Chance", 5))
					{
						Namer.addLore(ci, "(Socket)");
						return ci;
					}
					if (plugin.config.getBoolean("Lore.Enabled", true)
							&& gen.nextInt(100) <= plugin.config.getInt(
									"Lore.Chance", 5))
					{
						Tool tool = new Tool(ci);
						for (int i = 0; i < plugin.config.getInt(
								"Lore.EnhanceAmount", 2); i++)
						{
							tool.setLore(plugin.lore.get(plugin.gen
									.nextInt(plugin.lore.size())));
						}
						return tool;
					}
				}
			}
		}
		return ci;
	}

	/**
	 * Returns an specific typename of itemstack that was randomly generated
	 * 
	 * @return CraftItemStack
	 */
	public CraftItemStack getItem(String type)
	{
		Material mat = dropPicker();
		if (mat == null)
			return null;
		CraftItemStack ci = null;
		int attempts = 0;
		while (ci == null && attempts < 10)
		{
			for (Tier tier : plugin.tiers)
			{
				if (tier.getName().equalsIgnoreCase(type))
				{
					if (gen.nextInt(100) <= tier.getChance())
					{
						if(tier.getMaterials().size()>0&&!tier.getMaterials().contains(mat)){
							mat = tier.getMaterials().get(gen.nextInt(tier.getMaterials().size()));
						}
						int e = tier.getAmount();
						int l = tier.getLevels();
						short damage = 0;
						if (plugin.config.getBoolean("DropFix.Damage", true))
						{
							damage = damageItemStack(mat);
						}
						if (plugin.config.getBoolean("Display.TierName", true)&&!tier.getColor().equals(ChatColor.MAGIC))
						{
							ci = new Drop(mat, tier.getColor(),
									ChatColor.stripColor(name()),
									damage, tier.getColor()
											+ tier.getName());
						}
						else
						{
							ci = new Drop(mat, tier.getColor(),
									ChatColor.stripColor(name()),
									damage);
						}
						if(tier.getColor().equals(ChatColor.MAGIC)) return ci;
						for (; e > 0; e--)
						{
							int lvl = gen.nextInt(l + 1);
							Enchantment ench = drops.enchant();
							if (lvl != 0 && ench != null
									&& !tier.getColor().equals(ChatColor.MAGIC))
							{
								if (plugin.config.getBoolean(
										"SafeEnchant.Enabled", true))
								{
									if (ench.canEnchantItem(ci))
									{
										if (lvl >= ench.getStartLevel()
												&& lvl <= ench.getMaxLevel())
										{
											try
											{
												ci.addEnchantment(ench, lvl);
											}
											catch (Exception e1)
											{
												e++;
											}
										}
									}
								}
								else
								{
									ci.addUnsafeEnchantment(ench, lvl);
								}
							}
						}
						if (plugin.config
								.getBoolean("SocketItem.Enabled", true)
								&& gen.nextInt(100) <= plugin.config.getInt(
										"SocketItem.Chance", 5)
								&& !tier.getColor().equals(ChatColor.MAGIC))
						{
							Namer.addLore(ci, "(Socket)");
							return ci;
						}
						if (plugin.config.getBoolean("Lore.Enabled", true)
								&& gen.nextInt(100) <= plugin.config.getInt(
										"Lore.Chance", 5)
								&& !tier.getColor().equals(ChatColor.MAGIC))
						{
							Tool tool = new Tool(ci);
							for (int i = 0; i < plugin.config.getInt(
									"Lore.EnhanceAmount", 2); i++)
							{
								tool.setLore(plugin.lore.get(plugin.gen
										.nextInt(plugin.lore.size())));
							}
							return tool;
						}
						return ci;
					}
				}
			}
			attempts++;
		}
		return null;
	}

	/**
	 * Returns a Material that was randomly picked
	 * 
	 * @return Material
	 */
	public Material dropPicker()
	{
		int next = gen.nextInt(9);
		switch (next)
		{
			case 1:
				return drops.getHelmet();
			case 2:
				return drops.getChestPlate();
			case 3:
				return drops.getLeggings();
			case 4:
				return drops.getBoots();
			case 5:
				return drops.getHoe();
			case 6:
				return drops.getPickaxe();
			case 7:
				return drops.getAxe();
			case 8:
				return drops.getSpade();
			default:
				return drops.getSword();

		}
	}

	public short damageItemStack(Material itemstack)
	{
		short dur = itemstack.getMaxDurability();
		try
		{
			int newDur = gen.nextInt(dur + 1);
			return (short) newDur;
		}
		catch (Exception e)
		{
		}
		return dur;
	}

	public boolean canBeItem(Material material)
	{
		if (drops.isArmor(material) || drops.isTool(material))
			return true;
		return false;
	}

	public String name()
	{
		String prefix = plugin.prefix.get(gen.nextInt(plugin.prefix.size()));
		String suffix = plugin.suffix.get(gen.nextInt(plugin.suffix.size()));
		return prefix + " " + suffix;
	}

	public boolean matchesTier(String type)
	{
		for (Tier tier : plugin.tiers)
		{
			if (tier.getName().equalsIgnoreCase(type))
				return true;
		}
		return false;
	}

	public Tier getTier(String name)
	{
		for (Tier tier : plugin.tiers)
		{
			if (tier.getName().equalsIgnoreCase(name))
				return tier;
		}
		return null;
	}

	public boolean wearingSet(Player player)
	{
		ItemStack his = player.getInventory().getHelmet();
		ItemStack cis = player.getInventory().getChestplate();
		ItemStack lis = player.getInventory().getLeggings();
		ItemStack bis = player.getInventory().getBoots();
		if (his == null || cis == null || lis == null || bis == null)
			return false;
		Set<ItemStack> sis = new HashSet<ItemStack>();
		sis.add(cis);
		sis.add(lis);
		sis.add(bis);
		String potentialSet = new String();
		CraftItemStack chis = ((CraftItemStack) his);
		net.minecraft.server.ItemStack mistack = chis.getHandle();
		NBTTagCompound tag = mistack.tag;
		if (tag == null)
		{
			tag = new NBTTagCompound();
			tag.setCompound("display", new NBTTagCompound());
			mistack.tag = tag;
		}
		String[] ss = ChatColor.stripColor(tag.getString("Name")).split(" ");
		potentialSet = ss[0];
		for (ItemStack is : sis)
		{
			CraftItemStack c = ((CraftItemStack) is);
			net.minecraft.server.ItemStack cstack = c.getHandle();
			NBTTagCompound cstacktag = cstack.tag;
			if (cstacktag == null)
			{
				cstacktag = new NBTTagCompound();
				cstacktag.setCompound("display", new NBTTagCompound());
				cstack.tag = cstacktag;
			}
			String[] splits = ChatColor.stripColor(cstacktag.getString("Name"))
					.split(" ");
			if (!splits[0].equalsIgnoreCase(potentialSet))
				return false;
		}
		return true;
	}

	public Tool getItem(Tool tool)
	{
		for (Tier tier : plugin.tiers)
		{
			if (gen.nextInt(100) <= tier.getChance())
			{
				int e = tier.getAmount();
				int l = tier.getLevels();
				tool.setName(tier.getColor() + name());
				for (; e > 0; e--)
				{
					int lvl = gen.nextInt(l + 1);
					Enchantment ench = drops.enchant();
					if (lvl != 0 && ench != null
							&& !tier.getColor().equals(ChatColor.MAGIC))
					{
						if (plugin.config.getBoolean("SafeEnchant.Enabled",
								true))
						{
							try
							{
								tool.addEnchantment(ench, lvl);
							}
							catch (Exception e1)
							{
								e++;
							}
						}
						else
						{
							tool.addUnsafeEnchantment(ench, lvl);
						}
					}

				}

			}
			boolean sock = false;
			if (plugin.config.getBoolean("SocketItem.Enabled", true)
					&& gen.nextInt(100) <= plugin.config.getInt(
							"SocketItem.Chance", 5)
					&& !tier.getColor().equals(ChatColor.MAGIC))
			{
				Namer.addLore(tool, "(Socket)");
				sock = true;
			}
			if (plugin.config.getBoolean("Lore.Enabled", true)
					&& gen.nextInt(100) <= plugin.config.getInt("Lore.Chance",
							5) && !tier.getColor().equals(ChatColor.MAGIC)
					&& !sock)
			{
				for (int i = 0; i < plugin.config.getInt("Lore.EnhanceAmount",
						2); i++)
				{
					tool.setLore(plugin.lore.get(plugin.gen.nextInt(plugin.lore
							.size())));
				}
			}
		}
		return tool;
	}
}
