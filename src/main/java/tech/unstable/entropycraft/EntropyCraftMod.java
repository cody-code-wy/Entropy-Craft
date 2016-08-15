package tech.unstable.entropycraft;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.unstable.entropycraft.blocks.BlockBurntTorchEntropy;
import tech.unstable.entropycraft.blocks.BlockTorchEntopy;
import tech.unstable.entropycraft.blocks.IRegistrable;
import tech.unstable.entropycraft.blocks.TileEntities.EntityTorchEntropy;
import tech.unstable.entropycraft.proxies.BaseProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ^ ^ Do I actually need all of that? ^ ^ ... Probably

/**
 * Created by cody on 2016-07-30.
 */

@Mod(modid = EntropyCraftMod.MODID, version = EntropyCraftMod.VERSION) // Tell forge this IS _actually_ a **mod**! Very wow, much impress.
public class EntropyCraftMod {

    public static final String MODID = "entropycraft";
    public static final String VERSION = "0.1";
    public static Logger logger = LogManager.getLogger("EntropyCraft <Forge Messed Up>"); // My logger (has a default that should NEVER be used... hopefully. I just hate random NPEs)
    public static List<IRegistrable> localRegistry = new ArrayList<IRegistrable>(); // Holds a copy of everything that this mod registers
    public static Map<Class<? extends  Block>,Block> blockMap = new HashMap<Class<? extends  Block>,Block>();

    @SidedProxy(clientSide = "tech.unstable.entropycraft.proxies.ClientProxy",serverSide = "tech.unstable.entropycraft.proxies.ServerProxy") // Tells what classes to use when creating the proxy instance.
    public static BaseProxy proxy; // Instance of sided proxy

    @Mod.Instance(EntropyCraftMod.MODID) // Tell forge to fill up the instance below
    public static EntropyCraftMod instance; // An instance of this class (if its actually ever needed)


    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){ // Run in the forge pre-init stage
        logger = event.getModLog(); //Get a logger

        // Register all Blocks (Add ot Blocks list to be registered)
        BlockTorchEntopy blockTorchEntopy = new BlockTorchEntopy();
        blockMap.put(BlockTorchEntopy.class,blockTorchEntopy);
        localRegistry.add(blockTorchEntopy);

        BlockBurntTorchEntropy blockBurntTorchEntropy = new BlockBurntTorchEntropy();
        blockMap.put(BlockBurntTorchEntropy.class,blockBurntTorchEntropy);
        localRegistry.add(blockBurntTorchEntropy);

        // Add TileEntities to registry
        localRegistry.add(new EntityTorchEntropy());

        // Do the non-sided registrations
        for(IRegistrable registrable : localRegistry){
            registrable.registerAll();
        }

        proxy.doSidedRegister(); // Do the sided registrations
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) { // Run in the forge init stage (that is when you do all the other stuff other than registration. Ideally...)
        logger.info("MAXIMUM ENTROPY!"); // Just to test the logger (sure that's what it is)

        // Do the non-sided registrations
        for(IRegistrable registrable : localRegistry){
            registrable.registerAllLate();
        }

        proxy.doSidedRegisterLate(); // Do the sided late (init) registrations
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event){ // Run after everything is initialized, at this point hopefully all the stuff is registered and done. Normally used for hacky weird 'change other mods' type stuff.
        // Do the non-sided registrations
        for(IRegistrable registrable : localRegistry){
            registrable.registerAllAfter();
        }

        proxy.doSidedRegisterAfter(); // Do the sided after (postinit) registrations
    }
}
