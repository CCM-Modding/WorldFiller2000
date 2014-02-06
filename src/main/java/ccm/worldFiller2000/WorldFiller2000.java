/*
 * Copyright (c) 2014 Dries K. Aka Dries007 and the CCM modding crew.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ccm.worldFiller2000;

import ccm.worldFiller2000.cmd.CommandWorldFiller;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import org.mcstats.Metrics;

import java.io.IOException;

import static ccm.worldFiller2000.util.Constants.MODID;

@Mod(modid = MODID)
public class WorldFiller2000
{
    @Mod.Instance(MODID)
    public static WorldFiller2000 instance;

    @Mod.Metadata(MODID)
    private ModMetadata metadata;

    @Mod.EventHandler()
    public void event(FMLInitializationEvent event)
    {
        TickRegistry.registerTickHandler(TickHandler.INSTANCE, Side.SERVER);
        try
        {
            Metrics metrics = new Metrics(MODID, getVersion());
            metrics.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler()
    public void event(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandWorldFiller());
    }

    public static String getVersion()
    {
        return instance.metadata.version;
    }
}
