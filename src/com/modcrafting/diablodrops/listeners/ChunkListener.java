package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.Inventory;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.RuinGenerateEvent;

public class ChunkListener implements Listener
{

    private final DiabloDrops plugin;

    private int blockType;

    public ChunkListener(DiabloDrops plugin)
    {
        this.plugin = plugin;
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

    private void east(Block start)
    {
        Block startE = start.getRelative(BlockFace.EAST);
        startE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startEE = startE.getRelative(BlockFace.EAST);
        startEE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startEEE = startEE.getRelative(BlockFace.EAST);
        startEEE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    public Block getBlockAt(World world, int x, int y, int z)
    {
        return world.getBlockAt(x, y, z);
    }

    private void north(Block start)
    {
        Block startN = start.getRelative(BlockFace.NORTH);
        startN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNN = startN.getRelative(BlockFace.NORTH);
        startNN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNNN = startNN.getRelative(BlockFace.NORTH);
        startNNN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    private Location northeast(Block start)
    {
        Block startNE = start.getRelative(BlockFace.NORTH_EAST);
        startNE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNENE = startNE.getRelative(BlockFace.NORTH_EAST);
        startNENE.getRelative(BlockFace.UP).setType(Material.TORCH);
        startNENE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        Block startNENENE = startNENE.getRelative(BlockFace.NORTH_EAST);
        startNENENE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        return startNENENE.getLocation();
    }

    private Location northwest(Block start)
    {
        Block startNW = start.getRelative(BlockFace.NORTH_WEST);
        startNW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNWNW = startNW.getRelative(BlockFace.NORTH_WEST);
        startNWNW.getRelative(BlockFace.UP).setType(Material.TORCH);
        startNWNW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        Block startNWNWNW = startNWNW.getRelative(BlockFace.NORTH_WEST);
        startNWNWNW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        return startNWNWNW.getLocation();
    }

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event)
    {
        if (!plugin.config.getBoolean("Ruins.Enabled", true))
        {
            return;
        }
        if (!plugin.worlds.contains(event.getWorld().getName())
                && plugin.config.getBoolean("Worlds.Enabled", false))
        {
            return;
        }
        Chunk chunk = event.getChunk();
        int genInt = plugin.gen.nextInt(100) + 1;
        int confChance = plugin.config.getInt("Ruins.Chance", 1);
        if (genInt > confChance)
        {
            return;
        }
        RuinGenerateEvent rge = new RuinGenerateEvent(chunk);
        plugin.getServer().getPluginManager().callEvent(rge);
        if (rge.isCancelled())
            return;
        int realX = chunk.getX() * 16 + plugin.gen.nextInt(15);
        int realZ = chunk.getZ() * 16 + plugin.gen.nextInt(15);
        Block block = chunk.getWorld().getHighestBlockAt(realX, realZ);
        Biome b = block.getBiome();
        List<Material> allowedTypes = new ArrayList<Material>();
        if (b == Biome.DESERT || b == Biome.DESERT_HILLS || b == Biome.BEACH)
        {
            this.blockType = 24;
            allowedTypes.add(Material.SAND);
            allowedTypes.add(Material.SANDSTONE);
        }
        else if (b == Biome.FOREST || b == Biome.FOREST_HILLS
                || b == Biome.JUNGLE || b == Biome.JUNGLE_HILLS)
        {
            this.blockType = 17;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        else if (b == Biome.TAIGA || b == Biome.TAIGA_HILLS
                || b == Biome.FROZEN_OCEAN || b == Biome.FROZEN_RIVER
                || b == Biome.ICE_MOUNTAINS || b == Biome.ICE_PLAINS)
        {
            if (plugin.gen.nextBoolean())
                this.blockType = 79;
            else
                this.blockType = 80;
            allowedTypes.add(Material.SNOW);
            allowedTypes.add(Material.ICE);
            allowedTypes.add(Material.GRASS);
            allowedTypes.add(Material.DIRT);
        }
        else if (b == Biome.PLAINS || b == Biome.EXTREME_HILLS
                || b == Biome.SWAMPLAND || b == Biome.SMALL_MOUNTAINS)
        {
            this.blockType = 98;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        Block blockUnder = block.getRelative(BlockFace.DOWN);
        if (!allowedTypes.contains(blockUnder.getType()))
        {
            return;
        }
        block.setType(Material.CHEST);
        if(plugin.gen.nextBoolean()){
        	addPattern(block);
        }else{
        	deathRuin(block,allowedTypes);
        }
        if ((block.getState() instanceof Chest))
            return;
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

    private void south(Block start)
    {
        Block startS = start.getRelative(BlockFace.SOUTH);
        startS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
        Block startSS = startS.getRelative(BlockFace.SOUTH);
        startSS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
        Block startSSS = startSS.getRelative(BlockFace.SOUTH);
        startSSS.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
    }

    private Location southeast(Block start)
    {
        Block startSE = start.getRelative(BlockFace.SOUTH_EAST);
        startSE.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
        Block startSESE = startSE.getRelative(BlockFace.SOUTH_EAST);
        startSESE.getRelative(BlockFace.UP).setType(Material.TORCH);
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
        startSWSW.getRelative(BlockFace.UP).setType(Material.TORCH);
        startSWSW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
        Block startSWSWSW = startSWSW.getRelative(BlockFace.SOUTH_WEST);
        startSWSWSW.setTypeIdAndData(98, (byte) plugin.gen.nextInt(4), false);
        return startSWSWSW.getLocation();
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
    private void deathRuin(Block block,List<Material> mats){
    	Block under = block.getRelative(BlockFace.DOWN);
    	int square = plugin.gen.nextInt(9);
    	Location loc = under.getLocation();
		double maxX = Math.max(loc.getX()-square, loc.getX()+square);
		double maxZ = Math.max(loc.getZ()-square, loc.getZ()+square);
		double minX = Math.min(loc.getX()-square, loc.getX()+square);
		double minZ = Math.min(loc.getZ()-square, loc.getZ()+square);
		
		for(double i=0;i<=Math.abs(maxX-minX);i++){
			for(double ii=0;ii<=Math.abs(maxZ-minZ);ii++){
					Location nt = new Location(loc.getWorld(), minX+i, loc.getY(), minZ+ii);
					if(i==0||ii==0){
						for(int iii=plugin.gen.nextInt(6);iii>0;iii--){
							Location t = new Location(loc.getWorld(), minX+i, loc.getY()+iii, minZ+ii);
							t.getBlock().setTypeId(mats.get(plugin.gen.nextInt(mats.size())).getId());
						}
					}
					nt.getBlock().setTypeId(mats.get(plugin.gen.nextInt(mats.size())).getId());
				
			}
		}	
    }
}
