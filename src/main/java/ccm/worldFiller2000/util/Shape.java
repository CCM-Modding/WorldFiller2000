package ccm.worldFiller2000.util;

import net.minecraft.world.ChunkCoordIntPair;

import java.util.LinkedList;

public enum Shape
{
    SQUARE
            {
                @Override
                public void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad) {
                    for (int x = centerX - rad; x < centerX + rad; x++)
                    {
                        for (int z = centerZ - rad; z < centerZ + rad; z++)
                        {
                            chunkCoordIntPairs.add(new ChunkCoordIntPair(x, z));
                        }
                    }
                }
            },
    ROUND
            {
                @Override
                public void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad)
                {
                    for (int x = centerX - rad; x < centerX + rad; x ++)
                    {
                        for (int z = centerZ - rad; z < centerZ + rad; z ++)
                        {
                            if (((centerX - x) * (centerX - x)) + ((centerZ - z) * (centerZ - z)) < (rad * rad))
                                chunkCoordIntPairs.add(new ChunkCoordIntPair(x, z));
                        }
                    }
                }
            };

    public abstract void generateList(LinkedList<ChunkCoordIntPair> chunkCoordIntPairs, int centerX, int centerZ, int rad);
}
