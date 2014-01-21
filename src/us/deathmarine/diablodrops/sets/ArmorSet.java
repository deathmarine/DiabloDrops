package us.deathmarine.diablodrops.sets;

import java.util.List;

public class ArmorSet
{

    private final List<String> bonuses;
    private final String name;

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
