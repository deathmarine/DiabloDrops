package com.modcrafting.diablodrops.color;

import org.bukkit.Color;

public enum ArmorColors
{

    AQUA(Color.AQUA), BLACK(Color.BLACK), BLUE(Color.BLUE), FUCHSIA(
            Color.FUCHSIA), GRAY(Color.GRAY), GREEN(Color.GREEN), LIME(
            Color.LIME), MAROON(Color.MAROON), NAVY(Color.NAVY), OLIVE(
            Color.OLIVE), ORANGE(Color.ORANGE), PURPLE(Color.PURPLE), RED(
            Color.RED), SILVER(Color.SILVER), TEAL(Color.TEAL), WHITE(
            Color.WHITE), YELLOW(Color.YELLOW);

    private final Color _color;

    private ArmorColors(final Color path)
    {
        _color = path;
    }

    public Color getColor()
    {
        return _color;
    }

}
