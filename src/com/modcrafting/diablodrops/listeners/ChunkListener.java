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

    private void addRuin1Pattern(Block block)
    {
        World world = block.getWorld();
        Location location = block.getRelative(BlockFace.DOWN).getLocation();
        Block start = world.getBlockAt(location);
        northRuin1(start);
        southRuin1(start);
        eastRuin1(start);
        westRuin1(start);
        Location nw = northwestRuin1(start);
        pillarRuin1(world, nw);
        Location ne = northeastRuin1(start);
        pillarRuin1(world, ne);
        Location sw = southwestRuin1(start);
        pillarRuin1(world, sw);
        Location se = southeastRuin1(start);
        pillarRuin1(world, se);
        Block newStart = world.getBlockAt(new Location(world, location
                .getBlockX(), location.getBlockY() + 3, location.getBlockZ()));
        northRuin1(newStart);
        southRuin1(newStart);
        eastRuin1(newStart);
        westRuin1(newStart);
        northwestRuin1(newStart);
        northeastRuin1(newStart);
        southwestRuin1(newStart);
        southeastRuin1(newStart);
    }

    /**
     * Builds a nether temple.
     * 
     * @param loc
     */
    private void buildNetherTemple(Location loc)
    {
        int square = 5;
        World world = loc.getWorld();
        double maxX = Math.max(loc.getX() - square, loc.getX() + square);
        double maxZ = Math.max(loc.getZ() - square, loc.getZ() + square);
        double minX = Math.min(loc.getX() - square, loc.getX() + square);
        double minZ = Math.min(loc.getZ() - square, loc.getZ() + square);
        for (int i = 0; i <= Math.abs(maxX - minX); i++)
        {
            for (int ii = 0; ii <= Math.abs(maxZ - minZ); ii++)
            {
                ;
                if (world.getBlockTypeIdAt((int) minX + i, (int) loc.getY(),
                        (int) minZ + ii) == Material.AIR.getId()
                        || world.getBlockTypeIdAt((int) minX + i,
                                (int) loc.getY(), (int) minZ + ii) == Material.STATIONARY_WATER
                                .getId())
                {
                    return;
                }
            }
        }
        for (double i = 0; i <= Math.abs(maxX - minX); i++)
        {
            for (double ii = 0; ii <= Math.abs(maxZ - minZ); ii++)
            {
                if (i == 0 || ii == 0 || i == Math.abs(maxX - minX)
                        || ii == Math.abs(maxZ - minZ))
                {
                    for (int iii = 0; iii < 5; iii++)
                    {
                        if (i == 0 && ii == 0 || i == Math.abs(maxX - minX)
                                && ii == Math.abs(maxZ - minZ) || i == 0
                                && ii == Math.abs(maxZ - minZ) || ii == 0
                                && i == Math.abs(maxX - minX))
                            continue;
                        Location t = new Location(world, minX + i, loc.getY()
                                + iii, minZ + ii);
                        t.getBlock().setType(Material.NETHER_BRICK);
                    }
                }
                Location t = new Location(world, minX + i, loc.getY(), minZ
                        + ii);
                t.getBlock().setType(Material.NETHER_BRICK);
                Location r = new Location(world, minX + i,
                        (loc.getY() + (square * 2)) - (t.distance(loc)), minZ
                                + ii);
                r.getBlock().setType(Material.NETHER_BRICK);

            }
        }
        int faceX = 0;
        int faceZ = 0;
        switch (plugin.gen.nextInt(4))
        {
            case 0:
            {
                faceX = square;
                break;
            }
            case 1:
            {
                faceX = -square;
                break;
            }
            case 2:
            {
                faceZ = square;
                break;
            }
            case 3:
            {
                faceZ = -square;
                break;
            }
        }
        for (int i = 1; i < 3; i++)
        {
            world.getBlockAt((int) loc.getX() + faceX, (int) loc.getY() + i,
                    (int) loc.getZ() + faceZ).setTypeId(0);
            world.getBlockAt((int) loc.getX() - 2, (int) loc.getY() + i,
                    (int) loc.getZ()).setType(Material.NETHER_BRICK);
            world.getBlockAt((int) loc.getX(), (int) loc.getY() + i,
                    (int) loc.getZ() - 2).setType(Material.NETHER_BRICK);
            world.getBlockAt((int) loc.getX() + 2, (int) loc.getY() + i,
                    (int) loc.getZ()).setType(Material.NETHER_BRICK);
            world.getBlockAt((int) loc.getX(), (int) loc.getY() + i,
                    (int) loc.getZ() + 2).setType(Material.NETHER_BRICK);
        }
        world.getBlockAt((int) loc.getX() - 2, (int) loc.getY() + 3,
                (int) loc.getZ()).setType(Material.TORCH);
        world.getBlockAt((int) loc.getX(), (int) loc.getY() + 3,
                (int) loc.getZ() - 2).setType(Material.TORCH);
        world.getBlockAt((int) loc.getX() + 2, (int) loc.getY() + 3,
                (int) loc.getZ()).setType(Material.TORCH);
        world.getBlockAt((int) loc.getX(), (int) loc.getY() + 3,
                (int) loc.getZ() + 2).setType(Material.TORCH);
        Block door = world.getBlockAt((int) loc.getX() + faceX,
                (int) loc.getY() + 1, (int) loc.getZ() + faceZ);
        door.getRelative(BlockFace.NORTH_EAST).setType(Material.NETHER_FENCE);
        door.getRelative(BlockFace.NORTH_WEST).setType(Material.NETHER_FENCE);
        door.getRelative(BlockFace.SOUTH_EAST).setType(Material.NETHER_FENCE);
        door.getRelative(BlockFace.SOUTH_WEST).setType(Material.NETHER_FENCE);

    }

    /**
     * Builds death's ruins.
     * 
     * @param loc
     */
    private void deathRuin(Location loc)
    {
        // These may need to be a seperate plugin.
        int square = plugin.gen.nextInt(6);
        if (square < 1)
            return;
        List<Material> mats = getBiomeMaterials(loc.getBlock().getBiome());
        if (mats.size() < 1)
            return;
        double maxX = Math.max(loc.getX() - square, loc.getX() + square);
        double maxZ = Math.max(loc.getZ() - square, loc.getZ() + square);
        double minX = Math.min(loc.getX() - square, loc.getX() + square);
        double minZ = Math.min(loc.getZ() - square, loc.getZ() + square);
        for (double i = 0; i <= Math.abs(maxX - minX); i++)
        {
            for (double ii = 0; ii <= Math.abs(maxZ - minZ); ii++)
            {
                Location nt = new Location(loc.getWorld(), minX + i,
                        loc.getY(), minZ + ii);
                Block n = nt.getBlock();
                if (!n.getType().equals(Material.STATIONARY_WATER)
                        && !n.getType().equals(Material.AIR))
                {
                    n.setTypeId(mats.get(plugin.gen.nextInt(mats.size()))
                            .getId());

                    if (i == 0 || ii == 0 || i == Math.abs(maxX - minX)
                            || ii == Math.abs(maxZ - minZ))
                    {
                        for (int iii = plugin.gen.nextInt(6); iii > 0; iii--)
                        {
                            Location t = new Location(loc.getWorld(), minX + i,
                                    loc.getY() + iii, minZ + ii);
                            t.getBlock().setTypeId(
                                    mats.get(plugin.gen.nextInt(mats.size()))
                                            .getId());

                        }
                    }
                }
            }
        }
    }

    private void eastRuin1(Block start)
    {
        Block startE = start.getRelative(BlockFace.EAST);
        startE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startEE = startE.getRelative(BlockFace.EAST);
        startEE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startEEE = startEE.getRelative(BlockFace.EAST);
        startEEE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    private void generateRuin1(Chunk chunk, int realX, int realZ)
    {
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
        if (plugin.gen.nextBoolean())
        {
            addRuin1Pattern(block);
        }
        else
        {
            Block under = block.getRelative(BlockFace.DOWN);
            Location loc = under.getLocation();

            if (plugin.gen.nextBoolean())
            {
                buildNetherTemple(loc);
            }
            else
            {
                deathRuin(loc);
            }
        }
        block.setType(Material.CHEST);
        try
        {
            if (!(block.getState() instanceof Chest))
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
        catch (Exception e)
        {
        }
    }

    private void generateRuin2(Chunk chunk, int realX, int realZ)
    {
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
        Block[] blockSurround = new Block[]
        { block, block.getRelative(BlockFace.NORTH),
                block.getRelative(BlockFace.NORTH_EAST),
                block.getRelative(BlockFace.EAST),
                block.getRelative(BlockFace.SOUTH_EAST),
                block.getRelative(BlockFace.SOUTH),
                block.getRelative(BlockFace.SOUTH_WEST),
                block.getRelative(BlockFace.WEST),
                block.getRelative(BlockFace.NORTH_WEST) };
        Block northern = block.getRelative(BlockFace.NORTH).getRelative(
                BlockFace.NORTH);
        Block eastern = block.getRelative(BlockFace.EAST).getRelative(
                BlockFace.EAST);
        Block southern = block.getRelative(BlockFace.SOUTH).getRelative(
                BlockFace.SOUTH);
        Block western = block.getRelative(BlockFace.WEST).getRelative(
                BlockFace.WEST);
        Block[] ruinBase = new Block[]
        {
                northern,
                northern.getRelative(BlockFace.EAST),
                northern.getRelative(BlockFace.EAST)
                        .getRelative(BlockFace.EAST),
                northern.getRelative(BlockFace.EAST)
                        .getRelative(BlockFace.EAST)
                        .getRelative(BlockFace.SOUTH),
                eastern,
                eastern.getRelative(BlockFace.SOUTH),
                eastern.getRelative(BlockFace.SOUTH).getRelative(
                        BlockFace.SOUTH),
                eastern.getRelative(BlockFace.SOUTH)
                        .getRelative(BlockFace.SOUTH)
                        .getRelative(BlockFace.WEST),
                southern,
                southern.getRelative(BlockFace.WEST),
                southern.getRelative(BlockFace.WEST)
                        .getRelative(BlockFace.WEST),
                southern.getRelative(BlockFace.WEST)
                        .getRelative(BlockFace.WEST)
                        .getRelative(BlockFace.NORTH),
                western,
                western.getRelative(BlockFace.NORTH),
                western.getRelative(BlockFace.NORTH).getRelative(
                        BlockFace.NORTH),
                western.getRelative(BlockFace.NORTH)
                        .getRelative(BlockFace.NORTH)
                        .getRelative(BlockFace.EAST) };
        for (Block b1 : ruinBase)
        {
            pillarRuin2(b1, plugin.gen.nextInt(4) + 3, blockType);
        }
        for (Block b2 : blockSurround)
        {
            b2.getRelative(0, -1, 0).setType(Material.LAVA);
            for (int i = 2; i < plugin.gen.nextInt(5) + 2; i++)
            {
                b2.getRelative(0, -i, 0).setTypeId(blockType);
            }
            b2.setType(Material.GLASS);
            switch (plugin.gen.nextInt(7))
            {
                case 1:
                    b2.getRelative(0, 1, 0).setType(Material.IRON_BLOCK);
                    break;
                case 2:
                    b2.getRelative(0, 1, 0).setType(Material.GOLD_BLOCK);
                    break;
                case 3:
                    b2.getRelative(0, 1, 0).setType(Material.LAPIS_BLOCK);
                    break;
                default:
                    break;
            }
        }
    }

    private List<Material> getBiomeMaterials(Biome b)
    {
        List<Material> mats = new ArrayList<Material>();
        switch (b)
        {
            case BEACH:
            {
                mats.add(Material.SANDSTONE);
                break;
            }
            case DESERT:
            {
                mats.add(Material.SANDSTONE);
                break;
            }
            case DESERT_HILLS:
            {
                mats.add(Material.SANDSTONE);
                break;
            }
            case EXTREME_HILLS:
            {
                mats.add(Material.COBBLESTONE);
                mats.add(Material.COBBLESTONE);
                break;
            }
            case FOREST:
            {
                mats.add(Material.SMOOTH_BRICK);
                mats.add(Material.STONE);
                break;
            }
            case FOREST_HILLS:
            {
                mats.add(Material.SMOOTH_BRICK);
                mats.add(Material.STONE);
                break;
            }
            case FROZEN_OCEAN:
                break;
            case FROZEN_RIVER:
                break;
            case HELL:
            {
                mats.add(Material.NETHER_BRICK);
                break;
            }
            case ICE_MOUNTAINS:
            {
                mats.add(Material.ICE);
                mats.add(Material.SNOW_BLOCK);
                break;
            }
            case ICE_PLAINS:
            {
                mats.add(Material.ICE);
                mats.add(Material.SNOW_BLOCK);
                break;
            }
            case JUNGLE:
            {
                mats.add(Material.LOG);
                mats.add(Material.WOOD);
                break;
            }
            case JUNGLE_HILLS:
            {
                mats.add(Material.LOG);
                mats.add(Material.WOOD);
                break;
            }
            case MUSHROOM_ISLAND:
                break;
            case MUSHROOM_SHORE:
                break;
            case OCEAN:
                break;
            case PLAINS:
            {
                mats.add(Material.DOUBLE_STEP);
                break;
            }
            case RIVER:
                break;
            case SKY:
                break;
            case SMALL_MOUNTAINS:
                break;
            case SWAMPLAND:
            {
                mats.add(Material.DOUBLE_STEP);
                break;
            }
            case TAIGA:
            {
                mats.add(Material.SMOOTH_BRICK);
                mats.add(Material.STONE);
                break;
            }
            case TAIGA_HILLS:
            {
                mats.add(Material.SMOOTH_BRICK);
                mats.add(Material.STONE);
                break;
            }
            default:
                break;

        }
        return mats;
    }

    public Block getBlockAt(World world, int x, int y, int z)
    {
        return world.getBlockAt(x, y, z);
    }

    private Location northeastRuin1(Block start)
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

    private void northRuin1(Block start)
    {
        Block startN = start.getRelative(BlockFace.NORTH);
        startN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNN = startN.getRelative(BlockFace.NORTH);
        startNN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startNNN = startNN.getRelative(BlockFace.NORTH);
        startNNN.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    private Location northwestRuin1(Block start)
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
        if (plugin.gen.nextInt(100) + 1 > 7)
            generateRuin1(chunk, realX, realZ);
        else
            generateRuin2(chunk, realX, realZ);
    }

    private void pillarRuin1(World world, Location location)
    {
        Block start = world.getBlockAt(location);
        Block startU = start.getRelative(BlockFace.UP);
        startU.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startUU = startU.getRelative(BlockFace.UP);
        startUU.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startUUU = startUU.getRelative(BlockFace.UP);
        startUUU.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    private void pillarRuin2(Block block, int size, int type)
    {
        List<Block> blocks = new ArrayList<Block>();
        blocks.add(block);
        for (int i = 0; i <= Math.abs(size); i++)
        {
            blocks.add(block.getRelative(0, i, 0));
            blocks.add(block.getRelative(0, -i, 0));
        }
        for (Block b : blocks)
        {
            b.setTypeIdAndData(type, (byte) plugin.gen.nextInt(4), false);
        }
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

    private Location southeastRuin1(Block start)
    {
        Block startSE = start.getRelative(BlockFace.SOUTH_EAST);
        startSE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startSESE = startSE.getRelative(BlockFace.SOUTH_EAST);
        startSESE.getRelative(BlockFace.UP).setType(Material.TORCH);
        startSESE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        Block startSESESE = startSESE.getRelative(BlockFace.SOUTH_EAST);
        startSESESE.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        return startSESESE.getLocation();
    }

    private void southRuin1(Block start)
    {
        Block startS = start.getRelative(BlockFace.SOUTH);
        startS.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startSS = startS.getRelative(BlockFace.SOUTH);
        startSS.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startSSS = startSS.getRelative(BlockFace.SOUTH);
        startSSS.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }

    private Location southwestRuin1(Block start)
    {
        Block startSW = start.getRelative(BlockFace.SOUTH_WEST);
        startSW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startSWSW = startSW.getRelative(BlockFace.SOUTH_WEST);
        startSWSW.getRelative(BlockFace.UP).setType(Material.TORCH);
        startSWSW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        Block startSWSWSW = startSWSW.getRelative(BlockFace.SOUTH_WEST);
        startSWSWSW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
        return startSWSWSW.getLocation();
    }

    private void westRuin1(Block start)
    {
        Block startW = start.getRelative(BlockFace.WEST);
        startW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startWW = startW.getRelative(BlockFace.WEST);
        startWW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4), false);
        Block startWWW = startWW.getRelative(BlockFace.WEST);
        startWWW.setTypeIdAndData(blockType, (byte) plugin.gen.nextInt(4),
                false);
    }
}
