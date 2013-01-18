package com.modcrafting.diablodrops.sets;

import java.util.List;

public class ArmorSet
{

    private final String name;
    private final List<String> bonuses;

    public ArmorSet(String name, List<String> bonuses)
    {
        this.name = name;
        this.bonuses = bonuses;
    }

    /**
    * Gets the bonuses for this ArmorSet
    *
    * @return list of bonuses
    */
    public List<String> getBonuses()
    {
        return bonuses;
    }

    /**
    * Gets the name of this ArmorSet
    *
    * @return ArmorSet name
    */
    public String getName()
    {
        return name;
    }

}
