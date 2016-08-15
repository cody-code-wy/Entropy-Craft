package tech.unstable.entropycraft.blocks;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import tech.unstable.entropycraft.EntropyCraftMod;
import tech.unstable.entropycraft.blocks.TileEntities.EntityTorchEntropy;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by cody on 2016-07-30.
 */
public class BlockTorchEntopy extends BlockTorch implements ITileEntityProvider,IRegistrable{

    int decayRate = 1;

    public BlockTorchEntopy() {
        super();
        this.setTickRandomly(true);
        this.setRegistryName("torch");
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.isBlockContainer = true;

    }

    @Override
    public void registerAll() {

        //CameRegistry.register(this); // Dont register block when using substitute.
        //GameRegistry.register(new ItemBlock(this).setRegistryName(this.getRegistryName())); // Dont register item, use torch


        try {
            GameRegistry.addSubstitutionAlias("minecraft:torch", GameRegistry.Type.BLOCK,this);
            //GameRegistry.addSubstitutionAlias("minecraft:torch", GameRegistry.Type.ITEM, new ItemBlock(this).setRegistryName(this.getRegistryName()));// Why?
        } catch (ExistingSubstitutionException e) {
            EntropyCraftMod.logger.fatal("We are **NOT** alone!",e);
        }

    }

    @Override
    public void registerClient() {

        //Item item = Item.getItemFromBlock(this);
        //int meta = 0;
        //ModelResourceLocation loc = new ModelResourceLocation(this.getRegistryName(),"inventory");
        //ModelLoader.setCustomModelResourceLocation(item,meta,loc);

    }

    @Override
    public void registerServer() {

    }

    @Override
    public void registerAllLate() {

    }

    @Override
    public void registerClientLate() {

    }

    @Override
    public void registerServerLate() {

    }

    @Override
    public void registerAllAfter() {

    }

    @Override
    public void registerClientAfter() {

    }

    @Override
    public void registerServerAfter() {

    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof EntityTorchEntropy){
            EntityTorchEntropy Etorch = (EntityTorchEntropy) te;
            return Etorch.getTorchLight();
        } else {
            EntropyCraftMod.logger.debug("(LV) Tile Entity not found in " + world + " at " + pos);
            EntropyCraftMod.logger.debug("Returning default (0) level");
            return 0;
        }
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.randomTick(worldIn, pos, state, random);
        if ( worldIn.isRemote ){
            EntropyCraftMod.logger.debug("Skipping random tick on client");
            return;
        }
        TileEntity te = worldIn.getTileEntity(pos);
        if (te == null) {
            EntropyCraftMod.logger.debug("(RT) Tile Entity not found in " + worldIn + " at " + pos);
            worldIn.setTileEntity(pos,new EntityTorchEntropy()); // Make a TE for it
            te = worldIn.getTileEntity(pos); //Update var
            EntropyCraftMod.logger.info("Old torch \'upgraded\' in " + worldIn + " at " + pos);
        }
        if (te instanceof EntityTorchEntropy) {
            EntityTorchEntropy Etorch = (EntityTorchEntropy) te;
            Etorch.setTorchLight(Etorch.getTorchLight() - decayRate);
            worldIn.checkLight(pos);
        } else {
            EntropyCraftMod.logger.debug("Tile Entity wrong type (or null) in " + worldIn + " at " + pos);
        }
        EntropyCraftMod.logger.debug("Ticked Randomly!");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new EntityTorchEntropy();
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) //Supposedly this is needed.
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}
