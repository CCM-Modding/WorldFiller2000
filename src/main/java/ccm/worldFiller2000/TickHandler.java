package ccm.worldFiller2000;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.EnumSet;
import java.util.HashMap;

public class TickHandler implements ITickHandler
{
    public static final TickHandler INSTANCE = new TickHandler();

    private TickHandler() {}

    public HashMap<Integer, Filler> map = new HashMap<Integer, Filler>();

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (tickData[0] instanceof WorldServer)
        {
            WorldServer world = (WorldServer) tickData[0];
            int dim = world.provider.dimensionId;

            if (map.containsKey(dim))
            {
                map.get(dim).tick(world);
            }
        }
        else
        {
            System.out.println("??? " + tickData[0]);
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel()
    {
        return "WorldFiller2000_TickHandler";
    }
}
