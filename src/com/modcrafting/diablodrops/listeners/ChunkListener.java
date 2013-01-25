package com.modcrafting.diablodrops.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.modcrafting.diablodrops.DiabloDrops;
import com.modcrafting.diablodrops.events.RuinGenerateEvent;

public class ChunkListener implements Listener
{

    private int blockType;

    private final DiabloDrops plugin;

    public ChunkListener(final DiabloDrops plugin)
    {
        this.plugin = plugin;
    }

    private void addRuin1Pattern(final Block block, final int size)
    {
        World world = block.getWorld();
        Location location = block.getRelative(BlockFace.DOWN).getLocation();
        Block start = world.getBlockAt(location);
        pillarRuin1(world, northRuin1(start, size), size);
        pillarRuin1(world, southRuin1(start, size), size);
        pillarRuin1(world, eastRuin1(start, size), size);
        pillarRuin1(world, westRuin1(start, size), size);
        pillarRuin1(world, northwestRuin1(start, size), size);
        pillarRuin1(world, northeastRuin1(start, size), size);
        pillarRuin1(world, southwestRuin1(start, size), size);
        pillarRuin1(world, southeastRuin1(start, size), size);
        Block newStart = world
                .getBlockAt(new Location(world, location.getBlockX(), location
                        .getBlockY() + size, location.getBlockZ()));
        northRuin1(newStart, size);
        southRuin1(newStart, size);
        eastRuin1(newStart, size);
        westRuin1(newStart, size);
        northwestRuin1(newStart, size);
        northeastRuin1(newStart, size);
        southwestRuin1(newStart, size);
        southeastRuin1(newStart, size);
    }

