package ccm.worldFiller2000.cmd;

import ccm.worldFiller2000.Filler;
import ccm.worldFiller2000.TickHandler;
import ccm.worldFiller2000.util.Shape;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.List;

public class CommandWorldFiller extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "worldfiller";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "Use \"/worldfiller help\" for help.";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("Help text here.")); //TODO help text here
            return;
        }
        int dim = parseInt(sender, args[0]);
        World world = DimensionManager.getWorld(dim);
        if (world == null)
        {
            throw new WrongUsageException("World " + dim + " isn't loaded or doesn't exists.");
        }
        if (TickHandler.INSTANCE.map.containsKey(dim))
        {
            throw new WrongUsageException("World " + dim + " already has a filler.");
        }
        Filler filler = new Filler(dim, Shape.ROUND, 0, 0, 50, 1);
        TickHandler.INSTANCE.map.put(dim, filler);
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("This filler will need " + filler.getListSize() + " ticks or " + (filler.getListSize()/20) + " seconds."));
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        switch (args.length)
        {
            case 1:
                return getListOfStringsMatchingLastWord(args, "help");
        }
        return null;
    }
}
