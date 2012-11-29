package com.modcrafting.diablodrops.log;

/*
 * Copyright (C) 2012 p000ison
 * 
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of
 * this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send
 * a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco,
 * California, 94105, USA.
 */

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bukkit.plugin.Plugin;

/**
 * This should be get initialised when the plugin loads (It contains the Logging
 * system)
 * 
 * @author Max
 */
public class Logging
{

    private static Logger log;
    private static final Logger debugLogger = Logger.getLogger("Logging");
    private FileHandler debugFileHandler;

    /**
     * Gets the logger for your plugin
     * 
     * @param plugin
     *            The plugin to apply
     */
    public Logging(Plugin plugin)
    {

        log = plugin.getLogger();

        try
        {

            debugFileHandler = new FileHandler(plugin.getDataFolder()
                    .getAbsolutePath()
                    + File.pathSeparator
                    + plugin.getName()
                    + ".log", true);
            SimpleFormatter formatter = new SimpleFormatter();

            debugLogger.addHandler(debugFileHandler);
            debugFileHandler.setFormatter(formatter);

        }
        catch (IOException ex)
        {
            debug(null, ex, false);
        }
        catch (SecurityException ex)
        {
            debug(null, ex, false);
        }
    }

    /**
     * Logs a message
     * 
     * @param level
     *            The level to log
     * @param msg
     *            The message to log
     * @param toFile
     *            log to own log?
     */
    public static void debug(Level level, String msg, boolean toFile)
    {
        if (toFile)
        {
            if (debugLogger != null)
            {
                debugLogger.log(level, msg);
            }
        }
        log.log(level, msg);
    }

    /**
     * Logs a Exception
     * 
     * @param msg
     *            The message to log
     * @param exception
     *            the exception
     * @param toFile
     *            log to own log?
     */
    public static void debug(String msg, Throwable exception, boolean toFile)
    {
        if (toFile)
        {
            if (debugLogger != null)
            {
                debugLogger.log(Level.SEVERE, msg, exception);
            }
        }
        log.log(Level.SEVERE, msg, exception);
    }
}
