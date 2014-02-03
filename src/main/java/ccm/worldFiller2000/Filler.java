package ccm.worldFiller2000;

import ccm.worldFiller2000.util.Shape;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;

import java.util.LinkedList;

public class Filler
{
    private final int speed;
    private final int dim;
    private LinkedList<ChunkCoordIntPair> chunkCoordIntPairs = new LinkedList<ChunkCoordIntPair>();

    public Filler(int dim, Shape shape, int centerX, int centerZ, int size, int speed)
    {
        this.dim = dim;
        this.speed = speed;
        shape.generateList(chunkCoordIntPairs, centerX, centerZ, size);
    }

    public int getListSize()
    {
        return chunkCoordIntPairs.size();
    }

    public void tick(World world)
    {
        for (int i = 0; i < speed; i++)
        {
            if (chunkCoordIntPairs.isEmpty())
            {
                TickHandler.INSTANCE.map.remove(dim);
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler done for dim " + dim + "."));
                return;
            }

            if (getListSize() % (20 * 10) == 0)
            {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler for " + dim + " has " + (getListSize() / 20) + " seconds to go."));
            }

            ChunkCoordIntPair chunkCoordIntPair = chunkCoordIntPairs.removeFirst();

            if (world.getChunkProvider().chunkExists(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos))
            {
                //System.out.println("Skipped " + chunkCoordIntPair.chunkXPos + ";" + chunkCoordIntPair.chunkZPos);
                i--;
                continue;
            }

            world.getChunkProvider().loadChunk(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);

            world.setBlock(chunkCoordIntPair.getCenterXPos(), 100, chunkCoordIntPair.getCenterZPosition(), Block.glowStone.blockID);
        }
    }
}