    /**
     * Builds a nether temple.
     * 
     * @param loc
     * @return
     */
    private boolean buildNetherTemple(final Location loc)
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
                if ((world.getBlockTypeIdAt((int) minX + i, (int) loc.getY(),
                        (int) minZ + ii) == Material.AIR.getId())
                        || (world.getBlockTypeIdAt((int) minX + i,
                                (int) loc.getY(), (int) minZ + ii) == Material.STATIONARY_WATER
                                .getId()))
                    return false;
            }
        }
        for (double i = 0; i <= Math.abs(maxX - minX); i++)
        {
            for (double ii = 0; ii <= Math.abs(maxZ - minZ); ii++)
            {
                if ((i == 0) || (ii == 0) || (i == Math.abs(maxX - minX))
                        || (ii == Math.abs(maxZ - minZ)))
                {
                    for (int iii = 0; iii < 5; iii++)
                    {
                        if (((i == 0) && (ii == 0))
                                || ((i == Math.abs(maxX - minX)) && (ii == Math
                                        .abs(maxZ - minZ)))
                                || ((i == 0) && (ii == Math.abs(maxZ - minZ)))
                                || ((ii == 0) && (i == Math.abs(maxX - minX))))
                        {
                            continue;
                        }
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
        switch (plugin.getSingleRandom().nextInt(4))
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
        return true;

    }

    /**
     * Builds death's ruins.
     * 
     * @param loc
     */
    private boolean deathRuin(final Location loc)
    {
        int square = plugin.getSingleRandom().nextInt(6);
        if (square < 1)
            return false;
        List<Material> mats = getBiomeMaterials(loc.getBlock().getBiome());
        if (mats.size() < 1)
            return false;
        int iiii = 0;
        double maxX = Math.max(loc.getX() - square, loc.getX() + square);
        double maxZ = Math.max(loc.getZ() - square, loc.getZ() + square);
        double minX = Math.min(loc.getX() - square, loc.getX() + square);
        double minZ = Math.min(loc.getZ() - square, loc.getZ() + square);
        for (double i = 0; i <= Math.abs(maxX - minX); i++)
        {
            for (double ii = 0; ii <= Math.abs(maxZ - minZ); ii++)
            {
                iiii++;
                Location nt = new Location(loc.getWorld(), minX + i,
                        loc.getY(), minZ + ii);
                Block n = nt.getBlock();
                if (!n.getType().equals(Material.STATIONARY_WATER)
                        && !n.getType().equals(Material.AIR))
                {
                    n.setTypeId(mats.get(
                            plugin.getSingleRandom().nextInt(mats.size()))
                            .getId());

                    if ((i == 0) || (ii == 0) || (i == Math.abs(maxX - minX))
                            || (ii == Math.abs(maxZ - minZ)))
                    {
                        for (int iii = plugin.getSingleRandom().nextInt(6); iii > 0; iii--)
                        {
                            Location t = new Location(loc.getWorld(), minX + i,
                                    loc.getY() + iii, minZ + ii);
                            t.getBlock().setTypeId(
                                    mats.get(
                                            plugin.getSingleRandom().nextInt(
                                                    mats.size())).getId());

                        }
                    }
                }
            }
        }
        if (iiii < 1)
            return false;
        return true;
    }

    private Location eastRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.EAST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private void generateRuin1(final Block block, final int size)
    {
        Biome b = block.getBiome();
        List<Material> allowedTypes = new ArrayList<Material>();
        if ((b == Biome.DESERT) || (b == Biome.DESERT_HILLS)
                || (b == Biome.BEACH))
        {
            blockType = 24;
            allowedTypes.add(Material.SAND);
            allowedTypes.add(Material.SANDSTONE);
        }
        else if ((b == Biome.FOREST) || (b == Biome.FOREST_HILLS)
                || (b == Biome.JUNGLE) || (b == Biome.JUNGLE_HILLS))
        {
            blockType = 17;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        else if ((b == Biome.TAIGA) || (b == Biome.TAIGA_HILLS)
                || (b == Biome.FROZEN_OCEAN) || (b == Biome.FROZEN_RIVER)
                || (b == Biome.ICE_MOUNTAINS) || (b == Biome.ICE_PLAINS))
        {
            if (plugin.getSingleRandom().nextBoolean())
            {
                blockType = 79;
            }
            else
            {
                blockType = 80;
            }
            allowedTypes.add(Material.SNOW);
            allowedTypes.add(Material.ICE);
            allowedTypes.add(Material.GRASS);
            allowedTypes.add(Material.DIRT);
        }
        else if ((b == Biome.PLAINS) || (b == Biome.SWAMPLAND)
                || (b == Biome.SMALL_MOUNTAINS))
        {
            blockType = 98;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        else if (b == Biome.EXTREME_HILLS)
        {
            int chance = plugin.getSingleRandom().nextInt(100);
            if (chance <= 75)
            {
                blockType = 98;
            }
            else if ((chance > 75) && (chance <= 85))
            {
                blockType = 73;
            }
            else if ((chance > 85) && (chance <= 90))
            {
                blockType = 15;
            }
            else if ((chance > 90) && (chance <= 95))
            {
                blockType = 56;
            }
            else
            {
                blockType = 129;
            }
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
            allowedTypes.add(Material.STONE);
        }
        Block blockUnder = block.getRelative(BlockFace.DOWN);
        if (!allowedTypes.contains(blockUnder.getType()))
            return;
        addRuin1Pattern(block, size);
    }

    private void generateRuin2(final Block block)
    {
        Biome b = block.getBiome();
        List<Material> allowedTypes = new ArrayList<Material>();
        if ((b == Biome.DESERT) || (b == Biome.DESERT_HILLS)
                || (b == Biome.BEACH))
        {
            blockType = 24;
            allowedTypes.add(Material.SAND);
            allowedTypes.add(Material.SANDSTONE);
        }
        else if ((b == Biome.FOREST) || (b == Biome.FOREST_HILLS)
                || (b == Biome.JUNGLE) || (b == Biome.JUNGLE_HILLS))
        {
            blockType = 17;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        else if ((b == Biome.TAIGA) || (b == Biome.TAIGA_HILLS)
                || (b == Biome.FROZEN_OCEAN) || (b == Biome.FROZEN_RIVER)
                || (b == Biome.ICE_MOUNTAINS) || (b == Biome.ICE_PLAINS))
        {
            if (plugin.getSingleRandom().nextBoolean())
            {
                blockType = 79;
            }
            else
            {
                blockType = 80;
            }
            allowedTypes.add(Material.SNOW);
            allowedTypes.add(Material.ICE);
            allowedTypes.add(Material.GRASS);
            allowedTypes.add(Material.DIRT);
        }
        else if ((b == Biome.PLAINS) || (b == Biome.EXTREME_HILLS)
                || (b == Biome.SWAMPLAND) || (b == Biome.SMALL_MOUNTAINS))
        {
            blockType = 98;
            allowedTypes.add(Material.DIRT);
            allowedTypes.add(Material.GRASS);
        }
        Block blockUnder = block.getRelative(BlockFace.DOWN);
        if (!allowedTypes.contains(blockUnder.getType()))
            return;
        Block[] blockSurround = new Block[] { block,
                block.getRelative(BlockFace.NORTH),
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
        Block[] ruinBase = new Block[] {
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
            pillarRuin2(b1, plugin.getSingleRandom().nextInt(4) + 3, blockType);
        }
        for (Block b2 : blockSurround)
        {
            b2.getRelative(0, -1, 0).setType(Material.LAVA);
            for (int i = 2; i < (plugin.getSingleRandom().nextInt(5) + 2); i++)
            {
                b2.getRelative(0, -i, 0).setTypeId(blockType);
            }
            for (int i = 1; i < 5; i++)
            {
                b2.getRelative(0, i, 0).setTypeId(0);
            }
            b2.setType(Material.GLASS);
            switch (plugin.getSingleRandom().nextInt(7))
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

    private List<Material> getBiomeMaterials(final Biome b)
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

    public Block getBlockAt(final World world, final int x, final int y,
            final int z)
    {
        return world.getBlockAt(x, y, z);
    }

    private Location northeastRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.NORTH_EAST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private Location northRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.NORTH);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private Location northwestRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.NORTH_WEST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    @EventHandler
    public void onChunkPopulate(final ChunkPopulateEvent event)
    {
        if (!plugin.getConfig().getBoolean("Ruins.Enabled", true))
            return;
        if (!plugin.worlds.contains(event.getWorld().getName())
                && plugin.getConfig().getBoolean("Worlds.Enabled", false))
            return;
        Chunk chunk = event.getChunk();
        int genInt = plugin.getSingleRandom().nextInt(100) + 1;
        int confChance = plugin.getConfig().getInt("Ruins.Chance", 1);
        if (genInt > confChance)
            return;
        int realX = (chunk.getX() * 16) + plugin.getSingleRandom().nextInt(15);
        int realZ = (chunk.getZ() * 16) + plugin.getSingleRandom().nextInt(15);

        if (chunk.getWorld().getEnvironment() != Environment.NETHER)
        {
            Block block = chunk.getWorld().getHighestBlockAt(realX, realZ);
            switch (plugin.getSingleRandom().nextInt(4))
            {
                case 0:
                {
                    int size = plugin.getSingleRandom().nextInt(4) + 3;
                    generateRuin1(block, size);
                    RuinGenerateEvent rge = new RuinGenerateEvent(chunk, block);
                    plugin.getServer().getPluginManager().callEvent(rge);
                    if (rge.isCancelled())
                        return;
                    block = rge.getChest();
                    block.setType(Material.CHEST);
                    plugin.getDropAPI().fillChest(block, size);
                    break;
                }
                case 1:
                {
                    generateRuin2(block);
                    break;
                }
                case 2:
                {
                    Block under = block.getRelative(BlockFace.DOWN);
                    Location loc = under.getLocation();
                    if (buildNetherTemple(loc))
                    {
                        RuinGenerateEvent rge = new RuinGenerateEvent(chunk,
                                block);
                        plugin.getServer().getPluginManager().callEvent(rge);
                        if (rge.isCancelled())
                            return;
                        block = rge.getChest();
                        block.setType(Material.CHEST);
                        plugin.getDropAPI().fillChest(block);
                    }
                    break;
                }
                case 3:
                {
                    Block under = block.getRelative(BlockFace.DOWN);
                    Location loc = under.getLocation();
                    if (deathRuin(loc))
                    {
                        RuinGenerateEvent rge = new RuinGenerateEvent(chunk,
                                block);
                        plugin.getServer().getPluginManager().callEvent(rge);
                        if (rge.isCancelled())
                            return;
                        block = rge.getChest();
                        block.setType(Material.CHEST);
                        plugin.getDropAPI().fillChest(block);
                    }
                    break;
                }
            }
        }
    }

    private void pillarRuin1(final World world, final Location location,
            final int size)
    {
        Block start = world.getBlockAt(location);
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.UP);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
    }

    private void pillarRuin2(final Block block, final int size, final int type)
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
            b.setTypeIdAndData(type,
                    (byte) plugin.getSingleRandom().nextInt(4), false);
        }
    }

    public void setBlockAt(final World world, final int x, final int y,
            final int z, final int id)
    {
        getBlockAt(world, x, y, z).setTypeId(id);
    }

    public void setBlockAt(final World world, final int x, final int y,
            final int z, final int id, final int data)
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

    private Location southeastRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.SOUTH_EAST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private Location southRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.SOUTH);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private Location southwestRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.SOUTH_WEST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }

    private Location westRuin1(final Block start, final int size)
    {
        Block block = start;
        for (int i = 0; i < size; i++)
        {
            Block next = block.getRelative(BlockFace.WEST);
            next.setTypeIdAndData(blockType, (byte) plugin.getSingleRandom()
                    .nextInt(4), false);
            block = next;
        }
        return block.getLocation();
    }
}
