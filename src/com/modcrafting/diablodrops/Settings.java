package com.modcrafting.diablodrops;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings
{
    private final double socket;
    private final double tome;
    private final double standard;
    private final double lore;
    private final double custom;
    private final ChatColor[] colorList;
    private final boolean colorBlindCompat;

    public Settings(FileConfiguration fc)
    {
        socket = fc.getDouble("SocketItem.Chance", 5.0);
        tome = fc.getDouble("IdentifyTome.Chance", 2.0);
        standard = fc.getDouble("Percentages.ChancePerSpawn", 2.0);
        lore = fc.getDouble("Lore.Chance", 2.0);
        custom = fc.getDouble("Custom.Chance", 2.0);
        colorBlindCompat = fc.getBoolean("Display.ColorBlind", false);
        colorList = setupSocketColors(fc);
    }

    public int getCustomChance()
    {
        return (int) (custom * 100);
    }

    public int getLoreChance()
    {
        return (int) (lore * 100);
    }

    public int getSocketChance()
    {
        return (int) (socket * 100);
    }

    public ChatColor[] getSocketColors()
    {
        return colorList;
    }

    public int getStandardChance()
    {
        return (int) (standard * 100);
    }

    public int getTomeChance()
    {
        return (int) (tome * 100);
    }

    public boolean isColorBlindCompat()
    {
        return colorBlindCompat;
    }

    private ChatColor[] setupSocketColors(FileConfiguration fc)
    {
        List<String> colorStringList = fc.getStringList("SocketItem.Colors");
        if (colorStringList == null)
            colorStringList = Arrays.asList(new String[] { "GREEN", "BLUE",
                    "RED" });
        ChatColor[] colorList = new ChatColor[colorStringList.size()];
        for (int i = 0; i < colorStringList.size(); i++)
        {
            String string = colorStringList.get(i);
            ChatColor cc = null;
            try
            {
                cc = ChatColor.valueOf(string.toUpperCase());
            }
            catch (Exception e)
            {
                continue;
            }
            if (cc != null)
                colorList[i] = cc;
        }
        return colorList;
    }

}
