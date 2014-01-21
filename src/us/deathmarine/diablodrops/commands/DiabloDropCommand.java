package us.deathmarine.diablodrops.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.deathmarine.diablodrops.DiabloDrops;
import us.deathmarine.diablodrops.items.IdentifyTome;
import us.deathmarine.diablodrops.items.Socket;
import us.deathmarine.diablodrops.items.SockettedItem;
import us.deathmarine.diablodrops.sets.ArmorSet;
import us.deathmarine.diablodrops.tier.Tier;

public class DiabloDropCommand implements CommandExecutor {
	private DiabloDrops plugin;

	public DiabloDropCommand(final DiabloDrops plugin) {
		this.plugin = plugin;
	}

	public String combineSplit(final int startIndex, final String[] string) {
		StringBuilder builder = new StringBuilder();
		if (string.length >= 1) {
			for (int i = startIndex; i < string.length; i++) {
				builder.append(string[i]);
				builder.append(" ");
			}
			if (builder.length() > 1) {
				builder.deleteCharAt(builder.length() - 1);
				return builder.toString();
			}
		}
		return "";
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command,
			final String commandLabel, final String[] args) {
		if (!sender.hasPermission(command.getPermission())
				|| !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You cannot run this command.");
			return true;
		}
		Player player = (Player) sender;
		if (args.length < 1) {
			ItemStack ci = plugin.getDropAPI().getItem();
			while (ci == null)
				ci = plugin.getDropAPI().getItem();
			player.getInventory().addItem(ci);
			player.sendMessage(ChatColor.GREEN
					+ "You have been given a DiabloDrops item.");
			return true;
		}
		if (args[0].equalsIgnoreCase("tome")
				|| args[0].equalsIgnoreCase("book")) {
			player.getInventory().addItem(new IdentifyTome());
			player.sendMessage(ChatColor.GREEN
					+ "You have been given an Identify Tome.");
		} else if (args[0].equalsIgnoreCase("socket")
				|| args[0].equalsIgnoreCase("socketitem")) {
			List<String> l = plugin.getConfig().getStringList(
					"SocketItem.Items");
			player.getInventory().addItem(
					new Socket(Material.valueOf(l.get(
							plugin.getSingleRandom().nextInt(l.size()))
							.toUpperCase())));
			player.sendMessage(ChatColor.GREEN
					+ "You have been given a Socket Enhancement.");
		} else if (args[0].equalsIgnoreCase("socketted")
				|| args[0].equalsIgnoreCase("socketteditem")) {
			player.getInventory().addItem(
					new SockettedItem(plugin.getDropAPI().dropPicker()));
			player.sendMessage(ChatColor.GREEN
					+ "You have been given a Socket Item.");
		} else if (args[0].equalsIgnoreCase("repair")) {
			if (plugin.getDropAPI().canBeItem(player.getItemInHand().getType())) {
				player.getItemInHand().setDurability((short) 0);
				player.sendMessage(ChatColor.GREEN + "Item repaired.");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("reload")
				&& sender.hasPermission("diablodrops.reload")) {
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			plugin.getServer().getPluginManager().enablePlugin(plugin);
			plugin.reloadConfig();
			plugin.getLogger().info("Reloaded");
			sender.sendMessage(ChatColor.GREEN + "DiabloDrops Reloaded");
		} else if (args[0].equalsIgnoreCase("debug")) {
			int i = plugin.custom.size();
			plugin.getLogger().info(i + "]: Custom Items Loaded.");
			i = plugin.armorSets.size();
			plugin.getLogger().info(i + "]: ArmorSets Loaded.");
			i = plugin.tiers.size();
			plugin.getLogger().info(i + "]: Tiers Loaded.");
			i = plugin.prefix.size();
			plugin.getLogger().info(i + "]: Default Prefixes Loaded.");
			i = plugin.suffix.size();
			plugin.getLogger().info(i + "]: Default Suffixes Loaded.");
			i = plugin.hmprefix.size();
			plugin.getLogger().info(i + "]: Custom Prefixes Loaded.");
			i = plugin.hmsuffix.size();
			plugin.getLogger().info(i + "]: Custom Suffixes Loaded.");
			i = plugin.defenselore.size();
			plugin.getLogger().info(i + "]: Defense Lore Loaded.");
			i = plugin.offenselore.size();
			plugin.getLogger().info(i + "]: Offense Lore Loaded.");
			i = plugin.worlds.size();
			plugin.getLogger().info(i + "]: Worlds allowing Loaded.");
			if (args.length > 1 && args[1].equalsIgnoreCase("detailed")) {
				StringBuilder sb = new StringBuilder();
				sb.append("\n-----Custom-----\n");
				for (ItemStack tool : plugin.custom)
					sb.append(tool.getItemMeta().getDisplayName() + " ");
				sb.append("\n-----ArmorSet-----\n");
				for (ArmorSet a : plugin.armorSets)
					sb.append(a.getName() + " ");
				sb.append("\n-----Tiers-----\n");
				for (Tier a : plugin.tiers)
					sb.append(a.getName() + " ");
				sb.append("\n-----DefaultPrefix-----\n");
				for (String s : plugin.prefix)
					sb.append(s + " ");
				sb.append("\n-----DefaultSuffix-----\n");
				for (String s : plugin.suffix)
					sb.append(s + " ");
				sb.append("\n-----CustomPrefix-----\n");
				for (Material m : plugin.hmprefix.keySet()) {
					sb.append(m.toString() + "\n");
					for (String p : plugin.hmprefix.get(m))
						sb.append(p + " ");
				}
				sb.append("\n-----CustomSuffix-----\n");
				for (Material m : plugin.hmsuffix.keySet()) {
					sb.append(m.toString() + "\n");
					for (String p : plugin.hmsuffix.get(m))
						sb.append(p + " ");
				}
				sb.append("\n-----Defense Lore-----\n");
				for (String s : plugin.defenselore)
					sb.append(s + " ");
				sb.append("\n-----Offense Lore-----\n");
				for (String s : plugin.offenselore)
					sb.append(s + " ");
				plugin.getLogger().info(sb.toString());
			}
		} else if (args[0].equalsIgnoreCase("custom")) {
			if (plugin.custom.size() < 1)
				return true;
			ItemStack customItem = null;
			if (args.length > 1) {
				for (ItemStack is : plugin.custom) {
					String itemname = ChatColor.stripColor(plugin.getItemAPI()
							.getName(is));
					String titemname = combineSplit(1, args);
					if (itemname.equalsIgnoreCase(titemname)) {
						customItem = is;
						break;
					}
				}
			}
			if (customItem == null)
				customItem = plugin.custom.get(plugin.getSingleRandom()
						.nextInt(plugin.custom.size()));
			if (customItem != null) {
				player.getInventory().addItem(customItem);
				player.sendMessage(ChatColor.GREEN
						+ "You have been given "
						+ ChatColor.stripColor(plugin.getItemAPI().getName(
								customItem)) + " item.");
			}
		} else if (args[0].equalsIgnoreCase("give")) {
			if (args.length < 2) {
				sender.sendMessage(ChatColor.RED + "Not enough arguments.");
				return true;
			}
			List<String> stringList = new ArrayList<String>();
			for (String s : args) {
				if (s.equals(args[0]))
					continue;
				Player p = plugin.getServer().getPlayer(s);
				ItemStack giveItem = plugin.getDropAPI().getItem();
				while (giveItem == null)
					giveItem = plugin.getDropAPI().getItem();
				stringList.add(p.getName());
				p.getInventory().addItem(giveItem);
				p.sendMessage(ChatColor.GREEN
						+ "You have been given a DiabloDrops item.");
			}
			sender.sendMessage(ChatColor.GREEN + "You gave items to "
					+ ChatColor.WHITE
					+ stringList.toString().replace("[", "").replace("]", "")
					+ ChatColor.GREEN + ".");
			return true;
		} else if (args[0].equalsIgnoreCase("modify")) {
			if ((args.length < 2) || (player.getItemInHand() == null)
					|| player.getItemInHand().getType().equals(Material.AIR)) {
				sender.sendMessage(ChatColor.RED + "Not enough arguments.");
				return true;
			}
			ItemStack tool = player.getItemInHand();
			ItemMeta meta = tool.getItemMeta();
			if (args[1].equalsIgnoreCase("lore")) {
				if (args[2].equalsIgnoreCase("clear")) {
					meta.setLore(null);
					tool.setItemMeta(meta);
					player.sendMessage(ChatColor.GREEN
							+ "Cleared the lore for the item!");
					return true;
				}
				String lore = combineSplit(2, args);
				lore = ChatColor.translateAlternateColorCodes(
						"&".toCharArray()[0], lore);
				meta.setLore(Arrays.asList(lore.split(",")));
				tool.setItemMeta(meta);
				player.sendMessage(ChatColor.GREEN
						+ "Set the lore for the item!");
				return true;
			} else if (args[1].equalsIgnoreCase("name")) {
				if (args[2].equalsIgnoreCase("clear")) {
					tool.getItemMeta().setDisplayName(null);
					player.sendMessage(ChatColor.GREEN
							+ "Cleared the name for the item!");
					return true;
				}
				String name = combineSplit(2, args);
				name = ChatColor.translateAlternateColorCodes(
						"&".toCharArray()[0], name);
				meta.setDisplayName(name);
				tool.setItemMeta(meta);
				player.sendMessage(ChatColor.GREEN
						+ "Set the name for the item!");
				return true;
			} else if (args[1].equalsIgnoreCase("enchant")) {
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "Not enough arguments.");
					return true;
				}
				if (args[2].equalsIgnoreCase("clear")) {
					for (Enchantment e : Enchantment.values())
						tool.getItemMeta().removeEnchant(e);
					player.sendMessage(ChatColor.GREEN
							+ "Cleared the enchantments for the item!");
					return true;
				} else if (args.length > 4 && args[2].equalsIgnoreCase("add")) {
					int i = 1;
					try {
						i = Integer.parseInt(args[4]);
					} catch (NumberFormatException nfe) {
					}
					Enchantment ech = Enchantment.getByName(args[3]
							.toUpperCase());
					if (ech != null) {
						try {
							int l = Integer.parseInt(args[3]);
							ech = Enchantment.getById(l);
						} catch (NumberFormatException nfe) {
						}
					}
					if (ech != null) {
						tool.addUnsafeEnchantment(ech, i);
						player.sendMessage(ChatColor.GREEN
								+ "Added enchantment.");
					} else {
						player.sendMessage(ChatColor.RED + args[3]
								+ " :enchantment does not exist!");
					}
					return true;
				} else if (args[2].equalsIgnoreCase("remove")) {
					if (args.length > 3) {
						Map<Enchantment, Integer> hm = new HashMap<Enchantment, Integer>();
						for (Enchantment e1 : tool.getEnchantments().keySet())
							if (!e1.getName().equalsIgnoreCase(args[3]))
								hm.put(e1, tool.getEnchantmentLevel(e1));
						tool.addUnsafeEnchantments(hm);
						player.sendMessage(ChatColor.GREEN
								+ "Removed enchantment.");
						return true;
					}

					for (Enchantment e : Enchantment.values())
						tool.getItemMeta().removeEnchant(e);
					player.sendMessage(ChatColor.GREEN
							+ "Removed enchantments.");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("tier")) {
				//
				String name = "";
				Player p = null;
				for (String s : args) {
					if (StringUtils.containsIgnoreCase(s, "p:")) {
						s = s.replace("p:", "");
						p = Bukkit.getPlayer(s);
						continue;
					}
					if (!s.equals(args[0]))
						if (name.equals(""))
							name = s;
						else
							name = name + " " + s;
				}
				Tier tier = plugin.getDropAPI().getTier(name);
				ItemStack customItem = plugin.getDropAPI().getItem(tier);
				if (customItem != null && p != null) {
					p.getInventory().addItem(customItem);
					p.sendMessage(ChatColor.GREEN
							+ "You have been given a DiabloDrops item.");
					sender.sendMessage(ChatColor.WHITE + p.getName()
							+ ChatColor.GREEN
							+ " has been given a DiabloDrops item.");
				} else if (customItem != null && p == null
						&& sender instanceof Player) {
					player.getInventory().addItem(customItem);
					player.sendMessage(ChatColor.GREEN
							+ "You have been given a DiabloDrops item.");
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Either that is not a valid tier or you are unable to run this command.");
				}
				return true;
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage("You cannot run this command right now.");
				return true;
			} else {
				ItemStack ci2 = plugin.getDropAPI().getItem();
				while (ci2 == null) {
					ci2 = plugin.getDropAPI().getItem();
				}
				player.getInventory().addItem(ci2);
				player.sendMessage(ChatColor.GREEN
						+ "You have been given a DiabloDrops item.");
				return true;
			}
		}
		return true;
	}
}
