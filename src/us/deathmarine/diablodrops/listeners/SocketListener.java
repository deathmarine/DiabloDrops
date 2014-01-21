package us.deathmarine.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import us.deathmarine.diablodrops.DiabloDrops;
import us.deathmarine.diablodrops.events.PreSocketEnhancementEvent;
import us.deathmarine.diablodrops.events.SocketEnhancementEvent;

public class SocketListener implements Listener {
	private final DiabloDrops plugin;

	public SocketListener(final DiabloDrops instance) {
		plugin = instance;
	}

	@EventHandler
	public void burnGem(final FurnaceBurnEvent event) {
		Furnace furn = (Furnace) event.getBlock().getState();
		ItemStack tis = furn.getInventory().getSmelting();
		if (plugin.getItemAPI().isArmor(tis.getType())
				|| plugin.getItemAPI().isTool(tis.getType())) {
			for (String name : plugin.getConfig().getStringList(
					"SocketItem.Items"))
				if (event.getFuel().getType()
						.equals(Material.matchMaterial(name))) {
					ItemStack fuel = event.getFuel();
					if (fuel == null)
						continue;
					ItemMeta meta;
					if (fuel.hasItemMeta())
						meta = fuel.getItemMeta();
					else
						meta = Bukkit.getItemFactory().getItemMeta(
								fuel.getType());
					if (meta.hasDisplayName()) {
						ItemMeta tismeta;
						if (tis.hasItemMeta())
							tismeta = tis.getItemMeta();
						else
							tismeta = plugin.getServer().getItemFactory()
									.getItemMeta(tis.getType());
						if (tismeta.hasLore())
							for (String t : tismeta.getLore()) {
								if (t == null)
									continue;
								if (!ChatColor.stripColor(t).equals("(Socket)"))
									continue;
								if ((meta.getDisplayName().contains("Socket") || fuel
										.getType().equals(Material.SKULL_ITEM))) {
									ChatColor socketColor = findColor(t);
									ChatColor fuelColor = findColor(meta
											.getDisplayName());
									if ((fuelColor == socketColor)
											|| (socketColor == null)
											|| (socketColor == ChatColor.RESET)
											|| (socketColor == ChatColor.DARK_PURPLE)
											|| (socketColor == ChatColor.ITALIC)) {
										PreSocketEnhancementEvent psee = new PreSocketEnhancementEvent(
												tis, event.getFuel(), furn);
										plugin.getServer().getPluginManager()
												.callEvent(psee);
										if (psee.isCancelled()) {
											return;
										}
										plugin.furnanceMap.put(
												event.getBlock(),
												event.getFuel());
										plugin.getServer()
												.getScheduler()
												.scheduleSyncDelayedTask(
														plugin, new Runnable() {
															@Override
															public void run() {
																plugin.furnanceMap
																		.remove(event
																				.getBlock());
															}
														}, 20L * 30);
										event.setBurnTime(240);
										event.setBurning(true);
										return;
									}
								}
							}
					}
				}
			event.setCancelled(true);
			event.setBurning(false);
			event.setBurnTime(120000);
			return;
		}
	}

