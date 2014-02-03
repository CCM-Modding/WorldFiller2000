package ccm.worldFiller2000;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.world.World;

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
        World world = (World) tickData[0];
        int dim = world.provider.dimensionId;

        if (map.containsKey(dim))
        {
            map.get(dim).tick(world);
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
