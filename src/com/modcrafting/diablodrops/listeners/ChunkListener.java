package com.modcrafting.diablodrops.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;

import com.modcrafting.diablodrops.DiabloDrops;

public class ChunkListener implements Listener
{

	private DiabloDrops plugin;

	public ChunkListener(DiabloDrops plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent event)
	{
		if (!plugin.config.getBoolean("Ruins.Enabled", true))
			return;
		Chunk chunk = event.getChunk();
		if (plugin.gen.nextInt(100) != 1)
			return;
		int realX = chunk.getX() * 16 + plugin.gen.nextInt(15);
		int realZ = chunk.getX() * 16 + plugin.gen.nextInt(15);
		int realY = chunk.getWorld().getHighestBlockYAt(realX, realZ);
		Block block = chunk.getWorld().getBlockAt(realX, realY, realZ);
		Block blockUnder = block.getRelative(BlockFace.DOWN);
		if (blockUnder.getType() != Material.GRASS
				&& blockUnder.getType() != Material.DIRT)
			return;
		block.setType(Material.CHEST);
		plugin.getServer().broadcastMessage(
				"DiabloDrops Chest spawned at " + String.valueOf(realX) + " "
						+ String.valueOf(realY) + " " + String.valueOf(realZ));
		addPattern(block);
		Chest chestB = ((Chest) block.getState());
		Inventory chest = chestB.getBlockInventory();
		for (int i = 0; i < plugin.gen.nextInt(chest.getSize()); i++)
		{
			CraftItemStack cis = plugin.dropsAPI.getItem();
			while (cis == null)
				cis = plugin.dropsAPI.getItem();
			chest.setItem(i, cis);
		}
	}

	public Block getBlockAt(World world, int x, int y, int z)
	{
		return world.getBlockAt(x, y, z);
	}

	public void setBlockAt(World world, int x, int y, int z, int id)
	{
		getBlockAt(world, x, y, z).setTypeId(id);
	}

	public void setBlockAt(World world, int x, int y, int z, int id, int data)
	{
		getBlockAt(world, x, y, z).setTypeId(id);
		switch (data)
		{
			case 1:
				byte b1 = 1;
				getBlockAt(world, x, y, z).setData(b1);
			case 2:
				byte b2 = 2;
				getBlockAt(world, x, y, z).setData(b2);
			case 3:
				byte b3 = 3;
				getBlockAt(world, x, y, z).setData(b3);
			default:
				byte bd = 0;
				getBlockAt(world, x, y, z).setData(bd);
		}
	}

	private void addPattern(Block block)
	{
		World world = block.getWorld();
		Location location = block.getRelative(BlockFace.DOWN).getLocation();
		Block start = world.getBlockAt(location);
		north(start);
		south(start);
		east(start);
		west(start);
		Location nw = northwest(start);
		pillar(world, nw);
		Location ne = northeast(start);
		pillar(world, ne);
		Location sw = southwest(start);
		pillar(world, sw);
		Location se = southeast(start);
		pillar(world, se);
		Block newStart = world.getBlockAt(new Location(world, location
				.getBlockX(), location.getBlockY() + 3, location.getBlockZ()));
		north(newStart);
		south(newStart);
		east(newStart);
		west(newStart);
		northwest(newStart);
		northeast(newStart);
		southwest(newStart);
		southeast(newStart);
	}

	@SuppressWarnings("unused")
	private void addPattern(World world, Location location)
	{
		Block start = world.getBlockAt(location);
		north(start);
		south(start);
		east(start);
		west(start);
		Location nw = northwest(start);
		pillar(world, nw);
		Location ne = northeast(start);
		pillar(world, ne);
		Location sw = southwest(start);
		pillar(world, sw);
		Location se = southeast(start);
		pillar(world, se);
		Block newStart = world.getBlockAt(new Location(world, location
				.getBlockX(), location.getBlockY() + 3, location.getBlockZ()));
		north(newStart);
		south(newStart);
		east(newStart);
		west(newStart);
		northwest(newStart);
		northeast(newStart);
		southwest(newStart);
		southeast(newStart);
	}

	private void pillar(World world, Location location)
	{
		Block start = world.getBlockAt(location);
		Block startU = start.getRelative(BlockFace.UP);
		startU.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startUU = startU.getRelative(BlockFace.UP);
		startUU.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startUUU = startUU.getRelative(BlockFace.UP);
		startUUU.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
	}

	private Location southeast(Block start)
	{
		Block startSE = start.getRelative(BlockFace.SOUTH_EAST);
		startSE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSESE = startSE.getRelative(BlockFace.SOUTH_EAST);
		startSESE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSESESE = startSESE.getRelative(BlockFace.SOUTH_EAST);
		startSESESE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		return startSESESE.getLocation();
	}

	private Location southwest(Block start)
	{
		Block startSW = start.getRelative(BlockFace.SOUTH_WEST);
		startSW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSWSW = startSW.getRelative(BlockFace.SOUTH_WEST);
		startSWSW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSWSWSW = startSWSW.getRelative(BlockFace.SOUTH_WEST);
		startSWSWSW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		return startSWSWSW.getLocation();
	}

	private Location northeast(Block start)
	{
		Block startNE = start.getRelative(BlockFace.NORTH_EAST);
		startNE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNENE = startNE.getRelative(BlockFace.NORTH_EAST);
		startNENE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNENENE = startNENE.getRelative(BlockFace.NORTH_EAST);
		startNENENE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		return startNENENE.getLocation();
	}

	private Location northwest(Block start)
	{
		Block startNW = start.getRelative(BlockFace.NORTH_WEST);
		startNW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNWNW = startNW.getRelative(BlockFace.NORTH_WEST);
		startNWNW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNWNWNW = startNWNW.getRelative(BlockFace.NORTH_WEST);
		startNWNWNW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		return startNWNWNW.getLocation();
	}

	private void west(Block start)
	{
		Block startW = start.getRelative(BlockFace.WEST);
		startW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startWW = startW.getRelative(BlockFace.WEST);
		startWW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startWWW = startWW.getRelative(BlockFace.WEST);
		startWWW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
	}

	private void east(Block start)
	{
		Block startE = start.getRelative(BlockFace.EAST);
		startE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startEE = startE.getRelative(BlockFace.EAST);
		startEE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startEEE = startEE.getRelative(BlockFace.EAST);
		startEEE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
	}

	private void south(Block start)
	{
		Block startS = start.getRelative(BlockFace.SOUTH);
		startS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSS = startS.getRelative(BlockFace.SOUTH);
		startSS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startSSS = startSS.getRelative(BlockFace.SOUTH);
		startSSS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
	}

	private void north(Block start)
	{
		Block startN = start.getRelative(BlockFace.NORTH);
		startN.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNN = startN.getRelative(BlockFace.NORTH);
		startNN.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
		Block startNNN = startNN.getRelative(BlockFace.NORTH);
		startNNN.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
	}
}
