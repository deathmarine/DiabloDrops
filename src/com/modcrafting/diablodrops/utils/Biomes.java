package com.modcrafting.diablodrops.utils;

import org.bukkit.block.Biome;

public enum Biomes
{
    BEACH("Beach", Biome.BEACH, 0), DESERT("Desert", Biome.DESERT, 1), DESERT_HILLS(
            "Desert Hills", Biome.DESERT_HILLS, 2), EXTREME_HILLS(
            "Extreme Hills", Biome.EXTREME_HILLS, 3), FOREST("Forest",
            Biome.FOREST, 4), FOREST_HILLS("Forest Hills", Biome.FOREST_HILLS,
            5), FROZEN_OCEAN("Frozen Ocean", Biome.FROZEN_OCEAN, 6), FROZEN_RIVER(
            "Frozen River", Biome.FROZEN_RIVER, 7), HELL("Hell", Biome.HELL, 8), ICE_MOUNTAINS(
            "Ice Mountains", Biome.ICE_MOUNTAINS, 9), ICE_PLAINS("Ice Plains",
            Biome.ICE_PLAINS, 10), JUNGLE("Jungle", Biome.JUNGLE, 11), JUNGLE_HILLS(
            "Jungle Hills", Biome.JUNGLE_HILLS, 12), MUSHROOM_ISLAND(
            "Mushroom Island", Biome.MUSHROOM_ISLAND, 13), MUSHROOM_SHORE(
            "Mushroom Shore", Biome.MUSHROOM_SHORE, 14), OCEAN("Ocean",
            Biome.OCEAN, 15), PLAINS("Plains", Biome.PLAINS, 16), RIVER(
            "River", Biome.RIVER, 17), SKY("Sky", Biome.SKY, 18), SMALL_MOUNTAINS(
            "Small Mountains", Biome.SMALL_MOUNTAINS, 19), SWAMPLAND(
            "Swampland", Biome.SWAMPLAND, 20), TAIGA("Taiga", Biome.TAIGA, 21), TAIGA_HILLS(
            "Taiga Hills", Biome.TAIGA_HILLS, 22);

    public static Biomes fromName(final String name)
    {
        for (Biomes id : Biomes.values())
        {
            if (id.name().equalsIgnoreCase(name))
                return id;
        }
        return null;
    }

    public static Biomes valueOf(final Biome biome)
    {
        for (Biomes id : Biomes.values())
        {
            if (id.getBiome().equals(biome))
                return id;
        }
        return null;
    }

    public static Biomes valueOf(final int bId)
    {
        for (Biomes id : Biomes.values())
        {
            if (id.getId() == bId)
                return id;
        }
        return null;
    }

    private final String _name;
    private final Biome _biome;

    private final int _id;

    private Biomes(final String name, final Biome biome, final int id)
    {
        _name = name;
        _biome = biome;
        _id = id;
    }

    public Biome getBiome()
    {
        return _biome;
    }

    public int getId()
    {
        return _id;
    }

    public String getName()
    {
        return _name;
    }

}
