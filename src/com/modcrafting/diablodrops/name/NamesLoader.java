package com.modcrafting.diablodrops.name;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import com.modcrafting.diablodrops.DiabloDrops;

public class NamesLoader
{
    File dataFolder;
    DiabloDrops plugin;

    public NamesLoader(final DiabloDrops instance)
    {
        plugin = instance;
        dataFolder = instance.getDataFolder();
    }

    public void loadFile(final HashMap<Material, List<String>> hm, final File f)
    {
        Material m = Material.getMaterial(f.getName().replace(".txt", "")
                .toUpperCase());
        List<String> l = new ArrayList<String>();
        try
        {
            BufferedReader list = new BufferedReader(new FileReader(f));
            String p;
            while ((p = list.readLine()) != null)
            {
                if (!p.contains("#") && (p.length() > 0))
                {
                    l.add(p);
                }
            }

            if (m != null)
            {
                hm.put(m, l);
            }
            else
            {
                hm.put(Material.AIR, l);
            }

            list.close();
        }
        catch (Exception e)
        {
            if (plugin.debug)
            {
                plugin.log.warning(e.getMessage());
            }
        }
    }

    /**
     * Takes values from a file and adds them to list
     * 
     * @param l
     *            List of strings to add values
     * @param name
     *            Name of the file to take values from
     */
    public void loadFile(final List<String> l, final String name)
    {
        try
        {
            BufferedReader list = new BufferedReader(new FileReader(new File(
                    dataFolder, name)));
            String p;
            while ((p = list.readLine()) != null)
            {
                if (!p.contains("#") && (p.length() > 0))
                {
                    l.add(p);
                }
            }
            list.close();
        }
        catch (Exception e)
        {
            if (plugin.debug)
            {
                plugin.log.warning(e.getMessage());
            }
        }
    }

    /**
     * Creates a file with given name
     * 
     * @param name
     *            Name of the file to write
     */
    public void writeDefault(final String name,boolean overwrite)
    {
        File actual = new File(dataFolder, name);
        if (name.contains(".jar"))
        {
            actual = new File(dataFolder, "/lib/" + name);
        }
        if (!actual.exists()||overwrite)
        {
            try
            {
                InputStream input = this.getClass().getResourceAsStream(
                        "/" + name);
                FileOutputStream output = new FileOutputStream(actual,false);
                byte[] buf = new byte[8192];
                int length = 0;
                while ((length = input.read(buf)) > 0)
                {
                    output.write(buf, 0, length);
                }
                output.flush();
                output.close();
                input.close();
            }
            catch (Exception e)
            {
                if (plugin.debug)
                {
                    plugin.log.warning(e.getMessage());
                }
            }
        }
    }
}
