package ccm.worldFiller2000;

import ccm.worldFiller2000.util.Shape;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.LinkedList;

public class Filler
{
    private int speed = 1;
    private final int dim;
    private LinkedList<ChunkCoordIntPair> chunkCoordIntPairs = new LinkedList<ChunkCoordIntPair>();
    private boolean enabled = false;
    private boolean listGenerated = false;

    public Filler(int dim, final Shape shape, final int centerX, final int centerZ, final int size)
    {
        this.dim = dim;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                shape.generateList(chunkCoordIntPairs, centerX, centerZ, size);
                listGenerated = true;
            }
        }).start();

        TickHandler.INSTANCE.map.put(dim, this);
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("The filler will need " + getListSize()/getSpeed() + " ticks or " + (getListSize() / (20 * getSpeed())) + " seconds at " + speed + " chunks/sec.").setColor(EnumChatFormatting.GREEN));
    }

    public int getListSize()
    {
        return chunkCoordIntPairs.size();
    }

    public void tick(WorldServer world)
    {
        if (!listGenerated || !enabled) return;
        for (int i = 0; i < speed; i++)
        {
            if (chunkCoordIntPairs.isEmpty())
            {
                TickHandler.INSTANCE.map.remove(dim);
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler done for dim " + dim + ". Doing a world save..."));

                boolean flag = world.canNotSave;
                world.canNotSave = false;
                try
                {
                    world.saveAllChunks(true, (IProgressUpdate) null);
                }
                catch (MinecraftException e)
                {
                    System.out.println("Something went wrong saving the world.");
                    e.printStackTrace();
                }
                world.canNotSave = flag;

                return;
            }

            if (getListSize() % (20 * 10 * getSpeed()) == 0)
            {
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler for " + dim + " has " + (getListSize() / 20) + " seconds to go."));
            }

            ChunkCoordIntPair chunkCoordIntPair = chunkCoordIntPairs.removeFirst();

            if (world.getChunkProvider().chunkExists(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos))
            {
                i--;
                continue;
            }

            world.getChunkProvider().loadChunk(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);

            world.setBlock(chunkCoordIntPair.getCenterXPos(), 100, chunkCoordIntPair.getCenterZPosition(), Block.glowStone.blockID);
        }
    }

    public void start()
    {
        enabled = true;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("The filler will need " + getListSize() / getSpeed() + " ticks or " + (getListSize() / (20 * getSpeed())) + " seconds.").setColor(EnumChatFormatting.GREEN));
    }

    public void stop()
    {
        enabled = false;
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromText("Filler stopped.").setColor(EnumChatFormatting.GREEN));
    }

    public boolean isEnabled()
    {
        return enabled;
    }
}