	public ChatColor findColor(final String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++)
			if ((c[i] == new Character((char) 167)) && ((i + 1) < c.length))
				return ChatColor.getByChar(c[i + 1]);
		return null;
	}

	private int getInt(String string, int fallback) {
		String[] args = string.replace("+", "").split(" ");
		if (args.length <= 1)
			return fallback;
		Integer level = null;
		try {
			level = Integer.parseInt(args[0]);
		} catch (Exception e) {
			level = fallback;
		}
		return level;
	}

	@EventHandler
	public void onSmeltSocket(final FurnaceSmeltEvent event) {
		if (!plugin.furnanceMap.containsKey(event.getBlock())
				&& !plugin.getItemAPI().isTool(event.getResult().getType()))
			return;
		ItemStack is = plugin.furnanceMap.remove(event.getBlock());
		if (is == null)
			return;
		Material fuel = is.getType();
		if (!is.hasItemMeta() || !is.getItemMeta().hasDisplayName())
			return;
		ChatColor fuelColor = findColor(is.getItemMeta().getDisplayName());

		ItemStack tool = event.getResult();
		ItemStack oldtool = event.getSource();
		ItemMeta metaold;
		if (oldtool.hasItemMeta())
			metaold = oldtool.getItemMeta();
		else
			metaold = Bukkit.getItemFactory().getItemMeta(oldtool.getType());

		if (!metaold.hasLore()) {
			event.setCancelled(true);
			event.setResult(oldtool);
			return;
		}

		boolean namTest = false;
		for (String n : metaold.getLore())
			if (ChatColor.stripColor(n).equalsIgnoreCase("(Socket)")) {
				namTest = true;
			}
		if (!namTest) {
			event.setResult(event.getSource());
			return;
		}

		tool.addUnsafeEnchantments(oldtool.getEnchantments());

		ItemMeta meta = tool.getItemMeta();
		if (fuel.equals(Material.SKULL_ITEM)) {
			ChatColor color = ChatColor.WHITE;
			if (metaold.hasDisplayName())
				color = findColor(metaold.getDisplayName());
			SkullMeta skull = (SkullMeta) is.getItemMeta();
			String skullName = skull.getOwner();
			if ((skullName == null) || (skullName.trim().length() < 1)) {
				switch (is.getData().getData()) {
				case 4: {
					skullName = "Creeper";
					break;
				}
				case 3: {
					skullName = "Steve";
					break;
				}
				case 0: {
					skullName = "Skeleton";
					break;
				}
				case 1: {
					skullName = "Wither";
					break;
				}
				case 2: {
					skullName = "Zombie";
					break;
				}
				default: {
					skullName = "";
					break;
				}
				}
			}
			String old = null;
			if (metaold.hasDisplayName())
				old = metaold.getDisplayName();
			if (old != null) {
				if (old.contains("'")) {
					old = old.split("'")[1].substring(2);
				}
				metaold.setDisplayName(color + skullName + "'s "
						+ ChatColor.stripColor(old));
			}
		}
		List<String> list = new ArrayList<String>();
		for (String s : metaold.getLore()) {
			list.add(s);
		}
		if (list.contains(fuelColor + "(Socket)")) {
			if (plugin.getItemAPI().isArmor(tool.getType())
					&& plugin.defenselore.size() > 0) {
				list.remove(fuelColor + "(Socket)");
				String effect = plugin.defenselore.get(plugin.getSingleRandom()
						.nextInt(plugin.defenselore.size()));
				String effectToRemove = null;
				for (String s : list) {
					String[] split = s.split(" ");
					if (split.length <= 1)
						continue;
					String[] splitEffect = effect.split(" ");
					if (splitEffect.length <= 1)
						continue;
					if (ChatColor.stripColor(split[1]).equals(splitEffect[1])) {
						effectToRemove = s;
						int level = getInt(s, 0) + getInt(effect, 0);
						String levelString = (level > 0) ? "+"
								+ String.valueOf(level) : String.valueOf(level);
						String newEffect = levelString;
						for (String s2 : splitEffect) {
							if (s2.equals(splitEffect[0]))
								continue;
							newEffect = newEffect + " " + s2;
						}
						effect = newEffect;
					}
				}
				if (effectToRemove != null)
					list.remove(effectToRemove);
				list.add(fuelColor + effect);
			} else if (plugin.getItemAPI().isTool(tool.getType())
					&& plugin.offenselore.size() > 0) {
				list.remove(fuelColor + "(Socket)");
				String effect = plugin.offenselore.get(plugin.getSingleRandom()
						.nextInt(plugin.offenselore.size()));
				String effectToRemove = null;
				for (String s : list) {
					String[] split = s.split(" ");
					if (split.length <= 1)
						continue;
					String[] splitEffect = effect.split(" ");
					if (splitEffect.length <= 1)
						continue;
					if (ChatColor.stripColor(split[1]).equals(splitEffect[1])) {
						effectToRemove = s;
						int level = getInt(s, 0) + getInt(effect, 0);
						String levelString = (level > 0) ? "+"
								+ String.valueOf(level) : String.valueOf(level);
						String newEffect = levelString;
						for (String s2 : splitEffect) {
							if (s2.equals(splitEffect[0]))
								continue;
							newEffect = newEffect + " " + s2;
						}
						effect = newEffect;
					}
				}
				if (effectToRemove != null)
					list.remove(effectToRemove);
				list.add(fuelColor + effect);
			}
		}
		SocketEnhancementEvent see = new SocketEnhancementEvent(
				event.getSource(), is, tool, ((Furnace) event.getBlock()
						.getState()));
		plugin.getServer().getPluginManager().callEvent(see);
		meta.setDisplayName(metaold.getDisplayName());
		meta.setLore(list);
		tool.setItemMeta(meta);
		tool.setDurability(oldtool.getDurability());
		event.setResult(tool);
		return;
	}
}
