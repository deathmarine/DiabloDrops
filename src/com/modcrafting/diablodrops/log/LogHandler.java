package com.modcrafting.diablodrops.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogHandler extends Handler
{
    FileOutputStream fos;
    PrintWriter pw;

    public LogHandler(File dataFolder)
    {
        File logFile = new File(dataFolder, "Error.log");
        try
        {
            if (!logFile.exists())
            {
                logFile.createNewFile();
            }
            fos = new FileOutputStream(logFile);
            pw = new PrintWriter(fos);
            setFormatter(new SimpleFormatter());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws SecurityException
    {
        pw.close();
    }

    @Override
    public void flush()
    {
        pw.flush();
    }

    @Override
    public void publish(LogRecord record)
    {
        if (!isLoggable(record))
            return;
        pw.println(getFormatter().format(record));

    }

}
