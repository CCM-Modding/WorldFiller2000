package ccm.worldFiller2000;

import ccm.worldFiller2000.cmd.CommandWorldFiller;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import static ccm.worldFiller2000.util.Constants.MODID;

@Mod(modid = MODID)
public class WorldFiller2000
{
    @Mod.EventHandler()
    public void event(FMLInitializationEvent event)
    {
        TickRegistry.registerTickHandler(TickHandler.INSTANCE, Side.SERVER);
    }

    @Mod.EventHandler()
    public void event(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandWorldFiller());
    }
}
